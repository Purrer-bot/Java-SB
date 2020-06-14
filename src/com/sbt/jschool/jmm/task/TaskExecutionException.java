package com.sbt.jschool.jmm.task;

public class TaskExecutionException extends RuntimeException {
    public TaskExecutionException(){
        super();
        System.out.println("Thread: " + Thread.currentThread().getName());
    }
    public TaskExecutionException(String message, Throwable cause){
        super(message, cause);
        System.out.println("Thread: " + Thread.currentThread().getName());
    }
}
