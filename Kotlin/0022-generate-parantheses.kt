/**
 * Backtracking - StringBuilder
 *
 * Time Complexity: O(Cn * n) where Cn is the n-th Catalan number.
 * Space Complexity (excluding output): O(n) recursion depth + O(n) for the current builder => O(n).
 * Space Complexity (including output): O(Cn * n).
 *
 * See also:
 * https://usaco.guide/adv/catalan
 */
class Solution {
    fun generateParenthesis(n: Int): List<String> {
        val result = mutableListOf<String>()
        val sb = StringBuilder(2 * n)

        backtrack(result, sb, open = 0, close = 0, n = n)
        return result
    }

    private fun backtrack(
        result: MutableList<String>,
        sb: StringBuilder,
        open: Int,
        close: Int,
        n: Int
    ) {
        // Valid combination found
        if (sb.length == 2 * n) {
            result.add(sb.toString())
            return
        }

        // Add open parenthesis if we still can
        if (open < n) {
            sb.append('(')
            backtrack(result, sb, open + 1, close, n)
            sb.deleteCharAt(sb.length - 1) // undo
        }

        // Add close parenthesis if it won't break validity
        if (close < open) {
            sb.append(')')
            backtrack(result, sb, open, close + 1, n)
            sb.deleteCharAt(sb.length - 1) // undo
        }
    }
}

/**
 * Backtracking - CharArray
 *
 * Time Complexity: O(Cn * n) where Cn is the n-th Catalan number.
 * Space Complexity (excluding output): O(n) recursion depth + O(n) for the path array => O(n).
 * Space Complexity (including output): O(Cn * n).
 *
 * See also:
 * https://usaco.guide/adv/catalan
 */
class Solution {
    fun generateParenthesis(n: Int): List<String> {
        val result = mutableListOf<String>()
        val path = CharArray(2 * n)

        backtrack(result, path, idx = 0, open = 0, close = 0, n = n)
        return result
    }

    private fun backtrack(
        result: MutableList<String>,
        path: CharArray,
        idx: Int,
        open: Int,
        close: Int,
        n: Int
    ) {
        if (idx == 2 * n) {
            result.add(String(path))
            return
        }

        if (open < n) {
            path[idx] = '('
            backtrack(result, path, idx + 1, open + 1, close, n)
        }

        if (close < open) {
            path[idx] = ')'
            backtrack(result, path, idx + 1, open, close + 1, n)
        }
    }
}
