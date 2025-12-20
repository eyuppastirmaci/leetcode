/**
 * Iterative
 * Time Complexity: O(n + m) iterate through both lists once, where n and m are the lengths of the lists.
 * Space Complexity: O(1) constant extra space; pointers are rearranged without allocating new nodes.
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
    fun mergeTwoLists(list1: ListNode?, list2: ListNode?): ListNode? {
        val preHead = ListNode(0)

        var current = preHead

        // Use mutable pointers for traversal since inputs are read-only.
        var l1 = list1
        var l2 = list2

        while (l1 != null && l2 != null) {
            if (l1.`val` <= l2.`val`) {
                current.next = l1
                l1 = l1.next
            } else {
                current.next = l2
                l2 = l2.next
            }
            current = current.next!!
        }

        // Attach the remaining nodes from the non-empty list.
        current.next = l1 ?: l2

        return preHead.next
    }
}