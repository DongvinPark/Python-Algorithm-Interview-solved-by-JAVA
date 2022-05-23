//첫 번째 풀이는 틀린 풆이입니다. 총 71개의 테스트 케이스 중 마지막 71번재 테스트 케이스르 통과하지 못하고 시간초과 판정을 받으며 오답처리 됐습니다.
//그래도 사용한 원리 자체는 정답으로 처리된 두 번째 풀이와 동일합니다. 그것으 바로, 주어진 그래프에서 리프노드들을 전체 노드 수가 2개 이하로 남을 때까지 계속 지워나간 후 남은 노드들을 반환하는 것입니다.
class Solution {
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        //return type is List<Integer>

        ArrayList<Integer> answer = new ArrayList<>();

        if(n==1){
            answer.add(0); return answer;
        }

        if(n==2){
            answer.add(0); answer.add(1); return answer;
        }

        HashMap<Integer, LinkedList<Integer>> hm = new HashMap<>();

        //그래프를 만들어야 합니다. 이때, 문제에서 그래프의 방향성이 없다고 언급했음을 기억해야 합니다.
        //예를 들어서, 1-2 이렇게 두 개의 노드만으로 구성된 그래프가 주어졌을 때, 출발점을 키로 갖고, 각 출발점에서 도착할 수 있는 지점들을 요소로 갖는 리스트를 값으로 갖는 해시맵은 다음과 같습니다.
        // 1 : [2]
        // 2 : [1]
        //양뱡향이기 때문에, 1에서 2로 갈수도 있고, 2에서 1로 갈수도 있는 것을 모두 표현해야 합니다.
        for(int i=0; i<edges.length; i++){
            if(hm.containsKey(edges[i][0])==false){
                hm.put(edges[i][0], new LinkedList<>());
            }
            hm.get(edges[i][0]).add(edges[i][1]);

            if(hm.containsKey(edges[i][1])==false){
                hm.put(edges[i][1], new LinkedList<>());
            }
            hm.get(edges[i][1]).add(edges[i][0]);
        }//for

        //while 루프의 지속조건으로 해시맵의 사이즈를 직접 사용할 때는 해시맵에서 삭제해야 할 요소들의 키를 뽑아내는 작업과, 그렇게 뽑아낸 키를 이용해서 실제로 해시맵에서 요소를 삭제하는 작업을 별도의 for 루프로 분리시키는 것이 중요합니다.
        //여기서 이 두 가지 작업을 하나의 for에서 진행할 경우 삭제하는 행위가 hm.size()에 바로 영향을 주기 때문에 한 번 집입한 while 루프 내에서 루프의 지속 조건이 동일하게 적용되지 않는 문제가 발생합니다. 자바에서는 이럴 경우
        // ConcurrentModificationException 을 던지며 프로그램을 통째로 종료시켜버립니다.
        while(hm.size()>2){

            ArrayList<Integer> firstDelete = new ArrayList<>();

            for(int i : hm.keySet()){

                if(hm.get(i).size()==1){
                    firstDelete.add(i);
                }
            }//for

            //그래프가 양방향으로 구성돼 있다는 사실을 기억해야 합니다.
            //예를 들어서, 1-2 이렇게 두 개의 노드만으로 구성된 그래프가 주어졌을 때, 출발점을 키로 갖고, 각 출발점에서 도착할 수 있는 지점들을 요소로 갖는 리스트를 값으로 갖는 해시맵은 다음과 같습니다.
            // 1 : [2]
            // 2 : [1]
            //여기서 1노드를 삭제한다면, 해시맵에서 1을 키로 갖는 요소뿐만 아니라, 2에서 1로 갈 수 있는 것을 설명하는 부분인 2 : [1] 부분에서도 [1]을 삭제해야 합니다. 1노드가 존재하지 않는데, 2 노드에서 1노드로 이동할 수 있다는 것은 말이 안 되기 때문입니다.
            for(int k : firstDelete){
                int temp = hm.get(k).get(0);
                hm.remove(k);
                hm.get(temp).removeFirstOccurrence(k);
            }


        }//wh

        //while 루프 후 해시맵에서 남은 키셋의 요소들을 리스트에 담아서 반환합니다.
        //이렇게 별도의 리스트를 또 만들어서 반환하는 과정에서 시간이 많이 지체되고 있었습니다.
        for(int j : hm.keySet()){
            answer.add(j);
        }
        return answer;

    }//main
}//main class








//두 번째 풀이에서는 해시맵의 키셋을 또 리스트에 따로 담아서 반환할 필요 없이, 처음부터 leaves 리스트를 이용해서 n의 값을 줄여나가는 방법을 사용했습니다.
//매번 while 루프마다 다음 while 루프에서 삭제해야 하는 노드들의 리스트를 newLeaves 리스트에 따로 담아서 leaves 리스트를 초기화 해줘야 하는 것은 동일하지만, n의 값을 바로 줄여줄 수 있고, 최종 결과를 반환할 때 별도의 리스트를 또 만들 필요가 없어서 실행시간을 단축시킬 수 있었습니다.
//실행시간은 70밀리초에서 38밀리초 정도 걸렸는데, 이는 하위 약 24%에서 상위 약 33% 정도인데 아무래도 리트코드 내부 서버의 상황에 따라서 편차가 크게 나는 편인 것 같습니다.
class Solution {
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        //return type is List<Integer>

        ArrayList<Integer> answer = new ArrayList<>();

        if(n==1){
            answer.add(0); return answer;
        }

        if(n==2){
            answer.add(0); answer.add(1); return answer;
        }

        HashMap<Integer, LinkedList<Integer>> hm = new HashMap<>();

        //make graph
        for(int i=0; i<edges.length; i++){
            if(hm.containsKey(edges[i][0])==false){
                hm.put(edges[i][0], new LinkedList<>());
            }
            hm.get(edges[i][0]).add(edges[i][1]);

            if(hm.containsKey(edges[i][1])==false){
                hm.put(edges[i][1], new LinkedList<>());
            }
            hm.get(edges[i][1]).add(edges[i][0]);
        }//for


        ArrayList<Integer> leaves = new ArrayList<>();

        for(int i : hm.keySet()){

            if(hm.get(i).size()==1){
                leaves.add(i);
            }
        }//for

        while(n>2){

            n -= leaves.size();

            ArrayList<Integer> newLeaves = new ArrayList<>();

            for(int k : leaves){
                int temp = hm.get(k).get(0);
                hm.remove(k);
                hm.get(temp).removeFirstOccurrence(k);

                if(hm.get(temp).size()==1){
                    newLeaves.add(temp);
                }
            }

            leaves = newLeaves;

        }//wh

        return leaves;

    }//main
}//main class
