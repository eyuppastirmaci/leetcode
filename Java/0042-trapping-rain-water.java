// Two pointers
// Time: O(n), each pointer moves toward the other and together they traverse the array at most once.
// Space: O(1) extra, only a few integer variables are kept.
class Solution {
    public int trap(int[] height) {

        int left = 0;
        int right = height.length - 1;

        int leftMax = 0;
        int rightMax = 0;
        int water = 0;

        while (left < right) {

            if (height[left] < height[right]) {

                leftMax = Math.max(leftMax, height[left]);
                water += leftMax - height[left];
                left++;
            } else {

                rightMax = Math.max(rightMax, height[right]);
                water += rightMax - height[right];
                right--;
            }

        }

        return water;
    }
}