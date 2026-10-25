/*
 *
 * Direct Addressing
 *
 * Each key is its own index, values are stored shifted by one
 *
 * U: size of the key range, 10^6 + 1
 *
 * Time complexity: put, get, remove: O(1), no hashing or collisions
 *
 * Space complexity: O(U), about 4 MB of ints
 *                   memory depends on the key range, not on the number of keys
 *
 * Trade-off: needs a small, non-negative key range
 *            the full int range would take 16 GB even for a few keys
 *
 */
class MyHashMap {
    private static final int MAX_KEY = 1_000_000;
    private final int[] values = new int[MAX_KEY + 1];

    public void put(int key, int value) {
        values[key] = value + 1;
    }

    public int get(int key) {
        return values[key] - 1;
    }

    public void remove(int key) {
        values[key] = 0;
    }
}

/*
 *
 * Separate Chaining (Hash Table)
 *
 * n: number of keys currently in the map
 * M: maximum number of keys stored at the same time
 *
 * Time complexity: put, get, remove: O(1) on average
 *                  load factor keeps chains short, worst case O(n)
 *                  resize is O(n), amortized O(1) per put
 *
 * Space complexity: O(M)
 *                   O(n) for the nodes and O(M) for the table,
 *                   since the table grows with the keys and never shrinks
 *
 */
class MyHashMap {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;
    private static final int HALF_INT_BITS = Integer.SIZE / 2;
    private static final int NOT_FOUND = -1;

    private Node[] table;
    private int size;
    private int threshold;

    public MyHashMap() {
        table = new Node[DEFAULT_CAPACITY];
        threshold = (int) (DEFAULT_CAPACITY * LOAD_FACTOR);
    }

    public void put(int key, int value) {
        // hash is computed here instead of using indexFor because the node stores it for resize
        int hash = calcHash(key);
        int index = calcIndex(hash, table.length);
        Node node = findNode(index, key);

        if (node != null) {
            node.value = value;
            return;
        }

        table[index] = new Node(key, value, hash, table[index]);
        size++;

        if (size > threshold) {
            resize();
        }
    }

    public int get(int key) {
        Node node = findNode(indexFor(key), key);

        return node != null ? node.value : NOT_FOUND;
    }

    public void remove(int key) {
        int index = indexFor(key);

        Node current = table[index];
        Node prev = null;

        while (current != null) {
            if (current.key == key) {
                if (prev == null) {
                    table[index] = current.next;
                } else {
                    prev.next = current.next;
                }

                size--;

                return;
            }

            prev = current;
            current = current.next;
        }
    }

    private static int calcHash(int key) {
        return key ^ (key >>> HALF_INT_BITS);
    }

    private static int calcIndex(int hash, int tableLength) {
        return hash & (tableLength - 1);
    }

    private int indexFor(int key) {
        return calcIndex(calcHash(key), table.length);
    }

    private Node findNode(int index, int key) {
        Node current = table[index];

        while (current != null) {
            if (current.key == key) {
                return current;
            }

            current = current.next;
        }

        return null;
    }

    private void resize() {
        int oldCapacity = table.length;
        int newCapacity = oldCapacity * 2;
        Node[] newTable = new Node[newCapacity];

        for (int index = 0; index < oldCapacity; index++) {
            Node lowHead = null;
            Node lowTail = null;
            Node highHead = null;
            Node highTail = null;

            Node current = table[index];

            while (current != null) {
                Node next = current.next;
                current.next = null;

                if ((current.hash & oldCapacity) == 0) {
                    if (lowTail == null) {
                        lowHead = current;
                    } else {
                        lowTail.next = current;
                    }

                    lowTail = current;
                } else {
                    if (highTail == null) {
                        highHead = current;
                    } else {
                        highTail.next = current;
                    }

                    highTail = current;
                }

                current = next;
            }

            newTable[index] = lowHead;
            newTable[index + oldCapacity] = highHead;
        }

        table = newTable;
        threshold = (int) (newCapacity * LOAD_FACTOR);
    }

    private static class Node {
        final int key;
        final int hash;
        int value;
        Node next;

        Node(int key, int value, int hash, Node next) {
            this.key = key;
            this.value = value;
            this.hash = hash;
            this.next = next;
        }
    }
}
