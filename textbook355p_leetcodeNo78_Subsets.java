//textbook355p_leetcodeNo78_Subsets.java
//실행시간은 1~3밀리초를 왔다갔다 하고, 제출된 풀이의 거의 전부가 1~3밀리초 구간에만 존재해서 실행시간을 따지는 것이 큰 의미는 없었습니다.
//DFS 재귀호출 방법으로 풀이하는 것은 순열, 조합, 중복조합 문제를 해결했을 때와 동일한데, List<List<Integer>> answer에 정답을 추가해나가는 과정에서 차이가 납니다.
class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        //retutn type is List< List<Integer> >
        List<List<Integer>> answer = new ArrayList<>();

        //모든 집합은 공집합을 부분집합으로 갖기 때문에 공집합을 추가해 먼저 추가해 줘야 합니다.
        answer.add(new ArrayList<>());

        //입력배열이 sort 돼 있어야 DFS 재귀호출로 문제를 풀기가 수월해집니다.
        Arrays.sort(nums);

        //여기서 insert.add(nums[0]-1); 을 해주지 않을 경우, DFS 재귀메서드의 if(j > sb.get(sb.size()-1))에서 get을 호출할 때, sb에 아무 요소도 없는 상태에서 sb.get(0)를 호출하게 됩니다. 그러면 index 범위 오류가 뜨기 때문에 이러한 오류를 방지하기 위한 조치입니다.
        ArrayList<Integer> insert = new ArrayList<>();
        insert.add(nums[0]-1);

        int factor = 1;

        sub(insert, nums, answer, factor);

        return answer;

    }//main

    public static void sub(
            //자바에서 DFS 재귀 메서드를 정의할 때 넘겨줘야 할 파라미터들이 많은 편이어서 파라미터들을 라인 별로 하나씩 정의해서 나열하는 게 문제풀이하는 과정에서 확인하기 편리했던 것 같습니다.
            ArrayList<Integer> sb,
            int[] nums,
            List<List<Integer>> answer,
            int factor
    ){
        //nums 배열에서 나올 수 있는 최대 길이의 부분집합에 도달한 경우, 재귀호출을 멈춰야 합니다.
        if(factor == nums.length+1) {return;}

        for(int j : nums){
            //j가, 현재 sb에 있는 요소들 중 가장 마지막 요소보다 큰 경우에만 sb에 추가하고, answer에 추가해줍니다.
            //이때, sb에는 DFS 재귀호출을 시작하기 전에 미리 넣어놨던, nums[0]-1 이 들어있기 때문에 이것을 제외한 나머지 sb들을 answer에 추가해주도록 합니다.
            if(j > sb.get(sb.size()-1)){
                sb.add(j);

                ArrayList<Integer> temp = new ArrayList<>();
                for(int i=1; i<sb.size(); i++){
                    temp.add(sb.get(i));
                }//for

                answer.add(temp);

                sub(sb, nums, answer, factor+1);

                sb.remove(sb.size()-1);
            }//if

        }//for

    }//func
}//main class
