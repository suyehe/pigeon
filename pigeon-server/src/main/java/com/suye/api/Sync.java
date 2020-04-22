package com.suye.api;

/**
 * description
 *
 * @author zxy
 * create time 2020/4/20 16:16
 */
public class Sync {
    private static int next = 0;

    public static void main(String[] args) {
        Object lock = new Object();
        new Thread(new Task(0, lock, "X")).start();
        new Thread(new Task(1, lock, "Y")).start();
        new Thread(new Task(2, lock, "Z")).start();

    }

    static class Task implements Runnable {
        private int t;
        private Object lock;
        private String name;

        public Task(int t, Object lock, String name) {
            this.t = t;
            this.lock = lock;
            this.name = name;
        }

        @Override
        public void run() {
            while (next < 30) {
                synchronized (lock) {
                    if (next % 3 == t) {
                        System.out.println(name);
                        next++;
                    }
                    try {
                        lock.notifyAll();
                        lock.wait();
                    } catch (Exception ignore) {

                    }
                }

            }

        }
    }


}
