// Manual Reverse
// Time: O(n), one pass to normalize and one pass to fill the reverse array, plus a final O(n) equals.
// Space: O(n) extra, the normalized string and the reverse char array each hold up to n characters.
class Solution {
    public boolean isPalindrome(String s) {

        String normalized = s.toLowerCase().replaceAll("[^a-z0-9]", "");

        char[] reverseCharArray = new char[normalized.length()];

        for (int i = normalized.length() - 1; i >= 0; i--) {
            reverseCharArray[normalized.length() - 1 - i] = normalized.charAt(i);
        }

        String reverseWord = String.valueOf(reverseCharArray);

        return normalized.equals(reverseWord);
    }
}

// StringBuilder reverse
// Time: O(n), normalize is linear and StringBuilder.reverse swaps in-place over n characters, then equals is O(n).
// Space: O(n) extra, the normalized string and the StringBuilder's internal buffer each hold up to n characters.
class Solution {
    public boolean isPalindrome(String s) {

        String normalized = s.toLowerCase().replaceAll("[^a-z0-9]", "");
        String reversed = new StringBuilder(normalized).reverse().toString();
        return normalized.equals(reversed);
    }
}

// Two pointers
// Time: O(n), each character is visited at most once by either pointer.
// Space: O(1) extra, only two integer indices are kept.
class Solution {
    public boolean isPalindrome(String s) {

        int lpi = 0;
        int rpi = s.length() - 1;

        while (lpi < rpi) {
            while (lpi < rpi && !Character.isLetterOrDigit(s.charAt(lpi))) {
                lpi++;
            }

            while (lpi < rpi && !Character.isLetterOrDigit(s.charAt(rpi))) {
                rpi--;
            }

            if (Character.toLowerCase(s.charAt(lpi)) != Character.toLowerCase(s.charAt(rpi))) {
                return false;
            }

            lpi++;
            rpi--;
        }

        return true;
    }
}