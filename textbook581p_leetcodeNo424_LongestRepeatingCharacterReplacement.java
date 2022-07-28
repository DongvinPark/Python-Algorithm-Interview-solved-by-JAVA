//첫 번째 풀이는 제한시간 초과 판정을 받으며 오답처리된 답입니다.
//mostCharNum()메서드에서 최빈 문자의 등장 횟수를 처리하는 로직이 모든 for루프마다
//실행되는 것으로 인해서 시간이 오래 걸린 것이 원인으로 생각됩니다.
class Failed_Solution {
    public int characterReplacement(String s, int k) {
        //return type is int

        if(s.length() < k) return 0;

        if(s.length() == k+1 || s.length()==k) return s.length();

        int maxLength = 0;

        int left = 0;

        for(int right = 1; right<s.length(); right++){
            String sub = s.substring(left,right+1);

            int mostNum = mostCharNum(left,right,s);

            if((right-left+1 - mostNum) <= k ){
                int newOne = right-left+1;
                if(newOne >= maxLength){ maxLength = newOne; }
            }
            else if((right-left+1 - mostNum) > k){
                left++;
            }

        }//for

        return maxLength;

    }//main

    public int mostCharNum (int l, int r, String s){
        String sub = s.substring(l,r+1);
        char[] car = sub.toCharArray();
        int max = 0;
        HashMap<Character, Integer> map = new HashMap<>();
        for(char c : car){
            map.put(c, map.getOrDefault(c,0)+1);
        }
        for(char ch : map.keySet()){
            if(map.get(ch)>=max){
                max = map.get(ch);
            }
        }
        return max;
    }//func

}//main class








//두 번째 풀이의 실행시간은 15밀리초였고, 이는 상위 49%로 전체 정답들 중 중간 정도의 성능을 보여줬습니다.
//입력 문자열 : ANKAABACACD / k = 3 인 경우에 대해서 아래의 코드가 실제로 어떻게 작동하는지를 추적하고
//로그로 남긴 내용을 마지막에 첨부했습니다. 코드와 같이 참고하시면서 보면 이해하는 것에 도움이 될 듯합니다.
class Solution {
    public int characterReplacement(String s, int k) {
        //return type is int

        int answer = 0;
        int maxCount = 0;

        //문자들의 등장 빈도를 기록할 해시맵을 정의해줍니다.
        Map<Character, Integer> frequencies = new HashMap<>();

        //
        for (int left = 0, right = 0; right < s.length(); right++) {

            //현재 판단 대상이 되는 문자를 기록해줍니다.
            char rightChar = s.charAt(right);

            // 현재 판단 대상이 되는 문자를 해시맵에 넣어서 등장 빈도수가 누적되면서 기록될 수 있게 합니다.
            //해시맵에서 지원하는 메서드들 중 하나인 getOrDefault()메서드는 이러한 상황에서 쓰기에 딱 맞는 메서드입니다.
            //루프를 돌면서 right 인덱스 값이 입력 문자열의 처음부터 끝까지 전부 살펴보기 때문에 결과적으로 입력 문자열
            //내의 모든 문자들이 적어도 1번은 해시맵에 입력되서 등장빈도수가 기록되게 됩니다.
            frequencies.put(rightChar, frequencies.getOrDefault(rightChar, 0) + 1);

            //현재 판단 대상으로 삼고 있는 문자의 등장 빈도수와 이전 루프(첫 루프라면 maxCount는 0입니다)에서
            //기록했었던 maxCount를 비교해여 둘 중 더 큰 값으로 maxCount를 새로 설정해줍니다.
            //maxCount는 결과적으로 s.substring(left,right+1) 내에서 가장 자주 등장한 문자의 등장 횟수를 뜻합니다.
            //예를 들어서 left == 0, right == 3이라면, s.subString(left,right+1)=="ANKA"이고, 여기에서
            //가장 자주 등장한 문자는 A이며 A의 등장 횟수인 2로 maxCount가 초기화 되는 것입니다.
            maxCount = Math.max(maxCount, frequencies.get(rightChar));

            /*
            * 그렇다면, 왜 left,right 인덱스 값으로 추출한 서브문자열에서 가장 자주 등장하는 문자열의 등장 횟수를
            * 구하려고 하는지를 이해하는 것이 문제 풀이의 핵심입니다.
            * 예를 들어서 서브 스트링이 ACACA 이고, k값이 2라고 가정해보겠습니다.
            * 그렇다면, 서브스트링의 길이(right-left+1==5)에서 macCount(A의 등장횟수==3)를 뺀 값인 2가 K와 일치
            * 하게 됩니다. 이것이 바로 우리가 찾던 문자열인 것입니다.
            *
            * 이 부분을 이해하고나면, while문이 어떤 역할을 하는지도 이해할 수 있습니다.
            * 만약 서브스트링이 ACACACA 인데, k는 2라면, 해당 서브스트링은 어떤 문자를 A로 바꾼다 해도 절대
            * AAAAAAA가 될 수는 없습니다. 따라서 서브스트링을 추출할 때 시작점 인덱스 역할을 하는 left를 ++해줘야
            * 하는 것입니다. while은 이것을 하기 위해서 존재합니다.
            * */


            while (right - left + 1 - maxCount > k) {

                //while문에 진입한 직후, 삭제 예정인 문자를 기록해 놓습니다.
                //해당 문자는 탐색 범위가 아니기에 해시맵에서 등장횟수를 감소시켜주는 것이 필수적입니다.
                //그 후, 그 문자를 frequencies 해시맵에서 등장 횟수를 감소시켜 주고 left를 ++해 줍니다.
                char leftChar = s.charAt(left);
                frequencies.put(leftChar, frequencies.get(leftChar) - 1);
                left++;

                //이런 식으로 while 루프를 반복하면서 left 인덱스 값이 증가하다보면,
                // right-left+1-maxCount 값은 증가할 수밖에 없으며, 결국 while루프를 탈출하게 됩니다.
            }//wh

            //루프를 탈출한 후에는 원래 있던 answer값과 현재 for루프에서 찾아낸 서브스트링의 길이값(==right-left+1)
            //중에서 더 큰 값으로 answer를 새로 초기화해줍니다.
            answer = Math.max(answer, right - left + 1);
            //System.out.println("while 루프 탈출!! answer : " + answer);
        }//for

        return answer;
    }//main
}//main class

/*    입력 문자열 : ANKAABACACD / k = 3
        Solution Called!

        ----------------- 0 th for 진입 -----------------
        인덱스 : 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
        입력값 : A, N, K, A, A, B, A, C, A, C, D,
        루프 진입 직후 좌/우 인덱스 값 : 0/0
        현재 서브스트링 : A
        현재 right 인덱스 문자 : A
        >> rightChat 등록 후 frequencies 맵 현황
        A : 1
        현재 서브스트링 내 최빈문자 등장횟수(maxCount) : 1
        서브스트링 길이-maxCount/k : 0/3
        서브스트링 길이-maxCount > k 일 때만 while 루프 진입한다.
        while 루프 탈출!! answer : 1

        ----------------- 1 th for 진입 -----------------
        인덱스 : 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
        입력값 : A, N, K, A, A, B, A, C, A, C, D,
        루프 진입 직후 좌/우 인덱스 값 : 0/1
        현재 서브스트링 : AN
        현재 right 인덱스 문자 : N
        >> rightChat 등록 후 frequencies 맵 현황
        A : 1
        N : 1
        현재 서브스트링 내 최빈문자 등장횟수(maxCount) : 1
        서브스트링 길이-maxCount/k : 1/3
        서브스트링 길이-maxCount > k 일 때만 while 루프 진입한다.
        while 루프 탈출!! answer : 2

        ----------------- 2 th for 진입 -----------------
        인덱스 : 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
        입력값 : A, N, K, A, A, B, A, C, A, C, D,
        루프 진입 직후 좌/우 인덱스 값 : 0/2
        현재 서브스트링 : ANK
        현재 right 인덱스 문자 : K
        >> rightChat 등록 후 frequencies 맵 현황
        A : 1
        K : 1
        N : 1
        현재 서브스트링 내 최빈문자 등장횟수(maxCount) : 1
        서브스트링 길이-maxCount/k : 2/3
        서브스트링 길이-maxCount > k 일 때만 while 루프 진입한다.
        while 루프 탈출!! answer : 3

        ----------------- 3 th for 진입 -----------------
        인덱스 : 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
        입력값 : A, N, K, A, A, B, A, C, A, C, D,
        루프 진입 직후 좌/우 인덱스 값 : 0/3
        현재 서브스트링 : ANKA
        현재 right 인덱스 문자 : A
        >> rightChat 등록 후 frequencies 맵 현황
        A : 2
        K : 1
        N : 1
        현재 서브스트링 내 최빈문자 등장횟수(maxCount) : 2
        서브스트링 길이-maxCount/k : 2/3
        서브스트링 길이-maxCount > k 일 때만 while 루프 진입한다.
        while 루프 탈출!! answer : 4

        ----------------- 4 th for 진입 -----------------
        인덱스 : 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
        입력값 : A, N, K, A, A, B, A, C, A, C, D,
        루프 진입 직후 좌/우 인덱스 값 : 0/4
        현재 서브스트링 : ANKAA
        현재 right 인덱스 문자 : A
        >> rightChat 등록 후 frequencies 맵 현황
        A : 3
        K : 1
        N : 1
        현재 서브스트링 내 최빈문자 등장횟수(maxCount) : 3
        서브스트링 길이-maxCount/k : 2/3
        서브스트링 길이-maxCount > k 일 때만 while 루프 진입한다.
        while 루프 탈출!! answer : 5

        ----------------- 5 th for 진입 -----------------
        인덱스 : 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
        입력값 : A, N, K, A, A, B, A, C, A, C, D,
        루프 진입 직후 좌/우 인덱스 값 : 0/5
        현재 서브스트링 : ANKAAB
        현재 right 인덱스 문자 : B
        >> rightChat 등록 후 frequencies 맵 현황
        A : 3
        B : 1
        K : 1
        N : 1
        현재 서브스트링 내 최빈문자 등장횟수(maxCount) : 3
        서브스트링 길이-maxCount/k : 3/3
        서브스트링 길이-maxCount > k 일 때만 while 루프 진입한다.
        while 루프 탈출!! answer : 6

        ----------------- 6 th for 진입 -----------------
        인덱스 : 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
        입력값 : A, N, K, A, A, B, A, C, A, C, D,
        루프 진입 직후 좌/우 인덱스 값 : 0/6
        현재 서브스트링 : ANKAABA
        현재 right 인덱스 문자 : A
        >> rightChat 등록 후 frequencies 맵 현황
        A : 4
        B : 1
        K : 1
        N : 1
        현재 서브스트링 내 최빈문자 등장횟수(maxCount) : 4
        서브스트링 길이-maxCount/k : 3/3
        서브스트링 길이-maxCount > k 일 때만 while 루프 진입한다.
        while 루프 탈출!! answer : 7

        ----------------- 7 th for 진입 -----------------
        인덱스 : 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
        입력값 : A, N, K, A, A, B, A, C, A, C, D,
        루프 진입 직후 좌/우 인덱스 값 : 0/7
        현재 서브스트링 : ANKAABAC
        현재 right 인덱스 문자 : C
        >> rightChat 등록 후 frequencies 맵 현황
        A : 4
        B : 1
        C : 1
        K : 1
        N : 1
        현재 서브스트링 내 최빈문자 등장횟수(maxCount) : 4
        서브스트링 길이-maxCount/k : 4/3
        서브스트링 길이-maxCount > k 일 때만 while 루프 진입한다.
        while 루프 진입
        루프 진입 직후 left 인덱스 값 : 0
        left 인덱스 문자 : A
        >>A 등장횟수 감소 후 frequencies 맵 현황
        A : 3
        B : 1
        C : 1
        K : 1
        N : 1
        left 인덱스값 ++ 해줌.
        while 루프 탈출!! answer : 7

        ----------------- 8 th for 진입 -----------------
        인덱스 : 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
        입력값 : A, N, K, A, A, B, A, C, A, C, D,
        루프 진입 직후 좌/우 인덱스 값 : 1/8
        현재 서브스트링 : NKAABACA
        현재 right 인덱스 문자 : A
        >> rightChat 등록 후 frequencies 맵 현황
        A : 4
        B : 1
        C : 1
        K : 1
        N : 1
        현재 서브스트링 내 최빈문자 등장횟수(maxCount) : 4
        서브스트링 길이-maxCount/k : 4/3
        서브스트링 길이-maxCount > k 일 때만 while 루프 진입한다.
        while 루프 진입
        루프 진입 직후 left 인덱스 값 : 1
        left 인덱스 문자 : N
        >>N 등장횟수 감소 후 frequencies 맵 현황
        A : 4
        B : 1
        C : 1
        K : 1
        N : 0
        left 인덱스값 ++ 해줌.
        while 루프 탈출!! answer : 7

        ----------------- 9 th for 진입 -----------------
        인덱스 : 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
        입력값 : A, N, K, A, A, B, A, C, A, C, D,
        루프 진입 직후 좌/우 인덱스 값 : 2/9
        현재 서브스트링 : KAABACAC
        현재 right 인덱스 문자 : C
        >> rightChat 등록 후 frequencies 맵 현황
        A : 4
        B : 1
        C : 2
        K : 1
        N : 0
        현재 서브스트링 내 최빈문자 등장횟수(maxCount) : 4
        서브스트링 길이-maxCount/k : 4/3
        서브스트링 길이-maxCount > k 일 때만 while 루프 진입한다.
        while 루프 진입
        루프 진입 직후 left 인덱스 값 : 2
        left 인덱스 문자 : K
        >>K 등장횟수 감소 후 frequencies 맵 현황
        A : 4
        B : 1
        C : 2
        K : 0
        N : 0
        left 인덱스값 ++ 해줌.
        while 루프 탈출!! answer : 7

        ----------------- 10 th for 진입 -----------------
        인덱스 : 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
        입력값 : A, N, K, A, A, B, A, C, A, C, D,
        루프 진입 직후 좌/우 인덱스 값 : 3/10
        현재 서브스트링 : AABACACD
        현재 right 인덱스 문자 : D
        >> rightChat 등록 후 frequencies 맵 현황
        A : 4
        B : 1
        C : 2
        D : 1
        K : 0
        N : 0
        현재 서브스트링 내 최빈문자 등장횟수(maxCount) : 4
        서브스트링 길이-maxCount/k : 4/3
        서브스트링 길이-maxCount > k 일 때만 while 루프 진입한다.
        while 루프 진입
        루프 진입 직후 left 인덱스 값 : 3
        left 인덱스 문자 : A
        >>A 등장횟수 감소 후 frequencies 맵 현황
        A : 3
        B : 1
        C : 2
        D : 1
        K : 0
        N : 0
        left 인덱스값 ++ 해줌.
        while 루프 탈출!! answer : 7
        Final Answer : 7
        */
