package com.sbt.jschool.jmm;

import com.sbt.jschool.jmm.execution_manager.Context;
import com.sbt.jschool.jmm.execution_manager.ContextManager;
import com.sbt.jschool.jmm.execution_manager.Executor;
import com.sbt.jschool.jmm.task.Task;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
//        Callable<String> callable = ()-> "Meow!";
//        Task<String> task = new Task<>(callable);
//        for(int i = 0; i< 20; i++){
//           Runnable runnable = () -> System.out.println(task.get());
//           runnable.run();
//        }
        Runnable[] taskQueue = new Runnable[4];
        taskQueue[0] = ()->{
            System.out.println("Meow");
        };
        taskQueue[1] = ()->{
            throw new RuntimeException();
        };
        taskQueue[2] = ()->{
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Runnable callback = ()->{
            System.out.println("I am callback!");
        };

        taskQueue[3] = ()-> System.out.println(3274* 36428);

        Executor executor = new Executor(2);
        Context context = executor.execute(callback, taskQueue);
        Thread.sleep(2000);
        context.interrupt();

        System.out.println(context.getFailedTaskCount());
        System.out.println(context.getCompletedTaskCount());
        System.out.println(context.isFinished());

    }
}
