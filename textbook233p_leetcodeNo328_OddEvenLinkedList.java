//실행시간은 1밀리초로 나왔습니다. 하지만 리트코드에 제출된 대부분의 풀이가 0밀리초로 측정된 것들이어서 하위 23%의 선능을 기록하는 것으로 평가되었습니다.
//이번 문제는 문제에서 제시하는 규칙을 정확히 이해하는 것이 필요합니다.
//홀수 노드 다음에 짝수 노드가 이어지도록 연결리스트를 재구성하라는 것이 요구조건인데, 자칫 잘못하면 홀수 리스트를 홀수 번째 노드가 아닌, 홀수의 값을 갖고 있는 노드로 오해할 수 있습니다. 노드의 값을 기준으로 하는 것이 아니라, 몇 번째 노드인지만 중요합니다.
//그래서 1 > 2 > 4 > 5 > 9 > 7 이런 노드가 있다면, 1,3,5번째인 1 > 4 > 9 가 앞에 나오게 하고, 2,4,6번째 노드인 2 > 5 > 7이 뒤에 나오게 해서 최종적으로는 1 > 4 > 9 > 2 > 5 > 7과 같은 순서로 노드 순서를 조작해줘야 합니다.
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
    public ListNode oddEvenList(ListNode head) {
        //must return start node of the answer list
        
        //입력 노드가 null이거나, 1개 또는 2개 값만 가지고 있는 경우엔 head를 그대로 리턴하면 됩니다.
        if(head == null || head.next == null || head.next.next == null){return head;}
        
        ListNode Answer = head;//정답으로서 반환할 노드를 미리 만들어둬야 합니다. 왜냐하면 head는 아래의 루프가 지속되는 동안 가리키는 위치가 계속 변하기 때문입니다.
        ListNode j = head.next;//짝수번째 노드를 연결해나갈 때 사용할 노드를 루프 밖에서 미리 만들어줍니다.
        ListNode FirstJ = head.next;//홀수번째 노드와 짝수번째 노드 끼리 연결하는 작업을 끝내고 나면, [홀수번째 노드들 중 마지막 요소의 노드]와 [짝수번째 노드의 첫 번째 노드인 FirstJ 노드] 끼리 서로 연결을 해 줘야 합니다. 그렇게 해주기 위해서 짝수 번째 노드 중 첫 번째 노드인 head.next를 가리키는 FirstJ 노드를 루프 밖에서 만들어줍니다.
        
        while(true){
            head.next = head.next.next;//홀수번째 노드가 다음 홀수 번째 노드를 자신의 다음 노드로 가리키게 해 줍니다.
            j.next = j.next.next;//짝수번째 노드가 다음 짝수 번째 노드를 자신의 다음 노드로 가리키게 해 줍니다.
            head = head.next;//다음 루프 실행을 위해, 현재 홀수 번째 노드의 위치를 앞에서 설정한 다음 홀수 번째 노드를 가리키게 해줍니다.
            j = j.next;//다음 루프 실행을 위해, 현재 짝수번째 노드의 위치를 앞에서 설정한 다음 짝수번째 노드를 가리키게 해줍니다.

            //루프를 돌 수 있느 만큼 다 돈 후, 실제 정답 리스트를 만들어내기 위한 처리를 하는 부분입니다.
            //if 부분은 최초에 입력된 노드의 개수가 홀수 개일 때(3, 5, 7개 등등)를 처리합니다.
            //이 때는 바로 윗 줄의 j = j.next;를 통해서 홀수 번째 노드들 끼리 묶어 놓은 리스트의 마지막 요소가 자신의 다음 노드로 Null을 가리키고 있기 때문에 head.next = FirstJ;를 통해서 짝수번째 노드들을 묶어 놓은 리스트의 마지막 요소가 자신의 다음 노드로 FirstJ 노드를 가리키게 해주고 루프를 탈출하면 됩니다.
            if(j == null){
                head.next = FirstJ;
                break;
            }

            //이 부분은 처음에 입력된 리스트의 노드 개수가 짝수(4,6,8 개 등등)일 경우를 처리하는 부분 입니다.
            //이 부분은 그냥 else로만 작성하면, 입력 노드가 1 > 2 >3 > 4 > 5 일때 1 > 3 > 2 > 4와 같이 5를 빼먹은 결과를 제출하게 됩니다. 이것은 2의 다음 노드로 4를 연결시키는 작업을 j 노드를 통해 마친 후, j=j.next;가 실행될 때 j가 4를 가리키게 되면서 null이 아니게 되므로 루프를 한 번 더 돌아야 하는데 그 전에 else문에 진입해 버리기 때문에 발생하는 문제입니다.
            //이러한 문제는 else if를 사용해줌으로써 예방할 수 있습니다. else if 부분에 진입한 후 마지막 짝수번째 노드가 첫 홀수번째 노드를 자신의 다음 노드로 연결하게 지목하고, j는 이미 짝수번째 노드 중 마지막 노드를 가리키고 있으므로 j.next = null;로 처리해준 후 루프를 탈출합니다.
            else if(j.next == null){
                head.next = FirstJ;
                j.next = null;
                break;
            }
        }//while
        
        //리스트가 완성되었으므로 맨 처음에 미리 만들어두었던 Answer 노드를 반환합니다.
        return Answer;
        
    }//main
}//main class
