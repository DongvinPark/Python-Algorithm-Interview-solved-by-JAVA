//첫 번째 풀이는 76밀리초가 걸렸고, 이는 하위 5%의 성능입니다.
//교재 357쪽, 리트코드 332번 문제인 Reconstruct Itinerary(==일정재구성) 와 비슷한 논리를 사용합니다.
//일정 재구성 문제에서 사용한 상세한 로직에 대한 설명은 제 깃허브 레포지토리에 있고, 링크를 걸어놨습니다.
//https://github.com/DongvinPark/Python-Algorithm-Interview-solved-by-JAVA/blob/main/textbook357p_leetcodeNo332_ReconstructItinerary.java
//이 문제는 prqs로 표현되는 코스들을 전부 도는 과정에서 코스를 끝마치지 못하고 중간에 순환하게 되는 경우를 발견해내는 알고리즘으로 풀이할 수 있습니다.
class Solution {
    public boolean canFinish(int numCourses, int[][] prqs) {
        //return type is boolean
        
        if(prqs.length == 0) return true;
        
        ArrayList<Integer> trace = new ArrayList<>();
        
        ArrayList<Integer> visited = new ArrayList<>();
        
        HashMap<Integer, ArrayList<Integer>> hm = new HashMap<>();
        
        //일정 재구성 문제 때와 마찬가지로 출발점을 키로 삼고, 각 키 별로 출발점에서 도착할 수 있는 지점들의 목록을 저장하는 리스트를 만들어줍니다.
        for(int i=0; i<prqs.length; i++){
            if(!hm.containsKey(prqs[i][1])){
                hm.put(prqs[i][1], new ArrayList<Integer>());
            }
            hm.get(prqs[i][1]).add(prqs[i][0]);
        }//for
        
        //해시맵에 존재하는 키값을 전부 탐색해야 하므로 keySet()메서드를 이용해서 dfs를 호출해줍니다.
        for(int i : hm.keySet()){
            //호출 중에 한 번이라도 false가 나오면 중간에 순환구조가 발견된 것이므로 결과적으로 false가 됩니다.
            if(!dfs(i, trace, visited, hm)) return false;
        }
        
        //모든 dfs호출을 완료했는데도 false가 발견되지 않는다면 true를 반환해 줍니다.
        return true;
        
    }//main
    
    public static boolean dfs(
        int start,
        ArrayList<Integer> trace,
        ArrayList<Integer> visited,
        HashMap<Integer, ArrayList<Integer>> hm
    ){
        
        //한 번 방문했던 곳을 다시 방문하면 순환구조가 되므로 코스를 끝마칠 수 없습니다.
        if(trace.contains(start)) {
            return false;
        }
        
        //이미 방문했던 지점에서는 dfs호출을 즉시 그만 두고 true를 반환해줍니다.
        if(visited.contains(start)){
            return true;
        }
        
        //dfs가 strat를 인자로 받아서 호출되었다는 것은 start 지점을 방문했다는 뜻이므로, trace에 추가해줍니다.
        trace.add(start);
        
        //여기서 null 처리를 해주는 것이 중요합니다. null처리를 해주지 않으면 해당 dfs 호출시 인자로 넘어온 start 지점에서 더 이상 다음 지점을 탐색할 수 없는 경우에 NullPointerException이 발생하며 런 타임에러가 발생하기 때문입니다.
        if(hm.get(start)!=null){

            //위에서 만들었던 해시맵이 여기서 사용됩니다. start 지점에서 방문 가능한 다음 지점들을 순차적으로 dfs 호출해서 방문합니다.
            for(int i : hm.get(start)){
                if(!dfs(i, trace, visited, hm)) return false;
            }
        }//if
        
        //여기서 trace를 제거해주는 것이 꼭 필요합니다. trace를 제거하지 않고 다음 dfs를 호출할 경우, 순환구조가 아님에도 순환구조로 오판할 수 있기 때문입니다.
        trace.remove(trace.size()-1);
        
        //visited에 start를 추가해줍니다. 여기서 visited는 삭제하는 파트가 없다는 것에 주목해야 합니다. 그래야 다음 dfs호출에서 이미 이전 dfs 호출에서 방문했던 지점인지 파악하는 것에 사용할 수 있기 때문입니다.
        visited.add(start);
        
        //dfs호출이 끝났음에도 false를 발견하지 못했다면 true를 반환해줌으로써 탐색을 종료합니다.
        return true;
        
    }//func
    
}//main class
/*
여기서 주목해야 하는 부분은 dfs 재귀 메서드가 호출되는 구조입니다. 재귀 메서드가 호출되는 것은 다음과 같은 기하학적 구조를 갖습니다.
첫 번째 재귀 호출
    trace에 추가
    |        두 번째 재귀 호출
    |        |    trace에 추가
    |        |            세 번째 재귀 호출
    |        |            |        trace에 추가
    |        |            |        ....
    |        |            |        ...
    |        |            |        ..
    |        |            |        .
    |        |
    |        |
    |        |trace에서 제거 <<< 3rd 재귀 호출이 boolean값을 리턴하며 완전히 종료할 때까지 실행이 미뤄짐
    |        |visited에 추가 <<<  3rd 재귀 호출이 boolean값을 리턴하며 완전히 종료할 때까지 실행이 미뤄짐
    |
    |
    |trace에서 제거 <<< 두 번재 재귀 호출이 boolean값을 리턴하며 완전히 종료할 때까지 실행이 미뤄짐
    |visited에 추가 <<< 두 번재 재귀 호출이 boolean값을 리턴하며 완전히 종료할 때까지 실행이 미뤄짐

    입력값이 다음과 같을 때, dfs 메서드가 실제로 호출되면서 작동하는 양상을 추적해 보면 다음과 같습니다.
    int numCourses = 5;
    int[][] prqs = {{1,4},{2,4},{3,1},{3,2}};


hm Map before dfs : prqs배열을 통해서 출발점을 키로 삼고, 각 출발점에서 도착 가능한 지점의 리스트를 값으로 갖는 해시맵 리스트입니다. upper for는 dfs 호출을 최초로 일으키는 최상위for를 나타냅니다.
1 : [3]
2 : [3]
4 : [1, 2]

***** 1 call of upper for

      dfs called with : 1
       trace : []
       visited : []

      dfs called with : 3
       trace : [1]
       visited : []

***** 2 call of upper for

      dfs called with : 2
       trace : []
       visited : [3, 1]

      dfs called with : 3
       trace : [2]
       visited : [3, 1]
already visited. stop dfs.

***** 4 call of upper for

      dfs called with : 4
       trace : []
       visited : [3, 1, 2]

      dfs called with : 1
       trace : [4]
       visited : [3, 1, 2]
already visited. stop dfs.

      dfs called with : 2
       trace : [4]
       visited : [3, 1, 2]
already visited. stop dfs.

 FINAL RESULT : true
*/








//두 번재 풀이는 4밀리초가 걸렸고, 이는 상위 10%의 성능입니다.
//첫 번째 풀이와 사용하는 논리와 거의 똑같습니다. 대산, 해시맵 같은 컬렉션이 아니라 배열을 바로 조작하는 풀이이기 때문에 풀이시간이 훨씬 빠를 수 있었습니다.
public class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        //return type is boolean

        ArrayList[] graph = new ArrayList[numCourses];

        for(int i=0;i<numCourses;i++)
            graph[i] = new ArrayList();
            
        for(int i=0; i<prerequisites.length;i++){
            graph[prerequisites[i][1]].add(prerequisites[i][0]);
        }

        boolean[] visited = new boolean[numCourses];

        for(int i=0; i<numCourses; i++){
            if(!dfs(graph,visited,i))
                return false;
        }
        return true;
    }

    private boolean dfs(ArrayList[] graph, boolean[] visited, int course){
        if(visited[course])
            return false;
            
        if(graph[course].size()==0)
            return true;
        
        visited[course] = true;;
        for(int i=0; i<graph[course].size();i++){
            if(!dfs(graph,visited,(int)graph[course].get(i)))
                return false;
        }
        visited[course] = false;
        graph[course].clear();
        return true;
    }
}
