package com.littlestones.daemon;

/**
 * @program: JavaThreadLearn
 * @description: 守护线程
 * @author: Leil
 * @create: 2019-12-24 10:14
 */

class DaemonThread extends Thread {
    @Override
    public void run() {
        while (true) {
            System.out.println("我是守护线程。。。只要守护的线程不挂，我永远都不挂");
        }
    }
}

public class DaemonThreadDemo {

    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                DaemonThread daemonThread = new DaemonThread();
                daemonThread.setDaemon(true);
                daemonThread.start();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("主线程运行完成退出。。。");
            }
        });

        thread.start();
    }

}
