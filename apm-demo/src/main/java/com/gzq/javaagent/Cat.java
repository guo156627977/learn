package com.gzq.javaagent;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-08-08 17:54.
 */
public class Cat {

    private static AtomicInteger m_count = new AtomicInteger();

    private static double sampleRatio=0.8;
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            if (sampleRatio > 0) {//这段逻辑就是按采样率去剔除一些消息，只选取其中一部分进行后续的消费上传。
                int count = m_count.incrementAndGet();
                double v = 1 / sampleRatio;

                if (count % (v) == 0) {
                    // return offer(tree);
                    System.out.println("v = " + v);
                    System.out.println("count = " + count);
                }
            }
        }

    }
}
