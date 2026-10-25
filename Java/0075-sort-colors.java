/*
 *
 * Counting Sort
 *
 * n: number of elements
 *
 * Time complexity: O(n)
 *                  one pass to count each color, one pass to overwrite the array
 *
 * Space complexity: O(1)
 *                   only two counters, the 2s fill whatever is left
 *
 */
class Solution {
    public void sortColors(int[] nums) {

        int count0 = 0; // red
        int count1 = 0; // white

        for (int num : nums) {
            switch (num) {
                case 0 -> count0++;
                case 1 -> count1++;
            }
        }

        for (int i = 0; i < nums.length; i++) {
            if (i < count0) {
                nums[i] = 0;
            } else if (i < count0 + count1) {
                nums[i] = 1;
            } else {
                nums[i] = 2;
            }
        }
    }
}

/*
 *
 * Dutch National Flag
 *
 * n: number of elements
 *
 * Time complexity: O(n)
 *                  single pass, each step either advances mid or shrinks high
 *
 * Space complexity: O(1)
 *                   sorts in place with three pointers
 *
 */
class Solution {
    public void sortColors(int[] nums) {

        int low = 0; // boundary between 0s and 1s, next 0 goes here
        int mid = 0; // current element, boundary between 1s and unvisited
        int high = nums.length - 1; // boundary between unvisited and 2s, next 2 goes here
        // 0 0 .. | 1 1 .. | unvisited | 2 2 ..
        //       low      mid         high

        while (mid <= high) {
            switch (nums[mid]) {
                // move 0 to the end of the 0s region; the swapped-in element is already visited, so advance both
                case 0 -> {
                    swap(nums, low, mid);
                    low++;
                    mid++;
                }

                // 1 is already in its region, just move on
                case 1 -> mid++;

                // move 2 to the start of the 2s region; don't advance mid since the swapped-in element is unvisited
                case 2 -> {
                    swap(nums, mid, high);
                    high--;
                }
            }
        }

    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
