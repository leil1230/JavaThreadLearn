package com.littlestones.sync;

/**
 * @program: JavaThreadLearn
 * @description: Java多线程间实现同步
 * @author: Leil
 * @create: 2019-12-23 10:22
 */

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 案例:需求现在有100张火车票，有两个窗口同时抢火车票，请使用多线程模拟抢票效果。
 */

class SellTicketRunnable03 implements Runnable {

    public int count = 100;

    private Lock lock = new ReentrantLock();

    @Override
    public void run() {
        while (count > 0) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();
            if (count > 0) {
                int index = 100 - count + 1;
                System.out.println(Thread.currentThread().getName() + "卖出第" + index + "张票");
                count--;
            }
            lock.unlock();
        }
    }
}

public class JavaSyncDemo03 {

    public static void main(String[] args) {
        SellTicketRunnable03 runnable = new SellTicketRunnable03();
        Thread sellThread1 = new Thread(runnable);
        Thread sellThread2 = new Thread(runnable);
        sellThread1.start();
        sellThread2.start();
    }
}
