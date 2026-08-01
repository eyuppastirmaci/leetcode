/**
 * Iterative DFS
 *
 * NodeState pairs a node with the open interval left by its ancestors,
 * so the stack carries the bounds each node is checked against.
 *
 * Every step down narrows that interval, a left child capped by its
 * parent's value and a right child floored by it, so a node falling
 * inside its own interval is already ordered against every ancestor and
 * a local parent-child check is never enough on its own.
 *
 * minAllowed and maxAllowed are longs, since a node value can be
 * Integer.MIN_VALUE or Integer.MAX_VALUE and the bounds have to reach
 * outside the int range to keep the strict comparisons safe.
 *
 * Time: O(n), since every node is pushed and popped exactly once.
 * Space: O(h), since the stack only holds the current path and its
 * pending siblings.
 */
class Solution {
    public boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        }

        Deque<NodeState> stack = new ArrayDeque<>();
        stack.push(new NodeState(root, Long.MIN_VALUE, Long.MAX_VALUE));

        while (!stack.isEmpty()) {
            NodeState currentState = stack.pop();

            TreeNode currentNode = currentState.node();
            long minAllowed = currentState.minAllowed();
            long maxAllowed = currentState.maxAllowed();

            if (currentNode.val <= minAllowed || currentNode.val >= maxAllowed) {
                return false;
            }

            if (currentNode.left != null) {
                stack.push(new NodeState(currentNode.left, minAllowed, currentNode.val));
            }

            if (currentNode.right != null) {
                stack.push(new NodeState(currentNode.right, currentNode.val, maxAllowed));
            }
        }

        return true;
    }

    private record NodeState(TreeNode node, long minAllowed, long maxAllowed) {}
}
