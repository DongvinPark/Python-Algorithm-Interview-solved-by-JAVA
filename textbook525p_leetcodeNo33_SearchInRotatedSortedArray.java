//첫 번째 풀이는 3밀리초의 실행시간이 나왔고, 대부분의 풀이들이 0,1밀리초 구간에 몰려 있었기 때문에 하위 5%의 성능으로 측정되었습니다.
//1,2 밀리초로 측정된 풀이들에 비해서 좀 더 직관적으로 이해할 수 있지만, 인덱스를 직접 다루기 때문에 관련 경우의 수를 나눌 때
//세심히 주의를 기울여야 한다는 단점도 있습니다.
/*
* 첫 번째 풀이의 핵심은 회전된 배열을 원상태로 돌려 놓고서 이진탐색을 한 다음, 회전된 상태에 맞춰서 answer값을 재조정하는 것입니다.
* 예를 들어 원래 입력 배열이 5,7,9,1,3 이었다면,
* 이 배열을 다시 정렬한 것은 1,3,5,7,9 일 것입니다.
* 여기서 pivotIdx, 즉, 배열의 회전이 일어난 인덱스 값은 원래 입력 배열인 nums[] 배열에서 최솟값이 위치하고 있는
* 인덱스 값과 동일하다는 점을 알 수 있습니다.
* 13579배열이 pivotIdx == 3 값을 기준으로 회전한 것이 원래 입력배열인 nums[]인 것입니다.
* 일단 타깃 값을 13579 배열에서 이진 탐색을 이용해서 찾습니다. 그 값으로 answer를 초기화 해줍니다.
*
* nums[nums.length-1] 즉 원래 입력 배열의 마지막에 위치한 값을 기준으로, 이 값(코드에서는 oriPvValue 입니다)보다 작거나 같은 값인
* 1,3은 13579배열에서 57913 배열로 회전할 때, 13579배열의 0,1인덱스에서 pivotIdx(==3)만큼 더해져야 정답이 될 수 있음을 알 수 있습니다.
* 이 경우를 A 케이스라고 하겠습니다.
*
* oriPvValue 값보다 큰 값들인 5,7,9는 pivotIdx 값을 기준으로 배열을 회전시켰을 때
* 13579배열의 2,3,4인덱스에서 (nums.length-pivotIdx) 값만큼 빼줘야 정답이 됨을 알 수 있습니다. 이 경우를 B 케이스라고 하겠습니다.
*
* */
class Solution {

    //정답을 일단 -1로 맞춰둡니다.
    public int answer = -1;

    public int search(int[] nums, int target) {
        //return type is int

        int pivotIdx = 0;

        //nums[pivotIdx]
        int oriPvValue = 0;

        //피벗 인덱스 값을 먼저 찾아야 합니다. 넘스[]에서 최솟값이 위치하는 인덱스입니다.
        int forMin = 10_000;
        for(int i=0; i<nums.length; i++){
            if(nums[i]<forMin){
                forMin = nums[i];
                pivotIdx = i;
            }
        }//for

        //정답 판정시 필요한 기준값을 미리 설정합니다. 이것은 nums[] 배열을 재정렬 하기 전에 설정해야 합니다.
        oriPvValue = nums[nums.length-1];

        //Nums를 원래의 오름차순으로 재정렬합니다.
        Arrays.sort(nums);

        //바이너리 서치합니다.
        binary(0, nums.length-1, nums, target);

        //찾는 것이 없다면 -1을 리턴하면 됩니다.
        if(answer == -1) return answer;

        //여기에서 경우의 수를 나누는 것이 중요합니다.

        //A케이스입니다.
        if(target <= oriPvValue){
            return answer+(pivotIdx);
        }

        //B케이스입니다.
        else if(target > oriPvValue){
            return answer-(nums.length-pivotIdx);
        }

        //target == oriPvValue인 경우입니다.
        else {return 0;}

    }//main

    //전형적인 이진탐색 메서드입니다.
    public void binary(int start, int end, int[] inputArray, int target){
        int mid = end + (start - end)/2;
        if(inputArray[mid]==target){
            answer = mid;
        }

        if(start < end){
            if(target > inputArray[mid]){
                binary(mid+1, end, inputArray, target);
            }
            else{
                binary(start, mid-1, inputArray, target);
            }
        }//if
    }//fuc

}//main class









//실행시간은 첫 번째 풀이보다 1밀리초 빠른 2밀리초였지만, 대부분의 풀이가 0,1밀리초에 몰려 있어서 하위 5%의 성능을 기록했습니다.
//사살 첫 번째 풀이는 피벗 값의 인덱스에 따라서 경우의 수를 나눠서 인덱스르 직접조작하는 풀이방식이기 때문에 이해하기가 조금 귀찮은 면이 있습니다.
//두 번째 풀이는 첫 번째 풀이보다 더 직관적입니다. 두 번째 풀이이 핵심은 회전된 배열이라 할지라도, 피벗 인덱스 값을 기준으로 배열의 두 부분을 나누면
//각각의 배열은 어차피 정렬이 다 돼 있는 상태이기 때문에 nums[] 배열을 두 개로 나눠서 각각에 대해 이진탐색을 해준다는 것입니다.
/*
* 예를 들어 입력 배열이 4,5,6,7,0,1,2 라면, 배열을 4,5,6,7 배열과 0,1,2 배열로 나눠서 각각에 대해 이진탐색을 해주는 것입니다.
* */
class Solution {

    public int answer = -1;

    public int search(int[] nums, int target) {
        //return type is int

        if(nums[0]==target) return 0;
        if(nums[nums.length-1]==target) return nums.length-1;

        int pivotIdx = 0;

        //피벗 인덱스 값을 먼저 찾아야 합니다. 넘스[]에서 최솟값이 위치하는 인덱스입니다.
        int forMin = 10_000;
        for(int i=0; i<nums.length; i++){
            if(nums[i]<forMin){
                forMin = nums[i];
                pivotIdx = i;
            }
        }//for

        if(nums[pivotIdx]==target) return pivotIdx;

        //첫 번째 범위에 대해서 이진탐색을 해줍니다.
        binary(0, pivotIdx-1, nums, target);
        if(answer != -1) {
            return answer;
        }

        //그러고 나서 두 번째 범위에 대해서 이진탐색을 해줍니다.
        binary(pivotIdx, nums.length-1, nums, target);
        return answer;

    }//main

    //전형적인 이진탐색 메서드입니다.
    public void binary(int start, int end, int[] inputArray, int target){
        int mid = end + (start - end)/2;

        if(start <= end){

            if(inputArray[mid]==target){
                answer = mid;
            }
            if(target < inputArray[mid]){
                binary(start, mid-1, inputArray, target);
            }
            else{
                binary(mid+1, end, inputArray, target);
            }
        }//if
    }//fuc

}//main class
