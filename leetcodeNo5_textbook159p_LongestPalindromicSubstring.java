//첫 번째 풀이입니다. 가장 긴 펠린드롬 문자열의 길이가 짝수인 경우와 홀수인 경우로 나누어서 진행했습니다.
//또한 입력 문자열의 길이가 0,1,2,3인 경우를 일일이 전부 처리해주는 과정에서 코드가 길고 복잡해졌습니다.
//펠린드롬 문자열의 길이가 짝수인 경우와 홀수인 경우를 하나의 루프에서 전부 처리하지 못해서 입력 문자열에 대한 탐색을 2번이나 하고 있는 비효율적인 풀이입니다.
//펠린드롬 문자열을 판단하는 루프 내에서도 문자열의 인덱스를 직접 조작하는 과정에서 발생하는 예외 상황들을 전부 잡아내느라 코드가 다시 길고 복잡해지는 상황입니다.
//실행시간은 769밀리초로, 하위 10%에 속하는 성능이며 100명중 약 90등에 해당합니다.
class Solution {
    public String longestPalindrome(String s) {

        String even = "";
        String odd = "";

        if(s.equals("")){ return ""; }
        if(s.length() == 1){ return s; }
        if(s.length() == 2){
            if(s.charAt(0) == s.charAt(1)){ return s; }
            else{ return s.substring(0,1); }
        }

        if(s.length()==3){
            if(s.charAt(0)==s.charAt(1)){
                even = s.substring(0,2);
            }
            if(s.charAt(1)==s.charAt(2)){
                even = s.substring(1);
            }
            if(s.charAt(0)==s.charAt(2)){
                odd = s;
            }
        }

        //even case
        for(int i=0; i<s.length()-1; i++){
            if(s.charAt(i) == s.charAt(i+1)){

                if(i==0){ even = s.substring(i,i+2); continue; }
                if(i==s.length()-2 && even.length()<=2){ break; }
                int left = i;
                int right = i+1;
                while(true){
                    if(s.charAt(left) == s.charAt(right) ){
                        String newOne = s.substring(left,right+1);
                        if(even.length() < newOne.length()){
                            even = newOne;
                        }
                        if(left == 0 || right==s.length()-1){break;}
                        left -=1;
                        right +=1;
                    }
                    else{ break; }
                }//while
            }//outer if
            else{ continue; }
        }//for


        //odd case
        for(int i=1; i<=s.length()-2; i++){
            if(i==s.length()-1 && odd.length()<=3){break;}
            if(s.charAt(i-1) == s.charAt(i+1)){
                //System.out.println("entered");
                if(i==1){
                  //  System.out.println("i==1 entered");
                    if(s.charAt(i-1)==s.charAt(i+1)){ odd = s.substring(i-1,i+2); }
                    else {odd = s.substring(i,i+1);}
                   // System.out.println(odd);
                    continue;
                }
                int left = i-1;
                int right = i+1;
                while(true){
                    if(s.charAt(left)==s.charAt(right)){
                        String newOne = s.substring(left,right+1);
                        if(odd.length() < newOne.length()){
                            odd = newOne;
                          //  System.out.println("odd in while : "+odd);
                        }
                        if(left == 0 || right==s.length()-1){break;}
                        left -= 1;
                        right += 1;
                    }//if
                    else{break;}
                }//while

            }//outer if
            else{
                if(odd.length()<2){ odd = s.substring(i,i+1); }
                continue;
            }
        }//for

       // System.out.println("even : " + even);
      // System.out.println("odd : " + odd);
        if(even.length() > odd.length()){ return even; }
        else {return odd;}
    }//main
}//main class



//두 번째 풀이는 좀 더 상세한 설명을 덧붙였습니다. 첫 번째 풀이와는 다르게, substring()메서드를 전혀 사용하지 않고, 순수하게 입력 스트링의 인덱스만으로 문제를 해결하고 있습니다.
//입력 스트링의 substring을 추출해서 비교하는 것이 아니라, 펠린드롬으로 판별된 서브 스트링의 시작 인덱스 값과 끝 인덱스 값을 갖는 인트 타입 배열을 이용해서 정답을 도출하고 있습니다.
//문자열 객체를 거의 생성하지 않기 때문에 실행 속도가 빠릅니다. 실행시간은 34밀리초였고, 이는 상위 20%의 성적이며 100명 중 약 20등에 해당합니다.
class Solution {
    public String longestPalindrome(String s) {
        int[] res = new int[]{0, 1};//입력 스트링의 첫 글자와 두 번째 글자에 접근할 있도록 인덱스 값을 저장해 놓습니다.
        
        for(int i=0; i<s.length(); i++){//입력 스트링의 모든 인덱스에 대해서 1번씩 이번 for 문의 내용을 실행합니다.
            int[] even = palindrome(s, i , i);
            //인덱스 i에서 짝수 펠린드롬 문자열을 찾을 수 있다면 찾습니다. palindrome()메서드 내부의 while에 의해서 펠린드롬이 되는 한 끝까지 인덱스 값이 확장됩니다.
            int[] odd = palindrome(s, i , i+1);
            //인덱스 i에서 홀수 펠린드롬 문자열을 찾을 수 있다면 찾습니다. 이 부분도 짝수 펠린드롬을 찾을 때와 유사합니다.
            
            if(even[1] > res[1]){
                res = even;//찾아낸 짝수 펠린드롬의 마지막 글자의 인덱스가 res의 마지막 요소의 인덱스 값보다 크다면, res를 even으로 초기화시킵니다.
            }
            if(odd[1] > res[1]){
                res = odd; //찾아낸 홀수 펠린드로므이 마지막 글자의 인덱스가 [even으로 초기화 됐을 수도 있는 res] 의 마지막 요소의 인덱스 값보다 크다면, res를 odd로 초기화시킵니다.
            }
        }//for
        
        return s.substring(res[0], res[0]+res[1]);//저장돼 있는 res의 인덱스 값을 이용해서 입력 문자열에서 최종 정답 스트링을 추출해서 반환합니다.
    }//main
    
    private int[] palindrome(String s, int i, int j){        
        while(i>=0 && j<s.length() && s.charAt(i) == s.charAt(j)){//펠린드롬 조건을 만족하는 한, 좌측으로 확장되는 i와 우측으로 확장되는 j 각각을 갱산해가며 펠린드롬 여부를 판별합니다.
            i--;
            j++;
        }
        return new int[]{i+1, j-i-1};//펠린드롭 판별이 완료되면 펠린드롬 문자열의 시작 인덱스와 끝 인덱스를 값으로 갖고 있는 int[] 을 반환합니다.
        //이 인트 타입 배열은 위의 for 문에서 입력 문자열의 인덱스에서 짝수 및 홀수 펠린드롬이 어디까지 확장이 가능한지를 테스트하고 그 결과를 저장하는 것에 사용됩니다.
    }//func
}//main class
