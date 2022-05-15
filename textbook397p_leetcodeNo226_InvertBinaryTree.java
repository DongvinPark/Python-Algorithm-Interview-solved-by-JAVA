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
//실행시간은 0밀리초가 나왔고, 제출된 거의 모든 풀이가 이 실행시간에 몰려 있어서 큰 의미는 없었습니다.
class Solution {
    public TreeNode invertTree(TreeNode root) {
        //return type is TreeNode

        if(root == null) return null;

        LinkedList<TreeNode> list = new LinkedList<>();

        list.add(root);

        while(!list.isEmpty()){
            //큐에 있는 내용물들은 트리의 현재 층의 노듣들 입니다.
            //그 노드들을 앞에서부터 순서대로 꺼내면서 다음과 같은 처리들 중 하나를 선택해서 진행합니다.
            //노드가 null 일수도, 아닔 수도 있기 때문에 null처리를 이렇게 일일이 신경써줘야 합니다.

            TreeNode node = list.removeFirst();

            //두 자식노드 다 널인 경우. 두 자식 노드 간에 서로 위치를 바꿔줍니다.
            if(node.left != null && node.right != null){
                TreeNode temp = node.left;
                node.left = node.right;
                node.right = temp;
            }

            //왼쪽 자식노드만 널인 경우. 오른쪽 자식노드를 왼쪽 자식노드의 위치로 바꿉니다.
            else if(node.left == null && node.right != null){
                TreeNode newOne = new TreeNode();
                newOne = node.right;
                node.left = newOne;
                node.right = null;
            }

            //오른쪽 자식노드만 널인 경우. 왼쪽 자식노드를 오른쪽 자식노드의 위치로 바꿉니다.
            else if(node.left != null && node.right == null){
                TreeNode newOne = new TreeNode();
                newOne = node.left;
                node.right = newOne;
                node.left = null;
            }

            //자식노드들도 null이 아닌 경우에만, [다음 while 루프에 사용될 노드들]로서 큐에 넣어줘야 합니다.
            if(node.left != null) list.addLast(node.left);
            if(node.right != null) list.addLast(node.right);
        }//wh

        return root;

    }//main
}//main class
