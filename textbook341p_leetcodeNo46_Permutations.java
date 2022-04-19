//이번 풀이는 DFS의 백트래킹을 이용해서 풀 수 있습니다.
//실행시간은 0밀리초로 나와서 큰 의미는 없습니다.
//풀이과정에서 사용한 논리는 리트코드 17번 문제이자 교재 338쪽에서 등장하는 '전화 번호 문자 조합'과 동일합니다. 깃허브 링크는 다음과 같습니다.
// https://github.com/DongvinPark/Python-Algorithm-Interview-solved-by-JAVA/blob/main/textbook338p_leetcodeNo17_LetterCombinationsOfAPhoneNumber.java
class Solution {
    public List<List<Integer>> permute(int[] nums) {
        //return type is List< List<Integer> >
        List< List<Integer> > answer = new ArrayList<>();
        
        if(nums.length==0) return answer;
        
        permu(new ArrayList<Integer>(), nums, answer);
        
        return answer;
        
    }//main
    
    public static void permu(List<Integer> sb, int[] nums, List<List<Integer>> answer){
        if(sb.size() == nums.length){

            //리트코드 17번 문제인 '전화 번호 문자 조합' 과 풀이방법이 동일하지만, 중대한 차이점은 바로 이 부분입니다.
            //자바에서 객체는 참조를 이용해서 접근합니다. 재귀호출을 통해서 sb가 여러번 전달되기는 하지만 전부 같은 sb 객체입니다.
            //따라서 new 연산 없이 단순히 answer.add(sb); 라고 처리할 경우, answer 리스트의 모든 요소들이 sb라는 동일한 참조를 가지고 있는 상황이 됩니다.
            //문제는 아래쪽에 있는 for문의 sb.remove(sb.size()-1); 부분에서 발생합니다.
            //재귀호출이 끝난 후, 그동안 미뤄왔던 sb.remove(sb.size()-1);이 연속해서 실행되는 것입니다.
            //이 때, answer 리스트의 모든 요소들이 전부 동일한 sb 참조를 내용물로서 포함하고 있기 때문에, 모든 재귀호출이 종료된 후에 sb의 내용물은 전부 remove() 되고 결과적으로 asnwer의 내용물 또한 전부 빈 리스트가 되고 맙니다.
            //이러한 일을 막기 위해서는 아래와 같이 sb를 answer에 추가해 줄 때, new 연산을 사용해서 얕은 복사가 아닌 깊은 복사를 통해 값을 전달해줘야 정상적으로 답을 구할 수 있습니다.
            //참조를 전달하는 것이 아닌, 리터럴 값 자체를 answer.add()한다면 new 연산은 필요 없습니다.
            answer.add(new ArrayList<>(sb));
            return;
        }
        
        HashMap<Integer,String> hm = new HashMap<>();
        for(int i : nums){
            hm.put(i,"");
        }
        for(int j : sb){
            hm.remove(j);
        }
        
        
        for(int k : hm.keySet()){
            sb.add(k);
            permu(sb, nums, answer);
            sb.remove(sb.size()-1);
        }
        
    }//func
    
}//main class
