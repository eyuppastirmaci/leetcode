/**
 * Time: O(n) where n is the length of the input string
 * Space: O(1) only uses constant extra space
 */
class Solution {
    public int myAtoi(String s) {
        int position = 0;
        final int inputLength = s.length();
        
        while (position < inputLength && s.charAt(position) == ' ') position++;
        
        int signMultiplier = 1;
        if (position < inputLength && (s.charAt(position) == '-' || s.charAt(position) == '+')) {
            signMultiplier = (s.charAt(position) == '-') ? -1 : 1;
            position++;
        }
        
        if (position >= inputLength || !Character.isDigit(s.charAt(position))) return 0;
        
        int accumulatedValue = 0;
        final int maxDiv10 = Integer.MAX_VALUE / 10;
        
        for (; position < inputLength && Character.isDigit(s.charAt(position)); position++) {
            int digit = s.charAt(position) - '0';
            
            if (accumulatedValue > maxDiv10) {
                return (signMultiplier == 1) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            
            if (accumulatedValue == maxDiv10 && ((signMultiplier == 1 && digit > 7) || (signMultiplier == -1 && digit >= 8))) {
                return (signMultiplier == 1) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            
            accumulatedValue = accumulatedValue * 10 + digit;
        }
        
        return signMultiplier * accumulatedValue;
    }
}