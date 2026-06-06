/**
 * HashMap Two Pass
 *
 * First pass: create a bare copy of every node and map original -> copy. Second pass: wire each copy's next and random
 * by looking up the corresponding originals in the map. The map answers "what is this node's copy?", so random pointing
 * forward or backward no longer matters; every copy already exists.
 *
 * Time:  O(n). Two linear passes, with average O(1) map operations.
 * Space: O(n). The map holds one entry per node.
 */
class Solution {
    fun copyRandomList(head: Node?): Node? {
        var current = head
        val originalToCopy = mutableMapOf<Node, Node>()

        while (current != null) {
            originalToCopy[current] = Node(current.`val`)
            current = current.next
        }

        current = head
        while (current != null) {
            val copy = originalToCopy[current]!!
            copy.next = current.next?.let { originalToCopy[it] }
            copy.random = current.random?.let { originalToCopy[it] }
            current = current.next
        }

        return head?.let { originalToCopy[it] }
    }
}

/**
 * HashMap Single Pass
 *
 * Same idea as the two-pass version, but getOrPut creates a copy on demand instead of pre-building them all. So in one
 * pass we can resolve the current node, its next, and its random: whichever copy is missing is created right then.
 *
 * Time:  O(n). One linear pass, with average O(1) map operations.
 * Space: O(n). The map holds one entry per node.
 */
class Solution {
    fun copyRandomList(head: Node?): Node? {
        val originalToCopy = mutableMapOf<Node, Node>()

        fun getCopy(original: Node?): Node? =
            original?.let { originalToCopy.getOrPut(it) { Node(it.`val`) } }

        var current = head
        while (current != null) {
            val copy = getCopy(current)!!
            copy.next = getCopy(current.next)
            copy.random = getCopy(current.random)
            current = current.next
        }

        return getCopy(head)
    }
}

/**
 * Interleaving
 *
 * Instead of a map, we weave each copy directly after its original node, so that every node's copy becomes its own next.
 * This lets us find any node's copy structurally, without storing the mapping anywhere. First we build this woven list.
 * Then we wire each copy's random pointer, which is simply the next of the original's random, since every node's copy
 * sits right behind it. Finally we unweave the two lists, restoring the original's next pointers while extracting the
 * copy into its own chain. Restoring the original is essential; leaving the lists woven together would corrupt the input.
 *
 * Tradeoff: this trades O(1) space for temporarily mutating the input. During the first two passes the original list is
 * interleaved with the copies, so the input is not in a valid state until the final unweave restores it. That makes this
 * approach unsafe if the list is shared, accessed concurrently, or constrained to be read-only. The map-based solutions
 * never touch the input and stay thread-safe, at the cost of O(n) extra space.
 *
 * Time:  O(n). Three linear passes.
 * Space: O(1). Only existing nodes are relinked; no extra structure is used.
 */
class Solution {
    fun copyRandomList(head: Node?): Node? {
        if (head == null) return null

        var current: Node? = head
        while (current != null) {
            val copy = Node(current.`val`)
            copy.next = current.next
            current.next = copy
            current = copy.next
        }

        current = head
        while (current != null) {
            current.next?.random = current.random?.next
            current = current.next?.next
        }

        val dummy = Node(0)
        var copyTail: Node = dummy
        current = head
        while (current != null) {
            val copy = current.next!!
            current.next = copy.next
            copyTail.next = copy
            copyTail = copy
            current = current.next
        }

        return dummy.next
    }
}