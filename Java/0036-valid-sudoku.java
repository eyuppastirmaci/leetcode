// Time  : O(1) Since the board size is fixed at 9x9, there are always exactly 81 cells to process
// Space : O(1) Given that there are exactly 27 sets in total, each containing at most 9 elements...
class Solution {
    public boolean isValidSudoku(char[][] board) {

        Set<Character>[] rowSets = new HashSet[9];
        Set<Character>[] colSets = new HashSet[9];
        Set<Character>[] boxSets = new HashSet[9];

        for (int k = 0; k < 9; k++) {
            rowSets[k] = new HashSet<>();
            colSets[k] = new HashSet<>();
            boxSets[k] = new HashSet<>();
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                char cell = board[i][j];
                if (cell == '.') continue;

                int boxIndex = (i / 3) * 3 + (j / 3);

                if (!rowSets[i].add(cell)) return false;
                if (!colSets[j].add(cell)) return false;
                if (!boxSets[boxIndex].add(cell)) return false;
            }
        }

        return true;
    }
}