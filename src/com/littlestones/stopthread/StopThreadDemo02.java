package com.littlestones.stopthread;

/**
 * @program: JavaThreadLearn
 * @description: 使用标志正常退出线程
 * @author: Leil
 * @create: 2019-12-24 09:26
 */

class Thread02 extends Thread {
    private boolean flag = true;

    @Override
    public void run() {
        while (flag) {
            synchronized (this) {
//                try {
//                    wait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                    this.stopThread();
//                }

                try {
                    sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    this.stopThread();
                }
            }
        }
    }

    public void stopThread() {
        System.out.println("线程已经退出。。。");
        this.flag = false;
    }
}

public class StopThreadDemo02 {

    public static void main(String[] args) {
        Thread02 thread02 = new Thread02();
        thread02.start();
        System.out.println("线程开始");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread02.interrupt();
    }
}
