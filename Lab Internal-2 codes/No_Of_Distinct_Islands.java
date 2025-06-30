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