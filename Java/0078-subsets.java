/**
 * Include / Exclude Backtracking
 * Time: O(n * 2^n)
 * Space: O(n) auxiliary, O(n * 2^n) including the output
 */
class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> state = new ArrayList<>(); // shared state

        backtrack(0, nums, state, result);

        return result;
    }

    private void backtrack(
        int index,
        int[] nums,
        List<Integer> state, // currentSubset
        List<List<Integer>> result) {

        // base case
        if (index == nums.length) {
            // mutate result with current state
            result.add(new ArrayList<>(state));
            return;
        }

        // choose
        state.add(nums[index]);

        // explore
        backtrack(index + 1, nums, state, result);

        // undo choice
        state.remove(state.size() - 1);

        // explore without choosing
        backtrack(index + 1, nums, state, result);
    }
}

/**
 * Backtracking
 * Time: O(n * 2^n)
 * Space: O(n) auxiliary, O(n * 2^n) including the output
 */
class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> state = new ArrayList<>(); // shared state

        backtrack(0, nums, state, result);

        return result;
    }

    private void backtrack(
        int index,
        int[] nums,
        List<Integer> state,
        List<List<Integer>> result
    ) {
        // a copy is required because state is modified during backtracking
        List<Integer> subsetSnapshot = new ArrayList<>(state);

        // add a snapshot of the subset represented by the current recursive call stack
        result.add(subsetSnapshot);

        for (int i = index; i < nums.length; i++) {
            state.add(nums[i]);

            backtrack(i + 1, nums, state, result);

            // backtrack to the subset represented by the parent call stack frame
            state.remove(state.size() - 1);
        }
    }
}

/**
 * Bitmasking
 * Time: O(n * 2^n)
 * Space: O(n) auxiliary, O(n * 2^n) including the output
 */
class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        int subsetCount = 1 << nums.length; // calculates 2^n, the total number of possible subsets

        for (int mask = 0; mask < subsetCount; mask++) {
            List<Integer> subset = new ArrayList<>();

            for (int i = 0; i < nums.length; i++) {
                // isolates the bit that determines whether nums[i] is included in the current subset
                int elementBitMask = 1 << i;
                // checks whether the bit for nums[i] is set in the current subset mask
                boolean isElementIncluded = (mask & elementBitMask) != 0;

                if (isElementIncluded) {
                    subset.add(nums[i]);
                }
            }

            result.add(subset);
        }

        return result;
    }
}
