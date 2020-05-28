package com.sbt.jschool.multithreading.threadpool;

public interface ThreadPool {
    void start();
    void execute(Runnable runnable);
}
