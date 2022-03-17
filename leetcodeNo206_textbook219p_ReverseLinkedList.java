//첫 번째 풀이는 while 반복문을 사용한 풀이입니다.
//풀이시간은 0밀리초로 측정되었기에 사실상 떠지는 것이 큰 의미는 없는 것 같습니다.
//이 풀이는 교재 213쪽 리트코드로는 21번 문제인 Merge Two Sorted Lists에서 사용한 방법을 그대로 재사용했습니다. 해당 방법에 대한 풀이는 깃허브 내의 "leetcodeNo21_textbook213p_MergeTwoSortedLists.java"파일을 참고하시면 됩니다.
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
    public ListNode reverseList(ListNode head) {
        //must return the start ListNode of reversed input list
        
        if(head == null){ return head; }
        
        ListNode cur = head;
        ListNode prev = null;
        while(cur!=null){
            ListNode temp = cur.next;
            cur.next = prev;
            prev = cur;
            cur = temp;
        }//while
        
        return prev;
        
    }//main
}//main class



//두 번째 풀이는 재귀를 사용했습니다.
//재귀는 for나 while 같은 반복문보다 설계하기가 더 까다로운 것 같습니다.
//실행시간은 첫 번재 풀이와 동일하게 0밀리초로 측정되었기에 큰 의미는 없는 것 같습니다.
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
    public ListNode reverseList(ListNode head) {
        //must return the start ListNode of reversed input list
        
        return reverse(head, null);
        
        //재귀를 반복할수록 head 노드가 가리키는 노드가 입력 리스트의 마지막 요소방향으로 한 칸씩 이동하게 됩니다.
        
        
    }//main
    
    public static ListNode reverse(ListNode node, ListNode prev){
        if(node == null){ return prev; }//이 부분이 존재하기에 재귀 호출이 중단되고 최종적 정답인 prev 노드가 리턴됩니다.

        ListNode temp = node.next;//일단 현재 노드의 다음 노드를 가리키는 임시 노드를 새로 만들어둡니다.
        node.next = prev;//현재 노드가 자신의 앞에 있던 노드를 자신의 다음 노드로 설정하게 해줍니다.
        return reverse(temp, node);// 그 후, 앞서 만들어뒀던 임시노드와 현재 노드의 순서를 바꾸는 reverse()메서드를 재귀적으로 호출해줍니다.
        
    }//func
}//main class
