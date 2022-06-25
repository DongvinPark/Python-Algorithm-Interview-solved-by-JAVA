//트라이의 특성을 정확하게 이해하고, 기발한 아이디어들을 여럿 동원해서 풀이해야 하는 고난이도 문제였습니다.
//실행시간은 956밀리초였고, 이는 상위 26%의 성능이었습니다.
class Solution {
    //return type is List< List< Integer > >.

    private static class TrieNode {
        TrieNode[] charList;
        int IndexOfWord;
        List<Integer> list;

        TrieNode() {
            charList = new TrieNode[26];
            IndexOfWord = -1;
            list = new ArrayList<>();
        }
    }//end of TrieNode class


    //main LOGIC
    public List<List<Integer>> palindromePairs(String[] words) {
        List<List<Integer>> answer = new ArrayList<>();

        TrieNode root = new TrieNode();

        //String[] words는 문자열들이 들어있는 오리지널 입력값입니다. 이 입력 배열에 존재하는 문자열들을 순회하면서 트라이를 만들어줍니다.
        for (int i = 0; i < words.length; i++) {
            addWord(root, words[i], i);
        }

        //위에서 만들어낸 트라이를 전달하고, 그 트라이에 String[] words 내의 문자열들을 순서대로 집어넣어 가면서 팰린드롬 여부를 판단하여 정답 리스트에 추가해줍니다.
        for (int i = 0; i < words.length; i++) {
            search(words, i, root, answer);
        }

        return answer;
    }//main


    //word adding logic
    //1개의 단어를 입력 받고, 그 단어를 구성하는 문자를 끝에서부터 처음 문자 방향으로(즉, 역순으로) 탐색하면서 트라이 노드를 만들어나갑니다.
    //그렇게 트라이 노드가 새로 만들어진 후, 입력받은 단어의 첫 문자부터 현재 문자가 팰린드롬 상태라면, 리스트에 현재 단어가 오리지널 스트링 리스트 상에서 위치하는 인덱스를 새로만든 트라이노드의 리스트에 저장합니다.
    //이때 기억해야 할 점은, word의 탐색을 인덱스 0~마지막 방향의 정방향이 아니라, 마지막 인덱스~0 방향으로 가는 역순으로 진행하고 있다는 점입니다.
    //예를 들어서 appac 라는 단어를 집어넣는다고 생각해보자. 그러면 트라이는 다음과 같이 구성될 것입니다.
    /*
    *                                  루트 노드
    *                                 * c
    *                                /
    *                               * a (list에 addWord()메서드의 인자로 전달 받은 word가 오리지널 입력 스트링 배열 상에서 등장하는 인덱스를 저장합니다.
    *                              /    예들들어서, appac라는 word가 String[] words 배열의 4인덱스에서 등장한다면, 이 노드의 list에 4라는 값을 저장합니다.)
    *                             * p
    *                            /
    *                           * p
    *                          /
    *                         * a(IndexOfWord에 addWord()메서드의 인자로 전달 받은 word가 오리지널 입력 스트링 배열 상에서 등장하는 인덱스를 저장합니다.
    *                           예들들어서, appac라는 word가 String[] words 배열의 4인덱스에서 등장한다면, 이 노드의 indexOfWord에 4라는 값을 저장합니다.)
    * */
    private void addWord(TrieNode currentNode, String word, int index) {
        for (int i = word.length() - 1; i >= 0; i--) {
            //현재 입력받은 단어가 char[26] 배열 상에서 어떤 인덱스에 위치해야 하는 지를 결정합니다.
            //이것에 대한 자세한 설명은 제가 작성한 깃허브 링크인 https://github.com/DongvinPark/Python-Algorithm-Interview-solved-by-JAVA/blob/main/textbook461p_leetcodeNo208_ImplementTrie(PrefixTree).java 에서 확인해 보실 수 있습니다.
            int j = word.charAt(i) - 'a';

            // 만약 현재 노드의 charList[j]에 어떤 값도 가지고 있지 않을 경우, 해당 인덱스에 새로운 트라이노드를 만들어줍니다. charList는 트라이 노드 타입을 저장하는 26칸짜리 배열임을 기억해야 합니다.
            if (currentNode.charList[j] == null) {
                currentNode.charList[j] = new TrieNode();
            }

            // 위에서 appac라는 word 입력값으로 만들어낸 트리를 이용해서 새로운 입력값인 c 라는 단어가 과연 appac 라는 단어와 합쳐졌을 때 팰린드롬이 되는지 판단해야 하는 상황에 처했다고 가정해봅시다.
            //그렇다면 이것을 판단할 수 있는 근거를 트라이 노드에서 발견할 수 있어야 할 것입니다.
            //트라이를 만들때, appac 순서로 만들지 않고, cappa라는 역순으로 만든 이유가 여기에 있습니다. c 라는 한 글자로 이루어지 단어를 입력값으로 받아서, 이미 만들어둔 트라이 트리를 활용하여 c와 appac를 더한 문자열이 팰린드롬이 맞는지 판단할 때, c라는 word는 분명 위에서 만든 트리의 * c 노드까지 밖에 가지 못할 것입니다.
            //그러나, 트라이에서 남아 있는 appa 부분이 팰린드롬이므로, c와 appac를 더한 문자열은 당연히 팰린드롬이 될 수밖에 없습니다.
            //트라이를 만들어내는 메서드 내에서는 위에서 언급했듯이 단어의 철자를 역순으로 만들어가고 있기 때문에, 남은 단어가 팰린드롬인지 확인하려면, word의 철자를 0인덱스에서 i인덱스 방향으로 탐색하며 팰린드롬 여부를 판단해야 합니다.
            //그렇게 판단한 결과 팰린드롬이 맞다면(즉, c>a>p>p>a 방향으로 탐색하다가 맨 왼쪽 a문자를 만났을 경우), list에 현재 단어인 'appac'가 String[] words 배열에서 등장하는 인덱스를 저장합니다.
            //정리하면, 입력으로 들어온 값의 길이가 트라이에 존재하는 단어보다 길이가 짧음에도 둘이 합쳤을 때 팰린드롬인지 여부를 판단하기 위한 장치인 것입니다.
            if (isPalindrome(word, 0, i)) {
                currentNode.list.add(index);
            }

            //다음 for 루프를 위한 준비 작업입니다.
            currentNode = currentNode.charList[j];
        }//for

        //입력된 word 라는 문자열에 대해서 노드를 만드는 것을 모두 마쳤습니다. 현재 currentNode는 마지막으로 c>a>p>p>a에서 마지막에 등장한 a라는 단어를 나타내는 노드를 가리키고 있습니다.
        //마지막으로 남은 단어는 당연히 팰린드롬일 수밖에 없기 때문에, list에  'appac'가 String[] words 배열 내에서 등장하는 인덱스 값을 더해줍니다.
        currentNode.list.add(index);

        //또한 마지막 노드는 IndexOfWord에 'appac'가 String[] words 배열 내에서 등장하는 인덱스값을 더해줘야 합니다.
        //이것은 다음의 상황을 판단하기 위한 것입니다.
        //위에서 만든 트라이는 입력값이 appac 인 경우에 만들어낸 것입니다. 그런데, 이렇게 만들어진 트라이에 대해서 팰린드롬 여부를 판별해야 하는 단어가 'cappa'로 들어온 경우를 생각해볼 수 있습니다. 즉, cappa를  [appac 라는 입력값으로 만든 트라이]에 대해서 둘이 합쳤을 때 트라이인지 따져보면 둘은 당연히 팰린드롬일 수밖에 없습니다.
        //정리하면, 입력으로 들어온 값의 길이가 트라이에 존재하는 단어와 갈이가 같고 둘이 합쳤을 때 팰린드롬이기도 한 상황을 판별하기 위한 장치입니다.
        currentNode.IndexOfWord = index;
    }//func



    //searching logic
    //String[] words에 존재하는 각각의 단어들을 차례대로 순회하면서 앞서 만들어둔 트라이와 비교하여 합쳤을 때 팰린드롬이 맞는지 판단하기 위한 메서드입니다.
    //앞서 만들어둔 트라이를 root노드를 통해서 접근할 수 있도록 인자에 TrieNode currentNode를 전달해 주고, 정답을 추가하기 위한 2차원 리스트 res를 추가해줍니다.
    private void search(String[] words, int i, TrieNode currentNode, List<List<Integer>> answerList) {
        for (int j = 0; j < words[i].length(); j++) {

            //currentNode.IndexOfWord >= 0 && currentNode.IndexOfWord != i 라는 바로 아래의 if 조건문이 뜻하는 것은 위에서 appac라는 입력값으로 만든 트라이를 예로 들어서 이해할 수 있습니다.
            // currentNode.IndexOfWor >= 0를 통해서 두 번째 * a 와 같은 케이스를 잡아내기 위한 것이며,
            // currentNode.IndexOfWord != i를 통해서 자기 자신과 짝을 지어서 팰린드롬으로 판정되는 것을 막고 있습니다.
            // 마지막으로 isPalindrome(words[i], j, words[i].length() - 1)) 파트를 통해서 'c'와 같이 트라이에 존재하는 노드의 경로보다 짧으면서도 c와 appac를 합쳤을 때 팰랜드롬이 되는 경우를 정답으로 판정해 추가할 수 있게 해줍니다.
            /*
             *                                  루트 노드
             *                                 * c
             *                                /
             *                               * a (list에 addWord()메서드의 인자로 전달 받은 word가 오리지널 입력 스트링 배열 상에서 등장하는 인덱스를 저장합니다.
             *                              /    예들들어서, appac라는 word가 String[] words 배열의 4인덱스에서 등장한다면, 이 노드의 list에 4라는 값을 저장합니다.)
             *                             * p
             *                            /
             *                           * p
             *                          /
             *                         * a(IndexOfWord에 addWord()메서드의 인자로 전달 받은 word가 오리지널 입력 스트링 배열 상에서 등장하는 인덱스를 저장합니다.
             *                           예들들어서, appac라는 word가 String[] words 배열의 4인덱스에서 등장한다면, 이 노드의 indexOfWord에 4라는 값을 저장합니다.)
             * */
            if (currentNode.IndexOfWord >= 0 && currentNode.IndexOfWord != i && isPalindrome(words[i], j, words[i].length() - 1)) {
                answerList.add(Arrays.asList(i, currentNode.IndexOfWord));
            }

            //다음 노드에 대한 판단은 다음 for루프에서 진행되므로 이를 위한 준비를 해줍니다.
            currentNode = currentNode.charList[words[i].charAt(j) - 'a'];

            // appac로 만든 트라이에 'appackn'과 같은 입력값이 들어왔다고 가정해봅시다. 그렇다면, n노드 다음에는 노드가 존재하지 않을 것이고, 당연히 팰린드롬도 아닐 것인데다가 더 이상 탐색도 진행할 수 없으므로 search()메서드도 종료되어야 합니다.
            if (currentNode == null) return;
        }//1st for

        // appac로 만들어낸 트라이에서 cappa라는 String[] words 내의 한 요소가 전달된 경우, 즉, 글자수가 같고 둘이 합쳤을 때 팰린드롭이 되는 경우를 정답처리하여 추가하기 위한 부분입니다.
        for (int j : currentNode.list) {
            if (i == j) continue;
            answerList.add(Arrays.asList(i, j));
        }//2nd for
    }//func



    // palindrome logic
    //입력 받은 단어의 i번째 인덱스에서 등장하는 문자부터 j번째 인덱스에서 등장하는 문자까지 팰린드롬이 맞는지의 여부를 판별하기 위한 메서드입니다.
    private boolean isPalindrome(String word, int i, int j) {
        while (i < j) {
            if (word.charAt(i++) != word.charAt(j--)) return false;
        }

        return true;
    }//func


}//main class
