/**
 * Time: O(N) - Each node is visited once
 * Space: O(1) - Only a fixed number of pointers used
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
    public ListNode swapPairs(ListNode head) {

        ListNode preHead = new ListNode(0, head);

        ListNode prev = preHead;
        ListNode node1 = head;

        while (node1 != null && node1.next != null) {

            ListNode node2 = node1.next;
            ListNode nextStart = node2.next;

            prev.next = node2;
            node2.next = node1;
            node1.next = nextStart;

            prev = node1;
            node1 = nextStart;
        }

        return preHead.next;
    }
}