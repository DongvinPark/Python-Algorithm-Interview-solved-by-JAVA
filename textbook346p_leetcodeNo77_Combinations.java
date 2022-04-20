//첫 번째 풀이의 실행시간은 98밀리초였고, 이는 하위 11%의 성능이었습니다.
//오름차순으로 정렬된 상태에서 조합을 골라내면 되므로, 현재 List<Integer> sb에 들어있는 내용물 중 가장 마지막 인덱스의 값보다 큰 경우에만 재귀함수를 호출하는 방식으로 풀이하였습니다.
class Solution {
    public List<List<Integer>> combine(int n, int k) {
        //return type is List< List<Integer> >
        List< List<Integer> > answer = new ArrayList<>();

        int[] nums = new int[n];
        for(int i=0; i<n; i++){
            nums[i] = i+1;
        }

        //   >>> A <<<
        ArrayList<Integer> input = new ArrayList<>();
        input.add(0);

        combi(nums, k, input, answer);

        return answer;

    }//main

    public static void combi(int[] nums, int k, ArrayList<Integer> sb, List<List<Integer>> answer ){

        if(sb.size() == k+1){
            //최초로 combi() 메서드를 호출할 때 0을 추가한 다음 재귀호출을 진행했으므로, k+1일 때 멈춰야 합니다.
            //또한, answer 리스트의 모든요소들에 동일한 sb 참조가 대입되는 것을 방지하기 위해서 sb의 요소들을 input 리스트에 복사하고, input 리스트에서 0인덱스 요소를 제거한 다음에 answer리스트에 넣어줍니다.
            ArrayList<Integer> input = new ArrayList<>(sb);
            input.remove(0);
            answer.add(input);
            return;
        }

        for(int p : nums){
            //하지만, 이렇게 풀 경우 처음에 값을 추가할 때 sb에 아무런 요소도 없으므로, indexOutOfRange 오류에 걸립니다. 그래서 combi()메서드를 최초로 호출하기 전에 >>> A <<< 파트와 같이 미리 0을 추가한 다음 sb를 최초의 combi()메서드에 파라미터로서 넘겨줍니다.
                if(p > sb.get(sb.size()-1)){
                    sb.add(p);
                    combi(nums, k, sb, answer);
                    sb.remove(sb.size()-1);
                }


        }//for
    }//func

}//main class




//두 번째 풀이는 교재에 실려 있는 파이썬 풀이 코드를 자바로 다시 구현한 코드입니다.
//실행시간은 23밀리초였고, 이는 상위 33%에 해당하는 성능이었습니다.
class Solution {
    public List<List<Integer>> combine(int n, int k) {
        //return type is List< List<Integer> >
        List< List<Integer> > answer = new ArrayList<>();


        combi(n, k, 1, new ArrayList<>(), answer);

        return answer;

    }//main

    public static void combi(int n, int k, int start, ArrayList<Integer> sb, List<List<Integer>> answer ){

        if(k == 0){
            answer.add(new ArrayList<>(sb));
            return;
        }
        for(int i = start; i<n+1; i++){               
                    sb.add(i);
                    //호출이 반복될수록 k 값이 줄어들고, start 인덱스의 값이 1씩 증가하는 것에 주목해야 합니다. 이를 통해서 바로 위의 if절에서 적절한 요소들을 asnwer 리스트에 정답으로서 추가할 수 있게 됩니다.
                    combi(n, k-1, i + 1 ,sb, answer);
                    sb.remove(sb.size()-1);
        }//for
    }//func

}//main class
