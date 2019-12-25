package com.littlestones.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: JavaThreadLearn
 * @description:
 * @author: Leil
 * @create: 2019-12-25 16:37
 */
public class FixedThreadPoolDemo {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        for (int i = 1; i <= 10; i++) {
            final int index = i;
            threadPool.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "=====" + index);
            });
        }
    }
}
