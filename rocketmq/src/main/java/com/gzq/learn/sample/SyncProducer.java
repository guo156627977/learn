package com.gzq.learn.sample;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * @author guozhiqiang
 * @description 同步发送，等待消息返回
 * @created 2018-02-09 14:52.
 */
public class SyncProducer {

    public static void main(String[] args) throws InterruptedException, MQClientException {


        DefaultMQProducer producer = new DefaultMQProducer("ceb2");
        producer.setNamesrvAddr("guo001:9876");
        //producer.setVipChannelEnabled(false);
        producer.start();

        for (int i = 0; i < 10; i++) {
            try {
                Message message = new Message("TopicTest", ("Hello World! " + i).getBytes());
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
