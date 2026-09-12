import { readFile, writeFile } from 'node:fs/promises';

const readmePath = new URL('../README.md', import.meta.url);
const startMarker = '<!-- leetcode-progress:start -->';
const endMarker = '<!-- leetcode-progress:end -->';

export function countSolved(readme) {
  const rows = readme.matchAll(/^\|\s*(\d+)\s*\|\s*\[[^\]\r\n]+\]\(https:\/\/leetcode\.com\/problems\/[^)]+\)/gm);

  return new Set(Array.from(rows, (row) => Number(row[1]))).size;
}

export async function fetchTotal() {
  const response = await fetch('https://leetcode.com/graphql/', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Referer': 'https://leetcode.com/problemset/',
      'User-Agent': 'leetcode-readme-progress/1.0',
    },
    body: JSON.stringify({ query: '{ allQuestionsCount { difficulty count } }' }),
    signal: AbortSignal.timeout(15000),
  });

  if (!response.ok) {
    throw new Error(`LeetCode returned HTTP ${response.status}; keeping the saved counter without retrying.`);
  }

  const payload = await response.json();

  if (payload.errors?.length || !Array.isArray(payload.data?.allQuestionsCount)) {
    throw new Error('LeetCode did not return question counts.');
  }

  const total = payload.data.allQuestionsCount.find((entry) => entry.difficulty === 'All')?.count;

  if (!Number.isSafeInteger(total) || total <= 0) {
    throw new Error('LeetCode returned an invalid total question count.');
  }

  return total;
}

export function readSavedTotal(readme) {
  const progress = readme.match(/<!-- leetcode-progress:start -->([\s\S]*?)<!-- leetcode-progress:end -->/)?.[1];
  const total = Number(progress?.match(/<!-- leetcode-total: (\d+) -->/)?.[1]);

  if (!Number.isSafeInteger(total) || total <= 0) {
    throw new Error('No valid saved total in README. Run without --offline to fetch it once.');
  }

  return total;
}

export function updateProgress(readme, total) {
  const solved = countSolved(readme);

  if (!Number.isSafeInteger(total) || total <= 0 || total < solved) {
    throw new Error('The total must be a positive integer at least as large as the solved count.');
  }

  const newline = readme.includes('\r\n') ? '\r\n' : '\n';
  const badgeUrl = `https://img.shields.io/badge/LeetCode-${solved}%2F${total}-FFA116?style=for-the-badge&logo=leetcode&logoColor=FFA116&labelColor=222222`;
  const badge = `[![LeetCode: ${solved}/${total} solved](${badgeUrl})](https://leetcode.com/problemset/)`;
  const progress = [startMarker, `<!-- leetcode-total: ${total} -->`, badge, endMarker].join(newline);
  const start = readme.indexOf(startMarker);
  const end = readme.indexOf(endMarker);

  if (start === -1 && end === -1) {
    const headingEnd = readme.indexOf('\n');

    if (headingEnd === -1) {
      throw new Error('README must contain a heading followed by the problem table.');
    }

    return readme.slice(0, headingEnd + 1) + newline + progress + newline + newline + readme.slice(headingEnd + 1);
  }

  if (start === -1 || end < start || readme.indexOf(startMarker, start + 1) !== -1 || readme.indexOf(endMarker, end + 1) !== -1) {
    throw new Error('README progress markers are missing, duplicated, or out of order.');
  }

  return readme.slice(0, start) + progress + readme.slice(end + endMarker.length);
}

async function main() {
  const args = process.argv.slice(2);

  if (args.some((arg) => arg !== '--offline')) {
    throw new Error('Usage: node scripts/update-leetcode-progress.mjs [--offline]');
  }

  // Push runs reuse the saved total so new solutions do not trigger LeetCode requests.
  const total = args.includes('--offline')
    ? readSavedTotal(await readFile(readmePath, 'utf8'))
    : await fetchTotal();
  const readme = await readFile(readmePath, 'utf8');
  const updated = updateProgress(readme, total);

  if (updated === readme) {
    console.log(`README already up to date: ${countSolved(readme)}/${total}`);

    return;
  }

  await writeFile(readmePath, updated, 'utf8');
  console.log(`Updated README: ${countSolved(readme)}/${total}`);
}

if (import.meta.main) {
  main().catch((error) => {
    console.error(`Could not update LeetCode progress: ${error.message}`);
    process.exitCode = 1;
  });
}
