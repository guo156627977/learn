package com.gzq.dubbo.service.impl;

import com.gzq.dubbo.service.DemoService;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-04-07 22:39.
 */
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {

        return "Hello " + name;


    }
}
