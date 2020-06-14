package com.sbt.jschool.jmm.execution_manager;

public interface ExecutionManager {
    Context execute(Runnable callback, Runnable... tasks);
}
