/*
 *
 * Bitset
 *
 * Each key is one bit: word index is key >>> 6, bit index is key & 63
 *
 * U: size of the key range, 10^6 + 1
 *
 * Time complexity: add, remove, contains: O(1), no hashing or collisions
 *
 * Space complexity: O(U / 64), about 125 KB of longs
 *                   memory depends on the key range, not on the number of keys
 *
 * Trade-off: needs a small, non-negative key range
 *            the full int range would take 512 MB even for a few keys
 *
 */
class MyHashSet {
    private static final int MAX_KEY = 1_000_000;
    private static final int ADDRESS_BITS_PER_WORD = 6;
    private static final int BIT_INDEX_MASK = (1 << ADDRESS_BITS_PER_WORD) - 1;

    private final long[] words = new long[wordIndex(MAX_KEY) + 1];

    public void add(int key) {
        int index  = wordIndex(key);
        long bit = bitFor(key);

        words[index] |= bit;
    }

    public void remove(int key) {
        int index = wordIndex(key);
        long bit = bitFor(key);

        words[index] &= ~bit;
    }

    public boolean contains(int key) {
        int index = wordIndex(key);
        long bit = bitFor(key);

        long matchedBit = words[index] & bit;

        return matchedBit != 0;
    }

    private static int wordIndex(int key) {
        return key >>> ADDRESS_BITS_PER_WORD;
    }

    private static long bitFor(int key) {
        int bitIndex = key & BIT_INDEX_MASK;

        return 1L << bitIndex;
    }
}

/*
 *
 * Separate Chaining (LinkedList)
 *
 * n: number of keys currently in the set
 * B: number of buckets, 1009
 *
 * Time complexity: add, remove, contains: O(n / B) on average
 *                  O(1) in practice for 10^4 calls, worst case O(n)
 *
 * Space complexity: O(B + n)
 *                   O(B) for the bucket array and its lazily created lists
 *                   O(n) for the stored keys
 *
 */
class MyHashSet {
    private static final int BUCKETS = 1009;
    private final LinkedList<Integer>[] buckets = new LinkedList[BUCKETS];

    private LinkedList<Integer> bucketFor(int key) {
        int index = key % BUCKETS;

        if (buckets[index] == null) {
            buckets[index] = new LinkedList<>();
        }

        return buckets[index];
    }

    public void add(int key) {

        LinkedList<Integer> bucket = bucketFor(key);
        
        if (!bucket.contains(key)) {
            bucket.add(key);
        }
    }

    public void remove(int key) {
        bucketFor(key).remove(Integer.valueOf(key));
    }

    public boolean contains(int key) {
        return bucketFor(key).contains(key);
    }
}

/*
 *
 * Separate Chaining (Hash Table)
 *
 * n: number of keys currently in the set
 * M: maximum number of keys stored at the same time
 *
 * Time complexity: add, remove, contains: O(1) on average
 *                  load factor keeps chains short, worst case O(n)
 *                  resize is O(n), amortized O(1) per add
 *
 * Space complexity: O(M)
 *                   O(n) for the nodes and O(M) for the table,
 *                   since the table grows with the keys and never shrinks
 *
 */
class MyHashSet {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;
    private static final int HALF_INT_BITS = Integer.SIZE / 2;

    private Node[] table;
    private int size;
    private int threshold;

    public MyHashSet() {
        table = new Node[DEFAULT_CAPACITY];
        threshold = (int) (DEFAULT_CAPACITY * LOAD_FACTOR);
    }

    public void add(int key) {
        // hash is computed here instead of using indexFor because the node stores it for resize
        int hash = calcHash(key);
        int index = calcIndex(hash, table.length);
        Node node = findNode(index, key);

        if (node != null) {
            return;
        }

        table[index] = new Node(key, hash, table[index]);
        size++;

        if (size > threshold) {
            resize();
        }
    }

    public void remove(int key) {
        int index = indexFor(key);

        Node current = table[index];
        Node prev = null;

        while (current != null) {
            if (current.val == key) {
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

    public boolean contains(int key) {
        int index = indexFor(key);

        return findNode(index, key) != null;
    }

    private static int calcHash(int key) {
        return key ^ (key >>> HALF_INT_BITS);
    }

    private static int calcIndex(int hash, int tableLength) {
        return hash & (tableLength - 1);
    }

    private int indexFor(int key) {
        int hash = calcHash(key);

        return calcIndex(hash, table.length);
    }

    private Node findNode(int index, int key) {
        Node current = table[index];

        while (current != null) {
            if (current.val == key) {
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
        final int val;
        final int hash;
        Node next;

        Node(int val, int hash, Node next) {
            this.val = val;
            this.hash = hash;
            this.next = next;
        }
    }
}
