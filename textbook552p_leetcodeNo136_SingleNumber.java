//실행시간은 1밀리초로 나왔고, 이는 상위 0%에 해당하는 가장 빠른성능이었습니다.
//XOR 연산을 뜻하는 ' ^ ' 연산자의 의미를 이해하는 것이 중요합니다.
//해당 연산자는 두 개의 정수 사이에서 int k = 4^6; 이런 식으로 사용될 수 있는 연산자입니다.
//즉, ^ 연산자를 중심으로 좌우에 있는 두 정수가 서로 같은 정수일 때는 int k 가 0으로 초기화 됩니다.
//서로 같은 정수일 때 0으로 초기화되는 성질을 가지고 있기 때문에 문제에서 주어진 상황을 해결하기에 적절한 연산자임을 알 수 있습니다.
class Solution {
    public int singleNumber(int[] nums) {
        //return type is int

        int answer = 0;

        for(int n : nums){

            // for문을 for(int i=0; i<nums.length; i++)이라고 고칠 경우,
            // 아래의 연산은 다음과 동일합니다. answer = answer ^ nums[i];
            answer ^= n;
        }

        return answer;

    }//main
}//main class
