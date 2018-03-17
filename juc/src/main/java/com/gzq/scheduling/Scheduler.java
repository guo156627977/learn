package com.gzq.scheduling;

import org.springframework.beans.factory.InitializingBean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Scheduler implements InitializingBean {

    //biz线程池配置
    private int bizPoolSize = 10;
    private CompletionService bizCompletionService;
    private int bizThreadProcessCapacity = 10;

    //biz本地队列配置
    private int bizQueueCapacity = 10000;
    private BlockingQueue<Object> bizQueue;

    //读 线程组配置
    private int readThreadNum = 1;
    private ThreadGroup threadGroup;

    //log线程池配置

    private int logPoolSize = 10;
    private CompletionService logCompletionService;
    private int logThreadProcessCapacity = 10;

    //log本地队列配置
    private int logQueueCapacity = 10000;
    private BlockingQueue<Object> logQueue;

    public void startProcessQueue() {
        int getNum = 0;
        int availableBizThreadNum = bizPoolSize;
//        logCompletionService.submit(new SchedulingLogging(logQueue, logThreadProcessCapacity));
        for (; ; ) {
            if (availableBizThreadNum == bizPoolSize) {
                getNum = bizPoolSize * bizThreadProcessCapacity;
            } else {
                getNum = availableBizThreadNum * bizThreadProcessCapacity;
                availableBizThreadNum = processBizResult(availableBizThreadNum);
            }
            if (availableBizThreadNum > 0) {
                availableBizThreadNum -= submitBiz(getNum, availableBizThreadNum);
                availableBizThreadNum = processBizResult(availableBizThreadNum);
            }
        }
    }

    private int processBizResult(int availableBizThreadNum) {
//        List<Object> resultList = new ArrayList<>(bizPoolSize);
        Future<Object> future = null;
        int newAvaiable = 0;

        boolean needSubmitJob = logQueue.isEmpty() || logQueue.size() > logQueueCapacity * 0.75;
        for (int i = 0; i < bizPoolSize - availableBizThreadNum; i++) {
            try {
                future = bizCompletionService.take();
            } catch (InterruptedException e) {
                availableBizThreadNum++;
                e.printStackTrace();
            }
            //todo 如果为take 不需要判断为null
            if (future != null && future.isDone()) {
                try {
                    // resultList.add(future.get());
                    logQueue.put(future.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                newAvaiable++;
            }
        }
//                if(logQueue.size() + 1 >= this.logQueueCapacity){
        if (needSubmitJob) {
            logCompletionService.submit(new SchedulingLogging(logQueue, logThreadProcessCapacity));
        }
//                }
//        if (resultList.size() > 0) {
//            try {
//                if(logQueue.size() + 1 >= this.logQueueCapacity){
//                    logCompletionService.submit(new SchedulingLogging(logQueue));
//                }
//                logQueue.put(resultList);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
        return availableBizThreadNum + newAvaiable;
    }

    private int submitBiz(int getNum, int availableBizThreadNum) {
        int submitNum = 0;
//        List<Object> list = new ArrayList<>(getNum);
//        bizQueue.drainTo(list, getNum);
//        int loopNum = list.size() % bizThreadProcessCapacity == 0 ? list.size() / bizThreadProcessCapacity : (list.size() /
// bizThreadProcessCapacity + 1);
//        for (int j = 0; j < loopNum; j++) {
//            List<Object> subList = null;
//            if ((j + 1) * bizThreadProcessCapacity > list.size()) {
//                bizCompletionService.submit(new SchedulingProcessor(list.subList(j * bizThreadProcessCapacity, list.size())));
//            } else {
//                bizCompletionService.submit(new SchedulingProcessor(list.subList(j * bizThreadProcessCapacity, (j + 1) *
// bizThreadProcessCapacity)));
//            }
//            submitNum++;
//        }
        for (int i = 0; i < availableBizThreadNum; i++) {
            List<Object> list = new ArrayList<>(bizThreadProcessCapacity);
            bizQueue.drainTo(list, bizThreadProcessCapacity);
//            if (list.size() > 0) {
            bizCompletionService.submit(new SchedulingProcessor(list));
            submitNum++;
//            }
        }
        return submitNum;
    }

    void initScheduler() {
        ExecutorService bizExecutorService = Executors.newFixedThreadPool(bizPoolSize);
        bizCompletionService = new ExecutorCompletionService(bizExecutorService);
        bizQueue = new LinkedBlockingQueue(bizQueueCapacity);
        ExecutorService logExecutorService = Executors.newFixedThreadPool(logPoolSize);
        logCompletionService = new ExecutorCompletionService(logExecutorService);
        logQueue = new LinkedBlockingQueue(logQueueCapacity);
        readThreadNum = 2;
        threadGroup = new ThreadGroup("scheduling");
        for (int i = 0; i < readThreadNum; i++) {
            new Thread(threadGroup, new SchedulingReader(this.bizQueue)).start();
        }
        logQueue = new LinkedBlockingQueue(logQueueCapacity);
    }

    public BlockingQueue<Object> getLogQueue() {
        return logQueue;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initScheduler();
        startProcessQueue();
    }
}
