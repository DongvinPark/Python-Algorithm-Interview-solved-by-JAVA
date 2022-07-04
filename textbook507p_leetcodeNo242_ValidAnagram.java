//첫 번째 풀이는 정렬을 사용한 풀이 입니다. 실행시간은 5밀리초였고 이는 상위 약 25%의 성능이었습니다.
class Solution {
    public boolean isAnagram(String s, String t) {
        //return type is boolean

        //입력으로 받은 두 문자열의 길이가 다르면 당연히 애너그램일 수 없기 때문에 이때는 false를 리턴해줍니다.
        if(s.length() != t.length()) return false;

        char[] sChar = s.toCharArray();
        char[] tChar = t.toCharArray();

        Arrays.sort(sChar);
        Arrays.sort(tChar);

        //정렬된 두 개의 char[] 배열을 인덱스별로 비교해서 서로 다르면 false를 리턴합니다.
        for(int i=0; i<sChar.length; i++){
            if(sChar[i]!=tChar[i])return false;
        }//for

        //어떤 false도 리턴하지 않고 여기까지 도달한다면 true를 리턴합니다.
        return true;
    }//main
}//main class






//두 번째 풀이는 정렬을 전혀 사용하지 않는 풀이압니다.
//대신, 2 개의 char[]을 만든 후, 각각의 입력값 s,t에 대하여 각각의 단어에서 등정하는 알파벳들의 빈도수를 저장합니다.
//두 단어가 s="abc", t="cba"인 경우 c1[] 배열의 0,1,2인덱스에 저장된 값과 c2[] 배열의 0,1,2 인덱스에 저장된 값은 서로 일치할 수밖에 없습니다. 이는 s와 t문자열을 이루고 있는 알파벳이 어떤 순서로 이루어져 있는지와는 전혀 상관이 없습니다.
class Solution {
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        char[] c1 = new char[26];
        char[] c2 = new char[26];

        // c-'a'가 어떻게 정수가 되는지를 이해하는 것이 중요합니다.
        /*
        * int a = 'a'; // System.out.println(a); 출력결과 : 97
        * int b = 'b'; // 출력결과 : 98
        * int c = 'c'; // 출력결과 : 99
        * int d = 'd'; // 출력결과 : 100
        * …
        * int z = 'z'; // 출력결과 : 122
        *
        * 이와 같이 자바에서는 'a'라는 문자를 int 타입에 대입시켜서 출력할 경우 a,b,c,...,z의 순서에 맞춰서 97~122라는 정수가 출력됩니다. 그렇기 때문에, 'a'-'a'는 0일 수밖에 없고, 이는 c1[]의 0번째 인덱스를 뜻하는 c[0]으로써 사용되는 것이 가능하게 해줍니다.
        * */
        for(char c:s.toCharArray()){
            c1[c-'a']++;
        }

        for(char c:t.toCharArray()){
            c2[c-'a']++;
        }

        for(int i = 0; i < c1.length; i++){
            if(c1[i] != c2[i]){
                return false;
            }
        }

        return true;
    }
}
