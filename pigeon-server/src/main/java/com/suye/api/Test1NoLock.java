package com.suye.api;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * description
 *
 * @author zxy
 * create time 2020/4/16 21:21
 */
public class Test1NoLock {
    private static final int run=3;

    public static void main(String[] args) throws InterruptedException {

        AtomicInteger integer=new AtomicInteger(0);
        new Thread(new Task("X",10,0,1,integer)).start();
        new Thread(new Task("Y",10,1,2,integer)).start();
        new Thread(new Task("Z",10,2,0,integer)).start();

    }

    static class Task implements  Runnable{
        private String name;
        private int time;
        private int expect;
        private  int update;
        private AtomicInteger integer;

        public Task(String name, int time,int expect, int update, AtomicInteger integer) {
            this.name = name;
            this.time=time;
            this.expect = expect;
            this.update = update;
            this.integer = integer;
        }

        @Override
        public void run() {
            int count=0;
            while (count<time) {
                if (integer.compareAndSet(expect,run)){
                    count++;
                    System.out.println(name);
                    integer.compareAndSet(3,update);
                }
            }

        }
    }
}
