//실행시간은 9밀리초였고, 이는 상위 약 20%에 해당하는 성능이었습니다. 시간복잡도는 O(N * log N)입니다.
//입력이 2차 배열이고, matrix[i]는 결국 또 다른 1차 배열이므로, 각각의 matrix[i]에 대하여 이진탐색을 실시해주고, 결과를 찾지 못한 경우 false를, 결과를 찾은 경우 true를 반환해주면 됩니다.
class Solution {

    int ansIdx = -1;

    public boolean searchMatrix(int[][] matrix, int target) {

        for(int i =0; i<matrix.length; i++){
            //매 탐색시마다 andIdx를 -1로 초기화 해줘도 괜찮고, 사실 이 부분은 없어도 정답처리는 됩니다.
            //어차피 타깃 값을 한 번이라도 찾기만 하면 바로 true를 리턴하고 searchMatrix()라는 메인 메서드를 종료하면 되기 때문입니다.
            ansIdx = -1;

            //입력이 2차원 배열이므로, 이진탐색의 범위를 정확하게 입력하여 인자로 넘겨주는 것이 중요합니다.
            binary(0, matrix[i].length-1, matrix[i], target);

            //탐색 결과 타깃 값을 찾았다면 바로 true를 리턴하면 됩니다.
            if(ansIdx != -1) return true;
        }//for

        //값을 찾지 못했다면 false를 반환합니다.
        return false;

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
