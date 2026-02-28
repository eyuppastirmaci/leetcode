/**
 * Time: O(n)
 * trimEnd() scans from the end to remove trailing spaces, and split(" ") traverses the entire string to create substrings.
 *
 * Space: O(n)
 * split() creates a list of all word substrings, which in the worst case stores the entire string content.
 *
 */
class Solution {
    fun lengthOfLastWord(s: String): Int {
        return s.trimEnd().split(" ").last().length
    }
}

/**
 * Time: O(n)
 * In the worst case, both while loops together traverse the entire string once
 *
 * Space: O(1)
 * Only two integer variables (i, end) are used regardless of input size.
 *
 */
class Solution {
    fun lengthOfLastWord(s: String): Int {

        var i = s.length - 1

        while (i >= 0 && s[i] == ' ') {
            i--
        }

        val end = i

        while (i >= 0 && s[i] != ' ') {
            i--
        }

        return end - i
    }
}