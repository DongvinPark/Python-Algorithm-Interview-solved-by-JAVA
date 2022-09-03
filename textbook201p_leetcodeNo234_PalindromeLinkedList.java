//첫 번째 풀이는 ArrayList를 이용해서 인덱스를 직접 비교하는 방법으로 풀이했습니다.
//실행시간은 7밀리초였고, 이는 상위 약 37%의 성능으로 100명중 37등 정도입니다.
//이번 문제는 Single 연결리스트를 직접 구현하라는 문제는 아닙니다. 단, 노드의 정의를 제공해줌으로써, 각각의 노드에서 어떻게 값을 추출해야 하는지를 제시해줍니다.
//첫 번째 풀이는 single(단방향) 연결리스트를 ArrayList라는 다른 자료형으로 변환하는 다소 편법에 가까운 풀이라고 할 수 있겠습니다.
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
    public boolean isPalindrome(ListNode head) {
        //must return boolean type
        
        ArrayList<Integer> nums = new ArrayList<>();
        
        ListNode node = new ListNode();
        node = head;
        
        while(node!=null){
            nums.add(node.val);
            node = node.next;
        }//while
        
        if(nums.size()==1) return true;
        
        for(int i=0; i<nums.size()/2; i++){
            if(nums.get(i)!=nums.get(nums.size()-1-i)){
                return false;
            }
        }
        
        return true;
        
    }//main
}//main class





//두 번째 풀이는 slow, fast 포인터 2개를 사용한 풀이입니다. 실행시간은 6밀리초로, 첫 번째 풀이보다 1밀리초 빨라졌으며 상위 23%, 100명 중 약 23등의 성능입니다.
//슬로우 포인터는 한 칸씩 이동하고, 페스트 포인터는 2칸씩 이동합니다. 결과적으로 페스트 포인터가 리스트의 끝에 도착한 후 한 번 더 앞으로 가면, 슬로우 포인터는 입력 리스트의
//요소 개수가 홀수인 경우 중앙의 요소 바로 다음 요소에 위치하게 되고, 입력 리스트의 요소 개수가 짝수인 경우, 예를 들어서 리스트가 1,2,3,3,2,1일 때,
//두 번째 등장한 3의 위치에 위치하게 됩니다.
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
    public boolean isPalindrome(ListNode head)
    {
        ListNode slow = head;
        ListNode fast = head;
        while (fast!=null && fast.next!=null)
        {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        ListNode reversed = reverse(slow);//reverse라는 메서드가 ListNode 타입의 노드를 반환한다는 것을 아는 것이 중요합니다.
        //해당 메서드에서 반환된 노드는 1,2,3,3,2,1이 전체 입력 노드일 때, 오른쪽 절반에 해당하는 3,2,1을 역순으로 뒤집어서 만든
        //1,2,3 리스트의 첫 시작 노드를 반환하기 때문입니다.
        //따라서 원본 입력 리스트가 1,2,3,3,2,1 이었다면, reversed 노드와 head 노드는 결과적으로 동일한 요소를 포함하는 리스트들이 됩니다.
        //이 경우에 최종 정답인 true가 리턴 될 것입니다.
        ListNode cur = head;
        while (reversed!=null)
        {
            if (reversed.val!=cur.val)
                return false;
            reversed = reversed.next;
            cur = cur.next;
        }
        return true;
    }//main

    private ListNode reverse(ListNode slow) 
    {//리스트의 순서를 조작하는 작업에 들어갑니다.
        ListNode cur = slow, prev=null;//리스트의 순서를 조작할 때 사용하는 연산은 모두 참조를 이용해서 진행됩니다. cur = slow로 slow 참조 값을 cur에 대입하는 것은, 새로운 변수인 cur이 slow가 가리키는 변수와 동일한 변수를 가리기케 만든다는 것을 뜻합니다. 마찬가지로, 새로운 변수인 prev가 null을 가리키게 만들어줍니다.
        while (cur!=null)//리스트의 끝까지 탐색을 지속하기 위한 조건입니다.
        {
            ListNode temp = cur.next;//while루프를 반복할 때마다 temp라는 새로운 변수가 초기화됩니다. temp는 cur의 다음 요소를 가리키게 해줍니다.
            cur.next = prev;//cur이 자신의 다음 요소로 prev를 가리키게 해줍니다. 이로써, cur이 원래 가리키고 있던 다음 노드와의 연결은 끊어집니다.
            prev = cur;//prev가 cur을 가리키게 해줍니다.
            cur = temp;//여기가 중요합니다. 앞서서 루프의 시작부분에서 temp는 cur의 다음 요소라고 정의했습니다. 이러한 temp를 이용해서 cur이 temp가 가리키는 곳과 동일한 곳을 가리키도록 초기화 하면, 최종적으로 cur은 자신의 다음 노드를 가리키게 됨으로써, 탐색을 지속해 나갈 수 있게 됩니다.
            //해당 루프가 실제로 어떤 과정을 거쳐서 정답을 도출해 나가는지는 눈으로만 봐서는 정확히 알기 어렵습니다. 입력 리스트가 1 >> 2 >> 3 >> 3 >> 2 >> 1 일때를 기준으로 제가 직접 추적해본 while루프의 내부 동작은 다음과 같습니다.

            //while루프 진입 전 : 1 >> 2 >> 3 >>  (3)   >> 2 >> 1
            //while루프 진입 전 :                (cur)    prev는 null을 가리킵니다.
            // cur과 두 번째 3이 둘다 똑같이 소괄호로 둘러싸여 있습니다. 이것은 cur이 갖고 있는 참조 값이 두 번째 3을 가리키고 있다는 뜻입니다.



            //첫 번째 루프 결과 : 1 >> 2 >> 3 >>  (3)    X    { 2 }    >> 1
            //                              (prev)       {cur},{temp}
            //첫 번째 루프 결과, 두 번째 3과 2 사이의 연결이 끝어지고, temp와 cur이 {2}를 가리키게 초기화 되는 것을 알아볼 수 있습니다.



            //두 번째 루프 결과 : 1 >> 2 >> 3 >> 3   <<   2   X    {1}
            //                                     (prev)     {cur}{temp}
            //두 번째 루프 결과, 원래 두 번째 3에서 오른편의 2로 이어지던 흐름이 뒤바뀌어서 2의 다음의 두 번째 3이 되고, prev가 직전 루프의 두 번째 3에서 오른편의 2로 이동했으며, cur과 temp가 직전 루프의 {2}에서 {1}로 이동했습니다.



            //세 번째 루프 결과 : 1 >> 2 >> 3 >>   3   <<   2   <<   {1}
            //                                                  {prev} / cur과 temp는 모두 null이 됩니다.
            //세 번째 루프 결과, prev는 {1}을 가리키게 되고, cur과 temp가 모두 null이 됨으로써, 네 번째 루프는 실행되지 않고 while 루프를 탈출합니다. 결과적으로 prev는 {1}에서 시작해서 두 번째 3에서 끝나는 단일 연결리스트의 시작점이 됩니다. 이렇게 만들어진 단일 연결리스트를 통해 처음에 입력됐던 리스트의 펠린드롬 여부 판별에 사용될 수 있습니다.
            //이렇게 반환된 prev가 main 함수에서 펠린드롬 판별에 최종적으로 활용됩니다.
        }
        return true;
    }//func
}//main class
