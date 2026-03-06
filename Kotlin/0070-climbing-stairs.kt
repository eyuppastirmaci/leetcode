/**
 * Dynamic Programming
 *
 * Time: O(n)
 * Each number of ways is calculated once by summing the previous two states.
 *
 * Space: O(1)
 * Only the last two computed values are stored.
 */
class Solution {
    fun climbStairs(n: Int): Int {
        if (n == 1) return 1
        if (n == 2) return 2

        var prev = 1
        var cur = 2

        for (i in 2 until n) {
            val temp = cur
            cur = prev + cur
            prev = temp
        }

        return cur
    }
}