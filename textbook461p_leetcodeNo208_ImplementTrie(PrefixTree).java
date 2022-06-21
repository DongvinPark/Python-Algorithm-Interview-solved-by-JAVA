//실행시간은 62밀리초였고, 이는 상위 46%의 성능이었습니다.
//트라이(다른 말로는 prefix tree)라는 새로운 자료구조에 관련된 문제여서 풀이과정에서 생소한 점이 많았습니다.
class Trie {

    //일단 트라이에서 노드 역할을 해줄 클래스를 새로 하나 만들었습니다.
    //이때, 자식 노드를 2개 이하로만 저장하는 이진트리와는 달리, 사살상 트라이 내의 모든 노드가 모든 영소문자 알파벳의 개수만큼의 자식을 가질 수 있기 때문에 TrieNode[]은 26개의 요소를 저장할 수 있게 하였습니다.
    class TrieNode{
        TrieNode[] childList;
        boolean isEnd;
        public TrieNode() {

            //현재의 노드가 입력된 단어의 마지막 문자라면, 그 노드는 true 상태를 저장합니다.
            //예를 들어서 트라이에 'apple'이라는 단어가 입력되었을 때, e라는 단어를 저장하고 있는 노드의 isEnd는 true로 초기화됩니다.
            //이것은 다음에 이어질 메서드 중에서 search() 메서드를 구현할 때 중요한 역할을 합니다.
            this.isEnd = false;

            this.childList = new TrieNode[26];
        }
    }//end of node class


    //루트 노드를 따로 선언하고 기본생성자로 초기화해주는 것이 중요합니다.
    //최초로 단어를 삽입할 때, 그 단어의 첫 문자를 루트노드의 childList에 저장해야하기 때문입니다.
    TrieNode root;

    public Trie() {
        root = new TrieNode();
    }


    /*
    * 각 트라이 노드에는 26개의 문자를 저장할 수 있는 배열이 childList로서 제공되어 있다는 사실을 기억해야 합니다.
    * 이것은 루트노드에서도 마찬가지이므로, 입력된 단어의 첫 글자는 루트노드의 childList에 저장됩니다.
    * */
    public void insert(String word) {

        //단어가 입력될 때마다 루트노드에서 탐색을 해야 하므로 탐색을 시작할 노드의 위치를 루트노드를 이용해서 초기화해줍니다.
        TrieNode node = root;

        //입력된 단어를 char[]로 바꿔준 후, 해당 배열을 순회하면서 탐색을 진행합니다.
        for (char c : word.toCharArray()) {

            /*
            * 현재 단어를 childList의 어드 인덱스에 저장해야 하는지를 결정하기 위한 변수입니다.
            * 문자를 int 타입 변수에 대입시킨 후, System.out.print();로 출력해 보면 다음과 같습니다.
            * int a = 'a'; // 출력결과 : 97
              int b = 'b'; // 출력결과 : 98
              int c = 'c'; // 출력결과 : 99
              int d = 'd'; // 출력결과 : 100
              …
              int z = 'z'; // 출력결과 : 122
              *
              * 따라서, 현재 문자가 'a'라면, 인덱스 값인 pos는 0이 되면서 childList의 첫 번째 칸에 새로운 트라이 노드가 저장될 것이고,
              * 현재 문자가 'c'라면, 인ㄷ게스 값은 pos는 2가 되면서 childList의 세 번째 칸에 새로운 트라이 노드가 저장될 것입니다.
              * 물론, 실제로 저장이 되기 위해서는 childList[pos]의 값이 null, 즉, 저장된 것이 없어야 합니다.
              * 이 부분을 판별하는 것이 바로 아래에서 이어지는 if문 입니다.
              *
            * */
            int pos = c - 'a';

            //childList[pos]의 값이 null인 것은 현재의 트라이 노드가 현재의 문자를 포함하고 있지 않다는 뜻이므로 if문에 진입하여 childList 내에서 pos 인덱스의 위치에 새로운 트라이 노드를 저장해야 합니다.
            //왜 childList가 직접 문자 값을 저장하는 방식으로 풀이하지 않는지 궁금해 하실 것 같습니다.
            //그 이유는 childList의 인덱스 값만 알아도 바로 해당 인덱스가 나타내고자 하는 글자가 무엇인지 알 수 있기 때문입니다.
            //그리고 childList가 문자 리터럴을 직접 저장하는 방식으로 풀이할 경우, 저장하는 위치에 대한 계산과, 저장하는 문자에 대한 연산을 별도로 처리해야 하는 번거로움이 생깁니다.
            //if문에 진입하지 않는다는 것은 현재 문자를 현재의 트라이에서 이미 포함하고 있다는 뜻이므로 새로운 트라이를 pos 인덱스에 만들 필요가 없다는 뜻입니다.
            if (node.childList[pos] == null) {
                node.childList[pos] = new TrieNode();
            }

            //for each 문 내에 있으므로, 다음 루프를 위한 준비를 합니다. 만약 if문에 진입하지 않았었다면, 현재 노드의 자식노드를 pos 값을 이용해서 바로 접근하게 될 것이고, if문에 진입했었다면 if문에서 만들어두었던 트라이 노드를 pos 값을 이용해서 진입하는 것으로 해석할 수 있습니다.
            node = node.childList[pos];
        }//for

        //for each 루프를 끝낸 후, 현재 노드는 입력된 단어의 마지막 문자를 포함하고 있는 노드이므로, isEnd의 상태를 true로 초기화 해줍니다.
        node.isEnd = true;
    }//func


    //insert()메서드에서 설명한 로직과 유사한 로직을 이용해서 탐색을 해 나갑니다. 그러다가 일치하지 않는 문자가 발견될 경우, 바로 false를 리턴해줍니다. for each 루프를 무사히 마친 경우 true를 리턴해줍니다.
    public boolean search(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int pos = c - 'a';
            if (node.childList[pos] != null) {
                node = node.childList[pos];
            }
            else {
                return false;
            }
        }//for
        return node.isEnd;
    }//func

    // 이번 메서드도 search() 메서드에서 사용한 로직과 유사한 로직으로 탐색을 진행해 나가다가 일치하지 않는 경우를 발견할 경우 바로 false를 리턴해줍니다. 무사히 for each 루프를 통과한 경우 true를 리턴해줍니다.
    public boolean startsWith(String prefix) {
        TrieNode node = root;
        for ( char c : prefix.toCharArray() ) {
            int pos = c - 'a';
            if (node.childList[pos] != null) {
                node = node.childList[pos];
            }
            else {
                return false;
            }
        }
        return true;
    }//func

}//end of main class

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */
