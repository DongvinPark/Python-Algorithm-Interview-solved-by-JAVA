//첫 번째 풀이는 입력숫자를 이진수 문자열로 바꾸고, 그 문자열을 다시 charArray로 바꾼 후
// '1'의 개수를 직접 다 세는 풀이법입니다.
//실행시간은 2밀리초였고, 이는 하위 20%에 해당하는 성능이었습니다.
class Solution {
    // you need to treat n as an unsigned value
    public int hammingWeight(int n) {
        //return type is int

        int count = 0;

        for(char c : Integer.toBinaryString(n).toCharArray()){
            if(c=='1') count++;
        }

        return count;

    }//main
}//main class




//두 번째 풀이방법은 '1'의 갯수를 세는 것이 아니라, n과 n-1 간의 AND 연산을 반복한 횟수를 세서 정답을 도출합니다.
//예를 들어서 입력값이 11이라면, 이는 이진수로 1011로 표현될 수 있습니다.
/*
* 입력값이 11인 경우 실제로 루프가 어떻게 작동하는지를 추적한 로그는 다음과 같습니다.
* while이 3번 호출되었고, 이는 11의 이진수인 1011에서 1이 등장한 횟수인 3과 같습니다.
* 	while entered
n   : 1011
n-1 : 1010
result of AND operation : 1010

	while entered
n   : 1010
n-1 : 1001
result of AND operation : 1000

	while entered
n   : 1000
n-1 : 111
result of AND operation : 0
Final Answer : 3
*
* */
class Solution {
    // you need to treat n as an unsigned value
    public int hammingWeight(int n) {
        //return type is int

        int count = 0;

        while(n!=0){
            System.out.println("\n\twhile entered");
            System.out.println("n   : " + Integer.toBinaryString(n));
            System.out.println("n-1 : " + Integer.toBinaryString(n-1));
            n &= n-1;
            System.out.println("result of AND operation : " + Integer.toBinaryString(n));
            count ++;
        }

        return count;

    }//main
}//main class
