// Brute force
// Time: O(n2) worst case, but often faster in practice due to early break.
// Space: O(1) extra.
class Solution {
    public int[] twoSum(int[] numbers, int target) {

        for (int i = 0; i < numbers.length - 1; i++) {

            for (int j = i + 1; j < numbers.length; j++) {

                int sum = numbers[i] + numbers[j];

                if (sum == target) {
                    return new int[] { i + 1, j + 1 };
                } else if (sum > target) {
                    break;
                }
            }

        }

        throw new IllegalArgumentException("Input does not satisfy problem guarantee: no valid pair found for target " + target);
    }
}

// Binary search
// Time: O(n log n), for each of the n indices we binary search the remaining suffix in O(log n).
// Space: O(1) extra, only a few indices are kept.
class Solution {
    public int[] twoSum(int[] numbers, int target) {

        for (int i = 0; i < numbers.length - 1; i++) {

            int complement = target - numbers[i];

            int lowerBound = i + 1;
            int upperBound = numbers.length - 1;

            while (lowerBound <= upperBound) {

                int mid = lowerBound + (upperBound - lowerBound) / 2;

                if (numbers[mid] == complement) {
                    return new int[] { i + 1, mid + 1 };
                } else if (numbers[mid] < complement) {
                    lowerBound = mid + 1;
                } else {
                    upperBound = mid - 1;
                }

            }
        }

        throw new IllegalArgumentException("Input does not satisfy problem guarantee: no valid pair found for target " + target);
    }
}

// Two pointers
// Time: O(n), the two pointers move toward each other and together traverse the array at most once.
// Space: O(1) extra, only two integer indices are kept.
class Solution {
    public int[] twoSum(int[] numbers, int target) {

        int lpi = 0;
        int rpi = numbers.length - 1;

        while (lpi < rpi) {

            int sum = numbers[lpi] + numbers[rpi];

            if (sum == target) {
                return new int[] { lpi + 1, rpi + 1 };
            } else if (sum < target) {
                lpi++;
            } else {
                rpi--;
            }

        }

        throw new IllegalArgumentException("Input does not satisfy problem guarantee: no valid pair found for target " + target);
    }
}