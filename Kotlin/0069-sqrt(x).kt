/**
 * Binary Search
 *
 * Time: O(log x)
 * Search space is halved in each iteration.
 *
 * Space: O(1)
 * Only a constant number of variables are used.
 */
class Solution {
    fun mySqrt(x: Int): Int {
        if (x < 2) return x

        var left = 1
        var right = x / 2
        var answer = 0

        while (left <= right) {
            val mid = left + (right - left) / 2

            if (mid <= x / mid) {
                answer = mid
                left = mid + 1
            } else {
                right = mid - 1
            }
        }

        return answer
    }
}