//풀이시간은 0밀리초가 나왔지만, 관련된 통계가 리트코드에 집계돼 있지 않아서 큰 의미는 없었습니다.
class Solution {
    public int getSum(int a, int b) {
        //return type is int

        //System.out.println("before while : a == " + Integer.toBinaryString(a));
        //System.out.println("before while : b == " + Integer.toBinaryString(b));

        //자리 올림수가 없어질 때까지 루프를 반복합니다.
        while (b!=0){
            //System.out.println("\n\twhile entered");

            //전가산기의 진리표에 따르면, 합에 대한 진리표는 XOR연산의 진리표와 같습니다.
            // 따라서, 합을 계산할 때는 XOR연산을 적용해야 합니다.
            // answer는 결국 전자산기 로직에서 '합'을 표현하고 있음을 알 수 있습니다.
            int answer = a^b;

            //System.out.println("\tanswer : " + Integer.toBinaryString(answer));

            //carry의 역할을 이해하는 것이 중요합니다.
            // 전가산기의 진리표에 따르면, 자리올림수에 대한 진리표는 AND연산의 진리표와 같습니다.
            // 따라서 자리 올림수를 구할 때는 AND연산을 적용해야 합니다.
            // 또한 루프가 반복될 때마다 좌측으로 1칸씩 시프트(<<)되는 것을 알 수 있습니다.
            //이렇게 하는 이유는, 실제로 우리가 덧셈을 할 때, 자리 올림수는 왼쪽의 더 큰 단위의 자릿수 쪽으로 넘겨주곤 했던 것을
            //재현하기 위해서입니다. 예를 들어서 19+4를 계산할 때, 9+4==13이 될텐데, 이때 발생한 자리올림수 1을
            //19의 10 부분이 위치하고 있는 1쪽으로 넘겨줘야 하기 때문입니다.
            int carry = (a&b) <<1;

            //System.out.println("\tcarry : " + Integer.toBinaryString(carry));

            //이번 루프에서 계산한 answer, carry가 다음 루프의 a,b가 되어서
            // 다시 다음 루프의 answer, carray의 계산에서 활용됩니다.
            a = answer;
            b = carry;
        }//while

        return a;
    }//mian

}//main class


//두 번째 풀이는 개인적으로 매우 기발한 풀이라고 생각하여 리트코드에서 가져와 봤습니다.
//직접적인 덧셈 연산을 할 수 없는 대신, 지수함수와 로그함수의 성질을 이용해서
// 곱셈 연산이 지수함수에서는 지수들 같의 덧셈으로 표현될 수 있다는 점을 이용한 풀이입니다.
//실행시간은 첫 번째 풀이와 같이 0밀리초가 나왔습니다.
class Solution {
    public int getSum(int a, int b) {
        return (int)(Math.log(Math.pow(2, b) * Math.pow(2, a)) / Math.log(2));
    }
}

/*
첫 번째 풀이를 이용했을 때 실제로 메서드가 어떻게 작동하는지를 로그를 출력하여 기록한 것입니다.
* 2의 보수의 특징, 그리고 전가산기의 원리를 먼저 파악하고 오시는 것을 추천드립니다.
* 우선, 4+3과 7+(-1)의 경우 각각에 대해서 작동과정을 기록해 놓은 로그는 다음과 같습니다.
*
* >>> 7+3의 경우
before while : a == 111
before while : b == 11

	while entered
	answer : 100
	carry : 110

	while entered
	answer : 10
	carry : 1000

	while entered
	answer : 1010
	carry : 0
Final Answer : 10
*
*
*
*
*
* >>> 7+(-1)의 경우
before while : a == 111
*
* 2의 보수 표기법을 사용하고 있기 때문에, -1의 경우 32개의 1로 표현되고 있는 상황입니다.
before while : b == 11111111111111111111111111111111

	while entered
	answer : 11111111111111111111111111111000
	carry : 1110

	while entered
	answer : 11111111111111111111111111110110
	carry : 10000

	while entered
	answer : 11111111111111111111111111100110
	carry : 100000

	while entered
	answer : 11111111111111111111111111000110
	carry : 1000000

	while entered
	answer : 11111111111111111111111110000110
	carry : 10000000

	while entered
	answer : 11111111111111111111111100000110
	carry : 100000000

	while entered
	answer : 11111111111111111111111000000110
	carry : 1000000000

	while entered
	answer : 11111111111111111111110000000110
	carry : 10000000000

	while entered
	answer : 11111111111111111111100000000110
	carry : 100000000000

	while entered
	answer : 11111111111111111111000000000110
	carry : 1000000000000

	while entered
	answer : 11111111111111111110000000000110
	carry : 10000000000000

	while entered
	answer : 11111111111111111100000000000110
	carry : 100000000000000

	while entered
	answer : 11111111111111111000000000000110
	carry : 1000000000000000

	while entered
	answer : 11111111111111110000000000000110
	carry : 10000000000000000

	while entered
	answer : 11111111111111100000000000000110
	carry : 100000000000000000

	while entered
	answer : 11111111111111000000000000000110
	carry : 1000000000000000000

	while entered
	answer : 11111111111110000000000000000110
	carry : 10000000000000000000

	while entered
	answer : 11111111111100000000000000000110
	carry : 100000000000000000000

	while entered
	answer : 11111111111000000000000000000110
	carry : 1000000000000000000000

	while entered
	answer : 11111111110000000000000000000110
	carry : 10000000000000000000000

	while entered
	answer : 11111111100000000000000000000110
	carry : 100000000000000000000000

	while entered
	answer : 11111111000000000000000000000110
	carry : 1000000000000000000000000

	while entered
	answer : 11111110000000000000000000000110
	carry : 10000000000000000000000000

	while entered
	answer : 11111100000000000000000000000110
	carry : 100000000000000000000000000

	while entered
	answer : 11111000000000000000000000000110
	carry : 1000000000000000000000000000

	while entered
	answer : 11110000000000000000000000000110
	carry : 10000000000000000000000000000

	while entered
	answer : 11100000000000000000000000000110
	carry : 100000000000000000000000000000

	while entered
	answer : 11000000000000000000000000000110
	carry : 1000000000000000000000000000000

	while entered
	answer : 10000000000000000000000000000110
	carry : 10000000000000000000000000000000

	while entered
	* XOR연산을 통해서 가장 왼쪽에 위치하는 1이 결국 0이 되면서 정답이 도출되는 모양새입니다.
	answer : 110
	carry : 0
Final Answer : 6
*
* */
