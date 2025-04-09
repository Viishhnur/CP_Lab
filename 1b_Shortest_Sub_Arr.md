# 📘 1b - Shortest Subarray with Sum at Least K

## 🧩 Problem Statement

Given an **integer array** `nums` and an **integer** `k`, return the **length of the shortest non-empty subarray** of `nums` with a sum **at least** `k`.

If no such subarray exists, return `-1`.

A **subarray** is a contiguous part of an array.

---

## 🔍 Examples

### Example 1:

```
Input: nums = [1], k = 1
Output: 1
```

### Example 2:

```
Input: nums = [1, 2], k = 4
Output: -1
```

### Example 3:

```
Input: nums = [2, -1, 2], k = 3
Output: 3
```

---

## 🤔 Constraints

- `1 <= nums.length <= 10^5`
- `-10^5 <= nums[i] <= 10^5`
- `1 <= k <= 10^9`

---

## 📖 Approach Explanation

### ❌ Naive Approach (Fails for Negatives):

A classical sliding window approach fails when negative numbers are present, as they can cause the sum to dip below the target, preventing correct detection of valid subarrays.

### ✅ Optimized Approach (Monotonic Deque):

This approach uses:

- A **prefix sum** array to efficiently compute subarray sums.
- A **monotonic deque** to maintain indices of increasing prefix sums.

#### Steps:

1. Calculate prefix sums on the fly while iterating.
2. Use a deque to maintain a list of indices with increasing prefix sums.
3. If the current prefix sum is greater than or equal to `k`, update `minLen`.
4. Shrink the deque from the front while the condition `prefix[j] - prefix[deque.front()] >= k` holds.
5. Clean up the deque from the back to maintain increasing order.

---

## ⏱️ Time and Space Complexity

### Time Complexity:

- **O(n)** – Each element is added and removed from the deque at most once.

### Space Complexity:

- **O(n)** – For the prefix sum array.
- **O(n)** – In the worst case, all indices may be stored in the deque.

---

## 📂 How to Run the Code

### Input Format:

First line: Integer `n` (size of the array)
Next `n` lines: `n` integers representing the array `nums`
Last line: Integer `k`

### Sample Input:

```
3
2 -1 2
3
```

### Sample Output:

```
3
```

---

## 📁 File: `ShortestSubArrWithSumAtleastK.java`

This Java file contains both a commented-out naive approach and the full implementation of the **Monotonic Deque** based method to solve the problem efficiently.
## 🔧 Java Code

```java

import java.util.*;
public class ShortestSubArrWithSumAtleastK {

    // Approach-i) Classical Sliding Window fails , due to negative numbers dipping
    // the value and stopping us from caluclating
    // the minimum window
    // public int shortestSubarray(int[] nums, int k) {
    // int n = nums.length;

    // int sum = 0;

    // int left = 0 , right = 0;

    // int minLen = Integer.MAX_VALUE;
    // while(right < n){

    // sum += nums[right];

    // while(sum >= k){

    // int currLen = right - left + 1;
    // minLen = Math.min(minLen,currLen);
    // sum -= nums[left];
    // left++;
    // }

    // right++;
    // }

    // return (minLen != Integer.MAX_VALUE) ? minLen : -1;
    // }

    // Approach-ii -> Use Monotonic Deque , which store indices of increasing prefix
    // Sum , if dip occurs remove that index
    private static int shortestSubarray(int[] nums, int k) {
        if (nums[0] >= k)
            return 1;

        int n = nums.length;

        // // try calc prefix sum first
        long[] prefSum = new long[n]; // use long for larger sums

        // using a monotonic deque to store increasing prefix sum indices
        Deque<Integer> dq = new ArrayDeque<>();

        int j = 0;
        int minLen = Integer.MAX_VALUE;
        while (j < n) {
            if (j == 0) {
                prefSum[j] = nums[j];
            } else {
                prefSum[j] = prefSum[j - 1] + nums[j];
            }

            // check if this value is more than k
            if (prefSum[j] >= k) {
                minLen = Math.min(minLen, j + 1);
            }

            // check if their is a dip
            while (!dq.isEmpty() && prefSum[j] <= prefSum[dq.getLast()]) {
                dq.removeLast();
            }

            // shrink the window , jab tak condition satisfy hora hai
            while (!dq.isEmpty() && prefSum[j] - prefSum[dq.getFirst()] >= k) {
                minLen = Math.min(minLen, j - dq.getFirst());
                dq.removeFirst();
            }

            dq.addLast(j);
            j++;
        }

        return minLen == Integer.MAX_VALUE ? -1 : minLen;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        int[] nums = new int[n];

        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }

        int k = sc.nextInt();

        System.out.println(shortestSubarray(nums, k));

        sc.close();
    }
}


```

---
