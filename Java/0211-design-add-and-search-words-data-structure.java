/**
 * Trie + DFS
 *
 * L - length of the word
 * K - alphabet size (26)
 * N - total number of characters inserted
 * D - number of '.' characters in the search query (D <= 2 by constraints)
 *
 * Time:
 *   addWord: O(L)
 *   search:  O(L) without '.'
 *            O(K^D * L) with '.'
 *
 * Space:
 *   addWord: O(L * K) extra (worst case: creates L new nodes, each with K child refs)
 *   search:  O(D) extra, since recursion only happens on '.' characters and plain letters are handled iteratively
 *   Overall: O(N * K)
 */
class WordDictionary {
    private static final int ALPHABET_SIZE = 26;

    private final TrieNode root;

    private static class TrieNode {
        private boolean isEndOfWord;
        private final TrieNode[] children = new TrieNode[ALPHABET_SIZE];
    }

    public WordDictionary() {
        this.root = new TrieNode();
    }

    public void addWord(String word) {
        TrieNode current = root;

        for (int i = 0; i < word.length(); i++) {
            int index = word.charAt(i) - 'a';

            if (current.children[index] == null) {
                current.children[index] = new TrieNode();
            }

            current = current.children[index];
        }

        current.isEndOfWord = true;
    }

    public boolean search(String word) {
        return matchesFrom(word, 0, root);
    }

    private boolean matchesFrom(String word, int startIndex, TrieNode startNode) {
        TrieNode node = startNode;

        for (int i = startIndex; i < word.length(); i++) {
            char c = word.charAt(i);

            if (c == '.') {
                for (TrieNode child : node.children) {
                    if (child != null && matchesFrom(word, i + 1, child)) {
                        return true;
                    }
                }

                return false;
            }

            node = node.children[c - 'a'];
            if (node == null) {
                return false;
            }
        }

        return node.isEndOfWord;
    }
}
