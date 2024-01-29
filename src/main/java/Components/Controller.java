package Components;

import Observer.MetricsData;
import Observer.MetricsObserver;
import PolicyManager.Policy;
import PolicyManager.PolicyManager;
import PolicyManager.RoundRobinPolicy;
import Reflection.DynamicProxy;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/**
 * Class implementing a controller for a Function-as-a-Service (FaaS) system.
 * Allows registration, invocation, and management of functions through invokers.
 *
 * @param <I> Type of input data for the function.
 * @param <O> Type of output data for the function.
 */
public class Controller <I, O> implements MetricsObserver {

    // List of available invokers
    private final List<Invoker<I, O>> invokers = new ArrayList<>();

    // Map associating function names with their implementations.
    private final Map<String, Function<I, O>> functionMap;

    // Map indicating the amount of RAM associated with each function.
    private final Map<String, Integer> ramMap;

    // List of collected metrics Observer Pattern
    private final List<MetricsData> collectedMetrics = new ArrayList<>();

    // Policy manager
    private final PolicyManager<I, O>  policyManager;



    /**
     * Default constructor for the Components.Controller class.
     * Initializes the necessary lists, maps, policy manager and creates a default invoker.
     */
    public Controller() {
        this.functionMap = new HashMap<>();
        this.invokers.add(new Invoker<>(5, new AtomicInteger(1024)));
        this.ramMap = new HashMap<>(4, 4);
        this.policyManager = new PolicyManager<I, O> (new RoundRobinPolicy<I, O> ());
    }

    /**
     * Constructor with invoker count for the Components.Controller class.
     * Initializes the necessary lists, maps, policy manager and creates invokers according to the specified count.
     *
     * @param invokerCount Number of invokers to be created.
     */
    public Controller(int invokerCount) {
        this.functionMap = new HashMap<>();
        for (int i = 0; i < invokerCount; i++) {
            this.invokers.add(new Invoker<>(5, new AtomicInteger(1024)));
        }
        this.ramMap = new HashMap<>(4, 4);
        this.policyManager = new PolicyManager<I, O> (new RoundRobinPolicy<I, O> ());
    }

    /**
     * Constructor for the Components.Controller class.
     * Initializes the necessary lists, maps, policy manager and creates invokers according to the specified count and RAM.
     *
     * @param invokerCount Number of invokers to be created.
     * @param invokerCount2 Number of invokers2 to be created.
     * @param ram Amount of RAM associated with each invoker.
     * @param ram2 Amount of RAM associated with each invoker2.
     */
    public Controller(int invokerCount, int invokerCount2, int ram, int ram2) {
        this.functionMap = new HashMap<>();
        for (int i = 0; i < invokerCount; i++) {
            this.invokers.add(new Invoker<>(5, new AtomicInteger(ram)));
        }
        for (int i = 0; i < invokerCount2; i++) {
            this.invokers.add(new Invoker<>(5, new AtomicInteger(ram2)));
        }
        this.ramMap = new HashMap<>(4, 4);
        this.policyManager = new PolicyManager<I, O> (new RoundRobinPolicy<I, O> ());
    }

    /**
     * Adds a new invoker to the list of invokers from the controller with the specified number of threads and RAM.
     *
     * @param numThreads Number of threads for the new invoker.
     * @param ram        RAM configuration for the new invoker.
     */
     public void addInvoker(int numThreads, int ram) {
         this.invokers.add(new Invoker<>(numThreads, new AtomicInteger(ram)));
     }

    /**
     * Method to register a new function. It checks if the function is already registered.
     *
     * @param name Name of the function.
     * @param function Implementation of the function.
     * @throws IllegalArgumentException If the specified function is already registered.
     */
    public void registerAction(String name, Function<I, O> function){
        if (ramMap.containsKey(name)) {
            throw new IllegalArgumentException("Function already registered");
        }
        functionMap.put(name, function);
        ramMap.put(name, 300);
    }

    /**
     * Method to register a new function with a specific amount of RAM. It checks if the function is already registered.
     *
     * @param name Name of the function.
     * @param function Implementation of the function.
     * @param ram Amount of RAM associated with the function.
     * @throws IllegalArgumentException If the specified function is already registered or the RAM is less than 0.
     */
     public void registerAction2(String name, Function<I, O> function, int ram){
         if (ram <= 0) {
             throw new IllegalArgumentException("RAM must be greater than 0");
         }
         if (ramMap.containsKey(name)) {
             throw new IllegalArgumentException("Function already registered");
         }
        functionMap.put(name, function);
        ramMap.put(name, ram);
    }

    /**
     * Invokes a function with a single input synchronously to an invoker.
     *
     * @param name Name of the function to invoke.
     * @param input Input to the function.
     * @return Output of the function.
     * @throws IllegalArgumentException If the specified function is not found.
     * @throws IllegalStateException    If there is not enough available memory in any of the invokers.
     */
    public Object invoke(String name, I input){
        if(functionMap.containsKey(name)){
            Function<I, O> function = functionMap.get(name);
            int ram = ramMap.getOrDefault(name, 1);

            Invoker<I, O> invokerAssigned = null;
            for (Invoker<I, O> invoker : invokers) {
                if (invoker.getTotalMemory().get() >= ram) {
                    invokerAssigned = invoker;
                }
            }
            if (invokerAssigned == null) {
                throw new IllegalStateException("Not enough available memory in any of the invokers");
            }

            return invokerAssigned.doFunction(function, input, ram);
        }
        else {
            throw new IllegalArgumentException("No function found");
        }
    }
    /**
     * Invokes a function with a single input synchronously to an invoker.
     *
     * @param name Name of the function to invoke.
     * @param input Input to the function.
     * @param invoker Invoker to invoke the function.
     * @return Output of the function.
     * @throws IllegalArgumentException If the specified function is not found.
     */
    public Object invokeWithInvoker(String name, I input, Invoker<I, O> invoker) {
        if(functionMap.containsKey(name)){
            Function<I, O> function = functionMap.get(name);
            int ram = ramMap.getOrDefault(name, 1);
            return invoker.doFunction(function, input, ram);
        }
        else {
            throw new IllegalArgumentException("No function found");
        }
    }

    /**
     * Invokes a function with a list of inputs synchronously to an invoker.
     *
     * @param name      Name of the function to invoke.
     * @param inputList List of inputs to the function.
     * @return List of outputs of the function.
     * @throws IllegalArgumentException If the specified function is not found.
     * @throws IllegalStateException    If there is not enough available memory in any of the invokers.
     */
    public List<Object> invoke(String name, List<I> inputList) {
        if (functionMap.containsKey(name)) {
            Function<I, O> function = functionMap.get(name);
            int ram = ramMap.getOrDefault(name, 1);
            List<Object> result = new ArrayList<>();

            for (I value : inputList) {
                Invoker<I, O> invokerAssigned = null;
                for (Invoker<I, O> invoker : invokers) {
                    if (invoker.getTotalMemory().get() >= ram) {
                        invokerAssigned = invoker;
                        break; // Exit loop as soon as a suitable invoker is found
                    }
                }

                if (invokerAssigned == null) {
                    throw new IllegalStateException("Not enough available memory in any of the invokers");
                }

                result.add(invokerAssigned.doFunction(function, value, ram));
            }
            return result;
        } else {
            throw new IllegalArgumentException("No function found");
        }
    }


    /**
     * Invokes a function with a single input asynchronously to an invoker.
     *
     * @param name Name of the function to invoke.
     * @param input Input to the function.
     * @return Future representing the output of the function.
     * @throws IllegalArgumentException If the specified function is not found.
     * @throws IllegalStateException    If there is not enough available memory in any of the invokers.
     */
    public Future<O> invokeAsync(String name, I input) {
        if (functionMap.containsKey(name)) {
            Function<I, O> function = functionMap.get(name);
            int ram = ramMap.getOrDefault(name, 1);
            Invoker<I, O> invokerAssigned = null;

            for (Invoker<I, O> invoker : invokers) {
                if (invoker.getTotalMemory().get() >= ram) {
                    invokerAssigned = invoker;
                    break; // Exit loop as soon as a suitable invoker is found
                }
            }

            if (invokerAssigned == null) {
                throw new IllegalStateException("Not enough available memory in any of the invokers");
            }

            return invokerAssigned.doFunctionAsync(function, input, ram);
        } else {
            throw new IllegalArgumentException("No function found");
        }
    }

    /**
     * Invokes a function with a single input asynchronously to an invoker.
     *
     * @param name Name of the function to invoke.
     * @param input Input to the function.
     * @param invoker Invoker to invoke the function.
     * @return Future representing the output of the function.
     * @throws IllegalArgumentException If the specified function is not found.
     */
    public Future<O> invokeAsyncWithInvoker(String name, I input, Invoker<I, O> invoker){
        if(functionMap.containsKey(name)){
            Function<I, O> function = functionMap.get(name);
            int ram = ramMap.getOrDefault(name, 1);
            return invoker.doFunctionAsync(function, input, ram);
        }
        else {
            throw new IllegalArgumentException("No function found");
        }
    }

    /**
     * Invokes a function with a list of inputs asynchronously to an invoker.
     *
     * @param name Name of the function to invoke.
     * @param inputList List of inputs to the function.
     * @return List of Futures representing the outputs of the function for each input.
     * @throws IllegalArgumentException If the specified function is not found.
     * @throws IllegalStateException    If there is not enough available memory in any of the invokers.
     */
    public List<Future<O>> invokeAsync(String name, List<I> inputList) {
        if (functionMap.containsKey(name)) {
            Function<I, O> function = functionMap.get(name);
            List<Future<O>> futures = new ArrayList<>();
            int ram = ramMap.getOrDefault(name, 1);

            for (I input : inputList) {
                Invoker<I, O> invokerAssigned = null;
                for (Invoker<I, O> invoker : invokers) {
                    if (invoker.getTotalMemory().get() >= ram) {
                        invokerAssigned = invoker;
                        break; // Exit loop as soon as a suitable invoker is found
                    }
                }

                if (invokerAssigned == null) {
                    throw new IllegalStateException("Not enough available memory in any of the invokers");
                }

                Future<O> future = invokerAssigned.doFunctionAsync(function, input, ram);
                futures.add(future);
            }

            return futures;
        } else {
            throw new IllegalArgumentException("No function found");
        }
    }

    /**
     * Method to shut down the invokers and release resources.
     */
    public void shutdown(){
        for (Invoker<I, O> invoker : invokers) {
            invoker.shutdown();
        }
    }

    /**
     * Method to get the total available memory for an invoker.
     *
     * @param invoker Invoker to get the memory from.
     * @return Total available memory.
     */
    public AtomicInteger getTotalMemory(Invoker <I, O> invoker) {
        return invoker.getTotalMemory();
    }

    /**
     * Method to get the actual RAM for every invoker.
     *
     * @return A list with the actual use of RAM for every invoker.
     */
    public List<AtomicInteger> getInvokerMemoryUsage() {
        List<AtomicInteger> memoryUsageList = new ArrayList<>();
        for (Invoker<I, O> invoker : invokers) {
            memoryUsageList.add(invoker.getTotalMemory());
        }
        return memoryUsageList;
    }

    /**
     * Method to get the invoker id
     *
     * @param id id of the invoker
     * @return invoker with the specified id
     */
    public Invoker<I, O> getInvoker(int id) {
        return invokers.get(id);
    }

    /**
     * Method to get the actual RAM for every invoker.
     *
     * @return A list of the invokers.
     */
    public List<Invoker<I, O>> getInvokers() {
        return invokers;
    }

    /**
     * Method to use the policy manager to assign functions to invokers.
     *
     * @param policy Policy to be used by the policy manager.
     * @param functionsToAssign List of functions to assign.
     * @return List of invokers with assigned functions.
     */
    public List<Invoker<I, O>> usePolicyManager(Policy<I, O> policy, List<String> functionsToAssign) {
        // Set the policy manager with the provided policy
        policyManager.setPolicyManagerStrategy(policy);

        return policyManager.assignFunctions(invokers, functionMap, ramMap, functionsToAssign);
    }


    /**
     * Method to use the policy manager to assign functions to invokers.
     * @param policy Policy to be used by the policy manager.
     * @param functionsToAssign List of functions to assign.
     * @param inputList List of inputs for the functions.
     */
    public void invokeFunctionsPolicyManager(Policy<I, O> policy, List<String> functionsToAssign, List<I> inputList) {

        policyManager.setPolicyManagerStrategy(policy);
        System.out.println("----------------------------------");
        System.out.println("Using Policy: " + policyManager.getPolicyName());
        System.out.println("----------------------------------\n");

        System.out.println("RAM requirements of the functions: " + ramMap);
        System.out.println("Functions to assign: " + functionsToAssign + "\n");
        System.out.println("Invokers of the controller: " + invokers);
        System.out.println("Total memory of the invokers: " + getInvokerMemoryUsage() + "\n");

        List<Invoker<I, O>> assignedInvokers = policyManager.assignFunctions(invokers, functionMap, ramMap, functionsToAssign);
        System.out.println("Assigned invokers: " + assignedInvokers + "\n");
        System.out.println("Function assignments:");
        // For invoking every invoker from the assignedInvokers list
        for (int j = 0; j < functionsToAssign.size(); j++) {
            I input = inputList.get(j);
            Invoker<I, O> assignedInvoker = assignedInvokers.get(j);
            String functionName = functionsToAssign.get(j);
            System.out.println("Function " + functionName + ": \t Result :" + invokeWithInvoker(functionName, input, assignedInvoker) + " assigned to Invoker with id "+ assignedInvoker.getInvokerId() + " :" + assignedInvoker);
        }
        assignedInvokers.clear();
    }

    /**
     * Method to use the policy manager to assign functions to invokers.
     * @param policy Policy to be used by the policy manager.
     * @param functionsToAssign List of functions to assign.
     * @param inputList List of inputs for the functions.
     */
    public void invokeFunctionsPolicyManagerAsync(Policy<I, O> policy, List<String> functionsToAssign, List<I> inputList) {
        policyManager.setPolicyManagerStrategy(policy);
        System.out.println("----------------------------------");
        System.out.println("Using Policy: " + policyManager.getPolicyName());
        System.out.println("----------------------------------\n");

        System.out.println("RAM requirements of the functions: " + ramMap);
        System.out.println("Functions to assign: " + functionsToAssign + "\n");
        System.out.println("Invokers of the controller: " + invokers);
        System.out.println("Total memory of the invokers: " + getInvokerMemoryUsage() + "\n");

        List<Invoker<I, O>> assignedInvokers = policyManager.assignFunctions(invokers, functionMap, ramMap, functionsToAssign);
        System.out.println("Assigned invokers: " + assignedInvokers + "\n");
        System.out.println("Function assignments:");

        // For invoking every invoker from the assignedInvokers list
        List<Future<O>> futures = new ArrayList<>();

        for (int j = 0; j < functionsToAssign.size(); j++) {
            I input = inputList.get(j);
            Invoker<I, O> assignedInvoker = assignedInvokers.get(j);
            String functionName = functionsToAssign.get(j);

            // Use invokeAsyncWithInvoker for asynchronous invocation
            Future<O> future = invokeAsyncWithInvoker(functionName, input, assignedInvoker);
            futures.add(future);
        }

        // Wait for all asynchronous invocations to complete
        for (int j = 0; j < functionsToAssign.size(); j++) {
            try {
                Future<O> future = futures.get(j);
                Invoker<I, O> assignedInvoker = assignedInvokers.get(j);
                String functionName = functionsToAssign.get(j);
                O result = future.get(); // This blocks until the result is available
                System.out.println("Function " + functionName + ": \t Result :" + result + " assigned to Invoker with id " + assignedInvoker.getInvokerId() + " :" + assignedInvoker);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace(); // Handle exceptions appropriately
            }
        }

        assignedInvokers.clear();
    }


    /**
     * Method to get the policy manager name.
     *
     * @return name of the policy manager
     */
    public String getPolicyManager() {
        return policyManager.getPolicyName();
    }


    /**
     * Method to get the function name
     *
     * @param function function to get the name
     * @return name of the function
     * @throws IllegalArgumentException If the specified function is not found.
     */
    public String getFunctionName(Function<I, O> function) {
        for (Map.Entry<String, Function<I, O>> entry : functionMap.entrySet()) {
            if (entry.getValue().equals(function)) {
                return entry.getKey();
            }
        }
        throw new IllegalArgumentException("Function not found");
    }

    /**
     * Method to update the metrics.
     *
     * @param metricsData metrics data to be updated
     */
    public void updateMetrics(MetricsData metricsData) {
        collectedMetrics.add(metricsData);
    }

    /**
     * Method to get the maximum execution time of the functions
     *
     * @return  maximum execution time
     */
    public long getMaxExecutionTime() {
        return collectedMetrics.stream()
                .mapToLong(MetricsData::getExecutionTime)
                .max()
                .orElse(0);
    }

    /**
     * Method to get the minimum execution time of the functions
     *
     * @return minimum execution time
     */
    public long getMinExecutionTime() {
        return collectedMetrics.stream()
                .mapToLong(MetricsData::getExecutionTime)
                .min()
                .orElse(0);
    }

    /**
     * Method to get the average execution time of the functions
     *
     * @return average execution time
     */
    public double getAverageExecutionTime() {
        return collectedMetrics.stream()
                .mapToLong(MetricsData::getExecutionTime)
                .average()
                .orElse(0);
    }

    /**
     * Method to get the total execution time of the functions
     *
     * @return total execution time
     */
    public long getTotalExecutionTime() {
        return collectedMetrics.stream()
                .mapToLong(MetricsData::getExecutionTime)
                .sum();
    }

    /**
     * Method to get the memory used by the functions
     *
     * @return memory used
     */
    public int getMaxMemoryUsed() {
        return collectedMetrics.stream()
                .mapToInt(MetricsData::getMemoryUsed)
                .max()
                .orElse(0);
    }


    /**
     * Method to get the function map
     *
     * @return function map
     */
    public Map<String, Function<I, O>> getFunctionMap() {
        return this.functionMap;
    }

    /**
     * Method to get the ram map
     *
     * @return ram map
     */
    public  Map<String, Integer> getRamMap() {
        return this.ramMap;
    }


    /**
     * Method to create proxy
     * @param target target object
     * @return proxy object of the target
     */
    public Object createProxy(Object target){
        Class targetClass = target.getClass();
        Class[] interfaces = targetClass.getInterfaces();

        DynamicProxy proxy = new DynamicProxy(target, this);
        return Proxy.newProxyInstance(targetClass.getClassLoader(), interfaces, proxy);
    }


}

