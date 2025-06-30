import java.util.*;
class DisjointSet {
    private final int n;
    private final int[] parent;
    private final int[] rank;

    public DisjointSet(int n) {
        this.n = n;
        this.parent = new int[n];
        this.rank = new int[n];
        init();
    }

    private void init() {
        for (int i = 0; i < n; i++) {
            this.parent[i] = i;
        }
    }

    public int findUParent(int node) {
        if (parent[node] == node) {
            return node;
        }

        return parent[node] = findUParent(parent[node]);
    }

    public void unionByRank(int u, int v) {
        int pu = findUParent(u);
        int pv = findUParent(v);

        if (pu == pv)
            return; // do not add a edge between them since they are in same component

        if (rank[pu] == rank[pv]) {
            parent[pv] = pu;
            rank[pu]++;
        } else if (rank[pv] < rank[pu]) {
            parent[pv] = pu;
        } else {
            parent[pu] = pv;
        }

    }

    
}
public class No_Of_Distinct_Islands {
    
    private static final int[] dx = { 0, -1, 0, 1 };
	private static final int[] dy = { 1, 0, -1, 0 };
    int countDistinctIslands(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        DisjointSet dsu = new DisjointSet(m*n);

        for(int i = 0 ; i < m ; i++){
            for(int j = 0 ; j < n ; j++){
                if(grid[i][j] == 1){
                    int nodeId = i * n + j;
                    for(int k = 0 ; k < 4 ; k++){
                        int nx = i + dx[k];
                        int ny = j + dy[k];
                        
                        if(nx >= 0 && nx < m && ny >= 0 && ny < n && grid[nx][ny] == 1){
                            int nbrId = nx * n + ny;
                            dsu.unionByRank(nodeId, nbrId);
                        }

                    }
                }
            }
        }

		HashSet<String> seen = new HashSet<>();

        String[] pattern = new String[m*n];
        for(int i = 0 ; i < m * n ; i++){
            pattern[i] = "";
        }
        for(int i = 0 ; i < m ; i++){
            for(int j = 0 ; j < n ; j++){

                if(grid[i][j] == 1){
                    int nodeId = i * n + j;
                    int parent = dsu.findUParent(nodeId);
                    pattern[parent] += String.valueOf(nodeId - parent);
                    
                }
            }
        }

        for(String str : pattern){
            if(str != ""){
                seen.add(str);
            }
        }


		return seen.size();
    }
}

/*
 Method-2 DFS


// User function Template for Java

class Solution {

    private static final int[] dx = {0,-1,0,1}; 
    private static final int[] dy = {1,0,-1,0};

    private void dfs(int row,int col,int baserow,int basecol,int[][] grid,boolean[][] visited,int m,int n,List<String> shape){

        shape.add((row - baserow) + "," + (col - basecol));
        visited[row][col] = true;

        for(int i = 0 ; i < 4 ; i++){
            int nx = row + dx[i];
            int ny = col + dy[i];

            if(nx >= 0 && nx < m && ny >=0 && ny < n && !visited[nx][ny] && grid[nx][ny] == 1){
                dfs(nx,ny,baserow,basecol,grid,visited,m,n,shape);
            }
        }
    }
     int countDistinctIslands(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        HashSet<String> uniqueIslands = new HashSet<>();
        boolean[][] visited = new boolean[m][n];
        for(int i = 0 ; i < m ; i++){
            for(int j = 0 ; j < n ; j++){
                if(!visited[i][j] && grid[i][j] == 1){
                    List<String> shape = new ArrayList<>();
                    dfs(i,j,i,j,grid,visited,m,n,shape);
                    Collections.sort(shape); 
                    uniqueIslands.add(String.join(";", shape));
                }
            }
        }

        return uniqueIslands.size();
    }
}
 */