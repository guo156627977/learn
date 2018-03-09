package com.gzq.algorith;

/**
 * @author guozhiqiang
 * @description 两个大数相乘，位数不会超过两个的位数之和
 * @created 2018-02-12 16:16.
 */
public class BigDataIntegerMultiply {

    public static void main(String[] args) {

        int m =59798678;
        //int m = 250;
        int n =6986586;
        //int n = 33;

        char[] charsX = String.valueOf(m).toCharArray();
        char[] charsY = String.valueOf(n).toCharArray();

        int xLength = charsX.length;
        int yLength = charsY.length;
        int[] result = new int[xLength + yLength];
        int[] x = new int[xLength];
        int[] y = new int[yLength];
        //把char转换成int数组，为什么要减去一个'0'呢？因为要减去0的ascii码得到的就是实际的数字
        for (int i = 0; i < xLength; i++) {
            x[i] = charsX[i] - '0';
        }
        for (int i = 0; i < yLength; i++) {
            y[i] = charsY[i] - '0';
        }

        for (int i = 0; i < xLength; i++) {
            for (int j = 0; j < yLength; j++) {
                result[i + j + 1] += x[i] * y[j];
            }
        }
        //满10进位，从后往前满十进位
        for (int i = result.length - 1; i > 0; i--) {
            result[i - 1] += result[i] / 10;
            result[i] = result[i] % 10;
        }
        //new int[]
        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i]);

        }

    }
}
