/**
 * Recursive DFS Pre-Order
 *
 * Each recursive call creates a new stack frame because Java does not
 * perform tail-call optimization.
 *
 * This is DFS because the first recursive call explores one subtree
 * completely before the second subtree is visited.
 *
 * It is pre-order because each node is processed before its children.
 *
 * Time: O(n)
 * Space: O(h)
 */
class Solution {
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }

        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;

        invertTree(root.left);
        invertTree(root.right);

        return root;
    }
}

/**
 * Iterative DFS Pre-Order
 *
 * Uses an explicit stack instead of recursion.
 * The current node is processed before its children.
 * The right child is pushed first so the left child is popped first.
 *
 * Time: O(n)
 * Space: O(h)
 */
class Solution {
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }

        ArrayDeque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode current = stack.pop();

            TreeNode temp = current.left;
            current.left = current.right;
            current.right = temp;

            if (current.right != null) {
                stack.push(current.right);
            }

            if (current.left != null) {
                stack.push(current.left);
            }
        }

        return root;
    }
}


/**
 * Iterative BFS
 *
 * Uses a FIFO queue to visit nodes level by level.
 * Each node is processed before its children are visited.
 *
 * After swapping the children, the new right child is enqueued first,
 * so it is processed before the new left child at the same level.
 *
 * Time: O(n)
 * Space: O(w)
 */
class Solution {
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }

        ArrayDeque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();

            TreeNode temp = current.left;
            current.left = current.right;
            current.right = temp;

            if (current.right != null) {
                queue.offer(current.right);
            }

            if (current.left != null) {
                queue.offer(current.left);
            }
        }

        return root;
    }
}