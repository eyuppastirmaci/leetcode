/**
 * Time: we iterate through the string using a single for loop each character is added to the window at most once and
 * the windowStart pointer can advance at most n times in total therefore the time complexity increases linearly with
 * the length of the input: O(n).
 *
 * Space: we use a fixed number of variables. we also use an IntArray(26) to store character frequencies. since the
 * size of this array does not change based on the input length the extra memory usage is constant: O(1).
 *
 * why don't we ever decrease maxFrequency?
 * on each iteration it either stays the same size and slides right or grows by one.
 * a stale maxFrequency can't produce a wrong answer; maxLength only grows when the true maxFrequency grows, since
 * that's the only way the window itself can grow.
 * recomputing the true max on every loop would cost an extra O(26) without changing the result.
 */
class Solution {
    fun characterReplacement(s: String, k: Int): Int {

        var windowStart = 0
        var maxFrequency = 0
        var maxLength = 0

        val charCounts = IntArray(26)

        for (windowEnd in s.indices) {

            val endCharIndex = s[windowEnd] - 'A'

            charCounts[endCharIndex]++

            maxFrequency = maxOf(maxFrequency, charCounts[endCharIndex])

            if (windowEnd - windowStart + 1 - maxFrequency > k) {
                val startCharIndex = s[windowStart] - 'A'
                charCounts[startCharIndex]--
                windowStart++
            }

            maxLength = maxOf(maxLength, windowEnd - windowStart + 1)
        }

        return maxLength
    }
}