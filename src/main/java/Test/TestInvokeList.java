package Test;

import Components.Controller;

import java.util.List;
import java.util.function.Function;

public class TestInvokeList {
    public static void main(String[] args) {

        Controller<Integer, String> controller = new Controller<>();

        Function<Integer, String> sleepFunction = seconds -> {
            try {
                Thread.sleep(seconds * 1000L);
                return "Done!";
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };


        controller.registerAction2("sleepAction", sleepFunction, 150);

        List<Integer> inputList = List.of(1, 5, 5);

        long startTime = System.currentTimeMillis();

        List<Object> results = (List<Object>) controller.invoke("sleepAction", inputList);

        for (Object result : results) {
            System.out.println("Result: " + result);
        }

        long endTime = System.currentTimeMillis();

        long duration = endTime - startTime;
        System.out.println("Total execution time: " + duration + " milliseconds");

        controller.shutdown();
    }
}
