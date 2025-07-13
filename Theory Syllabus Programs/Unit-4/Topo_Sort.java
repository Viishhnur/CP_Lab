/* Topo Sort
1f) Write a JAVA Program to find a permutation of the vertices (topological order) which
corresponds to the order defined by all edges of the graph
 */
import java.util.*;
class Graph{
    private final int n;
    private final int[] indegree;
    private final List<List<Integer>> adjLst;

    private void build(int[][] edges){

        for(int i = 0 ; i < n ; i++){
            adjLst.add(new ArrayList<>());   
        }

        for(int[] edge : edges){
            int u = edge[0];
            int v = edge[1];
            adjLst.get(u).add(v); // add only u --> v edge
            indegree[v]++;
        }
    }
    public Graph(int n,int[][] edges){
        this.n = n;
        this.indegree = new int[n];
        this.adjLst = new ArrayList<>();
        build(edges);
    }

    private void dfs(int node,boolean[] visited,Stack<Integer> st){
        visited[node] = true;

        // go to nbrs
        for(int nbr : adjLst.get(node)){
            if(!visited[nbr]){
                dfs(nbr,visited,st);
            }
        }

        st.add(node);
    }
    public void printTopoSort(){

        // First way is via dfs
        Stack<Integer> st = new Stack<>();
        boolean[] visited = new boolean[n];
        for(int i = 0 ; i < n ; i++){
            if(!visited[i]){

                dfs(i,visited,st); // assuming there is only one single component in the graph
            }
        }

        // Now print the elements of stack
        System.out.println("Topo sort ordering is : ");
        while(!st.isEmpty()){
            System.out.println(st.peek());
            st.pop();
        }

    }


    public void printTopoSortUsingKahnsAlgo(){

        // Create a queue and push all elements whose indegree is 0
        Queue<Integer> qu = new LinkedList<>();
        for(int i = 0 ; i < n ; i++){
            if(indegree[i] == 0){
                qu.offer(i);
            }   
        }
        
        System.out.println("Topo sort ordering is : ");
        while(!qu.isEmpty()){
            int node = qu.poll();
            System.out.println(node);
            // go to nbrs
            for(int nbr : adjLst.get(node)){
                if(--indegree[nbr] == 0){
                    qu.offer(nbr);
                }
            }
        }
    }

}
public class Topo_Sort {
    public static void main(String[] args) {
        int n,e;
        int[][] edges;
        try(Scanner sc = new Scanner(System.in)){
            n = sc.nextInt();
            e = sc.nextInt();
            edges = new int[e][2];

            for(int i = 0 ; i < e ; i++){
                edges[i][0] = sc.nextInt();
                edges[i][1] = sc.nextInt();   
            }

        }

        Graph g = new Graph(n, edges);

        // Approach-i) Using DFS
        // g.printTopoSort();

        // Approach-ii) Using BFS(Kahn's Algo)
        g.printTopoSortUsingKahnsAlgo();
    }
}
