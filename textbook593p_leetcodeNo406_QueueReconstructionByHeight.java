//실행시간은 5~11밀리초였고, 이는 상위 1% ~ 상위 약 33% 정도의 성능이었습니다.
//이번 문제 풀이의 핵심은 입력 배열을 정렬을 하든, 우선순위 큐를 이용해서 풀든
// 각각의 요소(==int[]={숫자,숫자})들의 두 번째 값, 즉, 현재 요소의 키보다 크거나 같은 키를 가진 사람의 숫자 값이
// 정답 배열인 answer[][]에서 인덱스의 역할을 함을 이해하는 것입니다.
// 입력배열이 people = [[7,0],[4,4],[7,1],[5,0],[6,1],[5,2]]로 주어졌을 때, 정답은 아래와 같습니다.
//                  [[5,0],[7,0],[5,2],[6,1],[4,4],[7,1]]
//물론, [7,1]과 같이 각 요소의 int person[1] 값이 항상 answer[][]에서의 정확한 인덱스 값을 가리키고 있는 것은 아닙니다.
//그렇기 때문에 PriorityQueue의 활용과, PriorityQueue의 정렬규칙을 문제의 로직에 맞게 재설정하는 것이 중요합니다.
//입력된 people 배열의 내용을 우선순위 큐 내에서 다음의 규칙을 갖도록 재정렬시키는 것이 필요합니다.
/*
* 첫째는 각 요소의 0인덱스 값([7,1]이라면 7.)이 큰 것이 먼저 오도록 내림차순으로 정렬하되,
* 둘째는 각 요소의 0인덱스 값이 서로 동일한 경우에는 2인덱스 값([7,1]이라면 1.)이 작은 것이 앞에 오도록 하는 것입니다.
*
* 이렇게 입력배열을 정렬할 경우, people = [[7,0],[4,4],[7,1],[5,0],[6,1],[5,2]]은
* >>> [[7,0], [7,1], [6,1], [5,0], [5,2], [4,4]]로 우선순위 큐 내에서 정렬됩니다.
* 우선순위 큐를 poll()하여 내용물을 뽑아낼 때 위의 재정렬된 순서대로 뽑아내게 되는 것입니다.
* 그렇게 while루프를 통해서 뽑아낸 녀석들을 ArrayList에 넣어주되, 그냥 넣은 것이 아니라
* 뽑아낸 녀석(즉, int[]{숫자,숫자}타입의 자료)의 1인덱스에 존재하는 값을 ArrayList의 인덱스로 지정하여 add해주는
* 것입니다.
* ArrayList의 add(원하는 인덱스 값, 집어넣으려는 객체); 메서드는 아래와 같이 동작합니다.
* ArrayList<Integer> list = [3]; 인 상태에서 list.add(0,1);을 하면 1이 0인덱스의 자리를 새로 차지하고,
* 원래 0인덱스 자리에 있던 3이라는 값은 뒤로 밀려서 1인덱스에 위치하게 되는 것입니다.
* 결과적으로 list = [1,3] 꼴이 됩니다.
*
* 이것을 우선순위 큐에서 재정렬한 값들을 poll해주면서 적용해주면 결과적으로 정답을 도출할 수 있습니다.
*
* 입력배열이 people = [[7,0],[4,4],[7,1],[5,0],[6,1],[5,2]]로 주어졌을 때, 정답이 어떤 과정을 통해 도출
* 되는지를 로그로 추적하여 기록한 내용이 solution 아래에 기재돼 있습니다.
* */
class Solution {
    public int[][] reconstructQueue(int[][] people) {
        //return type is int[][]

        if(people.length==1) return people;

        PriorityQueue<int[]> queue = new PriorityQueue<>(new MySort());

        for(int[] input : people){
            queue.offer(input);
        }

        ArrayList<int[]> list = new ArrayList<>();

        while(queue.isEmpty()==false){
            //System.out.println("\n---------While 진입.---------");
            int[] popped = queue.poll();
            //System.out.println("\t poll() 된 내용물 : ");
            /*for(int k : popped){
                System.out.print(k + ", ");
            }
            System.out.println();*/
            list.add(popped[1] , popped);
            /*System.out.println("poll()된 내용물 리스트에 추가한 뒤의 ArrayList");
            System.out.print("{  ");
            for(int j=0; j< list.size(); j++){
                System.out.print("{");
                System.out.print(list.get(j)[0]);
                System.out.print(", ");
                System.out.print(list.get(j)[1]);
                System.out.print("}, ");
            }
            System.out.print(" }");*/
        }

        int[][] answer = new int[list.size()][2];

        for(int i=0; i<answer.length; i++){
            answer[i] = list.get(i);
        }

        return answer;

    }//main

    class MySort implements Comparator<int[]>{
        public int compare(int[] b, int[] a){
            int before0 = b[0];
            int after0 = a[0];
            if(before0-after0 != 0){
                return -(before0-after0);
            }
            else{
                int before1 = b[1];
                int after1 = a[1];
                return (before1-after1);
            }
        }
    }//

}//main class
/*
---------While 진입.---------
	 poll() 된 내용물 :
7, 0,
poll()된 내용물 리스트에 추가한 뒤의 ArrayList
{  {7, 0},  }


---------While 진입.---------
	 poll() 된 내용물 :
7, 1,
poll()된 내용물 리스트에 추가한 뒤의 ArrayList
{  {7, 0}, {7, 1},  }


---------While 진입.---------
	 poll() 된 내용물 :
6, 1,
poll()된 내용물 리스트에 추가한 뒤의 ArrayList
>>> 6,1 항목이 list.add(1,{6,1}객체); 되면서 원래 그 자리에 있던
7.1 항목을 뒤로 밀어낸 모습입니다.
{  {7, 0}, {6, 1}, {7, 1},  }



---------While 진입.---------
	 poll() 된 내용물 :
5, 0,
poll()된 내용물 리스트에 추가한 뒤의 ArrayList
{  {5, 0}, {7, 0}, {6, 1}, {7, 1},  }



---------While 진입.---------
	 poll() 된 내용물 :
5, 2,
poll()된 내용물 리스트에 추가한 뒤의 ArrayList
{  {5, 0}, {7, 0}, {5, 2}, {6, 1}, {7, 1},  }



---------While 진입.---------
	 poll() 된 내용물 :
4, 4,
poll()된 내용물 리스트에 추가한 뒤의 ArrayList
{  {5, 0}, {7, 0}, {5, 2}, {6, 1}, {4, 4}, {7, 1},  }
>> 마지막 루프에서 결국 list.add(원하는 인덱스 숫자, 객체); 메서드의 특성에 의해서 결국
정답이 도출되는 모습입니다.
*/









//두 번재 풀이는 아예 입력 배열 자체를 첫 번째 풀이에서 밝힌 우선순위 큐 정렬 규칙에 따라 정렬한 뒤에,
//그 내용물을 첫 번째 풀이와 동일하게 리스트에 넣고, 그 리스트를 static method들을 활용해서 바로 배열로
//변환하여 리턴하는 풀이입니다. 첫 번째 풀이보다 코드가 훨씬 간결하지만 실행시간은 2~3밀리초 정도 느렸습니다.
class Solution {
    public int[][] reconstructQueue(int[][] people) {

        Arrays.sort(people, (a,b)->(a[0] != b[0] ? b[0]-a[0] : a[1] - b[1]));

        List<int[]> res = new ArrayList<>();

        for (int[] p : people) {
            res.add(p[1], p);
        }

        return res.toArray(new int[people.length][2]);
    }
}
