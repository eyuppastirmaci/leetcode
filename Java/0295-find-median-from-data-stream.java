/**
 * Time: addNum - O(log n)
 *       findMedian - O(1)
 * Space: O(n)
 */
class MedianFinder {

    private final PriorityQueue<Integer> left; // max heap
    private final PriorityQueue<Integer> right; // min heap

    //    partition
    //  left     right
    // 1 2 3  |  4 5 6
    //      median

    public MedianFinder() {
        this.left = new PriorityQueue<>(Comparator.reverseOrder());
        this.right = new PriorityQueue<>();
    }

    public void addNum(int num) {
        if (left.isEmpty() || num <= left.peek()) {
            left.offer(num);
        } else {
            right.offer(num);
        }

        if (left.size() > right.size() + 1) {
            right.offer(left.poll());
        } else if (right.size() > left.size() + 1) {
            left.offer(right.poll());
        }
    }

    public double findMedian() {
        if (left.size() > right.size()) {
            return left.peek();
        }

        if (right.size() > left.size()) {
            return right.peek();
        }

        return ((double) left.peek() + right.peek()) / 2.0;
    }
}

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */
