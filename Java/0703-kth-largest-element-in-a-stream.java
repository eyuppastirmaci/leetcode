/**
 * Custom Heap
 * Time: O(n log k) initialization, O(log k) per add
 * Space: O(k)
 */
class KthLargest {

    private final int k;
    private final IntMinHeap minHeap;

    public KthLargest(int k, int[] nums) {
        this.k = k;
        this.minHeap = new IntMinHeap(k + 1);

        for (int value : nums) {
            retainTopK(value);
        }
    }
    
    public int add(int val) {
        retainTopK(val);

        // the minimum of the top k elements is the kth largest
        return this.minHeap.min();
    }

    private void retainTopK(int value) {
        // insert new element into heap
        this.minHeap.insert(value);

        // if there are more than needed, remove the minimum
        if (this.minHeap.size() > this.k) {
            this.minHeap.extractMin();
        }
    }

    private static class IntMinHeap {

        private final int[] elements; // stores the binary min-heap in a zero-based array representation
        private int size;

        public IntMinHeap(int initialCapacity) {
            this.elements = new int[initialCapacity];
        }

        // insert
        public void insert(int value) {
            int index = this.size;
            this.elements[index] = value;
            this.size++;
            siftUp(index);
        }

        // extractMin
        public int extractMin() {
            int min = this.elements[0];
            this.elements[0] = this.elements[this.size - 1];
            this.size--;
            siftDown(0);
            return min;
        }

        // min
        public int min() {
            return this.elements[0];
        }

        // size
        public int size() {
            return this.size;
        }

        private void siftUp(int index) {
            while (index > 0) {
                // in a zero-based array representation of a binary heap, the parent is at (index - 1) / 2
                int parentIndex = (index - 1) / 2;

                if (this.elements[index] >= this.elements[parentIndex]) {
                    break;
                }

                this.swap(index, parentIndex);
                index = parentIndex;
            }
        }

        private void siftDown(int index) {
            while (true) {
                // in a zero-based array representation of a binary heap, the left child is at 2 * index + 1
                int leftChildIndex = 2 * index + 1;

                if (leftChildIndex >= size) {
                    return;
                }

                // in a zero-based array representation of a binary heap, the right child is at 2 * index + 2
                int rightChildIndex = 2 * index + 2;
                int smallerChildIndex = leftChildIndex;

                if (rightChildIndex < size && elements[rightChildIndex] < elements[leftChildIndex]) {
                    smallerChildIndex = rightChildIndex;
                }

                if (elements[index] <= elements[smallerChildIndex]) {
                    return;
                }

                swap(index, smallerChildIndex);
                index = smallerChildIndex;
            }
        }

        private void swap(int firstIndex, int secondIndex) {
            int temp = this.elements[firstIndex];
            this.elements[firstIndex] = this.elements[secondIndex];
            this.elements[secondIndex] = temp;
        }

    }
}

/**
 * PriorityQueue
 * Time: O(n log k) initialization, O(log k) per add
 * Space: O(k)
 */
class KthLargest {

    private final int k;
    private final Queue<Integer> minHeap;

    public KthLargest(int k, int[] nums) {
        this.k = k;
        this.minHeap = new PriorityQueue<>();

        for (int value : nums) {
            this.add(value);
        }
    }
    
    public int add(int val) {
        this.minHeap.offer(val);

        if (this.minHeap.size() > this.k) {
            this.minHeap.poll();
        }

        return this.minHeap.peek();
    }
}

