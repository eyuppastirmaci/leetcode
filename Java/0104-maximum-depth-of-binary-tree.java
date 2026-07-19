/**
 * Iterative DFS
 *
 * Uses an explicit LIFO stack instead of the recursive call stack.
 * Each stack entry stores a node together with its depth from the root.
 *
 * When a node is popped, its depth is compared with the maximum depth
 * seen so far. Each non-null child is then pushed with depth + 1.
 *
 * Since the left child is pushed before the right child, the right
 * subtree is explored first due to the stack's LIFO behavior.
 *
 * Time: O(n)
 * Space: O(h)
 */
class Solution {
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Deque<NodeDepth> stack = new ArrayDeque<>();
        stack.push(new NodeDepth(root, 1));

        int maxDepth = 0;

        while (!stack.isEmpty()) {
            NodeDepth nodeDepth = stack.pop();
            TreeNode node = nodeDepth.node();
            int depth = nodeDepth.depth();

            maxDepth = Math.max(maxDepth, depth);

            int childDepth = depth + 1;

            if (node.left != null) {
                stack.push(new NodeDepth(node.left, childDepth));
            }

            if (node.right != null) {
                stack.push(new NodeDepth(node.right, childDepth));
            }
        }

        return maxDepth;
    }

    private record NodeDepth(TreeNode node, int depth) {}
}