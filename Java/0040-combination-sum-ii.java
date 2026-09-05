/**
 * Sorted Backtracking with Duplicate Skipping
 * 
 * n: the number of candidates 
 * k: the number of valid combinations
 * 
 * Time: O(n log n + 2^n + k * n)
 * Space: O(n) auxiliary, O(k * n) for the output
 */
class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);

        List<List<Integer>> result = new ArrayList<>();
        List<Integer> sharedState = new ArrayList<>();

        backtrack(0, target, sharedState, candidates, result);

        return result;
    }

    private void backtrack(
        int index,
        int remaining,
        List<Integer> sharedState,
        int[] candidates,
        List<List<Integer>> result
    ) {
        if (remaining == 0) {
            List<Integer> currentSnapshot = new ArrayList<>(sharedState);
            result.add(currentSnapshot);
            return;
        }

        for (int i = index; i < candidates.length; i++) {

            boolean hasPrevious = i > index;
            // current candidate is a duplicate of the previous one (because the array is sorted)
            boolean isDuplicate = hasPrevious && candidates[i] == candidates[i - 1];

            // duplicated candidate skip current
            if (isDuplicate) {
                continue;
            }

            // no later candidate can fit (because the array is sorted)
            if (candidates[i] > remaining) {
                break;
            }

            sharedState.add(candidates[i]);
            int nextRemaining = remaining - candidates[i];
            backtrack(i + 1, nextRemaining, sharedState, candidates, result);
            sharedState.remove(sharedState.size() - 1);
        }

    }
}
