/*
 *
 * Backtracking
 *
 * n: string length
 *
 * Time : O(n * 2^n)
 * Space: O(n) auxiliary, O(n * 2^n) including the output
 *
 */
class Solution {
    public List<List<String>> partition(String s) {
        List<List<String>> result = new ArrayList<>();
        List<String> sharedState = new ArrayList<>();

        backtrack(0, s, sharedState, result);

        return result;
    }

    private void backtrack(
        int index,
        String s,
        List<String> sharedState,
        List<List<String>> result
    ) {
        if (index == s.length()) {
            result.add(new ArrayList<>(sharedState));

            return;
        }

        for (int end = index + 1; end <= s.length(); end++) {
            String substring = s.substring(index, end);

            if (!isPalindrome(substring)) {
                continue;
            }

            sharedState.add(substring);
            backtrack(end, s, sharedState, result);
            sharedState.remove(sharedState.size() - 1);
        }
    }

    private boolean isPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;

        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }

            left++;
            right--;
        }

        return true;
    }
}
