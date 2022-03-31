//실행시간은 6밀리초였고, 이는 약 상위 46% 정도입니다.
//원형 큐를 구현하는 문제에서 중요한 점은 두 개의 포인터를 정확히 다루는 작업입니다.
//first와 last 중에서 특히 last 포인터가 중요합니다.
class MyCircularQueue {
    private int size;
    private Integer[] sp;
    private int first;
    private int last;

    public MyCircularQueue(int k) {
        size=0;
        first = 0;
        last = 0;
        sp = new Integer[k];
        for(int i=0; i<sp.length; i++){
            sp[i] = null;
        }
    }

    public boolean enQueue(int value) {
        if(isFull()){
            return false;
        }
        else{
            //이 부분이 이 문제의 핵심입니다.
            //만약 여기서 라스트(last) 인덱스에 값을 집어 넣은 후, 라스트 인덱스를 이동시키는 것이 아니라, 라스트 인덱스를 먼저 이동시킨 후 값을 집어넣는 방식을 택하면 상당히 골치아픈 일이 발생합니다.

            //일단 처음 큐가 만들어졌을 때는 퍼스트(first)와 라스트 인덱스는 둘 다 0을 가리키고 있습니다.
            //이 상태에서 최초의 enqueue()가 발생합니다.
            //이 때, 라스트 인덱스를 먼저 이동시킨 후 값을 이동된 라스트 인덱스에 집어넣는 방식으로 진행할 경우, 최초의 enqueue 때는 라스트 인덱스를 이동시킬 수 없습니다. 왜냐하면 요소가 1개 뿐이어서 라스트 인덱스와 퍼스트 인덱스 둘 다 그 첫 요소를 가리켜야 하기 때문입니다.
            //문제는 두 번째와 그 이후의 enqueue() 발생합니다. 두 번째 enqueue()에서는 퍼스트 인덱스와 라스트 인덱스가 서로 동일한 인덱스를 가리키고 있기 때문에 이 경우를 별도의 경우의 수인 if(first == last){...} 이런 식으로 처리해야 하고, 세 번째 enqueue() 부터는 라스트와 퍼스트 인덱스가 서로 다른 곳을 가키고 있으므로 이 또한 else{...}로 처리해야 합니다.

            //그런데 위와 같이 if(first==last){...}else{...}로 enqueue를 처리할 경우 한 가지 빠져나올 수 없는 자기 모순과 맞딱뜨리게 됩니다.
            //첫 번째 enqueue()가 발생했고, 퍼스트 인덱스와 라스트 인덱스는 당연히 enqueue()로 들어온 요소를 가리키고 있습니다. 이제 두 번째 enqueue()가 들어오려 하는데, 두 번째 enqueue()는 else{...}파트에 진입하지를 못합니다. 왜냐하면 첫 번째 enqueue() 후에 퍼스트 인덱스와 라스트 인덱스가 동일한 값을 갖고 있기 때문입니다. 이러한 문제는 세 번째와 그 이후의 enqueue()에도 똑같이 계속 발생합니다. 결국 CircularQueue 클래스의 enqueue() 동작은 첫 번째 말고는 전부 실패하고 맙니다.

            //따라서 last 인덱스를 다룰 때는 "last 인덱스란 진짜로 마지막 요소를 뜻하는 것이 아니라, 다음에 enqueue()로 집어넣을 요소가 위치하게 될 인덱스를 미리 가리키고 있는 것"이라는 의미를 일관되게 적용해야 합니다.
            sp[last]=value;
            last = (last+1)%sp.length;
            size++;
            return true;
        }
    }

    public boolean deQueue() {
        if(isEmpty()) return false;

        sp[first] = null;
        first = (first+1)%sp.length;
        size--;
        return true;

    }

    public int Front() {
        if(isEmpty()) return -1;
        else{
            return sp[first];
        }
    }

    public int Rear() {
        if(isEmpty()) return -1;
        else{
            //sp[]의 길이가 5라고 가정하고 설명하겠습니다.
            //이 부분은 약간 계산이 복잡할 수 있습니다. 앞에서 설명한 대로 last 인덱스는 현재의 마지막 요소의 인덱스를 뜻하는 것이 아니라, 다음 enqueue() 때 삽입될 요소가 위치해야 하는 인덱스를 미리 가리키고 있기 때문에 만약에 last 인덱스가 4라면 Rear() 때는 sp[3]을 반환해야 하고, last가 0이라면 Rear() 때는 sp[4]를 반환해야 하기 때문입니다.
            //이는 CircularQueue 라는 특성 때문에 발생하는 일입니다.
            return sp[(last+sp.length-1)%sp.length];
        }
    }

    public boolean isEmpty() {
        return size==0;
    }

    public boolean isFull() {
        return size==sp.length;
    }
}

/**
 * Your MyCircularQueue object will be instantiated and called as such:
 * MyCircularQueue obj = new MyCircularQueue(k);
 * boolean param_1 = obj.enQueue(value);
 * boolean param_2 = obj.deQueue();
 * int param_3 = obj.Front();
 * int param_4 = obj.Rear();
 * boolean param_5 = obj.isEmpty();
 * boolean param_6 = obj.isFull();
 */
