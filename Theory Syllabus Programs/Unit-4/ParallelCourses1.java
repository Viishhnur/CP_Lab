/*
# 📘 Problem: Parallel Courses - I

You are given an integer `n`, indicating there are `n` courses labeled from `1` to `n`. You are also given a 2D array `relations`
 where `relations[i] = [prevCourse, nextCourse]`, representing a prerequisite relationship between courses.

A course `prevCourse` must be taken before course `nextCourse`.

In **one semester**, you can take **any number of courses**, as long as **all their prerequisites have been completed in previous semesters**.

---

## 🧠 Objective:

Return the **minimum number of semesters** needed to complete all `n` courses.  
If it is **impossible to complete all courses** (i.e., due to cycles), return `-1`.

---

## 🔍 Example 1:

**Input:**
```
n = 3
relations = [[1, 3], [2, 3]]
```

**Output:**
```
2
```

**Explanation:**
- Semester 1: Take courses 1 and 2
- Semester 2: Take course 3 (since both 1 and 2 are prerequisites)

---

## 🔍 Example 2:

**Input:**
```
n = 3
relations = [[1, 2], [2, 3], [3, 1]]
```

**Output:**
```
-1
```

**Explanation:** Cycle exists, so it's impossible to complete all courses.




 */

import java.util.*;

public class ParallelCourses1 {
    public int minimumSemesters(int N, int[][] relations) {
        // Step 1: Create adjacency list and indegree array
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i <= N; i++) {
            adj.add(new ArrayList<>());
        }

        int[] indegree = new int[N + 1];
        for (int[] rel : relations) {
            int u = rel[0];
            int v = rel[1];
            adj.get(u).add(v);
            indegree[v]++;
        }

        // Step 2: Initialize queue with nodes having indegree 0
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= N; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }

        int semester = 0;
        int completedCourses = 0;

        // Step 3: Topological sort with level/semester tracking
        while (!queue.isEmpty()) {
            int size = queue.size();
            semester++;
            for (int i = 0; i < size; i++) {
                int course = queue.poll();
                completedCourses++;
                for (int neighbor : adj.get(course)) {
                    indegree[neighbor]--;
                    if (indegree[neighbor] == 0) {
                        queue.offer(neighbor);
                    }
                }
            }
        }

        // Step 4: Check if all courses are completed
        return completedCourses == N ? semester : -1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ParallelCourses1 pc = new ParallelCourses1();

        System.out.print("Enter the number of courses: ");
        int N = sc.nextInt();

        System.out.print("Enter the number of relations (prerequisites): ");
        int M = sc.nextInt();

        int[][] relations = new int[M][2];
        System.out.println("Enter the relations (prerequisite pairs in the format 'X Y'):");
        for (int i = 0; i < M; i++) {
            relations[i][0] = sc.nextInt();
            relations[i][1] = sc.nextInt();
        }

        int result = pc.minimumSemesters(N, relations);
        System.out.println("Minimum number of semesters required: " + result);
        sc.close();
    }
}
