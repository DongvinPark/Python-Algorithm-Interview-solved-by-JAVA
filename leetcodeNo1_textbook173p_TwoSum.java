//첫 번째 풀이는 브루트 포스의 방법으로 풀이했습니다.
//중첩된 for 루프에 의해서 시간 복잡도가 O(n^2)가 됐으며, 비효율적인 풀이라고 할 수 있습니다.
//실행시간은 91밀리초였고, 하위 20%의 성능으로 100명 중 약 80등 정도입니다.
class Solution {
    public int[] twoSum(int[] nums, int target) {
        
        int left = 0;
        int right = 0;
        for(int i=0; i<nums.length-1; i++){
            for(int j = i+1; j<nums.length; j++){
                if(nums[i] + nums[j] == target){
                    left = i;
                    right = j;
                    return new int[]{left,right};
                }  
            }//for
        }//for
        
        return new int[]{-1,-1};
        
    }//main
}//main class




//두 번째 풀이는 중첩된 for 루프 대신, HashMap을 활용하여 실행시간을 대폭 단축시켰습니다.
//하지만, HashMap에 삽입시키는 루프와 정답을 탐색하는 루프를 따로 진행시키는 방법을 사용함으로 인해 입력 배열에서 중복된 값이 포함될 경우를 처리하기 위한 cloneRight 변수 및 관련 조작이 별도로 필요해졌습니다.
//실행시간은 6밀리초였고, 이는 상위 54%의 성능으로 100명 중 약 54등입니다.
class Solution {
    public int[] twoSum(int[] nums, int target) {
        int left = 0;
        int right = 0;
        int cloneRight = 0;

        if(target%2 == 0){
            for(int i=0; i<nums.length; i++){
                if(nums[i]*2==(target)){
                    cloneRight = i;
                    break;
                }
            }
        }

        HashMap<Integer, Integer> hm = new HashMap<>();

        for(int i=0; i<nums.length; i++){
                hm.put(nums[i],i);
        }

        for(int i=0; i<nums.length; i++){
            if(hm.containsKey(target-nums[i])) {
                if(i!=hm.get(target-nums[i])) right = hm.get(target-nums[i]);
                else{continue;}
                left = i;
                //right = hm.get(target - nums[i]);
            }
        }

        if(left == right){
            return new int[]{cloneRight,right};
        }
        else{ return new int[]{left,right}; }
    }//main
}//main class




//세 번째 풀이는 해시맵을 사용한다는 것은 동일하지만, 해시맵에 삽입하는 연산과 정답을 찾아내는 연산을 하나의 루프에서 처리함으로써 논리적으로 더 깔끔하고 코드 길이도 단축되었으며, 실행시간도 더 빨라졌습니다.
//실행시간은 4밀리초였고, 이는 상위 30%로 100명 중 약 30등에 해당하는 성능입니다.
class Solution {
    public int[] twoSum(int[] nums, int target) {

        HashMap<Integer, Integer> hm = new HashMap<>();

        int k = nums.length;

        for(int i = 0; i < nums.length; i++){
            int val = nums[i];
            if( hm.containsKey(target-val) ){
                return new int[]{searchMap.get(target-val), i};
            }//if
            hm.put(val, i);
        }//for

        return null;
    }//main
}//main class
