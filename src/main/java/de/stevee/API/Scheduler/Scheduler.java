package de.stevee.API.Scheduler;

import java.util.concurrent.*;

public class Scheduler {
    public static final Scheduler scheduler = new Scheduler();
    private final ScheduledExecutorService executor;

    public Scheduler() {
        this.executor = Executors.newScheduledThreadPool(0);
    }

    /**
     * Execute a task immediately
     */
    public void execute(Runnable task) {
        executor.execute(task);
    }

    /**
     * Execute a task after a delay
     */
    public void executeAfter(Runnable task, long delay, TimeUnit unit) {
        executor.schedule(task, delay, unit);
    }

    /**
     * Execute a task repeatedly at fixed intervals
     */
    public Future<?> executeAtFixedRate(Runnable task, long initialDelay, long period, TimeUnit unit) {
        return executor.scheduleAtFixedRate(task, initialDelay, period, unit);
    }

    /**
     * Execute a task repeatedly with fixed delay between executions
     */
    public void executeWithFixedDelay(Runnable task, long initialDelay, long delay, TimeUnit unit) {
        executor.scheduleWithFixedDelay(task, initialDelay, delay, unit);
    }

    public void shutdown() {
        executor.shutdown();
    }

    public boolean isTerminated() {
        return executor.isTerminated();
    }
}
