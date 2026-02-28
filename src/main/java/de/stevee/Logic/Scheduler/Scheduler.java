package de.stevee.Logic.Scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class Scheduler {
    private final ScheduledExecutorService executor;

    public Scheduler() {
        // Create a scheduled executor with unlimited threads
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
    public void executeAtFixedRate(Runnable task, long initialDelay, long period, TimeUnit unit) {
        executor.scheduleAtFixedRate(task, initialDelay, period, unit);
    }

    /**
     * Execute a task repeatedly with fixed delay between executions
     */
    public void executeWithFixedDelay(Runnable task, long initialDelay, long delay, TimeUnit unit) {
        executor.scheduleWithFixedDelay(task, initialDelay, delay, unit);
    }

    /**
     * Shutdown the scheduler gracefully
     */
    public void shutdown() {
        executor.shutdown();
    }

    /**
     * Check if scheduler is terminated
     */
    public boolean isTerminated() {
        return executor.isTerminated();
    }
}
