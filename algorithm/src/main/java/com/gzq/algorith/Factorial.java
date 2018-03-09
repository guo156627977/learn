package com.gzq.algorith;

import java.math.BigDecimal;

/**
 * @author guozhiqiang
 * @description 阶乘
 * @created 2018-02-12 9:56.
 */
public class Factorial {

    public static void main(String[] args) {
        //factorial1(10);
        //int i = factorial2(10);
        //System.out.println("i = " + i);

        int n = 100;
        //factorial3(n);
        factorial4(n);

    }


    public static void factorial4(int n) {
        int[] ints = new int[200];
        ints[ints.length - 1] = 1;
        for (int i = 1; i <= n; i++) {
            ints = demo(ints, i);

        }
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < ints.length; i++) {
            stringBuilder.append(ints[i]);
        }
        String s = stringBuilder.toString().replaceAll("^[0]+", "");
        System.out.println(s);

    }

    public static int[] demo(int[] ints, int num) {
        //每个数组里的数都*对应的值
        for (int i = 0; i < ints.length; i++) {
            ints[i] *= num;
        }
        //进和留
        for (int i = ints.length - 1; i > 0; i--) {
            ints[i - 1] += ints[i] / 10;
            ints[i] = ints[i] % 10;
        }
        return ints;
    }


    /**
     * 用BigDecimal来做，底层也是数组
     *
     * @param n
     */
    public static void factorial3(int n) {

        BigDecimal sum = BigDecimal.valueOf(1);
        for (int i = 1; i <= n; i++) {
            BigDecimal value = BigDecimal.valueOf(i);
            sum = sum.multiply(value);
        }
        System.out.println("sum = " + sum);

    }


    /**
     * 小数字的阶乘，for循环实现，结果超过int.maxValue会算不出
     *
     * @param n
     * @return
     */
    public static int factorial2(int n) {

        if (n == 1) {
            return 1;
        } else {
            return n * factorial2(n - 1);
        }

    }

    /**
     * 小数字的阶乘，for循环实现，结果超过int.maxValue会算不出
     *
     * @param n
     * @return
     */
    public static int factorial1(int n) {
        long sum = 1;
        for (int i = 1; i < n; i++) {
            sum = sum * i;
        }
        System.out.println("sum = " + sum);

        return n;
    }

}
