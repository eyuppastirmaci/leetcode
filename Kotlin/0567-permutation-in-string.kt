/**
 * Time: we have to make one pass over s1 to fill s1CharCounts so O(n).
 * The first matches calculation is iterating over an array of 26 which is O (1).
 * The main sliding window makes one pass over s2 with O(1) work per iteration for O(m).
 * So the total time complexity is O(n+m).
 *
 * Space: We allocate two IntArrays of fixed size 26 to track character counts.
 * Since this is independent of the input size, the space complexity is O(1).
 */
class Solution {
    fun checkInclusion(s1: String, s2: String): Boolean {

        if (s1.length > s2.length) return false

        val s1CharCounts = IntArray(26)
        val s2CharCounts = IntArray(26)

        for (c in s1) {
            s1CharCounts[c - 'a']++
        }

        var matches = s1CharCounts.count { it == 0 }

        for (windowEnd in s2.indices) {
            val endCharIndex = s2[windowEnd] - 'a'

            s2CharCounts[endCharIndex]++

            if (s2CharCounts[endCharIndex] == s1CharCounts[endCharIndex]) {
                matches++
            } else if (s2CharCounts[endCharIndex] == s1CharCounts[endCharIndex] + 1) {
                matches--
            }

            if (windowEnd >= s1.length) {
                val startCharIndex = s2[windowEnd - s1.length] - 'a'

                s2CharCounts[startCharIndex]--

                if (s2CharCounts[startCharIndex] == s1CharCounts[startCharIndex]) {
                    matches++
                } else if (s2CharCounts[startCharIndex] == s1CharCounts[startCharIndex] - 1) {
                    matches--
                }
            }

            if (matches == 26) {
                return true
            }
        }

        return false
    }
}