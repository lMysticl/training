package two_pointers;

import java.util.*;

/*LeetCode – Median of Two Sorted Arrays (Java)

There are two sorted arrays A and B of size m and n respectively. Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).

Java Solution 1

This problem can be converted to the problem of finding kth element, k is (A's length + B' Length)/2.

If any of the two arrays is empty, then the kth element is the non-empty array's kth element. If k == 0, the kth element is the first element of A or B.

For normal cases(all other cases), we need to move the pointer at the pace of half of the array size to get log(n) time.*/
public class WordLadder_4_1 {

    public static void main(String[] args) {
        Solution solution = new Solution();
        Set<String> stringSet = new HashSet<String>();
        stringSet.add("hot");
        stringSet.add("dot");
        stringSet.add("dog");
        stringSet.add("lot");
        stringSet.add("log");
        System.out.println(solution.findLadders("hit", "cog", stringSet));

    }

    static class WordNode {
        String word;
        int numSteps;
        WordNode pre;

        WordNode(String word, int numSteps, WordNode pre) {
            this.word = word;
            this.numSteps = numSteps;
            this.pre = pre;
        }
    }

    public static class Solution {
        List<List<String>> findLadders(String start, String end, Set<String> dict) {
            List<List<String>> result = new ArrayList<List<String>>();

            LinkedList<WordNode> queue = new LinkedList<WordNode>();
            queue.add(new WordNode(start, 1, null));

            dict.add(end);

            int minStep = 0;

            HashSet<String> visited = new HashSet<String>();
            HashSet<String> unvisited = new HashSet<String>();
            unvisited.addAll(dict);

            int preNumSteps = 0;

            while (!queue.isEmpty()) {
                WordNode top = queue.remove();
                String word = top.word;
                int currNumSteps = top.numSteps;

                if (word.equals(end)) {
                    if (minStep == 0) {
                        minStep = top.numSteps;
                    }

                    if (top.numSteps == minStep && minStep != 0) {
                        //nothing
                        ArrayList<String> t = new ArrayList<String>();
                        t.add(top.word);
                        while (top.pre != null) {
                            t.add(0, top.pre.word);
                            top = top.pre;
                        }
                        result.add(t);
                        continue;
                    }

                }

                if (preNumSteps < currNumSteps) {
                    unvisited.removeAll(visited);
                }

                preNumSteps = currNumSteps;

                char[] arr = word.toCharArray();
                for (int i = 0; i < arr.length; i++) {
                    for (char c = 'a'; c <= 'z'; c++) {
                        char temp = arr[i];
                        if (arr[i] != c) {
                            arr[i] = c;
                        }

                        String newWord = new String(arr);
                        if (unvisited.contains(newWord)) {
                            queue.add(new WordNode(newWord, top.numSteps + 1, top));
                            visited.add(newWord);
                        }

                        arr[i] = temp;
                    }
                }


            }

            return result;
        }
    }
}
