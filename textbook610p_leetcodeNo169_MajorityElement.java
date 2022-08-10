//첫 번째 풀이는 해시맵을 이용한 풀이입니다.  처음부터 끝까지 일관되게 조사해나가는 O(N)의 시간복잡도를
//가지고 있습니다. 하지만, 입력 배열을 구성하고 있는 요소들이 1,2,3,4,5,8,8,8,8,8,8,8이런 식으로
//다양해질 경우, 실행시간이 증가할 수밖에 없는 단점이 있습니다.
// 또한 해시맵이 검색이 빠르기는 하지만 이또한 배열보다는 복잡한 자료구조이기에 시간이 좀 더 걸리는 측면이 있습니다.
//그 결과 실행시간은 17밀리초가 나왔고, 이는 하위 25%의 성능이었습니다.
class Solution {
    public int majorityElement(int[] nums) {
        //return type is int

        HashMap<Integer, Integer> map = new HashMap<>();

        for(int input : nums){
            map.put(input, map.getOrDefault(input, 0)+1);
        }

        int answer = 0;
        int max = 0;

        for(int k : map.keySet()){
            if(map.get(k) >= max){
                max = map.get(k);
                answer = k;
            }
        }

        return answer;

    }//main


}//main class










//두 번째 풀이는 입력배열을 정렬하고, 그 중에서 가운데 인덱스에 위치하는 값을 리턴하는 것입니다.
//이것은 과반수를 차지하고 있는 요소는 그 요소의 크기와 상관없이 정렬 후에 무조건 가운데 위치의
// 인덱스를 차지하고 있을 수밖에 없다는 점을 이용한 풀이입니다.
//실행시간은 4밀리초였고, 이는 상위 50%의 성능이었으며 중간정도의 성능이라고 평가할 수 있습니다.
class Solution {
    public int majorityElement(int[] nums) {
        //return type is int

        Arrays.sort(nums);
        return nums[nums.length/2];


    }//main


}//main class
