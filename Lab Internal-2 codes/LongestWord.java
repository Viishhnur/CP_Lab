/* Syllabus Program 
 Leetcode - 720 Longest Word in Dictionary 
Given an array of strings words representing an English Dictionary, return the longest word in words that can be built one character at a time by other 
words in words.

If there is more than one possible answer, return the longest word with the smallest lexicographical order. If there is no answer, return the empty string.

Note that the word should be built from left to right with each additional character being added to the end of a previous word. 

 

Example 1:

Input: words = ["w","wo","wor","worl","world"]
Output: "world"
Explanation: The word "world" can be built one character at a time by "w", "wo", "wor", and "worl".

Example 2:

Input: words = ["a","banana","app","appl","ap","apply","apple"]
Output: "apple"
Explanation: Both "apply" and "apple" can be built from other words in the dictionary. However, "apple" is lexicographically smaller than "apply".

 

Constraints:

    1 <= words.length <= 1000
    1 <= words[i].length <= 30
    words[i] consists of lowercase English letters.


 */
class TrieNode{
    boolean eow;
    TrieNode[] children;

    public TrieNode(){
        this.eow = false;
        this.children = new TrieNode[26];
    }
}

class Trie{
    private final TrieNode root;

    public Trie(){
        this.root = new TrieNode();
    }

    public void insert(String word){
        TrieNode crawl = root;

        for(char ch : word.toCharArray()){
            int idx = ch - 'a';

            if(crawl.children[idx] == null){
                crawl.children[idx] = new TrieNode();
            }

            crawl = crawl.children[idx];
        }

        crawl.eow = true;
    }

    public boolean search(String word){
        TrieNode crawl = root;

        for(char ch : word.toCharArray()){
            int idx = ch - 'a';

            if(crawl.children[idx] == null){
                return false;
            }

            crawl = crawl.children[idx];
        }

        return crawl.eow;
    }

}

public class LongestWord {
    // A word is valid if every prefix of it is also in the list.
    public String longestWord(String[] words) {
        Trie trie = new Trie();

        for(String word : words){
            trie.insert(word);
        }
        int maxLen = 0;
        String longestWord = "";

        for(int i = 0 ; i < words.length ; i++){
            String word = words[i];
            // get all the prefixes of this word
            boolean isPossible = true;
            for(int j = 0 ; j < word.length() ; j++){
                String sub = word.substring(0,j+1);
                if(!trie.search(sub)){
                    isPossible = false;
                    break;
                }
            }

            if(isPossible){
                if(word.length() > maxLen){
                    maxLen = word.length();
                    longestWord = word;
                }else if(word.length() == maxLen && word.compareTo(longestWord) < 0){
                    longestWord = word;
                }
            }

        }

        return longestWord;
    }
}