package TestJUnit;

import Components.Controller;
import MapReduce.CountWords;
import MapReduce.MapReduceUtil;
import MapReduce.WordCount;
import PolicyManager.*;
import org.junit.AfterClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class TestMapReducePolicyJUnit {

    private Controller<String, String> controller;
    private List<String> functionsToAssign = List.of("wordCount", "wordCount", "wordCount","wordCount","wordCount","wordCount","wordCount","wordCount","wordCount","wordCount","wordCount","wordCount","wordCount","wordCount");
    private List<String> pathsToAssign = List.of("source/Files/pg100.txt", "source/Files/pg145.txt", "source/Files/pg394.txt", "source/Files/pg1342.txt", "source/Files/pg1513.txt", "source/Files/pg2160.txt", "source/Files/pg2641.txt", "source/Files/pg2701.txt", "source/Files/pg4085.txt", "source/Files/pg5197.txt", "source/Files/pg6761.txt", "source/Files/pg16389.txt", "source/Files/pg37106.txt", "source/Files/pg67979.txt");

    private static long timeElapsedRoundRobin;
    private static long timeElapsedUniformGroup3;
    private static long timeElapsedUniformGroup5;
    private static long timeElapsedBigGroup3;
    private static long timeElapsedBigGroup5;
    private static long timeElapsedGreedyGroup;

    private static Map<String, Long> executionTimeMap = new HashMap<>();

    @AfterClass
    public static void tearDownAll() {
        // Store the execution times in an array
        long[] timeElapsedArray = {
                timeElapsedRoundRobin,
                timeElapsedUniformGroup3,
                timeElapsedUniformGroup5,
                timeElapsedBigGroup3,
                timeElapsedBigGroup5,
                timeElapsedGreedyGroup
        };

        // Sort the array in ascending order
        Arrays.sort(timeElapsedArray);

        // Print the ordered execution times along with their corresponding variables
        System.out.println("Ordered execution times (from least to most):");
        for (long time : timeElapsedArray) {
            String variableName = getKeyByValue(executionTimeMap, time);
            System.out.println(variableName + ": " + time + " milliseconds");
        }
    }

    // Helper method to get the key associated with a specific value in a map
    private static <K, V> K getKeyByValue(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Test
    public void testRRWordCount() {
        controller = new Controller<>(6, 5, 2048, 1024);
        testRRWordCount("how", new RoundRobinPolicy());
        controller.shutdown();
    }

    @Test
    public void testUG3WordCount() {
        controller = new Controller<>(6, 5, 2048, 1024);
        testUG3WordCount("how", new UniformGroupPolicy(3));
        controller.shutdown();
    }

    @Test
    public void testUG5WordCount() {
        controller = new Controller<>(6, 5, 2048, 1024);
        testUG5WordCount("how", new UniformGroupPolicy(5));
        controller.shutdown();
    }

    @Test
    public void testBG3WordCount() {
        controller = new Controller<>(6, 5, 2048, 1024);
        testBG3WordCount("how", new BigGroupPolicy(3));
        controller.shutdown();
    }

    @Test
    public void testBG5WordCount() {
        controller = new Controller<>(6, 5, 2048, 1024);
        testBG5WordCount("how", new BigGroupPolicy(5));
        controller.shutdown();
    }

    @Test
    public void testGGWordCount() {
        controller = new Controller<>(6, 5, 2048, 1024);
        testGGWordCount("how", new GreedyGroupPolicy());
        controller.shutdown();
    }



    private void testRRWordCount(String targetWord, Policy policy) {

        Function<String, String> wordCount = path -> {
            String textFromFile = MapReduceUtil.readTextFromFile(path);

            Map<String, Integer> wordCountResult = WordCount.wordCountMap(textFromFile);

            Map<String, Integer> countWordsResult = CountWords.countWordsMap(textFromFile);

            int totalWordCount = reduce(new Map[]{wordCountResult, countWordsResult}, targetWord);

            return "Target word: " + targetWord + " appears: " + totalWordCount + " times, and " + path + " has : " + countWordsResult + " words";
        };

        controller.registerAction2("wordCount", wordCount, 150);

        System.out.println("Executing policy: " + controller.getPolicyManager());

        long startTime = System.currentTimeMillis();
        controller.invokeFunctionsPolicyManager(policy, functionsToAssign, pathsToAssign);
        long endTime = System.currentTimeMillis();
        timeElapsedRoundRobin = endTime - startTime;

        String variableName = "RoundRobin";
        executionTimeMap.put(variableName , timeElapsedRoundRobin);

    }

    private void testUG3WordCount(String targetWord, Policy policy) {

        Function<String, String> wordCount = path -> {
            String textFromFile = MapReduceUtil.readTextFromFile(path);

            Map<String, Integer> wordCountResult = WordCount.wordCountMap(textFromFile);

            Map<String, Integer> countWordsResult = CountWords.countWordsMap(textFromFile);

            int totalWordCount = reduce(new Map[]{wordCountResult, countWordsResult}, targetWord);

            return "Target word: " + targetWord + " appears: " + totalWordCount + " times, and " + path + " has : " + countWordsResult + " words";
        };

        controller.registerAction2("wordCount", wordCount, 150);

        System.out.println("Executing policy: " + controller.getPolicyManager());

        long startTime = System.currentTimeMillis();
        controller.invokeFunctionsPolicyManager(policy, functionsToAssign, pathsToAssign);
        long endTime = System.currentTimeMillis();
        timeElapsedUniformGroup3 = endTime - startTime;

        String variableName = "UniformGroup3";
        executionTimeMap.put(variableName , timeElapsedUniformGroup3);

    }

    private void testUG5WordCount(String targetWord, Policy policy) {

        Function<String, String> wordCount = path -> {
            String textFromFile = MapReduceUtil.readTextFromFile(path);

            Map<String, Integer> wordCountResult = WordCount.wordCountMap(textFromFile);

            Map<String, Integer> countWordsResult = CountWords.countWordsMap(textFromFile);

            int totalWordCount = reduce(new Map[]{wordCountResult, countWordsResult}, targetWord);

            return "Target word: " + targetWord + " appears: " + totalWordCount + " times, and " + path + " has : " + countWordsResult + " words";
        };

        controller.registerAction2("wordCount", wordCount, 150);

        System.out.println("Executing policy: " + controller.getPolicyManager());

        long startTime = System.currentTimeMillis();
        controller.invokeFunctionsPolicyManager(policy, functionsToAssign, pathsToAssign);
        long endTime = System.currentTimeMillis();
        timeElapsedUniformGroup5 = endTime - startTime;

        String variableName = "UniformGroup5";
        executionTimeMap.put(variableName , timeElapsedUniformGroup5);

    }

    private void testBG3WordCount(String targetWord, Policy policy) {

        Function<String, String> wordCount = path -> {
            String textFromFile = MapReduceUtil.readTextFromFile(path);

            Map<String, Integer> wordCountResult = WordCount.wordCountMap(textFromFile);

            Map<String, Integer> countWordsResult = CountWords.countWordsMap(textFromFile);

            int totalWordCount = reduce(new Map[]{wordCountResult, countWordsResult}, targetWord);

            return "Target word: " + targetWord + " appears: " + totalWordCount + " times, and " + path + " has : " + countWordsResult + " words";
        };

        controller.registerAction2("wordCount", wordCount, 150);

        System.out.println("Executing policy: " + controller.getPolicyManager());

        long startTime = System.currentTimeMillis();
        controller.invokeFunctionsPolicyManager(policy, functionsToAssign, pathsToAssign);
        long endTime = System.currentTimeMillis();
        timeElapsedBigGroup3 = endTime - startTime;

        String variableName = "BigGroup3";
        executionTimeMap.put(variableName , timeElapsedBigGroup3);

    }

    private void testBG5WordCount(String targetWord, Policy policy) {

        Function<String, String> wordCount = path -> {
            String textFromFile = MapReduceUtil.readTextFromFile(path);

            Map<String, Integer> wordCountResult = WordCount.wordCountMap(textFromFile);

            Map<String, Integer> countWordsResult = CountWords.countWordsMap(textFromFile);

            int totalWordCount = reduce(new Map[]{wordCountResult, countWordsResult}, targetWord);

            return "Target word: " + targetWord + " appears: " + totalWordCount + " times, and " + path + " has : " + countWordsResult + " words";
        };

        controller.registerAction2("wordCount", wordCount, 150);

        System.out.println("Executing policy: " + controller.getPolicyManager());

        long startTime = System.currentTimeMillis();
        controller.invokeFunctionsPolicyManager(policy, functionsToAssign, pathsToAssign);
        long endTime = System.currentTimeMillis();
        timeElapsedBigGroup5 = endTime - startTime;

        String variableName = "BigGroup5";
        executionTimeMap.put(variableName , timeElapsedBigGroup5);

    }

    private void testGGWordCount(String targetWord, Policy policy) {

        Function<String, String> wordCount = path -> {
            String textFromFile = MapReduceUtil.readTextFromFile(path);

            Map<String, Integer> wordCountResult = WordCount.wordCountMap(textFromFile);

            Map<String, Integer> countWordsResult = CountWords.countWordsMap(textFromFile);

            int totalWordCount = reduce(new Map[]{wordCountResult, countWordsResult}, targetWord);

            return "Target word: " + targetWord + " appears: " + totalWordCount + " times, and " + path + " has : " + countWordsResult + " words";
        };

        controller.registerAction2("wordCount", wordCount, 150);

        System.out.println("Executing policy: " + controller.getPolicyManager());

        long startTime = System.currentTimeMillis();
        controller.invokeFunctionsPolicyManager(policy, functionsToAssign, pathsToAssign);
        long endTime = System.currentTimeMillis();
        timeElapsedGreedyGroup = endTime - startTime;

        String variableName = "GreedyGroup";
        executionTimeMap.put(variableName , timeElapsedGreedyGroup);

    }

    public static int reduce(Map<String, Integer>[] results, String targetWord) {
        int total = 0;

        for (Map<String, Integer> result : results) {
            total += result.getOrDefault(targetWord, 0);
        }

        return total;
    }

}
