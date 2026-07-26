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
 * Compares both trees in lockstep: two trees are the same iff the
 * current values match and both subtrees are the same. Base cases
 * handle structure — two nulls match, one null means the shapes
 * diverge.
 *
 * Pre-order because a node's comparison needs nothing from its
 * subtrees; only the boolean results are combined on the way up.
 * The && short-circuits, so a mismatch stops the traversal early.
 *
 * Time: O(min(m, n)) — stops as soon as one side runs out of nodes.
 * Space: O(h) call stack — O(n) skewed, O(log n) balanced.
 */
class Solution {
    public boolean isSameTree(TreeNode p, TreeNode q) {
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