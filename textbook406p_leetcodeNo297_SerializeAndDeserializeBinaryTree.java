/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
// 이진 트리를 직렬화한 후, 직렬화한 결과를 바탕으로 다시 원래의 이진트리로 복원해내라는 문제입니다.
// 직렬화의 결과물로서 스트링을 반환할 것을 제시하고 있는데, 스트링에서 null인 노드를 어떻게 처리할지는
// 문제를 푸는 사람의 마음대로 하면 됩니다. 코드 맨 아래 부분의 주석에서 그것에 대한 내용을 주석으로 알려주고 있습니다.
//이번 풀이는 while 문과 LinkedList 큐를 이용한 BFS 풀이로 접근했고, 실행시간은 9밀리초에 상위 약 9%의 성능이었습니다.
//트리를 직렬화 할때, 노드가 널이 아닌 경우엔 숫자와 공백 하나를 스트링빌더에 추가하고, 노드가 널인 경우엔 N이라는
//문자와 공백 하나를 스트링빌더에 추가해줍니다.
public class Codec {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        //return type is String

        //입력 노드가 null인 경우엔 아래와 같이 처리해줍니다. 왜 이러허게 처리했는지는 설명을 바로 아랫부분에서 설명할 예정입니다.
        if(root == null) return "N N ";
        
        LinkedList<TreeNode> list = new LinkedList<>();
        
        list.addLast(root);
        
        StringBuilder sb = new StringBuilder();
        
        //어떤 노드도 추가되기 전의 초기 상태의 스트링 빌더에 미리 N와 공백을 넣어 놓습니다.
        //이는 직렬화된 트리를 추후에 역직렬화해서 원래의 트리 형태로 복원해낼 때 인덱스 계산의 편의성을 위한 것입니다.
        //첫 번째 노드를 0 인덱스가 아니라 1 인덱스에 넣개 위한 조치입니다.
        sb.append("N"); sb.append(" ");
        
        while(list.isEmpty()==false){
            
            //리스트에 들어있는 요소들 중 첫 번째 요소를 꺼냅니다.
            TreeNode node = list.removeFirst();
            
            //널이 아닌 경우엔 현재 노드인 node의 왼쪽 자식과 오른쪽 자식을 리스트에 넣어줍니다.
            //이때 자식 노드가 널인 경우에도 일단 리스트에 넣을 수는 있습니다. 이렇게 되었다 해도, 어차피
            //다음 while루프에서 else 파트가 null 부분을 처리해주기 때문에서 굳이 여기에서 null 처리를 따로 해줄 필요가 없습니다.
            if(node != null){
                list.addLast(node.left);
                list.addLast(node.right);
                sb.append(node.val); sb.append(" ");
            }
            
            //현재 노드인 node가 null인 경우엔 N과 공백을 sb에 넣어줍니다.
            else{
                sb.append("N ");
            }
            
        }//wh
        
        //System.out.println(sb);
        
        return sb.toString();
        
    }//main 1

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        //return type is TreeNode
        if(data.equals("N N ")) return null;
        
        //직렬화된 결과물을 String[]의 형태로 만들어줍니다.
        String[] treeArray = data.split(" ");
        
        //for(String s : treeArray) System.out.print(s);
        
        
        TreeNode root = new TreeNode(Integer.parseInt(treeArray[1]));
        LinkedList<TreeNode> list = new LinkedList<>();
        
        //while 루프를 시작하기 위해서는 이 부분이 꼭 필요합니다.
        list.addLast(root);
        
        //인덱스를 2로 설정한 것이 중요합니다. 이는 이진 트리의 중요한 성질 때문입니다.
        /*
        이진 트리가 
                        1
                   2       3
                4   5     6   7
        이렇게 생긴 상황에서 이것을 직렬화 했을 때, 1번 노드는 1 인덱스에 넣으면 되고,
        2번 노드는 2 인덱스에, 3번 노드는 3 인덱스로 가면 됩니다.
        이때, 현재 노드가 i라면 왼쪽 자식 노드는 2i, 오른쪽 자식 노드는 2i+1라는 규칙을 찾을 수 있습니다.
        직렬화된 결과에서 1은 이미 list에 추가했으므로, 1의 왼쪽 자식노드인 2부터 시작하는 것이 직관적으로 이해가 쉽습니다.
        */
        int index = 2;
        
        while(list.isEmpty()==false){
            TreeNode node = list.removeFirst();
            
            //treeArray[index] 값이 널이 아닌 경우 왼쪽 자식 노드를 만들어줍니다.
            //그리고 다음 while루프를 위해서 node.left를 리스트에 집어넣어 줍니다.
            if(treeArray[index].equals("N")==false){
                node.left = new  TreeNode(Integer.parseInt(treeArray[index]));
                list.addLast(node.left);
            }
            //여기서 인덱스를 +1 시킨 것이 중요합니다. 인덱스를 +1 해줌으로써 현재 노드인 node의 왼쪽 자식 노드에 이어서
            //바로 오른쪽 자식 노드를 treeArray[]에서 접근할 수 있기 때문입니다.
            index +=1;
            
            //위와 같은 이유로 현재 노드인 node의 오른쪽 자식 노드에 대해서도 동일한 규칙으로 처리해줍니다.
            if(treeArray[index].equals("N")==false){
                node.right = new TreeNode(Integer.parseInt(treeArray[index]));
                list.addLast(node.right);
            }
            index +=1;
        }//wh
        
        return root;
        
    }//main 2
}//main class

// Your Codec object will be instantiated and called as such:
// Codec ser = new Codec();
// Codec deser = new Codec();
// TreeNode ans = deser.deserialize(ser.serialize(root));
