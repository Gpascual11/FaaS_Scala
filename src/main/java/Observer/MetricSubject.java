package Observer;

/**
 * Interface for the subject of the metrics.
 */
public interface MetricSubject {

    /**
     * Attach an observer to the subject.
     *
     * @param observer Observer to be attached.
     */
    void attachObserver(MetricsObserver observer);

    /**
     * Detach an observer from the subject.
     *
     * @param observer Observer to be detached.
     */
    void detatchObserver(MetricsObserver observer);

    /**
     * Notify all observers of the subject.
     *
     * @param data Data to be sent to the observers.
     */
    void notifyObservers(MetricsData data);

}
