/**
 * PriorityQueue + Cooldown Queue
 * Time: O(n)
 * Space: O(1)
 */
class Solution {
    public int leastInterval(char[] tasks, int n) {

        int[] taskCounts = new int[26];

        for (char task : tasks) {
            taskCounts[task - 'A']++;
        }

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());

        for (int count : taskCounts) {
            if (count > 0) {
                maxHeap.offer(count);
            }
        }

        int time = 0;
        Queue<int[]> cooldown = new LinkedList<>();

        while (!maxHeap.isEmpty() || !cooldown.isEmpty()) {
            time++;

            if (!maxHeap.isEmpty()) {
                int remaining = maxHeap.poll() - 1;

                if (remaining > 0) {
                    cooldown.offer(new int[] {remaining, time + n});
                }
            }

            if (!cooldown.isEmpty() && cooldown.peek()[1] == time) {
                maxHeap.offer(cooldown.poll()[0]);
            }
        }
        
        return time;
    }
}

/**
 * Greedy (idle-frame formula): max(N, (F - 1)(n + 1) + C)
 * Time: O(n)
 * Space: O(1)
 */
class Solution {
    public int leastInterval(char[] tasks, int n) {

        int[] taskCounts = new int[26];

        for (char task : tasks) {
            taskCounts[task - 'A']++;
        }

        int maxFreq = 0;
        for (int count : taskCounts) {
            maxFreq = Math.max(maxFreq, count);
        }

        int maxCount = 0;
        for (int count : taskCounts) {
            if (count == maxFreq) {
                maxCount++;
            }
        }

        int frameLength = (maxFreq - 1) * (n + 1) + maxCount;
        
        return Math.max(tasks.length, frameLength);
    }
}
