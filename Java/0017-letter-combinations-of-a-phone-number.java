/**
 * Cartesian Product
 * 
 * n: digits length
 *
 * Time: O(n * 4^n)
 *       in the worst-case scenario 4^n combinations are generated since each combination is of length n and characters are copied when constructing the string
 *
 * Space: O(n * 4^n)
 *        4^n result strings each of length n characters are stored 
 *        storing intermediate combinations does not change this growth rate
 * 
 * Backtracking can be used if the goal is to generate the results without storing all intermediate combinations
 */
class Solution {
    private static final Map<Character, List<Character>> DIGIT_TO_LETTERS = Map.of(
        '2', List.of('a', 'b', 'c'),
        '3', List.of('d', 'e', 'f'),
        '4', List.of('g', 'h', 'i'),
        '5', List.of('j', 'k', 'l'),
        '6', List.of('m', 'n', 'o'),
        '7', List.of('p', 'q', 'r', 's'),
        '8', List.of('t', 'u', 'v'),
        '9', List.of('w', 'x', 'y', 'z')
    );

    public List<String> letterCombinations(String digits) {
        List<String> combinations = new ArrayList<>();
        combinations.add("");

        for (char digit : digits.toCharArray()) {
            List<Character> letters = DIGIT_TO_LETTERS.get(digit);
            List<String> nextCombinations = new ArrayList<>();

            for (int i = 0; i < combinations.size(); i++) {
                for (char letter : letters) {
                    nextCombinations.add(combinations.get(i) + letter);
                }
            }

            combinations = nextCombinations;
        }

        return combinations;
    }
}

/**
 * Backtracking
 *
 * n: digits length
 *
 * Time: O(n * 4^n)
 * 
 * Space: O(n * 4^n)
 *        O(n) auxiliary space.
 */
class Solution {
    private static final Map<Character, List<Character>> DIGIT_TO_LETTERS = Map.of(
        '2', List.of('a', 'b', 'c'),
        '3', List.of('d', 'e', 'f'),
        '4', List.of('g', 'h', 'i'),
        '5', List.of('j', 'k', 'l'),
        '6', List.of('m', 'n', 'o'),
        '7', List.of('p', 'q', 'r', 's'),
        '8', List.of('t', 'u', 'v'),
        '9', List.of('w', 'x', 'y', 'z')
    );

    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        StringBuilder sharedState = new StringBuilder();

        backtracking(0, digits, sharedState, result);

        return result;
    }

    private void backtracking(
        int index,
        String digits,
        StringBuilder sharedState,
        List<String> result
    ) {
        // all digits have been processed so the current combination is complete
        if (index == digits.length()) {
            result.add(sharedState.toString());
            return;
        }

        List<Character> letters = DIGIT_TO_LETTERS.get(digits.charAt(index));
        
        for (char letter : letters) {
            sharedState.append(letter);
            backtracking(index + 1, digits, sharedState, result);
            sharedState.deleteCharAt(sharedState.length() - 1);
        }
    }
}
