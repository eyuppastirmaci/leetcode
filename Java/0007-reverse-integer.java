/**
 * Digit-by-Digit Reversal
 * Time: O(log |x|) where x is the input number
 * Space: O(1) only uses constant extra space
 */
class Solution {
    public int reverse(int x) {
        int result = 0;

        while (x != 0) {
            // Extract last digit
            int digit = x % 10;
            x /= 10;

            // Check for 32-bit overflow before multiplying by 10
            if (result > Integer.MAX_VALUE / 10 || (result == Integer.MAX_VALUE / 10 && digit > 7)) {
                return 0;
            }
            if (result < Integer.MIN_VALUE / 10 || (result == Integer.MIN_VALUE / 10 && digit < -8)) {
                return 0;
            }

            result = result * 10 + digit;
        }

        return result;
    }
}