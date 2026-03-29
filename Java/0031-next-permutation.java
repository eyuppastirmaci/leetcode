/**
 * Time: O(n) - in the worst case, the array is scanned to find the pivot,
 *              scanned again from the right to find the successor,
 *              and the suffix is reversed. These are linear passes, so the total is O(n).
 *
 * Space: O(1) - the permutation is modified in place without allocating any extra array.
 */
class Solution {
    public void nextPermutation(int[] nums) {

        int pivotIndex = -1;

        for (int scanIndex = nums.length - 2; scanIndex >= 0; scanIndex--) {
            if (nums[scanIndex] < nums[scanIndex + 1]) {
                pivotIndex = scanIndex;
                break;
            }
        }

        if (pivotIndex != -1) {
            for (int successorIndex = nums.length - 1; successorIndex > pivotIndex; successorIndex--) {
                if (nums[successorIndex] > nums[pivotIndex]) {
                    swap(nums, pivotIndex, successorIndex);
                    break;
                }
            }
        }

        // If no pivot is found, the array is in descending order, reversing the whole array transforms it into the
        // smallest permutation.
        reverseArray(nums, pivotIndex + 1, nums.length - 1);
    }

    private void swap(int[] nums, int firstIndex, int secondIndex) {
        int temp = nums[firstIndex];
        nums[firstIndex] = nums[secondIndex];
        nums[secondIndex] = temp;
    }

    private void reverseArray(int[] nums, int left, int right) {
        while (left < right) {
            swap(nums, left, right);
            left++;
            right--;
        }
    }
}