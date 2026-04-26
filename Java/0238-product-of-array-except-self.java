// Brute Force
// Time: O(n2), nested loops multiply every other element for each index.
// Space: O(1) extra, the output array doesn't count.
class Solution {
    public int[] productExceptSelf(int[] nums) {

        int[] answer = new int[nums.length];

        for (int i = 0; i < nums.length; i++) {

            int mul = 1;

            for (int j = 0; j < nums.length; j++) {
                if (i != j) {
                    mul *= nums[j];
                }
            }

            answer[i] = mul;
        }

        return answer;
    }
}

// Time: O(n), one ltr pass for prefixes and one rtl pass folding in suffixes.
// Space: O(1) extra, prefixes are stored in the output array and suffixes are tracked in a single variable.
class Solution {
    public int[] productExceptSelf(int[] nums) {

        int[] answer = new int[nums.length];

        answer[0] = 1;
        for (int i = 1; i < nums.length; i++) {

            answer[i] = answer[i-1] * nums[i-1];

        }

        int right = 1;
        for (int i = nums.length - 1; i >= 0; i--) {

            answer[i] = answer[i] * right;
            right = right * nums[i];

        }

        return answer;
    }
}