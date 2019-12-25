package com.littlestones.threadlocal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: JavaThreadLearn
 * @description: 简单的ThreadLocal实现
 * @author: Leil
 * @create: 2019-12-25 15:52
 */
public class SimpleThreadLocal<T> {

    private Map<Thread, T> map = Collections.synchronizedMap(new HashMap<>());

    public void set(T val) {
        map.put(Thread.currentThread(), val);
    }

    public T get() {
        Thread thread = Thread.currentThread();
        if (map.containsKey(thread)) {
            return map.get(thread);
        } else {
            T t = init();
            map.put(thread, t);
            return t;
        }
    }

    public void remove() {
        map.remove(Thread.currentThread());
    }

    public T init() {
        return null;
    }

}
