// 1h) Write a JAVA Program to check whether the permutation of a string forms a palindrome
/*
AlphaCipher is a string formed from another string by rearranging its letters

You are given a string S.
Your task is to check, can any one of the AlphaCipher is a palindrome or not.

Input Format:
-------------
A string S

Output Format:
--------------
Print a boolean value.


Sample Input-1:
---------------
carrace

Sample Output-1:
----------------
true


Sample Input-2:
---------------
code

Sample Output-2:
----------------
false
 */


import java.util.*;
public class PalindromePermutation {
    // Approach:-iii) Using bitwise operations 
    // T.C:- O(N) , S.C:- O(1)
    private static boolean isPalindromicParitionThere(String s){
        int n = s.length();

        int bitmask = 0;

        for(int i = 0 ; i < n ; i++){
            char ch = s.charAt(i);
            int idx = ch - 'a';
            bitmask ^=  (1 << idx);
        }

        return (bitmask == 0 || (bitmask & (bitmask - 1)) == 0);
    }
    
    

    public static void main(String[] args) {
        String s;
        try (Scanner sc = new Scanner(System.in)) {
            s = sc.next();
        }
        System.out.println(isPalindromicParitionThere(s));
    }
}

/* Approach:-i) Brute force with backtracking , find all perms
import java.util.*;
public class PalindromePermutation {
    static List<String> res;
    
    
    private static void swap(StringBuilder sb, int i,int j){
        char temp = sb.charAt(i);
        sb.setCharAt(i, sb.charAt(j));
        sb.setCharAt(j, temp);
    }
    private static void getAllPerms(int idx,StringBuilder sb,int n){
        if(idx == n){
            res.add(sb.toString());
            return;
        }
        for(int i = idx ; i < n ; i++){
            swap(sb,i,idx);
            getAllPerms(idx+1, sb, n);
            swap(sb,i,idx);
        }
    }

    private static boolean isPalindromic(String sb,int n){
        int i = 0 , j = n - 1;
        while(i < j){
            if(sb.charAt(i) != sb.charAt(j)) return false;
            i++;
            j--;
        }

        return true;
    }

    private static boolean check(String s,int n){
        getAllPerms(0,new StringBuilder(s),n);

        for(String str : res){
            if(isPalindromic(str, n)) return true;
        }

        return false;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        String s = sc.next();
        res = new ArrayList<>();

        System.out.println(check(s,s.length()));
        
        
        sc.close();
    }
}
 */

 /* 
Method-2 Optimized backtracking with early stopping
import java.util.*;
public class Palindromic_Permutation {
    private static boolean found = false;
    
    private static void swap(StringBuilder sb, int i,int j){
        char temp = sb.charAt(i);
        sb.setCharAt(i, sb.charAt(j));
        sb.setCharAt(j, temp);
    }
    private static void getAllPerms(int idx,StringBuilder sb,int n){
        if(idx == n){
            if(isPalindromic(sb.toString(), n)){
                found = true;
            }
            
            return;
        }
           
        for(int i = idx ; i < n ; i++){
            swap(sb,i,idx);
            getAllPerms(idx+1, sb, n);
            swap(sb,i,idx);
        }
    }

    private static boolean isPalindromic(String sb,int n){
        int i = 0 , j = n - 1;
        while(i < j){
            if(sb.charAt(i) != sb.charAt(j)) return false;
            i++;
            j--;
        }

        return true;
    }

    private static boolean check(String s,int n){
        getAllPerms(0,new StringBuilder(s),n);

       

        return found;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        String s = sc.next();
        
        // System.out.println(isPalindromicParitionThere(s));

        System.out.println(check(s,s.length()));
        
        
        sc.close();
    }
}
*/