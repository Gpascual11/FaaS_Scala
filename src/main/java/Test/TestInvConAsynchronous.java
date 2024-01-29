package Test;

import Components.Controller;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Function;

public class TestInvConAsynchronous {
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

        // Invoke the sleep function asynchronously three times
        Future<String> fut1 = controller.invokeAsync("sleepAction", 1);
        Future<String> fut2 = controller.invokeAsync("sleepAction", 7);
        Future<String> fut3 = controller.invokeAsync("sleepAction", 3);


        try {
            // Wait for the results
            System.out.println("Result 1: " + fut1.get());
            System.out.println("Result 2: " + fut2.get());
            System.out.println("Result 3: " + fut3.get());
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
