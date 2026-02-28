/**
 * Time: O(n)
 * Worst case, carry propagates through all n digits.
 * Best case, only the last digit is incremented in O(1).
 *
 * Space: O(1)
 * The input array is modified in-place. Only O(1) extra space is used unless all digits overflow, in which case a new array of size n+1 is created.
 *
 */
class Solution {
    fun plusOne(digits: IntArray): IntArray {

        carry(digits, digits.lastIndex)

        if (digits[0] == 0) {
            return intArrayOf(1) + digits
        } else {
            return digits
        }

    }

    private tailrec fun carry(digits: IntArray, i: Int) {
        if (i < 0) {
            return
        }

        digits[i] = (digits[i] + 1) % 10

        if (digits[i] != 0) {
            return
        }

        carry(digits, i - 1)
    }
}

/**
 * Time: O(n)
 * Worst case, Carry propagates through all n digits when all are 9
 * Best case, Only the last digit is incremented and returned immediately in O(1).
 *
 * Space: O(1)
 * The input array is modified in-place. Only O(1) extra space is used. If all digits overflow, a new array of size n+1 is created for the result.
 *
 */
class Solution {
    fun plusOne(digits: IntArray): IntArray {

        for (i in digits.lastIndex downTo 0) {

            if (digits[i] < 9) {
                digits[i]++
                return digits
            }

            digits[i] = 0
        }

        val res = IntArray(digits.size + 1)
        res[0] = 1
        return res
    }
}