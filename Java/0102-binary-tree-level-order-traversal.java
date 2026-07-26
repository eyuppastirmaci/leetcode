/**
 * Iterative BFS
 *
 * BFS is used because the result must be produced level by level.
 * A queue preserves the required FIFO order, so an iterative approach
 * is more natural than recursion and avoids an unnecessary call stack.
 *
 * At the start of each iteration, levelSize stores the number of nodes
 * currently belonging to that level. The for loop processes exactly
 * those nodes, while their children are added to the queue for the
 * next level.
 *
 * Time: O(n)
 * Space: O(w)
 */
class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();

        if (root == null) {
            return result;
        }

        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> currentLevel = new ArrayList<>(levelSize);

            for (int i = 0; i < levelSize; i++) {
                TreeNode currentNode = queue.poll();

                currentLevel.add(currentNode.val);

                if (currentNode.left != null) {
                    queue.offer(currentNode.left);
                }

                if (currentNode.right != null) {
                    queue.offer(currentNode.right);
                }
            }

            result.add(currentLevel);
        }

        return result;
    }
}