package Test;

import Components.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Function;

public class TestInvConAsynchronousExhaustive {
    public static void main(String[] args) {
        // Create a Components.Controller
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

        // Invoke the sleep function asynchronously ten times
        List<Future<String>> futures = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Future<String> future = controller.invokeAsync("sleepAction", 4);
            futures.add(future);
        }

        try {
            // Wait for the results
            for (int i = 0; i < 6; i++) {
                System.out.println("Result " + (i + 1) + ": " + futures.get(i).get());
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
