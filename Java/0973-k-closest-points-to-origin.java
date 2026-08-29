/**
 * PriorityQueue (Max Heap)
 * Time: O(n log k)
 * Space: O(k)
 */
class Solution {
    public int[][] kClosest(int[][] points, int k) {
        
        PriorityQueue<int[]> farthestOfK = new PriorityQueue<>(
            (a, b) -> Long.compare(squaredDistance(b), squaredDistance(a))
        );

        for (int[] point : points) {
            farthestOfK.offer(point);
            if (farthestOfK.size() > k) {
                farthestOfK.poll();
            }
        }

        int[][] closest = new int[k][2];

        for (int i = 0; i < k; i++) {
            closest[i] = farthestOfK.poll();
        }

        return closest;
    }

    private long squaredDistance(int[] p) {
        long x = p[0];
        long y = p[1];
        return x * x + y * y;
    }
}
