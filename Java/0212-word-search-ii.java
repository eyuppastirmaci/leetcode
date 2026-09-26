/*
 *
 * Trie + Backtracking
 *
 * m: number of rows
 * n: number of columns
 * S: total number of characters in words
 * L: maximum word length
 * D: maximum search depth, min(L, m * n)
 * R: number of found words
 *
 * Time complexity: O(S + m * n * 4 * 3^(D - 1))
 *                  building the trie takes O(S)
 *                  each starting cell has at most 4 first moves, then 3
 *                  since the previous cell cannot be reused
 *                  pruning and the early exit reduce the search;
 *                  this is a worst-case upper bound
 *
 * Space complexity: O(S + D + R), including the result list
 *                   O(S) for the trie with a fixed alphabet of 26 characters
 *                   O(D) for the recursion stack and O(R) for result references
 *                   existing word strings are reused and the board is restored
 *
 */
class Solution {

    private static final int ALPHABET_SIZE = 26;
    private static final char VISITED = '#';

    private static class TrieNode {
        String word;
        int childCount;
        TrieNode[] children = new TrieNode[ALPHABET_SIZE];
    }

    private static class Trie {

        private final TrieNode root;

        public Trie() {
            this.root = new TrieNode();
        }

        public void insert(String word) {
            TrieNode current = root;

            for (int i = 0; i < word.length(); i++) {
                int index = word.charAt(i) - 'a';

                if (current.children[index] == null) {
                    current.children[index] = new TrieNode();
                    current.childCount++;
                }

                current = current.children[index];
            }

            current.word = word;
        }
    }

    public List<String> findWords(char[][] board, String[] words) {
        Trie trie = new Trie();

        for (String word : words) {
            trie.insert(word);
        }

        List<String> result = new ArrayList<>();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                // every word is found; nothing left to search
                if (trie.root.childCount == 0) {
                    return result;
                }

                backtrack(
                    i,
                    j,
                    board,
                    trie.root,
                    result
                );
            }
        }

        return result;
    }

    private void backtrack(
        int row,
        int col,
        char[][] board,
        TrieNode parentNode,
        List<String> result
    ) {
        boolean isAboveTopBound = row < 0;
        boolean isBelowBottomBound = row >= board.length;
        boolean isOutsideLeftBound = col < 0;
        boolean isOutsideRightBound = col >= board[0].length;

        boolean isOutOfBounds = isAboveTopBound
                               || isBelowBottomBound
                               || isOutsideLeftBound
                               || isOutsideRightBound;

        if (isOutOfBounds) {
            return;
        }

        // guards against '#' - 'a' producing a negative index below
        if (board[row][col] == VISITED) {
            return;
        }

        char current = board[row][col];
        int index = current - 'a';

        TrieNode currentNode = parentNode.children[index];

        // no word in the trie continues with this character
        if (currentNode == null) {
            return;
        }

        // found a complete word; add it once and clear it to avoid duplicates
        if (currentNode.word != null) {
            result.add(currentNode.word);
            currentNode.word = null;
        }

        // add state
        board[row][col] = VISITED;

        // matched current prefix; move to next
        backtrack(row, col + 1, board, currentNode, result); // R
        backtrack(row + 1, col, board, currentNode, result); // B
        backtrack(row, col - 1, board, currentNode, result); // L
        backtrack(row - 1, col, board, currentNode, result); // T

        // undo state
        board[row][col] = current;

        // no word remains under this node; prune
        if (currentNode.word == null && currentNode.childCount == 0) {
            parentNode.children[index] = null;
            parentNode.childCount--;
        }
    }
}
