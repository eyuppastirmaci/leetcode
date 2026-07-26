/**
 * Iterative BFS
 *
 * BFS is used because the tree must be processed level by level.
 * A queue preserves the required FIFO order and avoids using the
 * recursive call stack.
 *
 * At the start of each iteration, levelSize stores the number of nodes
 * in the current level. Since nodes are processed from left to right,
 * the node at index levelSize - 1 is the rightmost node of that level
 * and is therefore visible from the right side.
 *
 * Time: O(n)
 * Space: O(w)
 */
class Solution {
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();

        if (root == null) {
            return result;
        }

        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();

            for (int i = 0; i < levelSize; i++) {
                TreeNode currentNode = queue.poll();

                if (i == levelSize - 1) {
                    result.add(currentNode.val);
                }

                if (currentNode.left != null) {
                    queue.offer(currentNode.left);
                }

                if (currentNode.right != null) {
                    queue.offer(currentNode.right);
                }
            }
        }

        return result;
    }
}
