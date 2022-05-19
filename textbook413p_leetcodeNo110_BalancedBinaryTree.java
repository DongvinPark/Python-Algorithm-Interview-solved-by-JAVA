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
//실행시간은 1밀리초였고, 이는 상위 5%의 성능이었지만 대부분의 풀이가 0,1밀리초 구간에만 몰려있었기 때문에 큰 의미는 없었습니다.
//DFS 재귀호출을 이용한 풀이입니다.
class Solution {
    public boolean isBalanced(TreeNode root) {
        //return type is boolean
        
        
        return check(root) != -1;
        
    }//main
    
    public int check(TreeNode node){
        if(node == null) return 0;
        
        int left = check(node.left);
        int right = check(node.right);
        
        //재귀 호출을 이어 나가다가, 아래의 if문에 해당하는 조건을 마주치게 되면 -1을 리턴합니다.
        //-1을 리턴했다는 것은 left, right 간의 차이가 1을 초과하는 부분이 발생했다는 의미입니다.
        //한번 -1이 리턴되기 시작하면 -1을 중간에 절대 사라지지 않고 최초의 dfs 호출까지 전파되서 최초의 dfs는 결국 -1을 리턴하게 됩니다.
        if(left == -1 || right == -1 || Math.abs(left-right)>1){
            return -1;
        }
        
        else return Math.max(left, right) +1;
        
    }//func
    
}//main class
// 리트코드의 Example 2에서 나온 예시를 바탕으로 작동과정을 추적해보면 다음과 같습니다.
/*
1층 >>>                  1
2층 >>>            2           2
3층 >>>      3         3
4층 >>> 4         4
null 구간
트리가 다음과 같이 생겼을 때,
4층의 두 개의 노드는 자신의 부모 노드인 3층 왼쪽의 3노드에게 각각 1을 리턴할 것입니다. 왜냐하면, 두 노드 모두 자식노드가 모두 null이기 때문에 높이차가 발생하지 않아서 else return~~ 부분이 작동할 것이기 때문입니다.

3층의 오른쪽 노드는 자식 노드가 모두 널이기 때문에 자신의 부모노드인 2층 왼쪽 2노드에게 1을 리턴합니다.
3층의 왼쪽 노드는 자신의 자식노드들인 4층의 노드들로부터 1을 전달 받지만 높이차는 1 보다 크지는 않은 상태 입니다. 따라서 자신의 부모노드인 2층 왼쪽 노드에게 2를 리턴합니다.

2층의 왼쪽 노드는 자신의 자식노드들로부터 left=2와 right=1을 전달 받습니다. 높이차는 1보다 크지 않습니다. 따라서 3을 루트노드인 1층 1노드에게 리턴합니다.
2층의 오른쪽 노드는 자식 노드가 모두 null이기 때문에 1을 1층의 1노드에게 리턴합니다.

1층의 루트노드는 left=3, right=1을 전달 받습니다. 하지만 높이차가 2가 되면서 1을 초과해버리기 때문에 결국 최초로 호출된 dfs 호출의 값은 -1이 될 수밖에 없습니다.
*/









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
//두 번째 풀이의 실행시간은 첫 번째 풀이와 동일합니다.
//하지만, 이해하기에 훨씬 직관적인 풀이입니다.
// 앞서 첫 번째 풀이에서 높이차가 1을 초과하는 상황이 발생하면 그것이 결국은 최초의 dfs 호출에까지 전파되면서 최종적으로 정답은 false가 되는 것을 확인할 수 있었습니다.
//그러면, 굳이 높이차를 계속 계산해나갈 필요가 없습니다.
//높이 차가 한 번이라도 1을 초과하는 순간에 dfs호출 메서드가 자신의 바깥에 있는 boolean answer = true;를 asnwer = false;로 초기화하고, 그 결과를 그대로 정답으로 반환하면 됩니다.
class Solution {
    
    public boolean answer = true;
    
    public boolean isBalanced(TreeNode root) {
        //return type is boolean
        
        
        int temp = check(root);
        
        return answer;
    }//main
    
    public int check(TreeNode node){
        if(node == null) return 0;
        
        int left = check(node.left);
        int right = check(node.right);
        
        if(Math.abs(left-right)>1){
            answer = false;
        }
        
        return Math.max(left, right) +1;
        
    }//func
    
}//main class
