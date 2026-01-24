/**
 * Knuth-Morris-Pratt Algorithm
 *
 * Time: O(N + M)
 * The haystack is traversed in a single pass after preprocessing the needle in O(M).
 *
 * Space: O(M)
 * An auxiliary array of size M is allocated to store the LPS values.
 *
 */
class Solution {
    fun strStr(haystack: String, needle: String): Int {
        if (needle.isEmpty()) return 0
        if (needle.length > haystack.length) return -1

        val lps = needle.lps()

        var i = 0
        var j = 0

        while (i < haystack.length) {
            when {
                haystack[i] == needle[j] -> {
                    i++
                    j++
                    if (j == needle.length) return i - j
                }

                j > 0 -> {
                    j = lps[j - 1]
                }

                else -> {
                    i++
                }
            }
        }

        return -1
    }

    private fun String.lps(): IntArray = IntArray(length).also { lps ->
        var len = 0

        for (i in 1 until length) {

            while (len > 0 && this[i] != this[len]) {
                len = lps[len - 1]
            }

            if (this[i] == this[len]) {
                lps[i] = ++len
            }

        }
    }
}