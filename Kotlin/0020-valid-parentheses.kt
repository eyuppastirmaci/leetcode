import java.util.ArrayDeque

/**
 * Stack
 * Time Complexity: O(n) - Single pass through the string with amortized O(1) push/pop operations.
 * Space Complexity: O(n) - Worst case consists of all open brackets, causing the stack size to reach n.
 */
class Solution {
    fun isValid(s: String): Boolean {
        val stack = ArrayDeque<Char>() // preferred over Stack to avoid synchronization overhead.

        for (char in s) {
            when (char) {
                '(', '{', '[' -> stack.push(char)
                ')' -> if (stack.isEmpty() || stack.pop() != '(') return false
                '}' -> if (stack.isEmpty() || stack.pop() != '{') return false
                ']' -> if (stack.isEmpty() || stack.pop() != '[') return false
            }
        }
        return stack.isEmpty()
    }
}