//첫 번째 풀이는 입력 리스트들 전부를 ArrayList<Integer>에 저장한 후, 이 리스트를 정렬한 후, singlry linkd list로 바꾸는 해답입니다.
//실행시간은 2밀리초였지만, 자바 제출자 중 하위 6%로 100명 중 94등에 해당하는 성능이었습니다.
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
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        //must return start Node of sorted singly linked list
        
        if(list1 == null && list2 == null){
            return list1;
        }
        
        ArrayList<Integer> sortedAL = new ArrayList();
        
        ListNode node1 = new ListNode();
        node1 = list1;      
        while(node1!=null){
            sortedAL.add(node1.val);
            node1 = node1.next;
        }
        
        ListNode node2 = new ListNode();
        node2 = list2;        
        while(node2!=null){
            sortedAL.add(node2.val);
            node2 = node2.next;
        }
        
        Collections.sort(sortedAL);
        
        ListNode answer = new ListNode();
        
        
        if(sortedAL.size()==1){ 
            answer.val = sortedAL.get(0);
            return answer;
        }
        
        answer.val = sortedAL.get(0);
        ListNode realAnswer = answer;
        for(int i=0; i<sortedAL.size()-1; i++){
            ListNode nextNode = new ListNode();
            nextNode.val = sortedAL.get(i+1);
            answer.next = nextNode;
            answer = nextNode;
        }
        
        return realAnswer;
        
    }//main
}//main class




//두 번재 풀이는 재귀호출을 이용한 풀이를 가져와 봤습니다. 실행시간이 0 밀리초로 나와서, 리트코드 사이트에서 측정 가능한 최소 시간인 1밀리초보다 적은 시간이 걸린 듯 합니다.
//성능 수치도 상위 0%, 100명 중 약 1등으로 나왔습니다.
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
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {

        //일단 최초로 함수를 호출하면, 두 개의 입력 리스트의 첫 시작 노드가 널인지 검사합니다.
        if(list1==null) { return list2; }
        if(list2==null) { return list1; }
        
        //if와 else로 나누어진 것을 확인할 수 있습니다.
        //이 과정을 거침으로 인해서 두 입력 노드가 갖고 있는 값 사이의 대소비교가 완료됩니다.

        if(list1.val <= list2.val) {
            //if 파트는 왼쪽 노드보다 오른쪽 노드가 크거나 같은 상황입니다.
            list1.next = mergeTwoLists(list1.next, list2);

            //그 다음 동작의 뜻을 이해하는 것이 중요합니다.
            //if 파트에 진입함으로써 오른쪽 노드가 왼쪽 노드보다 크거나 같다는 것이 확인되면, 작은 쪽으로 판명된 왼쪽 노드의 다음 노드가 무엇이 되어야 하는 지를 설정해 줍니다. 이렇게 해줌으로써 리스트를 대소 비교 순서에 맞게 이어나갈 수 있습니다. 이를 수행하기 위해서 mergeTwoLists()를 재귀적으로 호출합니다.
            //재귀적으로 호출하면서 인자를 어떻게 전달하는 지도 유심히 살펴봐야 합니다.
            //[이번 비교에서 더 작은 것으로 판명됐던 왼쪽 노드의 다음 노드] 와 [이번 비교에서 더 크거나 같은 것으로 판명된 오른쪽 노드(즉 list2)] 사이의 비교를 위한 것입니다. 이것의 비교를 통해서 list1의 다음 노드가 무엇인지 알 수 있습니다.
            //
            return list1;
        }//if

        else {
            //else 파트도 if파트와 비슷한 논리로 작동합니다.
            //대신 이번에는 오른쪽 노드가 왼쪽 노드보다 작은 것으로 판명되었기 때문에, 오른쪽 노드의 다음 노드로 어떤 노드가 와야 하는지를 결정해야 합니다.
            list2.next = mergeTwoLists(list1, list2.next);
            //따라서 재귀적인 호출을 통해 [이번 비교에서 큰 것으로 판명된 왼쪽 노드]와 [이번 비교에서 더 작은 것으로 판명된 노드(즉 list2)의 다음 노드] 간의 비교와 둘 중  더 작은 요소의 반환이 이루어집니다.
            return list2;
        }//else

        //이러한 재귀적인 호출은 최초 입력 리스트 두 개 중 하나가 null이 될 때까지 계속됩니다.
        //그리고 마침내 재귀적인 호출이 끝나면 마지막으로 호출된 mergeTwoLists()가 직전에 자신을 호출했던 mergeTwoLists()에게 대소비교에 맞는 노드를 반환하는 과정이 연쇄적으로 이루어지고, 결국 두 리스트의 병합 및 정렬이 완료됩니다.
        //이러한 과정은 직접 손으로 그려가면서 함수 사이의 호출 관계와 반환 값들을 추적하는 것이 가장 이해가 빠른 것 같습니다.

    }//main
}//main class
//코드는 간단하지만, 작동과정을 추적하는 것은 상당히 어려운 편이었습니다.
