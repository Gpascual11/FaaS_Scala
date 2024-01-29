package Test;

import Components.Controller;

import java.util.function.Function;

public class TestInvConSynchronousExhaustive {
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

        // Invoke the sleep function synchronously ten times
        for (int i = 0; i < 10; i++) {
            String result = (String) controller.invoke("sleepAction", 3);
            System.out.println("Result " + (i + 1) + ": " + result);
        }

        // Measure end time
        long endTime = System.currentTimeMillis();

        // Calculate and print the duration
        long duration = endTime - startTime;
        System.out.println("Total execution time: " + duration + " milliseconds");

        // Shutdown the controller
        controller.shutdown();
    }
}
