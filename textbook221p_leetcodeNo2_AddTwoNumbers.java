//첫 번째 풀이입니다. 실행시간은 9밀리초였고, 이는 하위 5%로 100명중 95등 정도입니다. 비효율적인 풀이입니다.
//이번 문제는 초등학교에서 배운 덧셈 연산의 과정을 효율적으로 구현하는 것이 핵심이었습니다.
//첫 번째 풀이의 경우, 덧셈이 수행되는 자리수마다 새로운 노드를 만들어서 정답을 저장하고 있습니다.
//덧셈이 수행되면서 값이 없는 상태, 예를 들면 (2 > 5 > 8) + (9) 이런 상태에서, 두 번째 자릿수와 세 번째 자릿수를 계산할 때 9 부분에 이어지는 숫자가 없을 경우,  9 > 0 > 0 이런 식으로 노드를 새로 채워주고 있습니다.
//또한 연산 과정에서 Null과 관련하여 발생하는 모든 경우들을 일일이 if, else 등의 구문으로 전부 잡아내며서 풀이하고 있기 때문에 코드가 복잡하고 길어졌습니다.
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






//두 번째 풀이의 실행시간은 3밀리초이고, 이는 상위 27%로 100명 중 약 27등 정도입니다.
//삼항 연산자인 ? : 구문을 활용하여 Null 처리를 간단하고 명료하게 처리하고 있습니다.
//게다가 dummyHead를 도입하고, 자릿수 계산을 마칠 때마다 dummyHead에 (sum%10)을 값으로 갖는 노드를 새로 만들어서 이어 붙이는 방식으로 정답을 구성하고 있습니다.
//null 처리를 간단명료하게 하고, dummyHead에 계산 결과를 이어 붙이는 방법을 사용함으로써 if, else 경우의 수를 처리하는 일 없이 코드를 짧게 만들 수 있었습니다.
//그리고 마지막의 if 구문을 통해서 마지막 자릿수의 계산 결과가 10 이상인 경우도 적절히 처리하고 있습니다.
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
