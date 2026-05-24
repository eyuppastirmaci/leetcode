/**
 * In this solution, instead of merging two arrays in O(m + n) time, we create imaginary left and right partitions as if
 * there were a merged array based on the total number of elements. We calculate how many elements should be in the left
 * partition using `totalSize`. By performing a binary search on the shorter array, we determine how many elements to
 * take from that array for the left side. The number of elements to take from the other array is automatically
 * determined to complete the left partition. Then, we simply check the values at the partition boundaries.
 * If the largest value to the left of `nums1` is less than or equal to the smallest value to the right of `nums2`,
 * and the largest value to the left of `nums2` is less than or equal to the smallest value to the right of `nums1`,
 * the correct partition has been found. Otherwise, we adjust the number of elements taken from the left side of `nums1`
 * using binary search.
 *
 * The time complexity is O(log(min(m, n))), because we always perform the binary search on the shorter array, and at
 * each step, the partition reduces the search range by approximately half.
 *
 * The space complexity is O(1) because we track the partition boundaries using only a few variables, without creating a
 * new merged array.
 *
 */
class Solution {
    fun findMedianSortedArrays(nums1: IntArray, nums2: IntArray): Double {

        if (nums1.size > nums2.size) {
            return findMedianSortedArrays(nums2, nums1)
        }

        val totalSize = nums1.size + nums2.size
        val leftPartitionSize = (totalSize + 1) / 2

        var left = 0
        var right = nums1.size

        while (left <= right) {
            val nums1Partition = left + (right - left) / 2
            val nums2Partition = leftPartitionSize - nums1Partition

            val nums1LeftMax = if (nums1Partition == 0) {
                Int.MIN_VALUE
            } else {
                nums1[nums1Partition - 1]
            }

            val nums1RightMin = if (nums1Partition == nums1.size) {
                Int.MAX_VALUE
            } else {
                nums1[nums1Partition]
            }

            val nums2LeftMax = if (nums2Partition == 0) {
                Int.MIN_VALUE
            } else {
                nums2[nums2Partition - 1]
            }

            val nums2RightMin = if (nums2Partition == nums2.size) {
                Int.MAX_VALUE
            } else {
                nums2[nums2Partition]
            }

            if (nums1LeftMax <= nums2RightMin && nums2LeftMax <= nums1RightMin) {
                val leftMax = maxOf(nums1LeftMax, nums2LeftMax).toDouble()

                if (totalSize % 2 == 1) {
                    return leftMax
                }

                val rightMin = minOf(nums1RightMin, nums2RightMin).toDouble()

                return (leftMax + rightMin) / 2.0
            }

            if (nums1LeftMax > nums2RightMin) {
                right = nums1Partition - 1
            } else {
                left = nums1Partition + 1
            }
        }

        throw IllegalStateException("unreachable")
    }
}