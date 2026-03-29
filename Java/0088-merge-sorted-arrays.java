/**
 * Two Pointers
 * Time: O(m + n) - the algorithm traverses both arrays from the end toward the beginning,
 * so the runtime grows linearly with the total number of elements in nums1 and nums2.
 * Space: O(1) - no extra array is created; nums1 is modified in place using only a few pointer variables.
 */
class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {

        int write = m + n - 1;
        int p1 = m - 1;
        int p2 = n - 1;

        while (p2 >= 0) {

            if (p1 < 0 || nums1[p1] <= nums2[p2]) {

                nums1[write] = nums2[p2];
                p2--;

            } else {

                nums1[write] = nums1[p1];
                p1--;

            }

            write--;

        }

    }

}