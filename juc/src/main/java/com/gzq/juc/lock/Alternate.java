package com.gzq.juc.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-04-09 14:05.
 */
public class Alternate {

    private volatile int number = 1;

    ReentrantLock lock = new ReentrantLock();

    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();
    Condition condition3 = lock.newCondition();

    public void LoopA(int loops) {
        lock.lock();
        try {
            if (number != 1) {
                try {
                    condition1.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            System.out.println(Thread.currentThread().getName() + " :" + "A " + loops);
            number = 2;
            condition2.signal();
        } finally {
            lock.unlock();
        }

    }


    public void LoopB(int loops) {
        lock.lock();
        try {
            if (number != 2) {
                try {
                    condition2.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            System.out.println(Thread.currentThread().getName() + " :" + "B " + loops);
            number = 3;
            condition3.signal();
        } finally {
            lock.unlock();
        }

    }

    public void LoopC(int loops) {
        lock.lock();
        try {
            if (number != 3) {
                try {
                    condition3.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            System.out.println(Thread.currentThread().getName() + " :" + "C " + loops);
            number = 1;
            condition1.signal();
        } finally {
            lock.unlock();
        }

    }

}
