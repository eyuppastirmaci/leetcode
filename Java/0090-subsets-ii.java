/**
 * Backtracking
 *
 * Time: O(n * 2^n) in the worst case
 *       Sorting takes O(n log n), and when all elements are distinct, there are 2^n subsets.
 *       Each call copies sharedState in at most O(n) time, giving O(n log n + n * 2^n) = O(n * 2^n) overall.
 *       Duplicate elements reduce the number of generated subsets through skipped branches, but the worst-case bound remains unchanged.
 *
 * Space: O(n) auxiliary, O(n * 2^n) including the output
 *        Auxiliary space is O(n) because sharedState holds at most n elements and the recursive call stack holds at most n + 1 calls.
 *        Total space including the output is O(n * 2^n) because result stores copies of 2^n subsets in the worst case.
 */
class Solution {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);

        List<List<Integer>> result = new ArrayList<>();
        List<Integer> sharedState = new ArrayList<>();

        backtrack(0, nums, sharedState, result);

        return result;
    }

    private void backtrack(
        int index,
        int[] nums,
        List<Integer> sharedState,
        List<List<Integer>> result
    ) {
        List<Integer> currentSnapshot = new ArrayList<>(sharedState);
        result.add(currentSnapshot);

        for (int i = index; i < nums.length; i++) {
            if (i > index && nums[i] == nums[i - 1]) {
                continue;
            }

            sharedState.add(nums[i]);
            backtrack(i + 1, nums, sharedState, result);
            sharedState.remove(sharedState.size() - 1);
        }
    }
}
