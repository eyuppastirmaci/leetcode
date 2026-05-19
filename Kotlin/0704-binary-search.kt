/**
 * since the array is sorted in ascending order, if the middle element (mid) is smaller than the target,
 * we know for sure that all elements to its left are also smaller than the target.
 * similarly, if mid is greater than the target, all elements to its right are also greater than the target.
 * by using this property, we compare the middle element with the target at each step and reduce the search space by half.
 * this allows us to eliminate the maximum number of options and reach the correct result in an optimal way.
 *
 * Time Complexity: O(logn): because we eliminate half of the remaining elements at each step. mathematically,
 * dividing a search space of size N by 2 at each step means it takes at most log2(N) steps to reduce the size down to 1.
 * Space Complexity: O(1): because we use only a constant amount of extra memory.
 */
class Solution {
    fun search(nums: IntArray, target: Int): Int {

        var left = 0
        var right = nums.lastIndex

        while (left <= right) {

            val midIndex = left + (right - left) / 2
            val mid = nums[midIndex]

            when {
                mid == target -> return midIndex
                mid < target -> left = midIndex + 1
                else -> right = midIndex - 1
            }
        }

        return -1
    }
}