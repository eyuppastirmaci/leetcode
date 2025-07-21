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
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode preHead = new ListNode(0);
        ListNode current = preHead;
        int carry = 0;
        
        while (l1 != null || l2 != null || carry != 0) {
            carry += getValue(l1) + getValue(l2);
            current.next = new ListNode(carry % 10);
            current = current.next;
            carry /= 10;
            
            l1 = advance(l1);
            l2 = advance(l2);
        }
        
        return preHead.next;
    }
    
    private int getValue(ListNode node) {
        return node != null ? node.val : 0;
    }
    
    private ListNode advance(ListNode node) {
        return node != null ? node.next : null;
    }
}