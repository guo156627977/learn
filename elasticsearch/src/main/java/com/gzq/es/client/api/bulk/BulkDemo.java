package com.gzq.es.client.api.bulk;

import com.gzq.es.client.util.ESUtil;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;

import java.io.IOException;
import java.util.Date;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-07-10 12:12.
 */
public class BulkDemo {

    public static void main(String[] args) throws IOException {
        Client client = ESUtil.getClient();
        BulkRequestBuilder bulkRequest = client.prepareBulk();

        bulkRequest.add(client.prepareIndex("twitter", "tweet", "1")
                .setSource(jsonBuilder()
                        .startObject()
                        .field("user", "kimchy")
                        .field("postDate", new Date())
                        .field("message", "trying out Elasticsearch")
                        .endObject()
                )
        );
        bulkRequest.add(client.prepareIndex("twitter", "tweet", "2")
                .setSource(jsonBuilder()
                        .startObject()
                        .field("user", "kimchy")
                        .field("postDate", new Date())
                        .field("message", "another post")
                        .endObject()
                )
        );
        //批量执行
        BulkResponse bulkResponse = bulkRequest.get();
        System.out.println(bulkResponse.status());
        if (bulkResponse.hasFailures()) {
            // process failures by iterating through each bulk response item
            System.out.println("存在失败操作");
        }

    }
}
