package ru.javawebinar.basejava;

public class MainDeadlock {
    private static final Object LOCK1 = new Object();
    private static final Object LOCK2 = new Object();

    public static void main(String[] args) {
        thread(LOCK1, LOCK2);
        thread(LOCK2, LOCK1);
    }

    private static void thread(Object LOCK1, Object LOCK2) {
        new Thread(() -> {
            synchronized (LOCK1) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (LOCK2) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
