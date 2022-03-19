//스트링의 인덱스를 직접 조작하는 방법으로 풀었습니다.
//풀이결과 실행시간은 약 2 밀리초였고, 실행시간은 상위 10%(100명 중 약 10등)이었습니다.
class Solution {
    public void reverseString(char[] s) {
        
        for(int i=0; i<(s.length)/2; i++){
            char temp = s[i];
            s[i] = s[s.length-1-i];
            s[s.length-1-i] = temp;
        }
        
    }// main
}//main class
