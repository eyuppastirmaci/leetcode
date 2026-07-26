/**
 * Iterative BST Traversal
 *
 * In a general binary tree, we could find and store the paths from the
 * root to p and q, then compare those paths to find their lowest common
 * ancestor.
 *
 * Since this is a binary search tree, its ordering property allows us
 * to determine whether both target nodes are in the left or right subtree
 * of the current node.
 *
 * If both targets are smaller than the current node, their lowest common
 * ancestor must be somewhere in the left subtree. If both are larger, it
 * must be somewhere in the right subtree.
 *
 * Otherwise, the targets are on different sides of the current node, or
 * the current node is equal to one of them. In either case, the current
 * node is their lowest common ancestor.
 *
 * Time: O(h), which is O(log n) for a balanced BST and O(n) for a
 * completely skewed BST.
 * Space: O(1)
 */
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode current = root;

        while (current != null) {
            if (current.val > p.val && current.val > q.val) {
                current = current.left;
            } else if (current.val < p.val && current.val < q.val) {
                current = current.right;
            } else {
                return current;
            }
        }

        return null;
    }
}