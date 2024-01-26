package Observer;

/**
 * Interface for the observer of the metrics.
 */
public interface MetricsObserver {

    /**
     * Update the metrics.
     *
     * @param data The data to update the metrics.
     */
    void updateMetrics(MetricsData data);
    
}
