/**
 * to get the area of any window, you just multiply its width by the shortest bar inside it.
 * brute forcing this with nested loops to check every start and end point gives us a O(n2) time.
 * a much better way is using an increasing stack. it's basically the classic 'next smaller element' approach.
 *
 * as we loop through, whenever we hit a shorter bar, it breaks our increasing rule. that shorter bar becomes the right
 * boundary for the bars sitting on the stack. so we start popping those taller bars off. for each popped bar, the new
 * top of the stack acts as its left boundary. we figure out the width by doing (right boundary - left boundary - 1)
 * since both bounds are strictly outside the rectangle. then just update the max area if we found a bigger one.
 *
 * to avoid a separate drain loop at the end, we iterate one extra step past the array and treat that virtual index as
 * a bar of height 0. since 0 is shorter than any real bar, it forces every remaining element off the stack with the
 * array size acting as their right boundary. clean single-loop structure, no duplicated logic.
 *
 * Time: O(n) amortized. every element goes in and comes out of the stack exactly once.
 * Space: O(n) since worst case (like a completely flat or always-increasing graph) we could put all n elements in the
 * stack.
 */
class Solution {
    fun largestRectangleArea(heights: IntArray): Int {

        val stack = ArrayDeque<Int>()
        var maxArea = 0

        for (index in 0..heights.size) {
            val currentHeight = if (index == heights.size) 0 else heights[index]

            /*
             * using > or >= both yield the correct result with the same total work. every bar is pushed and popped
             * exactly once either way, and both produce the same sequence of candidate areas, just at different points
             * in the loop. with >, equal heights stay grouped on the stack and get popped together when a strictly
             * smaller bar arrives. with >=, each duplicate immediately pops its predecessor instead. > is preferred
             * stylistically since grouped popping mirrors the intuition that consecutive equal bars share one rectangle.
             */
            while (stack.isNotEmpty() && heights[stack.last()] > currentHeight) {
                val poppedHeight = heights[stack.removeLast()]
                val width = if (stack.isEmpty()) index else index - stack.last() - 1
                maxArea = maxOf(maxArea, poppedHeight * width)
            }

            stack.addLast(index)
        }

        return maxArea
    }
}