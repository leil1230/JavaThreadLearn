package com.littlestones.join;

/**
 * @program: JavaThreadLearn
 * @description: join()的用法
 * @author: Leil
 * @create: 2019-12-24 10:37
 */
public class JoinThreadDemo {

    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("这里是子线程");
                int count = 100;
                while (count > 0) {
                    System.out.println(Thread.currentThread().getName() + "=====" + count);
                    count--;
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("这里是主线程");
    }

}
