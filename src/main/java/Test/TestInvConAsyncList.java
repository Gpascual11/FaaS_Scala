package Test;

import Components.Controller;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Function;

public class TestInvConAsyncList {
    public static void main(String[] args) {
        // Create a Controller
        Controller<Integer, String> controller = new Controller<>();

        // Register a sleep function
        Function<Integer, String> sleepFunction = seconds -> {
            try {
                Thread.sleep(seconds * 1000L); // Convert seconds to milliseconds
                return "Done!";
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        // Register the sleep function with a specific amount of RAM
        controller.registerAction2("sleepAction", sleepFunction, 150);

        // Measure start time
        long startTime = System.currentTimeMillis();

        // Invoke the sleep function asynchronously with a list of inputs
        List<Integer> inputList = Arrays.asList(1, 5, 3, 2, 4);
        List<Future<String>> futures = controller.invokeAsync("sleepAction", inputList);

        try {
            // Wait for the results
            for (int i = 0; i < futures.size(); i++) {
                Future<String> future = futures.get(i);
                System.out.println("Result " + (i + 1) + ": " + future.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            // Shutdown the controller
            controller.shutdown();
        }

        // Measure end time
        long endTime = System.currentTimeMillis();

        // Calculate and print the duration
        long duration = endTime - startTime;
        System.out.println("Total execution time: " + duration + " milliseconds");
    }
}
