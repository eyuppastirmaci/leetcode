/**
 * Recursive
 *
 * The key step is reversing one link per stack frame:
 *   head.next!!.next = head  -> the next node now points back to current.
 *   head.next = null         -> break the old forward link to avoid a cycle.
 *
 * We pass newHead up unchanged through every frame so the original tail is returned as the final head.
 *
 * Time:  O(n). We visit each node exactly once.
 * Space: O(n). The recursion uses one stack frame per node, so the call stack grows linearly with the list length.
 * This is the main trade-off versus the iterative version.
 */
class Solution {
    fun reverseList(head: ListNode?): ListNode? {
        if (head?.next == null) {
            return head
        }

        val newHead = reverseList(head.next)
        head.next!!.next = head
        head.next = null

        return newHead
    }
}

/**
 * Iterative
 *
 * On each step we reverse one link, then advance all pointers forward. When curr becomes null we have consumed every
 * node, and prev points to the original tail, which is the new head.
 *
 * Time:  O(n). We visit each node exactly once.
 * Space: O(1). We only use a fixed number of pointers, regardless of list length. This is why the iterative version is
 * preferred for long lists.
 */
class Solution {
    fun reverseList(head: ListNode?): ListNode? {
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
}