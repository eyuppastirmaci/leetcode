/**
 * Brute Force
 * Time: O(n * numRows) fill grid O(n) + read grid O(numRows * cols) where cols equals n/2
 * Space: O(n * numRows) extra 2D grid of size numRows x cols
 */
class Solution {
    public String convert(String s, int numRows) {
        if (numRows == 1 || numRows >= s.length()) return s;

        int stringLength = s.length();
        int cycleLength = 2 * numRows - 2;
        int diagonalWidth = numRows - 1;
        int numberOfCycles = (stringLength + cycleLength - 1) / cycleLength;
        int totalColumns = numberOfCycles * diagonalWidth;

        char[][] zigzagGrid = new char[numRows][totalColumns];
        int charIndex = 0;
        int currentColumn = 0;

        while (charIndex < stringLength) {
            for (int row = 0; row < numRows && charIndex < stringLength; row++) {
                zigzagGrid[row][currentColumn] = s.charAt(charIndex++);
            }
            for (int row = numRows - 2; row >= 1 && charIndex < stringLength; row--) {
                zigzagGrid[row][++currentColumn] = s.charAt(charIndex++);
            }
            currentColumn++;
        }

        StringBuilder result = new StringBuilder(stringLength);
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < totalColumns; col++) {
                if (zigzagGrid[row][col] != '\0') {
                    result.append(zigzagGrid[row][col]);
                }
            }
        }

        return result.toString();
    }
}

/**
 * Row-wise Merge
 * Time: O(n) each character is visited and appended exactly once
 * Space: O(n) uses numRows StringBuilders and output string
 */
class Solution {
    public String convert(String s, int numRows) {
        if (numRows == 1 || numRows >= s.length()) return s;

        StringBuilder[] rowBuilders = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++) {
            rowBuilders[i] = new StringBuilder();
        }

        int currentRow = 0;
        int direction = 1;

        for (char character : s.toCharArray()) {
            rowBuilders[currentRow].append(character);

            if (currentRow == 0) {
                direction = 1;
            } else if (currentRow == numRows - 1) {
                direction = -1;
            }

            currentRow += direction;
        }

        StringBuilder result = new StringBuilder(s.length());
        for (StringBuilder rowContent : rowBuilders) {
            result.append(rowContent);
        }

        return result.toString();
    }
}

/**
 * Cycle Skip
 * Time: O(n) each character index is computed and appended once
 * Space: O(1) only output StringBuilder is used besides input
 */
class Solution {
    public String convert(String s, int numRows) {
        if (numRows == 1 || numRows >= s.length()) return s;

        int stringLength = s.length();
        int cycleLength = 2 * numRows - 2;
        StringBuilder result = new StringBuilder(stringLength);

        for (int currentRow = 0; currentRow < numRows; currentRow++) {
            int position = currentRow;
            int downStep = cycleLength - 2 * currentRow;
            int upStep = 2 * currentRow;
            boolean goingDown = true;

            while (position < stringLength) {
                result.append(s.charAt(position));

                if (currentRow == 0 || currentRow == numRows - 1) {
                    position += cycleLength;
                } else {
                    int nextStep = goingDown ? downStep : upStep;
                    position += nextStep;
                    goingDown = !goingDown;
                }
            }
        }

        return result.toString();
    }
}