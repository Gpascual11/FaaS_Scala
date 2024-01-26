package Components;

import Observer.MetricsData;
import Observer.MetricSubject;
import Observer.MetricsObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/**
 * Class representing an invoker for executing functions synchronously and asynchronously.
 *
 * @param <I> Type of input data for the function.
 * @param <O> Type of output data for the function.
 */
public class Invoker <I, O> implements MetricSubject {

    // Static autogen counter id
    private static AtomicInteger idCounter = new AtomicInteger(0);

    // id of the invoker
    private int invokerId;

    // Executor service for handling asynchronous function execution
    private final ExecutorService executor;

    // Total available memory for the invoker
    private AtomicInteger totalMemory;

    // Object used for synchronization to prevent data race in multithreading
    private final Object memoryLock;

    // Observers for the invoker
    private final List<MetricsObserver> observers;

    // Cache for memoization (Save Action result, actionId, parameters)
    private final Map<String, O> cache;


    /**
     * Constructor for the Components.Invoker class.
     *
     * @param numThreads Number of threads in the thread pool.
     * @param totalMemory Total available memory for the invoker.
     */
    public Invoker(int numThreads, AtomicInteger totalMemory) {
        BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();
        this.executor = new ThreadPoolExecutor(numThreads, numThreads, 0L, TimeUnit.MILLISECONDS, taskQueue);
        this.totalMemory = totalMemory;
        this.memoryLock = new Object();
        this.invokerId = autogenerateId();
        this.observers = new ArrayList<>();
        this.cache = new HashMap<>(4, 4);
    }

    /**
     * Executes a function synchronously and returns the output.
     *
     * @param function Function to be executed.
     * @param input Input data for the function.
     * @param ram Amount of memory to be allocated.
     * @return Output of the function.
     */
    public O doFunction(Function<I, O> function, I input, int ram) {
        try {
            synchronized (memoryLock) {
                allocateMemory(ram);
            }
            long startTime = System.currentTimeMillis();
            O result = function.apply(input);
            long executionTime = System.currentTimeMillis() - startTime;

            MetricsData metricsData = new MetricsData(executionTime, invokerId, ram);

            if (observers != null) notifyObservers(metricsData);

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            synchronized (memoryLock) {
                releaseMemory(ram);
            }
        }
    }


    /**
     * Executes a function asynchronously and returns a future representing the output.
     *
     * @param function Function to be executed.
     * @param input Input data for the function.
     * @param ram Amount of memory to be allocated.
     * @return Future representing the output of the function.
     */
    public Future<O> doFunctionAsync(Function<I, O> function, I input, int ram) {
        return executor.submit(() -> {
            try {
                allocateMemory(ram);
                long startTime = System.currentTimeMillis();
                O result = function.apply(input);
                long executionTime = System.currentTimeMillis() - startTime;

                MetricsData metricsData = new MetricsData(executionTime, invokerId, ram);

                if (observers != null) notifyObservers(metricsData);

                return result;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                releaseMemory(ram);
            }
        });
    }


    /**
     * Shuts down the executor service of the invoker. Checks if the executor is already shut down.
     */
    public void shutdown() {
        if (!executor.isShutdown()) {
            idCounter = new AtomicInteger(0);
            executor.shutdownNow();
        }
    }

    /**
     * Getter for the total available memory.
     *
     * @return Total available memory.
     */
    public AtomicInteger getTotalMemory() {
        return totalMemory;
    }

    /**
     * Setter for the total available memory.
     *
     * @param totalMemory Total available memory.
     */
    public void setTotalMemory(AtomicInteger totalMemory) {
        synchronized (memoryLock) {
            this.totalMemory = totalMemory;
        }
    }

    /**
     * Method to allocate additional memory. Checks if the total available memory is enough.
     *
     * @param ram Amount of memory to be allocated.
     * @throws IllegalStateException If there is not enough available memory or if the amount of memory to be allocated is negative.
     * @throws IllegalArgumentException If the amount of memory to be allocated is negative.
     */
    public void allocateMemory(int ram) {
        if (ram < 0) {
            throw new IllegalArgumentException("Memory allocation must be a non-negative value.");
        }
        if (totalMemory.get() >= ram) {
            totalMemory.addAndGet(-ram);
        } else {
            throw new IllegalStateException("Not enough available memory.");
        }
    }

    /**
     * Method to release allocated memory. Checks if the amount of memory to be released is negative.
     *
     * @param ram Amount of memory to be released.
     * @throws IllegalArgumentException If the amount of memory to be released is negative.
     */
    public void releaseMemory(int ram) {
        if (ram < 0) {
            throw new IllegalArgumentException("Memory release must be a non-negative value.");
        }
        totalMemory.addAndGet(ram);
    }

    /**
     * Method to autogenerate id for the invoker.
     *
     * @return id of the new invoker
     */
    public static int autogenerateId() {
        return idCounter.incrementAndGet();
    }

    /**
     * Getter for the id of the invoker
     *
     * @return id of the invoker
     */
    public int getInvokerId() {
        return invokerId;
    }

    /**
     * Setter for the id of the invoker
     *
     * @param invokerId id of the invoker
     */
    public void setInvokerId(int invokerId) {
        this.invokerId = invokerId;
    }

    /**
     * Getter for the cache of the invoker
     *
     * @return cache of the invoker
     */
    public Map<String, O> getCache() {
        return cache;
    }

    /**
     * Attaches an observer to the invoker, adding it to the list of observers.
     *
     * @param observer observer to be attached
     */
    public void attachObserver(MetricsObserver observer) {
        observers.add(observer);
    }

    /**
     * Detaches an observer from the invoker, removing it from the list of observers.
     *
     * @param observer observer to be detatched
     */
    public void detatchObserver(MetricsObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notifies the observers of the invoker with the data passed as a parameter.
     *
     * @param data data to be sent to the observers
     */
    public void notifyObservers(MetricsData data) {
        for (MetricsObserver observer : observers) {
            observer.updateMetrics(data);
        }
    }
}
