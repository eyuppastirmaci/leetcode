/**
 *  Iterative DFS
 *
 *  Time: O(k), where k is the number of compared node pairs until the first mismatch.
 *        Worst case: O(n), when both trees are identical and all n corresponding nodes are compared.
 *
 *  Space: O(h) in the worst case, where h is the height of the tree.
 *         Balanced tree          : O(log n), because each level can leave one sibling branch pending.
 *         Completely skewed tree : O(1), in this implementation, because only existing child pairs are pushed and the
 *                                  stack never accumulates sibling branches.
 */
class Solution {

    private record NodePair(TreeNode first, TreeNode second) {}

    public boolean isSameTree(TreeNode p, TreeNode q) {

        Deque<NodePair> stack = new ArrayDeque<>();
        stack.push(new NodePair(p, q));

        while (!stack.isEmpty()) {

            NodePair currentPair = stack.pop();

            TreeNode firstTreeNode = currentPair.first();
            TreeNode secondTreeNode = currentPair.second();

            if (firstTreeNode == null && secondTreeNode == null) continue;
            if (firstTreeNode == null || secondTreeNode == null) return false;
            if (firstTreeNode.val != secondTreeNode.val) return false;

            if (firstTreeNode.left != null || secondTreeNode.left != null) {
                stack.push(new NodePair(firstTreeNode.left, secondTreeNode.left));
            }

            if (firstTreeNode.right != null || secondTreeNode.right != null) {
                stack.push(new NodePair(firstTreeNode.right, secondTreeNode.right));
            }

        }

        return true;

    }
}

/**
 * Recursive DFS Pre-Order
 *
 * Traverses both trees simultaneously, comparing node pairs in
 * lockstep. Two trees are the same iff the current values match and
 * the left subtrees are the same and the right subtrees are the same.
 *
 * The base cases resolve structure: two nulls are the same, one null
 * means the shapes diverge. A value mismatch fails fast before
 * recursing.
 *
 * It is pre-order because each pair is compared before its children
 * are visited — no information flows bottom-up, so no post-order
 * bookkeeping is needed.
 *
 * The && operator short-circuits: if the left subtrees differ, the
 * right subtrees are never visited. This prunes only failing cases;
 * two identical trees still visit every pair.
 *
 * Time: O(n)
 * Space: O(h)
 */

class Solution {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }

        if (p == null && q != null || p != null && q == null) {
            return false;
        }

        if (p != null && q != null && p.val != q.val) {
            return false;
        }

        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
}