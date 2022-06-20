//실행시간은 4밀리초였고, 이는 상위 19%의 성능이었습니다.
//이 문제에서 주의할 점은, 중복된 요소가 있어도 이를 무시하지 않는다는 점입니다.
//예를 들어서 nums를 오름차순으로 정렬한 결과가 1,2,3,4,4,5인데 여기에서 3번째로 큰 값을 찾으라고 했다면, 답은 3이 아니라 3 인덱스에서 등장하는 4입니다.
class Solution {
    public int findKthLargest(int[] nums, int k) {
        //return type is int
        
        Arrays.sort(nums);
        
        return nums[nums.length-k];
        
    }//main
}//end of main
