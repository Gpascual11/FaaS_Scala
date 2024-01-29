package Test;

import Components.Controller;
import Components.Invoker;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Function;

public class TestObserverComplet {
    public static void main(String[] args) {

        List<Invoker<Integer, String>> invokersList;

        // Create a Components.Controller
        Controller<Integer, String> controller = new Controller<>(6);
        invokersList = controller.getInvokers();

        for (Invoker<Integer, String> invoker : invokersList) {
            invoker.attachObserver(controller);
        }

        // Measure start time
        long startTime = System.currentTimeMillis();

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

        // Invoke the sleep function asynchronously for each Invoker
        Future<String> fut1 = controller.invokeAsync("sleepAction", 1);
        Future<String> fut2 = controller.invokeAsync("sleepAction", 5);
        Future<String> fut3 = controller.invokeAsync("sleepFunction", 5);

        try {
            // Wait for the results
            System.out.println("Result 1: " + fut1.get());
            System.out.println("Result 2: " + fut2.get());
            System.out.println("Result 3: " + fut3.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            // Shutdown the controller (which will also shutdown attached Invokers)
            controller.shutdown();
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
        System.out.println("Max Memory Used: " + controller.getMaxMemoryUsed());
    }
}
