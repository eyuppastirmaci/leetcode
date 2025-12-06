/**
 * Two Pointers (Ahead-Behind)
 * Time Complexity: O(n) single pass through the list; ahead and behind pointers traverse at most n nodes.
 * Space Complexity: O(1) only a constant number of pointers are used regardless of input size.
 */
/**
 * Example:
 * var li = ListNode(5)
 * var v = li.`val`
 * Definition for singly-linked list.
 * class ListNode(var `val`: Int) {
 *     var next: ListNode? = null
 * }
 */
class Solution {
    fun removeNthFromEnd(head: ListNode?, n: Int): ListNode? {
        val preHead = ListNode(0)
        preHead.next = head

        var ahead: ListNode? = preHead
        var behind: ListNode? = preHead

        repeat (n + 1) {
            ahead = ahead?.next
        }

        while (ahead != null) {
            ahead = ahead?.next
            behind = behind?.next
        }

        behind?.next = behind?.next?.next

        return preHead.next
    }
}