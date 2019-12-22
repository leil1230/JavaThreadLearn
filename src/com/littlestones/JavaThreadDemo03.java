package com.littlestones;


public class JavaThreadDemo03 {

    public static void main(String[] args) {
        System.out.println("主线程");
        // 使用实现接口的方式
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("这里是子线程");
                System.out.println("线程名称：" + Thread.currentThread().getName());
                System.out.println("线程ID：" + Thread.currentThread().getId());
            }
        });
        thread1.start();

        // 使用lambda表达式
        Thread thread2 = new Thread(() -> {
            System.out.println("这里是子线程");
            System.out.println("线程名称：" + Thread.currentThread().getName());
            System.out.println("线程ID：" + Thread.currentThread().getId());
        });
        thread2.start();
    }
}
