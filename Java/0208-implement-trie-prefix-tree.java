/**
 * Trie
 *
 * L - length of the word or prefix
 * K - alphabet size (26)
 * N - total number of characters inserted
 *
 * Time:
 *   insert: O(L)
 *   search: O(L)
 *   startsWith: O(L)
 *
 * Space:
 *   insert: O(L * K) extra (worst case: creates L new nodes)
 *   search: O(1) extra
 *   startsWith: O(1) extra
 *   Overall: O(N * K)
 */
class Trie {
    private static final int ALPHABET_SIZE = 26;

    private final TrieNode root;

    private static class TrieNode {
        private boolean isEndOfWord;
        private final TrieNode[] children = new TrieNode[ALPHABET_SIZE];
    }

    public Trie() {
        this.root = new TrieNode();
    }

    public void insert(String word) {
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
        TrieNode node = findNode(word);
        return node != null && node.isEndOfWord;
    }

    public boolean startsWith(String prefix) {
        TrieNode node = findNode(prefix);
        return node != null;
    }

    private TrieNode findNode(String key) {
        TrieNode current = root;

        for (int i = 0; i < key.length(); i++) {
            int index = key.charAt(i) - 'a';

            if (current.children[index] == null) {
                return null;
            }

            current = current.children[index];
        }

        return current;
    }
}
