/* 1i) Index Pairs
Write a JAVA Program to return all index pairs [i,j] given a text string and words (a
list), so that the substring text[i]…text[j] is in the list of words
Given a text string and words (a list of strings), return all index pairs [i, j] so that the substring
text[i]...text[j] is in the list of words.
Note:
• All strings contains only lowercase English letters.
• It's guaranteed that all strings in words are different.
•
Return the pairs [i,j] in sorted order (i.e. sort them by their first coordinate in case of ties sort them
by their second coordinate).

Example 1:
Input: text = "thestoryofleetcodeandme", words =
["story","fleet","leetcode"] Output: [[3,7],[9,13],[10,17]]

Example 2:
Input: text = "ababa", words = ["aba","ab"]
Output: [[0,1],[0,2],[2,3],[2,4]]

Explanation:
Notice that matches can overlap, see "aba" is found in [0,2] and [2,4].
 */

import java.util.Scanner;

class TrieNode {
    TrieNode[] children;
    boolean eow;

    public TrieNode() {
        this.children = new TrieNode[26];
        this.eow = false;
    }
}

class Trie {
    private final TrieNode root;

    public Trie() {
        this.root = new TrieNode();
    }

    public TrieNode getRoot() {
        return root;
    }

    public void insert(String word) {
        TrieNode crawl = root;

        for (char ch : word.toCharArray()) {
            int idx = ch - 'a';

            if (crawl.children[idx] == null) {
                crawl.children[idx] = new TrieNode();
            }

            crawl = crawl.children[idx];
        }

        crawl.eow = true;

    }

}

public class IndexPairs {

    private static void printPairs(String[] words, String text) {

        // create a trie and insert the words
        Trie trie = new Trie();
        for (String word : words) {
            trie.insert(word);
        }

        // now travrese thorugh the text
        int n = text.length();
        
        int i = 0;
        while (i < n) {
            TrieNode crawl = trie.getRoot();

            int idx = text.charAt(i) - 'a';

            int j = i;
            if (crawl.children[idx] != null) {
                // start traversing
                while (j < n  && crawl.children[text.charAt(j) - 'a'] != null) {
                    crawl = crawl.children[text.charAt(j) - 'a'];
                    if (crawl.eow) { // check if this is end of word
                        System.out.println(i + " " + j);
                    }
                    j++;
                }

            }

            i++;

        }

    }

    public static void main(String[] args) {
        try(Scanner sc = new Scanner(System.in)){

            String text = sc.nextLine();
    
            String[] words = sc.nextLine().split(" ");
    
            printPairs(words, text);

        }catch(Exception e){
            System.out.println(e.getMessage());
        }


    }
}