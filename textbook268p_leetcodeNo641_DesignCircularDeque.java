//첫 번째 풀이는 자바 언어에서 기본적으로 제공하는 타입인 LinkedList<E>를 이용했습니다.
//해당 타입에서 이미 필요한 계산을 전부 제공해주기 때문에 구현이 아주 간단합니다.
//실행시간은 7밀리초였고, 이는 상위 약 56%에 해당합니다.
class MyCircularDeque {
    
    private int size;
    private LinkedList<Integer> sp = new LinkedList<>();

    public MyCircularDeque(int k) {
        this.size = k;
    }
    
    public boolean insertFront(int value) {
        if(isFull()) return false;
        sp.addFirst(value);
        //size++;
        return true;
    }
    
    public boolean insertLast(int value) {
        if(isFull()) return false;
        sp.addLast(value);
        //size++;
        return true;
    }
    
    public boolean deleteFront() {
        if(isEmpty()) return false;
        sp.removeFirst();
        //size--;
        return true;
    }
    
    public boolean deleteLast() {
        if(isEmpty()) return false;
        sp.removeLast();
        //size--;
        return true;
    }
    
    public int getFront() {
        if(isEmpty()) return -1;
        return sp.getFirst();
    }
    
    public int getRear() {
        if(isEmpty()) return -1;
        return sp.getLast();
    }
    
    public boolean isEmpty() {
        if(sp.size() == 0){return true;}
        else return false;
    }
    
    public boolean isFull() {
        if(sp.size() == this.size) return true;
        else return false;
    }
}//end of class

/**
 * Your MyCircularDeque object will be instantiated and called as such:
 * MyCircularDeque obj = new MyCircularDeque(k);
 * boolean param_1 = obj.insertFront(value);
 * boolean param_2 = obj.insertLast(value);
 * boolean param_3 = obj.deleteFront();
 * boolean param_4 = obj.deleteLast();
 * int param_5 = obj.getFront();
 * int param_6 = obj.getRear();
 * boolean param_7 = obj.isEmpty();
 * boolean param_8 = obj.isFull();
 */




//두 번째 풀이는 Integer 타입 배열을 이용했습니다. 실행시간은 7밀리초로 첫 번째 풀이와 동일했습니다.
//아무래도 basetype인 int 타입이 아니라 Wrapper class인 Integer 타입을 사용한 점, null 삽입 동작을 수행하는 점 때문으로 보입니다.
//double ended queue 이기 때문에 first와 last 인덱스를 정확하게 이동시키고, 각각의 인덱스가 의미하는 바를 일관되게 유지하는 것이 필요합니다.
class MyCircularDeque {
    private int size;
    private int first;
    private int last;
    private Integer[] sp;
    

    public MyCircularDeque(int k) {
        size = 0;
        first = 0;
        last = 0;
        sp = new Integer[k];
    }
    
    public boolean insertFront(int value) {
        if(isFull()) return false;

        //첫 번째 요소를 삽입할 위치를 미리 정해준 후, 그곳에 삽입합니다.
        first = (first+sp.length-1)%sp.length;
        sp[first] = value;
        size++;
        return true;
    }
    
    public boolean insertLast(int value) {
        if(isFull()) return false;
        //last 인덱스는 first 인덱스와 전혀 다른 방식으로 작동합니다. 현재의 라스트 인덱스는 현재의 마지막 요소의 인덱스를 가리키는 것이 아니라, 다음 라스트 요소 삽입 때 그 요소를 삽입할 위치를 미리 가리키고 있는 것입니다. 반면 퍼스트 인덱스는 현재의 퍼스트 요소를 가리키고 있습니다.
        //그래서 바로 위의 insertFirst()메서드에서는 퍼스트 인덱스를 먼저 이동시킨 후 그 인덱스에 새로운 퍼스트 요소를 삽입한 반면, 라스트 인덱스는 삽입을 먼저 하고 그 후에 다음 라스트 요소 삽입을 위해 라스트 인덱스를 갱신하는 방식으로 작동합니다.

        //이러한 방식으로 퍼스트와 라스트 인덱스가 서로 작동하는 방식을 구분짓고 그 용도를 일관되게 유지하지 않을 경우, 퍼스트 요소를 삽입 완료했을 때, 퍼스트 인덱스와 라스트 인덱스가 동시에 퍼스트 요소를 가리키게 됩니다.
        //이 경우 다음 삽입 때는 퍼스트 인덱스와 라스트 인덱스가 서로 동일한 인덱스를 가맄고 있는 경우와 그렇지 않은 경우로 if-else 경우의 수를 나눠야 하고 그 결과 코드의 길이와 복잡도가 증가하며 오류가 날 가능성도 더 커집니다.
        sp[last] = value;
        last = (last+1)%sp.length;
        size++;
        return true;
    }
    
    public boolean deleteFront() {
        if(isEmpty()) return false;
        sp[first]=null;
        first = (first+1)%sp.length;
        size--;
        return true;
    }
    
    public boolean deleteLast() {
        if(isEmpty()) return false;

        //라스트 인덱스는 현재의 라스트 요소가 아니라 다음 라스트 요소가 위채해야 하는 인덱스를 미리 가리키고 있기 때문에, 현재의 마지막 요소를 읽어들이거나 삭제할 때 아래와 같은 인덱스 처리가 필요합니다.
        //이는 getRear() 메서드에서도 동일하게 해당되는 사항입니다.
        sp[(last+sp.length-1)%sp.length]=null;

        last = (last+sp.length-1)%sp.length;
        size--;
        return true;
    }
    
    public int getFront() {
        if(isEmpty()) return -1;
        else return sp[first];
    }
    
    public int getRear() {
        if(isEmpty()) return -1;
        else return sp[(last+sp.length-1)%sp.length];
    }
    
    public boolean isEmpty() {
        if(size == 0) return true;
        else return false;
    }
    
    public boolean isFull() {
        if(size == sp.length) return true;
        else return false;
    }
}//end of class

/**
 * Your MyCircularDeque object will be instantiated and called as such:
 * MyCircularDeque obj = new MyCircularDeque(k);
 * boolean param_1 = obj.insertFront(value);
 * boolean param_2 = obj.insertLast(value);
 * boolean param_3 = obj.deleteFront();
 * boolean param_4 = obj.deleteLast();
 * int param_5 = obj.getFront();
 * int param_6 = obj.getRear();
 * boolean param_7 = obj.isEmpty();
 * boolean param_8 = obj.isFull();
 */




//세 번째 풀이는 요소의 저장공간을 basetype인 int타입 배열로 설정했고, null삽입동작을 제거했습니다.
//그 결과 실행시간이 5밀리초로 측정되면서 상위 16%의 성능을 발휘하는 것으로 확인되었습니다.
//퍼스트 인덱스와 라스트 인덱스에 대힌 설명은 위의 두 번재 풀이와 동일합니다.
class MyCircularDeque {
    private int size;
    private int first;
    private int last;
    private int[] sp;
    

    public MyCircularDeque(int k) {
        size = 0;
        first = 0;
        last = 0;
        sp = new int[k];
    }
    
    public boolean insertFront(int value) {
        if(isFull()) return false;
        first = (first+sp.length-1)%sp.length;
        sp[first] = value;
        size++;
        return true;
    }
    
    public boolean insertLast(int value) {
        if(isFull()) return false;
        sp[last] = value;
        last = (last+1)%sp.length;
        size++;
        return true;
    }
    
    public boolean deleteFront() {
        if(isEmpty()) return false;
        //sp[first]=null;
        first = (first+1)%sp.length;
        size--;
        return true;
    }
    
    public boolean deleteLast() {
        if(isEmpty()) return false;
        //sp[(last+sp.length-1)%sp.length]=null;
        last = (last+sp.length-1)%sp.length;
        size--;
        return true;
    }
    
    public int getFront() {
        if(isEmpty()) return -1;
        else return sp[first];
    }
    
    public int getRear() {
        if(isEmpty()) return -1;
        else return sp[(last+sp.length-1)%sp.length];
    }
    
    public boolean isEmpty() {
        if(size == 0) return true;
        else return false;
    }
    
    public boolean isFull() {
        if(size == sp.length) return true;
        else return false;
    }
}//end of class

/**
 * Your MyCircularDeque object will be instantiated and called as such:
 * MyCircularDeque obj = new MyCircularDeque(k);
 * boolean param_1 = obj.insertFront(value);
 * boolean param_2 = obj.insertLast(value);
 * boolean param_3 = obj.deleteFront();
 * boolean param_4 = obj.deleteLast();
 * int param_5 = obj.getFront();
 * int param_6 = obj.getRear();
 * boolean param_7 = obj.isEmpty();
 * boolean param_8 = obj.isFull();
 */
