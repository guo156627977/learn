package com.gzq.juc.queue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-04-18 14:34.
 */
public class MyBlockQueue {

    final Lock lock = new ReentrantLock();
    final Condition full = lock.newCondition();
    final Condition empty = lock.newCondition();
    //设置存放元素的Object数组，最多存放30个元素
    final  Object[] items = new Object[30];
    //放置元素的位置
    int putptr;
    //取元素的位置
    int takeptr;
    //队列中元素的个数，当达到队列的最大长度(这里是30)或者0时，分别会阻塞put或者take操作，当满足put或者take的条件时，会自动唤醒相应的线程
     int count;

    public void put(Object o) {
        lock.lock();
        System.out.println("lock the put");
        try {
            while (count == items.length) {
                System.out.println("Full, blocking put");
                try {
                    full.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            items[putptr] = o;
            count++;
            if (++putptr == items.length) {
                putptr = 0;
            }
            System.out.println("put item success");
            //唤醒由于队列空了，阻塞在empty上的线程
            empty.signal();
        } finally {
            System.out.println("unlock the put");
            lock.unlock();
        }
    }

    public Object take() {
        lock.lock();
        System.out.println("lock the take");
        try {
            while (count == 0) {
                try {
                    empty.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Object o = items[takeptr];
            count--;
            if (++takeptr == items.length) {
                takeptr = 0;
            }
            //唤醒由于队列满了，阻塞在full上的线程
            full.signal();
            return o;
        } finally {
            System.out.println("unlock the take");
            lock.unlock();
        }
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public static void main(String[] args) {
        MyBlockQueue myBlockQueue = new MyBlockQueue();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println(i);
                    myBlockQueue.put("Lelouch");
                    // myBlockQueue.put("Zero");
                    // myBlockQueue.put("CvShrimp");
                    // System.out.println(myBlockQueue.take());
                    // System.out.println(myBlockQueue.take());
                }

            }
        };

        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println(i);
                    System.out.println(myBlockQueue.take());
                }

            }
        };

        ExecutorService threadPool = Executors.newCachedThreadPool();
        Future<?> submit1 = threadPool.submit(runnable1);
        Future<?> submit = threadPool.submit(runnable);
        while (submit.isDone() && submit1.isDone()) {
            System.out.println("shutdown");
            threadPool.shutdown();
        }
    }
}
