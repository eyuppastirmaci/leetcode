/**
 * Binary Exponential Division
 *
 * Time: O(log N)
 * Scans from the highest bit (31) down to 0 in a single loop.
 *
 * Space: O(1)
 * Uses only a few integer variables for calculation.
 */
class Solution {
    fun divide(dividend: Int, divisor: Int): Int {

        if (dividend == Int.MIN_VALUE && divisor == -1) {
            return Int.MAX_VALUE
        }

        val isNegative = (dividend < 0) xor (divisor < 0)

        var negativeDividend = if (dividend > 0) -dividend else dividend
        val negativeDivisor = if (divisor > 0) -divisor else divisor

        val HALF_INT_MIN = Int.MIN_VALUE shr 1
        var maxShift = 0
        var shifted = negativeDivisor
        while (shifted >= HALF_INT_MIN && shifted shl 1 >= negativeDividend) {
            shifted = shifted shl 1
            maxShift++
        }

        var quotient = 0
        for (bit in maxShift downTo 0) {
            val currentDivisor = negativeDivisor shl bit
            if (negativeDividend <= currentDivisor) {
                negativeDividend -= currentDivisor
                quotient += (-1 shl bit)
            }
        }

        return if (isNegative) quotient else -quotient
    }
}