package com.gzq.learn.scheduled;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-02-28 10:02.
 */
public class Producer {

    public static void main(String[] args) throws InterruptedException, MQClientException {


        DefaultMQProducer producer = new DefaultMQProducer("Scheduled");
        producer.setNamesrvAddr("guo001:9876");
        //producer.setVipChannelEnabled(false);
        producer.start();

        for (int i = 0; i < 10; i++) {
            try {
                Message message = new Message("ScheduledTopic", ("Hello scheduled message! " + i).getBytes());
                //1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
                //延迟10s,开源版目前只支持时间等级
                message.setDelayTimeLevel(3);
                SendResult send = producer.send(message);

                System.out.println("send = " + send);
            } catch (Exception e) {
                e.printStackTrace();
                Thread.sleep(1000);
            }
        }
        System.out.println("send successful!");

        producer.shutdown();
    }
}
