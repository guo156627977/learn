package com.gzq.learn.transaction;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-03-06 16:05.
 */
public class Test {

    public static void main(String[] args) {

        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<String> objects = new ArrayList<>();

        System.out.println(objects==null);
        System.out.println(objects.size()==0);


        System.out.println("objects.toString() = " + objects.toString());

        System.out.println(stringBuilder.toString().equals(""));

        BigDecimal bigDecimal = new BigDecimal(0);
        System.out.println("bigDecimal = " + bigDecimal);
    }
    
}
