/**
 * Iterative in-order traversal
 *
 * In-order visits a BST in ascending order, so the k-th node it reaches
 * is the k-th smallest without any sorting or extra collection.
 *
 * Every pop means one more node has been visited in that order, so the
 * counter drops by one and the traversal stops the moment it hits zero.
 *
 * Time: O(h + k), since the walk only descends the left spine before
 * popping k nodes.
 * Space: O(h), since the stack only holds the current path.
 */
class Solution {
    public int kthSmallest(TreeNode root, int k) {
        if (root == null) {
            throw new IllegalArgumentException("Root cannot be null");
        }

        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode currentNode = root;

        while (currentNode != null || !stack.isEmpty()) {
            while (currentNode != null) {
                stack.push(currentNode);
                currentNode = currentNode.left;
            }

            currentNode = stack.pop();

            k--;

            if (k == 0) {
                return currentNode.val;
            }

            currentNode = currentNode.right;
        }

        throw new IllegalStateException("k is out of range");
    }
}
