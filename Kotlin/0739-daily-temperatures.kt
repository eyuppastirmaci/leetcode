/**
 * Time: each index is pushed onto the stack once and popped at most once, so although the inner while loop may run
 * multiple times for a given i, the total number of push/pop operations across the entire outer loop is bounded by
 * 2n. This gives amortized O(n) overall, not O(n2) as the nested loops might suggest.
 *
 * Space: in the worst case (strictly decreasing temperatures) every index is pushed before any is popped, so the
 * stack holds up to n indices, giving O(n) auxiliary space.
 */
class Solution {
    fun dailyTemperatures(temperatures: IntArray): IntArray {

        val stack = ArrayDeque<Int>()
        val answer = IntArray(temperatures.size)

        for (i in temperatures.indices) {

            while (stack.isNotEmpty() && temperatures[stack.last()] < temperatures[i]) {

                val colderDayIndex  = stack.removeLast()
                val daysBetween = i - colderDayIndex
                answer[colderDayIndex] = daysBetween

            }

            stack.addLast(i)
        }

        return answer
    }
}