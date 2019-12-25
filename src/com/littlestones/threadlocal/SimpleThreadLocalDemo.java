package com.littlestones.threadlocal;

/**
 * @program: JavaThreadLearn
 * @description: ThreadLocal示例
 * @author: Leil
 * @create: 2019-12-25 15:26
 */

class Entity01 {

    public SimpleThreadLocal<Integer> seqNum = new SimpleThreadLocal<Integer>() {
        @Override
        public Integer init() {
            return 0;
        }
    };

    public int getNextSeqNum() {
        seqNum.set(seqNum.get() + 1);
        return seqNum.get();
    }
}


class ThreadDemo01 extends Thread {

    private Entity01 entity;

    public ThreadDemo01(Entity01 entity) {
        this.entity = entity;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            System.out.println(getName() + "=====" + entity.getNextSeqNum());
        }
    }
}

public class SimpleThreadLocalDemo {
    public static void main(String[] args) {
        Entity01 entity = new Entity01();
        ThreadDemo01 threadDemo01 = new ThreadDemo01(entity);
        ThreadDemo01 threadDemo02 = new ThreadDemo01(entity);
        threadDemo01.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadDemo02.start();
    }
}
