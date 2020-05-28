package com.sbt.jschool.multithreading.threadpool;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) {
        FixedThreadPool pool = new FixedThreadPool(4);
        Runnable r = () -> {
            int res = 1;
            for(int i = 1; i < 5; i++){
                res *= i;
            }
            System.out.println(Thread.currentThread() + " " + res);
        };
//        pool.start();
//        pool.execute(r);
//        pool.execute(r);
//        pool.execute(r);
//        pool.execute(r);
//        pool.execute(r);
//        pool.shutdown();
        ScalableThreadPool scalable = new ScalableThreadPool(2, 4);
        scalable.start();
        scalable.execute(r);
        scalable.execute(r);
        scalable.execute(r);
        scalable.execute(r);
        scalable.execute(r);
        scalable.execute(r);


        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        scalable.execute(r);


        scalable.shutdown();
    }
}
