/**
 * Horizontal Scanning Approach
 * Time: O(S) where S is the sum of all characters in all strings
 * Space: O(1) excluding the output, no extra data structures used
 *
 * Key optimization: Uses built-in startsWith() method for clean code and early
 * termination when prefix becomes empty. Avoids unnecessary character by character
 * comparison with manual loops.
 *
 * Algorithm: Initialize prefix with first string. For each subsequent string,
 * progressively shorten prefix by removing the last character until the string
 * starts with the prefix. If prefix becomes empty, immediately return empty string.
 * This handles all edge cases: empty array, single string, no common prefix.
 */
class Solution {
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) return "";

        String prefix = strs[0];

        for (int i = 1; i < strs.length; i++) {
            while (!strs[i].startsWith(prefix)) {
                prefix = prefix.substring(0, prefix.length() - 1);

                if (prefix.isEmpty()) return "";
            }
        }

        return prefix;
    }
}