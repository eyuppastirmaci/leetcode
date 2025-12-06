/**
 * Iterative Cartesian Product
 * Time Complexity: O(4^n * n) where n is the length of digits; generates all combinations iteratively.
 * Space Complexity: O(4^n * n) intermediate lists are created at each fold step.
 */
class Solution {
    fun letterCombinations(digits: String): List<String> {
        if (digits.isEmpty()) return emptyList()

        val digitLettersMap = mapOf(
            '2' to "abc",
            '3' to "def",
            '4' to "ghi",
            '5' to "jkl",
            '6' to "mno",
            '7' to "pqrs",
            '8' to "tuv",
            '9' to "wxyz"
        )

        val lettersList = digits.map { digit ->
            digitLettersMap[digit] ?: ""
        }

        return lettersList.fold(listOf("")) { acc, letters ->
            acc.flatMap { prefix ->
                letters.map { char -> prefix + char }
            }
        }
    }
}

/**
 * Backtracking (DFS)
 * Time Complexity: O(4^n * n) explores all possible letter combinations via recursive tree traversal.
 * Space Complexity: O(n) recursion stack depth equals the number of digits
 */
class Solution {
    private val digitMap = arrayOf(
        "", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"
    )

    fun letterCombinations(digits: String): List<String> {
        if (digits.isEmpty()) return emptyList()

        val result = ArrayList<String>()
        backtrack(digits, 0, StringBuilder(), result)
        return result
    }

    private fun backtrack(
        digits: String,
        index: Int,
        currentStr: StringBuilder,
        result: MutableList<String>
    ) {
        if (index == digits.length) {
            result.add(currentStr.toString())
            return
        }

        val digit = digits[index] - '0' // micro-optimization over .digitToInt()
        val letters = digitMap[digit]

        for (char in letters) {
            currentStr.append(char)
            backtrack(digits, index + 1, currentStr, result)
            currentStr.deleteCharAt(currentStr.length - 1)
        }
    }
}

