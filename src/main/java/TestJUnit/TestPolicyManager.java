package TestJUnit;

import Components.Controller;
import Components.Invoker;
import PolicyManager.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPolicyManager {

    private Controller<Integer, String> controller;
    static List<Integer> inputList = new ArrayList<>();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        // Shutdown the controller after each test
        controller.shutdown();
    }

    @Test
    public void testRoundRobinPolicy() {
        controller = new Controller<>(5, 2, 1024, 400);
        testPolicyRR1(controller, new RoundRobinPolicy());
    }

    @Test
    public void testRoundRobinPolicy2() {
        controller = new Controller<>(1, 5, 1500, 650);
        testPolicyRR2(controller, new RoundRobinPolicy());
    }

    @Test
    public void testRoundRobinPolicy3() {
        controller = new Controller<>(1, 5, 200, 650);
        testPolicyRR3(controller, new RoundRobinPolicy());
    }


    @Test
    public void testGreedyGroupPolicy() {
        controller = new Controller<>(3, 2, 1500, 600);
        testPolicyGG(controller, new GreedyGroupPolicy());
    }

    @Test
    public void testGreedyGroupPolicy2() {
        controller = new Controller<>(5, 3, 300, 600);
        testPolicyGG2(controller, new GreedyGroupPolicy());
    }

    @Test
    public void testGreedyGroupPolicy3() {
        controller = new Controller<>(2, 3, 300, 500);
        testPolicyGG3(controller, new GreedyGroupPolicy());
    }

    @Test
    public void testUniformGroupPolicy() {
        controller = new Controller<>(5, 2, 2000, 1200);
        testPolicyUG(controller, new UniformGroupPolicy(4));
    }

    @Test
    public void testUniformGroupPolicy2() {
        controller = new Controller<>(3, 6, 1000, 1200);
        testPolicyUG2(controller, new UniformGroupPolicy(2));
    }

    @Test
    public void testUniformGroupPolicy3() {
        controller = new Controller<>(1, 2, 1000, 1200);
        testPolicyUG3(controller, new UniformGroupPolicy(3));
    }

    @Test
    public void testBigGroupPolicy() {
        controller = new Controller<>(3, 6, 1200, 600);
        testPolicyBG(controller, new BigGroupPolicy(4));
    }

    @Test
    public void testBigGroupPolicy2() {
        controller = new Controller<>(5, 3, 1500, 800);
        testPolicyBG2(controller, new BigGroupPolicy(3));
    }

    @Test
    public void testBigGroupPolicy3() {
        controller = new Controller<>(2, 5, 1024, 400);
        testPolicyBG3(controller, new BigGroupPolicy(2));
    }

    private void testPolicyRR1(Controller<Integer, String> controller, Policy policy) {
        List<String> functionsToAssign = createFunctionsAndRam(controller);

        List<Invoker<Integer, String>> invokersAssigned = controller.usePolicyManager(policy, functionsToAssign);
        System.out.println("Round Robin Policy Test 1");
        System.out.println("Invokers from the controller: " + controller.getInvokers());
        System.out.println("Ram from the invokers: " + controller.getInvokerMemoryUsage());
        System.out.println("Functions to assign: " + functionsToAssign);
        System.out.println("With ram: " + controller.getRamMap());
        System.out.println("Invokers assigned: " + invokersAssigned);

        assertEquals(11, invokersAssigned.size());

        for (int i = 0; i < invokersAssigned.size(); i++) {
            int expectedInvokerId = (i % 7) + 1;    // As they have enough memory for every function should be assigned one by one
            assertEquals(expectedInvokerId, invokersAssigned.get(i).getInvokerId());
        }

        invokersAssigned.clear();
    }
    private void testPolicyRR2(Controller<Integer, String> controller, Policy policy) {
        List<String> functionsToAssign = createFunctionsAndRam(controller);

        List<Invoker<Integer, String>> invokersAssigned = controller.usePolicyManager(policy, functionsToAssign);
        System.out.println("Round Robin Policy Test 2");
        System.out.println("Invokers from the controller: " + controller.getInvokers());
        System.out.println("Ram from the invokers: " + controller.getInvokerMemoryUsage());
        System.out.println("Functions to assign: " + functionsToAssign);
        System.out.println("With ram: " + controller.getRamMap());
        System.out.println("Invokers assigned: " + invokersAssigned);

        assertEquals(11, invokersAssigned.size());

        for (int i = 0; i < invokersAssigned.size(); i++) {
            int expectedInvokerId = (i % 6) + 1;
            assertEquals(expectedInvokerId, invokersAssigned.get(i).getInvokerId());
        }

        invokersAssigned.clear();
    }

    private void testPolicyRR3(Controller<Integer, String> controller, Policy policy) {
        List<String> functionsToAssign = createFunctionsAndRam(controller);
        System.out.println("Round Robin Policy Test 3");
        // Expected to throw an exception
        assertThrows(java.lang.IllegalStateException.class, () -> {
            List<Invoker<Integer, String>> invokersAssigned = controller.usePolicyManager(policy, functionsToAssign);
        });
    }

    private void testPolicyGG(Controller<Integer, String> controller, Policy policy) {
        List<String> functionsToAssign = createFunctionsAndRam(controller);

        List<Invoker<Integer, String>> invokersAssigned = controller.usePolicyManager(policy, functionsToAssign);

        System.out.println("Greedy Group Policy Test 1");
        System.out.println("Invokers from the controller: " + controller.getInvokers());
        System.out.println("Ram from the invokers: " + controller.getInvokerMemoryUsage());
        System.out.println("Functions to assign: " + functionsToAssign);
        System.out.println("With ram: " + controller.getRamMap());
        System.out.println("Invokers assigned: " + invokersAssigned);


        assertEquals(11, invokersAssigned.size());
        //  In the Greedy Group we fully fill an invoke before moving to the next one
        assertEquals(1, invokersAssigned.get(0).getInvokerId());
        assertEquals(1, invokersAssigned.get(1).getInvokerId());
        assertEquals(1, invokersAssigned.get(2).getInvokerId());
        assertEquals(1, invokersAssigned.get(3).getInvokerId());
        assertEquals(1, invokersAssigned.get(4).getInvokerId());
        assertEquals(2, invokersAssigned.get(5).getInvokerId());
        assertEquals(2, invokersAssigned.get(6).getInvokerId());
        assertEquals(2, invokersAssigned.get(7).getInvokerId());
        assertEquals(2, invokersAssigned.get(8).getInvokerId());
        assertEquals(2, invokersAssigned.get(9).getInvokerId());
        assertEquals(3, invokersAssigned.get(10).getInvokerId());
    }

    private void testPolicyGG2(Controller<Integer, String> controller, Policy policy) {
        List<String> functionsToAssign = createFunctionsAndRam(controller);

        List<Invoker<Integer, String>> invokersAssigned = controller.usePolicyManager(policy, functionsToAssign);

        System.out.println("Greedy Group Policy Test 2");
        System.out.println("Invokers from the controller: " + controller.getInvokers());
        System.out.println("Ram from the invokers: " + controller.getInvokerMemoryUsage());
        System.out.println("Functions to assign: " + functionsToAssign);
        System.out.println("With ram: " + controller.getRamMap());
        System.out.println("Invokers assigned: " + invokersAssigned);


        assertEquals(11, invokersAssigned.size());
        //  In the Greedy Group we fully fill an invoke before moving to the next one
        assertEquals(1, invokersAssigned.get(0).getInvokerId());
        assertEquals(2, invokersAssigned.get(1).getInvokerId());
        assertEquals(3, invokersAssigned.get(2).getInvokerId());
        assertEquals(4, invokersAssigned.get(3).getInvokerId());
        assertEquals(5, invokersAssigned.get(4).getInvokerId());
        assertEquals(6, invokersAssigned.get(5).getInvokerId());
        assertEquals(6, invokersAssigned.get(6).getInvokerId());
        assertEquals(7, invokersAssigned.get(7).getInvokerId());
        assertEquals(7, invokersAssigned.get(8).getInvokerId());
        assertEquals(8, invokersAssigned.get(9).getInvokerId());
        assertEquals(8, invokersAssigned.get(10).getInvokerId());
    }

    private void testPolicyGG3(Controller<Integer, String> controller, Policy policy) {
        List<String> functionsToAssign = createFunctionsAndRam(controller);

        System.out.println("Greedy Group Policy Test 3");
        assertThrows(java.lang.IllegalStateException.class, () -> {
            List<Invoker<Integer, String>> invokersAssigned = controller.usePolicyManager(policy, functionsToAssign);
        });

    }

    private void testPolicyUG(Controller<Integer, String> controller, Policy policy) {
        List<String> functionsToAssign = createFunctionsAndRam(controller);

        List<Invoker<Integer, String>> invokersAssigned = controller.usePolicyManager(policy, functionsToAssign);

        System.out.println("Uniform Group Policy Test 1");
        System.out.println("Invokers from the controller: " + controller.getInvokers());
        System.out.println("Ram from the invokers: " + controller.getInvokerMemoryUsage());
        System.out.println("Functions to assign: " + functionsToAssign);
        System.out.println("With ram: " + controller.getRamMap());
        System.out.println("Invokers assigned: " + invokersAssigned);
        assertEquals(11, invokersAssigned.size());

        assertEquals(1, invokersAssigned.get(0).getInvokerId());
        assertEquals(1, invokersAssigned.get(1).getInvokerId());
        assertEquals(1, invokersAssigned.get(2).getInvokerId());
        assertEquals(1, invokersAssigned.get(3).getInvokerId());
        assertEquals(2, invokersAssigned.get(4).getInvokerId());
        assertEquals(2, invokersAssigned.get(5).getInvokerId());
        assertEquals(2, invokersAssigned.get(6).getInvokerId());
        assertEquals(2, invokersAssigned.get(7).getInvokerId());
        assertEquals(3, invokersAssigned.get(8).getInvokerId());
        assertEquals(3, invokersAssigned.get(9).getInvokerId());
        assertEquals(3, invokersAssigned.get(10).getInvokerId());
    }

    private void testPolicyUG2(Controller<Integer, String> controller, Policy policy) {
        List<String> functionsToAssign = createFunctionsAndRam(controller);

        List<Invoker<Integer, String>> invokersAssigned = controller.usePolicyManager(policy, functionsToAssign);

        System.out.println("Uniform Group Policy Test 2");
        System.out.println("Invokers from the controller: " + controller.getInvokers());
        System.out.println("Ram from the invokers: " + controller.getInvokerMemoryUsage());
        System.out.println("Functions to assign: " + functionsToAssign);
        System.out.println("With ram: " + controller.getRamMap());
        System.out.println("Invokers assigned: " + invokersAssigned);
        assertEquals(11, invokersAssigned.size());

        assertEquals(1, invokersAssigned.get(0).getInvokerId());
        assertEquals(1, invokersAssigned.get(1).getInvokerId());
        assertEquals(2, invokersAssigned.get(2).getInvokerId());
        assertEquals(2, invokersAssigned.get(3).getInvokerId());
        assertEquals(3, invokersAssigned.get(4).getInvokerId());
        assertEquals(3, invokersAssigned.get(5).getInvokerId());
        assertEquals(4, invokersAssigned.get(6).getInvokerId());
        assertEquals(4, invokersAssigned.get(7).getInvokerId());
        assertEquals(5, invokersAssigned.get(8).getInvokerId());
        assertEquals(5, invokersAssigned.get(9).getInvokerId());
        assertEquals(6, invokersAssigned.get(10).getInvokerId());
    }

    private void testPolicyUG3(Controller<Integer, String> controller, Policy policy) {
        List<String> functionsToAssign = createFunctionsAndRam(controller);

        System.out.println("Uniform Group Policy Test 3");
        assertThrows(java.lang.RuntimeException.class, () -> {
            List<Invoker<Integer, String>> invokersAssigned = controller.usePolicyManager(policy, functionsToAssign);
        });
    }

    private void testPolicyBG(Controller<Integer, String> controller, Policy policy) {
        List<String> functionsToAssign = createFunctionsAndRam(controller);

        List<Invoker<Integer, String>> invokersAssigned = controller.usePolicyManager(policy, functionsToAssign);

        System.out.println("Big Group Policy Test 1");
        System.out.println("Invokers from the controller: " + controller.getInvokers());
        System.out.println("Ram from the invokers: " + controller.getInvokerMemoryUsage());
        System.out.println("Functions to assign: " + functionsToAssign);
        System.out.println("With ram: " + controller.getRamMap());
        System.out.println("Invokers assigned: " + invokersAssigned);
        assertEquals(11, invokersAssigned.size());

        assertEquals(1, invokersAssigned.get(0).getInvokerId());
        assertEquals(1, invokersAssigned.get(1).getInvokerId());
        assertEquals(1, invokersAssigned.get(2).getInvokerId());
        assertEquals(1, invokersAssigned.get(3).getInvokerId());
        assertEquals(2, invokersAssigned.get(4).getInvokerId());
        assertEquals(2, invokersAssigned.get(5).getInvokerId());
        assertEquals(2, invokersAssigned.get(6).getInvokerId());
        assertEquals(2, invokersAssigned.get(7).getInvokerId());
        assertEquals(3, invokersAssigned.get(8).getInvokerId());
        assertEquals(3, invokersAssigned.get(9).getInvokerId());
        assertEquals(3, invokersAssigned.get(10).getInvokerId());
    }

    private void testPolicyBG2(Controller<Integer, String> controller, Policy policy) {
        List<String> functionsToAssign = createFunctionsAndRam(controller);

        List<Invoker<Integer, String>> invokersAssigned = controller.usePolicyManager(policy, functionsToAssign);

        System.out.println("Big Group Policy Test 2");
        System.out.println("Invokers from the controller: " + controller.getInvokers());
        System.out.println("Ram from the invokers: " + controller.getInvokerMemoryUsage());
        System.out.println("Functions to assign: " + functionsToAssign);
        System.out.println("With ram: " + controller.getRamMap());
        System.out.println("Invokers assigned: " + invokersAssigned);
        assertEquals(11, invokersAssigned.size());

        assertEquals(1, invokersAssigned.get(0).getInvokerId());
        assertEquals(1, invokersAssigned.get(1).getInvokerId());
        assertEquals(1, invokersAssigned.get(2).getInvokerId());
        assertEquals(2, invokersAssigned.get(3).getInvokerId());
        assertEquals(2, invokersAssigned.get(4).getInvokerId());
        assertEquals(2, invokersAssigned.get(5).getInvokerId());
        assertEquals(3, invokersAssigned.get(6).getInvokerId());
        assertEquals(3, invokersAssigned.get(7).getInvokerId());
        assertEquals(3, invokersAssigned.get(8).getInvokerId());
        assertEquals(4, invokersAssigned.get(9).getInvokerId());
        assertEquals(4, invokersAssigned.get(10).getInvokerId());
    }

    private void testPolicyBG3(Controller<Integer, String> controller, Policy policy) {
        List<String> functionsToAssign = createFunctionsAndRam(controller);

        System.out.println("Big Group Policy Test 3");
        assertThrows(java.lang.RuntimeException.class, () -> {
            List<Invoker<Integer, String>> invokersAssigned = controller.usePolicyManager(policy, functionsToAssign);
        });
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
        }, 250);

        controller.registerAction2("SumAction2", (x) -> {
            int sum = 0;
            for (int i = 0; i < x; i++) {
                sum += i;
            }
            return "Sum of " + x + " is " + sum;
        }, 300);

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
