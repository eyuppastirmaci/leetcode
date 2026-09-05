/**
 * Include / Exclude Backtracking
 * 
 * n: the number of candidates 
 * m: the smallest candidate
 * k: the number of valid combinations
 * 
 * Time: O(2^(n + target / m) + k * target / m)
 * Space: O(n + target / m) auxiliary, O(k * target / m) for the output
 */
class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
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

        if (index == candidates.length || remaining < 0) {
            return;
        }

        sharedState.add(candidates[index]);
        int nextRemaining = remaining - candidates[index];
        backtrack(index, nextRemaining, sharedState, candidates, result);
        sharedState.remove(sharedState.size() - 1);
        backtrack(index + 1, remaining, sharedState, candidates, result);
    }
}
