# 📖 1c - Fenwick Tree (Binary Indexed Tree) - Java Implementation

## 🔹 Problem Statement
Malika taught a new fun time program practice for Engineering Students. As a part of this she has given a set of numbers, and asked the students to find the **gross value (sum)** of numbers between indices **S1 and S2** (where **S1 ≤ S2**), inclusive.

Your task is to:
- Create a method `sumRange(S1, S2)` that returns the **sum of numbers between the indices S1 and S2**, inclusive.
- Use **Fenwick Tree (Binary Indexed Tree)** to solve this efficiently.

---

## ✏️ Input Format
- **Line 1**: An integer `n`, size of the array (set of numbers).
- **Line 2**: `n` space-separated integers.
- **Line 3**: Two integers `s1` and `s2`, index positions where `s1 <= s2`.

---

## 📝 Output Format
- An integer: **sum of integers between indices s1 and s2 (inclusive).**

---

## 🔧 Sample Input-1
```
8
1 2 13 4 25 16 17 8
2 6
```

### ✅ Sample Output-1
```
75
```

---

## ⚡ Constraints
- `1 <= nums.length <= 3 * 10^4`
- `-100 <= nums[i] <= 100`
- `0 <= index < nums.length`
- `-100 <= val <= 100`
- `0 <= left <= right < nums.length`
- At most `3 * 10^4` calls will be made to `update` and `sumRange`.

---

## 📊 Java Code (Using Fenwick Tree)
```java
import java.util.*;

class FenWickTree {
    int[] BIT;
    int[] nums;
    int n;

    public FenWickTree(int[] nums, int n) {
        this.n = n;
        this.nums = nums;
        this.BIT = new int[n + 1]; // 1 based indexing

        init(); // build the segment tree
    }

    private void init() {
        for (int i = 0; i < n; i++) { // Populate the fenwick tree
            update(i, nums[i]);
        }
    }

    public void update(int id, int val) {
        // this is where you are building the entire fenwick tree
        // for each val possible in array you are checking where all can this contribute
        // id mean to what all indices it can contribute

        id++; // Convert to 1-based for BIT
        while (id <= n) {
            BIT[id] += val; // add this val ,till where it can go in powers of two
            id += (id & -id); // isolates the last set bit
        }
    }

    public int sumQuery(int id) {
        id++;

        int sum = 0;
        while (id > 0) {
            sum += BIT[id];
            id -= (id & -id);
        }

        return sum;
    }

    public int sumRange(int a, int b) {
        return sumQuery(b) - sumQuery(a - 1);
    }

}

public class Test {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        // take range input
        int l = sc.nextInt();
        int r = sc.nextInt();

        FenWickTree fnwkTree = new FenWickTree(arr, n);
        System.out.println(fnwkTree.sumRange(l , r));
    }
}
```

---

## ⏱️ Time and Space Complexity

### ⏳ Time Complexity:
- `update(index, val)` → **O(log n)**
- `sumQuery(index)` → **O(log n)**
- `sumRange(s1, s2)` → **O(log n)** using 2 queries

### 📅 Space Complexity:
- `O(n)` for the BIT array
- `O(n)` for the original array reference

---

## 💡 Notes:
- Fenwick Tree (or BIT) is extremely useful for performing point updates and prefix/range sum queries in logarithmic time.
- It is commonly used in competitive programming and real-time applications like cumulative frequency analysis.

---


