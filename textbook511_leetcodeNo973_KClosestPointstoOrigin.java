//실행시간은 31밀리초였고, 이는 상위 14퍼센트의 성능이었습니다.
//첫 번째 풀이는 정렬을 위한 커스텀 클래스를 만들어서 Arrays.sort()메서드에 전달함으로써 유클리드 거리에 따른 오름차순으로 정렬하는 방법을 사용했습니다.
class Solution {
    public int[][] kClosest(int[][] points, int k) {
        //return type is int[][]

        Arrays.sort(points, new DistanceSort());

        int[][] answer = new int[k][2];

        for(int i=0; i<k; i++){
            answer[i] = points[i];
        }

        return answer;
    }


}//main

//Comparator 를 구현하는 커스텀 클래스를 정의하고, Comparator 중에서도 compare()메서드를 내가 원하는 로직에 맞춰서 오버라이드해줍니다.
//그리고 이렇게 만든 커스텀 클래스는 Arrays.sort()메서드에 정렬 규칙을 전달하는 인자로서 활용됩니다.
class DistanceSort implements Comparator<int[]>{
    public int compare(int[] b, int[] l){

        int bD = (b[0]*b[0])+(b[1]*b[1]);
        int lD = (l[0]*l[0])+(l[1]*l[1]);

        if(bD > lD){return 1;}
        else if(bD < lD){return -1;}
        else{return 0;}

    }//func

}//main class







//두 번째 풀이의 실행시간은 첫 번째 풀이의 실행시간과 동일하게 측정되었습니다.
//첫 번째 풀이와 다른 점은, PriorityQueue라는 별도의 자료구조에 람다식으로 정렬 규칙을 전달한다는 점입니다.
//정렬용 커스텀 클래스를 만들 필요가 없으므로 더 짧은 코드로 풀이할 수 있습니다.
class Solution {

    public int[][] kClosest(int[][] points, int k) {

        PriorityQueue<int[]> q = new PriorityQueue<>(
                (p1, p2) -> ( (p1[0]*p1[0] + p1[1]*p1[1]) - (p2[0]*p2[0] + p2[1]*p2[1]) )
        );

        for(int[] p : points) {
            q.add(p);
        }

        int[][] res = new int[k][];

        for (int i = 0; i < k; i++) {
            res[i] = q.poll();
        }

        return res;
    }

}
