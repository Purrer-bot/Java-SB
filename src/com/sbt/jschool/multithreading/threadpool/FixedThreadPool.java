package com.sbt.jschool.multithreading.threadpool;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Function;

public class FixedThreadPool implements ThreadPool {
    private BlockingQueue<Runnable> taskQueue;
    private ArrayList<WorkerThread> workers;
    private volatile boolean isRun = false;

    public FixedThreadPool(int poolSize) {
        if (poolSize <= 0) {
            throw new IllegalArgumentException();
        }
        this.taskQueue = new LinkedBlockingQueue<>();
        this.workers = new ArrayList<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            this.workers.add(new WorkerThread());
        }

    }

    @Override
    public void start() {
        workers.forEach(WorkerThread::start);
        this.isRun = true;
    }

    @Override
    public void execute(Runnable runnable) {
        taskQueue.offer(runnable);
    }

    public void abort() {
        this.isRun = false;
    }

    public void shutdown() {
        Runnable r = ()->{
            while(true) {
                if (workers.stream().allMatch(WorkerThread::isIdle) && taskQueue.isEmpty()) {
                    isRun = false;
                    break;
                }
            }
        };
        r.run();
    }

    private final class WorkerThread extends Thread {
        volatile boolean isIdle = true;
        @Override
        public void run() {
            while (isRun) {
                Runnable task = taskQueue.poll();
                if (task != null) {
                    this.isIdle = false;
                    task.run();
                }
                this.isIdle = true;
            }
        }

        boolean isIdle() {
            return isIdle;
        }
    }

}

