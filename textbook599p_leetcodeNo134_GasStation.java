//첫 번째 풀이는 시간초과 판정을 받으며 오답처리 되었습니다.
//37개의 테스트 케이스 중 35 번째 케이스를 분석하다가 중지되었습니다.
class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        //return type is int

        int allGas=0;
        int allCost=0;
        for(int g : gas){
            allGas += g;
        }
        for(int c : cost){
            allCost += c;
        }

        //연료의 합과 비용의 합을 비교해서 비용의 합이 큰 경우 -1을 리턴합니다.
        if(allGas < allCost) return -1;

        ArrayList<Integer> list = new ArrayList<>();

        //출발점이 가지고 있는 연료보다 다음 목적지로 가기 위한 비용이 더 많이 들 경우, 출발점이 될 수 없으므로,
        //그렇지 않은 지점의 인덱스들만 따로 리스트에 집어 넣어서 후보군을 만듭니다.
        for(int i =0; i<gas.length; i++){
            if(gas[i]>= cost[i]){
                list.add(i);
            }
        }

        int answer = -1;

        //후보 군들을 순회하면서 판단합니다.
        /*
        * 예를 들어서
        * 연료 : 1 2 3 4 5
        * 비용 : 3 4 5 1 2
        * 이럴 경우, 인덱스 3,4만 출발점 후보가 될 수 있는데, 이 중에서 4인덱스는 내부 for loop 결과
        * 중간에 culul이 음수가 되므로 정답이 될 수 없고, 최종 정답은 3이 됩니다.
        *
        * 그러나 이 방법은 후보군들을 대상으로 다시 전체 루프를 돌아야 되기 때문에,
        * 결국 O(N*N)의 시간복잡도를 가지게 되어서 시간초과 판정으로 오답처리된 것으로 생각됩니다.
        * */
        for(int k : list){

            int cumul = 0;

            //내부 for loop
            for(int i = k; i<gas.length; i++){
                int index = i % gas.length;
                cumul += (gas[index]-cost[index]);
                if(cumul < 0){ break; }
            }

            if(cumul >= 0) {
                answer = k;
                break;
            }

        }//O for

        return answer;
    }//main
}//main class










//두 번째 풀이는 O(N)의 시간복잡도를 가지고 있어서 2밀리초의 실행시간과 상위 4%라는 성능을 발휘할 수 있었습니다.
class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        //return type is int

        /*
        * 두 번째 풀이에서는 tank와 shortage의 역할을 이해하는 것이 중요합니다.
        *
        * 매 for루프에서 tank는 새로 계산 되는데, tank에는 현재 인덱스에서의 순비용(gas[i]-cost[i])이 누적됩니다.
        *
        * 하지만, 루프를 돌다보면 탱크의 잔량이 음수가 되는 순간이 반드시 1번 이상은 발생합니다. 왜냐하면, 문제에서 정답은
        * 반드시 1개만 존재한다는 제약을 걸어놓았기 때문에 순비용이 음수가 되는 순간이 반드시 1번 이상은 발생할 수밖에 없기 때문입니다.
        *
        * 예를 들어서 gas = 1,2,3,4,5 / cost = 1,2,3,4,5 이럴 경우,
        * 순비용이 음수가 되는 인덱스는 1개도 없게 되고 그 결과 정답은 0~4까지 모든 인덱스로 5개가 되는 것을 알 수 있습니다.
        *
        * 또 다른 예시로는 gas = 4,1,3,2,5
        *            /cost = 1,3,5,4,2인 경우가 있는데, 이때, 1,2,3 인덱스는 순비용이 전부 -2임을 알 수 있고,
        * 0 인덱스에 확보하여 tank에 저당한 3이라는 순비용은 루프를 반복하면서 결국 -2라는 순비용이 누적되어 2 인덱스에서 결국
        * 음수가 됩니다.
        *
        * 입력 값이 int[] gas = {4,1,3,2,5}; / int[] cost = {1,3,5,4,2};인 경우에 대해서 루프가 실제로 어떻게
        * 동작하는지를 로그를 출력하여 추적해본 결과를 코드의 맨 마지막 부분에 추가해 두었습니다.
        * */
        int tank=0;

        int shortage=0;

        int start=0;


        for(int i=0;i<gas.length;i++){
            /*System.out.println("\n----------- for loop entered------------");

            System.out.println("current INDEX : " + i);
            System.out.println("\t current netCost(순비용) : " + (gas[i]-cost[i]));*/

            tank += gas[i] - cost[i];

            /*System.out.println("\t TANK status   :   " + tank);
            System.out.println("\t Shortage status : " + tank);
            System.out.println("\t start index before : " + start);*/

            /*
            * 위에서 언급한 'tank 가 음수가 되는 순간'을 처리하기 위한 로직입니다.
            * 만약 이번 인덱스에서 tank가 음수 순비용이 누적된 끝에 결국 음수가 되었다면, 이번 인덱스는 절대 출발지점(즉, 정답)이
            * 될 수 없습니다.
            * 그래서 start를 이번 인덱스 + 1 의 값으로 초기화시켜주는 것입니다.
            *
            * 그리고 tank가 음수가 된 시점에서 shortage가 음수가된 tank 값의 누적을 통해서 초기화됩니다.
            *
            * 탱크가 음수가 되었다는 것은, gas = 4,1,3,2,5
            *                     /cost = 1,3,5,4,2인 경우에서 2인덱스에 진입한 후에 발생합니다.
            * 여기서는 연료양이 결국 -1이 되고, 연료양이 음수이기 때문에 여기는 이제 출발점이 될 수 없습니다.
            * 그리고 shortage가 tank==-1의 값이 누적됨으로써 -1로 초기화 됩니다.
            *
            * shortage는 부족한 연료의 양을 뜻합니다. for루프를 다 돌고나면, 결과적으로 tank와 shortage는 각기 따로따로
            * 값이 결정됩니다.
            * 그 후, shortage와 tank를 합친 값이 0보다 크거나 같아야만 start를 반환하고, 음수일 경우엔 -1을 리턴합니다.
            * */
            if(tank<0){
                //System.out.println("\n\t\t *** TANK값 음수 됨!!!");

                //얼마 만큼 연료의 양이 부족한지를 기록하는 것은 필수적입니다.
                //왜냐하면, gas = 2,3,4 / cost = 3,4,4 같은 경우에서 shortage를 기록하지 않으면
                //연료의 양이 분명 이동에 필요한 비용보다 작아서 어떤 인덱스에서 출발해도 주유소를 순회하는 것이
                //불가능한 경우를 판단할 수 없기 때문입니다.
                shortage+=tank;
                //System.out.println("\t\t 탱크 누적 후  shortage : " + shortage);

                /*
                * shortage에 부족한 연료분을 따로 기록해 두었다면, tank는 0으로 초기화 해야 합니다.
                * 이번 루프에서 tank < 0인 파트에 진입해서 shortage에 값을 누적하여 기록해두었다는 것은
                * 결과적으로 shortage에 누적된 값이 [0인덱스부터 현재의 i인덱스까지 순회하면서 발견한 모든 순비용의 합]과
                * 같기 때문입니다.
                * tank는 다음 for루프에서도 활용되기 때문에 이 값을 0으로 초기화 하지 않을 경우, 그로 인해 발생한 오차가
                * 다음 루프들에도 계속 영향을 주기 때문입니다.
                * */
                tank=0;

                start=i+1;
                //System.out.println("\t\t 마이너스 파트 진입 후 start INDEX : " + start);
            }//if

        }//for

        //shrotage 값이 tank 값과 합해져도 음수상태인 경우가 바로 gas = 2,3,4 / cost = 3,4,4와 같이
        //획득 가능한 모든 연료의 양보다, 이동을 위해 필요한 연료의 양보다 작아서 어떤 인덱스에서 출발해도 모든 주유소를
        //순회할 수 없는 경우를 나타냅니다. 그럴 때는 -1을 리턴해주고, 그렇지 않다면 start 값을 리턴해주면 됩니다.
        return shortage+tank >= 0 ? start : -1;

    }//main
}//main class
/*
입력 값 : int[] gas = {4,1,3,2,5}; / int[] cost = {1,3,5,4,2};
----------- for loop entered------------
current INDEX : 0
	 current netCost(순비용) : 3
	 TANK status   :   3
	 Shortage status : 3
	 start index before : 0

----------- for loop entered------------
current INDEX : 1
	 current netCost(순비용) : -2
	 TANK status   :   1
	 Shortage status : 1
	 start index before : 0

----------- for loop entered------------
current INDEX : 2
	 current netCost(순비용) : -2
	 TANK status   :   -1
	 Shortage status : -1
	 start index before : 0

		 *** TANK값 음수 됨!!!
		 탱크 누적 후  shortage : -1
		 마이너스 파트 진입 후 start INDEX : 3

----------- for loop entered------------
current INDEX : 3
	 current netCost(순비용) : -2
	 TANK status   :   -2
	 Shortage status : -2
	 start index before : 3

		 *** TANK값 음수 됨!!!
		 탱크 누적 후  shortage : -3
		 마이너스 파트 진입 후 start INDEX : 4

----------- for loop entered------------
current INDEX : 4
	 current netCost(순비용) : 3
	 TANK status   :   3
	 Shortage status : 3
	 start index before : 4

Final Answer : 4
* */
