//실행시간은 0밀리초였지만, 대부분의 풀이가 0밀리초 구간에 집중돼 있었기 때문에 큰 의미는 없었습니다.
class Solution {
    public int rob(int[] nums) {
        int len = nums.length;
        if (len == 0) return 0;
        int[] dp = new int[len + 1];
        dp[0] = 0;
        dp[1] = nums[0];

        
        //우리가 원하는 것은 단지 조건을 만족한는 합의 최댓값 하나뿐입니다.
        //다이나믹 프로그래밍의 특징을 생각해봅니다.
        //현재의 합을 구하고싶다면, 그 이전의 합 혹은 합들을 참고하면 됩니다.
        //dp[i]가 구하고자 하는 현재의 최댓값이라면, dp[i-1]은 그 이전 루프에서 구해놓은 적법한 최댓값입니다.
        
        // 그러면 왜 현재의 최댓값을 구하는데, {그 이전의 합}과 {그 이전의 이전의 합 + 현재 입력값} 중 최댓값
        //으로 하는 걸까요?
        
        //문제의 조건과 연결지어봅시다. 문제에서는 바로 옆 집은 털 수 없다고 했습니다.
        // dp[i]의 입장에서 바로 옆집은 어디일까요? 바로 dp[i-1]입니다.
        // 앞에서도 말했듯이, dp[i-1]은 현재 구하고자 하는 합의 바로 직전 합입니다.
        // 그렇기 때문에, 현재의 집을 털어먹어야 경우, 바로 직전 합인 dp[i-1]은 계산에 활용할 수 없습니다.
        // 그 대신 옆 옆 집인 dp[i-2]를 선택해야 하는 것입니다.
        
        // 남은 것은 이제 두 가지 상태 중 하나를 선택해서 현재의 dp[i]를 초기화하는 것입니다.
        // 현재 집을 털어먹은 상태와 현재 집을 털어먹지 않고 그냥 넘어가는 상태 둘 중 하나를 고르면 됩니다.
        // 물론, 이때 두 상황 중 합이 더 큰 걸로 선택해야 합니다. 우리는 최댓값을 구하고 싶기 때문입니다.
        
        //이 문제에서 index의 값에 따라서 경우의 수를 if, else로 마구 나누어서 판단하며 풀이하는 것은
        //사실상 불가능합니다. 같은 입력값이 연달아 나오는 경우, 나중에 어떤 인덱스 만큼을 전진해야 최댓값이 되는지를
        //사전에 예측하는 것이 불가능하기 때문입니다.
        
        // 그렇기 때문에 '상태'라는 개념을 도입한 것입니다.
        // 현재 dp[i]를 구하는데, 우리가 선택해야 하는 것은 직전의 합을 유지하는 상태와,
        // 현재 집을 털어먹는 상태 중에서 큰 값을 선택하기만 하면 되는 것입니다.
        
        // 물론 선택한 상태는 for 루프가 진행됨에 따라서 그때그때 바뀔 수 있습니다.
        // 중요한 것은 둘 중 어느 상황을 선택해도 둘 다 문제의 조건을 만족하는 중간 정답이라는 것입니다.
        // 현재 집을 털어먹지 않고 넘어가는 선택지인 dp[i-1]에 도달하는 과정에서 필요로 하는 중간
        // 경우의 수들을 일일이 고민하는 것은 이 문제의 본질이 아니고, 그렇게 풀라고 낸 문제가 아닙니다.
        
        // 단지 dp[i-1]은 직전의 최댓값이자, 현재 집을 털어먹지 않은 상태를 선택했을 때 나오는 최댓값인 것이고,
        // 현재 집을 털어먹을 경우의 상태의 값을 구하고 싶으면, 문제의 제약조건에 따라 dp[i-2]와 현재 집에 있는 돈
        // 둘을 합한 값을 구하면 되는 것입니다.
        
        // 그러고 나서 현재 나올 수 있는 최댓값 dp[i]를 두 상태 중 더 큰 것으로 Math.max()을 이용해서
        // dp[i]를 갱신하면 되는 것입니다.
        
        // 문제 상황에 맞는 중간 정답을 적법한 과정으로 갱신해 나간 끝에 구한 답은 당연히 정답입니다.
        
        /*
        실제 사례를 가져와서 더 구체적으로 설명해 보면, 다음과 같습니다.
        입력값이 1 3 9 7 4 5 라고 해봅시다.
        
        작동 과정을 로그로 남겨서 전부 출력해보면 아래와 같습니다.
------ BEFORE for loop -------
  dp[] = 0, 1, 0, 0, 0, 0, 0, 
nums[] = 1, 3, 9, 7, 4, 5

----- for entered -----
현재 인덱스 i 값 : 2
	* before max
  dp[] = 0, 1, 0, 0, 0, 0, 0, 
**dp[2] = Math.max( 1, 0+3)
nums[] = 1, 3, 9, 7, 4, 5

    * after max
  dp[] = 0, 1, 3, 0, 0, 0, 0, 
  >>> 이번 루프에서는 현재 집인 nums[2-1]인 3을 털지 않고 지나가는 것(즉, dp[1]==1 유지하는 것)보다
  털어먹는 것이 값이 더 크게 나오기 때문에 dp[i]를 0+3으로 갱신한 것을 확인할 수 있습니다.
  즉, nums[0]인 집은 건드리지 않고, nums[1]인 집만 털어먹는 상태를 선택한 것입니다.

----- for entered -----
현재 인덱스 i 값 : 3
	* before max
  dp[] = 0, 1, 3, 0, 0, 0, 0, 
**dp[3] = Math.max( 3, 1+9)
nums[] = 1, 3, 9, 7, 4, 5
	
    * after max
  dp[] = 0, 1, 3, 10, 0, 0, 0, 
  >>> 이번 루프를 이해하는 것이 이 문제의 본질을 이해하는 것과도 같습니다.
  dp[3]을 얼마로 초기화 할 지 결정하기 위해서는 두 가지 상태 중 하나를 선택해야 합니다.
  Math.max(3, 1+9) 를 해석하면 아래와 같습니다.
  Math.max( 직전 최댓값 유지할 것인가?, (옆 옆 집까지 도달하면서 달성한 최댓값 + 현재 집에 있는 돈)을 새롭게 택할 것인가? )
  당연히, 직전 선택값을 유지하는 3(==nums[1]) 보다 현재 집을 털어먹는 1+9(==nums[0]+nums[2])가 더 크기 때문에,
  현재 집을 털어먹는 상황을 선택해야 합니다.
  
  >> 그러면 정말 신기하게도 과거가 바뀝니다. 현재 상태를 적법하게 바꾸었을 뿐인데, 그것이 모든 과거에도 적절한 인덱스의
     집들을 털어먹은 경우의 수를 이미 포함하고 있는 것입니다.
  >> 즉, 이전 루프에서 나는 3의 돈이 있는 집을 선택함으로써 nums[0]의 집은 털어먹지 않은 상태를 선택했는데,
  >> 이번 루프에서는 현재 집을 털어먹는 상황을 선택함으로써, nums[0]과 nums[2]를 털어먹은 상태로
     dp[i]가 초기화 된 것입니다.
  >> 그래서 이 문제는 if, else 같은 index별 경우의 수로 나누어서 풀면 안 되는 것입니다.
  >> dp[i]를 선택하는 과정에서 두 상태 중 값이 더 큰 것을 선택하기만 하면, 그 선택지에는 과거의 적법한 경우의 수들을
    선택해온 상태들이 이미 포함돼 있기 때문입니다.

----- for entered -----
현재 인덱스 i 값 : 4
	* before max
  dp[] = 0, 1, 3, 10, 0, 0, 0, 
**dp[4] = Math.max( 10, 3+7)
nums[] = 1, 3, 9, 7, 4, 5
	
    * after max
  dp[] = 0, 1, 3, 10, 10, 0, 0, 

----- for entered -----
현재 인덱스 i 값 : 5
	* before max
  dp[] = 0, 1, 3, 10, 10, 0, 0, 
**dp[5] = Math.max( 10, 10+4)
nums[] = 1, 3, 9, 7, 4, 5
	
    * after max
  dp[] = 0, 1, 3, 10, 10, 14, 0, 

----- for entered -----
현재 인덱스 i 값 : 6
	* before max
  dp[] = 0, 1, 3, 10, 10, 14, 0, 
**dp[6] = Math.max( 14, 10+5)
nums[] = 1, 3, 9, 7, 4, 5
	
    * after max
  dp[] = 0, 1, 3, 10, 10, 14, 15, 
Final Answer : 15
        
        */

        for (int i = 2; i <= len; i++){
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i - 1]);
        }
        
        return dp[len];
    }//main
}//main class