/**
 * Backtracking
 *
 * n: board size and the number of queens
 * s: number of valid placements
 *
 * Time: O(n^2 + n * n! + s * n)
 *       preparing n row strings takes O(n^2)
 *       the search has O(n!) recursive calls as an upper bound
 *       each non-base call checks n columns
 *       hash set operations take O(1) on average
 *       copying n string references per valid board takes O(n) totaling O(s * n)
 *
 * Space: O(n^2 + s * n)
 *        O(n^2) auxiliary space for the prepared row strings
 *        the board and sets and recursion stack use O(n) additional space
 *        storing s valid boards takes O(s * n) space for string references
 */
class Solution {
    public List<List<String>> solveNQueens(int n) {
        // prepare row strings
        String[] rowPatterns = new String[n];
        char[] pattern = new char[n];
        Arrays.fill(pattern, '.');

        for (int column = 0; column < n; column++) {
            pattern[column] = 'Q';
            rowPatterns[column] = new String(pattern);
            pattern[column] = '.';
        }

        // Shared State
        // board
        String[] board = new String[n];

        // occupied columns and diagonals
        Set<Integer> columns = new HashSet<>(); // c
        Set<Integer> negativeDiagonals = new HashSet<>(); // r - c
        Set<Integer> positiveDiagonals = new HashSet<>(); // r + c

        List<List<String>> validPlacements = new ArrayList<>();

        backtrack(0, board, rowPatterns, columns, negativeDiagonals, positiveDiagonals, validPlacements);

        return validPlacements;
    }

    private void backtrack(
        int row,
        String[] board,
        String[] rowPatterns,
        Set<Integer> columns,
        Set<Integer> negativeDiagonals,
        Set<Integer> positiveDiagonals,
        List<List<String>> validPlacements
    ) {
        // base case
        if (row == board.length) {
            validPlacements.add(new ArrayList<>(Arrays.asList(board)));
            return;
        }

        for (int column = 0; column < board.length; column++) {
            // skip if the column or either diagonal is occupied
            if (columns.contains(column)
                || negativeDiagonals.contains(row - column)
                || positiveDiagonals.contains(row + column)
            ) {
                continue;
            }

            // add state
            board[row] = rowPatterns[column];
            columns.add(column);
            negativeDiagonals.add(row - column);
            positiveDiagonals.add(row + column);

            // next
            backtrack(
                row + 1,
                board,
                rowPatterns,
                columns,
                negativeDiagonals,
                positiveDiagonals,
                validPlacements
            );

            // undo state
            board[row] = null;
            columns.remove(column);
            negativeDiagonals.remove(row - column);
            positiveDiagonals.remove(row + column);
        }
    }
}