/**
 * Recursive DFS
 *
 * Traverses the root tree looking for a node whose value matches the
 * value of subRoot's root. Such a node is a possible subtree candidate,
 * so isSameTree is used to compare both trees structurally and by value.
 *
 * If the candidate does not match, the search continues recursively in
 * the left and right subtrees.
 *
 * In the worst case, isSameTree may compare up to m nodes for each of
 * the n nodes in root.
 *
 * Time: O(n * m)
 * Space: O(h + k)
 * 
 * Alternative approaches include serialization with KMP and Merkle hashing.
 */
class Solution {
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if (root == null) {
            return false;
        }

        if (root.val == subRoot.val && isSameTree(root, subRoot)) {
            return true;
        }

        return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
    }

    private boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }

        if (p == null || q == null) {
            return false;
        }

        return p.val == q.val
            && isSameTree(p.left, q.left)
            && isSameTree(p.right, q.right);
    }
}