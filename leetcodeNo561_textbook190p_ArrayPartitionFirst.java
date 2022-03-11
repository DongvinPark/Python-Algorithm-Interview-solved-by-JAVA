//배열을 오름차순으로 정렬한 후, 홀수 인덱스 요소만 골라서 더하면 정답입니다.
//실행시간은 15밀리초였고, 이는 상위 68%, 100명중 68등 정도입니다.
class Solution {
    public int arrayPairSum(int[] nums) {
        
        Arrays.sort(nums);
        
        if(nums.length == 2){ return nums[0]; }
        
        int sum = 0;
        
        for(int i=0; i<nums.length; i++){
            if(i%2 == 0){
                sum += nums[i];
            }
        }
        
        return sum;
        
    }//main
}//main class





//이번에는 정렬 후, 배열의 모든 요소를 일일이 검사하지 않고 인덱스 0부터 시작하여 아예 +2씩 인덱스를 더해주면서 홀수번째 인덱스만을 더했습니다.
//검사과정이 생략되자 실행시간이 첫 번째 풀이보다 5밀리초 단축된 10밀리초가 나왔으며, 이는 상위 1%의 실행속도입니다.
class Solution {
    public int arrayPairSum(int[] nums) {
        
        Arrays.sort(nums);
        
        int sum = 0;
        
        for(int i=0; i<nums.length; i+=2){
                sum += nums[i];
        }
        
        return sum;
        
    }//main
}//main class
