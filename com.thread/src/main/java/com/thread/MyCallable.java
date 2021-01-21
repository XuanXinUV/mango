package com.thread;

import java.util.Date;
import java.util.concurrent.Callable;

public class MyCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
        for (int i = 0; i < 10; i++) {
            System.out.println("MyCallable"+ " " + Thread.currentThread().getName() + " " + new Date().getTime());
        }
        return "MyCallable end";
    }
}
