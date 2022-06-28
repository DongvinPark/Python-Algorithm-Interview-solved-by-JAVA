//실행시간은 35~60밀리초였고, 이는 하위 약 5%의 성능이었습니다.
//배열을 직접 조작하는 데신 ArrayList와 같이 더 무거은 클래스를 쓰고, Collections와 new 연산 각종 sort 연산이 추가되면서 실행 속도가 느리게 나왔습니다.
class Solution {
    public int[][] merge(int[][] intervals) {
        //return type is int[][]

        if(intervals.length == 1) return intervals;

        ArrayList< ArrayList<Integer> > listOfArray = new ArrayList<>();

        //입력으로 들어온 2차 int[][]을ArrayList< ArrayList<Integer> > listOfArray에 입력해줍니다.
        for(int i=0; i< intervals.length; i++){
            ArrayList<Integer> input = new ArrayList<>();
            for(int j=0; j<intervals[i].length; j++){
                input.add(intervals[i][j]);
            }//I for
            listOfArray.add(input);
        }//O for

        //앞에서 만들어놓은 listOfArray의 요소들을 각 요소의 첫 번째 값을 기준으로 정렬할 수 있도록 람다 표현식을 이용해서 규칙을 전달해줍니다.
        Collections.sort(listOfArray, (a, b) -> a.get(0).compareTo( b.get(0) ));

        //병합을 완료한 값들을 입력하기 위한 리스트를 만들고, 그 리스트에 첫 요소를 미리 입력해 놓습니다.
        //이때, listOfArray 에서는 mergedList에 첫 요소를 전달한 후에 그 첫 요소를 listOfArray에서 삭제합니다.
        ArrayList<ArrayList<Integer>> mergedList = new ArrayList<>();
        mergedList.add(listOfArray.get(0));
        listOfArray.remove(0);

        //listOfArray의 내용물이 전부 사라질 때가지 while 루프 내의 내용을 실행합니다.
        while(listOfArray.isEmpty()==false){

            //mergedList의 마지막 요소와, listOfArray의 첫 내용물을 가져와서 서로 비교합니다.
            //이때, listOfArray에서 가져온 내용물은 listOfArray에서 삭제해야 함을 기억해야 합니다.
            ArrayList<Integer> origin = mergedList.get(mergedList.size()-1);
            ArrayList<Integer> newOne = listOfArray.get(0);
            listOfArray.remove(0);

            //newOne 의 값을 origin의 값과 병합을 할 건지 말 건지를 결정해야 합니다.
            //앞에서 listOfArray의 값들을 그 값들의 첫 번째 요소를 기준으로 정렬했습니다.
            //예를 들어서 origin == (1,2), newOne == (4,5)라면, 이 둘 사아에는 서로 겹치는 부분이 전혀 존재하기 않기 때문에 newOne을 그저 mergedList에 추가하기만 하면 됩니다.
            if(origin.get(1) < newOne.get(0)) mergedList.add(newOne);

            //그러나, 서로 겹치는 부분이 발생할 경우, 이것을 일일이 경우의 수를 나누어서 처리하는 것은 비효율적이고 오류가 발생하기 쉽습니다.
            //겹치는 부분이 존재할 수 있는 모든 경우를 다 정리하면 다음과 같고, 이것을 일일이 다 else if ... 들로 나누기에는 중간에 실수할 가능성이 크기 때문입니다.
            /*
            * >> 1,2 && 2,3과 같은 경우 / 1,2 && 1,4와 같은 경우 / 1,2 && 1,2와 같은 경우 / 1,4 && 2,7과 같은 경우 / 1,4 && 2,3과 같은 경우 를 전부 따져야 합니다.
            * */
            //하지만 결국 핵심은 4개의 서로 다른 숫자들 중에서 가장 작은 숫자와 가장 큰 숫자를 골라내는 것이기 때문에 이러한 복잡한 경우의 수 나누기 대신, origin, newOne 으로부터 4개의 숫자를 입력 받아서 정렬한 후, 첫 요소가 마지막 요소를 골라내기만 하면 됩니다.
            else{
                ArrayList<Integer> comp = new ArrayList<>();
                comp.add(origin.get(0));
                comp.add(origin.get(1));
                comp.add(newOne.get(0));
                comp.add(newOne.get(1));
                Collections.sort(comp);
                mergedList.get(mergedList.size()-1).set(0, comp.get(0));
                mergedList.get(mergedList.size()-1).set(1, comp.get(3));
            }
        }//wh

        //이제 mergedList를 다시 배열 형태로 바꿔서 리턴해주면 됩니다.
        int[][] answer = new int[mergedList.size()][2];
        for(int i=0; i<mergedList.size(); i++){
            answer[i][0] = mergedList.get(i).get(0);
            answer[i][1] = mergedList.get(i).get(1);
        }

        return answer;

    }//main
}//main class
