//첫 번째 풀이는 O(n^2)의 시간복잡도를 갖고 있습니다. 입력 배열의 각 요소에 대해서 정답을 찾을 때, 자신의 뒤에 나오는 모든 기온 값들을 전부 일일이 다 검사해서 답을 찾기 때문입니다.
//시간 초과 판정을 받을 걸로 예상하고 제출해보니, 역시나 47개의 테스트 케이스 중 마지막 테스트 케이스를 판단하다가 결국 시간 초과 판정을 받았습니다.
class Solution {
    public int[] dailyTemperatures(int[] temperatures) {
        //return type is int[].
        if(temperatures.length == 1){
            int[] answer = new int[1];
            answer[0]=0;
            return answer;
        }

        if(temperatures.length == 2){
            int[] answer = new int[2];
            if(temperatures[0]>temperatures[1]){
                answer[0]=1; answer[1]=0;
                return answer;
            }
            else {
                answer[0]=0; answer[1]=0;
                return answer;
            }
        }
        

        int[] answer = new int[temperatures.length];
        
        for(int i=0; i<temperatures.length; i++){
            if(i==temperatures.length-1){answer[i]=0; break;}
            int howManyMove = 0;
            
            for(int j=i+1; j<answer.length; j++){

                //자신의 다음 요소가 자신보다 작을 경우, howManyMove에 1을 더해줍니다.
                if(temperatures[j]<=temperatures[i]){
                    if(j!=temperatures.length){howManyMove++;}

                    if(j==temperatures.length){
                        //자신보다 더 큰 요소를 결국 찾지 못한 경우입니다.
                        answer[i]=0;
                        break;
                    }
                }


                else{
                    //자신보다 더 큰 요소를 찾아낸 경우입니다.
                    howManyMove++;
                    answer[i]=howManyMove;
                    break;
                }
            }//in for
            
        }//for
        
        return answer;

    }//main
}//main class




//두 번째 풀이는 Deque<Integer> 자료형을 이용하여 풀이했습니다.
//실행 결과, 약 40밀리초가 소요되었으며 이는 상위 약 25%의 성능이었습니다.
//이 문제를 풀 때는 스택에 현재의 기온 값이 아니라, 현재의 인덱스 값을 집어넣는다는 것을 기억해야 합니다.
class Solution {
    public int[] dailyTemperatures(int[] temperatures) {
        //return type is int[].

        int[] answer = new int[temperatures.length];

        Deque<Integer> stack = new ArrayDeque<>();

        //우선 입력 배열로 들어온 값들을 차례대로 순회합니다.
        for(int i=0; i<temperatures.length; i++){

            //while 문의 뜻을 이해하는 것이 중요합니다. while문의 역할은 정답을 찾아내서 answer 배열에 입력시키고 스택에서 이미 정답으로 처리된 인덱스들을 제거하는 역할입니다. 따라서 현재 인덱스에서 읽어낸 기온 값이 정답으로 처리되서는 안 될 경우 while 루프를 벗어나야 합니다.
            while(true){
                    //System.out.println(stack);

                //현재 스택이 비어 있다면 일단 스택에 추가해야 합니다.
                if(stack.isEmpty()) break;

                //현재 기온 값이 스택에 있는 첫 번째 인덱스보다 작을 경우, 스택에 집어넣어야 하므로 while 루프를 탈출해야 합니다.
                if(temperatures[i]<=temperatures[stack.peek()]){
                    break;
                }
                
                //if 조건문들에 해당이 되지 않는다면 정답으로 처리해 됩니다.
                //스택에서 하나씩 뽑아내면서 정답을 정답배열에 기록해줍니다.
                int k = stack.pop();
                answer[k] = i-k;
            }//while

            stack.push(i);
        }//for

        return answer;

    }//main
}//main class

/*
다음은 입력값이 73,74,75,71,69,72,76,73 인 경우 두 번째 풀이 방법에서 정답을 도출해 나가는 과정을 추적해본 결과입니다.

로그들을 확인해보면, 현재의 기온 값이 [ 스택에 저장돼 있는 첫 인덱스에 위치해 있는 기온값 ]보다 낮을 경우 스택에 저장하고 있음을 알 수 있습니다.

그러다가 현재의 기온값보다 스택에 저장된 첫 인덱스의 기온값이 더 크다면 스택에서 값을 뽑아내고, 그 인덱스에 해당하는 정답배열에 (현재인덱스 값 - 스택에서 뽑아낸 첫 요소의 값)을 기록해줍니다.

 for entered
i : 0
	0 was pushed in stack!!
stack(left most is first one!!) : [0]


 for entered
i : 1
0 was popped from stack!!
	1 was pushed in stack!!
stack(left most is first one!!) : [1]


 for entered
i : 2
1 was popped from stack!!
	2 was pushed in stack!!
stack(left most is first one!!) : [2]


 for entered
i : 3
	3 was pushed in stack!!
stack(left most is first one!!) : [3, 2]


 for entered
i : 4
	4 was pushed in stack!!
stack(left most is first one!!) : [4, 3, 2]


 for entered
i : 5
4 was popped from stack!!
3 was popped from stack!!
	5 was pushed in stack!!
stack(left most is first one!!) : [5, 2]


 for entered
i : 6
5 was popped from stack!!
2 was popped from stack!!
	6 was pushed in stack!!
stack(left most is first one!!) : [6]


 for entered
i : 7
	7 was pushed in stack!!
stack(left most is first one!!) : [7, 6]


>>> 마침내 정답을 반환합니다. 1, 1, 4, 2, 1, 1, 0, 0, 
*/
