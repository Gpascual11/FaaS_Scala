package Test;

import Components.Controller;
import PolicyManager.*;

import java.util.ArrayList;
import java.util.List;

public class TestPolicyManager {

    static List<Integer> inputList = new ArrayList<>();

    public static void main(String[] args) {
        Controller<Integer, String> controller = new Controller<>(1, 5, 1024, 400);
        List<String> functionsToAssign = new ArrayList<>();
        functionsToAssign = createFunctionsAndRam(controller);
        Policy policy = null;
        for (int i = 0; i < 4; i++) {

            switch (i) {
                case 0:
                    // Create a RoundRobinPolicy
                    policy = new RoundRobinPolicy();
                    break;
                case 1:
                    policy = new GreedyGroupPolicy();
                    break;
                case 2:
                    policy = new UniformGroupPolicy(4);
                    break;
                case 3:
                    policy = new BigGroupPolicy(2);
                    break;
            }

            //controller.invokeFunctionsPolicyManager(policy, functionsToAssign, inputList);
            controller.invokeFunctionsPolicyManagerAsync(policy, functionsToAssign, inputList);
        }
    }
    private static List<String> createFunctionsAndRam(Controller<Integer, String> controller) {
        List<String> functionsToAssign = new ArrayList<>();
        controller.registerAction2("sleepFunction", (x) -> {
            try {
                Thread.sleep(x * 1000L);
             } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Function " + x + " executed";
        }, 200);

        controller.registerAction2("SumAction2", (x) -> {
            int sum = 0;
            for (int i = 0; i < x; i++) {
                sum += i;
            }
            return "Sum of " + x + " is " + sum;
        }, 200);

        functionsToAssign.add("sleepFunction");
        functionsToAssign.add("sleepFunction");
        functionsToAssign.add("sleepFunction");
        functionsToAssign.add("SumAction2");
        functionsToAssign.add("SumAction2");
        functionsToAssign.add("SumAction2");
        functionsToAssign.add("SumAction2");
        functionsToAssign.add("sleepFunction");
        functionsToAssign.add("sleepFunction");
        functionsToAssign.add("SumAction2");
        functionsToAssign.add("SumAction2");

        inputList.add(5);
        inputList.add(2);
        inputList.add(3);
        inputList.add(4);
        inputList.add(5);
        inputList.add(6);
        inputList.add(7);
        inputList.add(5);
        inputList.add(5);
        inputList.add(10);
        inputList.add(11);

        return functionsToAssign;
    }
}

