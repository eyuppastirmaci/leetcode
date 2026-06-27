/**
 *
 * This solution correctly finds the duplicate number by storing each visited number in a HashSet.
 * If adding a number to the set fails, it means that the number has already been seen, so it is the duplicate.
 *
 * However, this solution does not satisfy the constant extra space requirement.
 * In the worst case, the HashSet grows linearly with the input size because we may add one number during each loop iteration.
 *
 * Time: O(n), because we iterate through the array once.
 * Space: O(n), because the HashSet can store up to n numbers.
 *
 */
class Solution {
    public int findDuplicate(int[] nums) {
        Set<Integer> numSet = new HashSet<>();

        for (int num : nums)  {
            if (!numSet.add(num)) {
                return num;
            }
        }

        throw new IllegalArgumentException("Input array must contain at least one duplicate number");
    }
}

/**
 *
 * Since every number is in the range [1, n], each value can be used as a valid index.
 * Also, no value can point back to index 0, so index 0 is not part of a cycle and is a safe starting point.
 * Therefore, we can think of the array as a directed graph with a tail starting from index 0 and a cycle later on.
 *
 * Because the array has n + 1 numbers but only n possible values, the pigeonhole principle guarantees that
 * at least one duplicate number exists. In this graph representation, the duplicate number is the cycle entry point.
 *
 * Floyd's Tortoise and Hare algorithm first finds a meeting point inside the cycle by moving slow one step and
 * fast two steps at a time. For the second phase, let F be the distance from the start to the cycle entry,
 * a be the distance from the cycle entry to the meeting point, and C be the cycle length.
 * At the meeting point, slow has traveled F + a steps and fast has traveled 2(F + a) steps.
 * Their difference must be a multiple of C, so F + a = kC, which gives F = kC - a.
 *
 * This means that starting one pointer from the beginning and one pointer from the meeting point, then moving both
 * one step at a time, makes them meet at the cycle entry point. That entry point is the duplicate number.
 *
 * Time: O(n), because both phases traverse the array linearly.
 * Space: O(1), because we only use a constant number of variables.
 *
 */
class Solution {
    public int findDuplicate(int[] nums) {
        int slow = nums[0];
        int fast = nums[0];

        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);

        int finder = nums[0];

        while (finder != slow) {
            finder = nums[finder];
            slow = nums[slow];
        }

        return finder;
    }
}