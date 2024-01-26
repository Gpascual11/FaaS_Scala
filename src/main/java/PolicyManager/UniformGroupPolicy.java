package PolicyManager;

import Components.Invoker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Class implementing a BigGroup policy for selecting invokers with available resources.
 *
 * @param <I> Type of input data for the function.
 * @param <O> Type of output data for the function.
 */
public class UniformGroupPolicy<I, O> implements Policy<I, O> {

    // Group size is the number of functions that will be assigned to each invoker
    private final int groupSize;

    /**
     * Constructor for the BigGroupPolicy class.
     *
     * @param groupSize Number of functions that will be assigned to each invoker.
     */
    public UniformGroupPolicy(int groupSize) {
        this.groupSize = groupSize;
    }

    /**
     * Method that assigns functions to invokers according to the BigGroup policy.
     *
     * @param invokers           List of invokers that will be assigned to the functions.
     * @param functionMap        List of functions to be assigned.
     * @param ramRequirements    Map containing the RAM requirements for each function.
     * @param functionsToAssign  List of functions to be assigned.
     * @return List of invokers that will be assigned to the functions.
     * @throws RuntimeException if there is not enough RAM in any invoker to assign the groupSize of functions
     */
    public List<Invoker<I, O>> assignFunctions(List<Invoker<I, O>> invokers, Map<String, Function<I, O>> functionMap, Map<String, Integer> ramRequirements, List<String> functionsToAssign) {
        List<Integer> totalMemory = new ArrayList<>();
        List<Invoker<I, O>> assignedInvokers = new ArrayList<>();

        for (Invoker<I, O> invoker : invokers) {
            totalMemory.add(invoker.getTotalMemory().get());
        }

        int invokersCount = invokers.size();
        int invokerIndex = 0;
        int totalFunctionsToAssign = functionsToAssign.size();

        for (int i = 0; i < totalFunctionsToAssign; ) {
            String functionName = functionsToAssign.get(i);

            int totalRamRequirement = ramRequirements.getOrDefault(functionName, 1) * groupSize;

            if (totalFunctionsToAssign - i < groupSize) {
                totalRamRequirement = ramRequirements.getOrDefault(functionName, 1) * (totalFunctionsToAssign - i);
            }

            int finalTotalRamRequirement = totalRamRequirement;

            boolean enoughRamAvailable = totalMemory.stream().anyMatch(ram -> ram >= finalTotalRamRequirement);

            if (!enoughRamAvailable) {
                // Throw an exception if there is not enough RAM in any invoker
                throw new RuntimeException("Insufficient RAM in any invoker to assign the groupSize of functions");
            }


            Invoker<I, O> selectedInvoker = invokers.get(invokerIndex);

            if (totalMemory.get(invokerIndex) >= totalRamRequirement) {
                for (int k = 0; k < groupSize && i < totalFunctionsToAssign; k++, i++) {
                    assignedInvokers.add(selectedInvoker);
                }
                // Update the memory of the selected invoker
                totalMemory.set(invokerIndex, totalMemory.get(invokerIndex) - totalRamRequirement);
            }

            // Move to the next invoker or loop back to the first
            invokerIndex = (invokerIndex + 1) % invokersCount;

        }

        return assignedInvokers;
    }
}
