/**
 * Time: O(s.length + t.length)
 *
 * First, we iterate over the t string once to store the required character frequencies in the need array.
 * This operation takes O(t.length) time.
 *
 * Next, we process the s string using the sliding window approach.
 * The right pointer moves from the beginning to the end of s once.
 * The left pointer can move a total of at most s.length positions.
 *
 * Therefore, the operation on s can be considered O(2 * s.length)
 * in the worst case, but since constant factors are ignored in Big-O notation, it is accepted as O(s.length).
 *
 * Consequently, the total time complexity is O(s.length + t.length).
 *
 * Space Complexity: O(1)
 *
 * We use a fixed-size IntArray of length 128 to store character frequencies.
 * The size of this array does not increase based on the values of s.length or t.length provided as input.
 *
 * Therefore, the extra space complexity is O(1).
 *
 * Note: Since the returned substring is considered the output, it is generally not included in the space complexity
 * calculation.
 *
 * Note: Since the question specifies that "s" and "t" contain only uppercase and lowercase English letters,
 * the characters remain within the ASCII range. Therefore, using an IntArray(128) is safe if the input included a
 * broader character set for example, if Unicode characters were supported a fixed 128-element array would not be safe.
 * In that case, it would be more appropriate to use a dynamic structure like a HashMap<Char, Int> to store character
 * frequencies.
*/
class Solution {
    fun minWindow(s: String, t: String): String {
        if (t.length > s.length) return ""

        val need = IntArray(128)
        for (ch in t) {
            need[ch.code]++
        }

        var missing = t.length
        var minLen = Int.MAX_VALUE
        var minStart = 0

        var left = 0
        for (right in s.indices) {
            val rightChar = s[right]

            if (need[rightChar.code] > 0) {
                missing--
            }
            need[rightChar.code]--

            while (missing == 0) {
                if (right - left + 1 < minLen) {
                    minLen = right - left + 1
                    minStart = left
                }

                val leftChar = s[left]
                need[leftChar.code]++

                if (need[leftChar.code] > 0) {
                    missing++
                }

                left++
            }
        }

        return if (minLen == Int.MAX_VALUE) {
            ""
        } else {
            s.substring(minStart, minStart + minLen)
        }
    }
}