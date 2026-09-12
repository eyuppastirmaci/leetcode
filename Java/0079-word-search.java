/*
 *
 * Backtracking
 * 
 * m: number of rows
 * n: number of columns
 * L: word length
 *
 * Time complexity: O(m * n * 3^L)
 * Space complexity: O(L)
 * 
 */
class Solution {
    public boolean exist(char[][] board, String word) {
        if (word.length() > board.length * board[0].length) {
            return false;
        }

        int[] frequencies = new int[128];

        // count how many times each character appears on the board
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                frequencies[board[i][j]]++;
            }
        }

        if (!hasEnoughCharacters(word, frequencies)) {
            return false;
        }

        // start from the rarer end of the word to reduce the number of starting cells
        if (frequencies[word.charAt(0)] > frequencies[word.charAt(word.length() - 1)]) {
            word = new StringBuilder(word).reverse().toString();
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                char current = board[i][j];

                if (word.charAt(0) == current) {
                    boolean isFound = backtrack(i, j, word, board, 0);
                    if (isFound) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean backtrack(
        int i,
        int j,
        String word,
        char[][] board,
        int index
    ) {
        boolean isAboveTopBound = i < 0;
        boolean isBelowBottomBound = i >= board.length;
        boolean isOutsideLeftBound = j < 0;
        boolean isOutsideRightBound = j >= board[0].length;

        boolean isOutOfBounds = isAboveTopBound
                               || isBelowBottomBound
                               || isOutsideLeftBound
                               || isOutsideRightBound;

        if (isOutOfBounds) {
            return false;
        }

        // wrong way move on to the next one
        if (board[i][j] != word.charAt(index)) {
            return false;
        }

        // we found what we were looking for
        if (index == word.length() - 1) {
            return true;
        }

        char current = board[i][j];
        board[i][j] = '#';

        // matched but still have a way to go try the adjacents
        boolean isFound = backtrack(i, j + 1, word, board, index + 1) // R
                          || backtrack(i + 1, j, word, board, index + 1) // B
                          || backtrack(i, j - 1, word, board, index + 1) // L
                          || backtrack(i - 1, j, word, board, index + 1); // T

        board[i][j] = current;

        return isFound;
    }

    private boolean hasEnoughCharacters(String word, int[] frequencies) {
        int[] requiredFrequencies = new int[128];

        for (int i = 0; i < word.length(); i++) {
            char current = word.charAt(i);
            requiredFrequencies[current]++;

            if (requiredFrequencies[current] > frequencies[current]) {
                return false;
            }
        }

        return true;
    }
}