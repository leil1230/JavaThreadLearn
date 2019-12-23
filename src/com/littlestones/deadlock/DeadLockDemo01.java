package com.littlestones.deadlock;

/**
 * @program: JavaThreadLearn
 * @description: Java多线程死锁
 * @author: Leil
 * @create: 2019-12-23 10:22
 */


public class DeadLockDemo01 {

    private static Object lock1 = new Object();
    private static Object lock2 = new Object();

    public static void main(String[] args) {
        new Thread() { //线程1
            public void run() {
                while (true) {
                    synchronized (lock1) {
                        System.out.println(this.getName() + ":获取lock1锁");
                        synchronized (lock2) {
                            System.out.println(this.getName() + ":获取lock2锁");
                        }
                    }
                }
            }
        }.start();

        new Thread() { //线程2
            public void run() {
                while (true) {
                    synchronized (lock2) {
                        System.out.println(this.getName() + ":获取lock2锁");
                        synchronized (lock1) {
                            System.out.println(this.getName() + "::获取lock1锁");
                        }
                    }
                }
            }
        }.start();
    }
}
