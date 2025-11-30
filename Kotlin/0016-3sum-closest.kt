/**
 * Two Pointers
 * Time Complexity: O(n^2) nested loops dominate the O(n log n) sorting cost.
 * Space Complexity: O(log n) Dual-Pivot Quicksort for primitives requires stack space.
 */
class Solution {
    fun threeSumClosest(nums: IntArray, target: Int): Int {
        nums.sort()

        var closestSum = nums[0] + nums[1] + nums[2]

        for (i in 0 until nums.size -2) {

            var left = i + 1
            var right = nums.size -1

            while (left < right) {

                val currentSum = nums[i] + nums[left] + nums[right]

                if (currentSum == target) return target

                if ((target - currentSum).absoluteValue < (target - closestSum).absoluteValue) {
                    closestSum = currentSum
                }

                if (currentSum < target) left++ else right--
            }

        }

        return closestSum
    }
}