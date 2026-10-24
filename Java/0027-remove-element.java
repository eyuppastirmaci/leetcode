/*
 *
 * Two Pointers
 *
 * n: length of nums
 *
 * Time complexity: O(n)
 *                  each iteration either moves left forward or right backward,
 *                  so the pointers meet after at most n steps
 *
 * Space complexity: O(1)
 *                   the array is modified in place, only two pointers are used
 *
 */
class Solution {
    public int removeElement(int[] nums, int val) {

        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            if (nums[left] != val) {
                left++;
            } else {
                nums[left] = nums[right];
                nums[right] = 0;
                right--;
            }
        }

        return right + 1;
    }
}
