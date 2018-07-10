package com.gzq.es.client.api.bulk;

import com.gzq.es.client.util.ESUtil;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-07-10 14:38.
 */
public class BulkProcessorDemo {

    public static void main(String[] args) {
        Client client = ESUtil.getClient();
        BulkProcessor bulkProcessor = BulkProcessor.builder(client, new BulkProcessor.Listener() {
            @Override
            public void beforeBulk(long executionId, BulkRequest request) {
                //设置bulk批处理的预备工作
                System.out.println("请求数" + request.numberOfActions());
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
                //设置bulk批处理的善后工作
                if (!response.hasFailures()) {
                    System.out.println("执行成功！");
                } else {
                    System.out.println("执行失败！");
                }
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
                //设置bulk批处理的异常处理工作
                System.out.println(failure);
            }
        })//设置提交批处理操作的请求阀值数
                .setBulkActions(1000)
                //设置提交批处理操作的请求大小阀值
                .setBulkSize(new ByteSizeValue(5, ByteSizeUnit.MB))
                //设置刷新索引时间间隔
                .setFlushInterval(TimeValue.timeValueSeconds(5))
                //设置并发处理线程个数
                .setConcurrentRequests(1)
                // 设置回滚策略，等待时间100ms,retry次数为3次
                .setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
                .build();
        bulkProcessor.add(new DeleteRequest("twitter", "tweet", "1"));
        bulkProcessor.add(new DeleteRequest("twitter", "tweet", "2"));
        // 刷新所有请求
        bulkProcessor.flush();
        // 关闭bulkProcessor
        bulkProcessor.close();
        // 刷新索引
        client.admin().indices().prepareRefresh().get();
        // Now you can start searching!
        client.prepareSearch().get();

    }
}
