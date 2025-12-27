/**
 * Min-Heap
 * Time Complexity: O(N log k) Heap insertion and deletion operations take O(log k) for each of the N total nodes.
 * Space Complexity: O(k) PriorityQueue maintains at most k nodes one from each list at any time.
 */
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode mergeKLists(ListNode[] lists) {

        PriorityQueue<ListNode> queue = new PriorityQueue<>((node1, node2) -> node1.val - node2.val);


        ListNode preHead = new ListNode(0);
        ListNode current = preHead;

        for (ListNode node: lists) {

            if (node != null) {
                queue.offer(node);
            }

        }

        while (!queue.isEmpty()) {

            ListNode min = queue.poll();

            current.next = min;
            current = current.next;

            if (min.next != null) {
                queue.offer(min.next);
            }

        }

        return preHead.next;
    }
}