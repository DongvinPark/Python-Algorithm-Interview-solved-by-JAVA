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
//실행시간은 1밀리초였고, 이는 약 상위 약 34%의 성능이었습니다.
//이번 문제는 재귀 호출로 풀이하는 것이 가장 적절했습니다. 입력 받은 두 노드가 모두 널이 아닐 때만 새로운 노드를 만들고, 그 새로운 노드를 만드는 것에 재귀 호출을 활용합니다.
class Solution {
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        //retutn type is TreeNode
        
        //둘 다 널이 아닌 경우, 새로운 노드를 만들고, 그 노드의 값을 root1.val과 root2.val을 합쳐서 만들어줍니다. 이때, 문제에서 주어진 TreeNode의 정의를 참고해서 생성자를 활용합니다.
        if(root1 != null && root2 != null){
            TreeNode newOne = new TreeNode(root1.val + root2.val);

            //재귀 호출의 구조를 파악하는 것이 중요합니다. 현재 호출의 왼쪽 자식노드와 오른쪽 자식 노드를 만들어 줄 때, 재귀 호출을 이용해줍니다.
            newOne.left = mergeTrees(root1.left, root2.left);
            newOne.right = mergeTrees(root1.right, root2.right);
            return newOne;
        }
        
        //널 처리를 정확히 해주는 것이 중요합니다.
        //root1만 null인 경우엔 root2를 반환해줍니다.
        else if(root1==null && root2!=null){
            return root2;
        }
        
        //root2만 널인 경우엔 root1를 반환해줍니다.
        else if(root1 != null && root2 == null){
            return root1;
        }
        
        //둘 다 널인 경우 null을 반환해줍니다.
        return null;
        
    }//main
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
//첫 번째 풀이는 노드를 새로 만든다는 점에서 공간복잡도가 O(n)인 풀이입니다.
//새로운 노드를 만들 필요 없이, root1의 노드에 root2의 노드들을 그대로 합치는 방식을 이용하면 공간복잡도에 O(1)에 풀이할 수 있습니다.
class Solution {
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        
        
        if( root1 == null ){
            return root2;
        }
        
        if( root2== null ){
            return root1;
        }
        
        
        root1.val += root2.val;
        root1.left = mergeTrees(root1.left, root2.left);
        root1.right = mergeTrees(root1.right, root2.right);
        return root1;
    }
}
