//다익스트라 알고리즘을 이용하는 문제였습니다.
//실행시간은 20밀리초였고 이는 상위 약 32%의 성능이었습니다.
class Solution {
    public int networkDelayTime(int[][] times, int n, int k) {
        //return type is int
        
        //make graph
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (int i = 0; i <= n; i++) {
            graph.put(i, new ArrayList<>());
        }
        for (int[] time: times) {
            int start = time[0], destination = time[1], delayTime = time[2];
            graph.get(start).add(new int[] {destination, delayTime});
        }
        
        //que : saving (node : time) pairs
        //우선순위 큐는 time의 값을 기준으로 정렬해야 합니다. 따라서 생성자에 람다식을 전달하여 정렬 규칙을 정확하게 정의해줍니다.
        PriorityQueue<int[]> que = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        
        //아래의 while루프를 시작할 수 있어야 하므로 큐에 시작점 k와 도착시간 0으로 구성된 pair를 하나 넣어 줍니다. 출발점 k에서 시작하여 도착점이 자기 자신이므로 시간은 당연히 0입니다.
        que.offer(new int[] {k, 0});
        
        //visited : saving visited nodes. 이미 방문했던 노드들을 저장해둡니다.
        Set<Integer> visited = new HashSet<>();
       
        int distance = 0;
        while (!que.isEmpty()) {
        	//큐에서 첫 요소를 뽑아냅니다. 큐의 첫 요소가 뜻하는 것은, 현재 출발점에서 다음 노드로 이동할 수 있는 지점들 중에서
            //최단시간으로 갈 수 있는 노드와 그 최단시간의 쌍을 뽑아낸다는 것입니다.
            //큐가 그냥 큐가 아니고, 도착시간이 작은 순서대로 나열한 우선순위 큐라는 것을 기억해야 합니다.
            int[] cur = que.poll();
            int node = cur[0];
            int time = cur[1];
            
            //현재 위치에서 가장 짧은 시간 안에 방문할 수 있는 다음 노드가 기존에 방문했던 지점이 아닐 경우에만
            //다음 방문예정지를 큐에 집어넣는 작업을 시작할 수 있습니다.
            if(!visited.contains(node)){
            	
            	//방문기록을 추가해줍니다. 이로써 해당 노드는 신호를 받을 수 있는 노드로 처리됩니다.
                visited.add(node);
                
                //앞에서 추가해 준 노드까지 도달하는 시간으로 distance값을 초기화 해줍니다.
                //distance값은 while루프가 진행됨에 따라서 자연스럽게 최댓값
                //(즉, 출발점 k 노드에서 신호를 받기까지 가장 오래 걸리는 노드가 신호를 받는 시간)까지 갱신됩니다.
                distance = time;
                
                //이제 while루프에서 남은 부분은 다음 while루프를 준비하는 부분입니다.
                //방문기록에 추가했던 노드에서 이동 가능한 바로 다음 노드들의 목록을 graph.get(node)를 통해서 구합니다.
                for (int[] nextNodes: graph.get(node)) {
                	//만약 nextNodes 배열의 요소가 이미 방문한 지점이 아니라면, '해당 노드'와 '해당 노드가 신호를 받는데 걸리는 시간'의 쌍을 큐에 집어넣어야 합니다.
                    //이때, 기존의 time에 nextNodes[1]을 더한 값을 큐에 넣어줘야 합니다.
                    //처음에 그래프를 만들 때, 노드의 출발점을 키로 가지고, 값으로는 출발점에서 도착 가능한 바로 이웃 노드와 그 노드까지 가는데 걸리는 시간의 쌍을
                    //저장하는 ArrayList<int[]>를 갖고 있음을 기억해야 합니다.
                	if (!visited.contains(nextNodes[0])) {
                		que.offer(new int[]{nextNodes[0], time + nextNodes[1]});
                	}
                }//for
            }//Outer if
            
        }//wh
        
        //while루프를 끝내면 방문지점들의 개수와 입력 값 n과 비교하여 최종 정답을 가려낼 수 있습니다.
        return visited.size() != n ? -1 : distance; 
    }//main
}//end of main
