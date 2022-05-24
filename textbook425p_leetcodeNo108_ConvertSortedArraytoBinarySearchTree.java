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
//실행시간은 0밀리초가 나왔지만, 제출된 풀이의 거의 전부가 0밀리초 구간에 모여 있어서 큰 의미는 없었습니다.
    //파이썬에서는 nums[mid:]와 같이 슬라이싱 기능을 지원하지만, 자바에서는 그런 기능이 없으므로, 배열의 인덱스를 직접 조작하는 방식으로 풀이해야 합니다.
    //재귀적으로 이진 탐색을 진행해 호출되고, 그 결과로 리턴되는 트리노드가 직전 재귀호출에서 노드들을 구성하는 것에 이용되는 반복구조를 발견할 수 있습니다.
    //이진 탐색을 진행해나가면서 mid 값을 이용해서 노드를 만드들고, 그 노드의 왼쪽 자식노드와 오른쪽 자식 노드를 만들기 위해서 다시 재귀호출을 이용하는 구조입니다.
class Solution {

    //public List<Integer> list = new ArrayList<>();

    public TreeNode sortedArrayToBST(int[] nums) {
        //return type is TreeNode

        TreeNode answer = recul(nums, 0, nums.length);

        return answer;

    }//main

    public TreeNode recul(int[] nums, int min, int max  ){

        int mid = (min + max)/2;

        //System.out.println(min + "," + max +"," +  mid + "\n");

        //언제 null을 리턴할지 정하는 것이 중요합니다.
        //탐색의 시작 인덱스와 끝 인덱스가 서로 일치하면 탐색 구간이 존재하지 않으므로 null을 반환해야 합니다.
        //또한 탐색 인덱스가 마지막 요소의 인덱스인 nums.length-1보다 큰 경우엔 찾는 값이 존재할 수 없으므로 null을 리턴해야 합니다.
        if( (min == max) || (mid > nums.length-1 ) ){
            return null;
        }

        //현재 호출의 mid 값을 이용해서 트리 노드를 만들어줍니다. 그 후 현재 노드의 왼쪽 및 오른쪽 자식노드를 만들 때 dfs 재귀호출을 이용합니다.
        TreeNode node = new TreeNode(nums[mid]);

        //System.out.println("left entered");
        node.left = recul(nums, min, mid);

        //System.out.println("right entered");
        node.right = recul(nums, mid+1, max);

        return node;

    }//func

}//main class

/*
*
* 아래의 호출구조는 입력 배열인 nums[] 가 [-10,-3,0,5,9]인 경우에 dfs 호출이 작동되는 구조를 추적한 결과압니다.
* 각 호출에 대해서 탐색 시작 인덱스, 탐색 끝 인덱스, mid 값으로 구성됩니다.
* 호출 관계 및 순서를 그래프로 표현해 보면 다음과 같습니다.
*
*                                                 0 : 1st
*                                             /                  \
*                          -3 : 2nd                                      9 : 7th
*                      /          \                                 /               \
*              -10 : 3rd         null : 6th                  5 : 8th                 null : 11th
*               /       \                                    /       \
*     null : 4th      null : 5th                     null : 9th     null : 10th
*
first DFS call. root node.
0,5,2

left entered
0,2,1

left entered
0,1,0

left entered
0,0,0

right entered
1,1,1

right entered
2,2,2

right entered
3,5,4

left entered
3,4,3

left entered
3,3,3

right entered
4,4,4

right entered
5,5,5
* */
