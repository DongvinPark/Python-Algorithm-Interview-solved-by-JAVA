//실행시간은 7~14밀리초였고, 이는 상위 21% ~ 75%의 성능이었습니다.
//스택이나 Deque를 사용하지 않고, 순수하게 DFS만으로 풀이해봤습니다.
class Solution {
    //여기서 선언해주면, private void findItineraryFrom() 메서스에서도 인식할 수 있습니다.
    //그러면, 해당 메서드에 전달해야 되는 인자의 개수를 줄일 수 있습니다. result를 따로 전달할 필요가 없기 때문입니다.
    List<String> result;

    public List<String> findItinerary(List<List<String>> tickets) {
        //return type is List< List<String> >.
        result = new ArrayList<>();

        //출발점을 key로 삼고, 그 출발점에서 도착할 수 있는 지점을 리스트의 형태로 저장할 수 있게끔 리스트를 값으로 삼는 해시맵이 필요합니다.
        Map<String, List<String>> map = new HashMap<>();

        //입력된 tickets 리스트의 내용물들을 해시맵에 담아줍니다.
        //그리고, 각 키에 들어 있는 리스트의 값들을 사전순으로 배열해줍니다. 이는 도착점이 겹칠경우 사전순으로 방문해야 한다는 문제의 조건 때문입니다.
        for (List<String> tk : tickets) {
            if (!map.containsKey(tk.get(0))) {
                map.put(tk.get(0), new ArrayList<>());
            }
            map.get(tk.get(0)).add(tk.get(1));
        }
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            Collections.sort(entry.getValue());
        }
        //tickets = [["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]] 이러한 입력이 들어왔다면, 다음과 같은 해시맵이 만들어집니다.
        // 키 :     JFK                   | SFO                   | ATL
        // 키별 값 :  > ATL > SFO          |   > ATL               |  > JFK > SFO

        
        //JFK를 시작점으로 삼아서 dfs 메서드를 호출해줍니다.
        findItineraryFrom("JFK", map, new ArrayList<>(), tickets.size());

        result.add(0, "JFK");

        return result;
    }//main

    private void findItineraryFrom(
            String start,
            Map<String, List<String>> map,
            List<String> curRes,
            int numTickets
    ) {
        if (curRes.size() == numTickets) {
            result.addAll(curRes);
            return;
        }

        //위에서 정의한 해시맵에서 키를 통해서, 해당 키를 시작점으로 봤을 때 그 시작점에서 도착할 수 있는 지점들의 리스트를 가져올 수 있습니다.
        //그런데 그 리스트가 존재하지 않는다면, 그 시작점에서는 더이상 도착 할 수 있는 지점이 없다는 뜻입니다.
        //모든 티켓을 사용하기도 전에 이러한 상황이 되었다면, 해당 경로는 유효하지 않으므로, dfs 호출을 즉시 종료해야 합니다.
        if (map.get(start) == null) {
            return;
        }

        //위의 두 if 문에 해당하지 않는다면, 다음 dfs를 호출할 수 있는 조건이 충족됩니다.
        //주어진 시작점인 start에서 도찰할 수 있는 지점들의 리스트를 해시맵에서 꺼내 온 후, 그 리스트의 도착지점들에 대해서 dfs호출을 해줄 것입니다.
        for (int i = 0; i < map.get(start).size(); i++) {
            //도착점을 임시저장해줍니다.
            String dest = map.get(start).get(i);
            //그 후 도착점을 리스트에서 없애줍니다. 한번 사용한 티켓은 다시 사용할 수 없기 때문입니다.
            map.get(start).remove(dest);

            //curRes에 현재 for루프의 도착점을 저장한 후, 그 현재 도착점을 시작점으로 하여 dfs호출해 줍니다.
            curRes.add(dest);
            findItineraryFrom(dest, map, curRes, numTickets);

            //만약, 유효한 경로가 최초로 발견된다면 더이상 dfs호출을 지속할 이유가 없습니다. 즉시 dfs호출을 멈춰줍니다.
            if (result.size() > 0) {
                return;
            }

            //앞에서 삭제했었던 도착점을 다시 집어넣어 줍니다. 그래야 다음 for루프에서 온전한 해시맵을 넘겨줌으로써 정확한 작동이 가능해집니다.
            map.get(start).add(i, dest);
            //curRes에서도 위와 같은 이유로 마지막 요소를 제거해 줘야 합니다. curRes는 예비 정답리스트와도 같은데, 현재 for루프에서 살펴봤던 경로를 다음 for루프에서도 중복으로 살펴보고 curRes에 추가해주는 일은 없어야 하기 때문입니다.
            curRes.remove(curRes.size() - 1);
        }//for

    }//func

}//main class
