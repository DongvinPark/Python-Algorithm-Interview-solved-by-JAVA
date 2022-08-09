/*
* 이번 문제에서 요구하는 논리는 그리 어렵지 않았습니다.
* 쿠키와 아이들을 크기 순서대로 각각 정렬해주고, 각각의 아이들을 차례대로 큐(또는 리스트)에서 꺼내가면서
* 아이가 마음에 들어할 때까지 쿠키 또한 차례대로 꺼내고, 아이가 만족할 때마다 그 횟수를 누적해서 리턴하면 되기 때문입니다.
*
* 그런데, 이번 문제는 실제 풀이를 해보면서 어떤 자료구조를 어떻게 활용하느냐에 따라서 자꾸 이상한 오류들이 떠서 풀이하는 중간에
* 짜증이 나는 경우가 많았던 문제였습니다.
* */


/*
* g = 1,2,3 / s=3 일때 정답이 1인데, 3이라고 내놓는 오답입니다.
* */
class Failed_Solution_WrngAnswer {
    public int findContentChildren(int[] g, int[] s) {
        //return type is int

        Arrays.sort(g);
        Arrays.sort(s);

        Deque<Integer> gList = new ArrayDeque<>();
        Deque<Integer> sList = new ArrayDeque<>();

        for(int boy : g){ gList.offer(boy); }
        for(int cookie : s){ sList.offer(cookie); }

        int answer = 0;

        int poppedCookie = 0;

        while(!gList.isEmpty() && !sList.isEmpty()){

            int poppedChild = gList.pollFirst();

            while(!sList.isEmpty()){

                poppedCookie = sList.peekFirst();

                /*
                * 오답의 원인은 여기에 었었습니다. 입력이 아이들 = 1,2,3 / 쿠키 = 3 이기 때문에
                * 첫 아이는 당연히 만족을 하게 되고, 카운트는 ++가 됩니다.
                * 그런데 여기서 sList에서 3이라는 쿠키를 꺼내서 제거하지 않은 채로 break;를 해버리는 것이 문제였습니다.
                * 꺼내기도 전에 내부 while 루프를 break를 해버리기 때문에 다음 바깥 while 루프는 쿠키를 첫 번째
                * 아이에게 이미 주었음에도 두 번째 아이에게도 쿠키를 주고, 세 번째 아이도 쿠키를 줍니다.
                *
                * 이건 두 번째와 세 번째 아이도 3보다 작은 쿠키에 만족하는 아이들이었기에 그렇습니다.
                *
                * 결국, 1개뿐인 쿠키가 3명의 아이들에게 모두 분배되는 이상한 상황이 되어서 오답이 되고만 것입니다.
                * */
                if(poppedChild <= poppedCookie){ answer++; break; }
                sList.removeFirst();
            }//I wh

        }//O wh


        return answer;

    }//main
}//main class







/*
* 입력이 아이들 = 1,2,3 / 쿠키 = 1,1 일 때 NoSuchElementException을 던지는 오답입니다.
* NoSuchElementException은 리스트 형태의 자료구조에서 찾고자 하는 아이템을 찾을 수 없을 때 발생하는 예외입니다.
* 이러한 예외가 발생한 이유는 Outer while loop 내의 innter while loop의 조건문이 잘못되었기 때문입니다.
* */
class Failed_Solution_NoSuchElementException {
    public int findContentChildren(int[] g, int[] s) {
        //return type is int

        Arrays.sort(g);
        Arrays.sort(s);

        Deque<Integer> gList = new LinkedList<>();
        Deque<Integer> sList = new LinkedList<>();

        for(int boy : g){ gList.addLast(boy); }
        for(int cookie : s){ sList.addLast(cookie); }

        int answer = 0;

        int poppedCookie = 0;

        while(!gList.isEmpty() ){
            System.out.println("\n------------Outer while loop entered");

            int poppedChild = gList.pollFirst();
            System.out.println("\tpoppedChild : " + poppedChild);

            // ( poppedChild > poppedCookie) || !sList.isEmpty()에서 ||를 쓰면 안 됩니다.
            // 쿠키가 없을 때는 당연히 while루프를 탈툴해야 하는데, 위와 같이 || 연산자를 쓸 경우
            // 쿠키가 없어도, ( poppedChild > poppedCookie) 조건만 만족하면 inner while loop
            // 에 진입하게 되고, 결과적으로 sList가 아미 비어 있는 상태인데도 sLIst.getFirst();
            // 이런 식으로 잘못된 접근을 하게 되기 때문입니다.
            /*
            * 이게 정답이 되기 위해서는 inner while loop를 아래의 내용으로 통째로 교체해야 합니다.
            * 교체된 내용에 따르면, ( poppedChild > poppedCookie) 이 내용도 사실상 필요 없습니다.
            * 아이가 만족할 때까지 쿠키를 꺼내가며 비교하는 작업인 inner while loop를 지속하는 조건은
            * 쿠키가 비어 있지 않을 것과, 아이가 만족하지 않을 것 이 두 가지 뿐인데, 후자의 조건을 바로
            * if(poppedChild <= poppedCookie){ answer++; break; }에서 판단하고 있기 때문입니다.
            * while(true){
                if(sList.isEmpty()) break;
                poppedCookie = sList.pollFirst();
                if(poppedChild <= poppedCookie){ answer++; break; }
            }//I wh
            * */
            while( ( poppedChild > poppedCookie) || !sList.isEmpty()){
                System.out.println("\t\tInner while loop entered");

                System.out.println("\t\tsList status before poll() : " + sList);

                poppedCookie = sList.getFirst();

                System.out.println("\t\tpoppedCookie : " + poppedCookie);

                sList.removeFirst();
                if(poppedChild <= poppedCookie){ answer++; break; }
            }//inner while loop

        }//O wh


        return answer;

    }//main
}//main class
/*
223
111345

*/












/*
* 이 녀석도 바로 위의 오답과 마찬가지의 이유로 오답 입니다. inner while loop의 조건문이 잘못되었고, 정답으로 만들기 위해서는
* 바로 위의 풀이에서 설명한 것처럼 하면 됩니다.
* */
class Failed_Solution_NullPointerException {
    public int findContentChildren(int[] g, int[] s) {
        //return type is int

        Arrays.sort(g);
        Arrays.sort(s);

        LinkedList<Integer> gList = new LinkedList<>();
        LinkedList<Integer> sList = new LinkedList<>();

        for(int boy : g){ gList.addLast(boy); }
        for(int cookie : s){ sList.addLast(cookie); }

        int answer = 0;

        int poppedCookie = 0;

        while(!gList.isEmpty()){

            int poppedChild = gList.pollFirst();

            while( ( poppedChild > poppedCookie) || !sList.isEmpty()){
                poppedCookie = sList.pollFirst();
                if(poppedChild <= poppedCookie){ answer++; break; }
            }//I wh

        }//O wh


        return answer;

    }//main
}//main class
/*
223
111345

*/









//이제 진짜 정답입니다. 실행시간은 18밀리초였고, 이는 하위 약 15%의 성능이었습니다. 실행속도가 빠른 답들은
//거의 대부분 Deque 같은 자료구조를 사용하지 않고, int[]의 인덱스를 직접 계산하는 풀이들이었기 때문이라 생각됩니다.
class Solution {
    public int findContentChildren(int[] g, int[] s) {
        //return type is int

        Arrays.sort(g);
        Arrays.sort(s);

        Deque<Integer> gList = new ArrayDeque<>();
        Deque<Integer> sList = new ArrayDeque<>();

        for(int boy : g){ gList.offer(boy); }
        for(int cookie : s){ sList.offer(cookie); }

        int answer = 0;

        int poppedCookie = 0;

        while(!gList.isEmpty() && !sList.isEmpty()){

            int poppedChild = gList.pollFirst();

            // 이번에 쿠키를 받을 차례인 아이가 만족할 때까지 쿠키를 꺼내가며 비교하는 루프입니다.
            // 쿠키가 비었다면 당연히 while 루프를 벗어나야 합니다.
            // 쿠카가 비어있지 않은 상태에서만 sList에서 쿠키를 꺼낼 수 있고, 그 상태에서
            // 아이가 만족을 해야만 카운트를 증가시킬 수 있습니다.
            // 쿠키가 다 비었다면 외부 while 루프도 역시 탈출하게 됩니다.
            while(!sList.isEmpty()){

                poppedCookie = sList.pollFirst();

                if(poppedChild <= poppedCookie){ answer++; break; }

            }//I wh

        }//O wh


        return answer;

    }//main
}//main class
