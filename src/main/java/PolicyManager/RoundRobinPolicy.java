package PolicyManager;

import Components.Invoker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Class implementing a round-robin policy for selecting invokers with available resources.
 * @param <I> Type of input data for the function.
 * @param <O> Type of output data for the function.
 */
public class RoundRobinPolicy<I, O> implements Policy<I, O> {

    // Current index of the invoker to be assigned
    private int currentIndex;


    /**
     * Constructor for the RoundRobinPolicy class.
     *
     */
    public RoundRobinPolicy() {
        this.currentIndex = -1;
    }
    /**
     * Method to assign functions to invokers based on the strategy.
     *
     * @param invokers List of available invokers.
     * @param functionMap Map associating functions with their RAM requirements.
     * @param ramMap Map associating functions with their RAM requirements.
     * @return List of invokers with assigned functions.
     * @throws IllegalStateException if the lists of invokers, functions, and RAM requirements have different lengths.
     */
    public List<Invoker<I, O>> assignFunctions(List<Invoker<I, O>> invokers, Map<String, Function<I, O>> functionMap, Map<String, Integer> ramMap, List<String> functionsToAssign) {
        List<Invoker<I, O>> assignedInvokers = new ArrayList<>();
        List<Integer> totalMemory = new ArrayList<>();

        for (Invoker<I, O> invoker : invokers) {
            totalMemory.add(invoker.getTotalMemory().get());
        }

        for (String functionToAssignName : functionsToAssign) {
            for (Map.Entry<String, Function<I, O>> entry : functionMap.entrySet()) {
                if (entry.getKey().equals(functionToAssignName)) {
                    String functionName = entry.getKey();

                    int ramRequirement = ramMap.getOrDefault(functionName, 1);
                    int invokerId = getNextInvokerWithResources(invokers, ramRequirement, totalMemory);
                    if (totalMemory.get(invokerId) >= ramRequirement) {
                        assignedInvokers.add(invokers.get(invokerId));
                        totalMemory.set(invokerId, totalMemory.get(invokerId) - ramRequirement);
                    } else {
                        throw new IllegalStateException("Not enough available memory for function assignment");
                    }
                }
            }
        }
        return assignedInvokers;
    }

    /**
     * Method to get the next invoker with available resources based on the round-robin policy.
     *
     * @param invokers List of available invokers.
     * @param ramRequirement RAM requirement of the function to be assigned.
     * @param totalMemory List of available memory for each invoker.
     * @return The next invoker with available resources.
     * @throws IllegalStateException If no invokers with available resources are found.
     */
    public int getNextInvokerWithResources(List<Invoker<I, O>> invokers, int ramRequirement, List<Integer> totalMemory) {
        if (invokers.isEmpty()) {
            throw new IllegalStateException("No invokers available");
        }

        int initialIndex = currentIndex;

        do {
            currentIndex = (currentIndex + 1) % invokers.size();

            if (totalMemory.get(currentIndex) >= ramRequirement) {
                return currentIndex;
            }

        } while (currentIndex != initialIndex);

        throw new IllegalStateException("No invokers with available resources");
    }
}