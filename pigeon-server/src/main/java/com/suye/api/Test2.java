package com.suye.api;

import java.util.concurrent.CountDownLatch;

/**
 * description
 * 有n个赛车，让它们都在起跑线上就绪后，同时出发，最后一个赛车跑完后结束比赛，用Java多线程的技术把这种情况写出来
 * @author zxy
 * create time 2020/4/16 21:21
 */
public class Test2 {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch downLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            final int num=i;
            new Thread(()->{
                try {
                    System.out.println(num+"到达赛场");
                    downLatch.countDown();
                    downLatch.await();
                    System.out.println(num+"跑完");
                } catch (Exception e) {
                }

            }).start();
        }
        downLatch.await();
        System.out.println("比赛结束");

    }
}
