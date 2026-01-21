/**
 * Two Pointers
 * Time: O(N) - The array is traversed exactly once.
 * Space: O(1) - Mutates the input array in-place without allocating a new array.
 */
class Solution {
    fun removeDuplicates(nums: IntArray): Int {

        if (nums.isEmpty()) return 0

        var insertIndex = 1

        for (i in 1 until nums.size) {

            if (nums[i] != nums[i - 1]) {
                nums[insertIndex] = nums[i]
                insertIndex++;
            }

        }

        return insertIndex;
    }
}