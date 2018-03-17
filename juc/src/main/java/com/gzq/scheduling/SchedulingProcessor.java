package com.gzq.scheduling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

public class SchedulingProcessor<T> implements Callable {
    final List<T> list;

    boolean print = true;

    public SchedulingProcessor(List<T> list) {
        this.list = list;
    }

    @Override
    public List<T> call() throws Exception {
        List<T> result = new ArrayList<>(this.list.size());
        for (T o : list) {
            o = (T) (Thread.currentThread().getName() + " : " + String.valueOf(o));
            result.add(o);
        }
        if (print) {
            System.out.println(this);
        }
        return result;
    }

    @Override
    public String toString() {
        return "处理：" + Thread.currentThread().getName() + " | " + this.getClass().getName() + " is processing [" + Arrays.toString(this.list.toArray(new Object[this.list.size()])) + "]";
    }
}
