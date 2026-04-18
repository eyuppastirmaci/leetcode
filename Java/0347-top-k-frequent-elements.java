// Frequency Bucket
// Time: O(n), just run through the array to count frequencies, drop them into  buckets, and gather the top k
// every step is strictly linear, which neatly takes care of the O(n log n) rule.
// Space: O(n) for the frequency map and the bucket array, both bounded by the number of unique elements in nums
// (which is at most n)
class Solution {
    public int[] topKFrequent(int[] nums, int k) {

        var map = new HashMap<Integer, Integer>();
        List<Integer>[] buckets = new List[nums.length + 1];

        for (int num: nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int num = entry.getKey();
            int freq = entry.getValue();

            if (buckets[freq] == null) {
                buckets[freq] = new ArrayList<>();
            }

            buckets[freq].add(num);
        }

        int[] result = new int[k];
        int index = 0;

        for (int i = buckets.length - 1; i >= 0 && index < k; i--) {
            if (buckets[i] == null) continue;
            for (int num : buckets[i]) {
                result[index++] = num;
                if (index == k) break;
            }
        }

        return result;
    }
}