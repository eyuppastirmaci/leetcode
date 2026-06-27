/**
 *
 * The main challenge of this problem is satisfying two seemingly conflicting requirements:
 *
 * 1. Access a key in O(1) time.
 * 2. Update the usage order in O(1) time whenever a key is accessed or inserted.
 *
 * We first consider using only a linked list because inserting or removing nodes is efficient.
 * However, a linked list alone cannot locate a node by its key without scanning the entire list,
 * making get(key) O(n).
 *
 * A singly linked list is also insufficient because removing an arbitrary node requires access
 * to its previous node. Even if we already know the node itself, finding its predecessor would
 * require traversing the list.
 *
 * A doubly linked list solves this problem by storing both prev and next pointers.
 * Unlike a singly linked list, this allows removing an arbitrary node in O(1) once we already
 * have a reference to it. We can then immediately move that node to the front of the list,
 * making it the most recently used entry.
 *
 * This still leaves one missing piece: how to find the corresponding node for a given key in O(1).
 * To achieve this, we maintain a HashMap that maps:
 *
 *     key -> Node
 *
 * The HashMap provides constant-time lookup, while the doubly linked list maintains the usage order.
 * Every successful get() or put() moves the corresponding node to the front of the list, making it
 * the most recently used (MRU). The least recently used (LRU) node is always kept immediately before
 * the tail, so eviction is simply removing that node.
 *
 * The linked list uses two sentinel (dummy) nodes: head and tail.
 * Real cache entries are always stored between these sentinels, eliminating special cases for empty
 * lists, single-element lists, and head/tail removals. As a result, insertion and removal always use
 * the same pointer updates without requiring separate edge-case handling.
 *
 * Each linked list node stores:
 *
 * - key   : needed to remove the corresponding entry from the HashMap during eviction.
 * - value : the cached value returned by get().
 * - prev  : enables O(1) removal by directly accessing the previous node.
 * - next  : enables O(1) insertion and removal by directly accessing the next node.
 *
 * Together, the HashMap and doubly linked list complement each other:
 *
 * - HashMap          -> O(1) average key lookup.
 * - DoublyLinkedList -> O(1) insertion, removal, and usage-order updates.
 *
 * Time:
 * - get(): O(1) average.
 * - put(): O(1) average.
 *
 * Space:
 * O(capacity), because both the HashMap and the doubly linked list store at most
 * 'capacity' cache entries.
 *
 */
class LRUCache {

    private static class Node {
        int key;
        int value;

        Node prev;
        Node next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity;
    private final Map<Integer, Node> storage;

    private final Node head;
    private final Node tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.storage = new HashMap<>();

        head = new Node(0, 0);
        tail = new Node(0, 0);

        head.next = tail;
        tail.prev = head;
    }

    private void addFirst(Node node) {
        node.prev = head;
        node.next = head.next;

        head.next.prev = node;
        head.next = node;
    }

    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;

        node.prev = null;
        node.next = null;
    }

    private Node removeLast() {
        if (tail.prev == head) {
            return null;
        }

        Node lru = tail.prev;
        remove(lru);

        return lru;
    }

    public int get(int key) {
        Node node = storage.get(key);

        if (node == null) {
            return -1;
        }

        remove(node);
        addFirst(node);

        return node.value;
    }

    public void put(int key, int value) {
        Node node = storage.get(key);

        if (node != null) {
            node.value = value;
            remove(node);
            addFirst(node);
            return;
        }

        if (storage.size() == capacity) {
            Node lru = removeLast();
            storage.remove(lru.key);
        }

        Node newNode = new Node(key, value);
        addFirst(newNode);
        storage.put(key, newNode);
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */