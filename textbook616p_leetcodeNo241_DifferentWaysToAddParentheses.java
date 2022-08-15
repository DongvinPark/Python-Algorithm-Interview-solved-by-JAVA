//첫 번째 풀이는 오답입니다.
/*

이 풀이는 정답이 입력 문자열이    15 - 7 * 6 + 24    이고, 정답이 [-195,-51,240,-3,72] 일때,
오답인 [-195, 240, 72]를 리턴합니다.

위와 같은 입력문자열이 주어졌을 때, 도출 가능한 경우의 수는 아래와 같은 계산 과정을 거쳐서 총 5가지 입니다.

15 - (7 * (6 + 24)) = -195
15 - ((7 * 6) + 24) = -51

((15 - 7) * (6 + 24)) = 240

(15 - (7 * 6)) + 24 = -3
((15 - 7) * 6) + 24 = 72

아래의 코드를 잘 살펴보면, for 루프를 1번 밖에 사용하고 있지 않습니다. 따라서, 정답이 나오는 경우의 수가 위와 같을 때
아래와 같은 경우는 정답을 계산해내지만,

15 - (7 * (6 + 24)) = -195     <<<     첫 번째 recul 호출의 첫 번째 for 루프에서 정답 도출
((15 - 7) * (6 + 24)) = 240     <<<    첫 번째 recul 호출의 두 번째 for 루프에서 정답 도출
(15 - (7 * 6)) + 24 = -3     <<<     첫 번째 recul 호출의 세 번째 for 루프에서 정답 도출

'('이러한 괄호가 연달아서 나오는 -51, 72의 경우에는 정답을 도출해 내지를 못합니다.
그 이유는, 아래의 풀이에서는 15 - (7 * (6 + 24)) = -195 인 경우를 계산한 후, 15 - ((7 * 6) + 24) = -51
까지 계산하지 않은 채 그냥 바로

((15 - 7) * (6 + 24)) = 240 부분으로 넘어가기 때문입니다.

마찬가지로, (15 - (7 * 6)) + 24 = -3 부분을 계산하지 않은 채 바로 ((15 - 7) * 6) + 24 = 72 바로 이 부분만을
계산하고 최종적으로 정답을 냅니다.

* */

class Failed_Solution {

    public List<Integer> diffWaysToCompute(String expression) {
        //return type is LIst<Integer>

        LinkedList<Integer> answer = recur(expression);

        return answer;

    }//main

    public LinkedList<Integer> recur(
            String input
    ){
        ////System.out.println("RECUR CALLED. INPUT : " + input);

        LinkedList<Integer> answer = new LinkedList<>();

        if(input.indexOf('+')==-1 && input.indexOf('-')==-1 && input.indexOf('*')==-1){
            answer.addLast(Integer.valueOf(input));
            return answer;
        }

        for(int i =0; i<input.length(); i++){

            ////System.out.println("\n---------for entered--------loop No. : " + i);

            char ch = input.charAt(i);
            ////System.out.println("current char : " + ch);

            LinkedList<Integer> left = new LinkedList<>();
            LinkedList<Integer> right = new LinkedList<>();

            //연산자를 기준으로 좌우를 나눈다.

            if(ch=='+' || ch=='-' || ch=='*'){
                ////System.out.println("\t연산자 발견!! : " + ch);

                left = recur(input.substring(0,i));
                right = recur(input.substring(i+1, input.length()));

                //-------------- 연산자에 따라서 적절한 조치를 취하여 List<Integer>를 리턴한다.

                if(ch=='+'){
                    /*//System.out.println("\t\t + 진입");
                    //System.out.println("\t\t 좌 : " + left);
                    //System.out.println("\t\t 우 : " + right);*/
                    int leftR = left.getFirst();
                    int rightR = right.getFirst();
                    answer.addLast( (leftR+rightR) );
                }

                else if(ch=='-'){
                    //System.out.println("\t\t");
                    //System.out.println("\t\t 좌 : " + left);
                    //System.out.println("\t\t 우 : " + right);
                    int leftR = left.getFirst();
                    int rightR = right.getFirst();
                    answer.addLast( (leftR-rightR) );
                }

                else if (ch=='*'){
                    //System.out.println("\t\t");
                    //System.out.println("\t\t 좌 : " + left);
                    //System.out.println("\t\t 우 : " + right);
                    //left만 값이 1개 뿐
                    if(left.size()==1 && right.size()!=1){
                        //System.out.println("\t\t\t 왼쪽만 1개");
                        for(int inR : right){
                            answer.addLast(left.getFirst()*inR);
                        }
                    }

                    //right만 값이 1개 뿐
                    else if(left.size()!=1 && right.size()==1){
                        //System.out.println("\t\t\t 오른쪽만 1개");
                        for(int inL : left){
                            answer.addLast(right.getFirst()*inL);
                        }
                    }

                    //둘 다 값이 1개만 있다
                    else if(left.size()==1 && right.size()==1){
                        //System.out.println("\t\t\t 둘 다 1개");
                        answer.addLast( ( left.getFirst() * right.getFirst() ) );
                    }

                }//O elif


            }//Outer if

        }//for

        //System.out.println("\t\t반환된 answer : " + answer);

        return answer;

    }//func

}//main class











//두 번째 풀이의 실행시간은 5밀리초였고, 이는 하위 36%의 성능이었습니다.
/*

15 - (7 * (6 + 24)) = -195
15 - ((7 * 6) + 24) = -51

((15 - 7) * (6 + 24)) = 240

(15 - (7 * 6)) + 24 = -3
((15 - 7) * 6) + 24 = 72

* */

/*

재귀 호출 루프를 추적해보면 다음과 같습니다. 그림이 복잡한 편이기 때문에 전부 추적하지는 않고,

아래의 풀이가 첫 번째 오답 답안에서 놓쳤던 -51을 어떻게 잡아내서 정답리스트에 추가하는지를 추적했습니다.

각각의 호출은 순서대로 번호를 매겼고, () 안에 호출시 파라미터로 전달된 문자열을 기록했습니다.


1호출(15-7*6+24)-------> 2호출(15){1호출에 15 리턴}
                   |(마이너스 연산!)
                   ...> 3호출(7*6+24)---------> (7*6)+24의 경우.{66리턴} ------------------------> 42리턴
                           {66,210}리턴!!|                                         |(플러스 연산!)
                     특히, 66은           |                                         |.............> 24리턴
                     아까 빼먹었던 그놈임!!  |(곱하기 연산!)
                                       --------> 7*(6+24)의 경우.{210리턴}--------------> 6리턴
                                                                            |(플러스 연산!)
                                                                            |.........> 24리턴

.
  .
   .
    .
     .

* */

class Solution {
    public List<Integer> diffWaysToCompute(String expression) {
        System.out.println("\n\nMAIN RECAR FUNC CALLED / input : " + expression);

        List<Integer> answer = new ArrayList<>();

        if (!expression.contains("+") && !expression.contains("-") && !expression.contains("*")) {
            System.out.println("\n\n숫자만 있어서 그냥 숫자 리턴");
            answer.add(Integer.valueOf(expression));
        }

        else {

            for (int i = 0; i < expression.length(); i++) {
                System.out.println("\n      ------ Outer For 진입 -------- No. : " + i);

                char ch = expression.charAt(i);

                if (Character.isDigit(ch)) {
                    continue;
                }

                List<Integer> left = diffWaysToCompute(expression.substring(0, i));
                List<Integer> right = diffWaysToCompute(expression.substring(i + 1, expression.length()));

                System.out.println("\t left list : " + left);
                System.out.println("\t right list : " + right);

                // for(int inLeft: left){...} 바로 이 부분이 숨겨져 있던 정답인 -51을 찾아내느데 결정적인 역할을 했습니다.
                // Left의 모든 요소에 대해서 right의 요소들과의 연산 결과를 기록해야 하는데, 첫 번째 풀이에서는 Left 리스트를
                // 무시한 채, right 리스트에 대해서만 계산을 했었던 것입니다.
                for (int inLeft: left) {

                    for (int inRight: right) {
                        if (ch == '+') { answer.add(inLeft + inRight); }
                        else if (ch == '-') { answer.add(inLeft - inRight); }
                        else if (ch == '*') { answer.add(inLeft * inRight); }
                    }//I for

                }//O for

            }//O O for

        }//Outer else

        System.out.println("RETURNED LIST : " + answer);
        return answer;
    }
}
