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
//실행시간은 1밀리초였고, 이는 상위 약 50%의 성능이었습니다.
    //문제에서 원하는 답을 도출하기 위한 전제조건을 파악하는 것이 중요합니다.
    //문제에서 말하는 '거리'를 구하기 위해서 노드 간의 거리 횟수를 구할 때 필연적으로 중간 노드들이 존재할 수밖에 없습니다.
    //이번 문제풀이에서는 '상태값'의 의미를 이해하는 것이 중요합니다.
    //특정 노드의 상태값이란, 그 노드를 루트노드로 가정했을 때 그 노드에서 리프 노드까지 도달하는데 이동해야 하는 횟수 중 최댓값을 뜻합니다.
    //이 문제의 정답은 반드시 루트노드를 포함하는 경로를 통해서만 얻을 수 있으므로,
    //최종 정답은 문제에서 주어진 루트 노드의 왼쪽 차일드 노드의 상태값과 오른쪽 차일드 노드의 상태값의 합임을 알 수 있습니다.
class Solution {
    public int diameterOfBinaryTree(TreeNode root) {
        //return type is int

        //base 타입이 아닌, 컬렉션을 이용해야 여러 dfs 호출의 중복을 통한 값의 갱신이 용이합니다. 컬렉션 중에서 가장 익숙한 ArrayList<>를 이용했습니다.
        ArrayList<Integer> longest = new ArrayList<>();
        longest.add(0);

        //루트 노드로부터 탐색을 시작합니다. 이때, 인자로 ArrayList인 longest를 전달합니다.
        dfs(longest, root);

        return longest.get(0);

    }//main

    public static int dfs(
            ArrayList<Integer> longest,
            TreeNode node
    ){

        //탐색해 들어간 노드가 null일 경우 0을 리턴해줍니다. 즉, 직전 노드의 왼쪽 차일드 노드 또는 오른쪽 차일드 노드가 존재하지 않을 경우 0을 리턴해줍니다.
        if(node == null){return 0;}


        
        //dfs 호출이 null을 만날 때까지 이어진 후에서야 dfs 메서드의 return Math.max(left,right) + 1; 부분이
        //순차적으로 호출되는 구조를 선택한 것에 주목해야 합니다.
        
        //이러한 탐색을 왼쪽 차일드 노드와 오른쪽 차일드 노드에 모두 해준 다음 기존 longest 값과
        //(leftStatusValue + rightStatusValue) 값 중 큰 값으로 longest 값을 갱신해줍니다.
        int leftStatusValue = dfs(longest, node.left);
        int rightStatusValue = dfs(longest, node.right);

        longest.set(0, Math.max(longest.get(0), leftStatusValue+rightStatusValue)) ;

        //노드의 깊이가 깊어질수록 +1이 누적되는 구조압니다.
        return Math.max(leftStatusValue, rightStatusValue) + 1;

    }//func

}//main class
/*
* 노드가 다음과 같을 때 dfs 호출 구조는 다음과 같습니다.
*          1
*         / \
*        2   3
*       / \
*      4   5
*
* 0번째 호출------- 1번째 호출 : 2 노드(0번째 호출을 leftStatusValue에 2를 전달. +1이 누적되기 때문에.) -------- 3번째 호출 : 4노드(1번째 호출의 leftStatusValue에 1을 전달함.)
*   1노드     |                        |
*            |                        |
*            |                        \---- 4번째 호출 : 5노드(1번째 호출의 rigntStatusValue에 1을 전달함)
*            |
* *          \----------5 번째 호출 : 3노드(0번째 호출의 rightStatusValue에 1을 전달. +1이 누적되기 때문에.)
*
* */
