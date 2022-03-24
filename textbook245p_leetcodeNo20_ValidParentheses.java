//첫 번째 풀이는 매칭 테이블을 만들지 않는 풀이입니다.
//실행시간은 3밀리초였고, 상위 47% 성능이었습니다.
//매칭 테이블을 만들지 않고 풀 경우, 예외처리가 복잡해지는 단점이 있습니다.
class Solution {
    public boolean isValid(String s) {
        //must return boolean type

        ArrayList<Character> stack = new ArrayList<>();
        ArrayList<Character> noMatch = new ArrayList<>();

        char[] input = s.toCharArray();

        for(int i=0; i<input.length; i++) {
            if (input[i] == '(' || input[i] == '{' || input[i] == '[') {
                stack.add(0, input[i]);
            }
            if(input[i]==')'){
                if(stack.size()==0){return false;}
                if(stack.get(0)=='(') stack.remove(0);
                else noMatch.add(input[i]);
            }
            else if(input[i]=='}'){
                if(stack.size()==0){return false;}
                if(stack.get(0)=='{') stack.remove(0);
                else noMatch.add(input[i]);
            }
            else if(input[i]==']'){
                if(stack.size()==0){return false;}
                if(stack.get(0)=='[') stack.remove(0);
                else noMatch.add(input[i]);
            }
        }


        if(stack.size()!=0) return false;
        if(noMatch.size() != 0) return false;

        return true;
    }//main
}//main class





//두 번째 풀이는 매칭테이블을 이용한 풀이입니다.
//실행시간과 상대적 성능은 첫 번째 풀이와 동일합니다.
//매칭 테이블이 없는 풀이보다 코드가 더 간결하고 논리가 명확합니다.
class Solution {
    public boolean isValid(String s) {
        //must return boolean type

        char[] input = s.toCharArray();

        LinkedList<Character> stack = new LinkedList<>();

        String open = "({[";
        String close = ")}]";

        for(int i=0; i<input.length; i++){
            if(open.indexOf(input[i])!=-1){stack.push(input[i]);}
            else if(close.indexOf(input[i])!=-1){
                if(stack.isEmpty()) return false;
                if(close.indexOf(input[i])!=open.indexOf(stack.pop())) return false;
            }//else if
        }//for
        
        if(stack.size()!=0) return false;
        return true;
    }//main
}//main class
