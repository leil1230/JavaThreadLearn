package com.littlestones.communication;

/**
 * @program: JavaThreadLearn
 * @description: Java线程通信示例
 * @author: Leil
 * @create: 2019-12-23 17:44
 */

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 需求:第一个线程写入(input)用户，另一个线程取读取(out)用户。实现写一个，读一个操作。
 */

class User {
    public String name;
    public String sex;
    // 读写标志(true：表示已经写入|false：表示未写入)
    public boolean flag = false;

    @Override
    public String toString() {
        return "name:" + this.name + "=======" + "sex:" + this.sex;
    }
}

class InputThread extends Thread {
    private User user;

    InputThread(User user) {
        this.user = user;
    }

    @Override
    public void run() {
        int count = 0;
        while (true) {
            synchronized (user) {
                if (this.user.flag) {
                    try {
                        // 使当前线程等待
                        this.user.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (count == 0) {
                    this.setVal("小红", "女");
                } else {
                    this.setVal("小明", "男");
                }
                count = (count + 1) % 2;

                this.user.flag = true;
                // 通知另一个线程解除等待状态，继续执行
                this.user.notify();
            }
        }
    }

    private void setVal(String name, String sex) {
        this.user.name = name;
        this.user.sex = sex;
    }

}


class OutThread extends Thread {

    private User user;

    OutThread(User user) {
        this.user = user;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (user) {
                if (!this.user.flag) {
                    try {
                        this.user.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(this.user.toString());
                this.user.flag = false;
                this.user.notify();
            }
        }
    }
}

public class ThreadCommDemo {
    public static void main(String[] args) {
        User user = new User();
        InputThread inputThread = new InputThread(user);
        OutThread outThread = new OutThread(user);
        inputThread.start();
        outThread.start();
    }


}
