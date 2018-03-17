package com.gzq.demo.test;

import org.junit.Test;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-03-13 11:38.
 */
public class HelloAgentTest {

    @Test
    public void testHelloAgent() {
        System.out.println("Hello Test!");
        new UserServiceImpl().getUser();
    }
}
