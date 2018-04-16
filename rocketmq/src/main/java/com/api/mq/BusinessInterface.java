package com.api.mq;
/**   
 * @类注释: 
 * @author: zhangyinhu
 * @date 2015-4-2 下午5:19:26  
 */
public interface BusinessInterface<V> {
	
	
	   /**
     * 业务处理
     * @param args
     * @return
     */
    void doBusiness(V args);

}


