package com.gzq.scheduling;

import java.util.concurrent.BlockingQueue;

public class SchedulingReader implements Runnable {
    BlockingQueue queue;

    boolean print = true;

    public SchedulingReader(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        boolean run = true;
        int count = 0;
        while (run) {
            if(count > 1000000){
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                double b = Math.random();
                if (print) {
                    System.out.println(this.toString() + "模拟暂停");
                }
                if(count > 2000000){
                    count = 0;
                }
                continue;
            }
            double b = Math.random();
            if (print) {
                System.out.println(this.toString() + b);
            }
            try {
                queue.put(b);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return "生成数据：" + Thread.currentThread().getName() + " | " + this.getClass().getName() + " is reading...";
    }
}
