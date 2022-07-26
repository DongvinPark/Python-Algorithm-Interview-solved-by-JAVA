//첫 번째 풀이는 매 범위마다 PriorityQueue를 새로 만들어서 그 중에서 최댓값을 찾아내서 리스트에 추가하고,
//그 리스트의 내용물대로 int[]를 만들어서 리턴하는 방식이었습니다.
//일종의 브루트 포스 풀이이기 때문에 TimeLimitExceeded 판정을 받고 오답처리 되었습니다.
class Wrong_Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        //return type is int

        ArrayList<Integer> beforeAns = new ArrayList<>();

        int[] answer = new int[nums.length - k + 1];

        PriorityQueue<Integer> queue = new PriorityQueue<>( Collections.reverseOrder() );

        for(int i=0; i<= nums.length-k; i++){
            queue.clear();
            for(int j=i; j<i+k; j++ ){
                queue.add(nums[j]);
            }
            beforeAns.add( queue.poll() );
        }

        for(int m=0; m<answer.length; m++){
            answer[m] = beforeAns.get(m);
        }

        return answer;

    }//main
}//main class



//두 번째 풀이의 실행시간은 43~73밀리초가 걸렸고, 이는 하위 31% ~ 상위 19%의 성능이었습니다.
//큐를 ArrayDeque<Integer>로 정의하되, 큐 안에 nums[]의 요소 값이 들어가는 것이 아니라, 인덱스 값이 들어간다는 매우 중요한 차이점이 있습니다.
class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {

        //System.out.println("\nSolution called");

        //정답 배열을 먼저 만들었습니다. 정답 배열의 크기를 정하는 것은 그리 어렵지 않았습니다.
        int[] answer = new int[nums.length-k+1];

        Deque<Integer> deque = new ArrayDeque<>();

        //정답 배열에 정답을 기록할 때 사용할 인덱스를 미리 정의해 놓습니다.
        int index=0;

        //nums[]의 내용물들을 i 인덱스를 이용해서 순회합니다.
        for(int i=0; i<nums.length; i++){

            /*
            * 아래에 있는 A파트와 B파트의 역할을 이해하는 것이 중요합니다.
            * A파트는 큐의 앞부분을 버리는 역할을 하고, B파트는 큐의 뒷 부분을 버리는 역할을 합니다.
            * A파트는 큐의 맨 앞부분 값이 i-k와 같을 때, 큐의 앞 부분을 버려줍니다.
            *
            * 이렇게 해줌으로써 i가 진행될 때마다 큐가 갖고 있는 인덱스 값들 중
            * 가장 첫 번째 인덱스 값(queue.peekFirst()를 통해서 확인 가능)의 num[queue.peekFirst()]값이
            * 슬라이딩 윈도우에서 최댓값이 될 수 있게 해줍니다.
            *
            * A파트는 슬라이딩 윈도우를 이용한 탐색에 있어서 범위를 정확하게 제한하기 위한 것입니다.
            * 예를 들어서 현재 인덱스가 4라면, 슬라이딩 윈도우의 탐색 범위는 2~4인덱스 까지이고,
            * 1인덱스는 탐색 범위에 포함돼서는 안 됩니다. 현재 인덱스가 4일 때, i-k==1 이므로, 큐의 첫 번째
            * 값이 1을 포함하고 있다면 이를 버려줘야 합니다.
            *
            * B파트는 현재의 nums[i] 값, 즉 새로 큐에 들어올 인덱스가 가리키고 있는 값보다
            * 작은 값을 가리키고 있는 큐의 인덱스들을 삭제하기 위한 것입니다.
            * 큐의 뒤에서부터 값을 삭제해나감으로써, 큐의 맨 앞 부분의 값이 현재의 nums[i]값보다 큰 경우에만
            * 남아 있게 해 줍니다.
            * 이렇게 함으로써 현재의 슬라이딩 윈도우에서 최댓값이 자연스럽게 큐에 남아 있을 수 있게 합니다.
            * */

            //A PART
            if(!deque.isEmpty() && deque.peekFirst()==i-k){
                deque.pollFirst();
            }

            //B PART
            while(!deque.isEmpty() && nums[deque.peekLast()]<=nums[i]){
                deque.pollLast();
            }
            deque.offer(i);

            //예를 들어서 k==3이라면, nums[]를 최소한 3개는 순회하여 nums[i]의 i가 2는 되어야
            //최초로 슬라이딩 윈도우에서 최댓값을 가려낼 수 있습니다. 따라서 i>=k-1 일 때만 정답 배열에 index
            //값을 이용해서 정답을 기록할 수 있게 해주고, 정답 배열의 다음 인덱스에 기록을 이어나가기 위해서 index++;
            //을 해줍니다.
            if(i>=k-1){
                answer[index]=nums[deque.peekFirst()];
                index++;
            }
        }//for

        return answer;
    }//main
}//main class
/*
*
입력 배열이 아래와 같고,  k == 3일 때 루프가 실제로 어떻게 작동하는지를 추적한 로그입니다.
* 아래의 로그 내용들을 살펴보면 정답이 어떻게 도출되는지를 명확하게 이해할 수 있습니다.
1, 3, -1, -3, 5, 3, 6, 7,
Solution called


-------------0th for entered
current i of for : 0
index : 0
for 진입 직후 큐 현황
(큐가 비어 있음)
	>> 큐의 앞 부분을 버려야 하는가? No.
	>> 큐의 뒷 부분을 얼마나 버릴 것인가? No.
>> 버릴 것 다 버리고, 현재 for의 인덱스값인 0 offer한 후의 큐
0,


-------------1th for entered
current i of for : 1
index : 0
for 진입 직후 큐 현황
0,
	>> 큐의 앞 부분을 버려야 하는가? No.
	>> 큐의 뒷 부분을 얼마나 버릴 것인가? nums[1]==3 > nums[0]==1이므로 큐에서 0을 버려야 한다.
	큐의 뒷 부분을 버린다
>> 버릴 것 다 버리고, 현재 for의 인덱스값인 1 offer한 후의 큐
1,


-------------2th for entered
current i of for : 2
index : 0
for 진입 직후 큐 현황
1,
	>> 큐의 앞 부분을 버려야 하는가? No.
	>> 큐의 뒷 부분을 얼마나 버릴 것인가? nums[2]==-1 < nums[1]==3이므로 버릴 필요 없다.
>> 버릴 것 다 버리고, 현재 for의 인덱스값인 2 offer한 후의 큐
1, 2,
*** 최초로 정답을 추가하고 인덱스를 ++ 해 놓는다!!


-------------3th for entered
current i of for : 3
index : 1
for 진입 직후 큐 현황
1, 2,
	>> 큐의 앞 부분을 버려야 하는가? No.
	>> 큐의 뒷 부분을 얼마나 버릴 것인가? No.
>> 버릴 것 다 버리고, 현재 for의 인덱스값인 3 offer한 후의 큐
1, 2, 3,
*** 정답을 추가하고 인덱스를 ++ 해 놓는다!!


-------------4th for entered
current i of for : 4
index : 2
for 진입 직후 큐 현황
1, 2, 3,
	>> 큐의 앞 부분을 버려야 하는가? 탐색범위가 2,3,4인덱스 이므로 큐가 가진 1인덱스 값을 버린다.
	큐의 앞부분을 버린다
	>> 큐의 뒷 부분을 얼마나 버릴 것인가? nums[2]==-1, nums[3]==-3 모두 nums[4]==5보다 작다.
	            따라서 큐의 내용물은 전부 버려야 한다.
	큐의 뒷 부분을 버린다
	큐의 뒷 부분을 버린다
>> 버릴 것 다 버리고, 현재 for의 인덱스값인 4 offer한 후의 큐
4,
*** 정답을 추가하고 인덱스를 ++ 해 놓는다!!


-------------5th for entered
current i of for : 5
index : 3
for 진입 직후 큐 현황
4,
	>> 큐의 앞 부분을 버려야 하는가? No.
	>> 큐의 뒷 부분을 얼마나 버릴 것인가? No.
>> 버릴 것 다 버리고, 현재 for의 인덱스값인 5 offer한 후의 큐
4, 5,
*** 정답을 추가하고 인덱스를 ++ 해 놓는다!!


-------------6th for entered
current i of for : 6
index : 4
for 진입 직후 큐 현황
4, 5,
	>> 큐의 앞 부분을 버려야 하는가? No.
	>> 큐의 뒷 부분을 얼마나 버릴 것인가? nums[4]==5, num[5]==3 모두 nums[6]==6보다 작다.
	            * 따라서 큐의 내용물 전부 버려야 한다.
	큐의 뒷 부분을 버린다
	큐의 뒷 부분을 버린다
>> 버릴 것 다 버리고, 현재 for의 인덱스값인 6 offer한 후의 큐
6,
*** 정답을 추가하고 인덱스를 ++ 해 놓는다!!


-------------7th for entered
current i of for : 7
index : 5
for 진입 직후 큐 현황
6,
	>> 큐의 앞 부분을 버려야 하는가? No.
	>> 큐의 뒷 부분을 얼마나 버릴 것인가? nums[7]==7이 nums[]의 최댓값이므로 큐를 전부 비운다.
	큐의 뒷 부분을 버린다
>> 버릴 것 다 버리고, 현재 for의 인덱스값인 7 offer한 후의 큐
7,
*** 정답을 추가하고 인덱스를 ++ 해 놓는다!!

Final answer
3, 3, 5, 5, 6, 7,
Process finished with exit code 0
* */
