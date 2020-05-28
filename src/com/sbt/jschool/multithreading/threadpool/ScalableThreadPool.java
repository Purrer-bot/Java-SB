package com.sbt.jschool.multithreading.threadpool;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ScalableThreadPool implements ThreadPool {
    private BlockingQueue<Runnable> taskQueue;

    private ArrayList<WorkerThread> workers;
    private volatile boolean isRun = false;

    private int minPool;
    private int maxPool;

    public ScalableThreadPool(int min, int max) {
        if (min <= 0 || max <= 0 || min > max) {
            throw new IllegalArgumentException();
        }
        this.taskQueue = new LinkedBlockingQueue<>();

        this.workers = new ArrayList<>(min);
        this.minPool = min;
        this.maxPool = max;
        for (int i = 0; i < minPool; i++) {
            WorkerThread t = new WorkerThread();
            this.workers.add(t);
        }
    }

    @Override
    public void start() {
        this.workers.forEach(WorkerThread::start);
        this.isRun = true;

    }

    @Override
    public void execute(Runnable runnable) {
        if (taskQueue.isEmpty()) {

           workers.subList(minPool, workers.size()).removeIf(c -> workers.size() > minPool && c.isIdle);
        }
        taskQueue.offer(runnable);
        if (taskQueue.size() > workers.size() && workers.size() < maxPool) {
            WorkerThread w = new WorkerThread();
            w.start();
            workers.add(w);
            System.out.println("Added");
        }
        System.out.println(taskQueue.size());

    }

    public void shutdown() {
        Runnable r = () -> {
            while (true) {
                if (workers.stream().allMatch(WorkerThread::isIdle) && taskQueue.isEmpty()) {
                    isRun = false;
                    break;
                }
            }
        };
        r.run();
    }

    private final class WorkerThread extends Thread {
        volatile boolean isIdle;

        WorkerThread() {
            this.isIdle = true;
        }

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
