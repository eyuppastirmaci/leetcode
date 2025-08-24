/**
 * Brute Force
 * Time: O(n3) - n2 substring, n for palindrome check
 * Space: O(1)
 */
class Solution {
    public String longestPalindrome(String s) {
        if (s == null || s.isEmpty()) return "";
        
        int n = s.length();
        int maxStart = 0;
        int maxLength = 1;
        
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (isPalindrome(s, i, j)) {
                    int currentLength = j - i + 1;
                    if (currentLength > maxLength) {
                        maxStart = i;
                        maxLength = currentLength;
                    }
                }
            }
        }
        
        return s.substring(maxStart, maxStart + maxLength);
    }
    
    private boolean isPalindrome(String s, int left, int right) {
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}

/**
 * Dynamic Programming
 * Time: O(n2)
 * Space: O(n2)
 */
class Solution {
    public String longestPalindrome(String s) {
        if (s == null || s.isEmpty()) return "";
        
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        int maxStart = 0;
        int maxLength = 1;
        
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
        }
        
        for (int i = 0; i < n - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                dp[i][i + 1] = true;
                maxStart = i;
                maxLength = 2;
            }
        }
        
        for (int len = 3; len <= n; len++) {
            for (int i = 0; i < n - len + 1; i++) {
                int j = i + len - 1;
                
                if (s.charAt(i) == s.charAt(j) && dp[i + 1][j - 1]) {
                    dp[i][j] = true;
                    maxStart = i;
                    maxLength = len;
                }
            }
        }
        
        return s.substring(maxStart, maxStart + maxLength);
    }
}

/**
 * Expand Around Center
 * Time: O(n2)
 * Space: O(1)
 */
class Solution {
    public String longestPalindrome(String s) {
        if (s == null || s.isEmpty()) return "";
        
        int maxPalindromeStart = 0;
        int maxPalindromeLength = 0;
        
        for (int centerIndex = 0; centerIndex < s.length(); centerIndex++) {
            if (maxPalindromeLength >= (s.length() - centerIndex) * 2) {
                break;
            }
            
            int oddLength = expandAroundCenter(s, centerIndex, centerIndex);
            int evenLength = expandAroundCenter(s, centerIndex, centerIndex + 1);
            int currentMaxLength = Math.max(oddLength, evenLength);
            
            if (currentMaxLength > maxPalindromeLength) {
                maxPalindromeLength = currentMaxLength;
                maxPalindromeStart = centerIndex - (currentMaxLength - 1) / 2;
            }
        }
        
        return s.substring(maxPalindromeStart, maxPalindromeStart + maxPalindromeLength);
    }
    
    private int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;
    }
}

/**
 * Manacher's Algorithm
 * Time: O(n)
 * Space: O(n)
 */
class Solution {
    public String longestPalindrome(String s) {
        if (s == null || s.isEmpty()) return "";
        
        StringBuilder processed = new StringBuilder("#");
        for (char c : s.toCharArray()) {
            processed.append(c).append("#");
        }
        
        String T = processed.toString();
        int n = T.length();
        int[] P = new int[n];
        int center = 0, right = 0;
        int maxLen = 0, centerIndex = 0;
        
        for (int i = 0; i < n; i++) {
            int mirror = 2 * center - i;
            
            if (i < right) {
                P[i] = Math.min(right - i, P[mirror]);
            }
            
            int a = i + P[i] + 1;
            int b = i - P[i] - 1;
            while (a < n && b >= 0 && T.charAt(a) == T.charAt(b)) {
                P[i]++;
                a++;
                b--;
            }
            
            if (i + P[i] > right) {
                center = i;
                right = i + P[i];
            }
            
            if (P[i] > maxLen) {
                maxLen = P[i];
                centerIndex = i;
            }
        }
        
        int start = (centerIndex - maxLen) / 2;
        return s.substring(start, start + maxLen);
    }
}