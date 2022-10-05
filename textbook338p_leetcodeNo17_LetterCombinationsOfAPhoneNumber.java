//이번 문제는 어쩔 수 없이 모든 경우의 수를 다 탐색해야 하는 문제였습니다.
//실행시간은 0밀리초여서 큰 의미는 없었습니다.
//하지만 여기서 중요한 것은, 재귀 호출을 할 때는 메서드의 인자와 리턴 형식을 정확하게 설계해야 한다는 점이이었습니다.
class Solution {

    //>>> P <<<

    public List<String> letterCombinations(String digits) {
        //return type is List<String>
        
        String[] keyB = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz" };
       
        ArrayList<String> answer = new ArrayList<>();
        
        if(digits.length()==0) return answer;
        
        search(0, new StringBuilder(), digits, answer, keyB);
        
        return answer;
        
    }//main
    
    //무려 5개의 인자를 전달해야 했습니다. String[] keyB는 >>> P <<<의 위치에 private static final 변수로
    //선언할 경우 메서드의 인자를 5개에서 4개로 줄일 수 있습니다.
    public void search(int i, StringBuilder sb, String digits, List<String> answer, String[] keyB ){
        // 첫 파라미터인 i는 인트 타입이고, 세번째 파라미터인 digits는 String입니다.
        //입력으로 전달된 digits를 int 타입 숫자로 바꿔서 String[] keyB에서 탐색 대상 문자열을 골라낸 후,
        //다음 재귀호출을 실시할 때 i+1을 다음 재귀호출의 인자로 전달해야 하기 때문입니다.
        //두 번째 파라미터인 sb는 Stringbuilder 타입입니다. 일반 String 타입에 += 연산을 적용할 경우 프로그램의 성능이 크게 떨어지기 때문입니다. 그리고 무엇보다도, 진전 호출에서 만들어낸 결과물을 다음 재귀호출에 전달해주기 위해서 필요합니다.
        //네 번째 파라미터인 List<Stirng> answer는 마지막 재귀호출에서 sb를 전부 찾아낸 다음, 정답으로 리턴할 리스트에 값을 추가해야 하기 때문에 필요합니다.


        if(sb.length()==digits.length()){
            answer.add(sb.toString());
            return;
        }
        
        //digits.charAt(i)-'0' 의 의미를 이해하는 것이 필요합니다. int a = '1';을 출력해보면, 49이고, int b = '0';을 출력해보면 48입니다.
        //따라서 a-b == 1이 되고, 이는 '1'이라는 문자가 컴퓨터 공학적인 의미의 엄밀한 의미가 아니라 겉으로 보이는 값이 1과 일치합니다.
        //따라서 digits.charAt(i)-'0'는 사실 digits의 첫 번재 글자를 정수로 바꿔주는 것과 같습니다.
        String letters = keyB[digits.charAt(i)-'0'];
        for(char s : letters.toCharArray()){
            sb.append(s);
            search(i+1, sb, digits, answer, keyB);
            //이 부분이 없으면 정답을 도출해내지 못합니다. 그 이유는 아래에서 더 자세히 설명합니다.
            sb.deleteCharAt(sb.length() - 1);
        }
        
    }//func
    
}//main class

/*
입력값이 digits = "23"인 경우로 예를 들어보겠습니다.
함수호출 구조는 다음과 같습니다.
<첫 번째 호출 : digits의 첫 글자가 2이므로, String[] keyB의 2 인덱스의 문자열 사용>
<두 번재 호출 : digits의 두번째 글자가 3. keyB[3]의 문자열 사용>


첫 호출               <두 번째 호출>
a--------------------------d >>> a와 d를 sb에 찾았으므로, 이 sb를 List<Stirng> answer에 추가해야 합니다.
                           |
                           e >>> 이번 호출에서는 sb가 a와 e를 갖고 있어야 합니다. 그런데, 여기서 직전 호출의 sb인 ad에 그대로 e를 추가할 경우 오답이 됩니다.
                           |     d를 제거한 후, b를 더해야 정답입니다. 나머지 다른 호출들에 대해서도 마찬가지 압니다.
                           |
                           f

첫 호출               <두 번째 호출>
b--------------------------d ...
                           |
                           e ...
                           |
                           f ...

첫 호출               <두 번째 호출>
c--------------------------d ...
                           |
                           e ...
                           |
                           f ...


*/
