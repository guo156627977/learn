package com.gzq.scheduling;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SchedulingBlockQueue<T> extends LinkedBlockingQueue<T> {

    private boolean print = true;
    private String bizName = "";
    private final ReentrantLock lock = new ReentrantLock();

    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    private int capacity;

    public SchedulingBlockQueue(int capacity, String bizName) {
        super(capacity);
        this.capacity = capacity;
        this.bizName = bizName;
    }

    @Override
    public T take() throws InterruptedException {
        lock.lock();
        try {
            if (super.isEmpty()) {
                if (print) {
                    System.out.println(bizName + "|size为" + super.size() + " notFull唤醒");
                    System.out.println(bizName + "|size为" + super.size() + " notEmpty等待");
                }
                notFull.signal();
                notEmpty.await();
            }
            T result = super.take();
            if (super.size() > 1) {
                notEmpty.signal();
                if (print) {
                    System.out.println(bizName + "|size为" + super.size() + " notEmpty唤醒");
                }
            }
            return result;
        } finally {
            lock.unlock();
        }

    }

//    @Override
//    public int drainTo(Collection c, int maxElements) {
//        lock.lock();
//        try {
//            if (super.isEmpty()) {
//                try {
//                    if (print) {
//                        System.out.println(bizName + "|size为" + super.size() + " notFull唤醒");
//                        System.out.println(bizName + "|size为" + super.size() + " notEmpty等待");
//                    }
//                    notFull.signal();
//                    notEmpty.await();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            int drainCount = super.drainTo(c, maxElements);
//            if (super.size() > 1) {
//                notEmpty.signal();
//                if (print) {
//                    System.out.println(bizName + "|size为" + super.size() + " notEmpty唤醒");
//                }
//            }
//            return drainCount;
//        } finally {
//            lock.unlock();
//        }
//    }

    @Override
    public void put(T o) throws InterruptedException {
        lock.lock();
        try {
            if (super.size() == this.capacity) {
                if (print) {
                    System.out.println(bizName + "|size为" + super.size() + " notFull等待");
                }
                notFull.await();
            }
            super.put(o);
            if (print) {
                System.out.println(bizName + "|size为" + super.size() + " notEmpty唤醒");
            }
            notEmpty.signal();
            if (super.size() + 1 < this.capacity) {
                notFull.signal();
                if (print) {
                    System.out.println(bizName + "|size为" + super.size() + " notFull唤醒");
                }
            }
        } finally {
            lock.unlock();
        }
    }
}
