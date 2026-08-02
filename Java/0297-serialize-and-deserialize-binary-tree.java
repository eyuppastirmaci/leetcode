/**
 * Recursive DFS Pre-Order
 *
 * A flat list of values cannot be turned back into a tree, because
 * nothing in it says whether a node has children: a 1 with a left child
 * of 2 and a 1 with a right child of 2 both flatten to "1,2". The
 * format has to carry that missing structure, so every absent child is
 * written out as an explicit marker instead of being skipped.
 *
 * With the markers present a single preorder pass is enough. The
 * problem places no restriction on the format, so tokens are joined by
 * commas and null is written as N, which cannot collide with a value
 * since values contain only digits and a minus sign.
 *
 * serialize threads one StringJoiner through the recursion as a
 * parameter. deserialize splits the string and pours the tokens into a
 * queue, so the queue itself is the cursor: every call polls exactly
 * one token and the recursion advances it in the order serialize wrote
 * it. An n node tree yields n values and n + 1 markers, and the rebuild
 * makes exactly 2n + 1 calls, so the tokens run out precisely when the
 * tree is finished and no bounds check is needed.
 *
 * Preorder is what makes the two halves mirror each other. The root is
 * written before its children, so on the way back the node exists
 * before its children are read and every written line has a direct
 * counterpart: writing a marker for null becomes returning null for a
 * marker, and descending into both children becomes assigning both
 * children. That symmetry is what makes the round trip verifiable by
 * reading the two methods side by side.
 *
 * The empty tree needs no special case, since serialize(null) hits the
 * base case and yields "N", which deserialize reads back as null.
 *
 * The cost of the choice is recursion depth, which follows the height
 * and nests one frame per node on a fully skewed tree, up to 10^4 here.
 * The output also does not match the level order format LeetCode prints
 * in the examples, so it cannot be eyeballed against them.
 *
 * A level order codec answers both: a queue drives serialize and
 * deserialize iteratively, removing the depth entirely and producing
 * LeetCode's own format. It pays for that by breaking the symmetry, as
 * the two halves no longer resemble each other and the rebuild has to
 * track which parent is still waiting for which child.
 *
 * Preorder paired with inorder, the reconstruction used in problem 105,
 * is not an option here. Locating the root inside inorder requires
 * distinct values, and 10^4 nodes drawn from only 2001 possible values
 * make duplicates unavoidable.
 *
 * Time: O(n) in both directions, since every node is visited once.
 * Space: O(n) for the string and the token queue, plus O(h) for the
 * recursion.
 *
 * Thread safe: the accumulator is a parameter rather than a field, so
 * every call owns its state and concurrent calls on the same Codec
 * cannot interfere.
 */

import java.util.StringJoiner;

public class Codec {

    private static final String SEPARATOR = ",";
    private static final String NULL_MARKER = "N";

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringJoiner joiner = new StringJoiner(SEPARATOR);

        appendPreorder(root, joiner);

        return joiner.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        Deque<String> tokens = new ArrayDeque<>(Arrays.asList(data.split(SEPARATOR)));

        return buildPreorder(tokens);
    }

    // Writes the subtree in preorder, emitting NULL_MARKER for every
    // absent child so the flattened form keeps the tree's shape.
    private void appendPreorder(TreeNode node, StringJoiner joiner) {
        if (node == null) {
            joiner.add(NULL_MARKER);
            return;
        }

        joiner.add(String.valueOf(node.val));

        appendPreorder(node.left, joiner);
        appendPreorder(node.right, joiner);
    }

    // Rebuilds the subtree from the same preorder stream, consuming
    // exactly one token per call.
    private TreeNode buildPreorder(Deque<String> tokens) {
        String token = tokens.poll();

        if (token.equals(NULL_MARKER)) {
            return null;
        }

        TreeNode node = new TreeNode(Integer.parseInt(token));

        node.left = buildPreorder(tokens);
        node.right = buildPreorder(tokens);

        return node;
    }
}
