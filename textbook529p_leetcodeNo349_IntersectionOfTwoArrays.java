//실행시긴은 6밀리초였고, 이는 하위 34%의 성능이었습니다.
//첫 번째 입력 배열은 정렬하고, 두 번째 입력 배열의 내용을 순회하면서 첫 번째 배열에서 두 번째 입력 배열 내 요소를 이진탐색으로 찾아 봅니다.
//만약 찾아냈다면, 그 요소가 list에 포함돼 있지 않은 새로운 요소인 경우만 list에 포함시키고, 그 list의 내용물을 배열로 바꿔서 리턴합니다.
class Solution {

    public int answer = -1;

    public int[] intersection(int[] nums1, int[] nums2) {
        //return type is int[]

        ArrayList<Integer> list = new ArrayList<>();

        Arrays.sort(nums1);

        for(int j : nums2){
            answer = -1;
            binary(0, nums1.length-1, nums1, j);
            //정답을 구성하는 요소들이 유일해야 한다고 했으므로, 기존의 리스트에서 포함하고 있지 않은 요소만 추가할 수 있도록 해야 합니다. 기존의 요소들이 있는지 판단하기 위해서 ArrayList의 contains()메서드를 활용했습니다.
            if(answer!=-1 && !list.contains(j)) list.add(j);
        }

        int[] ansArray = new int[list.size()];

        for(int p=0; p<list.size(); p++){
            ansArray[p] = list.get(p);
        }

        return ansArray;

    }//Main

    public void binary(int start, int end, int[] inputArray, int target){
        int mid = end + (start-end)/2;

        if(start <= end){
            if(inputArray[mid]==target) {answer = mid;}

            if(target > inputArray[mid]){
                binary(mid+1, end, inputArray, target);
            }
            else{
                binary(start, mid-1, inputArray, target);
            }
        }//if

    }//func

}//main class







//실행시간은 첫 번째 풀이보다 2밀리초 느린 9밀리초로 측정되었고 이는 하위 14%의 성능이었습니다.
//첫 번째 풀이와 다른 점은 이진검색을 직접 구현하지 않고 Arrays.binarySearch()라는 자바에서 제공하는 static 메서드를 활용했다는 점입니다.
class Solution {

    public int[] intersection(int[] nums1, int[] nums2) {
        //return type is int[]

        ArrayList<Integer> list = new ArrayList<>();

        Arrays.sort(nums1);

        for(int j : nums2){
            int answer = Arrays.binarySearch(nums1, j);
            if(answer>-1 && !list.contains(j)) list.add(j);
        }

        int[] ansArray = new int[list.size()];

        for(int p=0; p<list.size(); p++){
            ansArray[p] = list.get(p);
        }

        return ansArray;

    }//Main

}//main class









//세 번째 풀이는 HashSet을 이용해봤습니다. HashSet 자체가 요소들 간의 중복을 허용하지 않는 자료구조이므로, 이 문제를 매우 쉽게 해결할 수 있게 해주는 자료구조라고 할 수 있습니다.
//실행시간은 두 번째 풀이와 동일한 9밀리초였습니다.
class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> set = new HashSet<>();
        Set<Integer> set2 = new HashSet<>();
        for(int i : nums1){
            set.add(i);
        }
        for(int i : nums2){
            if(set.contains(i)){
                set2.add(i);
            }
        }
        int[] arr = set2.stream().mapToInt(Integer::intValue).toArray();
        return arr;
    }
}
