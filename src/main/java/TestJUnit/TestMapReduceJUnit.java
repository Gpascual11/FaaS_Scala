package TestJUnit;

import Components.Controller;
import MapReduce.CountWords;
import MapReduce.MapReduceUtil;
import MapReduce.WordCount;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Function;

public class TestMapReduceJUnit {

    private Controller<String, String> controller;

    @Before
    public void setUp() {
        controller = new Controller<>(2, 3, 500, 350);
    }

    @After
    public void tearDown() {
        controller.shutdown();
    }

    @Test
    public void testWordCount1() {
        testWordCount1("apple");
    }

    @Test
    public void testWordCount2() {
        testWordCount2("mushroom");
    }
    @Test
    public void testWordCount3() {
        testWordCount3("hello");
    }
    @Test
    public void testWordCount4() {
        testWordCount4("morning");
    }
    @Test
    public void testWordCount5() {
        testWordCount5("how");
    }
    @Test
    public void testWordCount6() {
        testWordCount6("mother");
    }
    @Test
    public void testWordCount7() {
        testWordCount7("word");
    }

    // Add more test methods as needed with different targetWord values

    private void testWordCount1(String targetWord) {
        Function<String, String> wordCount = path -> {
            String textFromFile = MapReduceUtil.readTextFromFile(path);

            Map<String, Integer> wordCountResult = WordCount.wordCountMap(textFromFile);

            Map<String, Integer> countWordsResult = CountWords.countWordsMap(textFromFile);

            int totalWordCount = reduce(new Map[]{wordCountResult, countWordsResult}, targetWord);

            return "Target word: " + targetWord + " appears: " + totalWordCount + " times, and " + path + " has : " + countWordsResult + " words";
        };

        controller.registerAction2("wordCount", wordCount, 150);

        Future<String> future = controller.invokeAsync("wordCount", "source/Files/pg100.txt");
        Future<String> future1 = controller.invokeAsync("wordCount", "source/Files/pg145.txt");
        Future<String> future2 = controller.invokeAsync("wordCount", "source/Files/pg394.txt");
        Future<String> future3 = controller.invokeAsync("wordCount", "source/Files/pg1342.txt");
        Future<String> future4 = controller.invokeAsync("wordCount", "source/Files/pg1513.txt");
        Future<String> future5 = controller.invokeAsync("wordCount", "source/Files/pg2160.txt");
        Future<String> future6 = controller.invokeAsync("wordCount", "source/Files/pg2641.txt");
        Future<String> future7 = controller.invokeAsync("wordCount", "source/Files/pg2701.txt");
        Future<String> future8 = controller.invokeAsync("wordCount", "source/Files/pg4085.txt");
        Future<String> future9 = controller.invokeAsync("wordCount", "source/Files/pg5197.txt");
        Future<String> future10 = controller.invokeAsync("wordCount", "source/Files/pg6761.txt");
        Future<String> future11 = controller.invokeAsync("wordCount", "source/Files/pg16389.txt");
        Future<String> future12 = controller.invokeAsync("wordCount", "source/Files/pg37106.txt");
        Future<String> future13 = controller.invokeAsync("wordCount", "source/Files/pg67979.txt");

        try {
            // Get results from futures
            String result = future.get();
            String result1 = future1.get();
            String result2 = future2.get();
            String result3 = future3.get();
            String result4 = future4.get();
            String result5 = future5.get();
            String result6 = future6.get();
            String result7 = future7.get();
            String result8 = future8.get();
            String result9 = future9.get();
            String result10 = future10.get();
            String result11 = future11.get();
            String result12 = future12.get();
            String result13 = future13.get();

            // Print more results as needed

            // Perform assertions
            Assertions.assertEquals("Target word: apple appears: 10 times, and source/Files/pg100.txt has : {count=966501} words", result);
            Assertions.assertEquals("Target word: apple appears: 2 times, and source/Files/pg145.txt has : {count=319402} words", result1);
            Assertions.assertEquals("Target word: apple appears: 1 times, and source/Files/pg394.txt has : {count=74011} words", result2);
            Assertions.assertEquals("Target word: apple appears: 0 times, and source/Files/pg1342.txt has : {count=130408} words", result3);
            Assertions.assertEquals("Target word: apple appears: 0 times, and source/Files/pg1513.txt has : {count=28987} words", result4);
            Assertions.assertEquals("Target word: apple appears: 0 times, and source/Files/pg2160.txt has : {count=151409} words", result5);
            Assertions.assertEquals("Target word: apple appears: 0 times, and source/Files/pg2641.txt has : {count=69636} words", result6);
            Assertions.assertEquals("Target word: apple appears: 3 times, and source/Files/pg2701.txt has : {count=215831} words", result7);
            Assertions.assertEquals("Target word: apple appears: 1 times, and source/Files/pg4085.txt has : {count=193626} words", result8);
            Assertions.assertEquals("Target word: apple appears: 0 times, and source/Files/pg5197.txt has : {count=222620} words", result9);
            Assertions.assertEquals("Target word: apple appears: 0 times, and source/Files/pg6761.txt has : {count=164710} words", result10);
            Assertions.assertEquals("Target word: apple appears: 0 times, and source/Files/pg16389.txt has : {count=81291} words", result11);
            Assertions.assertEquals("Target word: apple appears: 1 times, and source/Files/pg37106.txt has : {count=195624} words", result12);
            Assertions.assertEquals("Target word: apple appears: 0 times, and source/Files/pg67979.txt has : {count=71331} words", result13);
            // Add more assertions as needed

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
    private void testWordCount2(String targetWord) {
        Function<String, String> wordCount = path -> {
            String textFromFile = MapReduceUtil.readTextFromFile(path);

            Map<String, Integer> wordCountResult = WordCount.wordCountMap(textFromFile);

            Map<String, Integer> countWordsResult = CountWords.countWordsMap(textFromFile);

            int totalWordCount = reduce(new Map[]{wordCountResult, countWordsResult}, targetWord);

            return "Target word: " + targetWord + " appears: " + totalWordCount + " times, and " + path + " has : " + countWordsResult + " words";
        };

        controller.registerAction2("wordCount", wordCount, 150);

        Future<String> future = controller.invokeAsync("wordCount", "source/Files/pg100.txt");
        Future<String> future1 = controller.invokeAsync("wordCount", "source/Files/pg145.txt");
        Future<String> future2 = controller.invokeAsync("wordCount", "source/Files/pg394.txt");
        Future<String> future3 = controller.invokeAsync("wordCount", "source/Files/pg1342.txt");
        Future<String> future4 = controller.invokeAsync("wordCount", "source/Files/pg1513.txt");
        Future<String> future5 = controller.invokeAsync("wordCount", "source/Files/pg2160.txt");
        Future<String> future6 = controller.invokeAsync("wordCount", "source/Files/pg2641.txt");
        Future<String> future7 = controller.invokeAsync("wordCount", "source/Files/pg2701.txt");
        Future<String> future8 = controller.invokeAsync("wordCount", "source/Files/pg4085.txt");
        Future<String> future9 = controller.invokeAsync("wordCount", "source/Files/pg5197.txt");
        Future<String> future10 = controller.invokeAsync("wordCount", "source/Files/pg6761.txt");
        Future<String> future11 = controller.invokeAsync("wordCount", "source/Files/pg16389.txt");
        Future<String> future12 = controller.invokeAsync("wordCount", "source/Files/pg37106.txt");
        Future<String> future13 = controller.invokeAsync("wordCount", "source/Files/pg67979.txt");

        try {
            // Get results from futures
            String result = future.get();
            String result1 = future1.get();
            String result2 = future2.get();
            String result3 = future3.get();
            String result4 = future4.get();
            String result5 = future5.get();
            String result6 = future6.get();
            String result7 = future7.get();
            String result8 = future8.get();
            String result9 = future9.get();
            String result10 = future10.get();
            String result11 = future11.get();
            String result12 = future12.get();
            String result13 = future13.get();

            // Print more results as needed

            // Perform assertions
            Assertions.assertEquals("Target word: mushroom appears: 0 times, and source/Files/pg100.txt has : {count=966501} words", result);
            Assertions.assertEquals("Target word: mushroom appears: 1 times, and source/Files/pg145.txt has : {count=319402} words", result1);
            Assertions.assertEquals("Target word: mushroom appears: 0 times, and source/Files/pg394.txt has : {count=74011} words", result2);
            Assertions.assertEquals("Target word: mushroom appears: 0 times, and source/Files/pg1342.txt has : {count=130408} words", result3);
            Assertions.assertEquals("Target word: mushroom appears: 0 times, and source/Files/pg1513.txt has : {count=28987} words", result4);
            Assertions.assertEquals("Target word: mushroom appears: 1 times, and source/Files/pg2160.txt has : {count=151409} words", result5);
            Assertions.assertEquals("Target word: mushroom appears: 0 times, and source/Files/pg2641.txt has : {count=69636} words", result6);
            Assertions.assertEquals("Target word: mushroom appears: 0 times, and source/Files/pg2701.txt has : {count=215831} words", result7);
            Assertions.assertEquals("Target word: mushroom appears: 0 times, and source/Files/pg4085.txt has : {count=193626} words", result8);
            Assertions.assertEquals("Target word: mushroom appears: 0 times, and source/Files/pg5197.txt has : {count=222620} words", result9);
            Assertions.assertEquals("Target word: mushroom appears: 0 times, and source/Files/pg6761.txt has : {count=164710} words", result10);
            Assertions.assertEquals("Target word: mushroom appears: 0 times, and source/Files/pg16389.txt has : {count=81291} words", result11);
            Assertions.assertEquals("Target word: mushroom appears: 1 times, and source/Files/pg37106.txt has : {count=195624} words", result12);
            Assertions.assertEquals("Target word: mushroom appears: 0 times, and source/Files/pg67979.txt has : {count=71331} words", result13);
            // Add more assertions as needed

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
    private void testWordCount3(String targetWord) {
        Function<String, String> wordCount = path -> {
            String textFromFile = MapReduceUtil.readTextFromFile(path);

            Map<String, Integer> wordCountResult = WordCount.wordCountMap(textFromFile);

            Map<String, Integer> countWordsResult = CountWords.countWordsMap(textFromFile);

            int totalWordCount = reduce(new Map[]{wordCountResult, countWordsResult}, targetWord);

            return "Target word: " + targetWord + " appears: " + totalWordCount + " times, and " + path + " has : " + countWordsResult + " words";
        };

        controller.registerAction2("wordCount", wordCount, 150);

        Future<String> future = controller.invokeAsync("wordCount", "source/Files/pg100.txt");
        Future<String> future1 = controller.invokeAsync("wordCount", "source/Files/pg145.txt");
        Future<String> future2 = controller.invokeAsync("wordCount", "source/Files/pg394.txt");
        Future<String> future3 = controller.invokeAsync("wordCount", "source/Files/pg1342.txt");
        Future<String> future4 = controller.invokeAsync("wordCount", "source/Files/pg1513.txt");
        Future<String> future5 = controller.invokeAsync("wordCount", "source/Files/pg2160.txt");
        Future<String> future6 = controller.invokeAsync("wordCount", "source/Files/pg2641.txt");
        Future<String> future7 = controller.invokeAsync("wordCount", "source/Files/pg2701.txt");
        Future<String> future8 = controller.invokeAsync("wordCount", "source/Files/pg4085.txt");
        Future<String> future9 = controller.invokeAsync("wordCount", "source/Files/pg5197.txt");
        Future<String> future10 = controller.invokeAsync("wordCount", "source/Files/pg6761.txt");
        Future<String> future11 = controller.invokeAsync("wordCount", "source/Files/pg16389.txt");
        Future<String> future12 = controller.invokeAsync("wordCount", "source/Files/pg37106.txt");
        Future<String> future13 = controller.invokeAsync("wordCount", "source/Files/pg67979.txt");

        try {
            // Get results from futures
            String result = future.get();
            String result1 = future1.get();
            String result2 = future2.get();
            String result3 = future3.get();
            String result4 = future4.get();
            String result5 = future5.get();
            String result6 = future6.get();
            String result7 = future7.get();
            String result8 = future8.get();
            String result9 = future9.get();
            String result10 = future10.get();
            String result11 = future11.get();
            String result12 = future12.get();
            String result13 = future13.get();

            // Print more results as needed

            // Perform assertions
            Assertions.assertEquals("Target word: hello appears: 0 times, and source/Files/pg100.txt has : {count=966501} words", result);
            Assertions.assertEquals("Target word: hello appears: 0 times, and source/Files/pg145.txt has : {count=319402} words", result1);
            Assertions.assertEquals("Target word: hello appears: 0 times, and source/Files/pg394.txt has : {count=74011} words", result2);
            Assertions.assertEquals("Target word: hello appears: 0 times, and source/Files/pg1342.txt has : {count=130408} words", result3);
            Assertions.assertEquals("Target word: hello appears: 0 times, and source/Files/pg1513.txt has : {count=28987} words", result4);
            Assertions.assertEquals("Target word: hello appears: 0 times, and source/Files/pg2160.txt has : {count=151409} words", result5);
            Assertions.assertEquals("Target word: hello appears: 0 times, and source/Files/pg2641.txt has : {count=69636} words", result6);
            Assertions.assertEquals("Target word: hello appears: 0 times, and source/Files/pg2701.txt has : {count=215831} words", result7);
            Assertions.assertEquals("Target word: hello appears: 0 times, and source/Files/pg4085.txt has : {count=193626} words", result8);
            Assertions.assertEquals("Target word: hello appears: 0 times, and source/Files/pg5197.txt has : {count=222620} words", result9);
            Assertions.assertEquals("Target word: hello appears: 0 times, and source/Files/pg6761.txt has : {count=164710} words", result10);
            Assertions.assertEquals("Target word: hello appears: 0 times, and source/Files/pg16389.txt has : {count=81291} words", result11);
            Assertions.assertEquals("Target word: hello appears: 0 times, and source/Files/pg37106.txt has : {count=195624} words", result12);
            Assertions.assertEquals("Target word: hello appears: 0 times, and source/Files/pg67979.txt has : {count=71331} words", result13);
            // Add more assertions as needed

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
    private void testWordCount4(String targetWord) {
        Function<String, String> wordCount = path -> {
            String textFromFile = MapReduceUtil.readTextFromFile(path);

            Map<String, Integer> wordCountResult = WordCount.wordCountMap(textFromFile);

            Map<String, Integer> countWordsResult = CountWords.countWordsMap(textFromFile);

            int totalWordCount = reduce(new Map[]{wordCountResult, countWordsResult}, targetWord);

            return "Target word: " + targetWord + " appears: " + totalWordCount + " times, and " + path + " has : " + countWordsResult + " words";
        };

        controller.registerAction2("wordCount", wordCount, 150);

        Future<String> future = controller.invokeAsync("wordCount", "source/Files/pg100.txt");
        Future<String> future1 = controller.invokeAsync("wordCount", "source/Files/pg145.txt");
        Future<String> future2 = controller.invokeAsync("wordCount", "source/Files/pg394.txt");
        Future<String> future3 = controller.invokeAsync("wordCount", "source/Files/pg1342.txt");
        Future<String> future4 = controller.invokeAsync("wordCount", "source/Files/pg1513.txt");
        Future<String> future5 = controller.invokeAsync("wordCount", "source/Files/pg2160.txt");
        Future<String> future6 = controller.invokeAsync("wordCount", "source/Files/pg2641.txt");
        Future<String> future7 = controller.invokeAsync("wordCount", "source/Files/pg2701.txt");
        Future<String> future8 = controller.invokeAsync("wordCount", "source/Files/pg4085.txt");
        Future<String> future9 = controller.invokeAsync("wordCount", "source/Files/pg5197.txt");
        Future<String> future10 = controller.invokeAsync("wordCount", "source/Files/pg6761.txt");
        Future<String> future11 = controller.invokeAsync("wordCount", "source/Files/pg16389.txt");
        Future<String> future12 = controller.invokeAsync("wordCount", "source/Files/pg37106.txt");
        Future<String> future13 = controller.invokeAsync("wordCount", "source/Files/pg67979.txt");

        try {
            // Get results from futures
            String result = future.get();
            String result1 = future1.get();
            String result2 = future2.get();
            String result3 = future3.get();
            String result4 = future4.get();
            String result5 = future5.get();
            String result6 = future6.get();
            String result7 = future7.get();
            String result8 = future8.get();
            String result9 = future9.get();
            String result10 = future10.get();
            String result11 = future11.get();
            String result12 = future12.get();
            String result13 = future13.get();

            // Print more results as needed

            // Perform assertions
            Assertions.assertEquals("Target word: morning appears: 137 times, and source/Files/pg100.txt has : {count=966501} words", result);
            Assertions.assertEquals("Target word: morning appears: 154 times, and source/Files/pg145.txt has : {count=319402} words", result1);
            Assertions.assertEquals("Target word: morning appears: 45 times, and source/Files/pg394.txt has : {count=74011} words", result2);
            Assertions.assertEquals("Target word: morning appears: 76 times, and source/Files/pg1342.txt has : {count=130408} words", result3);
            Assertions.assertEquals("Target word: morning appears: 7 times, and source/Files/pg1513.txt has : {count=28987} words", result4);
            Assertions.assertEquals("Target word: morning appears: 45 times, and source/Files/pg2160.txt has : {count=151409} words", result5);
            Assertions.assertEquals("Target word: morning appears: 29 times, and source/Files/pg2641.txt has : {count=69636} words", result6);
            Assertions.assertEquals("Target word: morning appears: 72 times, and source/Files/pg2701.txt has : {count=215831} words", result7);
            Assertions.assertEquals("Target word: morning appears: 100 times, and source/Files/pg4085.txt has : {count=193626} words", result8);
            Assertions.assertEquals("Target word: morning appears: 68 times, and source/Files/pg5197.txt has : {count=222620} words", result9);
            Assertions.assertEquals("Target word: morning appears: 40 times, and source/Files/pg6761.txt has : {count=164710} words", result10);
            Assertions.assertEquals("Target word: morning appears: 52 times, and source/Files/pg16389.txt has : {count=81291} words", result11);
            Assertions.assertEquals("Target word: morning appears: 64 times, and source/Files/pg37106.txt has : {count=195624} words", result12);
            Assertions.assertEquals("Target word: morning appears: 28 times, and source/Files/pg67979.txt has : {count=71331} words", result13);
            // Add more assertions as needed

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
    private void testWordCount5(String targetWord) {
        Function<String, String> wordCount = path -> {
            String textFromFile = MapReduceUtil.readTextFromFile(path);

            Map<String, Integer> wordCountResult = WordCount.wordCountMap(textFromFile);

            Map<String, Integer> countWordsResult = CountWords.countWordsMap(textFromFile);

            int totalWordCount = reduce(new Map[]{wordCountResult, countWordsResult}, targetWord);

            return "Target word: " + targetWord + " appears: " + totalWordCount + " times, and " + path + " has : " + countWordsResult + " words";
        };

        controller.registerAction2("wordCount", wordCount, 150);

        Future<String> future = controller.invokeAsync("wordCount", "source/Files/pg100.txt");
        Future<String> future1 = controller.invokeAsync("wordCount", "source/Files/pg145.txt");
        Future<String> future2 = controller.invokeAsync("wordCount", "source/Files/pg394.txt");
        Future<String> future3 = controller.invokeAsync("wordCount", "source/Files/pg1342.txt");
        Future<String> future4 = controller.invokeAsync("wordCount", "source/Files/pg1513.txt");
        Future<String> future5 = controller.invokeAsync("wordCount", "source/Files/pg2160.txt");
        Future<String> future6 = controller.invokeAsync("wordCount", "source/Files/pg2641.txt");
        Future<String> future7 = controller.invokeAsync("wordCount", "source/Files/pg2701.txt");
        Future<String> future8 = controller.invokeAsync("wordCount", "source/Files/pg4085.txt");
        Future<String> future9 = controller.invokeAsync("wordCount", "source/Files/pg5197.txt");
        Future<String> future10 = controller.invokeAsync("wordCount", "source/Files/pg6761.txt");
        Future<String> future11 = controller.invokeAsync("wordCount", "source/Files/pg16389.txt");
        Future<String> future12 = controller.invokeAsync("wordCount", "source/Files/pg37106.txt");
        Future<String> future13 = controller.invokeAsync("wordCount", "source/Files/pg67979.txt");

        try {
            // Get results from futures
            String result = future.get();
            String result1 = future1.get();
            String result2 = future2.get();
            String result3 = future3.get();
            String result4 = future4.get();
            String result5 = future5.get();
            String result6 = future6.get();
            String result7 = future7.get();
            String result8 = future8.get();
            String result9 = future9.get();
            String result10 = future10.get();
            String result11 = future11.get();
            String result12 = future12.get();
            String result13 = future13.get();

            // Print more results as needed

            // Perform assertions
            Assertions.assertEquals("Target word: how appears: 2306 times, and source/Files/pg100.txt has : {count=966501} words", result);
            Assertions.assertEquals("Target word: how appears: 342 times, and source/Files/pg145.txt has : {count=319402} words", result1);
            Assertions.assertEquals("Target word: how appears: 147 times, and source/Files/pg394.txt has : {count=74011} words", result2);
            Assertions.assertEquals("Target word: how appears: 237 times, and source/Files/pg1342.txt has : {count=130408} words", result3);
            Assertions.assertEquals("Target word: how appears: 62 times, and source/Files/pg1513.txt has : {count=28987} words", result4);
            Assertions.assertEquals("Target word: how appears: 98 times, and source/Files/pg2160.txt has : {count=151409} words", result5);
            Assertions.assertEquals("Target word: how appears: 172 times, and source/Files/pg2641.txt has : {count=69636} words", result6);
            Assertions.assertEquals("Target word: how appears: 241 times, and source/Files/pg2701.txt has : {count=215831} words", result7);
            Assertions.assertEquals("Target word: how appears: 125 times, and source/Files/pg4085.txt has : {count=193626} words", result8);
            Assertions.assertEquals("Target word: how appears: 157 times, and source/Files/pg5197.txt has : {count=222620} words", result9);
            Assertions.assertEquals("Target word: how appears: 88 times, and source/Files/pg6761.txt has : {count=164710} words", result10);
            Assertions.assertEquals("Target word: how appears: 177 times, and source/Files/pg16389.txt has : {count=81291} words", result11);
            Assertions.assertEquals("Target word: how appears: 361 times, and source/Files/pg37106.txt has : {count=195624} words", result12);
            Assertions.assertEquals("Target word: how appears: 72 times, and source/Files/pg67979.txt has : {count=71331} words", result13);
            // Add more assertions as needed

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
    private void testWordCount6(String targetWord) {
        Function<String, String> wordCount = path -> {
            String textFromFile = MapReduceUtil.readTextFromFile(path);

            Map<String, Integer> wordCountResult = WordCount.wordCountMap(textFromFile);

            Map<String, Integer> countWordsResult = CountWords.countWordsMap(textFromFile);

            int totalWordCount = reduce(new Map[]{wordCountResult, countWordsResult}, targetWord);

            return "Target word: " + targetWord + " appears: " + totalWordCount + " times, and " + path + " has : " + countWordsResult + " words";
        };

        controller.registerAction2("wordCount", wordCount, 150);

        Future<String> future = controller.invokeAsync("wordCount", "source/Files/pg100.txt");
        Future<String> future1 = controller.invokeAsync("wordCount", "source/Files/pg145.txt");
        Future<String> future2 = controller.invokeAsync("wordCount", "source/Files/pg394.txt");
        Future<String> future3 = controller.invokeAsync("wordCount", "source/Files/pg1342.txt");
        Future<String> future4 = controller.invokeAsync("wordCount", "source/Files/pg1513.txt");
        Future<String> future5 = controller.invokeAsync("wordCount", "source/Files/pg2160.txt");
        Future<String> future6 = controller.invokeAsync("wordCount", "source/Files/pg2641.txt");
        Future<String> future7 = controller.invokeAsync("wordCount", "source/Files/pg2701.txt");
        Future<String> future8 = controller.invokeAsync("wordCount", "source/Files/pg4085.txt");
        Future<String> future9 = controller.invokeAsync("wordCount", "source/Files/pg5197.txt");
        Future<String> future10 = controller.invokeAsync("wordCount", "source/Files/pg6761.txt");
        Future<String> future11 = controller.invokeAsync("wordCount", "source/Files/pg16389.txt");
        Future<String> future12 = controller.invokeAsync("wordCount", "source/Files/pg37106.txt");
        Future<String> future13 = controller.invokeAsync("wordCount", "source/Files/pg67979.txt");

        try {
            // Get results from futures
            String result = future.get();
            String result1 = future1.get();
            String result2 = future2.get();
            String result3 = future3.get();
            String result4 = future4.get();
            String result5 = future5.get();
            String result6 = future6.get();
            String result7 = future7.get();
            String result8 = future8.get();
            String result9 = future9.get();
            String result10 = future10.get();
            String result11 = future11.get();
            String result12 = future12.get();
            String result13 = future13.get();

            // Print more results as needed

            // Perform assertions
            Assertions.assertEquals("Target word: mother appears: 353 times, and source/Files/pg100.txt has : {count=966501} words", result);
            Assertions.assertEquals("Target word: mother appears: 135 times, and source/Files/pg145.txt has : {count=319402} words", result1);
            Assertions.assertEquals("Target word: mother appears: 54 times, and source/Files/pg394.txt has : {count=74011} words", result2);
            Assertions.assertEquals("Target word: mother appears: 108 times, and source/Files/pg1342.txt has : {count=130408} words", result3);
            Assertions.assertEquals("Target word: mother appears: 15 times, and source/Files/pg1513.txt has : {count=28987} words", result4);
            Assertions.assertEquals("Target word: mother appears: 26 times, and source/Files/pg2160.txt has : {count=151409} words", result5);
            Assertions.assertEquals("Target word: mother appears: 102 times, and source/Files/pg2641.txt has : {count=69636} words", result6);
            Assertions.assertEquals("Target word: mother appears: 12 times, and source/Files/pg2701.txt has : {count=215831} words", result7);
            Assertions.assertEquals("Target word: mother appears: 45 times, and source/Files/pg4085.txt has : {count=193626} words", result8);
            Assertions.assertEquals("Target word: mother appears: 90 times, and source/Files/pg5197.txt has : {count=222620} words", result9);
            Assertions.assertEquals("Target word: mother appears: 70 times, and source/Files/pg6761.txt has : {count=164710} words", result10);
            Assertions.assertEquals("Target word: mother appears: 41 times, and source/Files/pg16389.txt has : {count=81291} words", result11);
            Assertions.assertEquals("Target word: mother appears: 336 times, and source/Files/pg37106.txt has : {count=195624} words", result12);
            Assertions.assertEquals("Target word: mother appears: 86 times, and source/Files/pg67979.txt has : {count=71331} words", result13);
            // Add more assertions as needed

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
    private void testWordCount7(String targetWord) {
        Function<String, String> wordCount = path -> {
            String textFromFile = MapReduceUtil.readTextFromFile(path);

            Map<String, Integer> wordCountResult = WordCount.wordCountMap(textFromFile);

            Map<String, Integer> countWordsResult = CountWords.countWordsMap(textFromFile);

            int totalWordCount = reduce(new Map[]{wordCountResult, countWordsResult}, targetWord);

            return "Target word: " + targetWord + " appears: " + totalWordCount + " times, and " + path + " has : " + countWordsResult + " words";
        };

        controller.registerAction2("wordCount", wordCount, 150);

        Future<String> future = controller.invokeAsync("wordCount", "source/Files/pg100.txt");
        Future<String> future1 = controller.invokeAsync("wordCount", "source/Files/pg145.txt");
        Future<String> future2 = controller.invokeAsync("wordCount", "source/Files/pg394.txt");
        Future<String> future3 = controller.invokeAsync("wordCount", "source/Files/pg1342.txt");
        Future<String> future4 = controller.invokeAsync("wordCount", "source/Files/pg1513.txt");
        Future<String> future5 = controller.invokeAsync("wordCount", "source/Files/pg2160.txt");
        Future<String> future6 = controller.invokeAsync("wordCount", "source/Files/pg2641.txt");
        Future<String> future7 = controller.invokeAsync("wordCount", "source/Files/pg2701.txt");
        Future<String> future8 = controller.invokeAsync("wordCount", "source/Files/pg4085.txt");
        Future<String> future9 = controller.invokeAsync("wordCount", "source/Files/pg5197.txt");
        Future<String> future10 = controller.invokeAsync("wordCount", "source/Files/pg6761.txt");
        Future<String> future11 = controller.invokeAsync("wordCount", "source/Files/pg16389.txt");
        Future<String> future12 = controller.invokeAsync("wordCount", "source/Files/pg37106.txt");
        Future<String> future13 = controller.invokeAsync("wordCount", "source/Files/pg67979.txt");

        try {
            // Get results from futures
            String result = future.get();
            String result1 = future1.get();
            String result2 = future2.get();
            String result3 = future3.get();
            String result4 = future4.get();
            String result5 = future5.get();
            String result6 = future6.get();
            String result7 = future7.get();
            String result8 = future8.get();
            String result9 = future9.get();
            String result10 = future10.get();
            String result11 = future11.get();
            String result12 = future12.get();
            String result13 = future13.get();

            // Print more results as needed

            // Perform assertions
            Assertions.assertEquals("Target word: word appears: 515 times, and source/Files/pg100.txt has : {count=966501} words", result);
            Assertions.assertEquals("Target word: word appears: 86 times, and source/Files/pg145.txt has : {count=319402} words", result1);
            Assertions.assertEquals("Target word: word appears: 27 times, and source/Files/pg394.txt has : {count=74011} words", result2);
            Assertions.assertEquals("Target word: word appears: 52 times, and source/Files/pg1342.txt has : {count=130408} words", result3);
            Assertions.assertEquals("Target word: word appears: 28 times, and source/Files/pg1513.txt has : {count=28987} words", result4);
            Assertions.assertEquals("Target word: word appears: 42 times, and source/Files/pg2160.txt has : {count=151409} words", result5);
            Assertions.assertEquals("Target word: word appears: 38 times, and source/Files/pg2641.txt has : {count=69636} words", result6);
            Assertions.assertEquals("Target word: word appears: 72 times, and source/Files/pg2701.txt has : {count=215831} words", result7);
            Assertions.assertEquals("Target word: word appears: 60 times, and source/Files/pg4085.txt has : {count=193626} words", result8);
            Assertions.assertEquals("Target word: word appears: 37 times, and source/Files/pg5197.txt has : {count=222620} words", result9);
            Assertions.assertEquals("Target word: word appears: 42 times, and source/Files/pg6761.txt has : {count=164710} words", result10);
            Assertions.assertEquals("Target word: word appears: 28 times, and source/Files/pg16389.txt has : {count=81291} words", result11);
            Assertions.assertEquals("Target word: word appears: 108 times, and source/Files/pg37106.txt has : {count=195624} words", result12);
            Assertions.assertEquals("Target word: word appears: 13 times, and source/Files/pg67979.txt has : {count=71331} words", result13);
            // Add more assertions as needed

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static int reduce(Map<String, Integer>[] results, String targetWord) {
        int total = 0;

        for (Map<String, Integer> result : results) {
            total += result.getOrDefault(targetWord, 0);
        }

        return total;
    }
}
