/**
 * Binary Search (Half-Open Interval)
 *
 * Time: O(log n)
 * Each iteration halves the search space [lo, hi), so the loop runs at most log2(n) times.
 *
 * Space: O(1)
 * Only a constant number of variables are used regardless of input size.
 *
 */
class Solution {
    fun searchInsert(nums: IntArray, target: Int): Int {

        var lo = 0
        var hi = nums.size

        while (lo < hi) {

            val mid = lo + (hi - lo) / 2

            if (nums[mid] < target) {
                lo = mid + 1
            } else {
                hi = mid
            }

        }



        return lo

    }
}