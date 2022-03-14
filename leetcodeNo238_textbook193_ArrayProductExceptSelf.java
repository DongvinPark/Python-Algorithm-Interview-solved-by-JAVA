//리트코드 238번 문제입니다.
//첫 번째 풀이는 20개의 테스트 케이스 중 19개를 통과했지만, 마지막 테스트 케이스를 평가 받는 도중에 시간초과 판정을 받으며 0점처리 됐습니다. 입력된 배열의 요소 1개를 계산할 때마가 현재 인덱스를 제외한 전체 배열을 읽어야 해서 총 연산 횟수가 n(n-1)이 되었고, 결과적으로 O(n^2)의 시간 복잡도를 가지게 됩니다.
class Solution {
    public int[] productExceptSelf(int[] nums) {
        //출력 형식은 인트 타입 배열이다.
        
       int result = 1;
        int[] answer = new int[nums.length];
        
        for(int i=0; i<nums.length; i++){
            result = 1;
            for(int j=0; j<nums.length; j++){
                if(j!=i){
                    result *= nums[j];
                }//if
            }//inner for
            answer[i]=result;
        }//for
        
        return answer;
        
    }//main
}//main class


//두 번째 풀이는 look up table을 이용합니다.
//입력배열이 주어졌을 때, 인덱스의 처음부터 시작해서 오른쪽으로 진행해 갈수록, 이전 인덱스 요소들의 곱셈 값을 누적해서 저장해두는 새로운 배열을 만들어둡니다.
//예를 들어서 입력배열이 {1,2,3,4,5,6}이라면, 0 인덱스는 아직 이전 인덱스가 없으므로 1을 저장합니다.
//1인덱스는 직전 인덱스들이 0 인덱스 1개 뿐이므로 1을 저장합니다.
//2인덱스는 직전 인덱스가 0,1 두개 있으므로, 1*2를 저장합니다.
//이렇게 5 인덱스는 1*2*3*4*5를 저장해 둡니다.
//그 다음엔 인덱스의 끝에서부터 첫 인덱스 방향으로 누적해서 곱한 값을 저장해 주는 새로운 배열을 만듭니다.
//입력 배열이 {1,2,3,4,5,6} 이라면, 
//{2*3*4*5*6, 3*4*5*6, 4*5*6, 5*6, 6, 1} 이러한 값을 갖게 됩니다.
//정답 배열의 인덱스 요소들은 왼쪽 누적 배열의 인덱스 요소값과 오른쪽 누적 배열의 인덱스 요소값을 곱한 값으로 구성하면 됩니다.
//사실 이렇게 look up table을 만들 필요 없이 바로 오른쪽 인덱스 누적 곱셈 값을 이미 계산해 놓은 왼쪽 인덱스 누적 곱셈 값에 곱해서 리턴하면 실행속도를 더 빠르게 해서 정답 판정을 받을 수 있지만, 원리를 더 정확히 설명하고자 look up table을 만들어서 해결했습니다.
class Solution {
    public int[] productExceptSelf(int[] nums) {
        //출력 형식은 인트 타입 배열이다.
        int[] answer = new int[nums.length];
        
        int[] left = new int[nums.length];
        int[] right = new int[nums.length];
        
        int factor = 1;
        //left array를 만듭니다. 인덱스의 맨 왼쪽부터 누적 곱셈 값들을 기록합니다.
        for(int i=0; i<nums.length; i++){
            if(i==0){ left[0]=factor; continue; }
            factor *= nums[i-1];
            left[i]=factor;
        }
        
        //right array를 만듭니다. 인덱스의 맨 오른쪽(즉, 마지막 인덱스)부터 왼쪽으로 누적 곱셈 값들을 기록합니다.
        factor = 1;
        for(int i=nums.length-1; i>=0; i--){
            if(i==nums.length-1){right[nums.length-1]=factor; continue;}
            factor *= nums[i+1];
            right[i]=factor;
        }
        
        //make answer. 왼쪽 방향 누적 배열 인덱스 요소 값과 오른쪽 방향 누적 배열 인덱스 요소 값을 곱하면 정답입니다.
        for(int i=0; i<answer.length; i++){
            answer[i] = left[i]*right[i];
        }
        
        return answer;
        
    }//main
}//main class
