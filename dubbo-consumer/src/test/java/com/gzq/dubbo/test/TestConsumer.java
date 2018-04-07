package com.gzq.dubbo.test;

import com.gzq.dubbo.service.DemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-04-07 22:57.
 */
public class TestConsumer {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"consumer.xml"});
        context.start();
        DemoService demoService = (DemoService) context.getBean("demoService"); // 获取远程服务代理
        String hello = demoService.sayHello("world"); // 执行远程方法
        System.out.println(hello); // 显示调用结果
    }

}
