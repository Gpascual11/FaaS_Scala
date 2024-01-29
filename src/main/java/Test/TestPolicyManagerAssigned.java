package Test;

import Components.Controller;
import Components.Invoker;
import PolicyManager.*;

import java.util.ArrayList;
import java.util.List;

public class TestPolicyManagerAssigned {
    public static void main(String[] args) {
        Controller<Integer, String> controller = new Controller<>(2, 5, 1024, 650);
        PolicyManager policyManager = null;
        List<String> functionsToAssign = new ArrayList<>();

        functionsToAssign = createFunctionsAndRam(controller);
        // Continue until the user enters 0
        for (int i = 0; i < 4; i++) {
            // Use switch statement to select the policy
            switch (i) {
                case 0:
                    policyManager = new PolicyManager(new RoundRobinPolicy());
                    break;
                case 1:
                    policyManager = new PolicyManager(new GreedyGroupPolicy());
                    break;
                case 2:
                    policyManager = new PolicyManager(new UniformGroupPolicy(3));
                    break;
                case 3:
                    policyManager = new PolicyManager(new BigGroupPolicy(2));
                    break;
            }
            System.out.println("----------------------------------");
            System.out.println("Policy: " + policyManager.getPolicyName());
            System.out.println("----------------------------------\n");

            System.out.println("RAM requirements: " + controller.getRamMap());
            System.out.println("Invokers: " + controller.getInvokers());
            System.out.println("Total memory of the invokers: " + controller.getInvokerMemoryUsage());

            List<Invoker<Integer, String>> assignedInvokers = policyManager.assignFunctions(controller.getInvokers(), controller.getFunctionMap(), controller.getRamMap(), functionsToAssign);
            System.out.println("Assigned invokers: " + assignedInvokers);
            System.out.println("Total functions to assign: " + functionsToAssign.size());
            System.out.println("Function assignments:");
            // For invoking every invoker from the assignedInvokers list
            for (int j = 0; j < functionsToAssign.size(); j++) {
                Invoker<Integer, String> assignedInvoker = assignedInvokers.get(j);
                String functionName = functionsToAssign.get(j);
                int x = j + 1;
                System.out.println("Function " + functionName + ": " + controller.invokeWithInvoker(functionName, x, assignedInvoker) + " assigned to Invoker with id " + assignedInvoker.getInvokerId() + " :" + assignedInvoker);
            }
            assignedInvokers.clear();
        }

        // Close the scanner and shutdown the controller when done
        controller.shutdown();
    }

    private static List<String> createFunctionsAndRam(Controller<Integer, String> controller) {
        List<String> functionsToAssign = new ArrayList<>();
        controller.registerAction2("sleepFunction", (x) -> {
            try {
                Thread.sleep(1000);
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

        return functionsToAssign;
    }
}

