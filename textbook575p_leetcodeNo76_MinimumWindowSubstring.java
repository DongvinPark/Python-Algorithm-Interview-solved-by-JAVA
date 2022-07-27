//첫 번째 풀이는 실패한 풀이입니다. 입력값이 s="bdab", t="ab"로 주어지는 경우를 통과하지 못해서
//266개의 테스트 케이스 중, 155번째 테스트 케이스인 위와 같은 경우에서 오답처리 됩니다.
//원인은 bda를 첫 번째 정답으로 확정한 후, a를 다시 확인하지 않고 다음 for루프로 넘아가 버리기 때문입니다.
//따라서, ab라는 진짜 정답을 찾아내지를 못한채 오답인 "bda"를 제출하고 마는 것입니다.
class Failed_Solution {
    public String minWindow(String s, String t) {
        //return type is String

        if(s.equals(t)) return s;

        LinkedList<Character> ans = new LinkedList<>();

        LinkedList<Character> copy = new LinkedList<>();

        for(char c : s.toCharArray()){ ans.addLast(c); }
        for(char c : t.toCharArray()){ copy.addLast(c); }

        char[] input = s.toCharArray();

        LinkedList<Character> newOne = new LinkedList<>();

        for(int i=0; i<input.length; i++){

            if(newOne.isEmpty() && !copy.contains(input[i])) continue;

            //필요한 문자를 찾았다!!
            if(copy.contains(input[i])){
                System.out.println("found!!");
                newOne.addLast(input[i]);
                copy.removeFirstOccurrence(input[i]);
            }

            //원본문자를 하나라도 찾은 다음에 만나는 다른 문자는 일단 newOne에 포함시켜줘야 한다.
            else if(!newOne.isEmpty() && !copy.contains(input[i])){
                newOne.addLast(input[i]);
            }

            //만약 이번 루프에서 원본 문자를 전부 찾았다면?
            //ans 리스트와 뉴원을 길이 비교하고, 둘 중 더 짧은 놈으로 ans를 초기화한다.
            //그리고 copy를 다시 채워 넣는다. 단, 이것은 현재 탐색 인덱스가 마지막 인덱스가 아닌 경우에 한해서다.
            // 아무튼 이것은 다음 탐색을 위해서다.
            if(copy.isEmpty()){
                //System.out.println("다음 탐색 리셋");
                if(newOne.size() <= ans.size()){
                    ans.clear();
                    for(char ch : newOne){ans.addLast(ch);}
                    if(i!=input.length-1){
                        for(char cha : t.toCharArray()){copy.addLast(cha);}
                    }
                }
                if(i!=input.length-1){
                    newOne.clear();
                }
            }

        }//for

        String before = "";
        for(char ch : ans){before += ch;}

        if(!copy.isEmpty() && before.equals(s)) {
            return "";
        }
        else{
            StringBuilder sb = new StringBuilder();
            for(char cha : ans){
                sb.append(cha);
            }
            return sb.toString();
        }


    }//main
}//main class







//두 번째 풀이는 해시맵 2개를 동원한 제법 복잡한 풀이였습니다.
//실행시간은 16~38밀리초였는데, 이는 하위 22% ~ 상위 32% 정도의 성능이었습니다.
/*
* 입력문자열이 아래와 같을 때 실제로 코드가 어떻게 동작하는지를 추적하여 로그로 남긴 부분을
* 함께 참고하시면 이해하는 데 도움이 될 것이라 생각합니다.
입력 문자열 s : APKBECKPEBANC
입력 문자열 t : ABC
Solution Called~~


---------------0 th for 루프 시작---------------
현재 판단 대상 문자 : A
	while진입 전 sMap 현황
		키 : A/등장횟수 : 1
	문자를 찾음!!
	while루프 진입하는가?


---------------1 th for 루프 시작---------------
현재 판단 대상 문자 : P
	while진입 전 sMap 현황
		키 : P/등장횟수 : 1
		키 : A/등장횟수 : 1
	while루프 진입하는가?


---------------2 th for 루프 시작---------------
현재 판단 대상 문자 : K
	while진입 전 sMap 현황
		키 : P/등장횟수 : 1
		키 : A/등장횟수 : 1
		키 : K/등장횟수 : 1
	while루프 진입하는가?


---------------3 th for 루프 시작---------------
현재 판단 대상 문자 : B
	while진입 전 sMap 현황
		키 : P/등장횟수 : 1
		키 : A/등장횟수 : 1
		키 : B/등장횟수 : 1
		키 : K/등장횟수 : 1
	문자를 찾음!!
	while루프 진입하는가?


---------------4 th for 루프 시작---------------
현재 판단 대상 문자 : E
	while진입 전 sMap 현황
		키 : P/등장횟수 : 1
		키 : A/등장횟수 : 1
		키 : B/등장횟수 : 1
		키 : E/등장횟수 : 1
		키 : K/등장횟수 : 1
	while루프 진입하는가?


---------------5 th for 루프 시작---------------
현재 판단 대상 문자 : C
	while진입 전 sMap 현황
		키 : P/등장횟수 : 1
		키 : A/등장횟수 : 1
		키 : B/등장횟수 : 1
		키 : C/등장횟수 : 1
		키 : E/등장횟수 : 1
		키 : K/등장횟수 : 1
	문자를 찾음!!
	while루프 진입하는가?
	!*!문자를 전부 찾음. while진입
		일단 정답을 기록함.
		>>기록된 정답 : APKBEC
		정답 기록후 윈도우 시작 및 끝 인덱스 : 0/5
sMap에서 삭제 예정인 문자. 즉, windowStart인덱스 문자 : A
			sMap에서 A 완전제거
	sMap에서 문자 삭제 후 윈도우 시작 인덱스값 : 1
			found 변수 -- 해줌


---------------6 th for 루프 시작---------------
현재 판단 대상 문자 : K
	while진입 전 sMap 현황
		키 : P/등장횟수 : 1
		키 : B/등장횟수 : 1
		키 : C/등장횟수 : 1
		키 : E/등장횟수 : 1
		키 : K/등장횟수 : 2
	while루프 진입하는가?


---------------7 th for 루프 시작---------------
현재 판단 대상 문자 : P
	while진입 전 sMap 현황
		키 : P/등장횟수 : 2
		키 : B/등장횟수 : 1
		키 : C/등장횟수 : 1
		키 : E/등장횟수 : 1
		키 : K/등장횟수 : 2
	while루프 진입하는가?


---------------8 th for 루프 시작---------------
현재 판단 대상 문자 : E
	while진입 전 sMap 현황
		키 : P/등장횟수 : 2
		키 : B/등장횟수 : 1
		키 : C/등장횟수 : 1
		키 : E/등장횟수 : 2
		키 : K/등장횟수 : 2
	while루프 진입하는가?


---------------9 th for 루프 시작---------------
현재 판단 대상 문자 : B
	while진입 전 sMap 현황
		키 : P/등장횟수 : 2
		키 : B/등장횟수 : 2
		키 : C/등장횟수 : 1
		키 : E/등장횟수 : 2
		키 : K/등장횟수 : 2
	while루프 진입하는가?


---------------10 th for 루프 시작---------------
현재 판단 대상 문자 : A
	while진입 전 sMap 현황
		키 : P/등장횟수 : 2
		키 : A/등장횟수 : 1
		키 : B/등장횟수 : 2
		키 : C/등장횟수 : 1
		키 : E/등장횟수 : 2
		키 : K/등장횟수 : 2
	문자를 찾음!!
	while루프 진입하는가?
	!*!문자를 전부 찾음. while진입
sMap에서 삭제 예정인 문자. 즉, windowStart인덱스 문자 : P
			sMap에서 P 등장횟수 1감소
	sMap에서 문자 삭제 후 윈도우 시작 인덱스값 : 2
	!*!문자를 전부 찾음. while진입
sMap에서 삭제 예정인 문자. 즉, windowStart인덱스 문자 : K
			sMap에서 K 등장횟수 1감소
	sMap에서 문자 삭제 후 윈도우 시작 인덱스값 : 3
	!*!문자를 전부 찾음. while진입
sMap에서 삭제 예정인 문자. 즉, windowStart인덱스 문자 : B
			sMap에서 B 등장횟수 1감소
	sMap에서 문자 삭제 후 윈도우 시작 인덱스값 : 4
	!*!문자를 전부 찾음. while진입
sMap에서 삭제 예정인 문자. 즉, windowStart인덱스 문자 : E
			sMap에서 E 등장횟수 1감소
	sMap에서 문자 삭제 후 윈도우 시작 인덱스값 : 5
	!*!문자를 전부 찾음. while진입
sMap에서 삭제 예정인 문자. 즉, windowStart인덱스 문자 : C
			sMap에서 C 완전제거
	sMap에서 문자 삭제 후 윈도우 시작 인덱스값 : 6
			found 변수 -- 해줌


---------------11 th for 루프 시작---------------
현재 판단 대상 문자 : N
	while진입 전 sMap 현황
		키 : P/등장횟수 : 1
		키 : A/등장횟수 : 1
		키 : B/등장횟수 : 1
		키 : E/등장횟수 : 1
		키 : K/등장횟수 : 1
		키 : N/등장횟수 : 1
	while루프 진입하는가?


---------------12 th for 루프 시작---------------
현재 판단 대상 문자 : C
	while진입 전 sMap 현황
		키 : P/등장횟수 : 1
		키 : A/등장횟수 : 1
		키 : B/등장횟수 : 1
		키 : C/등장횟수 : 1
		키 : E/등장횟수 : 1
		키 : K/등장횟수 : 1
		키 : N/등장횟수 : 1
	문자를 찾음!!
	while루프 진입하는가?
	!*!문자를 전부 찾음. while진입
sMap에서 삭제 예정인 문자. 즉, windowStart인덱스 문자 : K
			sMap에서 K 완전제거
	sMap에서 문자 삭제 후 윈도우 시작 인덱스값 : 7
	!*!문자를 전부 찾음. while진입
sMap에서 삭제 예정인 문자. 즉, windowStart인덱스 문자 : P
			sMap에서 P 완전제거
	sMap에서 문자 삭제 후 윈도우 시작 인덱스값 : 8
	!*!문자를 전부 찾음. while진입
		일단 정답을 기록함.
		>>기록된 정답 : EBANC
		정답 기록후 윈도우 시작 및 끝 인덱스 : 8/12
sMap에서 삭제 예정인 문자. 즉, windowStart인덱스 문자 : E
			sMap에서 E 완전제거
	sMap에서 문자 삭제 후 윈도우 시작 인덱스값 : 9
	!*!문자를 전부 찾음. while진입
		일단 정답을 기록함.
		>>기록된 정답 : BANC
		정답 기록후 윈도우 시작 및 끝 인덱스 : 9/12
sMap에서 삭제 예정인 문자. 즉, windowStart인덱스 문자 : B
			sMap에서 B 완전제거
	sMap에서 문자 삭제 후 윈도우 시작 인덱스값 : 10
			found 변수 -- 해줌
Final Answer : BANC
* */
class Solution {
    public String minWindow(String s, String t) {

        String answer = "";

        //예외 처리를 해줍니다.
        if(s.length() < t.length()){
            return answer;
        }

        int minWindow = Integer.MAX_VALUE;

        //두 종류의 변수를 정의해줍니다. needs는 우리가 찾아야 하는 문자의 개수이고,
        // found는 우리가 이미 찾은 문자의 개수입니다.
        int needs = t.length();
        int found = 0;

        //문자값을 키로 가지고, 해당 키가 등장한 횟수를 값으로 가지는 해시맵 2개를 정의해 줍니다.
        // 그 후, tMap을 입력 문자열 중 하나인 t로 초기화 해줍니다.
        //tMap의 경우, 우리가 찾아야 하는 문자들의 등장 빈도를 기록하게 됩니다. 예를 들어서 입력문장열인 t가
        //"ABC"라면, tMap은 <'A',1> <'B',1> <'C',1> 이렇게 3개의 맵을 갖게 됩니다.
        Map<Character, Integer> sMap = new HashMap<>();
        Map<Character, Integer> tMap = new HashMap<>();
        for(int i=0; i<t.length(); i++){
            char ch = t.charAt(i);
            if(!tMap.containsKey(ch)){
                tMap.put(ch,1);
            }
            else{
                int before = tMap.get(ch);
                before++;
                tMap.put(ch,before);
            }
        }

        //슬라이딩 윈도우의 시작점 인덱스를 0으로 초기화 해줍니다.
        int windowStart = 0;

        for(int windowEnd = 0; windowEnd<s.length(); windowEnd++){
            //System.out.println("\n\n---------------" + windowEnd + " th for 루프 시작---------------");


            //현재 입력문자열 s에서 판단의 대상으로 삼고 있는 문자를 정의해 놓습니다.
            char curChar = s.charAt(windowEnd);
            //System.out.println("현재 판단 대상 문자 : " + curChar);
            //그 후, 현재 문자인 curChar를 sMap에 기록해줍니다. sMap에서는 루프가 진행 될수록
            //입력 문자열 s의 문자들에 대하여 차례대로 등장빈도수를 기록하게 됩니다.
            sMap.put(curChar, sMap.getOrDefault(curChar,0)+1);
            //System.out.println("\twhile진입 전 sMap 현황");
            for(char ch : sMap.keySet()){
                //System.out.println("\t\t키 : " + ch + "/등장횟수 : " + sMap.get(ch));
            }

            //if we have encountered a useful character, we will increment have variable.
            if(tMap.containsKey(curChar)
                    && sMap.get(curChar)<=(tMap.get(curChar))
            ){
                //System.out.println("\t문자를 찾음!!");
                found++;
            }

            //만약 found와 needs가 서로 일치한다면, 입력 문자열인 t에서 찾아야 하는 문자는 적어도 이번 for루프에서
            //전부 찾은 것이라고 판단할 수 있습니다.
            //System.out.println("\twhile루프 진입하는가?");
            while(found==needs){
                //System.out.println("\t!*!문자를 전부 찾음. while진입");
                //그렇다면, 현재 찾아놓은 문자들에 맞춰서 일단 정답을 기록해 놓습니다.
                if(minWindow > windowEnd-windowStart+1){
                    //System.out.println("\t\t일단 정답을 기록함.");
                    minWindow = windowEnd-windowStart+1;
                    answer = s.substring(windowStart, windowEnd+1);
                    //System.out.println("\t\t>>기록된 정답 : " + answer);
                    //System.out.println("\t\t정답 기록후 윈도우 시작 및 끝 인덱스 : " + windowStart + "/" + windowEnd);
                }

                //위에서 일단 정답을 기록해 놓기는 했지만, 우리는 문제에서 제시한 조건을 만족하는 최소 길이의
                //문자열을 찾아야하므로 현재 정답으로 기록해놓은 문자열보다 더 길이가 짧은 문자열을 찾으려는 시도를
                //계속해야 합니다.
                char charToRemove = s.charAt(windowStart);
                //System.out.println("sMap에서 삭제 예정인 문자. 즉, windowStart인덱스 문자 : " + charToRemove);

                //다음 탐색을 위해서는 그동안 탐색을 위해서 채워 놓은 sMap을 비워줘야 합니다.
                if(sMap.get(charToRemove) == 1){
                    //1번만 등장한 경우엔 sMap에서 완전 제거해주고,
                    //System.out.println("\t\t\tsMap에서 "+ charToRemove + " 완전제거");
                    sMap.remove(charToRemove);
                }
                else{
                    //2번 이상 등장했던 문자는 등장 횟수를 감소시켜줍니다.
                    //System.out.println("\t\t\tsMap에서 "+ charToRemove + " 등장횟수 1감소");
                    sMap.put(charToRemove, sMap.get(charToRemove)-1);
                }

                //sMap에서 등장횟수를 감소시켜주거나, 삭제해준 후, windowStart를 ++해줍니다.
                //그래야 다음 탐색에서 정답을 정확히 도출해 낼 수 있기 때문입니다.
                windowStart++;
                //System.out.println("\tsMap에서 문자 삭제 후 윈도우 시작 인덱스값 : " + windowStart);

                //다음 탐색을 위해서 found 변수의 횟수를 감소시켜줘야 합니다. sMap에서 삭제한 문자가
                //tMap에서도 등장했던 문자이고, sMap에서 charToRemove가 등장한 횟수가 tMap에서
                //등장한 횟수보다 작다면 found--;를 해줍니다.
                if(
                        tMap.containsKey(charToRemove) &&
                  sMap.getOrDefault(charToRemove,0)<(tMap.get(charToRemove))
                ){
                    //System.out.println("\t\t\tfound 변수 -- 해줌");
                    found--;
                }
            }//while

        }//for

        return answer;
    }
}//main class
