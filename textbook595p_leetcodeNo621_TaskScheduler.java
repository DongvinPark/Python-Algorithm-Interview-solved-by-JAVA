//첫 번째 풀이는의 실행시간은 약 79밀리초였고, 이는 하위 17%의 성능이었습니다.
//우선, PriorityQueue에 들어가야하는 자료형이 Integer 타입의 숫자임을 이해하는 것이 중요합니다.
//인트 타입 숫자가 아닌, Character 타입 자료형을 넣게 될 경우, 가장 자주 등장하는 것이
// 우선 추출될 수 있도록 규칙을 재설정하여 우선순위 큐를 생성하는 것이 복잡하고 까다로워지기 때문입니다.
/*
* 이번 문제풀이에서 가장 중요한 것은 작업 횟수를 최소화시키는 조건을 파악하는 것입니다.
* 작업 횟수가 최소화되기 위해서는 우선 idle 횟수가 최소가 되어야 합니다.
* 그리고, 가장 자주 등장하는 작업들 사이사이에 그 작업이 아닌 다른 작업을 n번 끼워넣는 작업이
* 최대한 여러번 실행 되어야 합니다.
* */
class Solution {
    public int leastInterval(char[] tasks, int n) {
        Map<Character, Integer> task_counter = new HashMap<>();

        //tasks의 문자별 등장 빈도수를 값으로 갖도록 해시맵을 셋팅해줍니다.
        for(Character task: tasks) {
            task_counter.put(task, task_counter.getOrDefault(task,0) + 1);
        }


        //등장 빈도수가 가장 높은 숫자가 우선적으로 추출될 수 있도록 우선순위 큐를 생성해주고,
        //만들어뒀던 해시맵에서 값 리스트를 이용하여 큐를 초기화해줍니다.
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        pq.addAll(task_counter.values());


        int answer = 0;

        //큐의 내용물이 하나도 없게 될 때까지 while루프를 지속합니다.
        while(pq.size() > 0) {
            //System.out.println("\n-------------while enter--------------");
            //큐에서 꺼낸 후, count변수에 저장하고, 그 count를 임시로 저장할 객체를 만들어줍니다.
            List<Integer> add_back = new ArrayList<>();

            //n+1번 작업을 반복합니다.
            /*
            * 이것은 정답이 구성되면서 나타나는 특징 때문입니다.
            * 리트코드엣 제시한 예시 3번에서는 입력값이 AAA AAA B C D E F G 였고, n==2일때
            *  답은
            * ABC ADE AFG A## A## A였습니다. #은 idle입니다.
            * A 사이에 A가 아닌 작업이 2개 들어가 있고, A가 아닌 작업이 전부 처리되고 나면,
            * A 사이를 idle로 채우고 있습니다.
            * 즉, 한 개의 덩어리가 n+1개로 구성돼 있는 것을 확인할 수 있기에 이를 정답의 도출
            * 과정에서 사용하고 있는 것입니다.
            * */
            for(int i= 0; i <= n; i++) {
                //System.out.println("\t for enter");
                //큐가 비어있지 않다면 큐에서 가장 등장빈도수가 높은 값을 추출하고 그 횟수를
                //1회 감소시킵니다.
                //그리고 감소된 count가 0보다 클 때만 추후에 다시 큐에 넣어주기 위해서
                //add_back 이라는 임시 객체에 넣어줍니다.
                //만약 큐에서 꺼낸 등장 횟수 숫자가 1이었다면 이번의 추출로 인해서
                //count가 0으로 변할 것이고, 이 카운트는 add_back에 추가되지 않기 때문에
                //결국 큐에서 영구히 삭제되는 결과를 가져오게 됩니다.
                //이로써 결국 큐는 while 루프를 반복할수록 비워지게 되고, while 루프는 결국
                //끝나게 됩니다.
                //for 루프를 반복하다가 큐에서 뽑아낼 것이 아무 것도 없을 때가 곧 idle이
                //카운트 되는 순간입니다. 예를 들어서 입력값이 AAA BBB, n=2라면,
                //for 루프는 3번 실행되어야 하는데, 앞의 2번의 for루프를 통해 이미 큐에서는
                //A의 등장횟수와 B의 등장횟수가 아예 0이 돼 버리기 때문입니다.
                //이때는 하는 수 없이 idel을 넣어줘야 합니다. 입력값이 이러한 상황에서의
                //실제 while 루프의 작동과정을 기록한 로그가 아래에 첨부되어 있습니다.
                if(pq.size() > 0) {
                    //System.out.println("\t\tqueue not empty");
                    int count = pq.poll();
                    count--;
                    if(count > 0) {
                        add_back.add(count);
                    }
                }//O if
                /*else if (pq.size()==0){
                    System.out.println("\t\tqueue is EMPTY!!");
                }*/

                //그 후 answer를 ++ 해줍니다.
                System.out.println("\t\t\t___정답 누적!!___");
                answer += 1;

                //이 부분은 모든 작업이 완료 된 후 바로 루프를 탈출하기 위해서 존재합니다.
                //모든 작업이 끝났는데도 굳이 idle같은 것을 추가해서는 안 되기 때문입니다.
                if(pq.size() == 0 && add_back.size() == 0) {
                    break;
                }

            }//for

            //n+1 개의 덩어리를 구성하면서 answer를 누적적으로 카운트해주는 작업을 끝낸 후에는
            //작업 다음 while 루프를 위해서 add_back 리스트 객체를 다시 큐에 집어넣어 줍니다.
            pq.addAll(add_back);

        }//wh

        return answer;
    }
}
/*
-------------while enter--------------
	 for enter
		queue not empty
			___work counted___
	 for enter
		queue not empty
			___work counted___
	 for enter
		queue is EMPTY!!
			___work counted___

-------------while enter--------------
	 for enter
		queue not empty
			___work counted___
	 for enter
		queue not empty
			___work counted___
	 for enter
		queue is EMPTY!!
			___work counted___

-------------while enter--------------
	 for enter
		queue not empty
			___work counted___
	 for enter
		queue not empty
			___work counted___

Final answer : 8
* */







//두 번째 풀이는 어떤 자료구조도 사용하지 않고, 그저 배열 하나만을 이용할 뿐이어서 그런지
// 3밀리초의 실행시간에 상위 1%이내라는 경이로운 실행속도를 보여주고 있습니다.
//이것이 가능한 이유는 입력 tsak 배열이 영문자 알파벳으로만 구성돼 있기 때문입니다.
/*
* 그리고, 문제에서 필요로 하는 로직을 정확하게 이해하는 것을 필요로 합니다.
* 생각해보면 이 문제의 정답을 결정하는 핵심요건은 결국 Idle이 몇 번 들어가야 하냐입니다.
* Idle이 들어야하는 횟수를 정확히 알아낼 수만 있다면, 그 횟수를 tasks.length 값에
* 추가해서 리턴해주기만 하면 되는 것입니다.
* */
class Solution {
    public int leastInterval(char[] tasks, int n) {

        //우선 알파벳 별로 등장한 횟수를 세서 등장 횟수를 기록해주는 배열을 만들어줍니다.
        //예를 들어서 A라는 문자는 charMap[]의 0인덱스를 통해서 나타낼 수 있습니다.
        //만약 입력값이 AAABCD 라면, charMap[0]에는 3이라는 값이 기록되는 것입니다.
        int[] charMap = new int[26];
        for(int i=0; i<tasks.length; i++){
            charMap[tasks[i] - 'A']++;
        }

        //위에서 만들었던 배열에서 가장 자주 등장한 문자의 등장횟수를 알아내기 위해서 배열을
        //오름차순으로 정렬해줍니다. Arrays.sort()의 기본 동작이 오름차순정렬이므로
        //그저 charMap만 전달해주면 됩니다.
        Arrays.sort(charMap);

        //최빈 등장 횟수는 정렬된 배열의 마지막 요소의 값입니다. 여기서 1을 뺀 값을 저장해둡니다.
        int max = charMap[25] - 1;

        //배치될 수 있는 Idle의 최댓값을 셋팅해줍니다.
        /*
        * 예를 들어서 입력값이 A A A A A B C D E F G이고, n==2 라고 해 보겠습니다.
        * 이때, BCDEFG는 그 사이에 idle을 끼워 넣을 필요가 없습니다. 이미 서로 완전히 다른
        * 작업들이기 때문입니다. 그리고, BCDEFG가 접촉하고 있는 5번째 A는 뒤에 idle을
        * 붙일 필요가 없습니다. 그 A 이후에는 BCDEFG라는 전부 서로 다른 문자들이 배치되기 때문입니다.
        * 그렇다면, Idle이 끼어들 수 있는 최댓값은 A의 갯수에서 1을 빼준 값에 n을 곱한 값이 됩니다.
        * 여기서 말하는 Idle의 최댓값은 결국 동일한 작업들을 서로 전허 분리시키지 않아서
        * 발생할 수 있는 가장 비효율적인 작업 환경 하에서 발생함을 알 수 있습니다.
        *
        * 이제 이렇게 셋팅한 slots 값에서 필요없는 Idle의 갯수를 빼서 tasks.length 값과 더하면
        * 되는 것입니다.
        * */
        int slots = max * n;

        /*
        * 필요없는 Idle 들을 삭제하는 과정은 다음과 같습니다. 위에서 들었던 AAAAA BCDEFG의 예를
        * 가져와보면, 동일한 작업들인 A를 서로 분리시키지 않아서 발생하는 가장 비효울적인 작업루트는
        * 아래와 같고, 이때 #은 Idle입니다.
        * A## A## A## A## A B C D E F G
        * 여기서 작업을 효율화 시키는 방법은 결국, A가 아닌 작업들을 # 위치에 끼워넣으면서 #들을
        * 삭제시키는 것입니다. 따라서 BCDEFG가 아래와 같이
        * ABC ADE AFG A##A 로 만들면 되는 것입니다.
        *
        * 하지만, 여기서 Math.min을 사용하는 이유가 있습니다.
        * 입력이 AAABBB 일때, A##A##ABBB 인 상태에서 B의 등장횟수인 charMap[i]를
        * 그냥 바로 빼버리면 AB#ABBA라는 오답이 되고 맙니다.
        * 따라서, 이것을 방지해주기 위해서 charMap[i]를 빼주되, 가장 자주 등장한 문자
        * 의 등장횟수에서 1을 뺀 값(==max)과 비교해서 더 작은 값을 빼주는 것입니다.
        * AAABBB의 경우에서 max는 2고, B는 3회 등장했으므로, idle은 3개가 아니라
        * 2개만 빠지게 되고, 결국 정답인 AB#AB#AB, 8이 되는 것입니다.
        * */
        for(int i=24; i>=0; i--){
            slots -= Math.min(charMap[i], max);
        }

        //슬롯이 0이라면 n==0인 상황과 동일합니다. 따라서 그냥 tasks.length를 반환하면 되고,
        //그렇지 않다면 tasks.length + slots 를 반환하면 됩니다.
        return slots > 0 ? slots + tasks.length : tasks.length;
    }
}
