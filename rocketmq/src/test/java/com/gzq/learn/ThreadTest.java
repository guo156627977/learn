package com.gzq.learn;

import org.junit.Test;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-02-05 10:16.
 */
public class ThreadTest {

private static volatile int count =0;

    @Test
    public void test() throws Exception {
        MyTask myTask = new MyTask();
        Thread t1 = new Thread(myTask);
        Thread t2 = new Thread(myTask);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("count = " + count);

    }


    class MyTask implements Runnable {

        @Override
        public void run() {
            int size = 100000;
            for (int i = 0; i < size; i++) {
                count++;
            }
        }
    }

}
