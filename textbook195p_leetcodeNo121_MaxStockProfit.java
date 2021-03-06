//실행시간은 1밀리초였고, 자바 언어로 제출한 정답중 가장 빠른 수준에 속해 있었습니다. 상위 0.0%에 속하는 것으로 나왔습니다.
//입력 배열이 주어졌을 때, 배열을 순서대로 읽어들이면서 특정 조건에 따라서 최솟값을 갱신하고, 그렇게 갱신된 최솟값을 기준으로 현재 인덱스 요소에 있는 값과의 차이를 구합니다.
//그러한 차이는 문제에서 요구한 차액인데, 이러한 차액을 구해 나가면서 차액이 갱신되게 만듭니다.
class Solution {
    public int maxProfit(int[] prices) {
        //must return int type value
        
        int lower = 0;//최솟값을 0으로 초기화시킵니다. 최솟값을 루프의 바깥에서 초기화시키는 것이 중요합니다. 왜냐하면 루프가 반복되고 이전 루프에서 계산한 최솟값을 기억해서 사용해야 하기 때문입니다.
        int rev = 0;//문제의 최종 정답으로서 반환될 수익입니다. 최솟값을 루프의 바깥에서 정의한 것과 같은 이유로, 루프의 바깥에 초기화시켜줍니다.
        
        for(int i=0; i<prices.length; i++){
            if(i==0){ lower = prices[0]; continue; }//첫 번째 루프에서는 일단 첫 인덱스 요소의 값으로 최솟값을 초기화시켜야 합니다.
            
            if(prices[i]>lower){//두 번째 루프부터 if문의 조건식에 접근할 수 있습니다.
                //만약 현재 인덱스 값이 직전 루프에서 저장해 둔 최솟값보다 큰 상황이라면 양의 수익을 거둘 수 있는 조건이 충족된 것입니다.
                //최초로 여기에 진입했다면 rev는 0인 상태입니다.
                
                int diff = prices[i]-lower;//일단 현재 인덱스에서 거둘 수 있는 양의 수익을 구합니다.
                if(diff > rev){ rev = diff; }//그리고, 그 수익이 현재 rev 보다 크다면, rev를 갱신해줍니다.
                else{ continue; }//수익이 나기는 하지만 직전 루프들에서 확인했던 수익보다 작거나 같다면 아무것도 하지 않고 다음 루프로 이동합니다.
                
            }//outer if
            else{
                //현재 인덱스 요소의 값이 최솟값보다 작거나 같은 상황입니다. 최솟값을 현재 인덱스 요소의 값으로 갱신해줍니다.
                lower = prices[i];
            }
            
        }//for루프는 여기까지 입니다. 루프를 돌면서 갱신되는 것은 두 개입니다. 하나는 수익 계산의 기준이 되는 '최솟값'이고, 다른 하나는 최솟값을 기준으로 계산되는 '수익'입니다.
        //최솟값의 판단과 수익의 기준이 모두 현재 인덱스 요소의 값에 의해서 결정되며, 따라서 O(n)의 시간복잡도에서 해결가능하다는 것을 이해하는 것이 중요합니다.
        
        return rev; 
        
    }//Main
}//main class
