/**
 * Recursive DFS Post-Order
 *
 * Computes the height of each subtree bottom-up. A node's height is
 * 1 + max(leftHeight, rightHeight).
 *
 * The diameter candidate at each node is leftHeight + rightHeight,
 * which is the number of edges on the longest path passing through
 * that node. The maximum candidate is accumulated in a class-level
 * field, since the diameter does not have to pass through the root.
 *
 * It is post-order because each node is processed after both of its
 * children have returned their heights.
 *
 * Time: O(n)
 * Space: O(h)
 */

class Solution {

    private int maxDiameter;

    public int diameterOfBinaryTree(TreeNode root) {
        maxDiameter = 0;
        getHeight(root);

        return maxDiameter;
    }

    private int getHeight(TreeNode node) {
        if (node == null) {
            return 0;
        }

        int heightLeft = getHeight(node.left);
        int heightRight = getHeight(node.right);
        int currentHeight = 1 + Math.max(heightLeft, heightRight);

        maxDiameter = Math.max(maxDiameter, heightLeft + heightRight);

        return currentHeight;
    }
}

/**
 * Iterative DFS Post-Order
 *
 * Uses an explicit stack instead of recursion. Each node is pushed
 * twice: first unvisited, so its children can be pushed on top of it,
 * and then visited, so it is processed only after both children are
 * done. This two-phase push is what produces the post-order sequence.
 *
 * A map from node to height replaces the recursive return channel:
 * when a visited node is popped, its children's heights are read from
 * the map (defaulting to 0 for null children, the base case), and its
 * own height is written back for its parent to read.
 *
 * The diameter candidate at each node is leftHeight + rightHeight,
 * accumulated in a local variable since everything lives in one method.
 *
 * Time: O(n)
 * Space: O(n) for the height map, plus O(h) for the stack
 */

class Solution {

    public int diameterOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int maxDiameter = 0;
        Deque<NodeState> stack = new ArrayDeque<>();
        Map<TreeNode, Integer> heights = new HashMap<>();

        stack.push(new NodeState(root, false));

        while (!stack.isEmpty()) {
            NodeState nodeState = stack.pop();
            TreeNode node = nodeState.node();
            boolean isVisited = nodeState.visited();

            if (isVisited) {
                int heightLeft = heights.getOrDefault(node.left, 0);
                int heightRight = heights.getOrDefault(node.right, 0);

                int currentHeight = 1 + Math.max(heightLeft, heightRight);
                heights.put(node, currentHeight);

                maxDiameter = Math.max(maxDiameter, heightLeft + heightRight);

                continue;
            }

            stack.push(new NodeState(node, true));

            if (node.left != null) {
                stack.push(new NodeState(node.left, false));
            }

            if (node.right != null) {
                stack.push(new NodeState(node.right, false));
            }
        }

        return maxDiameter;
    }

    private record NodeState(TreeNode node, boolean visited) {}
}