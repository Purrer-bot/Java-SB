package com.sbt.jschool.jmm.task;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantLock;

public class Task<T> {
    private T res;
    private Callable<T> task;
    private ReentrantLock lock;

    public Task(Callable<T> callable){
        this.lock = new ReentrantLock();
        this.task = callable;
    }
    public T get() {
        if(res != null){
            System.out.println("Cached result");
            return res;
        }
        lock.lock();
        try {
            if(this.res != null){
                return res;
            }
            this.res = task.call();
        }
        catch (Exception e){
            throw new TaskExecutionException("Error: ", e);
        }
        finally {
            System.out.println("Unlock");
            lock.unlock();
        }
        return res;
    }

}
