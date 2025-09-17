/**
 * Two Pointer
 * Time: O(n) - where n is the length of the height array
 * Space: O(1) - only using constant extra space
 */
class Solution {
    public int maxArea(int[] height) {
        int indexLeft = 0;
        int indexRight = height.length - 1;

        int max = 0;

        while (indexLeft < indexRight) {
            int heightLeft = height[indexLeft];
            int heightRight = height[indexRight];
            int baseHeight = Math.min(heightLeft, heightRight);
            int step = indexRight - indexLeft;
            int area = step * baseHeight;

            if (area > max) {
                max = area;
            }

            if (heightLeft < heightRight) {
                indexLeft++;
            } else {
                indexRight--;
            }
        }

        return max;
    }
}