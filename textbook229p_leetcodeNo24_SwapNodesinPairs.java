//실행시간은 0밀리초로 나와서 큰 의미는 없습니다.
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
        //must return start node of the answer list
        
        //입력 노드가 null이거나, 노드가 1개 뿐일 때를 처리해줍니다.
        if(head == null || head.next == null){return head;}
        
        //while루프의 모든 경우에 대해서 동일한 반복 구조를 적용시키기 위해서, dumHead라는 노드를 새로 만들어서 임시로 dumHead.next = head;로 연결시켜 주고, prev가 dumHead를 가리키게 합니다.
        ListNode dumHead = new ListNode();
        dumHead.next = head;
        
        ListNode prev = new ListNode();
        
        prev = dumHead;
        
        while(head != null){
            //입력 노드의 개수가 1 > 2 > 3과 같이 홀수 개일 경우를 처리하기 위한 구문입니다.
            if(head.next == null){break;}

            //입력 노드가 1 > 2 > 3 > 4 인 경우에 대해 생각해보겠습니다.
            //앞에서 언급한 대로, dumHead를 이용해주기 때문에 루프 실행 전의 노드 구조는 {DH} > (1) > 2 > 3 > 4 가 됩니다.
            //                                                            {p}   (h) 이 부분은 p가 DH를, h가 1을 가리키고 있다는 뜻입니다.
            prev.next = head.next; // p가 자신의 다음 노드로 2를 가리키게 해 줍니다.
            ListNode temp = head.next.next;//헤드 노드의 다음 다음 노드 부분을 가리키는 새로운 노드 temp를 정의해줍니다.
            head.next.next = head;//[헤드 노드의 다음 노드인 2 노드]가 자신의 다음 노드로 헤드 노드(즉, 1 노드)를 가리키게 해 줍니다.
            head.next = temp;//이번엔 head 노드가 자신의 다음 노드로, 앞에서 미리 만들어 두었던 temp 노드(즉, 3 노드)를 가리키게 해줍니다.
            prev = head;//다음 루프 실행을 위해 prev가 현재의 Head를 가리키게 해 줍니다.
            head = temp;//다음 루프의 실행을 위해 현재의 헤드 노드가 미리 만들어 놓았던 temp 노드를 가리키게 해줍니다.
        }//while
        
        //루프를 탈출 한 후, dumHead노드의 다음 노드를 정답으로서 반환합니다.
        return dumHead.next;
        
    }//main
}//main class





//실행시간은 0밀리초로 나와서 큰 의미는 없습니다.
//첫 번째 풀이에 비해서 dumHead 노드를 만들 필요도 없고, temp 노드도 만들 필요 없기 때문에 코드가 더 짧아진다는 장점이 있습니다.
//하지만 재귀호출의 작동과정을 머릿속에서 그려가면서 코드를 짜는 것이 쉽지 않아서 더 난이도가 높은 풀이방법이라고 할 수 있습니다.
//재귀호출 구조를 파악할 때는 눈으로만 읽지 말고, 손으로 호출 및 반환 과정을 직접 그려가면서 이해하는 것이 가장 빠른 것 같습니다.
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
        //must return start node of answer list.
        //System.out.println("\nswapPairs called");

        if (head != null && head.next != null){

            //현재 헤드 노드의 다음 노드를 가리키는 p 노드를 새로 정의해 줍니다.
            ListNode p = head.next;
            
            //p의 다음 노드를 인자로 전달하면서 자신을 재귀호출합니다. [재귀호출된 결과 노드]를 현재의 헤드 노드가 자신의 다음 노드로 가리키도록 해줍니다.
            head.next = swapPairs(p.next);

            //p가 현재의 헤드노드를 가리키게 함으로써 swap 해 줍니다.
            p.next = head;
            
            return p;
        }

        //재귀 호출 끝에 입력 노드의 마지막 노드에 도달한 경우, 그 마지막 노드가 리턴 됩니다.
        return head;

    }//main
}//main class
