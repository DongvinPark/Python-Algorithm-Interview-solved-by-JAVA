//첫 번째 풀이는 재귀적 호출을 이용한 풀이입니다.
//문자열에서 중복되는 문자들을 제거하고 알파벳 순으로 재배열하여 ArrayList<Character> 타입으로 반환해주는 별도의 메서드인 remvDupleSort()를 반복적으로 호출하다보니 실행시간이 약 46밀리초 정도 나오며 하위 5%정도의 성적을 기록했습니다.
class Solution {
    public String removeDuplicateLetters(String s) {
        //return type is String
                System.out.println("main function called");

                //우선 입력 문자열에서 중복되는 문자들을 제거하고 알파벳 순으로 정렬한 리스트에서 문자를 하나식 꺼내며 비교해 나갑니다.
        for(Character c : remvDupleSort(s)){

            //현재 비교하고자 하는 문자가 첫 입력 문자열에서 최초로 등장하는 위치를 찾습니다.
            //예를 들어서 입력 문자열이 s = "cbacdcbc" 라면, remvDupleSort(s)는 [a,b,c,d]입니다.
            //여기서 remvDupleSort(s)의 첫 문자는 a 이므로 a의 등장 인덱스는 2 입니다.
            int i = s.indexOf(c);

            //a의 등장 인덱스를 찾았으므로, 최초 입력 문자열에서 a를 포함한 이후 문자들을 분리해서 별도의 문자열로 만듭니다.
            //이렇게 해줌으로써, 최초로 등장해야할 문자인 a의 이전에 있던 문자들인 cb는 정답에서 아예 제외됩니다.
            //String sub는 acdcbc입니다.
            String sub = s.substring(i);

            //이제 두 가지 리스트의 결과를 비교하여 if문에 진입할지의 여부를 판단합니다. remvDupleSort(s)와 앞에서 만든 서브 문자열로 만든 remvDupleSort(sub)가 비교 대상입니다.
            //두 개의 리스트의 길이가 서로 같다는 것의 의미를 이해하는 것이 중요합니다.
            //이 둘은 어차피 각자에게 입력된 문자열에서 중복 문자들을 제거하고 알파벳 순으로 정렬한 것을 리스트의 형태로 반환한 것입니다. 따라서 서로 길이가 동일하다는 것은, [ 최초 입력 문자열에서 최초로 등장하는 사전 순으로 가장 빠른 문자(첫 루프에서는 a)를 이용해 분리한 sub문자열(즉, acdcbc) ]이 최초 입력 문자열인 s가 갖고 있는 모든 서로 다른 알파벳들(첫 루프의 경우 a,b,c,d)을 동일하게 포함하고 있다는 뜻입니다.
            //이것은 sub 문자열에서, [ 두 리스트에서 공통으로 첫 문자로 나타난 a ]를 삭제하고, a를 정답에 추가해야 함을 의미합니다.
            //따라서 이번 for 루프에서 판단의 기준이 되는 문자 c == 'a'는 정답의 첫 번째 문자로 추가 돼야 합니다. 문제의 조건에 따르면 중복되는 문자는 삭제하되, 중복되지 않거나 사전 순으로 가장 빨리 등장하는 단어는 포함해야 하기 때문입니다.
            if(remvDupleSort(s).size() == remvDupleSort(sub).size()){

                //이 부분은 앞에서 정의한 sub = "acdcbc"에서 a가 이미 정답에 포함되므로 a와 일치하지 않는 문자들만으로 다시 String을 구성하여 다음 재귀 호출에 이용하기 위한 것입니다.
                //a는 이미 정답을 구성하는 문자가 되었으므로 앞으로 등장하는 a들은 전부 삭제해야 합니다.
                //아래의 과정을 통해서 sb는 cdcbc로 구성되고, 이것이 다음 재귀호출의 입력 파라미터로서 전달됩니다.
                StringBuilder sb = new StringBuilder();
                for(Character p : sub.toCharArray()) {
                    if (p != c) sb.append(p);
                }

                //if 문에 진입하지 않은 경우에 대해서도 생각해 봐야 합니다. 이 경우, remvDupleSort(s)와 remvDupleSort(sub)의 길이가 서로 다른 것이고, 이는 곧 sub문자열이 포함하고 있는 알파벳의 종류가 s문자열이 포함하고 있는 알파벳의 종류보다 적다는 뜻입니다.
                //예를 들어서 최초 입력 문자열이 cbacdcbc인 경우에서 두 번째 for 루프에서 c의 값은 'b'입니다.
                //이때 s는 cdcbc이지만, sub는 bc입니다. 이러한 상황에서 if문에 진입해버리면 cdcbc에서 b 앞에 있는 문자들인 cdc는 판단의 기회조차 갖지 못하고 정답에서 배제되버리고 맙니다. 그렇기 때문에 두 번째 for 루프에서는 아무 행동도 하지 않고 그저 다음 for 루프로 이동해야 합니다.


                //'a'를 일단 정답에 추가하고 나머지 정답은 앞에서 만든 sb를 스트링으로 변환한 후 재귀호출의 인자로 전달함으로써 구합니다. 이 과정은 sb가 결국 빈 문자열이 될 때까지 재귀를 통해 반복되다가 return 된 값들이 마지막에 가서 합쳐짐으로써 최종 정답인 acdb를 반환하게 됩니다.
                //재귀를 반복할수록 스트링 sub의 길이가 줄어듬을 알 수 있습니다.
                return c + removeDuplicateLetters(sb.toString());
            }
        }//for


    return "";
    }//main
    public static ArrayList<Character> remvDupleSort(String s){
               // System.out.println("remvDuple called");
        ArrayList<Character> letters = new ArrayList<>();
        char[] input = s.toCharArray();
        Arrays.sort(input);
        char before = '@';
        for(char c : input){
            if(c != before){
                letters.add(c);
                before = c;
            }
        }//for
        return letters;
    }

}//main class

//아래의 재귀호출 실행 내역은 입력값을 s = cbacdcbc로 했을 때의 결과입니다.
/*
for enter
c : a
s : cbacdcbc
sub : acdcbc
remvD s : [a, b, c, d]
remvD sub : [a, b, c, d]
if enter
added to answer : a


 for enter
c : b
s : cdcbc
sub : bc
remvD s : [b, c, d]
remvD sub : [b, c]


 for enter
c : c
s : cdcbc
sub : cdcbc
remvD s : [b, c, d]
remvD sub : [b, c, d]
if enter
added to answer : c


 for enter
c : b
s : db
sub : b
remvD s : [b, d]
remvD sub : [b]


 for enter
c : d
s : db
sub : db
remvD s : [b, d]
remvD sub : [b, d]
if enter
added to answer : d


 for enter
c : b
s : b
sub : b
remvD s : [b]
remvD sub : [b]
if enter
added to answer : b
acdb
*/



//두 번째 풀이는 스택을 이용합니다. 실행시간이 3~5밀리초로 앞의 풀이보다 10배 가까이 빠른 속도를 보여주고, 상위 약 11%의 성능이었습니다.
class Solution {
    public String removeDuplicateLetters(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        Set<Character> seen = new HashSet<>();
        Map<Character, Integer> lastIndex = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            lastIndex.put(s.charAt(i), i);
            //해시 맵은 같은 키가 들어올 경우, 값이 갱신 된다는 사실을 기억 해야 합니다.
        }
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!seen.contains(c)) {//seen 집합이 현재 문자를 포함하고 있지 않을 경우 if문에 진입합니다. 즉, 이미 처리한 문자가 아닐 경우에 stack에 추가될 수도 있는 판단의 기회를 제공합니다. 그렇지 않을 경우, 즉, 이미 스택에 정답으로 저장해 놓은 문자들 집합과 중복될 경우 그냥 오답으로 처리하여 버리는 것입니다.

                while(true) {//while루프의 내부에 있는 break 조건들에 전부 해당되지 않는다면, 결국 스택 첫 요소를 삭제하게 됩니다. while 루프에서 break 한다는 것은, 결국 정답에 부합되기 때문에 스택에 포함시켜야 담아야 합니다.

                    //스택이 비었다면 와일루프 탈출
                    if(stack.isEmpty()){break;}

                    //peek()는 첫 요소를 삭제하지 않고 반환하는 메서드입니다. 즉, 현재 문자가 스택의 첫 번째 문자보다 나중 문자거나, 같은 문자일 경우 와일 루프를 탈출합니다.. 여기의 진짜 의도는, 스택의 첫 요소보다 사전 순으로 같거나 나중 문자일 경우 일단 스택에 집어 넣는다는 뜻입니다.
                    if(c >= stack.peek()) break;


                    //스택의 첫 문자가 해시맵 상에서 갖고 있는 값이 현재의 인덱스보다 작거나 같다면 와일 루프를 탈출합니다. 여기의 진짜 의도는 해시맵 값의 뜻을 알아야 이해할 수 있습니다. 스택의 첫 요소의 해시맵 인덱스 값이 현재 for 루프의 인덱스보다 크다는 것은, 현재 스택의 첫 요소에 있는 문자가 앞으로 또 등장하기 때문입니다. 그렇기 때문에 현재 스택의 첫 위치를 차지하고 있지만 앞으로 또 나오는 것이 분명한 녀석을 스택에서 없애줘야 하는 것입니다.
                    if(lastIndex.get(stack.peek())<=i)break;


                    //위의 세 조건에 모두 해당되지 않는다면 스택에서 첫 요소 없애고, 그 요소를 seen 집합에서도 없앱니다.
                    seen.remove(stack.pop());
                }//while

                //while루프의 세 가지 break 조건들 중 하나라도 해당 돼서 while 루프를 탈출했다면 일단 스택에 채워 넣습니다.
                stack.push(c);
                seen.add(c);
            }//if
        }//for
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            //스택에서 내용물을 꺼낼 때는 첫 요소만을 꺼낼 수 있기 때문에, 스택에서 꺼낸 요소를 역순으로 배열해야 한다는 점을 잊어서는 안 됩니다.
            sb.insert(0, stack.pop());
        }
        return sb.toString();
    }
}
