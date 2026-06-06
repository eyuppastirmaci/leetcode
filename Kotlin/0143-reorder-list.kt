/**
 * The reordered list starts with the first node, then the last node, then the second node, then the second to last node,
 * then the third node, then the third to last node, and so on. In other words it interleaves the first half read from
 * front to back with the second half read from back to front. This naturally breaks down into three steps.
 *
 * Step 1. Find the middle using Floyd's Tortoise and Hare. We maintain two pointers. The slow pointer moves one node at
 * a time, while the fast pointer moves two nodes at a time. By stopping while fast.next.next is not null, the slow
 * pointer lands on the last node of the first half. We then take slow.next as the start of the second half and set
 * slow.next to null, which cleanly splits the list into two separate halves. Splitting here is essential.
 * Without it the first half would still be linked to the second half and the merge step could form a cycle.
 *
 * Step 2. Reverse the second half in place. Since this is a singly linked list, we cannot walk backwards to read the
 * second half from its tail. Instead we reverse it in place using the classic prev, curr and next trio, which gives us
 * the second half ordered back to front while using no extra space.
 *
 * Step 3. Merge the two halves by alternating nodes. We weave one node from the first half and one node from the
 * reversed second half, repeating until the second half is exhausted. Because the first half is always equal in length
 * to the second half or exactly one node longer, the first half's next pointer can become null on the final iteration.
 * We break at that point to avoid a null dereference, leaving the last node's next correctly pointing to null.
 *
 * Time: O(n), where n is the number of nodes. Finding the middle, reversing the second half, and merging each traverse
 * a linear portion of the list, so the total work is linear.
 *
 * Space: O(1). All the work is done by relinking existing nodes in place. We only use a constant number of pointers.
 */
class Solution {
    fun reorderList(head: ListNode?): Unit {
        val headNode = head ?: return
        if (headNode.next == null) return

        val mid = findMiddle(headNode)
        val secondStart = mid.next
        mid.next = null

        val secondReversed = reverse(secondStart)

        merge(headNode, secondReversed)
    }

    private fun findMiddle(head: ListNode): ListNode {
        var slow: ListNode = head
        var fast: ListNode? = head

        while (fast?.next?.next != null) {
            slow = slow.next!!
            fast = fast.next!!.next
        }

        return slow
    }

    private fun reverse(head: ListNode?): ListNode? {
        var prev: ListNode? = null
        var curr = head

        while (curr != null) {
            val next = curr.next
            curr.next = prev
            prev = curr
            curr = next
        }

        return prev
    }

    private fun merge(first: ListNode, second: ListNode?) {
        var f: ListNode = first
        var s: ListNode? = second

        while (s != null) {
            val fNext = f.next
            val sNext = s.next
            f.next = s
            s.next = fNext
            f = fNext ?: break
            s = sNext
        }
    }
}