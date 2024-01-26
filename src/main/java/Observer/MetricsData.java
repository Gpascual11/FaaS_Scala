package Observer;

/**
 * Class that represents the data that is sent to the observers.
 */
public class MetricsData {

    // Execution time of the function.
    private final long executionTime;
    // Invoker that executed the function.
    private final int invoker;
    // Amount of memory used by the function.
    private final int memoryUsed;

    /**
     * Constructor for the MetricsData class.
     *
     * @param executionTime Execution time of the function.
     * @param invoker Invoker that executed the function.
     * @param memoryUsed Amount of memory used by the function.
     */
    public MetricsData(long executionTime, int invoker, int memoryUsed) {
        this.executionTime = executionTime;
        this.invoker = invoker;
        this.memoryUsed = memoryUsed;
    }


    /**
     * Getter for the execution time.
     * @return executionTime Execution time of the function.
     */
    public long getExecutionTime() {
        return executionTime;
    }


    /**
     * Getter for the invoker.
     * @return invoker Getter of the invoker that executed the function.
     */
    public int getInvoker() {
        return invoker;
    }


    /**
     * Getter for the memory used.
     * @return memoryUsed Amount of memory used by the function.
     */
    public int getMemoryUsed() {
        return memoryUsed;
    }

}
