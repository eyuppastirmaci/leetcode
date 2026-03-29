/**
 * Recursive Traversal
 * Time: Each node is visited exactly once, so time complexity is O(n).
 *
 * Space:
 *   Auxiliary: The call stack holds at most the nodes along the path from the root to a leaf at any given time.
 *     - Balanced tree : O(log n) - tree height is log n
 *     - Skewed   tree : O(n)     - degenerate case, tree height equals n
 *     Therefore, auxiliary space complexity is O(h) where h is the height of the tree.
 *   Total: O(n) when including the output list.
 */
class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {

        List<Integer> result = new ArrayList<>();
        traverse(root, result);

        return result;

    }

    private void traverse(TreeNode node, List<Integer> result) {

        if (node == null) return;

        traverse(node.left, result);

        result.add(node.val);

        traverse(node.right, result);

    }
}

/**
 * Iterative Traversal
 * Time: Since each node in the tree is added to the stack at most once and removed from the stack at most once, and a
 *       fixed number of operations are performed on each node, the time complexity is O(n).
 *
 * Space:
 *   Auxiliary: The stack holds at most the nodes along the path from the root to a leaf at any given time.
 *     - Balanced tree : O(log n) - tree height is log n
 *     - Skewed   tree : O(n)     - degenerate case, tree height equals n
 *     Therefore, auxiliary space complexity is O(h) where h is the height of the tree.
 *   Total: O(n) when including the output list.
 *
 * The only difference is that in the recursive solution, space comes from the call stack, while in the iterative
 * solution it comes from an explicit stack. However, both hold at most h nodes at any given time, so the auxiliary
 * space complexity is the same.
 */
class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {

        Deque<TreeNode> stack = new ArrayDeque<>();
        List<Integer> result = new ArrayList<>();
        TreeNode current = root;

        while (current != null || !stack.isEmpty()) {

            while (current != null) {
                stack.push(current);
                current = current.left;
            }

            current = stack.pop();
            result.add(current.val);
            current = current.right;

        }

        return result;
    }
}

/**
 * Morris Traversal
 *
 * Time: O(n) - Each node is visited at most twice (thread creation + visit).
 *
 * Space:
 *   Auxiliary: O(1) - No stack or call stack is used; only a few pointer variables are temporarily modified
 *     to point to nodes in the tree, but they are eventually restored.
 *   Total: O(n) when including the output list.
 *
 * Trade-off: This approach temporarily mutates the tree structure by creating threads this makes it unsuitable for
 * read-only or concurrent scenarios where tree mutation is not acceptable.
 */
class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        TreeNode current = root;

        while (current != null) {

            if (current.left == null) {

                // No left child, add value, move right
                result.add(current.val);
                current = current.right;

            } else {

                // Find the inorder predecessor of the left subtree
                TreeNode predecessor = current.left;
                while (predecessor.right != null && predecessor.right != current) {
                    predecessor = predecessor.right;
                }

                if (predecessor.right == null) {

                    // Thread not yet established, create it and go left
                    predecessor.right = current;
                    current = current.left;

                } else {

                    // Thread already exists, clean up thread, add value
                    predecessor.right = null;
                    result.add(current.val);
                    current = current.right;
                }

            }
        }

        return result;
    }
}