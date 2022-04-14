//재귀호출을 이용한 풀이입니다. 실행시간은 3밀리초였고 상위 10% 정도의 성능을 기록했습니다.
//이 문제를 접근할 때는 육지를 만났을 때 연결된 육지들을 전부 1이 아닌 다른 값으로 대체시켜줌으로써 섬의 개수를 세는 것으로 접근해야 합니다.
class Solution {
    public int numIslands(char[][] grid) {
        //return type is int
        
        int answer = 0;
        
        for(int i=0; i<grid.length; i++){
            for(int j = 0; j<grid[i].length; j++){
                if(grid[i][j]=='1'){
                        
                        dfs(i,j,grid);
                        
                        answer++;
                    }
            }//i for
        }// o for
        
        return answer;
        
    }//main
    
    public static void dfs(int vt, int hr, char[][] grid){

        //이 부분이 중요합니다. 입력으로 주어진 grid의 범위를 벗어나면 안 되므로, 그렇게 벗어난 경우 어떤 행동도 하지 않고 메서드를 종료시킴으로써 인덱스 범위 오류를 방지합니다.
        if(vt < 0 || vt >= grid.length || hr<0 || hr>=grid[vt].length ){return;}
        

        if(grid[vt][hr]=='1' && vt < grid.length && hr < grid[vt].length){//지금 육지에 있으므로, 현재 위치에 2 마킹하고, 동서남북 방향으로 재귀호출을 실행합니다.
            grid[vt][hr] = '2';
            dfs(vt , hr+1, grid);
            dfs(vt , hr-1, grid);
            dfs(vt+1 , hr, grid);
            dfs(vt-1 , hr, grid);
        }
        if(grid[vt][hr] == '2') return;
    }//func
    
}//end of main
