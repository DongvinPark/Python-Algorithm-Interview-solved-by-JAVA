//실행시간은 1밀리초에 하위 21%였지만, 대부분의 풀이가 0밀리초와 1밀리초에만 몰려 있었기 때문에 큰 의미는 없었습니다.
    //트리의 각 층별 노드들을 큐에 집어넣고 다음 층에서 그것을 빼내는 것을 내용으로 하여 while루프를 실행시킵니다. while루프의 실행 횟수가 곧 트리의 깊이입니다.
class Solution {
    public int maxDepth(TreeNode root) {
        //return type is int

        if(root == null) return 0;

        LinkedList<TreeNode> list = new LinkedList<>();

        list.addFirst(root);

        int times = 0;

        while(!list.isEmpty()){
            times++;

            //이번 문제에서 주의해야 할 부분이 바로 이부분입니다.
            //for루프를 시작하기 전에 큐의 역할을 하는 리스트의 요소의 개수를 미리 저장해 놓는 것이 관건입니다.
            int size = list.size();

            //앞에서 저장해 놓은 size를 여기서 사용합니다.
            //size를 미리 지정하지 않고 list.size()를 사용하면 예기치 못한 오류가 발생합니다.
            //예를 들어서 리스트에 내용물이 1, 2가 들어있었는데, 여기서 list.removeFirst()를 해버릴 경우 for 루프는 2번을 반복해야 하는데, list.size()가 직전 for루프의 removeFirst()에 의해서 1로 줄어들어 버립니다. 하지만 이미 i값은 직전 for루프에 의해서 1로 늘어나 있기 때문에 결과적으로 for 루프는 애초에 계획했던 2회가 아니라 1회만 실행되고, list는 내용물을 전부 비우지 못하고 한 개 요소를 남겨놓게 됩니다. 이는 결국 오답으로 이어집니다.
            for(int i =0; i<size; i++){
                TreeNode curr = list.removeFirst();

                if(curr.left!=null){
                    list.add(curr.left);
                }
                if(curr.right!=null){
                    list.add(curr.right);
                }
            }
        }//wh

        return times;
    }//main
}//main class
