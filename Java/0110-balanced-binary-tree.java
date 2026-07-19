/**
 * Recursive DFS Post-Order with Sentinel
 *
 * Computes the height of each subtree bottom-up, reusing the height
 * channel to also carry the balance information: -1 is a sentinel
 * meaning "an imbalance was found below", since a real height can
 * never be negative.
 *
 * A node returns -1 if its left subtree reported -1, if its right
 * subtree reported -1, or if the height difference of its children
 * exceeds 1. Otherwise it returns its normal height,
 * 1 + max(leftHeight, rightHeight).
 *
 * Checking the left result before recursing into the right subtree
 * allows early pruning: once an imbalance is found, no further
 * subtrees are visited and the sentinel propagates straight to the
 * root. This does not change the worst case, since a balanced tree
 * still visits every node.
 *
 * Time: O(n)
 * Space: O(h)
 */
class Solution {
    public boolean isBalanced(TreeNode root) {
        return checkHeight(root) != -1;
    }

    private int checkHeight(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int heightLeft = checkHeight(root.left);

        if (heightLeft == -1) {
            return -1;
        }

        int heightRight = checkHeight(root.right);

        if (heightRight == -1 || Math.abs(heightLeft - heightRight) > 1) {
            return -1;
        }

        return 1 + Math.max(heightLeft, heightRight);
    }
}