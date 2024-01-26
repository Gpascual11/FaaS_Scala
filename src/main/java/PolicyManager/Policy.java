package PolicyManager;

import Components.Invoker;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Interface for the policy manager strategy.
 * @param <I> Type of input data for the function.
 * @param <O> Type of output data for the function.
 */
public interface Policy<I, O> {
    /**
     * Assigns functions to invokers.
     *
     * @param invokers List of invokers.
     * @param functionMap Map of functions and their RAM requirements.
     * @param ramMap Map of invokers and their RAM.
     * @param functionsToAssign List of functions to assign.
     * @return List of invokers with assigned functions.
     */
    List<Invoker<I, O>> assignFunctions (List<Invoker<I, O>> invokers, Map<String, Function<I, O>> functionMap, Map<String, Integer> ramMap, List<String> functionsToAssign);
}