package com.gzq.es.client.api.bulk;

import com.gzq.es.client.util.ESUtil;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-07-10 15:31.
 */
public class DeleteByQueryDemo {

    public static void main(String[] args) {
        Client client = ESUtil.getClient();
        BulkByScrollResponse response = DeleteByQueryAction.INSTANCE
                .newRequestBuilder(client)
                .filter(QueryBuilders.matchQuery("title", "模式"))
                .source("index1")//设置索引名称
                .get();
        //被删除文档数目
        long deleted = response.getDeleted();
        System.out.println(deleted);
    }
}
