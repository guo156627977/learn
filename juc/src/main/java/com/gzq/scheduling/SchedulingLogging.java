package com.gzq.scheduling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

public class SchedulingLogging implements Callable {
    BlockingQueue queue;
    boolean print = true;
    int processCapacity;

    public SchedulingLogging(BlockingQueue queue, int processCapacity) {
        this.queue = queue;
        this.processCapacity = processCapacity;
    }

    @Override
    public Boolean call() throws Exception {
        while (!queue.isEmpty()) {
            List<Object> list = new ArrayList<>(processCapacity);
            queue.drainTo(list, processCapacity);
//            if (list.size() > 0) {
                if (print) {
                    System.out.println(this.toString() + "[" + Arrays.toString(list.toArray(new Object[list.size()])) + "]");
                }
//            }
        }
        return Boolean.TRUE;
    }

    @Override
    public String toString() {
        return "写日志：" + Thread.currentThread().getName() + " | " + this.getClass().getName() + " is processing ";
    }
}
