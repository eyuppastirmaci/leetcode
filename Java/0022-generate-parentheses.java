/**
 * Backtracking - Catalan numbers
 *
 * Time: O(4^n / sqrt(n))
 *
 * Space: O(n) auxiliary, O(4^n / sqrt(n)) including the output
 */
class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        char[] sharedState = new char[2 * n];

        backtrack(n, sharedState, result, 0, 0);

        return result;
    }

    private void backtrack(
        int n,
        char[] sharedState,
        List<String> result,
        int open,
        int close
    ) {
        int currentLength = open + close;

        if (currentLength == 2 * n) {
            result.add(new String(sharedState));

            return;
        }

        // there can be at most n opening parentheses
        if (open < n) {
            sharedState[currentLength] = '(';
            backtrack(n, sharedState, result, open + 1, close);
        }

        // a closing parenthesis can be added only if an unmatched opening parenthesis exists
        if (open > close) {
            sharedState[currentLength] = ')';
            backtrack(n, sharedState, result, open, close + 1);
        }
    }
}
