package com.littlestones;

class RunnableDemo implements Runnable {

    @Override
    public void run() {
        System.out.println("这里是子线程");
        System.out.println("线程名称：" + Thread.currentThread().getName());
        System.out.println("线程ID：" + Thread.currentThread().getId());
    }
}

public class JavaThreadDemo02 {

    public static void main(String[] args) {
        System.out.println("这里是主线程");
        RunnableDemo runnableDemo = new RunnableDemo();
        Thread thread = new Thread(runnableDemo);
        thread.start();
    }
}
