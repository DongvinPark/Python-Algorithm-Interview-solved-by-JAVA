//첫 번째 풀이는 O(n)시간이 걸리는 풀이입니다. 일단 정답은 내기 때문에 통과는 되지만, 이진 탐색보다 비효울적인 시간복잡도를 가지고 있기에 일부 감점이 있을지도 모르겠습니다.
//실행시간은 2밀리초가 나왔지만, 대부분의 풀이들이 0 또는 1밀리초에 집중돼 있었기 때문에 하위 5%의 성능이 나왔습니다.
class Solution {

    public int search(int[] nums, int target) {
        //return type is int

        int answer = -1;

        for(int i=0; i<nums.length; i++){
            if(nums[i]==target) answer = i;
        }

        return answer;

    }//main

}//main class




//두 번째 풀이는 실행시간이 11밀리초로 가장 느리게 나왔으며, 이는 하위 약 3%의 성능이었습니다.
//ArrayList<> 컬렉션에서 제공하는 indexOf()메서드를 사용하고 있는데, 컬렉션과 같이 단순배열보다 복잡한 자료구조를 써서 그런지 풀이시간이 다른 풀이법들보다 많이 느린 것으로 측정되었습니다.
class Solution {

    public int search(int[] nums, int target) {
        //return type is int

        ArrayList<Integer> AL = new ArrayList<>();

        for(int v : nums){ AL.add(v); }

        return AL.indexOf(target);

    }//main

}//main class







//세 번째 풀이는 재귀 호출 없이 단순 While 반복문으로 풀이했는데, 이는 0밀리초의 실행시간을 보였습니다.
//이는 O(log N)의 시간복잡도를 가지는 이진 탐색 로직을 사용하였기에 가능한 결과였다고 생각합니다.
class Solution {

    public int search(int[] nums, int target) {
        //return type is int

        int start = 0;
        int end = nums.length-1;
        int mid = 0;

        //while 반복문의 조건을 정확하게 설정하는 것이 중요합니다. 그렇지 않을 경우, whiel 루프 아랫쪽에 있는 return -1; 구문이 unreachable 처리되면서 컴파일 에러가 발생하고 즉시 오답처리됩니다.
        while(start <= end){

            //미드 인덱스 값을 이렇게 처리하는 것은 컴퓨터 과학의 측면에서 매우 중요한 의미가 있습니다.
            //정수 타입 자료형에는 저장할 수 있는 최댓값이 있는데, 단순히 (스타트 + 엔드)/2 로 처리할 경우, 스타트와 엔드 값을 더한 값이 정수타입에서 저장할 수 있는 최댓값을 넘어버릴 수 있기 때문입니다.
            //그래서 스타트와 엔드 값을 그냥 더하는 것이 아니라, end + (start-end)/2로 처리하여 스타트에서 엔데 값을 빼게 함으로써 오버플로우가 발생할 가능성을 애초에 차단하는 것입니다.
            mid = end + (start - end)/2;

            //미드 인덱스에 있는 값이 타겟 값과 일치하면 미드 인덱스 값을 리턴합니다.
            if(nums[mid]==target){return mid;}

            //미드 인덱스에 있는 값이 타깃 값보다 작은 경우입니다. 이때는 0인덱스부터 미드 인덱스까지는 탐색을 할 필요가 없게 되기 때문에, 스타트 값을 (미드+1)로 설정해줍니다.
            if(nums[mid]<target){
                start = mid+1;
            }

            //반대로 이번에는 미드 인덱스에 있는 값이 타깃 값보다 큰 경우입니다. 이 경우, 미드+1 부터 엔드 인덱스까지는 탐색을 할 필요가 없습니다. 이때는 엔드 값을 미드 + 1의 값으로 설정해 줍니다.
            else if(nums[mid]>target){
                end = mid-1;
            }

        }//wh

        return -1;

    }//main

}//main class








//네 번째 풀이는 재귀 호출을 활용하는 풀이입니다. 풀이 시간은 직전의 while 루프를 활용한 것과 마찬가지로 0밀리초로 측정되었고, 이번 풀이도 이진 탐색 로직을 적용해서 그런 것으로 생각됩니다.
class Solution {

    public int answer = -1;

    public int search(int[] nums, int target) {
        //return type is int

        binary(0, nums.length-1, nums, target);

        return answer;

    }//main

    public void binary(int start, int end, int[] nums, int target){

        //미드 인덱스 값을 이렇게 설정한 것은 직전 풀이에서 밝은 이유와 동일합니다.
        int mid = end + (start - end)/2;

        //네 번째 풀이에서도 이진 탐색이 진행되는 조건을 if 조건문을 활용해서 정확하게 한정시켜주는 것이 중요합니다. 이 부분이 없을 경우, 재귀호출이 끝없이 이어지다가 StackOverflow 에러를 내면서 오답처리 됩니다.
        if(start <= end){
            if(nums[mid]==target){
                answer = mid;
                return;
            }

            //미드 값이 타겟보다 작기 때문에 스타트 인덱스 값을 미드+1 값으로 설정하여 재귀호출을 해줍니다. 이로써 0~mid에 해당하는 인덱스는 더 이상 탐색을 진행하지 않아도 됩니다.
            if(nums[mid]<target){
                binary(mid+1, end, nums, target);
            }

            //미드 값이 타겟보다 크거나 같기 때문에 엔드 인덱스 값을 미드-1로 설정하여 재귀호출 해줍니다. 이로써 mid~end에 해당하는 인덱스는 더 이상 탐색을 진행할 필요하 없게 됩니다.
            else{
                binary(start, mid-1, nums, target);
            }
        }//Outer if

    }//func

}//main class
