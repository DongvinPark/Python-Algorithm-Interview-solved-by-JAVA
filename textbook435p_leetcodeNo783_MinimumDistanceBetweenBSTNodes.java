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
//두 가지 풀이 모두 이진 탐색 트리의 특성을 정확히 파악하여 활용해야 한다는 공통점이 있습니다. 그것은, 이진 탐색 트리에서는 항상 왼쪽 자식 노드의 값이 부모 노드의 값보다 작아야 하고, 오른쪽 자신 노드의 값은 항상 부모노드의 값보다 크거나 같아야 한다는 점 때문에 중위 순회를 할 경우, 트리 내의 모든 값들을 오름차순으로 방문하게 된다는 점입니다.
    //따라서, 이들 간의 차이 중 최솟값을 찾아내는 것은 예를 들어서 1, 2, 5, 6, 9가 있을 때, 1과2, 2와5, 5와6, 6,9 간의 차이에서 가장 작은 값인 1을 반환하면 됩니다.
    //첫 번째 풀이는 실행시간이 0밀리초와 1밀리초를 왔다갔다 하능 등의 애매한 면이 있었지만, 두 번째 풀이는 항상 0밀로초로 측정되었습니다.
    //첫 번째 풀이는 아무래도 ArrayList를 동원해서 최솟값을 계산하는 과정이 dfs 재귀 호출 밖에서 별도로 이루어지는 것 때문인 것으로 추정됩니다.
    //두 번째 풀이는 별도의 리스트를 필요로 하지 않고, dfs 호출 내부에서 필요한 로직을 전부 포함하고 있으므로 dfs 호출이 전부 종료되는 즉시 별도의 과정을 필요로 하지 않고 즉시 정답을 알안낼 수 있기 때문에 첫 번째 풀이보다 항상 빠르게 측정된 것 같습니다.
class Solution {

    private ArrayList<Integer> list = new ArrayList<>();

    public int minDiffInBST(TreeNode root) {
        //return type is int

        int min = 100_001;

        dfs(root);

        //Collections.sort(list);

        for(int i=0; i<=list.size()-2; i++){
            if(list.get(i+1)-list.get(i) <= min){
                min = list.get(i+1)-list.get(i);
            }
        }

        return min;

    }//main

    public void dfs(TreeNode node){

        //중위 순회로 트리를 순회하면서 현재 노드의 값을 list에 집어넣습니다. 중위순회의 특징으로 인해, 이진 탐색 트리의 값이 자동으로 오름차순으로 리스트에 입력되기 때문에, 리스트의 값들을 굳이 또 한번 Collection.sort(list); 이런 식으로 정렬해줄 필요가 없습니다.
        if(node != null){
            dfs(node.left);
            list.add(node.val);
            dfs(node.right);
        }

    }//func

}//main class










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
class Solution {
    Integer min;

    Integer prev;

    public int minDiffInBST(TreeNode root) {
        //return type is int

        min = 100_001;

        prev = null;

        preorder(root);

        return min;

    }//main

    public void preorder(TreeNode node){

        if(node == null){ return; }

        //여기에 dfs 재귀 호출을 node.left를 인자로 넘김으로써 호출하는 부분이 나온 것이 뜻하는 것을 이해해야 합니다.
        //이렇게 함으로써
        preorder(node.left);

        //왼쪽 자식노드에 대한 재귀호츨을 통해서 min 값이 이미 초기화 돼 있다는 사실을 기억하는 것이 중요합니다.
        //따라서 현재의 if문은 왼쪽 자식노드 내에서 실행된느 것이 아니라, 왼쪽 자식노드의 부모인 그냥 node 내에서 실행되는 것임을 정확히 파악해야 합니다. 따라서 현재의 if 문 내의 Math.min 비교 메서드는 결국 [왼쪽 자식 노드에 의해서 초기화 된 min]과 [현재 노드값과 직전 노드의 값 간의 차이] 중에서 더 작은 값을 가려내는 것으로 해석할 수 있습니다.
        if(prev != null){
            min = Math.min( min, node.val -prev );
        }
        //prev 부분에 현재의 노드를 저장하는 것도 빼먹으면 안 됩니다. 지금 저장해 놓은 prev는 현재의 노드를 담고 있는데, 이는 다음 dfs 호출에서 prev가 if 문의 비교 부분에서 등장하여 최솟갑시 비교를 할지 말지를 결정하는 부분에서 사용되어야 하기 때문입니다.
        prev = node.val;

        preorder(node.right);

    }//func

}//main class
