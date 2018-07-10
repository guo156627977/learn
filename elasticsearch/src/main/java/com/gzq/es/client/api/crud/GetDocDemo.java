package com.gzq.es.client.api.crud;

import com.gzq.es.client.util.ESUtil;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-07-10 11:12.
 */
public class GetDocDemo {

    public static void main(String[] args) {
        Client client = ESUtil.getClient();
        GetResponse response = client.prepareGet("website", null, "1").get();
        System.out.println(response.isExists());
        System.out.println(response.getIndex());
        System.out.println(response.getType());
        System.out.println(response.getId());
        System.out.println(response.getVersion());
        String source = response.getSourceAsString();
        System.out.println(source);

    }
}
