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
        //tickets = [["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]] 이러한 입력이 들어왔다면,
	//다음과 같은 해시맵이 만들어집니다.
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
            //curRes에서도 위와 같은 이유로 마지막 요소를 제거해 줘야 합니다. curRes는 예비 정답리스트와도 같은데, 현재 for루프에서 살펴봤던
	    //경로를 다음 for루프에서도 중복으로 살펴보고 curRes에 추가해주는 일은 없어야 하기 때문입니다.
            curRes.remove(curRes.size() - 1);
        }//for

    }//func

}//main class





//두 번째 풀이는 dfs를 이용한 것은 동일하지만, 해시맵의 '값' 부분에 있는 각각의 리스트들에 대해서 '큐'연산을 적용하고 있다는 차이점이 있습니다.
//실행시간은 10밀리초였고, 이는 상위 50% 정도의 성능입니다.
class Solution {
    public List<String> findItinerary(List<List<String>> tickets) {
        //retutn type is List<String>
        List<String> answer = new ArrayList<>();

        HashMap<String, ArrayList<String>> hm = new HashMap<>();

        //첫 번째 풀이와 동일하게 출발점을 '키'로 하고, 각각의 출발점에서 도착 가능한 지점의 리스트를 '값'으로 하는 해시맵을 만든 후,
	//해시맵 내의 모든 '값'들에 대해서 사전순 정렬을 해 줍니다.
        for(int i=0; i<tickets.size(); i++){
            if(!hm.containsKey(tickets.get(i).get(0)) ){
                hm.put(tickets.get(i).get(0), new ArrayList<String>());
            }
            hm.get(tickets.get(i).get(0)).add(tickets.get(i).get(1));
        }//for
        for(String s : hm.keySet()){
            Collections.sort(hm.get(s));
        }

        //JFK를 출발점으로 삼아 dfs 호출을 시작합니다.
        dfs(hm, "JFK", answer);

        //여기서 왜 reverse로 순서를 뒤집어줘야 하는지에 대해서는 코드의 맨 아래부분의 설명을 참고하시길 바랍니다.
        Collections.reverse(answer);

        return answer;
    }//main

    public static void dfs(
            HashMap<String, ArrayList<String>> hm,
            String start,
            List<String> answer
    ){

        //이 부분은 [출발-도착] 이라는 티켓들의 연결이 순환 구조를 가지지 않고 일방향으로 연결되다가 끊어지는 경우에 발생하는
	//NullPointerException을 방지하기 위한 처리입니다. 이 경우 마지막으로 호출된 dfs의 인자 중 start를 answer에 추가한 후,
	//dfs호출을 즉시 종료해주면 됩니다.
        if(hm.get(start)==null){
            answer.add(start); return;
        }

        //정답을 도출해나가는 핵심은 이 부분입니다.
        //이번에 호출된 dfs에서 start로 춟발점에 대한 정보를 받습니다. 그러면 앞서 만들어놓은 해시맵에서 출발점을 키로 갖고 있는 '값'을 가져올 수 있고,
	// 이 '값'에는 해당 출발점에서 도착할 수 있는 다른 공항들의 리스트가 들어 있습니다. 사전순 방문이므로 리스트의 첫 번째 요소부터 방문해야 합니다.
        //가능한 모든 대안을 살펴봐야 하므로 리스트의 요소가 없을 때까지 while 문을 반복해줍니다.
        while(!hm.get(start).isEmpty()){

            String next = hm.get(start).get(0);
            hm.get(start).remove(0);

            //while 문 내부에서 dfs가 다시 호출되는데, 이때는 이번 호출의 도착점을 새로운 출발점으로 삼아서 dfs 호출을 해줘야 합니다. 리스트에서
	    //next에 해당하는 값은 삭제되므로, 한 번 방문하는 것에 사용된 티켓이 다시 사용되는 일은 없습니다.
            dfs(hm, next, answer);
        }//wh

        answer.add(start);
    }//func

}//main class

/*
* 이러한 dfs 호출 구조를 [[JFK, SFO], [JFK, ATL], [SFO, ATL], [ATL, JFK], [ATL, SFO]]이라는 입력에 대해서 실행해 보면 다음과 같은 실행과정을 거칩니다.
* HashMap<String, <List>String > before dfs calls :
	Key : ATL[JFK, SFO]
	Key : SFO[ATL]
	Key : JFK[ATL, SFO]

	1st dfs called. parameter is : JFK
		hm.get(JFK) elements before pop() : [ATL, SFO]
		hm.get(JFK) elements after pop() : [SFO]
		popped element : ATL

	2nd dfs called. parameter is : ATL
		hm.get(ATL) elements before pop() : [JFK, SFO]
		hm.get(ATL) elements after pop() : [SFO]
		popped element : JFK

	3rd dfs called. parameter is : JFK
		hm.get(JFK) elements before pop() : [SFO]
		hm.get(JFK) elements after pop() : []
		popped element : SFO

	4th dfs called. parameter is : SFO
		hm.get(SFO) elements before pop() : [ATL]
		hm.get(SFO) elements after pop() : []
		popped element : ATL

	5th dfs called. parameter is : ATL
		hm.get(ATL) elements before pop() : [SFO]
		hm.get(ATL) elements after pop() : []
		popped element : SFO

	6th dfs called. parameter is : SFO

answer : [SFO]
answer : [SFO, ATL]
answer : [SFO, ATL, SFO]
answer : [SFO, ATL, SFO, JFK]
answer : [SFO, ATL, SFO, JFK, ATL]
answer : [SFO, ATL, SFO, JFK, ATL, JFK]

Final Result : [JFK, ATL, JFK, SFO, ATL, SFO]

* * dfs 호출이 while 문 내부에서 중첩되고, answer에 정답을 추가하는 것은 while 문 밖에 있습니다. 그렇기 때문에, dfs 6중첩이 끝날 때 까지
* answer에 정답을 추가하는 것이 미뤄집니다.
* dfs 6번째 호출이 끝나고나서야 6번째 호출의 answer.add()가 실행됩니다. 그후, 5,4,3,2,1 번째 호출의 answer.add()가 차례대로 실행됩니다.
* answer.add()가 첫 번째 호출이 아닌 6번째 호출부터 실행되기 때문에, answer 리스트의 내용물들은 정답과 반대의 순서로 구성될 수밖에 없습니다.
* 따라서 모든 dfs 호출이 종료된 후, answer리스트의 순서를 Collections.reverse(answer);로 반대로 바꿔줘야 정답입니다.
* */
