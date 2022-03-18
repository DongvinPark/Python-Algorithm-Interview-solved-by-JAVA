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
        //must return start node of answer list.

        ListNode start = new ListNode();
        ListNode answer = start;

        while(true){
            //System.out.println("\n\n while start");

            int l1v = l1.val;
            int l2v = l2.val;

            int sum = l1v + l2v;
            //System.out.println("sum : " + sum);

            int del = sum/10;
            //System.out.println("del : " + del);
            int left = sum%10;
            //System.out.println("left : " + left);

            if(l1.next != null && l2.next != null){
                //System.out.println("both not null");
                l1.next.val += del;
                start.val = left;
                ListNode nextStart = new ListNode();
                start.next = nextStart;
                start = start.next;

                l1 = l1.next;
                l2 = l2.next;
                continue;
            }

            if(l1.next != null && l2.next == null){
                //System.out.println("l2 end enter");
                l1.next.val += del;
                start.val = left;
                ListNode nextStart = new ListNode();
                start.next = nextStart;
                start = start.next;

                ListNode newN = new ListNode();
                l2.next = newN;
                l1 = l1.next;
                l2 = l2.next;
                continue;
            }//outer if

            if(l1.next == null && l2.next != null){
                //System.out.println("l1 null enter");
                l2.next.val += del;
                start.val = left;
                ListNode nextStart = new ListNode();
                start.next = nextStart;
                start = start.next;

                ListNode newN = new ListNode();
                l1.next = newN;
                l1 = l1.next;
                l2 = l2.next;
                continue;
            }//out if

            if(l1.next == null && l2.next == null){
                //System.out.println("end enter");
                if(sum < 10){
                    System.out.println("if enter");
                    start.val = left; break;
                }
                else{
                    System.out.println("else enter");
                    start.val = left;

                    ListNode newN = new ListNode();
                    newN.val = del;
                    start.next = newN;
                    break;
                }
            }//out if

        }//while

        return answer;

    }//main
}//main class







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
        //must return start node of the answer list
        
        ListNode dummyHead = new ListNode(0);
        
        ListNode p=l1, q=l2, curr = dummyHead;
        
        int carry = 0;
        
        while(true)
        {
            if(p==null && q == null){break;}
            int x = (p != null) ? p.val:0;
            int y = (q != null) ? q.val:0;
            
            int sum = x + y + carry;
            carry = sum/10;
            
            curr.next = new ListNode(sum%10);
            if(p != null) { p = p.next; }
            if(q != null) { q = q.next; }
            curr = curr.next;
        }//while
        
        if (carry > 0)
        {
            curr.next = new ListNode(carry);
        }//if
        
        return dummyHead.next;
        
    }//main
}//main class
