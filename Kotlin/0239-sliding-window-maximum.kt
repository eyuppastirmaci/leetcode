/**
 * Time: O(n * k)
 * For each of the (n - k + 1) windows, we scan all k elements to find the maximum. This gives roughly n * k operations.
 *
 * Space: O(1)
 * Only a few index/value variables are used in addition to the output array, which is required by the problem and not
 * counted as auxiliary space.
*/
class Solution {
    fun maxSlidingWindow(nums: IntArray, k: Int): IntArray {
        val maxes = IntArray(nums.size - k + 1)

        for (i in 0..nums.size - k) {
            var currentMax = nums[i]

            for (j in i + 1 until i + k) {
                if (nums[j] > currentMax) {
                    currentMax = nums[j]
                }
            }

            maxes[i] = currentMax
        }

        return maxes
    }
}

/**
 * Monotonic Deque
 *
 * The deque holds indices whose values are strictly decreasing, so the front
 * always points to the maximum of the current window.
 *
 * When a new element nums[i] arrives, any index at the back of the deque whose value is less than or equal to nums[i]
 * can never be a future max, because it will expire no later than i and until then nums[i] is at least as large,
 * so we pop those indices from the back.
 *
 * The front index is removed once it falls outside the window range [i - k + 1, i], which we check with a single
 * comparison before processing each new element.
 *
 * Time: O(n). Each index is pushed and popped at most once, so the total number of deque operations is bounded by 2n.
 *
 * Space: O(k). The deque holds at most k indices at any time.
 */
class Solution {
    fun maxSlidingWindow(nums: IntArray, k: Int): IntArray {
        val maxes = IntArray(nums.size - k + 1)
        val deque = ArrayDeque<Int>()

        for (i in nums.indices) {
            if (deque.isNotEmpty() && deque.first() <= i - k) {
                deque.removeFirst()
            }

            while (deque.isNotEmpty() && nums[deque.last()] <= nums[i]) {
                deque.removeLast()
            }

            deque.addLast(i)

            if (i >= k - 1) {
                maxes[i - k + 1] = nums[deque.first()]
            }
        }

        return maxes
    }
}