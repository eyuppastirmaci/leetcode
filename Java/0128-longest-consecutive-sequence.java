// Time: O(n), the inner while loop only runs when num is the start of a sequence
// (num-1 not in set), so each number is visited at most twice across all iterations.
// Space: O(n), the HashSet stores all unique numbers from nums.
class Solution {
    public int longestConsecutive(int[] nums) {

        Set<Integer> set = new HashSet<>();

        for (int num : nums) {
            set.add(num);
        }

        int longestLength = 0;

        for (int num: set) {
            if (!set.contains(num - 1)) {

                int currentNum = num;
                int currentLength = 1;

                while (set.contains(currentNum+1)) {
                    currentLength++;
                    currentNum++;
                }

                if (currentLength > longestLength) {
                    longestLength = currentLength;
                }
            }
        }

        return longestLength;
    }
}