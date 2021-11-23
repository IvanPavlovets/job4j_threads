package ru.job4j.concurrent;

public class ThreadSleep {
    /**
     * В методе происходит приостановка нити на 3 мс
     * затем после пробуждения нить печатает Loaded.
     * sleep() - может вызвать исключение InterruptedException,
     * нить могут попросить прервать свое выполнение
     * @param args
     */
    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> {
                    try {
                        System.out.println("Start loading ... ");
                        Thread.sleep(3000);
                        System.out.println("Loaded.");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
        );
        thread.start();
        System.out.println("main");
    }
}
