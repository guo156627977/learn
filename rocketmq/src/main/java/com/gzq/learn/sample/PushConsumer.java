package com.gzq.learn.sample;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.Set;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-02-26 10:11.
 */
public class PushConsumer {

    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("ceb2");
        consumer.setNamesrvAddr("guo001:9876");

        Set<MessageQueue> messageQueues = consumer.fetchSubscribeMessageQueues("TopicTest");


        consumer.start();
    }
}
