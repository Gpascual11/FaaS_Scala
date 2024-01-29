package TestJUnit;
import Components.Controller;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TestInvCon {

    private Controller<Integer, String> controller;

    @BeforeEach
    void setUp() {
        // Initialize the controller before each test
        controller = new Controller<>();
    }

    @AfterEach
    void tearDown() {
        // Shutdown the controller after each test
        controller.shutdown();
    }

    @Test
    void testInvokeAsyncSingle() {
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

        // Invoke the sleep function asynchronously
        Future<String> future = controller.invokeAsync("sleepAction", 3);

        // Use assertTimeoutPreemptively to handle asynchronous behavior
        assertTimeoutPreemptively(
                // Set a timeout longer than the expected total execution time
                java.time.Duration.ofSeconds(10),
                () -> assertEquals("Done!", future.get())
        );

        // Measure end time
        long endTime = System.currentTimeMillis();

        // Calculate and print the duration
        long duration = endTime - startTime;
        System.out.println("Total execution time: " + duration + " milliseconds");
    }

    @Test
    void testInvokeAsyncMultiple() {
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
            Future<String> future = controller.invokeAsync("sleepAction", 3);
            futures.add(future);
        }

        try {
            // Wait for the results
            for (int i = 0; i < 6; i++) {
                System.out.println("Result " + (i + 1) + ": " + futures.get(i).get());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // Measure end time
        long endTime = System.currentTimeMillis();

        // Calculate and print the duration
        long duration = endTime - startTime;
        System.out.println("Total execution time: " + duration + " milliseconds");
    }

    @Test
    void testInvokeSynchronous() {
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
    }

    @Test
    void testInvokeList() {
        // Register a sleep function
        Function<Integer, String> sleepFunction = seconds -> {
            try {
                Thread.sleep(seconds * 1000L);
                return "Done!";
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        // Register the sleep function with a specific amount of RAM
        controller.registerAction2("sleepAction", sleepFunction, 150);

        List<Integer> inputList = List.of(1, 5, 5);

        // Measure start time
        long startTime = System.currentTimeMillis();

        List<Object> results = (List<Object>) controller.invoke("sleepAction", inputList);

        // Print the results
        for (Object result : results) {
            System.out.println("Result: " + result);
        }

        // Measure end time
        long endTime = System.currentTimeMillis();

        // Calculate and print the duration
        long duration = endTime - startTime;
        System.out.println("Total execution time: " + duration + " milliseconds");
    }
}
