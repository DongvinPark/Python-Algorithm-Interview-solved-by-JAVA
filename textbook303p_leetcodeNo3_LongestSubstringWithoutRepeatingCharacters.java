//첫 번재 풀이는 매우 무식한 방법입니다.
//실행시간이 228밀리초나 나왔고, 이는 하위 10%에 해당하는 성능입니다.
//이처럼 무식한 실행시간이 나온 이유는, 매 비교 때마다 해시셋을 새로 만들어서 비교했기 때문입니다.
class Solution {
    public int lengthOfLongestSubstring(String s) {
        //return type is int
        
        if(s.length()==0) return 0;
        
        if(s.length()==1){return 1;}
        
        int answer = 0;
        
        char[] input = s.toCharArray();
        
        for(int i=0; i<input.length; i++){
            
            HashSet<Character> hs = new HashSet<>();
            hs.add(input[i]);
            int num = 1;
            
           int right = i+1;
            
            while(true){
                if(  right > input.length-1 || hs.contains(input[right])){
                    break;
                }//if
                
                num++;
                hs.add(input[right]);
                right++;      
                if(right >= input.length) break;
            }//wh
            
            if(answer < num){ answer = num; }
        }//for
        
        return answer;
        
    }//main
}//main class






//두 번째 풀이는 5밀리초의 실행시간을 기록했고, 이는 상위 약 14%의 성능이었습니다.
//첫 번째 풀이보다 효율적이었던 이유는 다음과 같습니다.
//1. 직전 루프에서 이미 만들어둔 해시셋을 사용할 수 있기 때문입니다. 중복된 문자를 만났을 경우, 해시 셋의 remove()메서드를 이용해서 없애준 후, l 인덱스를 ++ 해주는 연산을 해준 것이 효율성을 끌어올린 가장 중요한 원인입니다.
//2. 중복 문자를 발견했을 때, left 인덱스를 ++해 줘서 실행시간이 O(n^2)이 되는 것을 막을 수 있었습니다.
class Solution {
    public int lengthOfLongestSubstring(String s) {
        //return type is int
        
        if(s.length()==0) return 0;
        
        if(s.length()==1){return 1;}
        
        int answer = 0;
        int l = 0;
        int r = 0;
        
        HashSet<Character> hs = new HashSet<>();
        
        while(true){
            if(r==s.length()) break;
            if(l==r || !hs.contains(s.charAt(r)) ){
                hs.add(s.charAt(r));
                answer = Math.max(answer, r-l+1);
                r++;
            }//if
            else{
                hs.remove(s.charAt(l));
                l++;
            }//el
        }//wh
        
        return answer;
        
    }//main
}//main class
