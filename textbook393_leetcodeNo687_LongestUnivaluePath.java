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
//이번 문제는 바로 저번에 풀었던 문제인 교재 390쪽, 리트코드 543번 문항와 풀이 논리가 유사합니다.
    //풀이논리에 대한 자세한 설명은 제 깃허브 링크롤 참고해주시면 될 듯합니다.
    // https://github.com/DongvinPark/Python-Algorithm-Interview-solved-by-JAVA/blob/main/textbook390p_leetcodeNo543_DiameterofBinaryTree.java
    //실행시간은 8밀리초였고, 이는 하위 10%의 성능이었습니다.
class Solution {
    public int longestUnivaluePath(TreeNode root) {
        //return type is int

        ArrayList<Integer> longest = new ArrayList<>();
        longest.add(0);

        dfs(longest, root);

        return longest.get(0);


    }//main

    public static int dfs(
            ArrayList<Integer> longest,
            TreeNode node
    ){
        //현재 노드가 null일 경우 0을 리턴합니다.
        if(node==null){
            return 0;
        }

        //일단 dfs 호출을 모든 리프 노드까지 진행시키는 것이 중요합니다.
        int left = dfs(longest, node.left);
        int right = dfs(longest, node.right);

        //그러고 나서, 판단 절차에 들어갑니다. 현재 노드와 자식 노드의 값이 서로 일치할 경우, +1을 누적해줍니다. 값이 일치하지 않을 경우 0으로 초기화해줍니다.
        if(node.left!=null && node.val == node.left.val){
            left++;
        }
        else{ left=0; }
        if(node.right!=null && node.val == node.right.val){
            right++;
        }
        else{ right=0; }

        //left와 right 값을 더한 값과 기존의 longest 값을 이용해서 그 중에서 큰 값으로 longest 값을 초기화 해줍니다.
        longest.set(0, Math.max(longest.get(0), left+right) );

        //이 부분이 중요합니다. 이 부분은 결국 현재 호출을 발생시킨 직전 호출의 left 또는 rignt에 리턴되어 대입 될 값들이기 때문입니다. 하나의 left의 값을 저장하는 부분과 rignt의 값을 저장하는 부분이 따로 존재하기 때문에 현재의 left와 right 중에서 큰 값 1개 만을 진적 호출의 left 또는 right에 전달해야  합니다.
        //문제에서 '가장 긴 거리'를 구하라고 한 것을 기억해야 합니다.
        return Math.max(left, right);

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
//두 번째 풀이는 3밀리초의 실행시간이 나왔고, 이는 상위 25%의 성능입니다. ArrayList를 사용하지 않았고, 대신 Solution 클래스의 longestUnivaluePath 메서드 바깥에 Solution 클래스 필드로 int longest를 정의했습니다. 그리고 dfs 메서드는 언제든지 longest 필드를 참조할 수 있도록 public static int 대신 pulbic int로 변경했습니다.
class Solution {
    int longest = 0;

    public int longestUnivaluePath(TreeNode root) {
        //return type is int


        dfs(root);

        return longest;


    }//main

    public int dfs(
            TreeNode node
    ){
        if(node==null){
            return 0;
        }

        int left = dfs(node.left);
        int right = dfs(node.right);

        if(node.left!=null && node.val == node.left.val){
            left++;
        }
        else{ left=0; }
        if(node.right!=null && node.val == node.right.val){
            right++;
        }
        else{ right=0; }

        longest = Math.max(longest, left+right);

        return Math.max(left, right);

    }//func

}//main class
