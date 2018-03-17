package com.gzq.scheduling;

public class Main {
    public static void main(String[] args) {
        Scheduler scheduleObj = new Scheduler();
        try {
            scheduleObj.afterPropertiesSet();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
