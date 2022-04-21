//첫 번째 풀이는 25밀리초의 실행시간이 소요되었고 이는 하위 약 8%의 성능이었습니다.
//풀이와 관련된 로직은 교재 346쪽, 리트코드 77번 문제인 조합 구하기 문제와 거의 같습니다. 해당 문제에 대해 해설한 깃허브 링크를 공유해드립니다.
//   https://github.com/DongvinPark/Python-Algorithm-Interview-solved-by-JAVA/blob/main/textbook346p_leetcodeNo77_Combinations.java
//단, 여기서 다른 점은 중복조합으로 선택해야 하므로 아래 코드의 >>> A <<< 부분의 if 비교문에서 '=' 비교 연산자도 포함하고 있다는 점입니다.
class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
    // retutn type : List< Lsit<Integer> >
        List<List<Integer>> answer = new ArrayList<>();
        
        ArrayList<Integer> input = new ArrayList<>();
        input.add(0);
        
        hCombi(candidates, input, answer, target);
        
        return answer;
        
    }//main
    
    public static void hCombi( 
        int[] candidates,
        ArrayList<Integer> sb,
        List<List<Integer>> answer,
        int target
    ){

        //실행시간이 느리게 나온 부분은 for를 통해서 Hcombi 메서드가 호출될 때마다 일일이 sum을 계산하였기 때문입니다.
        //두 번재 풀이는 로직이 첫 번째 풀이와 거의 똑같은데, sum을 계산하지 않고 Hcombi()메서드가 재귀적으로 호출될 때마다 sb에 추가된 요소중 가장 마지막에 추가된 요소를 target에서 뺀 결과를 인자로 전해줍니다.
        //모든 Hcombi()호출에 대한 sum 계산을 해줄 필요없게 되면서 실행 속도가 더 빨라진 것을 확인할 수 있었습니다.
        int sum = 0;
        for(int i : sb) sum += i;

        if(sum == target){
            ArrayList<Integer> r = new ArrayList<>(sb);
            r.remove(0);
            answer.add(r);
        }
        if(sum > target){return;}
        
        for(int j : candidates){
            // >>> A <<<
            if(j >= sb.get(sb.size()-1)) {
                sb.add(j);
            hCombi(candidates, sb, answer, target);
            sb.remove(sb.size()-1);
            }        
        }
    }//func
    
}//main class








//두 번째 풀이는 6밀리초의 실행시간이 나오면서 첫 번째 풀이보다 실행시간이 약 4배 정도 빨라졌습니다. 이는 상위 약 47%의 성능이었습니다.
class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
    // retutn type : List< Lsit<Integer> >
        List<List<Integer>> answer = new ArrayList<>();
        
        ArrayList<Integer> input = new ArrayList<>();
        input.add(0);
        
        int factor = target;
        
        hCombi(candidates, input, answer, target);
        
        return answer;
        
    }//main
    
    public static void hCombi( 
        int[] candidates,
        ArrayList<Integer> sb,
        List<List<Integer>> answer,
        int target
    ){
        if(target == 0){
            ArrayList<Integer> r = new ArrayList<>(sb);
            r.remove(0);
            answer.add(r);
            return;
        }
        if(target < 0){return;}
        
        for(int j : candidates){
            if(j >= sb.get(sb.size()-1)){
                sb.add(j);
            hCombi(candidates, sb, answer, target-sb.get(sb.size()-1));
            sb.remove(sb.size()-1);
            }        
        }
    }//func
    
}//main class
