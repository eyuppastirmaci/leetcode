/**
 * the matrix as a whole can be treated as one large sorted array due to the fact that:
 * 1. all rows in the matrix are sorted in non-decreasing order
 * 2. each row's first element is greater than the previous row's last element
 * we perform a binary search on this large virtual array of size m * n by converting our virtual index back to the
 * proper row / column indices in the 2D matrix via mathematical operations (division and mod), and at each iteration
 * we compare the middle element of the matrix against the target value we are looking for and remove half of the
 * search space. this will allow us to optimally find our target value.
 *
 * Time O(log(m * n)): because we treat the matrix as a large virtual array of size m * n and because we are
 * cutting the search space in half every time we check against our target value, and therefore, just like a
 * binary search, mathematically we will need only log2(m * n) steps to locate our target.
 *
 * Space O(1): because we use only a constant amount of extra space.
 */
class Solution {
    fun searchMatrix(matrix: Array<IntArray>, target: Int): Boolean {

        val rowCount = matrix.size
        val colCount = matrix[0].size

        var left = 0
        var right = rowCount * colCount - 1

        while (left <= right) {

            val mid = left + (right - left) / 2
            /**
             * since each row's first element is greater than the previous row's last element, the matrix behaves like a
             * single sorted 1D array.
             * so we map the virtual index back to its corresponding row and column indices.
             * `mid / colCount` gives the row index.
             * `mid % colCount` gives the column index within that row.
             */
            val midValue = matrix[mid / colCount][mid % colCount]

            if (midValue == target) {
                return true
            } else if (midValue < target) {
                left = mid + 1
            } else {
                right = mid - 1
            }
        }

        return false
    }
}