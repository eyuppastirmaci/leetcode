class Solution {
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        Set<Character> uniqueChars = new HashSet<>();
        
        int windowStart = 0;
        int windowEnd = 0;
        int maxLength = 0;

        while (windowEnd < s.length()) {
            char rightChar = s.charAt(windowEnd);

            if (uniqueChars.contains(rightChar)) {
                uniqueChars.remove(s.charAt(windowStart));
                windowStart++;
            } else {
                uniqueChars.add(rightChar);
                windowEnd++;
                maxLength = Math.max(maxLength, uniqueChars.size());
            }
        }
        return maxLength;
    }
}