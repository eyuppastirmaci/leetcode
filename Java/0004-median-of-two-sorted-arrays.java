/**
 * Binary Search
 * Time: O(log(min(m, n)))
 * Space: O(1)
 */
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }
        
        int smallerArrayLength = nums1.length;
        int largerArrayLength = nums2.length;
        
        int totalLeft = (smallerArrayLength + largerArrayLength + 1) / 2;
        
        int left = 0;
        int right = smallerArrayLength;
        
        while (left < right) {
            int partitionSmaller = left + (right - left) / 2;
            int partitionLarger = totalLeft - partitionSmaller;
            
            if (nums1[partitionSmaller] < nums2[partitionLarger - 1]) {
                left = partitionSmaller + 1;
            } else {
                right = partitionSmaller;
            }
        }
        
        int finalPartitionSmaller = left;
        int finalPartitionLarger = totalLeft - finalPartitionSmaller;
        
        int nums1LeftMax = finalPartitionSmaller == 0 ? Integer.MIN_VALUE : nums1[finalPartitionSmaller - 1];
        int nums2LeftMax = finalPartitionLarger == 0 ? Integer.MIN_VALUE : nums2[finalPartitionLarger - 1];
        int leftMax = Math.max(nums1LeftMax, nums2LeftMax);
        
        if ((smallerArrayLength + largerArrayLength) % 2 == 1) {
            return leftMax;
        }
        
        int nums1RightMin = finalPartitionSmaller == smallerArrayLength ? Integer.MAX_VALUE : nums1[finalPartitionSmaller];
        int nums2RightMin = finalPartitionLarger == largerArrayLength ? Integer.MAX_VALUE : nums2[finalPartitionLarger];
        int rightMin = Math.min(nums1RightMin, nums2RightMin);
        
        return (leftMax + rightMin) / 2.0;
    }
}