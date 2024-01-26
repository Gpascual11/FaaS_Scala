package MapReduce;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for counting words in a text.
 */
public class CountWords {

    /**
     * Counts the number of words in the given text.
     *
     * @param text The input text.
     * @return The count of words in the text.
     */
    public static int countWords(String text) {
        String[] words = text.split("\\s+");
        return words.length;
    }

    /**
     * Map function for counting words.
     *
     * @param text The input text.
     * @return A map containing the word count with the key "count".
     */
    public static Map<String, Integer> countWordsMap(String text) {
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("count", countWords(text));
        return resultMap;
    }
}
