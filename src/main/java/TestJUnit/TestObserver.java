package TestJUnit;

import Components.Controller;
import Components.Invoker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestObserver {

    private Controller<Integer, String> controller;
    private List<Invoker<Integer, String>> invokersList;

    @BeforeEach
    void setUp() {
        // Initialize the controller and invokers before each test
        controller = new Controller<>(6);
        invokersList = controller.getInvokers();

        for (Invoker<Integer, String> invoker : invokersList) {
            invoker.attachObserver(controller);
        }
    }

    @AfterEach
    void tearDown() {
        // Shutdown the controller after each test
        controller.shutdown();
    }

    @Test
    void testObserverComplet() {
        // Register a sleep function
        Function<Integer, String> sleepFunction = seconds -> {
            try {
                Thread.sleep(seconds * 1000L); // Convert seconds to milliseconds
                return "Done!";
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        // Register the sleep function with a specific amount of RAM for each Invoker
        controller.registerAction2("sleepAction", sleepFunction, 150);
        controller.registerAction2("sleepFunction", sleepFunction, 200);

        // Measure start time
        long startTime = System.currentTimeMillis();

        // Invoke the sleep function asynchronously for each Invoker
        Future<String> fut1 = controller.invokeAsync("sleepAction", 1);
        Future<String> fut2 = controller.invokeAsync("sleepAction", 5);
        Future<String> fut3 = controller.invokeAsync("sleepFunction", 5);

        try {
            // Wait for the results
            assertEquals("Done!", fut1.get());
            assertEquals("Done!", fut2.get());
            assertEquals("Done!", fut3.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // Measure end time
        long endTime = System.currentTimeMillis();

        // Calculate and print the duration
        long duration = endTime - startTime;
        System.out.println("Total execution time: " + duration + " milliseconds");

        // Access metrics from the Controller
        System.out.println("Max Execution Time: " + controller.getMaxExecutionTime());
        System.out.println("Min Execution Time: " + controller.getMinExecutionTime());
        System.out.println("Average Execution Time: " + controller.getAverageExecutionTime());
        System.out.println("Total Execution Time: " + controller.getTotalExecutionTime());
    }
}
