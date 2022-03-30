//큐 자료구조를 이용해서 스택을 구현하라는 문제였습니다.
//ArrayList를 이용해서 문제를 풀었습니다.
//스택의 푸시 메서드는 ArrayList의 add()메서드로 구현하고, 스택의 pop 동작은 ArrayList의 get()과 remove()메서드로 구현했습니다.
//실행시간은 0밀리초로 나와서 큰 의미는 없었습니다.
class MyStack {
    
    private int size;
    private ArrayList<Integer> stack = new ArrayList<>();

    public MyStack() {
        size = 0;
    }
    
    public void push(int x) {
        stack.add(x);
        size++;
    }
    
    public int pop() {
       // if(size==0){
       //     return null;
        //}
       // else{
            int answer = stack.get(stack.size()-1);
            stack.remove(stack.size()-1);
            size--;
            return answer;
       // }
    }
    
    public int top() {
        //if(size==0) return null;
       // else{
            return stack.get(stack.size()-1);
       // }
    }
    
    public boolean empty() {
        if(size==0) return true;
        else return false;
    }
}

/**
 * Your MyStack object will be instantiated and called as such:
 * MyStack obj = new MyStack();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.top();
 * boolean param_4 = obj.empty();
 */
