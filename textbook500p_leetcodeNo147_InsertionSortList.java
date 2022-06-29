//실행시간은 25밀리초였고, 이는 하위 약 25%에 해당하는 성능이었습니다.
//배열이 아닌, 단방향 리스트가 주어진 상황에서 삽입정렬 로직을 이용하여 구현해야 하는 문제였습니다.
class Solution {
    public ListNode insertionSortList(ListNode head) {
        //return type is ListNode

        if (head == null || head.next == null) {
            return head;
        }

        //루프를 시작하기 전에 더미헤드 노드를 미리 만들어둡니다.
        ListNode dummyHead = new ListNode();

        //주어진 노드를 끝까지 탐색하기 위해  바깥쪽 반복 루프의 조건문을 아래와 같이 설정했습니다.
        while (head != null) {
            //System.out.println("\n\nO while entered");
            //System.out.println("current head.val : " + head.val);

            //현재의 더미 노드를 가리키는 새로운 노드를 만들어줍니다.
            ListNode curNode = dummyHead;

            //새로운 curNode를 이동시키기 위한 로직입니다. 왜 이러한 로직이 사용되었는지는 아래에서 설명할 예정입니다.
            while (curNode.next != null && curNode.next.val < head.val) {
                //System.out.println("\tI while enter");
                curNode = curNode.next;
            }//I wh

            //왜 next 노드를 새로 만들었는지에 대해서도 아래에서 설명할 예정입니다.
            ListNode next = curNode.next;
            /*if(next == null){
                System.out.println("ListNode next is NULL");
            }
            else{
                System.out.println("ListNode next.val : " + next.val);
            }*/

            //왜 이러한 동작을 보여야 하는지를 아래에서 설명할 예정입니다.
            curNode.next = new ListNode(head.val);
            curNode.next.next = next;

            //다음 while 루프를 위한 준비를 해줍니다.
            head = head.next;
        }//O wh

        return dummyHead.next;
    }//main
}//class
/*
* 입력 노드가 다음과 같은 경우에 대해서 실제 코드가 어떻게 동작하는지 로그를 출력해보면 다음과 같습니다.
*    4    >>>    2    >>>    1    >>>    3
*  head
* 4 아래에 head가 써 있는 것은, head 노드가 4라는 값을 가지고 있다는 뜻입니다.
*
*
Outer while entered
current head.val : 4
ListNode next is NULL


Outer while entered
current head.val : 2
ListNode next.val : 4


Outer while entered
current head.val : 1
ListNode next.val : 2


Outer while entered
current head.val : 3
	I while enter
	I while enter
ListNode next.val : 4

final answer >>> 1, 2, 3, 4,
*
*
* 바깥쪽 루프를 시작하기 전의 상태입니다.
*  더미헤드 >>> null
* 이러한 상태이고, 더미헤드 노드의 뒤쪽에 정렬된 리스트들을 이어 붙여 나가면서 정답을 구성해나갈 것입니다.
*
* 첫 번째 루프입니다.
* 바깥쪽 루프에 처음 진입했을 때 가장먼저 하는 일은 더미노드를 가리키는 curNode를 새로 만들어주는 일입니다.
* 첫 루프에서는 더미헤드 노드의 다음 노드는 null일 수밖에 없기 때문에, [ 더미노드를 가리키고 있는 curNode ]의 다음 노드도 당연히 null일 수밖에 없습니다.  그래서 로그에서 ListNode next is NULL 라는 문장이 출력된 것입니다.
* 새로운 노드를 만들어서 이어 붙이기 전에, curNode.next를 가리키고 있는 새로운 노드은 next 노드를 만들어줍니다. 이때, curNode는 여전히 더미헤드 노드를 가리키고 있으며, curNode.next 는 null이라는 것을 기억해야 합니다.
* null 상태인 curNode.next 노드에 new 연산을 사용해서 새로운 노드를 만들어줍니다. 그후, curNode.next의 다음 노드를 next로 설정해 줍니다.
* next 노드의 진짜 역할을 두 번째 루프에서 확인해 볼 수 있습니다.
* 이렇게 해줌으로써  dummyHead         >>>       4       >>>       null
*                curNode               curNode.next            next
*
*
*
*
* 두 번째 루프입니다.
* 두 번째 루프를 시작하기 전의 상태는 다음과 같습니다.
* 원래 입력 리스트에서 head노드는 4>2>1>3에서 4가 아니라 4에서 한 칸 전진한 2노드를 가리키고 있습니다.
* 더미헤드로 표현되는 정답 리스트는 dummyHead >>> 4 >>> null 인 상태입니다.
* 첫 루프와 마찬가지로 더미헤드를 가리키는 curNode를 새로 만들어줍니다. 이때, curNode.next의 값은 4입니다. 왜냐하면, curNode가 dummyHead노드를 가리키고 있기 때문입니다. head 노드의 값이 3인데 이는 4보다 작기 때문에 안쪽 루프는 진입을 할 필요가 없습니다.
*
* 여기서부터 next 노드의 진짜 역할이 드러나기 시작합니다. next 노드는 4노드를 가리키기 위해서 존재합니다.
* 4 노드는 2 노드보다 정렬 순서 상 나중에 등장해야 하는 노드입니다.
* 그런데 더미헤드 노드 다음에 2노드가 위치하도록 만들어놓을 경우, 더미헤드 노드 바로 다음에 위치하게 만든 2노드와 4노드를 서로 연결시켜 줄 때 사용할 수 있는 참조 값이 사라지게 됩니다. 정답을 만들어내려면 당연히 2노드 다음에 4노드가 오도록 연결시켜줘야 하는데, 더미헤드 노드 다음에 2노드를 새로 만들어서 붙여주는 과정에서 더미헤드와 4노드 사이의 연결이 끊어져버리는 것입니다.
* 따라서, 2노드의 다음 노드를 정의 할 때, 4노드를 가리키고 있는 노드를 미리 만들어둘 필요가 있는데, 바로 이것이 next노드인 것입니다.
* 새로 만들어진 노드는 curNode.next로 표현할 수 있고, 2노드와 4노드를 연결시켜줘야 하는데,
* 이것을 수행하는 구문이 바로 curNode.next.next = next;인 것입니다.
* 두 번째 루프를 실행한 결과는 다음과 같습니다.
*         dummyHead     >>>      2      >>>      4      >>>      null
*           curNode         curNdoe.next        next
*
*
* 세 번째 루프도 두 번째 루프와 동일한 과정을 밟습니다. 세 번째 루프를 실행한 결과는 다음과 같습니다.
*         dummyHead     >>>     1       >>>      2      >>>      4      >>>      null
*           curNode        curNdoe.next        next
*
*
* 네 번째 루프에서는 마침내 안쪽 루프에 실제로 진입하게 됩니다. 현재 head는 3 값을 가리키고 있는데, 이것은 1,2 보다 나중에 등장해야 하기 때문입니다.
* 바깥 루프에 들어서면 그동안 해왔던 것처럼 더미헤드 노드를 가리키는 curNode를 새로 만들어줍니다. 이때, curNode.next의 값이 현재 head노드가 가리키는 값보다 작기 때문에 curNode가 가리키는 위치를 수정해야 할 필요성이 있습니다. curNode가 가리키는 위치를 수정하기 위한 로직을 맡는 부분이 안쪽 while루프 입니다.
*
* 안쪽 루프에서 비교할 때, curNode.next의 값과 head의 값을 서로 비교하는 것에 유의해야 합니다. 새로운 노드를 만들어주는 위치가 curNode가 아니라 curNode.next이기 때문에, curNode.next의 값보다 작을 때까지 curNode를 이동시켜야 하기 때문입니다.
* curNode가 2 노드에 위치해야 curNode.next의 위치에 3노드를 만들어주고, 4를 가리키게 미리 만들어준 next노드와 연결하여 정답을 최종적으로 확정할 수 있게 됩니다.
*
* 참고로, head.val == 3인 값보다 작은 노드의 개수인 2개 만큼 내부 while 루프가 실행된 것을 로그를 통해서 확인할 수 있습니다.
*
* */
