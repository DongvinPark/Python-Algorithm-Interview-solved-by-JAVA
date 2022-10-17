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
//첫 번째 풀이의 실행시간은 21밀리초에 하위 30% 정도의 성능을 기록했습니다.
//리스트에 있는 값들을 전부 ArrayList<Integer>에 담은 후, 자바에 내장된 정렬함수를 사용해서 정렬해줍니다.
//그 후 정렬된 리스트의 값들을 차례로 순회하면서 노드를 만들어 줬습니다.
class Solution {
    public ListNode sortList(ListNode head) {
        //return type is ListNode
        
        if(head == null || head.next == null) return head;
        
        ArrayList<Integer> nums = new ArrayList<>();
        
        while(true){
            nums.add(head.val);
            head = head.next;
            if(head == null){break;}
        }//while
        
        Collections.sort(nums);
        
        ListNode answer = new ListNode();
        
        answer.val = nums.get(0);
        ListNode temp = answer;
        
        for(int i=1; i<nums.size(); i++){
            ListNode nextNode = new ListNode(nums.get(i));
            
            temp.next = nextNode;
            
            temp = nextNode;
            
        }//for
        
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
// 두 번째 풀이는 병합 정렬 로직을 단방향 리스트에 적용하여 풀이하였습니다.
//실행시간은 약간의 편차가 있었지만 첫 번째 풀이와 거의 비슷하거나, 1~3 밀리초 정도 늦게 나왔습니다.
//병합정렬의 작동 원리는 이미 다른 문서들에서 잘 설명돼 있으므로 구글링을 통해 찾아보시는 것을 추천드립니다.
class Solution {
    public ListNode sortList(ListNode head) {
        //return type is ListNode

        if(head == null) return head;

        ListNode temp = head;

        //while 문의 조건문 부분이 temp가 아니라 temp.next라고 설정돼 있는 이유가 있습니다.
        //이 부분이 temp.next 가 아니라 temp라고 설정해 놓을 경우, 해당 루프가 끝난 후의 temp 노드는 리스트의 마지막 노드가 아니라,
        //마지막 노드의 다음 노드 즉, Null을 가리키게 되기 때문입니다.
        while(temp.next != null) { temp = temp.next; }

        //정답을 도출하기 위한 메서드에 입력 리스트의 첫 노드와 마지막 노드를 인자로 전달해주면서, 해당 메서드를 호출해줍니다.
        return mergeSort(head, temp);

    }//main

    //아랫쪽에 있는 mergeSort() 메서드에서 사용할 메서드입니다.
    //mergeSort() 메서드의 어느 부분에서 merge()메서드가 호출이 되는지를 먼저 살펴본 후 관련 설명을 읽어보시는 것을 추천드립니다.
    //이 메서드는 mergeSort()에서 head1, head2 노드로서 표현되는 두 개의 리스트들을 실제로 오름차순으로 정리하는 역할을 합니다.
    public ListNode merge(ListNode start1, ListNode start2){

        //루트 노드와 레스 노드를 따로 만들어 둔 후에 레스 노드가 루트노드를 가리키게 만드는 것이 중요합니다.
        //이렇게 함으로써 루트노드가 앞으로 어떤 식으로 마구 변화하든지에 상관없이, 레스 노드는 항상 while 루프에
        //진입하기 전의 루트 노드가 가리키고 있던 곳을 일정하게 가리키게 할 수 있기 때문입니다.
        //이번 메서드 내에서 루트 노드는 결국 dummy Head 노드의 역할을 하게 되는 것입니다.
        ListNode root = new ListNode(0);
        ListNode res = root;

        //while 루프 내의 조건문은 다음과 같이 해석할 수 있습니다.
        // "스타트1 노드와 스타트2 노드 둘 중 하나라도 null이 아닐 경우 while루프를 지속하라."
        // 이것은 다음의 조건문과 정확히 일치하는 조건입니다. 즉, "두 노드가 모두 널일 때에만 while 루프를 탈출하라."
        //생각해보면, 이것은 매우 당연한 판단입니다. 두 노드중 하나라도 널이 아닐 경우 널이 아닌 노드의 끝까지 탐색을 마쳐야 하기 때문입니다.
        /*
        예를 들어서 스타트1 노드가  1 >> 널  로 구성돼 있고, 
                    스타트2 노드가  2 >> 3 >> 널 로 구성돼 있다고 가정해보겠습니다.

                    이 경우 첫 루프에서는 else if(스타트1 < 스타트 2) 파트를 진입하게 될 것입니다.
                    그리고 두 번째 루프와 세 번째에서는 스타트1 노드가 널인 상태이기 때문에 if(스타트1==null) 파트를 진입하게 될 것입니다.
                    어떤 조건문을 만나게 되든지 간에, 더 작은 값을 가지는 노드 쪽에 대해서 먼저 .next 연산을 통해 다음 노드로 이동하는 것을 확인할 수 있습니다.
                    이러한 방식으로 스타트1, 스타트2 노드를 통해 표현되는 두 개의 리스트의 구성요소들을 정렬합니다.
        */
        while(start1 != null || start2 != null){
            if(start1 == null){
                root.next = start2;
                start2 = start2.next;
            }
            else if(start2 == null){
                root.next = start1;
                start1 = start1.next;
            }
            else if(start1.val < start2.val){
                root.next = start1;
                start1 = start1.next;
            }
            else{
                root.next = start2;
                start2 = start2.next;
            }
            root = root.next;
        }//wh

        return res.next;

    }//func------------


    // 슬로우 노드와 패스트 노드를 활용하여 슬로우 노드를 기준으로 리스트를 2 개로 나눠줍니다.
    //그 후 첫 리스트(startNode ~ slow 까지)와 두 번째 리스트(slow.next(==temp1) ~ endNode) 각각에 대해서 mergeSort()를 재귀호출 해줍니다.
    //그 후, 위에서 정의했었던 merge()메서드를 호출해줍니다.
    //이때 merge() 메서드는 첫 번째 리스트의 첫 노드, 그리고 두 번째 리스트의 첫 노드 이렇게 2 개의 노드를 입력 인자로서 받습니다.
    //mergeSort()는 후위 순회를 이용하고 있음을 알 수 있습니다.
    //재귀호출을 먼저 하고 나서야 이번 노드에서 처리해야 할 일(즉, merge()를 호출하는 것)을 처리하기 때문입니다.
    public ListNode mergeSort(ListNode startNode, ListNode endNode){
        if(startNode == null || startNode.next == null){return startNode;}

        ListNode slow = startNode;
        ListNode fast = startNode.next;

        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }

        ListNode temp1 = slow.next;
        slow.next = null;
        endNode.next = null;

        ListNode head1 = mergeSort(startNode, slow);
        ListNode head2 = mergeSort(temp1, endNode);

        return merge(head1, head2);
    }//func--------------

}//main class
