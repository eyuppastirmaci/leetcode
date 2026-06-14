/**
 * Hash Map
 *
 * The brute-force approach checks every pair (i, j) with two nested loops, which costs O(n^2) time.
 * We can do better by trading space for time: as we scan the array once, we remember what value would
 * complete each number we've already seen.
 *
 * For every index i, the number that pairs with nums[i] is its complement, target - nums[i]. Instead of
 * searching for that complement later, we store it ahead of time. When we reach a number that was registered
 * as someone's complement, the map already holds the index of that earlier element, so we have found the pair
 * in a single lookup.
 *
 * Because we put the complement (not the value) into the map keyed to the current index, the entry we read back
 * always belongs to an earlier element. The returned pair is therefore naturally ordered with the smaller index
 * first, as required.
 *
 * Time: O(n).
 *   - The loop runs at most n times, once per element.
 *   - Inside each iteration we do a map lookup and possibly a map insertion. On a hash map both are O(1) on
 *     average, independent of how many entries the map already holds; the cost of a single lookup does not grow
 *     as the map fills up.
 *   - Combining these: n iterations multiplied by O(1) work per iteration gives O(n) * O(1) = O(n). The single
 *     linear pass is the dominant term, so the overall time complexity is O(n).
 *
 * Space: O(n).
 *   - The two int pointers (i and the temporary pairIndex) take O(1) space; their size does not depend on the
 *     input length.
 *   - The map is what grows with the input: in the worst case we scan almost the entire array before finding the
 *     pair, inserting an entry on each of those iterations, so the map can hold up to n entries.
 *   - Combining these: O(1) for the local variables plus O(n) for the map. The map term dominates, so the overall
 *     space complexity is O(n).
 */
class Solution {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> complementToIndex = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            Integer pairIndex = complementToIndex.get(nums[i]);

            if (pairIndex != null) {
                return new int[] { pairIndex, i };
            }

            complementToIndex.put(target - nums[i], i);
        }

        throw new IllegalArgumentException("There is no valid pair");
    }
}