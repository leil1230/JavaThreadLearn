package com.littlestones.threadpool;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: JavaThreadLearn
 * @description: CachedThreadPool示例
 * @author: Leil
 * @create: 2019-12-25 16:32
 */
public class CachedThreadPoolDemo {

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        for (int i = 1; i <= 10; i++) {
            final int index = i;
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "=====" + index);
                }
            });
        }
    }

}
