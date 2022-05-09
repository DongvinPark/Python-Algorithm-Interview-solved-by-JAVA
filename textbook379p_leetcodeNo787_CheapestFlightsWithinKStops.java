
//이번 문제도 다익스트라 알고리즘을 이용하는 풀이였고, 실행시간은 7~10밀리초였으며 이는 상위 약 40%에서 60%에 해당하는 성능이었습니다.
//이번 문제는 (박상길 저)파이썬 알고리즘 인터뷰 교재에 있는 답의 로직을 그대로 따라할 경우 시간 제한 초과 판정을 받으며 오답처리됩니다. 저자가 책을 쓸 당시엔 정답이었지만, 나중에 리트코드 측에서 문제를 변경하여 난이도를 올린 것으로 추측됩니다.
class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        //return type is int

        //일단 맵을 만들어야 합니다. 출발점을 키로 갖고, 각 키별로 (도착점, 도착점까지 가는데 드는 비용)의 int[]를 요소로 갖는 ArrayList<int[]>를 값으로 갖게 해줍니다.
        //입력이 flights = [[0,1,100],[1,2,100],[2,0,100],[1,3,600],[2,3,200]]와 같을 경우, 해시맵으로 구현한 그래프의 모습은 다음과 같습니다.
        /*
        Map before BFS searching : 
출발점 : 0 / [도착점,비용] int[] 리스트 : [1,100], 
출발점 : 1 / [도착점,비용] int[] 리스트 : [2,100], [3,600], 
출발점 : 2 / [도착점,비용] int[] 리스트 : [0,100], [3,200], 

        */
        HashMap<Integer, ArrayList<int[]>> hm = new HashMap<>();
        for(int i=0; i<flights.length; i++){
            if(!hm.containsKey(flights[i][0]) ){
                hm.put(flights[i][0], new ArrayList<>());
            }
            hm.get(flights[i][0]).add(new int[]{flights[i][1], flights[i][2]});
        }//for

        //우선순위 큐에 int[]를 요소로 집어넣되, int[]는 다음과 같이 구성됩니다.
        //(현재지점에 올때까지 누적된 가격, 현재 위치, 지금까지 이동한 횟수)
        //다음지점으로 이동할 때 가격이 가장 적은 것을 선택해야 하므로 int[]의 첫 번째 요소를 기준으로 우선순위 큐의 요소들이 정렬될 수 있게 해줍니다.
        PriorityQueue<int[]> que = new PriorityQueue<>((a, b) -> a[0] - b[0]);


        //여기가 중요합니다. 시간초과 판정을 피하기 위해서 꼭 필요한 부분입니다.
        //visited 배열의 인덱스가 각 지점의 위치값 역할을 하고, 각 인덱스별로 지정될 값은 출발점에서 해당 인덱스(즉, 위치)에 도달하는데 필요로 하는 최소 이동횟수를 뜻합니다.
        int[] visited = new int[n];
        
        //while 루프 시작을 위해 큐에 첫 요소를 미리 삽입해줍니다. 당연히 문제에서 src로 주어진 출발점이어야 합니다.
        que.add(new int[]{0 ,src ,0 });

        while(!que.isEmpty()){
            int[] curr = que.poll();

            int curAllprice = curr[0];
            int curPosition = curr[1];
            int moveSum = curr[2];

            //정답을 찾은 경우입니다.
            if(curPosition == dst) return curAllprice;
            
            //여기가 시간제한 초과를 피하기 위한 핵심입니다. 아래의 조건문의 뜻은 다음과 같습니다.
            //만약 현재 위치를 인덱스로 한 visited배열의 값이 0이 아닐 경우 지금의 위치는 과거에 방문했었던 위치인 것입니다. 게다가 아직 이동횟수가 남아 있는 상황(즉, 출발점에서 현재위치까지 오는데 필요로 하는 최소 이동횟수가 현재까지 이동하면서 소모한 횟수보다 작은 경우)이라는 뜻입니다.
            //다익스트라 알고리즘에서 특정 지점을 한 번 지나간다는 것은, 이미 그 지점까지 도달하는데 필요로 하는 최소의 경로를 거쳐왔다는 뜻을 가지고 있습니다. 그런데 이미 방문했던 곳을 다시 방문한다는 것은 이미 최소한의 거리가 아니라는 뜻이됩니다. 따라서 문제에서 요구한 k+1이내의 이동횟수(k는 허용가능한 중간 거점의 수이기 때문에 실제 이동가능 횟수는 k+1입니다) 이내에서의 최소 이동횟수 조건을 위배하는 것입니다. 따라서 이러한 경우는 다음 루프에 탐색할 지점을 큐에 삽입하는 동작을 실시하기 전에 탐색을 그만두고 루프의 처음으로 돌아가야 합니다.
            //이것이 없을 경우 왜 시간제한이 걸리는지도 제가 직접 리트코드의 테스트 케이스 중 하나를 잡아내서 테스트해봤습니다. 결과는 풀이의 맨 아래 부분에 있습니다.
            if (visited[curPosition] != 0 && visited[curPosition]<= moveSum) continue;
            
            //모든 조건을 통과했다면, 현재까지 누적된 이동횟수를 이용해서 visited 배열의 현재 위치 인덱스의 값을 갱신해줍니다.
            visited[curPosition] = moveSum;

            //이 부분은 현재 위치에서 다음 위치로 가는데 필요로 하는 비용을 더해서 큐에다가 다음 루프 때 탐색할 지점들을 삽입하는 부분입니다.
            if(curr[2]<k+1 && hm.get(curPosition)!=null){
                for(int j=0; j<hm.get(curPosition).size(); j++){
                    int cumulPrice = curAllprice + hm.get(curPosition).get(j)[1];
                    que.offer(new int[]{cumulPrice, hm.get(curPosition).get(j)[0], curr[2]+1});
                }//for
            }//if

        }//wh

        //끝내 정답을 찾아내지 못하면 -1을 리턴합니다.
        return -1;
    }//main
}//main class

/*
위에서는 노드가 5개뿐인 간단한 경우로 알아봤지만, 시간 초과를 발생시킨 테슽트 케이스는 노드가 13개였습니다.
 int[][] flights = {{11,12,74},{1,8,91},{4,6,13},{7,6,39},{5,12,8},{0,12,54},{8,4,32},{0,11,4},{4,0,91},{11,7,64},{6,3,88},{8,5,80},{11,10,91},{10,0,60},{8,7,92},{12,6,78},{6,2,8},{4,3,54},{3,11,76},{3,12,23},{11,6,79},{6,12,36},{2,11,100},{2,5,49},{7,0,17},{5,8,95},{3,9,98},{8,10,61},{2,12,38},{5,7,58},{9,4,37},{8,6,79},{9,0,1},{2,3,12},{7,10,7},{12,10,52},{7,2,68},{12,2,100},{6,9,53},{7,4,90},{0,5,43},{11,2,52},{11,8,50},{12,4,38},{7,9,94},{2,7,38},{3,7,88},{9,12,20},{12,0,26},{10,5,38},{12,8,50},{0,2,77},{11,0,13},{9,10,76},{2,6,67},{5,6,34},{9,7,62},{5,3,67}};
입력값이 이러했고 상당히 복잡했습니다.
여기서 시간제한을 극복하기 위한 visited배열과 관련 if문이 없을 경우에 while문이 호출되는 횟수는
무려 15,775,790 회인 것에 반해서, 필요없는 부분을 과감히 제끼는 로직을 적용하면 고작 75회로 줄어듭니다.
이러한 상황이어서, 관련 로직에 대한 이해없이는 시간초과가 발생할 수밖에 없는 상황이었습니다.

입력이 5개였던 처음의 상황에서 작동과정Map before BFS searching : 
출발점 : 0 / [도착점,비용] int[] 리스트 : [1,100], 
출발점 : 1 / [도착점,비용] int[] 리스트 : [2,100], [3,600], 
출발점 : 2 / [도착점,비용] int[] 리스트 : [0,100], [3,200], 

		**while entered
current position
 : cumulative price/current positon/sum of number of movements >> 0/0/0
	need to search next destinations

		**while entered
current position
 : cumulative price/current positon/sum of number of movements >> 100/1/1
	need to search next destinations

		**while entered
current position
 : cumulative price/current positon/sum of number of movements >> 200/2/2

		**while entered
current position
 : cumulative price/current positon/sum of number of movements >> 700/3/2
	!!found answer!
Final Result : 700
을 추적해보면 다음과 같습니다.

*/
