//두 개의 스택을 활용하여 큐를 구현하는 문제입니다.
//하나의 스택에 있는 내용물들을 pop해서 다른 스택에 push하면 뽑아낸 쪽의 스택에 있던 내용물들의 정렬 순서가 푸시 받은 스택 안에서 반대로 정렬된다는 사실을 이용하는 것이 중요합니다.
//실행시간은 0밀리초로 측정돼서 큰 의미는 없었습니다.
class MyQueue {

    private int size;
    private Stack<Integer> s1 = new Stack<>();
    private Stack<Integer> s2 = new Stack<>();
    
    public MyQueue() {
        size = 0;
    }
    
    public void push(int x) {
        s1.push(x);
        size++;
    }
    
    public int pop() {
        while(!s1.isEmpty()){
            s2.push(s1.pop());
        }

        int answer = s2.pop();

        //뽑아내서 반환해야 하는 내용물을 임시 저장한 후, s2에서 s1으로 다시 옮겨야 합니다.
        //그래야 다음 큐 삽입 동작에 대해서 오류 없이 작동합니다.
        while(!s2.isEmpty()){
            s1.push(s2.pop());
        }
        size--;
        return answer;
    }
    
    
    public int peek() {
        while(!s1.isEmpty()){
            s2.push(s1.pop());
        }
        int answer = s2.peek();

        //여기도 마찬가지 입니다. 뽑아내서 반환해야 하는 내용물을 임시 저장한 후, s2에서 s1으로 다시 옮겨야 합니다.
        //그래야 다음 큐 삽입 동작에 대해서 오류 없이 작동합니다.
        while(!s2.isEmpty()){
            s1.push(s2.pop());
        }
        return answer;
    }
    
    public boolean empty() {
        return size==0;
    }
}

/**
 * Your MyQueue object will be instantiated and called as such:
 * MyQueue obj = new MyQueue();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.peek();
 * boolean param_4 = obj.empty();
 */
