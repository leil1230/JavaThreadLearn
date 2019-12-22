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
##### (2)实现Runnable接口，重写run()方法
```java
class RunnableDemo implements Runnable {

    @Override
    public void run() {
        System.out.println("这里是子线程");
        System.out.println("线程名称：" + Thread.currentThread().getName());
        System.out.println("线程ID：" + Thread.currentThread().getId());
    }
}

public class JavaThreadDemo02 {

    public static void main(String[] args) {
        System.out.println("这里是主线程");
        RunnableDemo runnableDemo = new RunnableDemo();
        Thread thread = new Thread(runnableDemo);
        thread.start();
    }
}
```

##### (3)使用匿名内部类方式
```java
public class JavaThreadDemo03 {

    public static void main(String[] args) {
        System.out.println("主线程");
        // 使用实现接口的方式
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("这里是子线程");
                System.out.println("线程名称：" + Thread.currentThread().getName());
                System.out.println("线程ID：" + Thread.currentThread().getId());
            }
        });
        thread1.start();

        // 使用lambda表达式
        Thread thread2 = new Thread(() -> {
            System.out.println("这里是子线程");
            System.out.println("线程名称：" + Thread.currentThread().getName());
            System.out.println("线程ID：" + Thread.currentThread().getId());
        });
        thread2.start();
    }
}
```
##### (4)使用继承Thread类还是使用实现Runnable接口的方式新建线程好？
> 使用实现Runnable接口的方式好，因为java是单继承，所以继承Thread接口后不能继承其他父类，而实现Runnable接口后还可以实现其他接口，继承其他父类。

##### (5)启动线程调用start()方法还是run()方法？
> 调用run()方法是使用实例调用，是单线程调用，不会开启多线程。

#### 4、多线程运行状态
> ①新建状态：新建一个线程，线程还没有开始运行，此时线程就是新建状态

> ②就绪状态：当线程对象调用了start()方法即启动了线程，start()方法创建线程运行的系统资源，并调度线程运行run()方法。当start()方法返回后，线程就处于就绪状态。处于就绪状态的线程并不一定立即运行run()方法，线程还必须同其他线程竞争cpu时间，只有获得cpu时间才能运行线程。因为在单cpu的计算机系统中，不可能同时运行多个线程，一个时刻仅能有一个线程处于运行状态，因此可能存在多个处于就绪状态的线程，对于多个就绪状态的线程是由Java运行时系统的线程调度程序来调度的。

> ③运行状态：当线程获取cpu时间后，线程进入运行状态，真正开始执行run()方法。

> ④阻塞状态：a、线程通过调用sleep()方法进入睡眠；b、线程调用一个在I\O上被阻塞的操作；c、线程试图得到一个锁，而该锁正被其他线程持有；d、线程在等待某个触发条件。

> ⑤死亡状态：a、run()方法正常退出自然死亡；b、一个未捕获的异常终止了run()方法使线程猝死。

#### 5、判断线程的存活状态
> 调用isAlive()方法，如果是可运行或被阻塞，方法返回true。

> 线程是新建状态或线程死亡，返回false。