/**
 * Iterative DFS
 *
 * NodeState pairs a node with the path maximum inherited from its
 * parent, so the stack carries the value each node is compared against.
 *
 * Every node travels down with the largest value seen so far on its
 * path, and a node is good exactly when nothing on that path exceeds
 * it, so the single comparison against that carried maximum settles the
 * node without revisiting any ancestor.
 *
 * Time: O(n), since every node is pushed and popped exactly once.
 * Space: O(h), since the stack only holds the current path and its
 * pending siblings.
 */
class Solution {
    public int goodNodes(TreeNode root) {
        int count = 0;

        if (root == null) {
            return count;
        }

        Deque<NodeState> stack = new ArrayDeque<>();
        stack.push(new NodeState(root, root.val));

        while (!stack.isEmpty()) {
            NodeState nodeState = stack.pop();

            TreeNode node = nodeState.node();
            int pathMax = nodeState.maxValue();

            if (node.val >= pathMax) {
                count++;
            }

            int childMaxValue = Math.max(node.val, pathMax);

            if (node.left != null) {
                stack.push(new NodeState(node.left, childMaxValue));
            }

            if (node.right != null) {
                stack.push(new NodeState(node.right, childMaxValue));
            }
        }

        return count;
    }

    private record NodeState(TreeNode node, int maxValue) {}
}
