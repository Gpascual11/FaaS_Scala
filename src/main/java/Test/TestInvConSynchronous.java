package Test;

import Components.Controller;

import java.util.function.Function;

public class TestInvConSynchronous {
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

        // Invoke the sleep function synchronously three times
        String result1 = (String) controller.invoke("sleepAction", 1);
        String result2 = (String) controller.invoke("sleepAction", 5);
        String result3 = (String) controller.invoke("sleepAction", 5);

        // Print the results
        System.out.println("Result 1: " + result1);
        System.out.println("Result 2: " + result2);
        System.out.println("Result 3: " + result3);

        // Measure end time
        long endTime = System.currentTimeMillis();

        // Calculate and print the duration
        long duration = endTime - startTime;
        System.out.println("Total execution time: " + duration + " milliseconds");

        // Shutdown the controller
        controller.shutdown();
    }
}
