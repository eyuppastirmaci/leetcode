/**
 * A rotated sorted array actually consists of two separate sorted segments.
 * The main idea behind this solution is as follows: by using nums[n - 1] as a fixed reference, we can determine which
 * segment any given element belongs to. Thus, at each step, the core question boils down to: Are target and nums[mid]
 * in the same segment?
 *
 * First, we set `last` to `nums[nums.size - 1]`.
 * If `target > last`, `target` is in the left segment;
 * if `target <= last`, it is in the right segment. During the binary search, we calculate `mid`.
 * If `nums[mid] == target`, we return `mid` directly.
 * Otherwise, we determine which segment `mid` is in by checking if `nums[mid] > last`.
 *
 * If target and nums[mid] are in the same segment, we can perform a standard binary search, since the segment itself is sorted.
 * If target > nums[mid], we move right; if target < nums[mid], we move left.
 * If they are in different segments, the value comparison can be misleading.
 * In this case:
 * if target is in the left segment, we set right = mid - 1;
 * if target is in the right segment, we set left = mid + 1.
 *
 * This method works within a single binary search without needing to find the pivot separately.
 * In the standard solution, we typically check which half is sorted. Here, however, we use the last element as a fixed
 * reference and reduce the segment division to a single comparison. Since the values are distinct, this division is reliable.
 * If there were duplicates, we might not be able to definitively separate the segments in cases like `nums[mid] == last`.
 *
 * Time: The time complexity is O(log n), because at each step we halve the search range using binary search logic.
 * In an array with n elements, we take at most log n steps.
 *
 * Space: Since we don’t use any extra data structures and only
 * maintain a few variables like left, right, mid, and last, the space complexity is O(1).
 */
class Solution {
    fun search(nums: IntArray, target: Int): Int {
        var left = 0
        var right = nums.size - 1

        val last = nums[nums.size - 1]
        val targetInLeftSegment = target > last

        while (left <= right) {
            val mid = left + (right - left) / 2

            if (nums[mid] == target) {
                return mid
            }

            val midInLeftSegment = nums[mid] > last
            val sameSegment = targetInLeftSegment == midInLeftSegment

            if (sameSegment) {
                if (target > nums[mid]) {
                    left = mid + 1
                } else {
                    right = mid - 1
                }
            } else {
                if (targetInLeftSegment) {
                    right = mid - 1
                } else {
                    left = mid + 1
                }
            }
        }

        return -1
    }
}