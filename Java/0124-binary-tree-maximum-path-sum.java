/**
 * Recursive DFS Post-Order
 *
 * A path may not repeat a node, so it can bend at most once: it climbs
 * from one end up to the highest node on the path and then descends to
 * the other end. That highest node is the only one allowed to use both
 * of its children.
 *
 * Each call returns the best sum reachable by starting at the node and
 * descending into at most one branch, which is the only shape a parent
 * can extend without branching. A branch returning a negative gain is
 * clamped to 0, because skipping a branch is never worse than taking a
 * losing one, and that clamp also covers the single-node path for free.
 *
 * The answer travels on a separate channel. At every node the bent path
 * node.val + left + right is compared against maxSum, but it is never
 * returned upward, since a parent extending it would force a third
 * direction through the node and repeat it.
 *
 * maxSum starts at Integer.MIN_VALUE rather than 0, because every value
 * may be negative, in which case the answer is the largest single node.
 *
 * It is post-order because a node is processed only after both of its
 * children have returned their gains.
 *
 * Time: O(n), since every node is visited exactly once.
 * Space: O(h) for the call stack, which degrades to O(n) on a skewed
 * tree.
 *
 * Not thread safe: maxSum is mutable instance state shared by the whole
 * traversal, so concurrent calls on the same Solution instance would
 * overwrite each other's results. Resetting it at the start of
 * maxPathSum makes sequential reuse safe, not concurrent use.
 *
 * StackOverflowError risk: the constraints allow up to 3 * 10^4 nodes,
 * and a fully skewed tree makes the recursion depth equal to the node
 * count, so this can nest 30,000 frames. An iterative post-order
 * traversal with an explicit stack moves that depth onto the heap and
 * removes the risk.
 */

class Solution {

    private int maxSum;

    public int maxPathSum(TreeNode root) {
        maxSum = Integer.MIN_VALUE;

        maxGain(root);

        return maxSum;
    }

    // Returns the best sum reachable from this node going down at most
    // one branch, and records in maxSum the best path that bends here.
    private int maxGain(TreeNode node) {
        if (node == null) {
            return 0;
        }

        int left = Math.max(maxGain(node.left), 0);
        int right = Math.max(maxGain(node.right), 0);

        int sum = node.val + left + right;

        maxSum = Math.max(sum, maxSum);

        int maxBranch = Math.max(left, right);

        return node.val + maxBranch;
    }
}
