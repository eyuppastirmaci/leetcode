/*
 *
 * n: length of nums
 *
 * Time complexity: O(n)
 * Space complexity: O(1) extra, the output array of size 2n doesn't count
 *
 */
class Solution {
    public int[] getConcatenation(int[] nums) {
        int length = nums.length;

        int[] answer = new int[2 * length];

        for (int i = 0; i < length; i++) {
            answer[i] = nums[i];
            answer[i + length] = nums[i];
        }

        return answer;
    }
}

/*
 *
 * n: length of nums
 *
 * Time complexity: O(n)
 * Space complexity: O(1) extra, the output array of size 2n doesn't count
 *
 */
class Solution {
    public int[] getConcatenation(int[] nums) {
        int length = nums.length;

        int[] answer = Arrays.copyOf(nums, 2 * length);
        System.arraycopy(nums, 0, answer, length, length);

        return answer;
    }
}
