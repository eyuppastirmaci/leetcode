/**
 * Floyd's Tortoise and Hare
 *
 * While traversing the linked list, one possible approach is to store the values we have seen in a Set.
 * However, this would not be reliable because different nodes can have the same value. Seeing the same value again
 * does not necessarily mean that we have visited the same node before.
 *
 * Instead of storing node values, we could store the node references themselves in a Set. In that case, if we encounter
 * the same node reference again, we can conclude that there is a cycle in the linked list. This approach works correctly,
 * but it requires extra memory that grows linearly with the input size.
 *
 * To solve the problem with constant space complexity, we use Floyd's Tortoise and Hare algorithm. We maintain two
 * pointers: slow and fast. The slow pointer moves one node at a time, while the fast pointer moves two nodes at a time.
 *
 * If the linked list has a cycle, the fast pointer will eventually catch up to the slow pointer inside the cycle, meaning
 * both pointers will point to the same node reference. If there is no cycle, the fast pointer will eventually reach null,
 * which means the linked list has an end.
 *
 * The reason the fast pointer is guaranteed to meet the slow pointer (rather than skip over it) is that, once both
 * pointers are inside the cycle, the gap between them decreases by exactly one node on every iteration: the fast pointer
 * moves two steps and the slow pointer moves one step, so the distance shrinks by one each time. Since the gap decreases
 * by one and can never jump past zero, it will eventually become zero, at which point both pointers land on the same node.
 *
 * Time: The time complexity is O(n), where n is the number of nodes in the linked list. In the worst case, each pointer
 * visits a linear number of nodes before either detecting a cycle or reaching the end of the list.
 *
 * Space: The space complexity is O(1), because we only use two pointers regardless of the input size. We do not use any
 * extra data structure such as a Set, HashMap, or ArrayList.
 */
class Solution {
    fun hasCycle(head: ListNode?): Boolean {
        var slow = head
        var fast = head

        while (fast?.next != null) {
            slow = slow?.next
            fast = fast?.next?.next

            if (slow === fast) {
                return true
            }
        }

        return false
    }
}