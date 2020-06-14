package com.sbt.jschool.jmm.execution_manager;

import java.util.concurrent.atomic.AtomicInteger;

public class ContextManager implements Context {
    private volatile AtomicInteger completedTaskCounter;
    private volatile AtomicInteger failedTaskCounter;
    private volatile AtomicInteger interruptedTaskCounter;
    private Executor executor;

    public ContextManager(Executor executor) {
        this.completedTaskCounter = new AtomicInteger();
        this.failedTaskCounter = new AtomicInteger();
        this.interruptedTaskCounter = new AtomicInteger();
        this.executor = executor;
    }

    public void incCompletedTaskCounter() {
        completedTaskCounter.incrementAndGet();
    }

    public void incFailedTaskCounter() {
        failedTaskCounter.incrementAndGet();
    }

    public void incInterruptedTaskCounter() {
        interruptedTaskCounter.incrementAndGet();
    }

    public void decInterruptedTaskCounter() {
        if (interruptedTaskCounter.get() >= 0) {
            interruptedTaskCounter.decrementAndGet();
        }
    }

    public void setInterruptedTaskCounter(AtomicInteger interruptedTaskCounter) {
        this.interruptedTaskCounter = interruptedTaskCounter;
    }

    @Override
    public int getCompletedTaskCount() {
        return completedTaskCounter.get();
    }

    @Override
    public int getFailedTaskCount() {
        return failedTaskCounter.get();
    }

    @Override
    public int getInterruptedTaskCount() {
        return interruptedTaskCounter.get();
    }

    @Override
    public void interrupt() {
        this.executor.stop();
    }

    @Override
    public boolean isFinished() {
        int allTasks = interruptedTaskCounter.get() + completedTaskCounter.get();
        return failedTaskCounter.get() == 0 && allTasks == executor.getTaskQueue().size();
    }
}
