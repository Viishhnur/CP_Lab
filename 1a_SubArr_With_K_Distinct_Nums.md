# 📊 1a - Subarrays with K Different Integers – Java Solution

## ✅ Problem Statement

Given an integer array `nums` and an integer `k`, return the **number of good subarrays** of `nums`.

A **good subarray** is a contiguous subarray of `nums` where the number of **different integers is exactly `k`**.

### 🔹 Example 1:

```
Input: nums = [1,2,1,2,3], k = 2
Output: 7

Explanation:
Good subarrays: [1,2], [2,1], [1,2], [2,3], [1,2,1], [2,1,2], [1,2,1,2]
```

### 🔹 Example 2:

```
Input: nums = [1,2,1,3,4], k = 3
Output: 3

Explanation:
Good subarrays: [1,2,1,3], [2,1,3], [1,3,4]
```

## 📌 Constraints

- `1 <= nums.length <= 2 * 10^4`
- `1 <= nums[i], k <= nums.length`
---

## 🧠 Approach

We use the **"at most K distinct integers"** trick:

- The number of subarrays with **exactly K** distinct integers is:
  ```
  atMostK(k) - atMostK(k - 1)
  ```

We use a sliding window and a frequency map to compute the number of subarrays with at most K distinct integers.

---

## 🔧 Java Code

```java

import java.util.*;
public class KDistinctElementsSubArray {
    // Count subarrays with at most K distinct elements
    private static int countAtmost(int[] nums, int k) {
        if (k <= 0)
            return 0;
        int n = nums.length;
        int ans = 0;
        HashMap<Integer, Integer> freq = new HashMap<>();

        int left = 0, right = 0;

        while (right < n) {
            freq.put(nums[right], freq.getOrDefault(nums[right], 0) + 1);

            while (freq.size() > k) {
                freq.put(nums[left], freq.get(nums[left]) - 1);
                if (freq.get(nums[left]) == 0) {
                    freq.remove(nums[left]);
                }
                left++;
            }

            ans += right - left + 1; // counts the sub arrays where distinct count <= k
            right++;
        }

        return ans;
    }

    private static int subarraysWithKDistinct(int[] nums, int k) {
        return countAtmost(nums, k) - countAtmost(nums, k - 1);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        int[] nums = new int[n];

        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }

        int k = sc.nextInt();

        System.out.println(subarraysWithKDistinct(nums, k));
        sc.close();
    }
}
```

---

## ⏱ Time and Space Complexity

| Complexity       | Value                  |
|------------------|-------------------------|
| Time Complexity  | O(n) average case       |
| Space Complexity | O(k) for HashMap        |

Where `n` is the length of the input array and `k` is the number of distinct integers.

---
