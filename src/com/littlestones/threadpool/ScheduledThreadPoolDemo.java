package com.littlestones.threadpool;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @program: JavaThreadLearn
 * @description: ScheduledThreadPool示例
 * @author: Leil
 * @create: 2019-12-25 16:42
 */
public class ScheduledThreadPoolDemo {

    public static void main(String[] args) {
        ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(3);
        for (int i = 1; i <= 10; i++) {
            threadPool.schedule(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "：延迟3秒打印log");
                }
            }, 3, TimeUnit.SECONDS);
        }
    }

}
