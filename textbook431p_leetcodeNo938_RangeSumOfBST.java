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

//Brute Force Method
//첫 번째 풀이는 모든 노드들을 dfs로 전부 탐색하는 풀이입니다. 테스트 케이스의 크기가 크지 않아서 시간이 1밀리초밖에 걸리지 않았지만, 테스트 케이스의 규모가 커지면, 최적화하지 않은 풀이보다 시간이 더 많이 걸릴 것입니다.
class Solution {

    public int sum = 0;

    public int rangeSumBST(TreeNode root, int low, int high) {
        //return type is int

        dfs(root, low, high);

        return sum;
    }//main

    public void dfs(TreeNode node, int low, int high){

        //일단 노드를 방문하고, 그 노드가 null이 아니면서, 값이 주어진 최소 및 최댓값의 범위에 있을 때 sum 에 누적시킵니다.
        //그 후, 현재 노드의 왼쪽 자식 노드와 오른쪽 자식 노드에 대해서 동일한 dfs 재귀호출을 이어나가게 해서 모든 노드에 대해 탐색할 수 있게 해줍니다.
        if(node != null){

            if(node.val >= low && node.val <= high){
                sum += node.val;
            }

            dfs(node.left, low, high);
            dfs(node.right, low, high);

        }

    }//func

}//main class







//DFS Optimization
//두 번째 풀이는 모든 노드를 탐색하지 않고, 조건을 만족하는 경우에만 dfs 호출을 해서 불필요한 탐색이 이루어지지 않게 최적화한 풀이입니다.
//실행시간은 테스트 케이스가 크지 않아서 0밀리초가 나왔습니다. 첫 번째 풀이가 1밀리초가 걸린 것에 비해서 차이가 1밀리초 밖에 나지 않았지만, 확실히 실행시간이 줄어들면서 최적화가 효과가 있었음을 알 수 있습니다.
class Solution {

    public int sum = 0;

    public int rangeSumBST(TreeNode root, int low, int high) {
        //return type is int

        dfs(root, low, high);

        return sum;
    }//main

    public void dfs(TreeNode node, int low, int high){

        //현재의 노드가 null이 아닐 때에만 방문하는 것은 동일합니다.
        if(node != null){

            //하지만 여기서 경우의 수가 나눠집니다.
            //왜 이렇게 경우가 나누어진 것인지는 이진 탐색트리의 특징을 알고나면 쉽게 이해할 수 있습니다.
            /* 아래와 같이 생긴 이진트리가 있고, 최솟값이 7, 최댓값이 15인 상황이 리트코드 예시에서 주어졌습니다.
            *                10
            *         5               15
            *     3      7      null      18
            *
            * 이때, 재귀호출 끝에  5노드에 도착하면, 이진 탐색 트리의 특성상 5노드의 왼쪽 노드는 볼 필요 없습니다.
            * 왜냐하면 이진 탐색 트리에서 각각의 노드들의 자식 노드는 부노 노드 보다 작은 값을 가져야 하기 문입니다.
            * 따라서 현재 노드가 최솟값인 7보다 작으면, 현재 노드은 5 노드의 왼쪽 자식노드는 반드시 5보다 작을 수밖에 없으므로 더 이상 dfs 재귀호출을 할 필요가 없는 것입니다.
            *
            * 반대로, 현재 노드가 최댓값인 15보다 크다면, 이진탐색 트리의 특성상 현재 노드의 오른쪽 자식 노드는 현재 노드보다 같거나 큰 값이 들어가야 하므로 현재 노드의 오른쪽 자식노드를 탐색하기 위해서 재귀호출을 이어나갈 필요가 없습니다.
            *
            * 그리고 현재 노드가 최솟값 7 이상, 최댓값 15 이하인 경우 왼쪽 자식노드와 오른쪽 자식노드의 값이 최소 및 최대 범위를 벗어나는지 확신할 수 없기 때문에 이때는 반드시 왼쪽과 오르쪽 자식노드를 전부 탐색해야 합니다.
            * */

            if(node.val < low){
                dfs(node.right, low, high);
            }
            else if(node.val > high){
                dfs(node.left, low, high);
            }
            else{
                dfs(node.left, low, high);
                dfs(node.right, low, high);
            }

            if(node.val >= low && node.val <= high){
                sum += node.val;
            }

        }

    }//func

}//main class
