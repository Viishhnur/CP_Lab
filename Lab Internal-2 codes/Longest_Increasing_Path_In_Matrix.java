/* (1k) Leetcode - 329 Longest Increasing Path In Matrix 
Given an m x n integers matrix, return the length of the longest increasing path in matrix.

From each cell, you can either move in four directions: left, right, up, or down. You may not move diagonally or move outside the boundary 
(i.e., wrap-around is not allowed).

 

Example 1:

Input: matrix = [[9,9,4],[6,6,8],[2,1,1]]
Output: 4
Explanation: The longest increasing path is [1, 2, 6, 9].

Example 2:

Input: matrix = [[3,4,5],[3,2,6],[2,2,1]]
Output: 4
Explanation: The longest increasing path is [3, 4, 5, 6]. Moving diagonally is not allowed.

Example 3:

Input: matrix = [[1]]
Output: 1

 

Constraints:

    m == matrix.length
    n == matrix[i].length
    1 <= m, n <= 200
*/
import java.util.*;
public class Longest_Increasing_Path_In_Matrix {
    private final int[] dx = {0,-1,0,1};
    private final int[] dy = {1,0,-1,0};

    private int dfs(int x,int y,int[][] grid,final int m,final int n){

        int curr = grid[x][y];

        int maxi = 0;

        for(int i = 0 ; i < 4 ; i++){
            int nx = x + dx[i];
            int ny = y + dy[i];

            if(nx >= 0 && nx < m && ny >= 0 && ny < n && grid[nx][ny] < curr){
                maxi = Math.max(maxi,1 + dfs(nx,ny,grid,m,n));
            }
        }

        return maxi;
    }

    private int dfsMemo(int x,int y,int[][] grid,final int m,final int n,int[][] dp){

        if(dp[x][y] != -1){
            return dp[x][y];
        }
        int curr = grid[x][y];

        int maxi = 0;

        for(int i = 0 ; i < 4 ; i++){
            int nx = x + dx[i];
            int ny = y + dy[i];

            if(nx >= 0 && nx < m && ny >= 0 && ny < n && grid[nx][ny] < curr){
                maxi = Math.max(maxi,1 + dfsMemo(nx,ny,grid,m,n,dp));
            }
        }

        return dp[x][y] = maxi;
    }
    
    private int bfs(int start_x,int start_y,int[][] grid,final int m,final int n){
        Queue<int[]> qu = new LinkedList<>();
        boolean[][] visited = new boolean[m][n];

        qu.offer(new int[]{start_x,start_y,0}); // (i,j,len)
        visited[start_x][start_y] = true;

        int maxi = 0;
        while(!qu.isEmpty()){
            int size = qu.size();

            for(int c = 0 ; c < size ; c++){
                int[] curr = qu.poll();
                int x = curr[0] , y = curr[1] , len = curr[2];
                
                int curr_val = grid[x][y];
                maxi = Math.max(maxi,1 + len);

                // go to nbrs 
                for(int i = 0 ; i < 4 ; i++){
                    int nx = x + dx[i];
                    int ny = y + dy[i];

                    if(nx >= 0 && nx < m && ny >= 0 && ny < n && grid[nx][ny] < curr_val){
                        qu.offer(new int[]{nx,ny,1 + len});
                        visited[nx][ny] = true;
                    }
                }
            }   
        }

        return maxi;
    }
    public int longestIncreasingPath(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;    

        // int maxi = 0;
        // for(int i = 0 ; i < m ; i++){
        //     for(int j = 0 ; j < n ; j++){
        //         // Find all Longest Decreasing paths ending (i,j) it is same as Longest Increasing Paths from this point
                
        //         // Approach-i) BFS(TLE)
        //         // maxi = Math.max(maxi,bfs(i,j,mat,m,n));
                
        //         // Approach-ii) DFS(TLE)
        //         // maxi = Math.max(maxi,1 + dfs(i,j,mat,m,n));

        //         // Approach-iii) DFS + Memoization (Worked fine)
        //         // int[][] dp = new int[m][n];
        //         // for(int[] row : dp){
        //         //     Arrays.fill(row,-1);
        //         // }
        //         // maxi = Math.max(maxi,1 + dfsMemo(i,j,mat,m,n,dp));



        //     }
        // }
        // return maxi;
        

        // Approach-iv) Optimal , using Topological Sorting
        return findLIPathLength(mat,m,n);
        
    }

    private int findLIPathLength(int[][] grid,final int m,final int n){

        // Intution treat each cell of matrix as graph 
        // Suppose I am at (i,j) , and let's its nbr be (x,y) , there would be a directed edge from (i,j) to (x,y) 
        // only if grid[x][y] > grid[i][j]

        // So start processing the nodes with 0 indegree first

        // Step-i) Calc the indegree of each node
        int[][] indegree = new int[m][n];

        for(int i = 0 ; i < m ; i++){
            for(int j = 0 ; j < n ;j++){

                for(int k = 0 ; k < 4 ; k++){
                    int ni = i + dx[k];
                    int nj = j + dy[k];

                    if(ni >= 0 && ni < m && nj >= 0 && nj < n && grid[ni][nj] > grid[i][j]){
                        // there is an edge from (i,j) -> (ni,nj)
                        indegree[ni][nj]++;
                    }
                }
            }
        }

        // Step-ii) Create a queue and push all the nodes whose indegree is 0
        Queue<int[]> qu = new LinkedList<>();

        for(int i = 0 ; i < m ; i++){
            for(int j = 0 ; j < n ; j++){
                if(indegree[i][j] == 0){
                    qu.offer(new int[]{i,j});
                }
            }
        }


        // Step-iii) Starting processing these nodes using BFS in topological ordering 
        int level = 0;
        while(!qu.isEmpty()){
            int size = qu.size();

            for(int c = 0 ; c < size ; c++){
                int[] curr = qu.poll();
                int x = curr[0] , y = curr[1];

                // go to all it's nbrs and degree their indegree
                for(int i = 0 ; i < 4 ; i++){
                    int nx = x + dx[i];
                    int ny = y + dy[i];

                    if(nx >= 0 && nx < m && ny >= 0 && ny < n && grid[nx][ny] > grid[x][y]){
                        if(--indegree[nx][ny] == 0){
                            qu.offer(new int[]{nx,ny});
                        }
                    }
                }

            }

            level++;

        }

        return level;

    }
}