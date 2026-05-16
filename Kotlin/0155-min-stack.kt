/**
 * Time: every operation reads or mutates only the last element of an ArrayDeque, so each runs in amortized O(1).
 *
 * Space: we store one Node holding two Int fields per pushed element, so memory grows linearly with stack size
 * giving O(n).
 */
class MinStack() {

    private val stack = ArrayDeque<Node>()

    fun push(`val`: Int) {

        val currentMin = stack.lastOrNull()?.min?.let { minOf(it, `val`) } ?: `val`

        stack.addLast(Node(`val`, currentMin))
    }

    fun pop() {
        stack.removeLast()
    }

    fun top(): Int {
        return stack.last().value
    }

    fun getMin(): Int {
        return stack.last().min
    }

    private data class Node(val value: Int, val min: Int)

}

/**
 * Time: every operation reads or mutates only the head node with a constant number of field accesses and reference
 * assignments, so each runs in O(1).
 *
 * Space: we store one Node holding two Int fields and one reference per pushed element, so memory grows linearly with
 * stack size giving O(n).
 */
class MinStack() {

    private var head: Node? = null

    fun push(`val`: Int) {

        val currentMin = head?.min?.let { minOf(it, `val`) } ?: `val`

        head = Node(`val`, currentMin, head)
    }

    fun pop() {
        head = head?.next
    }

    fun top(): Int {
        return head!!.value
    }

    fun getMin(): Int {
        return head!!.min
    }

    private class Node(
        val value: Int,
        val min: Int,
        val next: Node?
    )

}

/**
 * Time: push performs a constant number of array writes plus an amortized O(1) resize, while pop, top, and getMin
 * each read or update a single index, so every operation runs in amortized O(1).
 *
 * Space: we store two Int slots per pushed element across the values and mins arrays, so memory grows linearly with
 * stack size giving O(n).
 */
class MinStack() {

    private var values = IntArray(16)
    private var mins = IntArray(16)
    private var size = 0

    fun push(`val`: Int) {
        ensureCapacity()
        values[size] = `val`
        mins[size] = if (size == 0) `val` else minOf(mins[size - 1], `val`)
        size++
    }

    fun pop() {
        size--
    }

    fun top(): Int {
        return values[size - 1]
    }

    fun getMin(): Int {
        return mins[size - 1]
    }

    private fun ensureCapacity() {
        if (size == values.size) {
            values = values.copyOf(values.size * 2)
            mins = mins.copyOf(mins.size * 2)
        }
    }
}