//첫 번째 풀이는 실행시간이 1밀리초가 나왔지만 대부분의 풀이가 0 또는 1밀리초에 집중돼 있어서 큰 의미는 없었습니다.
//XOR 연산의 뜻을 이해하는 것이 중요합니다. 예를 들어서 7(==0111)과 2(==0010)를 서로 XOR 해주면 5(==0101)입니다.
//여기서 5(==0101)에서 1의 개수인 2개가 정답입니다. XOR연산을 하면 결국 두 정수 이진법으로 나타낸 결과를 서로 자릿수별로 비교했을 때
//서로 다른 값을 가져야만 최종 결과에서 1로 바뀌기 때문에 최종결과에서 1이 포함된 횟수를 세면 정답이 됨을 알 수 있습니다.
/*
   0 1 1 1
   0 0 1 0
>> 0 1 0 1 == 십진법으로는 5.
*/
class Solution {
    public int hammingDistance(int x, int y) {
        //return type is int
        
        char[] cArray = Integer.toBinaryString(x^y).toCharArray();
        
        int answer = 0;
        
        for(int i =0; i<cArray.length; i++){
            if(cArray[i]=='1') answer++;
        }
        
        return answer;
        
    }//main
}//main class




//사실 첫 번재 풀이와 같이 XOR연산의 결과를 굳이 charArray로 변환해서 1의 개수를 세지 않아도, 이를 대신해주는 static 메서드를 이용하면
//단 한 줄 만으로도 바로 정답을 반환할 수 있습니다.
//그리고 이렇게 풀이하면 실행시간이 0밀리초로 측정되면서 더 빨리 풀이되는 것을 확인할 수 있습니다.
class Solution {
    public int hammingDistance(int x, int y) {
        //return type is int
        
        return Integer.bitCount(x^y);
        
    }//main
}//main class
