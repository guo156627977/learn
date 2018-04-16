package com.gzq.juc.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-04-09 13:57.
 */
public class TestABCAlternatePrintThreadPool {

    static ExecutorService executorService = Executors.newFixedThreadPool(5);

    private static Alternate alternate = new Alternate();

    public static void main(String[] args) {
        executorService.submit(new TaskA());
        executorService.submit(new TaskB());
        executorService.submit(new TaskC());
    }

    static class TaskA implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                alternate.LoopA(5);
            }
        }
    }

    static class TaskB implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                alternate.LoopB(5);
            }
        }
    }

    static class TaskC implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                alternate.LoopC(5);
            }
        }
    }





}
