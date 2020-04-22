package com.suye.api;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * description
 * 一个线程打印Y，一个线程打印Z，同时执行连续打印10次"XYZ"
 *
 * @author zxy
 * create time 2020/4/16 21:21
 */
public class Test1 {

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Condition conditionX = lock.newCondition();
        Condition conditionY = lock.newCondition();
        Condition conditionZ = lock.newCondition();
        int time = 10;
        Thread x = new Thread(new Task("X", time, lock, conditionX, conditionY));
        Thread y = new Thread(new Task("Y", time, lock, conditionY, conditionZ));
        Thread z = new Thread(new Task("Z", time, lock, conditionZ, conditionX));
        x.start();
        y.start();
        z.start();
    }

    static class Task implements Runnable {
        /**
         * 实际执行任务内容
         */
        private String name;
        /**
         * 任务执行次数
         */
        private int time;
        /**
         *同步锁
         */
        private Lock lock;
        /**
         * 等待资源
         */
        private Condition wait;
        /**
         * 唤醒资源
         */
        private Condition notify;

        public Task(String name, int time, Lock lock, Condition wait, Condition notify) {
            this.name = name;
            this.time = time;
            this.lock = lock;
            this.wait = wait;
            this.notify = notify;
        }

        @Override
        public void run() {
            int count = 0;
            while (count < time) {
                count++;
                lock.lock();
                System.out.println(name);
                try {
                    notify.signal();
                    wait.await();
                } catch (InterruptedException ignore) {

                }
            }

        }
    }


}
