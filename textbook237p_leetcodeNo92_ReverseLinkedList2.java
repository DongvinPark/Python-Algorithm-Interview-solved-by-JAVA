//실행시간은 0밀리초로 나와서 큰 의미는 없었습니다.
//DS노드 즉, Dummy Start 노드를 만들어서 그 노드가 자신의 다음 노드로 head를 가리키도록 만든 것이 해결의 열쇠었습니다.
//left 또는 right가 시작 노드인가 또는 마지막 노드인가에 따라서 모든 경우의 수를 일일이 처리해줄 경우 코드가 길고 복잡해지며, 실수가 발생할 가능성도 높아지게 됩니다.
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
    public ListNode reverseBetween(ListNode head, int left, int right) {
        //must return start node of the answer list

        //입력 노드가 1개 뿐인 경우를 처리합니다.
        if(head.next == null){return head;}

        //입력 노드의 개수와 상관없이, left와 right가 서로 같다면 전체 입력리스트의 요소들 간의 순서는 바뀌는 것이 없습니다.
        if(left == right){return head;}
        
        //입력 노드의 개수가 2개인데, 여기서 left와 right가 서로 다른 경우를 처리합니다. 이때는 그냥 전체 노드를 뒤집어준 후 처음에 만들어둔 노드를 반환하면 됩니다.
        if(head.next.next == null){
            ListNode answer = new ListNode();
            answer = head.next;
            head.next.next = head;
            head.next = null;
            return answer;
        }

        //더미 스타트 노드를 만든 후, 그 노드가 헤드 노드를 가리키게 해 줍니다. 이렇게 해 줌으로써 left가 헤드 노드와 일치한다고 해도, before left 노드 즉 bl노드를 만드는데 아무 문제가 없습니다.
        //그리고 이는 나중에 l, r, bl, ar 노드들이 올바른 노드를 가리키게 만들어 줄 때 편리하게 작업할 수 있게 해줍니다.
        ListNode DS = new ListNode();
        DS.next = head;

        // left, right, before left, after right 노드들을 만들어줍니다.
        ListNode l = new ListNode();
        ListNode r = new ListNode();
        ListNode bl = new ListNode();
        ListNode ar = new ListNode();

        //앞에서 만든 4개의 노드들이 적절한 노드들을 가리키도록 해줍니다. for 문의 조건을 설정해 줄 때 i<=right로 = 기호를 포함한 것에 유의해야 합니다.
        //만약 입력 노드가 1 > 2 > 3 > 4 >5 이고, left == 2, right == 4 였다면, l, r, ar, bl 노드들은 다음의 노드들을 가리키게 됩니다. DS > 1  >  2 >  3  >  4  >  5
        //                bl    l          r    ar
        ListNode iter = new ListNode();
        iter = DS;
        for(int i=0; i<=right; i++){
            if(i==(left-1)){bl = iter; //System.out.println("bl : " + bl.val);
                           }
            if(i==(left)){l = iter; 
                          //System.out.println("left : " + l.val);
                         }
            if(i==right){
                r = iter;
                //System.out.println("r : " + r.val);
                ar=r.next;
                //System.out.println("ar : " + ar.val);
            }
            iter = iter.next;
        }

        // l 노드와 r 노드 사이 노드들을 뒤집어줍니다. 이로써 노드들 간의 연결 관계는 다음과 같이 표시할 수 있습니다.
        // DS >   1  >  2  <  3  <  4    5
        //       bl     l           r    ar
        reverse(l, r, left, right);

        //그 후 bl이 r을 다음 노드로 가리키게 만들고, l이 ar을 다음 노드로 가리키게 만들면 문제에서 원하는 순서대로 노드 연결구조가 재구성 됩니다.
        //        1  <  2  <  3  <  4  <  1  <  DS
        //       ar     l           r    bl
        bl.next = r;
        l.next = ar;

        //이제 DS 노드의 다음 노드를 정답으로 반환하면 됩니다.
        return DS.next;

    }//main

    //이 메서드가 어떻게 작동하는지에 대한 자세한 원리는 제 깃허브 Repository의 textbook201p_leetcodeNo234_PalindromeLinkedList.java 파일을 참조하시면 됩니다.
    //제 깃허브 Repository 링크입니다.
    //https://github.com/DongvinPark/Python-Algorithm-Interview-solved-by-JAVA
    public static void reverse(ListNode l, ListNode r, int left, int right){
        ListNode cur = l;
        ListNode prev = null;
        for(int i=0; i<right-left+1; i++){
            ListNode temp = cur.next;
            cur.next = prev;
            prev = cur;
            cur = temp;
        }//while
    }//func

}//main class
