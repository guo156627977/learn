package com.gzq.juc.lock;

/**
 * @author guozhiqiang
 * @description
 * @created 2018-04-02 20:56.
 */
public class TestABCAlternatePrint {


    public static void main(String[] args) {
        Alternate alternate = new Alternate();

        new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < 5; i++) {
                    alternate.LoopA(i);
                }

            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < 5; i++) {
                    alternate.LoopB(i);
                }

            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < 5; i++) {
                    alternate.LoopC(i);
                }

            }
        }).start();


    }


}
