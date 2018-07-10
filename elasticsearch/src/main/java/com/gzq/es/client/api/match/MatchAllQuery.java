package com.gzq.es.client.api.match;

import com.gzq.es.client.util.ESUtil;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import java.util.Map;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-07-10 15:48.
 */
public class MatchAllQuery {

    public static void main(String[] args) {
        Client client = ESUtil.getClient();
        //构造查询对象
        QueryBuilder query = QueryBuilders.matchAllQuery();
        //搜索结果存入SearchResponse
        SearchResponse response = client.prepareSearch("my-index")
                .setQuery(query) //设置查询器
                .setSize(10)      //一次查询文档数
                .get();
        SearchHits hits = response.getHits();
        for (SearchHit hit : hits) {
            System.out.println("source:" + hit.getSourceAsString());
            System.out.println("index:" + hit.getIndex());
            System.out.println("type:" + hit.getType());
            System.out.println("id:" + hit.getId());
            //遍历文档的每个字段
            Map<String, Object> map = hit.getSourceAsMap();
            for (String key : map.keySet()) {
                System.out.println(key + "=" + map.get(key));
            }
            System.out.println("--------------------");
        }
    }
}
