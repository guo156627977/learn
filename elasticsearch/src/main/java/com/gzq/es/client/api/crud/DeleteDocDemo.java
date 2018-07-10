package com.gzq.es.client.api.crud;

import com.gzq.es.client.util.ESUtil;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.Client;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-07-10 11:18.
 */
public class DeleteDocDemo {
    public static void main(String[] args) {
        Client client = ESUtil.getClient();
        DeleteResponse response = client.prepareDelete("index1", "blog", "1").get();
        //删除成功返回OK，否则返回NOT_FOUND
        System.out.println(response.status());
        //返回被删除文档的类型
        System.out.println(response.getType());
        //返回被删除文档的ID
        System.out.println(response.getId());
        //返回被删除文档的版本信息
        System.out.println(response.getVersion());
    }

}
