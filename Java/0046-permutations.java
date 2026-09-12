/**
 * Backtracking
 * 
 * Time: O(n^2 * n!)
 *       There are n! permutations, and each non-terminal call checks n elements with a sharedState.contains scan that can take O(n) time.
 * 
 * Space: O(n) auxiliary, O(n * n!) including the output
 *        Auxiliary space is O(n) because sharedState holds at most n elements and the recursive call stack holds at most n + 1 calls. 
 *        Total space including the output is O(n * n!) because result stores n! lists containing n elements each.
 */
class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> sharedState = new ArrayList<>();

        backtrack(nums, sharedState, result);

        return result;
    }

    private void backtrack(
        int[] nums,
        List<Integer> sharedState,
        List<List<Integer>> result
    ) {
        if (sharedState.size() == nums.length) {
            List<Integer> currentSnapshot = new ArrayList<>(sharedState);
            result.add(currentSnapshot);

            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (!sharedState.contains(nums[i])) {
                sharedState.add(nums[i]);
                backtrack(nums, sharedState, result);
                sharedState.remove(sharedState.size() - 1);
            }
        }
    }
}
