/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
//중위 순회와 전위 순회의 특성을 정확하게 파악해야 풀 수 있는 고난이도 문제였습니다.
    //실행시간은 5밀리초였고, 이는 하위 약 34%의 성능이었지만, 3밀리초 측정되면서 상위 19%로 표시되기도 하는등 리트코드 홈페이지의 서버 상황에 따라서 편차가 있었습니다.
    /*
    * 풀이방법을 설명하기 전에 전위순회와 중위 순회의 차이를 정확하게 알아두는 것이 로직을 이해하는데 중요한 역할을 합니다.
    * 다음과 같은 트리가 있다고 가정하겠습니다. n은 null을 뜻합니다.
    *                      1
    *          2                        3
    *    4         5               6         7
    * n    n    n    n          n    n    n    n
    *
    *
    * public static void printTree(TreeNode node){
        if(node != null){
                System.out.print(node.val + ", ");
            printTree(node.left);
            printTree(node.right);
        }
    }
    * 전위 순회는 위의 코드와 같이, 현재의 노드에서 하는 일(즉, node.val을 프린트 하는 것)을 왼쪽 자식 노드와 오른쪽 자식 노드를 재귀호출을 통해 불러내는 작업보다 앞서서 하기 때문에 '전'위 순회입니다.
    * 반대로 중위 순회는 아래의 코드와 같이 현재의 노드에서 처리하 일을 하기 전에 먼저 왼쪽 자식 노드를 재귀호출로 불러내고, 그 다음 현재 노드에서 할일을 처리하고, 그 다음에 오른쪽 자식 노드를 재귀호출로 불러내는 작업을 합니다.
    * 현재 하고자 하는 일이 두 자식 노드를 이용한 재귀호출 사이에 샌드위치처럼 낑겨 있어서 '중'위 순회라고 합니다.
    * public static void printTree(TreeNode node){
        if(node != null){
            printTree(node.left);
                System.out.print(node.val + ", ");
            printTree(node.right);
        }
    }
    * 위의 트리를 순회하는 순서를 배열로 나타내면 다음과 같습니다.
    * 전위 순회(preorder traversal) : 1 2 4 5 3 6 7
    * 중위 순회(inorder traversal)  : 4 2 5 1 6 3 7
    *                    인덱스 값  : 0 1 2 3 4 5 6
    *
    * 전위순회는 현재 노드를 먼저 표시한 후, 왼쪽 자식노드와 오른쪽 자식노드를 차례로 호출하기 때문에 전위 순회 배열의 첫 인덱스에 위치하는 값은 우리가 만들어야 하는 트리의 루트노드 값이 되는 것을 알 수 있습니다.
    *
    * 중위순회의 값은 이번 문제를 해결할 때 매우 중요한 힌트를 주고 있습니다.
    * 중위순회 배열의 값들(인덱스 아닙니다!!) 중 '1'이라는 값은 중위순회 배열의 3인덱스에 위차하고 있습니다. 3이라는 인덱스가 뜻하는 것은, 원본 트리의 1노드보다 왼쪽에 위치하는 노드들(즉, 4,2,5노드)의 개수입니다. 같은 방식으로, 중위순회 배열의 3이라는 값은 인덱스 5에서 나타나는데, 이는 원본 트리에서 3보다 왼쪽에 위치하는 노드들(1,2,4,5,6노드)의 개수와 일치합니다.
    *
    *
    * 또한, 중위 순회 배열에서 특정한 값, 예를 들면 1이라는 값을 기준으로 원본트리를 봤을 때, 1노드의 왼쪽에는 4,2,5 노드가 존재하고, 1노드의 오른쪽에는 6,3,7노드가 존재하는 것을 알 수 있습니다.
    *
    * 따라서 문제에서 요구하는대로 트리를 만들어나갈 때 아마도 dfs 재귀호출을 통해서 풀게될텐데, 이때 현재 dfs호출에서 1의 값을 이용해서 노드를 만들고 난 후, 왼쪽 자식노드를 만들기 위해 재귀호출 할 때는 중위 순회 배열을 처음부터 끝까지 보는 것이 아니라 0인덱스에서 2(1값이 중위순회 배열에서 등장하는 인덱스에서 -1한 값)까지만 보면 됩니다. 마찬가지로 오른쪽 자식노드를 만들 때는 중위 순회 배열을 4(1값이 중위순회 배열에서 등장하는 인덱스에서 +1한 값)인덱스에서 6인덱스까지만 보도록 호출하면 됩니다.
    *
    * */
class Solution{

    public TreeNode buildTree(int[] preorder, int[] inorder) {

        //전위순회 배열의 첫 요소가 루트노드를 구성하도록 만들어야 하므로, 최초의 호출은 currentIndexInPreorderArray 값을 0으로 주고, 중위순회 배열의 탁색구간을 처음 인덱스부터 마지막 요소의 인덱스까지로 설정해줍니다.
        return dfs(0, 0, inorder.length - 1, preorder, inorder);

    }//main

    public TreeNode dfs(int currentIndexInPreorderArray, int inStart, int inEnd, int[] preorder, int[] inorder) {

        /*System.out.println("\n\t\t\t DFS CALLED");
        System.out.println("dfs param : NodeValIdx, inStart, inEnd");
        System.out.println("          :      " + currentIndexInPreorderArray + ",       " + inStart + ",        " + inEnd);*/

        //풀이 코드의 아랫부분에 위에서 설명했던 1,2,3,4,5,6,7로 구성된 원본트리를 가지고 메서드 호출 순서를 추적한 내용을 적어놨습니다. 그 내용을 보면, 아래의 if문에서 currentIndexInPreorderArray > preorder.length - 1 부분은 최하단의 최우측 노드를 호출한 후에 null 처리를 하기 위한 것이고, inStart > inEnd 부분은 리프 노드에서 널 처리를 하기 위한 부분임을 알 수 있습니다.
        //각각의 DFS CALLED 부분에서 호출 당시의 inStart, inEnd 인자 값을 같이 확인해보면 왜 널 처리가 됐는지 쉽게 파악할 수 있습니다.
        if (currentIndexInPreorderArray > preorder.length - 1 || inStart > inEnd) {
            //System.out.println("null returned\n\n");
            return null;
        }

        //일단, 인자를 통해서 넘겨 받는 currentIndexInPreorderArray 값을 이용해서 현재 노드를 만들어줍니다. 이때 preorder 배열을 사용해야 원본 트리의 루트 노드를 첫 노드로서 성공적으로 만들 수 있습니다.
        TreeNode root = new TreeNode(preorder[currentIndexInPreorderArray]);

        //System.out.println("current NodeValue : " + root.val);

        //왼쪽 자식 노드와 오른쪽 자식 노드를 만들기 전에 inIndex 값을 구해야 합니다. 이 값은 위에서 말했던, 현재 노드를 만들 때 사용한 값이 중위순회 배열 상에서 어떤 인덱스에서 나타나는지를 기록하는 것입니다.
        //inIndex 값은 현재 만들어 놓은 노드의 값봐 왼쪽에 위치해야 하는 노드들의 개수를 뜻합니다.
        int inIndex = 0;

        for (int i = inStart; i <= inEnd; i++) {
            if (inorder[i] == root.val) {
                inIndex = i;
            }
        }
        //System.out.println("inIndex : " + inIndex);

        //왼쪽 자식노드를 만들기 위한 dfs 호출을 해줍니다. preorder는 왼쪽 자식노드를 항상 먼저 확인하기 때문에, 전위 순회 배열 상에서 단순히 +1만 해주는 것으로도 현재 노드의 왼쪽 자식노드를 만들 때 어떤 인덱스 값을 사용해야 하는지를 알 수 있습니다.
        //현재 노드를 만들 때 사용한 인덱스 값은 currentIndexInPreorderArray 이므로, 여기에 +1만 해줘도 바로 현재 노드의 왼쪽 자식노드를 만들 때 필요한 인덱스 값을 구할 수 있습니다.
        //탐색해야 하는 중위 순회 배열의 인덱스 범위는 시작 부분인 inStart 부분은 변동이 없지만, 끝 부분 인덱스는 inIndex에서 -1을 해줘야 합니다. 예를 들어서 중위 순회 배열이 4 2 5 1 6 3 7와 같고, 현재 dfs 호출에서 1값을 이용해서 노드를 만들었다면, 1 노드의 왼쪽 자식 노드를 만들 때는 중위 순회 배열에서 1 보다 왼쪽에 있는 노드들만 살펴보면 됩니다.
        root.left = dfs(currentIndexInPreorderArray + 1, inStart, inIndex - 1, preorder, inorder);




        //오른쪽 자식노드를 만드는 것이 이번 문제의 하이라이트입니다. 중위 순회 배열의 탐색 범위가 'inIndex +1' 부터 inEnd 까지인 것은 이해하기 쉬운 편이지만, 오른쪽 자식노드를 만들 때 필요한 인덱스 값이 왜 [currentIndexInPreorderArray + inIndex - inStart + 1]과 같이 이해하는 것이 쉽지 않습니다.
        /*
        * 우리가 만들어야 하는 원본 트리의 모습과 전위 및 중위 순회 배열은 아래와 같습니다.
        *                      1
        *          2                        3
        *    4         5               6         7
        * n    n    n    n          n    n    n    n
        * 전위 순회(preorder traversal) : 1 2 4 5 3 6 7
        * 중위 순회(inorder traversal)  : 4 2 5 1 6 3 7
        *                    인덱스 값  : 0 1 2 3 4 5 6
        * 지금까지 설명한 재귀호출 구조를 따른다면, 3노드를 만들어내는 재귀호출은 차례대로 6노드와 7노드를 만들기 위한 dfs 호출을 자신의 차례에서 처리해야 합니다.
        * 6노드를 만드는 것은 쉬웠습니다. 전위순회의 특성상 항상 왼쪽 자식 노드를 먼저 방문하기 때문에 전위 순회 배열에서 3 값이 등장하는 인덱스인 4에서 그저 +1을 해준 6인덱스를 root.left = dfs(4+1, 4, 4, ...); 로 만들어주면 되기 때문입니다.
        *
        * 그러나 3노드의 오른쪽 자식노드를 만들 때는 전위 순회 배열 상에서 얼마나 인덱스를 옮겨야 할 것인지 정하는 것이 복잡합니다.
        * 그 이유는 전위순회가 갖는 특징 때문입니다.
        * 앞서 전위 순회는 항상 왼쪽 자식 노드를 먼저 본다고 말했습니다. 1노드를 만들고 나서 바로 2노드를 만드는 호출이 이어지지만, 1노드의 오른쪽 자식노드은 3노드를 만들기 위한 호출이 실제로 싫행되는 것은 1노드의 왼쪽 서브 트리가 전부 preorder 순으로 순회가 완료되고 나서입니다.
        *
        * 현재 3노드를 만든 호출에서 오른쪽 자식노드인 7노드를 만들기 위한 인덱스 값을 정하는 문제로 다시 돌아오면, 전위 순회 배열에서 3노드는 4인덱스에서 등장하고, 3노드의 오른쪽 자식 노드는 6인덱스에서 등장합니다.
        * 3노드가 자신의 오른쪽 자식 노드를 만들기 위해서는 4인덱스와 6인덱스 사이에 존재하는 5인덱스를 건너 뛰게끔 인게스 값을 계산해서 root.right = dfs(.....); 호출을 해야합니다.
        * 전위 순회 배열의 5인덱스에 있는 값은 3의 왼쪽 자식 노드의 값인 6입니다. 즉, 3노드는 자신의 왼쪽 서브트리들을 전부 건너 뛰어서 자신의 오른쪽 자식노드를 만들어야 하는 것입니다.
        * 이때 currentIndexInPreorderArray + inIndex - inStart + 1 를 다항식의 항별로 분리해서 의미를 해석해보면 다음과 같습니다.
        * currentIndexInPreorderArray : 현재 3노드가 만들어질 때 사용한 전위 순회 배열 상의 등장 인덱스입니다. 여기서는 4의 값을 가집니다.
        * inIndex : 중위 순회 배열 상에서 3이라는 값이 등장하는 인덱스입니다. 이는 앞에서도 여러번 반복했듯이 원본 트리에서 3노드보다 왼쪽에 존재하는 모든 노드의 개수와 같습니다. 3노드의 경우 1,2,4,5,6으로 5개의 노드가 3노드보다 왼쪽에 위치합니다. 즉, 일단은 이만큼을 currentIndexInPreorderArray에서 건너 뛰어야 하는 것입니다.
        * inStart : 그러나 잘 생각해보면, 앞의 1,2,4,5,6 노드들 중에서 3노드의 왼쪽 자식노드는 6 하나뿐입니다. 즉, 5개의 노드들 중 4개는 건너뛰어야 하는 값에서 제외시켜야 하는 것입니다. 왜냐하면, 1,2,5,6노드들은 3의 왼쪽 서브트리에 속해 있지 않기 때문입니다. 바꿔말하면, 1,2,5,6 노드들은 이미 원본 트리의 복구를 위해서 사용한 값이기 때문에 건너뛰어야 하는 범위에서 빼줘야 하는 것입니다.
        * 이 값은 3노드를 만들기 위힌 dfs 호출에 포합된 인자 중 하나인 inStart의 값과 동일합니다.
        * +1 부분은 인덱스의 전진을 통해 탐색을 계속해 나가기 위한 것입니다.
        *
        * 결과적으로 3노드는 자신의 오른쪽 자식 노드를 만들때, 오른쪽 자식노드의 생성에 사용될 인덱스를 4+5-4+1 = 6으로 최종결정하여 dfs 재귀호출을 합니다. 그 결과 3노드의 오른쪽 자식노드의 값은 preorder[6] == 7의 값을 갖게 됩니다.
        * */
        root.right = dfs(currentIndexInPreorderArray + inIndex - inStart + 1, inIndex + 1, inEnd, preorder, inorder);
        return root;
    }//func

}//main class
/*
* 			 DFS CALLED
dfs param : NodeValIdx, inStart, inEnd
          :      0,       0,        6
current NodeValue : 1
inIndex : 3

			 DFS CALLED
dfs param : NodeValIdx, inStart, inEnd
          :      1,       0,        2
current NodeValue : 2
inIndex : 1

			 DFS CALLED
dfs param : NodeValIdx, inStart, inEnd
          :      2,       0,        0
current NodeValue : 4
inIndex : 0

			 DFS CALLED
dfs param : NodeValIdx, inStart, inEnd
          :      3,       0,        -1
null returned



			 DFS CALLED
dfs param : NodeValIdx, inStart, inEnd
          :      3,       1,        0
null returned



			 DFS CALLED
dfs param : NodeValIdx, inStart, inEnd
          :      3,       2,        2
current NodeValue : 5
inIndex : 2

			 DFS CALLED
dfs param : NodeValIdx, inStart, inEnd
          :      4,       2,        1
null returned



			 DFS CALLED
dfs param : NodeValIdx, inStart, inEnd
          :      4,       3,        2
null returned



			 DFS CALLED
dfs param : NodeValIdx, inStart, inEnd
          :      4,       4,        6
current NodeValue : 3
inIndex : 5

			 DFS CALLED
dfs param : NodeValIdx, inStart, inEnd
          :      5,       4,        4
current NodeValue : 6
inIndex : 4

			 DFS CALLED
dfs param : NodeValIdx, inStart, inEnd
          :      6,       4,        3
null returned



			 DFS CALLED
dfs param : NodeValIdx, inStart, inEnd
          :      6,       5,        4
null returned



			 DFS CALLED
dfs param : NodeValIdx, inStart, inEnd
          :      6,       6,        6
current NodeValue : 7
inIndex : 6

			 DFS CALLED
dfs param : NodeValIdx, inStart, inEnd
          :      7,       6,        5
null returned



			 DFS CALLED
dfs param : NodeValIdx, inStart, inEnd
          :      7,       7,        6
null returned
* */
