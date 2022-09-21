//첫 번째 풀이는 이진탐색을 활용했습니다. 실행시간은 7밀로초로 느린 편이고 이는 하위 12%의 성능이었습니다.
//시간복잡도는 O(N * logN)입니다.
class Solution {

    public int ansIdx = -1;

    public int[] twoSum(int[] numbers, int target) {
        //return type is int

        int[] ansArray = new int[2];

        //입력 배열을 차례대로 순회하되, 현재 i번째 요소를 보고 있다면,
        //i+1 ~ numbers.length-1 인덱스까지의 범위에 대해 이진탐색을 실시합니다.
        //탐색 결과가 문제의 조건과 일치한다면 정답에 포함시킵니다. 문제에서 정답은 유일하다 했으므로
        //정답을 찾았다면 나머지 요소들은 더 이상 볼 필요가 없습니다. 따라서 break; 를 활용해서 실행시간을 단축시킬 수 있습니다.
        for(int i=0; i<numbers.length-1; i++){
            ansIdx = -1;

            binary(i+1, numbers.length-1, numbers, target-numbers[i]);

            if(ansIdx != -1 && numbers[i]+numbers[ansIdx]==target){
                ansArray[0] = i+1;
                ansArray[1] = ansIdx+1;
                break;
            }//O if
        }//for

        return ansArray;

    }//main

    //전형적인 이진탐색 로직입니다.
    public void binary(int start, int end, int[] inputArray, int target){
        int mid = end + (start-end)/2;

        if(start <= end){
            if(inputArray[mid]==target) {ansIdx = mid;}

            if(target > inputArray[mid]){
                binary(mid+1, end, inputArray, target);
            }
            else{
                binary(start, mid-1, inputArray, target);
            }
        }//if

    }//func

}//main class








//두 번째 풀이는 시작점 포인터 1개와 마지막점 포인터 1개 이렇게 2개의 포인터를 이용하는 풀이입니다.
//실행시간은 2밀리초로 빠른 편이고, 이는 상위 23%의 성능이었습니다.
//입력 배열을 1번만 읽으면 되기 때문에 시간 복잡도는 O(N)입니다.
class Solution {
    public int[] twoSum(int[] numbers, int target) {
        //return type is int[]

        int[] result = new int[2];

        int left = 0;

        int right = numbers.length - 1;

        while(left < right) {

            //일단 합을 먼저 계산합니다. 그 후 왼쪽과 오른쪽 중 어느 쪽 포인터를 이동시킬 것인지를 정합니다.
            int sum = numbers[left] + numbers[right];

            //정답을 찾았다면 정답을 기록하고 바로 루프를 탈출하면 됩니다.
            if(sum == target) {
                result[0] = left+1;
                result[1] =right+1;
                break;
            }

            //합이 타깃보다 작다면 왼쪽 포인터를 ++하여 합이 커질 수 있게 해줍니다.
            //이게 가능한 이유는 입력 배열이 오름차순으로 정렬되 있는 배열이기 때문입니다.
            else if(sum < target) {
                left++;
            }

            //합이 타깃보다 크다면 오른쪽 포인터를 --하여 합이 줄어들게 해줍니다.
            //이게 가능한 이유는 left++ 때와 마찬가지로, 입력 배열이 오름차순으로 정렬돼 있는 배열이기 때문입니다.
            else if (sum > target) {
                right--;
            }

        }//wh
        return result;
    }//mian
}//main class
