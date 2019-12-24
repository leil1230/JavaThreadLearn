package com.littlestones.volatiledemo;

/**
 * @program: JavaThreadLearn
 * @description: volatile示例
 * @author: Leil
 * @create: 2019-12-24 15:22
 */

class ThreadDemo extends Thread {

    volatile boolean flag;

    ThreadDemo(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        System.out.println(getName() + "线程开始运行。。。");
        while (flag) {
        }
        System.out.println(getName() + "线程已经结束。。。");
    }

    public void stopThread() {
        this.flag = false;
    }
}

public class VolatileThreadDemo {

    public static void main(String[] args) {
        ThreadDemo threadDemo = new ThreadDemo(true);
        threadDemo.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadDemo.stopThread();
    }
}
