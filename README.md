[TOC]
### Java多线程快速入门

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
> ①调用isAlive()方法，如果是可运行或被阻塞，方法返回true。

> ②线程是新建状态或线程死亡，返回false。

### Java多线程间同步

#### 1、什么是线程安全
> 通过一个案例了解线程安全

> 案例:需求现在有100张火车票，有两个窗口同时抢火车票，请使用多线程模拟抢票效果。

> 先来看一个线程不安全的例子
```java
class SellTicketRunnable implements Runnable {

    public int count = 100;

    @Override
    public void run() {
        while (count > 0) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int index = 100 - count + 1;
            System.out.println(Thread.currentThread().getName() + "卖出第" + index + "张票");
            count--;
        }
    }
}

public class JavaSyncDemo {

    public static void main(String[] args) {
        SellTicketRunnable runnable = new SellTicketRunnable();
        Thread sellThread1 = new Thread(runnable);
        Thread sellThread2 = new Thread(runnable);
        sellThread1.start();
        sellThread2.start();
    }
}
```
![Image text](https://raw.githubusercontent.com/leil1230/JavaThreadLearn/master/img/1577069014.jpg)
> 可以看到两个线程同时卖票的时候，会出现漏卖，多卖同一张票，还会出现超卖的问题，这就是线程不安全的问题。

> 当多个线程同时共享，同一个全局变量或静态变量，做写的操作时，可能会发生数据冲突问题，也就是线程安全问题。但是做读操作是不会发生数据冲突问题。

#### 2、线程安全问题的解决办法
##### (1)使用同步代码块
```java
class SellTicketRunnable implements Runnable {

    public int count = 100;

    private Object lock = new Object();

    @Override
    public void run() {
        while (count > 0) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock) {
                if (count > 0) {
                    int index = 100 - count + 1;
                    System.out.println(Thread.currentThread().getName() + "卖出第" + index + "张票");
                    count--;
                }
            }
        }
    }
}

public class JavaSyncDemo {

    public static void main(String[] args) {
        SellTicketRunnable runnable = new SellTicketRunnable();
        Thread sellThread1 = new Thread(runnable);
        Thread sellThread2 = new Thread(runnable);
        sellThread1.start();
        sellThread2.start();
    }
}
```
![Image text](https://raw.githubusercontent.com/leil1230/JavaThreadLearn/master/img/1577069997.jpg)
> 从上面的案例可以看出，使用synchronized同步代码块包裹住写操作，每个线程在调用同步代码块中逻辑的时候，都需要先获取同步锁，所以避免了多线程写操作数据的冲突问题。

##### (2)使用同步函数
```java
class SellTicketRunnable01 implements Runnable {

    public int count = 100;

    @Override
    public void run() {
        while (count > 0) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.sale();
        }
    }

    synchronized void sale() {
        if (count > 0) {
            int index = 100 - count + 1;
            System.out.println(Thread.currentThread().getName() + "卖出第" + index + "张票");
            count--;
        }
    }
}

public class JavaSyncDemo01 {

    public static void main(String[] args) {
        SellTicketRunnable01 runnable = new SellTicketRunnable01();
        Thread sellThread1 = new Thread(runnable);
        Thread sellThread2 = new Thread(runnable);
        sellThread1.start();
        sellThread2.start();
    }
}
```
> synchronized包裹的函数，其实就是给该函数块添加了一把this锁。

> 注意：synchronized 修饰静态方法使用锁是当前类的字节码文件（即`类名.class`），同理，如果在静态方法中添加个同步代码块，可以获取`类名.class`为代码块加锁

```java
class SellTicketRunnable02 implements Runnable {

    public static int count = 100;

    @Override
    public void run() {
        while (count > 0) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            SellTicketRunnable02.sale();
        }
    }

    static void sale() {
        synchronized (SellTicketRunnable02.class) {
            if (count > 0) {
                int index = 100 - count + 1;
                System.out.println(Thread.currentThread().getName() + "卖出第" + index + "张票");
                count--;
            }
        }
    }
}

public class JavaSyncDemo02 {

    public static void main(String[] args) {
        SellTicketRunnable02 runnable = new SellTicketRunnable02();
        Thread sellThread1 = new Thread(runnable);
        Thread sellThread2 = new Thread(runnable);
        sellThread1.start();
        sellThread2.start();
    }
}
```
##### (3)使用lock锁
```java
class SellTicketRunnable03 implements Runnable {

    public int count = 100;

    private Lock lock = new ReentrantLock();

    @Override
    public void run() {
        while (count > 0) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();
            if (count > 0) {
                int index = 100 - count + 1;
                System.out.println(Thread.currentThread().getName() + "卖出第" + index + "张票");
                count--;
            }
            lock.unlock();
        }
    }
}

public class JavaSyncDemo03 {

    public static void main(String[] args) {
        SellTicketRunnable03 runnable = new SellTicketRunnable03();
        Thread sellThread1 = new Thread(runnable);
        Thread sellThread2 = new Thread(runnable);
        sellThread1.start();
        sellThread2.start();
    }
}
```
> lock和synchronized的区别<br/>
> ①lock在使用时需要手动的获取锁和释放锁;<br/>②lock可以尝试非阻塞的获取锁，如果这一时刻锁没有被其他线程获取到，则成功获取并持有锁；<br/>③lock锁可以响应中断，当获取到锁的线程被中断时，中断异常会被抛出，同时锁被释放；<br/>④lock在指定截至时间之前获取锁，如果解释时间到了依旧无法获取锁，就返回。

```java
// lock锁的安全使用方法
class lockDemo {
    Lock lock = new ReentrantLock();
    void demoFun() {
        lock.lock();
        try {
            // 可能出现线程安全的操作
        } finally {
            lock.unlock();
        }
    }
}
```

##### (4)使用Java原子类
> `java.util.concurrent.atomic.AtomicBoolean;`
  `java.util.concurrent.atomic.AtomicInteger;`
  `java.util.concurrent.atomic.AtomicLong;`
  `java.util.concurrent.atomic.AtomicReference;`
 
 ```java
class SellTicketRunnable04 implements Runnable {

    public AtomicInteger count = new AtomicInteger(100);

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (count.get() > 0) {
                int index = 100 - count.getAndDecrement() + 1;
                System.out.println(Thread.currentThread().getName() + "卖出第" + index + "张票");
            }
        }
    }
}

public class JavaSyncDemo04 {

    public static void main(String[] args) {
        SellTicketRunnable04 runnable = new SellTicketRunnable04();
        Thread sellThread1 = new Thread(runnable);
        Thread sellThread2 = new Thread(runnable);
        sellThread1.start();
        sellThread2.start();
    }
}
```

#### 3、死锁
> 先看一个死锁的示例
```java
public class DeadLockDemo01 {

    private static Object lock1 = new Object();
    private static Object lock2 = new Object();

    public static void main(String[] args) {
        new Thread() { //线程1
            public void run() {
                while (true) {
                    synchronized (lock1) {
                        System.out.println(this.getName() + ":获取lock1锁");
                        synchronized (lock2) {
                            System.out.println(this.getName() + ":获取lock2锁");
                        }
                    }
                }
            }
        }.start();

        new Thread() { //线程2
            public void run() {
                while (true) {
                    synchronized (lock2) {
                        System.out.println(this.getName() + ":获取lock2锁");
                        synchronized (lock1) {
                            System.out.println(this.getName() + "::获取lock1锁");
                        }
                    }
                }
            }
        }.start();
    }
}
```
![Image 死锁](https://raw.githubusercontent.com/leil1230/JavaThreadLearn/master/img/1577090534.jpg)
> 运行上面的代码，可以观察到线程卡死，就是出现了死锁

> 线程1先拿到lock1锁，再拿到lock2锁，执行完成后才能释放所有锁；<br/>线程2先拿到lock2锁，再拿到lock1锁，执行完成后才能释放所有锁。<br/>如果在线程1获取到lock1锁的时候，线程2获取到lock2还没释放，线程1无法获取lock2锁，也就无法释放lock2锁，这时系统就会出现死锁。

> 线程死锁的避免办法：**不要在同步中嵌套同步**

### Java多线程间通讯

> **多线程之间通讯，其实就是多个线程在操作同一个资源，但是操作的动作不同。**<br/>
#### 1、使用`wait()`和`notify()`方法在线程中通讯
> 需求:第一个线程写入(input)用户，另一个线程取读取(out)用户。实现写一个，读一个操作。
```java
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
```
> `wait()`、`notify()`、`notifyAll()`是三个定义在Object类中的方法，可以用来控制线程的状态。<br/>
> ①如果对象调用了`wait()`方法，就会使持有该对象的线程把该对象的控制权交出去，然后处于等待状态；<br/>
> ②如果对象调用了`notify()`方法，就会通知某个正在等待这个对象控制权的线程，可以继续运行；<br/>
> ③如果对象调用了`notifyAll()`方法，就会通知所有正在等待这个对象控制权的线程，可以继续运行。

> **`wait()`与`sleep()`的区别**<br/>
> `sleep()`方法属于`Thread`类中，`wait()`方法属于`Object`类中。`sleep()`方法导致程序暂停执行指定的时间，让出cpu给其他线程，但是监控状态依然保持，当指定的时间到了之后又会自动恢复运行状态。<br/>
> 在调用`sleep()`方法的过程中，线程不会释放对象锁。<br/>
> 调用`wait()`方法的时候，线程会放弃对象锁，进入等待此对象的等待锁定池，只有针对此对象调用`notify()`方法后本线程才进入对象锁定池准备，获取对象锁后进入运行状态。

> **注意：`wait()`方法需要包裹在同步块中**

#### 2、使用`condition`的`await()`和`signal()`方法在线程中通讯
```java
class User01 {
    public String name;
    public String sex;
    // 读写标志(true：表示已经写入|false：表示未写入)
    public boolean flag = false;

    public Lock lock = new ReentrantLock();

    Condition condition = lock.newCondition();

    @Override
    public String toString() {
        return "name:" + this.name + "=======" + "sex:" + this.sex;
    }
}

class InputThread01 extends Thread {
    private User01 user;

    InputThread01(User01 user) {
        this.user = user;
    }

    @Override
    public void run() {
        int count = 0;
        while (true) {
            this.user.lock.lock();
            if (this.user.flag) {
                try {
                    // 使当前线程等待
                    this.user.condition.await();
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
            this.user.condition.signal();
            this.user.lock.unlock();
        }
    }

    private void setVal(String name, String sex) {
        this.user.name = name;
        this.user.sex = sex;
    }

}


class OutThread01 extends Thread {

    private User01 user;

    OutThread01(User01 user) {
        this.user = user;
    }

    @Override
    public void run() {
        while (true) {
            this.user.lock.lock();
            if (!this.user.flag) {
                try {
                    this.user.condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(this.user.toString());
            this.user.flag = false;
            this.user.condition.signal();
            this.user.lock.unlock();
        }
    }
}

public class ThreadCommDemo01 {
    public static void main(String[] args) {
        User01 user = new User01();
        InputThread01 inputThread = new InputThread01(user);
        OutThread01 outThread = new OutThread01(user);
        inputThread.start();
        outThread.start();
    }
}
```
> `condition.await()`方法类似`wait()`方法，`condition.signal()`方法类似`notify()`方法。
