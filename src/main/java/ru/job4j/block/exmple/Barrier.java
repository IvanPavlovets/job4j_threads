package ru.job4j.block.exmple;

public class Barrier {
    private boolean flag = false;
    private final Object monitor = this;

    /**
     * меняет flag с false на true
     */
    public void on() {
        synchronized (monitor) {
            flag = true;
            monitor.notifyAll();
        }
    }

    /**
     * меняет flag с true на false.
     */
    public void off() {
        synchronized (monitor) {
            flag = false;
            monitor.notifyAll();
        }
    }

    /**
     * Проверяет состояние flag.
     */
    public void check() {
        synchronized (monitor) {
            while (!flag) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
