import Components.Controller;
import MapReduce.CountWords;
import MapReduce.TestMapReduce;
import MapReduce.MapReduceUtil;
import MapReduce.WordCount;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Function;
public class TestMapReduceAsync {

    public static void  main (String[] args) {

        String targetWord = "mother";

        Controller<String, String> controller = new Controller<>(2, 3,  500, 350);

        Function<String, String> wordCount = path -> {
            String textFromFile = MapReduceUtil.readTextFromFile(path);

            Map<String, Integer> wordCountResult = WordCount.wordCountMap(textFromFile);

            Map<String, Integer> countWordsResult = CountWords.countWordsMap(textFromFile);
            System.out.println("CountWords for " + path + ": " + countWordsResult);


            int totalWordCount = TestMapReduce.reduce(new Map[]{wordCountResult, countWordsResult}, targetWord);
            System.out.println("Total of the word: " + targetWord + " for: " + path + ": " + totalWordCount);
            return path + " Done!";
        };

        controller.registerAction2("wordCount", wordCount, 150);

        Future<String> fut1 = controller.invokeAsync("wordCount", "source/Files/pg100.txt");
        Future<String> fut2 = controller.invokeAsync("wordCount", "source/Files/pg145.txt");
        Future<String> fut3 = controller.invokeAsync("wordCount", "source/Files/pg2160.txt");
        Future<String> fut4 = controller.invokeAsync("wordCount", "source/Files/pg394.txt");
        Future<String> fut5 = controller.invokeAsync("wordCount", "source/Files/pg4085.txt");
        Future<String> fut6 = controller.invokeAsync("wordCount", "source/Files/pg5197.txt");
        Future<String> fut7 = controller.invokeAsync("wordCount", "source/Files/pg37106.txt");
        Future<String> fut8 = controller.invokeAsync("wordCount", "source/Files/pg67979.txt");
        Future<String> fut9 = controller.invokeAsync("wordCount", "source/Files/pg16389.txt");


        try {
            System.out.println("Result 1: " + fut1.get());
            System.out.println("Result 2: " + fut2.get());
            System.out.println("Result 3: " + fut3.get());
            System.out.println("Result 4: " + fut4.get());
            System.out.println("Result 5: " + fut5.get());
            System.out.println("Result 6: " + fut6.get());
            System.out.println("Result 7: " + fut7.get());
            System.out.println("Result 8: " + fut8.get());
            System.out.println("Result 9: " + fut9.get());

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            controller.shutdown();
        }

    }
}
