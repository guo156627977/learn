package com.gzq.learn.sample;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

/**
 * @author guozhiqiang
 * @description 只发送，不需要返回结果
 * @created 2018-02-23 10:53.
 */
public class OnewayProducer {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("ceb2");
        producer.setNamesrvAddr("guo001:9876");
        Thread.currentThread().getState();
        producer.start();
        for (int i = 0; i < 10; i++) {
            Message message = new Message("TopicTest", null, "key" + i, "Hello Oneway!".getBytes());
            producer.sendOneway(message);
        }
        System.out.println(" 消息发送完毕 ");
        producer.shutdown();
    }
}
