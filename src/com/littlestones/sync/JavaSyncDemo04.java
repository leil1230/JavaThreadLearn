package com.littlestones.sync;

/**
 * @program: JavaThreadLearn
 * @description: Java多线程间实现同步
 * @author: Leil
 * @create: 2019-12-23 10:22
 */

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 案例:需求现在有100张火车票，有两个窗口同时抢火车票，请使用多线程模拟抢票效果。
 */

class SellTicketRunnable04 implements Runnable {

    public AtomicInteger count = new AtomicInteger(100);

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (count.get() > 0) {
                int index = 100 - count.getAndDecrement() + 1;
                System.out.println(Thread.currentThread().getName() + "卖出第" + index + "张票");
            }
        }
    }
}

public class JavaSyncDemo04 {

    public static void main(String[] args) {
        SellTicketRunnable04 runnable = new SellTicketRunnable04();
        Thread sellThread1 = new Thread(runnable);
        Thread sellThread2 = new Thread(runnable);
        sellThread1.start();
        sellThread2.start();
    }
}
