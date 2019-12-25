package com.littlestones.threadlocal;

/**
 * @program: JavaThreadLearn
 * @description: ThreadLocal示例
 * @author: Leil
 * @create: 2019-12-25 15:26
 */

class Entity {
    // 使用重写initialValue方法初始化值
//    public ThreadLocal<Integer> seqNum = new ThreadLocal<Integer>() {
//        @Override
//        protected Integer initialValue() {
//            return 0;
//        }
//    };

    // 使用lambda表达式初始化值
    public ThreadLocal<Integer> seqNum = ThreadLocal.withInitial(() -> 0);

    public int getNextSeqNum() {
        seqNum.set(seqNum.get() + 1);
        return seqNum.get();
    }
}


class ThreadDemo extends Thread {

    private Entity entity;

    public ThreadDemo(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            System.out.println(getName() + "=====" + entity.getNextSeqNum());
        }
    }
}

public class ThreadLocalDemo {
    public static void main(String[] args) {
        Entity entity = new Entity();
        ThreadDemo threadDemo01 = new ThreadDemo(entity);
        ThreadDemo threadDemo02 = new ThreadDemo(entity);
        threadDemo01.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadDemo02.start();
    }
}
