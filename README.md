### Java多线程快速入门
[TOC]
#### 1、线程和进程的区别
> 进程是所有线程的集合，每一个线程是进程的一条执行路径。

#### 2、多线程的应用场景
> 多线程主要体现在提高程序的效率，比如迅雷多线程下载，多线程分批发送短信等。

#### 3、多线程的创建方式
##### （1）继承Thread类，重写run()方法
```java
class ThreadDemo extends Thread {
    @Override
    public void run() {
        System.out.println("这里是子线程");
        System.out.println("线程名称：" + this.getName());
        System.out.println("线程ID：" + this.getId());
    }
}

public class JavaThreadDemo01 {

    public static void main(String[] args) {
        System.out.println("这里是主线程");
        ThreadDemo threadDemo = new ThreadDemo();
        threadDemo.start();
    }
}
```