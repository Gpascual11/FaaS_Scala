package MapReduce;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class to count the number of words in a text
 * WordCount class
 */
public class WordCount {


    /**
     * wordCountMap method counts the number of words in a text
     * @param text the text to count the words
     * @return a map with the words and the number of times they appear in the text
     */
    public static Map<String, Integer> wordCountMap(String text) {
        Map<String, Integer> wordCount = new HashMap<>();
        String[] words = text.split("\\s+");

        for (String word : words) {
            word = word.toLowerCase().replaceAll("[^a-zA-Z]", "");
            wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
        }

        return wordCount;
    }
}
