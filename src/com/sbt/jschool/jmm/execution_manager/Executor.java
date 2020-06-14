package com.sbt.jschool.jmm.execution_manager;

import com.sbt.jschool.multithreading.threadpool.FixedThreadPool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Executor implements ExecutionManager{
    private ExecutorService executor;
    private BlockingQueue<Runnable> taskQueue;
    private ContextManager context;
    List<Future> tasksList;

    public Executor(int poolSize){
        if(poolSize <= 0) throw new IllegalArgumentException();

        this.taskQueue = new LinkedBlockingQueue<>();
        this.executor = Executors.newFixedThreadPool(poolSize);
        this.context = new ContextManager(this);
        this.tasksList = Collections.synchronizedList(new ArrayList<>());
    }

    public Context execute(Runnable callback, Runnable... tasks) {
        this.taskQueue.addAll(Arrays.asList(tasks));
        this.context.setInterruptedTaskCounter(new AtomicInteger(tasks.length));

        Runnable taskRunnable = ()->{
            for(Runnable task : tasks){
                try {
                    executor.submit(task).get();
                } catch (InterruptedException | ExecutionException e) {
                    System.out.println("Error in execution");
                    context.incFailedTaskCounter();
                } catch(RejectedExecutionException e){
                    System.out.println("Rejected task");
                    break;
                }
                finally {
                    context.incCompletedTaskCounter();
                    this.taskQueue.remove(task);
                    this.context.decInterruptedTaskCounter();
                }
            }
            callback.run();
            executor.shutdown();
        };
        Thread taskThread = new Thread(taskRunnable);
        taskThread.start();

        return context;
    }

    void stop(){
        executor.shutdown();
    }

    BlockingQueue<Runnable> getTaskQueue(){
        return this.taskQueue;
    }
}
