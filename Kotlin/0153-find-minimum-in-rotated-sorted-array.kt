/**
 *
 * Since we rotate the array, we can think of it as being split into two sorted segments.
 * For example, in the array [4,5,6,7,0,1,2], the split point is between 7 and 0, and the first element after this
 * split is the smallest element in the array.
 *
 * When performing a binary search, we compare the current nums[mid] element with nums[right].
 * If nums[mid] > nums[right], the mid element is in the larger segment to the left of the split.
 * In this case, the minimum element is definitely to the right of mid, so we move to the right by setting left = mid + 1.
 *
 * Otherwise, nums[mid] < nums[right]. Since the elements in this problem are unique, when left < right,
 * mid and right have different indices, and the condition nums[mid] == nums[right] does not occur.
 * In this case, the mid element is in the smaller/sorted portion to the right of the split, or the current range is
 * already sorted. The minimum element could be mid itself or it could be to the left of mid.
 * Therefore, to avoid excluding mid, we set right = mid.
 *
 * We continue this process until the left and right boundaries are equal. When the loop ends, left and right point to
 * the same index, which is the index of the smallest element in the array. Consequently, nums[left] is returned.
 *
 * Time: O(log n), because each binary search step eliminates half of the remaining search range.
 * Space: O(1), because we only use a constant amount of extra variables.
 *
 */
class Solution {
    fun findMin(nums: IntArray): Int {

        var left = 0
        var right = nums.size - 1

        while (left < right) {

            val mid = left + (right - left) / 2

            if (nums[mid] > nums[right]) {
                left = mid + 1
            } else {
                right = mid
            }
        }

        return nums[left]
    }
}