package com.gzq.learn.sample;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-02-11 14:24.
 */
public class PullConsumer {

    private static final Map<MessageQueue, Long> OFFSE_TABLE = new HashMap<MessageQueue, Long>();


    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("order_Consumer");
        consumer.setNamesrvAddr("guo001:9876");

        consumer.start();
        Set<MessageQueue> messageQueues = consumer.fetchSubscribeMessageQueues("TopicTest");

        for (MessageQueue messageQueue : messageQueues) {
            System.out.printf("Consume from the queue: %s%n", messageQueue);
            SINGLE_MQ:
            while (true) {
                PullResult pullResult = consumer.pull(messageQueue, null, getMessageQueueOffset(messageQueue), 32);
                //List<MessageExt> msgFoundList = pullResult.getMsgFoundList();
                //for (MessageExt messageExt : msgFoundList) {
                //    System.out.println("message = " + new String(messageExt.getBody()));
                //}
                System.out.printf("%s%n", pullResult);
                putMessageQueueOffset(messageQueue, pullResult.getNextBeginOffset());

                switch (pullResult.getPullStatus()) {
                    case FOUND:
                        break;
                    case NO_MATCHED_MSG:
                        break;
                    case NO_NEW_MSG:
                        break SINGLE_MQ;
                    case OFFSET_ILLEGAL:
                        break;
                    default:
                        break;
                }

            }
        }
        consumer.shutdown();

    }

    public static long getMessageQueueOffset(MessageQueue messageQueue) {
        Long offset = OFFSE_TABLE.get(messageQueue);
        if (offset != null) {
            return offset;
        }
        return 0;
    }

    public static void putMessageQueueOffset(MessageQueue messageQueue, long offset) {
        OFFSE_TABLE.put(messageQueue, offset);
    }
}
