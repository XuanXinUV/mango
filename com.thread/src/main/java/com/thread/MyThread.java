package com.thread;

import java.util.Date;

public class MyThread extends Thread {
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("MyThread"+ " " + Thread.currentThread().getName() + " " + new Date().getTime());
        }
    }
}
