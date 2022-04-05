//첫 번째 풀이는 ArrayList<Integer> 타입을 이용해 풀이했습니다.
//실행시간은 16밀리초였고, 이는 하위 약 22%의 성능으로 측정되었습니다.
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
        //return type is ListNode
        ArrayList<Integer> AsList = new ArrayList<>();
        
        ListNode answer = new ListNode();
        
        //입력 리스트가 빈 리스트인 경우를 처리해 줍니다.
        if(lists.length == 0){
            answer = null;
            return answer;
        }
        
        //입력 리스트에 있는 모든 노드들의 값을 ArrayList에 추가해줍니다.
        for(int i=0; i<lists.length; i++){
            ListNode start = new ListNode();
                start = lists[i];
            while(start!=null){
                AsList.add(start.val);
                start = start.next;
            }//while
            
        }//outer for
        
        Collections.sort(AsList);
        
        //ArrayList를 sort한 후, 그 값에 따라서 노드들을 만들고 정답을 반환합니다.
        ListNode begin = new ListNode();
        answer = begin;
        for(int j : AsList){
            ListNode chain = new ListNode(j);
            begin.next = chain;
            begin = begin.next;
        }
        
        return answer.next;
        
    }//main
}//main class






//두 번재 풀이는 자바 언어에서 네이티브로 지원하는 PriorityQueue 자료형을 활용했습니다.
//코드 길이가 더 짧아지고, 실행시간이 14밀리초로 측정되며 앞선 풀이보다 2밀리초 줄어들었지만 큰 차이는 아니었습니다.
//원인을 분석해본 결과, ListNode를 만드는 new 연산이 상당한 시간을 잡아먹는 것으로 생각됩니다.
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
        //return type is ListNode
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        
        ListNode answer = new ListNode();
        
        if(lists.length == 0){
            answer = null;
            return answer;
        }
        
        for(int i=0; i<lists.length; i++){
            ListNode start = new ListNode();
                start = lists[i];
            while(start!=null){
                pq.add(start.val);
                start = start.next;
            }//while
            
        }//outer for
        
        ListNode begin = new ListNode();
        answer = begin;
        while(!pq.isEmpty()){
            ListNode chain = new ListNode(pq.remove());
            begin.next = chain;
            begin = begin.next;
        }//while
        
        return answer.next;
        
    }//main
}//main class





//두 번째 풀이와 동일한 논리를 사용하지만, 실행시간은 $~8밀리초로 나왔으며 첫 번째 및 두 번째 풀이보다 거의 절반 가까이 가까이 감소했습니다.
//이는 상위 약 50%에서 98% 정도에 달하는 성능입니다. 1밀리초의 간격에 워낙 많은 정답 제출자들이 몰려있다보니, 10밀리초 이내로 측정될 경우 사실상 큰 의미는 없는 것 같습니다.
//이는 ListNode를 만드는 new 연산을 최소화한 것에서 원인을 찾을 수 있습니다.
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
        //return type is ListNode
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        
        ListNode answer = new ListNode();
        
        if(lists.length == 0){
            answer = null;
            return answer;
        }
        
        for(ListNode head : lists){
            while(head != null){
                pq.add(head.val);
                head = head.next;
            }
        }
        
        ListNode begin = new ListNode();
        answer = begin;
        while(!pq.isEmpty()){
            begin.next = new ListNode(pq.remove());
            begin = begin.next;
        }//while
        
        return answer.next;
        
    }//main
}//main class
