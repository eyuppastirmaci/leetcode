/*
 *
 * Hash Map
 *
 * Counts each element's occurrences and returns it as soon as
 * its frequency exceeds n / 2, without scanning the rest
 *
 * n: length of nums
 *
 * Time complexity: O(n) average
 *                  at most one pass with O(1) average hash map operations,
 *                  O(n log n) worst case due to treeified buckets
 *
 * Space complexity: O(n)
 *                   at most n / 2 + 1 distinct values are stored
 *                   (does NOT satisfy the O(1) space follow-up)
 *
 */
class Solution {
    public int majorityElement(int[] nums) {

        Map<Integer, Integer> counts = new HashMap<>();
        int threshold = nums.length / 2;

        for (int num : nums) {
            int currentCount = counts.getOrDefault(num, 0) + 1;

            if (currentCount > threshold) {
                return num;
            }

            counts.put(num, currentCount);
        }

        throw new IllegalArgumentException("No majority element exists in the given array.");
    }
}

/*
 *
 * Sorting
 *
 * The majority element fills more than half of the sorted array,
 * so its block must cover the middle index n / 2
 *
 * n: length of nums
 *
 * Time complexity: O(n log n)
 *                  dominated by Arrays.sort, the middle lookup is O(1)
 *                  (guaranteed since JDK 14, which falls back to heap sort
 *                  when recursion gets too deep, older JDKs have a
 *                  theoretical O(n^2) worst case)
 *
 * Space complexity: O(log n) typically
 *                   Arrays.sort on int[] uses Dual-Pivot Quicksort, which
 *                   needs O(log n) recursion stack, but on nearly sorted
 *                   inputs it may switch to merge sort and allocate an
 *                   O(n) auxiliary buffer
 *
 * Note: nums is modified in place by the sort
 *
 */
class Solution {
    public int majorityElement(int[] nums) {

        Arrays.sort(nums);

        return nums[nums.length / 2];
    }
}

/*
 *
 * Boyer-Moore Majority Vote Algorithm
 *
 * Each decrement cancels the candidate against a different value,
 * the majority element outnumbers all others combined, so it survives
 *
 * Assumes a majority element exists, otherwise verify with a second pass
 *
 * Note: the final value of count is not the candidate's actual frequency
 *
 * n: length of nums
 *
 * Time complexity: O(n)
 *                  a single pass over nums with O(1) work per element
 *
 * Space complexity: O(1)
 *                   only candidate and count are stored
 *
 */
class Solution {
    public int majorityElement(int[] nums) {

        int candidate = 0;
        int count = 0;

        for (int num : nums) {

            if (count == 0) {
                candidate = num;
                count++;
            } else if (candidate == num) {
                count++;
            } else {
                count--;
            }

        }

        return candidate;
    }
}