package com.gzq.es.client.api.bulk;

import com.gzq.es.client.util.ESUtil;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.client.Client;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-07-10 11:54.
 */
public class MultiGetDemo {

    public static void main(String[] args) {
        Client client = ESUtil.getClient();
        MultiGetResponse mgResponse = client.prepareMultiGet().add("index1", null, "1","2")
                .add("my-index", null, "1", "2", "2").get();
        for (MultiGetItemResponse response : mgResponse) {
            GetResponse rp = response.getResponse();
            if (rp != null&&rp.isExists()) {
                System.out.println("rp = " + rp.getSourceAsString());
            }
        }
    }
}
