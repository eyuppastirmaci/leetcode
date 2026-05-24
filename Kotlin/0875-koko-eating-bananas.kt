/**
 *
 * In this problem, the value we’re looking for is k, which is Koko’s hourly banana-eating rate. The minimum value
 * for k is 1, and the maximum value is the largest element in the piles array. Since Koko works on at most one
 * pile per hour, if she chooses a rate up to the size of the largest pile,
 * each pile will be finished in at most one hour.
 *
 * Using brute force, we could try out k values one by one from 1 to maxpile or from maxpile to 1. For each k,
 * we would iterate through the entire piles array to calculate the total number of hours required.
 * The time complexity of this approach would be O(n * maxpile), and since maxpile could be 10^9, it is inefficient.
 *
 * As a more optimal approach, we use binary search. This is because as k increases, the time it takes for Koko to
 * finish the bananas decreases; as k decreases, the time increases. In other words, there is a monotonic structure.
 * If all the bananas can be eaten within h hours with a given k value, then all larger k values are also valid.
 * If a given k is insufficient, then smaller k values are also insufficient.
 *
 * Therefore, we perform a binary search on the solution space with left = 1 and right = maxpile. At each step, we test
 * the mid speed. If the bananas are finished within h hours at mid, we keep this value as a candidate solution and
 * check to the left for a smaller speed. If they aren’t finished,
 * we move to the right because a larger speed is needed.
 *
 * The binary search takes log(maxpile) steps. Since we traverse the entire piles array at each step, the total time
 * complexity is O(n log maxpile). Since we do not use any extra data structures, the space complexity is O(1).
 *
 */
class Solution {
    fun minEatingSpeed(piles: IntArray, h: Int): Int {
        var left = 1
        var right = piles.maxOrNull() ?: 1
        var answer = right

        while (left <= right) {
            val mid = left + (right - left) / 2

            if (canEatAll(piles, h, mid)) {
                answer = mid
                right = mid - 1
            } else {
                left = mid + 1
            }
        }

        return answer
    }

    private fun canEatAll(piles: IntArray, h: Int, speed: Int): Boolean {
        var hours = 0L

        for (pile in piles) {
            // For each pile, the required time is ceil(pile / speed).
            // Instead of using floating-point math, we compute it as:
            hours += (pile.toLong() + speed - 1) / speed

            if (hours > h) {
                return false
            }
        }

        return true
    }
}