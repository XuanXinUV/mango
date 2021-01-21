package com.thread;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ThreadTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /*MyThread myThread = new MyThread();
        myThread.start();*/

        for (int i = 0; i < 10; i++) {
            System.out.println("MainThread"+ " " + Thread.currentThread().getName() + " " + new Date().getTime());
        }

        /*Thread thread = new Thread(new MyRunnable());
        thread.start();*/

        FutureTask<String> task = new FutureTask<String>(new MyCallable());
        Thread thread = new Thread(task, "MyCallable");
        thread.start();
        String result = task.get();
        System.out.println("MyCallable result: " + result);
    }
}
