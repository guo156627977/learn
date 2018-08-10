package com.gzq.algorith;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-08-06 14:34.
 */
public class Random {

    private static final java.util.Random random = new java.util.Random();

    public static void main(String[] args) {
        int sum=0;
        double radio =0.3;
        int count =100000;
        for (int i = 0; i < count; i++) {
            boolean b = samplingJudge(radio);
            if (b){
                sum++;
            }

        }
        System.out.println("sum = " + sum);

        String a = "192.168.1.1:2181,192.168.1.1:2181,";
        String substring = a.substring(0, a.length() - 1);
        System.out.println("substring = " + substring);

    }

    public static boolean samplingJudge(double radio) {
        boolean flag = false;
        double v = random.nextDouble();
        if (radio - v > 0) {
            flag = true;
        }
        return flag;
    }

}
