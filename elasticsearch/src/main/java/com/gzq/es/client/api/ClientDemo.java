package com.gzq.es.client.api;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-06-26 23:22.
 */
public class ClientDemo {

    public static void main(String[] args) throws UnknownHostException {
        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
        TransportClient client = new PreBuiltTransportClient(settings).addTransportAddress(new TransportAddress(InetAddress.getByName
                ("guo001"), 9300));
        GetResponse response = client.prepareGet("website", "blog", "1").execute().actionGet();
        System.out.println("response = " + response.toString());
        client.close();


    }
}
