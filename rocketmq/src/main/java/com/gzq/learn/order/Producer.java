package com.gzq.learn.order;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.List;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-02-26 11:21.
 */
public class Producer {
    public static void main(String[] args) throws InterruptedException, MQClientException {


        DefaultMQProducer producer = new DefaultMQProducer("order_Producer");
        producer.setNamesrvAddr("guo001:9876");
        //producer.setVipChannelEnabled(false);
        producer.start();

        for (int i = 0; i < 10; i++) {
            String key = "KEY"+i;
            for (int j = 0; j < 3; j++) {
                try {
                    Message message = new Message("TopicOrderTest", "order", key, ("Hello World! " + j).getBytes());

                    SendResult send = producer.send(message, new MessageQueueSelector() {
                        @Override
                        public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                            int index = Math.abs(arg.hashCode() % mqs.size());
                            return mqs.get(index);
                        }
                    }, key);
                    System.out.printf("%s%n", send);
                } catch (Exception e) {
                    e.printStackTrace();
                    Thread.sleep(1000);
                }
            }

        }

        producer.shutdown();
    }
}
