package com.gzq.learn.batch;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.util.ArrayList;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-02-28 10:46.
 */
public class Producer {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("batch_group");
        producer.setNamesrvAddr("guo001:9876");
        producer.start();

        ArrayList<Message> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Message message = new Message("TopicTest", null, "key" + 1, ("Hello Batch!" + i).getBytes());
            list.add(message);
        }
        producer.send(list);
        System.out.println("批量发送结束");
        producer.shutdown();
    }
}
