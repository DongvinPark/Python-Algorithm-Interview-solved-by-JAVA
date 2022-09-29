//실행시간은 12밀리초였고, 이는 상위 약 2%에 해당하는 성능이었습니다.
//Saperate Chaining 방식으로 구현한 해시맵입니다.
class MyHashMap {

    //chain 방식으로 해시맵을 구현하기 위해서는 키, 값 쌍을 저장할 노드 타입의 자료구조가 있어야 합니다.
    //이는 문제에서 제공해주지 않기 때문에 문제를 푸는 사람이 직접 구현해야 합니다.
    private class Node{
        public int key;
        public int value;
        public Node next;

        public Node(int k, int v){
            key = k;
            value = v;
            next = null;
        }
    }

    //키, 값 쌍을 담고 있는 노드를 저장할 자료구조로 ArrayList를 사용하기로 했습니다.
    ArrayList<Node> bucket;

    //해시코드를 계산할 때 사용할 상수값을 정해줍니다.
    private final int size = 1000;


    public MyHashMap() {
        bucket = new ArrayList<>();
        for(int i=0; i<size; i++){
            bucket.add(null);
        }
    }

    public void put(int key, int value) {
        //입력 받은 키를 1000으로 나눴을 때의 나머지를 구합니다.
        //이렇게 구한 값에 해당하는 bucket의 인덱스에 노드를 저장하려고 합니다.
        int loc = key % size;
        
        Node first = bucket.get(loc);
        
        //만약 해당 bucket의 인덱스에 어떤 요소도 저장돼 있지 않았다면 그냥 거기에 저장하면 됩니다.
        if(first == null) {
            bucket.set(loc, new Node(key, value));
            return;
        }
        
        //저장하고자 하는 인덱스이 이미 다른 요소가 있는 경우입니다.
        //예를 들면, bucket에 put(1,1)을 하고, 다시 put(1001,2)이런 식의 상황이 된 것입니다.
        //해시맵은 이미 존재하는 키값과 동일한 값으로 다시 put()메서드를 호출할 경우, 원래 있던 키, 값 쌍에서
        //'값' 부분을 새롭게 들어온 키,값 쌍의 값으로 변경(혹은 덮어쓰기)한다는 점입니다.
        //while 루프 내의 첫 번재 if 파트는 이를 처리하기 위한 부분입니다.
        //while 루프 내의 두 번째 if 파트는 현재 firt노드가 현재 bucket 인덱스에 존재하는 연속된 노드들 중
        //마지막 노드에 해당될 때 while 루프를 빠져나오게 하기 위한 것입니다.
        while(first!=null){
            if(first.key == key){ first.value = value; return; }
            if(first.next == null) break;
            first = first.next;
        }
        Node last = new Node(key, value);
        first.next = last;
        
    }

    public int get(int key) {
        int loc = key % size;
        int answer;
        
        Node first = bucket.get(loc);
        
        if(first == null) return -1;

        //get() 메서드의 while 루프를 작성할 때 주의해야 할 점이 있습니다.
        //만약 여기서 첫 번째 loc에서 바로 값을 찾은 경우를 if(first.key == key){...} else{}이런 식으로
        //while 루프에서 따로 빼내서 처리할 경우 while 루프 내의 로직을 작성하기가 매우 까다로워집니다.
        //else{...} 내부에 while루프를 집어넣고, 그 안에서 다시 chain 들을 탐색해야 하기 때문입니다.
        //buckey.get(loc) 노드에서 바로 값을 찾을 수 있는 경우도 if로 while 루프 밖에 따로 빼내서 처리하는 것이 아니라,
        //while 루프 내부에서 한 번에 처리하는 것이 코드도 간결하고 로직도 깔끔해집니다.
        while(first != null){
            if(first.key == key) return first.value;
            first = first.next;
        }
        
        return -1;
        
    }

    public void remove(int key) {
        int loc = key % size;
        Node first = bucket.get(loc);
        
        //지우고자 하는 키,값 쌍의 키가 존재하지 않는 경우 remove 메서드를 바로 종료시킵니다.
        if(first == null) return;
        
        //bucket의 loc 인덱스에서 입력된 키에 해당하는 노드를 바로 찾았을 경우를 처리해 줍니다.
        if(first.key == key){
            bucket.set(loc, first.next);
            return;
        }
        
        //bucket의 loc 인덱스에 다수의 노드가 이미 chaining 돼 있을 경우에 원하는 키,값 쌍을
        //삭제하기 위해서는 chanin을 탐색해야 하고, 이때 prev노드가 필요합니다.
        //여기에서도 remove 메서드에서 밝힌 것과 같이, 첫 시도만에 삭제해야 하는 키,값 쌍의 키를
        //찾아낸 경우도 while 루프 내에서 한번에 처리해야 합니다.
        Node prev = first;
        while(first != null){
            if(first.key == key){
                prev.next = first.next;
                return;
            }
            prev = first;
            first = first.next;
        }//wh
        
    }//remove
}//end of class

/**
 * Your MyHashMap object will be instantiated and called as such:
 * MyHashMap obj = new MyHashMap();
 * obj.put(key,value);
 * int param_2 = obj.get(key);
 * obj.remove(key);
 */
