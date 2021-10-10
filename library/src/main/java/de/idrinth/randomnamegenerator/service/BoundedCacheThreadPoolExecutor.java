package de.idrinth.name_generator.service;

import de.idrinth.name_generator.ThreadPoolStatus;
import java.util.Comparator;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BoundedCacheThreadPoolExecutor extends ThreadPoolExecutor implements ThreadPoolStatus {
    protected volatile int waiting = 0;

    public BoundedCacheThreadPoolExecutor(int maximumCPUPoolSize) {
        this(maximumCPUPoolSize,new ExpectedCostRunnableComparator());
    }

    public BoundedCacheThreadPoolExecutor(int maximumCPUPoolSize, Comparator<Runnable> comparator) {
        super(
                0,//minimum pool size
                maximumCPUPoolSize*Runtime.getRuntime().availableProcessors(),
                1,//expiration time
                TimeUnit.MINUTES,//expiration time unit
                new PriorityBlockingQueue<Runnable>(1, comparator)
        );
    }
    @Override
    public synchronized boolean isIdle() {
        return waiting <= 0;
    }
    @Override
    public int getWaiting() {
        return waiting;
    }
    @Override
    protected void afterExecute(Runnable r, Throwable thrwbl) {
        super.afterExecute(r, thrwbl);
        waiting--;
    }
    @Override
    public Future submit(Runnable filler) {
        waiting++;
        return super.submit(filler);
    }
    @Override
    public Future submit(Callable clbl) {
        waiting++;
        return super.submit(clbl);
    }
}
