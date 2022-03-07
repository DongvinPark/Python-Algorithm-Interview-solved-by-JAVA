//첫 번째 풀이는 자바의 ArrayList 자료형을 이용했습니다.
//실행시간은 8 밀리초, 전체 자바 풀이 중 상위 60%(100명 중 약 63등)였습니다.
class Solution {
    public boolean isPalindrome(String s) {
        
        String toLower = s.toLowerCase();
        
        ArrayList<Character> filtered = new ArrayList<>();
        
        //preprocessing
        for(int i=0; i<toLower.length(); i++){
            if(Character.isLetterOrDigit(toLower.charAt(i)) ){
                filtered.add(toLower.charAt(i));
            }
        }//for
        
        for(int i=0; i<(filtered.size()/2);i++){
            if(filtered.get(i) != filtered.get(filtered.size()-1-i) ){
                return false;        
            }
        }
        
        return true;
        
    }// main
}//main class



//두 번재 풀이는 String 타입에 += 연산을 적용해봤습니다. 결과는 역시 최악이었습니다;;
//실행시간이 883 밀리초, 상위 90%(100명중 약 90등)였습니다.
class Solution {
    public boolean isPalindrome(String s) {
        
        String toLower = s.toLowerCase();
        
        String filtered ="";
        
        for(int i=0 ; i<toLower.length(); i++){
            if( Character.isLetterOrDigit( toLower.charAt(i) ) ){
                filtered += toLower.charAt(i);
            }
        }//for
        
        char[] result = filtered.toCharArray();
        
        for(int i=0; i<(result.length/2); i++ ){
            if( result[i] != result[result.length-1-i] ){
                return false;
            }
        }
        
        return true;
        
    }//main
}//main class



//세 번째 풀이는 StringBuilder 클래스를 사용해봤습니다.
//실행시간은 6밀리초, 상위 40%로 100명 중 약 40등 초반입니다.
class Solution {
    public boolean isPalindrome(String s) {
        
        String toLower = s.toLowerCase();
        
        StringBuilder filtered = new StringBuilder();
        
        for(int i=0 ; i<toLower.length(); i++){
            if( Character.isLetterOrDigit( toLower.charAt(i) ) ){
                filtered.append( toLower.charAt(i) );
            }
        }//for
        
        char[] result = filtered.toString().toCharArray();
        
        for(int i=0; i<(result.length/2); i++ ){
            if( result[i] != result[result.length-1-i] ){
                return false;
            }
        }
        
        return true;
        
    }//main
}//main class
