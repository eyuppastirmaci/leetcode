/**
 * Time: we visit each token exactly once and every ArrayDeque push/pop touches only the last element with a constant
 * number of field accesses, so the algorithm runs in amortized O(n) where n is the number of tokens.
 *
 * Space: in the worst case the input is all operands before any operator, so the stack can hold up to n / 2 + 1
 * Int values, so memory grows linearly with input size giving O(n).
 */
class Solution {
    fun evalRPN(tokens: Array<String>): Int {
        val stack = ArrayDeque<Int>()

        for (token in tokens) {
            val number = token.toIntOrNull()

            if (number != null) {
                stack.addLast(number)
            } else {
                val right = stack.removeLast()
                val left = stack.removeLast()

                val result = when (token) {
                    "+" -> left + right
                    "-" -> left - right
                    "*" -> left * right
                    "/" -> left / right
                    else -> error("Invalid operator")
                }

                stack.addLast(result)
            }
        }

        return stack.last()
    }
}