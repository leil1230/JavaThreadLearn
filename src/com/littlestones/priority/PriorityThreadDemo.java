package com.littlestones.priority;

/**
 * @program: JavaThreadLearn
 * @description: 线程优先级示例
 * @author: Leil
 * @create: 2019-12-24 10:59
 */


class PriorityThread extends Thread {

    public PriorityThread(String name) {
        this.setName(name);
    }

    @Override
    public void run() {
        System.out.println(getName() + "==========" + getId());
    }
}

public class PriorityThreadDemo {

    public static void main(String[] args) {
        PriorityThread priorityThread1 = new PriorityThread("priority_10");
        PriorityThread priorityThread2 = new PriorityThread("priority_default");
        priorityThread1.setPriority(10);
        priorityThread2.start();
        priorityThread1.start();
    }
}
