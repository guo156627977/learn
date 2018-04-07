package com.gzq.dubbo.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-04-07 22:47.
 */
public class TestProvider {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"provider.xml"});
        context.start();
        System.in.read(); // 按任意键退出
    }
}
