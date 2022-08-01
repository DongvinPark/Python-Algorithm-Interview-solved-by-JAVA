//실행시간은 2밀리초였고, 이는 하위 44%의 성능이었습니다. 가끔 1밀리초로 측정될 경우 이는 상위 3%의 성능이었지만,
//대부분의 풀이가 1,2밀리초 구간에 집중돼 있어서 큰 의미는 없었습니다.
//차례대로 입력 배열을 순회하면서, 다음 값이 현재 값보다 클 경우에만 차액을 구해서 profit에 누적시켜주면 되는 문제였습니다.
//이번 문제는 지금 당장 보기에 좋은 것만 선택한다는 그리디 논리에 충실한 문제라는 것을 알 수 있습니다.
class Solution {
    public int maxProfit(int[] prices) {
        //return type is int

        if(prices.length <= 1) return 0;

        int profit = 0;

        for(int i=0; i<prices.length-1; i++){
            if(prices[i]<prices[i+1]){
                profit += (prices[i+1] - prices[i]);
            }
        }//O for

        return profit;

    }//main
}//main class
