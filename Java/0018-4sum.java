/**
 * K-Sum Backtracking Approach with Two-Pointer Base Case
 * Time: O(n log n + n^(k-1)) where k is the number of elements to sum
 * Space: O(k) for recursion stack and current combination list
 *
 * Key optimizations:
 * 1. Early pruning with min/max possible sum checks
 * 2. Duplicate skipping at each recursion level
 * 3. Two-pointer technique for k=2 base case
 * 4. Smart bounds checking to break/continue early
 *
 * Algorithm: Sort array, then recursively reduce k-sum to (k-1)-sum by
 * fixing elements one at a time. Use two-pointer for base case (k=2).
 * Prune impossible branches and skip duplicates for unique combinations.
 */
class Solution {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        return kSum(nums, 4, target);
    }

    public List<List<Integer>> kSum(int[] nums, int k, long target) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        kSumBacktrack(nums, k, target, 0, new ArrayList<>(), result);
        return result;
    }

    private void kSumBacktrack(int[] nums, int k, long target, int start, List<Integer> current, List<List<Integer>> result) {
        int n = nums.length;

        if (start + k > n) return;

        long minPossible = 0, maxPossible = 0;
        for (int t = 0; t < k; t++) {
            minPossible += nums[start + t];
            maxPossible += nums[n - 1 - t];
        }
        if (target < minPossible || target > maxPossible) return;

        if (k == 2) {
            int left = start, right = n - 1;
            while (left < right) {
                long sum = (long) nums[left] + nums[right];
                if (sum == target) {
                    List<Integer> combo = new ArrayList<>(current);
                    combo.add(nums[left]);
                    combo.add(nums[right]);
                    result.add(combo);

                    left++;
                    right--;

                    while (left < right && nums[left] == nums[left - 1]) left++;
                    while (left < right && nums[right] == nums[right + 1]) right--;
                } else if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }
            return;
        }

        for (int i = start; i <= n - k; i++) {
            if (i > start && nums[i] == nums[i - 1]) continue;

            long minWithI = nums[i];
            for (int t = 1; t < k; t++) minWithI += nums[i + t];
            if (minWithI > target) break;

            long maxWithI = nums[i];
            for (int t = 0; t < k - 1; t++) maxWithI += nums[n - 1 - t];
            if (maxWithI < target) continue;

            current.add(nums[i]);
            kSumBacktrack(nums, k - 1, target - nums[i], i + 1, current, result);
            current.remove(current.size() - 1);
        }
    }
}
