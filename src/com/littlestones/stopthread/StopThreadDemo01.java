package com.littlestones.stopthread;

/**
 * @program: JavaThreadLearn
 * @description: 使用标志正常退出线程
 * @author: Leil
 * @create: 2019-12-24 09:26
 */

class Thread01 extends Thread {

    volatile boolean flag = true;

    @Override
    public void run() {
        while (flag) {
            try {
                // 可能发生异常的操作
                System.out.println(getName() + "线程一直在运行。。。");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                this.stopThread();
            }
        }
    }

    public void stopThread() {
        System.out.println("线程停止运行。。。");
        this.flag = false;
    }
}

public class StopThreadDemo01 {

    public static void main(String[] args) {
        Thread01 thread01 = new Thread01();
        thread01.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread01.stopThread();
    }
}
