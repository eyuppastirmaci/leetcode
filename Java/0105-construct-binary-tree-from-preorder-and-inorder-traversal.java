/**
 * Recursive Divide and Conquer
 *
 * Preorder opens with the root, and that root's position in inorder
 * splits the remaining values into a left and a right subtree, so each
 * call owns a disjoint range and the split repeats down the tree.
 *
 * The value to index map turns the root lookup into O(1), which is what
 * keeps the build linear instead of quadratic.
 *
 * Time: O(n), since every node is created once and its position is
 * found in constant time.
 * Space: O(n) for the map, plus O(h) for the recursion.
 */
class Solution {

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        // Locating a root inside inorder must be O(1), otherwise the whole build degrades to O(n^2).
        Map<Integer, Integer> inorderIndexByValue = new HashMap<>(inorder.length);

        for (int index = 0; index < inorder.length; index++) {
            inorderIndexByValue.put(inorder[index], index);
        }

        return buildSubtree(preorder, 0, preorder.length - 1, 0, inorderIndexByValue);
    }

    // Builds the subtree sitting in the current preorder range, with its inorder values starting at inorderStart.
    private TreeNode buildSubtree(int[] preorder, 
                                  int preorderStart, 
                                  int preorderEnd, 
                                  int inorderStart, 
                                  Map<Integer, Integer> inorderIndexByValue) {
        if (preorderStart > preorderEnd) {
            return null;
        }

        int rootValue = preorder[preorderStart];
        TreeNode root = new TreeNode(rootValue);
        int rootInorderIndex = inorderIndexByValue.get(rootValue);
        int leftSubtreeSize = rootInorderIndex - inorderStart;
        int leftSubtreePreorderEnd = preorderStart + leftSubtreeSize;

        root.left = buildSubtree(preorder,
                        preorderStart + 1,
                        leftSubtreePreorderEnd,
                        inorderStart,
                        inorderIndexByValue);

        root.right = buildSubtree(preorder,
                        leftSubtreePreorderEnd + 1,
                        preorderEnd, rootInorderIndex + 1,
                        inorderIndexByValue);

        return root;
    }
}

/**
 * Iterative Single Pass
 *
 * The stack holds the path of nodes whose subtrees are still open, so
 * the newest node always attaches to the node on top or to one of the
 * ancestors that the inorder pointer has just closed.
 *
 * The inorder pointer names the next node whose left subtree is
 * finished, so a stack top matching it is done on the left and the last
 * node popped is the deepest ancestor still missing a right child.
 *
 * Time: O(n), since every node is pushed and popped at most once.
 * Space: O(h), since the stack only holds the current path and no map
 * is needed.
 */
class Solution {

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder.length == 0) {
            return null;
        }

        TreeNode root = new TreeNode(preorder[0]);

        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);

        int inorderIndex = 0;

        for (int preorderIndex = 1; preorderIndex < preorder.length; preorderIndex++) {
            TreeNode newNode = new TreeNode(preorder[preorderIndex]);

            if (stack.peek().val != inorder[inorderIndex]) {
                // The node on top still misses its left child, so the next preorder value goes there.
                stack.peek().left = newNode;
            } else {
                // Every ancestor matching the current inorder value has a complete left subtree.
                // The last one popped is the deepest ancestor still waiting for a right child.
                TreeNode parentOfRightChild = null;

                while (!stack.isEmpty() && stack.peek().val == inorder[inorderIndex]) {
                    parentOfRightChild = stack.pop();
                    inorderIndex++;
                }

                parentOfRightChild.right = newNode;
            }

            stack.push(newNode);
        }

        return root;
    }
}
