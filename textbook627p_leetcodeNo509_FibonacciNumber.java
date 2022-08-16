//피보나치 수열을 구해보라는 문제는 전형적인 다이나믹 프로그래밍 문제입니다.
/*
* 첫 번째 풀이는 시간복잡도가 무려 O(2^n)에 달하는 극히 비효율적인 풀이입니다.
* 그래서인지 실행시간이 15밀리초가 나왔고, 이는 하위 16%에 해당하는 성능이었습니다.
* 테스트 케이스가 31개가 아니라, 100개 정도로 늘어났다면 아마도 타임 아웃 판정을 받았을 것입니다.
*
* 이 풀이가 비효율적인 이유는 매번 재귀 호출이 이어질 때마다 직전 호출의 두 배 만큼을 다시 호출해야 하는 구조이기 때문입니다.
*
* 따라서 이러한 비효율성을 개선하기 위해서는 재귀호출이 1회로 제한되어야 하고, 직전 루프에서 계산한 내용이
* 다음 루프에서 재활용 될 수 있어야 합니다.
*
* 이러지는 풀이는 이것을 가능하게 합니다.
* */
class Bad_Solution {
    public int fib(int n) {
        //return type is int

        //이 풀이는 최악의 실행시간을 갖는 풀이다.
        // 각각의 재귀 호출이 그 전의 호출을 2배로 만드는 모양새이기 때문이다.

        if(n <= 1){ return n; }

        return fib(n-2) + fib(n-1);

    }//main
}//main class






/*
* 두 번째 풀이의 실행시간은 0밀리초였고, 이는 상위 0%에 속하는 성능이지만, 대부분의 풀이가 여기에 분포돼 있어서
* 큰 의미는 없었습니다.
*
* 아래의 풀이가 직전 풀이보다 훨씬 효율적인 것은 이전 호출의 계산 결과를 배열의 형태로 리턴 받음으로서 전달 받을
* 수 있기 때문입니다.
*
* 피보나치 수열이 0 1 1 2 3 5 8 ... 로 이어지고,
* F(0) == 0 / F(1) == 1 / F(2) == 1 / F(3) == 2 일 때,
* F(N)을 구하는 재귀호출은 직전 호출로부터 new int[]{F(N-1), F(N-2)};를 전달 받습니다.
* F(N)을 구하는 것은 그저 F(N-1) + F(N-2)면 끝납니다.
*
* F(N)을 계산할 때 필요한 값 자체를 배열의 형태로 넘겨받은 자료에서 바로 찾을 수 있기 때문에, 첫 번째 풀이에서와
* 같이 매 호출마다 정답을 찾기 위해서 필요로 하는 F(N-1), F(N-2) 값을 다시 계산할 필요가 없는 것입니다.
* */
class Solution {
    public int fib(int n) {
        //return type is int

        //이것은 다이나믹 프로그래밍 중에서도 하향식 풀이, 즉 메모이제이션 풀이에 해당합니다.

        int[] ans = recur(n);

        return ans[0];

    }//main

    public int[] recur(int input){

        if(input <= 1){
            return new int[]{input, 0};
        }//if

        else{
            int[] temp = recur(input-1);
            return new int[]{temp[0]+temp[1], temp[0]};
        }//else

    }//func

}//main class








/*
* 세 번째 풀이 또한 실행시간은 두 번째 풀이와 동일하게 나와서 매우 효율적인 풀이라고 할 수 있습니다.
* */
class Solution {
    public int fib(int n) {

        //이 풀이는 다이나믹 프로그래밍 중에서도 상향식에 해당하는 타뷸레이션 풀이입니다.
        //재귀를 사용하지 않고, 주어진 조건에 맞는 루프를 반복하여 직접 정답을 찾아낸다는 것이 두 번째 풀이와
        //다른 점입니다.

        if (n==0) return 0;

        else if (n==1) return 1;

        int beforeBefore = 0;
        int before = 1;
        int curIndex = 2;
        int ans = 0;

        while (curIndex <= n ) {

            //들어오자마자 일단 정답을 먼저 기록해 놓습니다.
            ans = beforeBefore + before;

            /*
             * 그 후의 과정은 전부 다음 루프를 위한 준비일 뿐입니다.
             * beforeBefore는 before를 보게 만들고,
             * 이번 루프에서 냈던 답이 결국 다음 루프에서 새로운 before 값이 된다는 것을 이용한 것입니다.
             * 그래서 before가 이번 루프에서 기록한 답으로 초기화 되는 것입니다.
             * */
            beforeBefore = before;
            before = ans;
            curIndex++;
        }//wh

        return ans;
    }//main
}//main class
