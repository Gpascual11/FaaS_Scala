package MapReduce;

import java.util.Map;

/**
 * TestMapReduce
 */
public class TestMapReduce {

    /**
     * Reduce function
     * @param results Array of maps
     * @param targetWord Word to count
     * @return Total count of the target word
     */
    public static int reduce(Map<String, Integer>[] results, String targetWord) {
        int total = 0;

        for (Map<String, Integer> result : results) {
            total += result.getOrDefault(targetWord, 0);
        }

        return total;
    }

    public static void main(String[] args) {
        String[] filePaths = {
                "source/Files/pg100.txt",
                "source/Files/pg145.txt",
                "source/Files/pg394.txt",
                "source/Files/pg1342.txt",
                "source/Files/pg1513.txt",
                "source/Files/pg2160.txt",
                "source/Files/pg2641.txt",
                "source/Files/pg2701.txt",
                "source/Files/pg4085.txt",
                "source/Files/pg5197.txt",
                "source/Files/pg6761.txt",
                "source/Files/pg16389.txt",
                "source/Files/pg37106.txt",
                "source/Files/pg67979.txt"
        };

        for (String filePath : filePaths) {
            String textFromFile = MapReduceUtil.readTextFromFile(filePath);

            Map<String, Integer> wordCountResult = WordCount.wordCountMap(textFromFile);

            Map<String, Integer> countWordsResult = CountWords.countWordsMap(textFromFile);
            System.out.println("CountWords per a " + filePath + ": " + countWordsResult);

            String targetWord = "mushroom";
            int totalWordCount = TestMapReduce.reduce(new Map[]{wordCountResult, countWordsResult}, targetWord);
            System.out.println("Total of the word '" + targetWord + "' for " + filePath + ": " + totalWordCount);
        }
    }
}
