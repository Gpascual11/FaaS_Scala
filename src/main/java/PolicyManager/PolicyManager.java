package PolicyManager;

import Components.Invoker;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Class implementing a policy manager for a Function-as-a-Service (FaaS) system.
 * Allows assignment of functions to invokers based on a specific strategy.
 * @param <I> Type of input data for the function.
 * @param <O> Type of output data for the function.
 */
public class PolicyManager<I, O> {

    /**
     * Strategy to be used by the policy manager.
     */
    private Policy<I,O> policy;

    /**
     * Constructor for the PolicyManager.PolicyManager class.
     *
     * @param policy Strategy to be used by the policy manager.
     */
    public PolicyManager(Policy<I,O> policy) {
        this.policy = policy;
    }

    /**
     * Getter for the policy manager strategy.
     *
     * @return Policy manager strategy.
     */
    public Policy<I, O> getPolicyManagerStrategy() {
        return policy;
    }

    /**
     * Setter for the policy manager strategy.
     *
     * @param policy Policy manager strategy.
     */
    public void setPolicyManagerStrategy(Policy<I, O> policy) {
        this.policy = policy;
    }

    /**
     * Getter for the policy manager name strategy
     *
     * @return name of the policy manager strategy
     */
    public String getPolicyName() {
        return policy.getClass().getSimpleName();
    }

    /**
     * Method to assign functions to invokers based on the strategy.
     *
     * @param invokers  List of available invokers.
     * @param functionMap   Map associating functions with their RAM requirements.
     * @param ramMap    Map associating functions with their RAM requirements.
     * @param functionsToAssign List of functions to assign.
     * @return List of invokers with assigned functions.
     * @throws IllegalArgumentException if the lists of invokers, functions, and RAM requirements have different lengths.
     */
    public List<Invoker<I, O>> assignFunctions(List<Invoker<I, O>> invokers, Map<String, Function<I, O>> functionMap, Map<String, Integer> ramMap, List<String> functionsToAssign) {
        if (functionMap.size() <= 0 || ramMap.size() <= 0 || invokers.size() <= 0) {
            System.out.println("List of invokers: " + invokers.size() + "or functions: " + functionMap.size() + " or RAM requirements: " + ramMap.size() + " are wrong");
            throw new IllegalArgumentException("Lists of invokers, functions, and RAM requirements must have the same length.");
        }
        return policy.assignFunctions(invokers, functionMap, ramMap, functionsToAssign);
    }
}