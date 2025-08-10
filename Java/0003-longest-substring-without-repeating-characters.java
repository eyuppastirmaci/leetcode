/**
 * Brute Force
 * Time: O(n3) - n2 substring, n for unique character check
 * Space: O(min(n, m)) - m = charset size, HashSet for checking
 */
class Solution {
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        
        int maxLength = 0;
        
        for (int i = 0; i < s.length(); i++) {
            for (int j = i; j < s.length(); j++) {
                if (hasUniqueChars(s, i, j)) {
                    maxLength = Math.max(maxLength, j - i + 1);
                }
            }
        }
        
        return maxLength;
    }
    
    private boolean hasUniqueChars(String s, int start, int end) {
        Set<Character> chars = new HashSet<>();
        for (int i = start; i <= end; i++) {
            if (!chars.add(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}

/**
 * Sliding Window with HashSet
 * Time: O(2n) = O(n) - worst case visiting each character twice.
 * Space: O(min(n, m)) - m = charset size
 */
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

/**
 * Sliding Window with HashMap
 * Time: O(n)
 * Space: O(min(n, m)) - m = charset size
 */
class Solution {
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        
        Map<Character, Integer> charIndexMap = new HashMap<>();
        int maxLength = 0;
        int windowStart = 0;
        
        for (int windowEnd = 0; windowEnd < s.length(); windowEnd++) {
            char currentChar = s.charAt(windowEnd);
            
            if (charIndexMap.containsKey(currentChar)) {
                windowStart = Math.max(windowStart, charIndexMap.get(currentChar) + 1);
            }
            
            charIndexMap.put(currentChar, windowEnd);
            
            maxLength = Math.max(maxLength, windowEnd - windowStart + 1);
        }
        
        return maxLength;
    }
}