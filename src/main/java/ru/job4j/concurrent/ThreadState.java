package ru.job4j.concurrent;

public class ThreadState {
    /**
     * В методе показано, как нить переходит между своими состояниями.
     * 1) sout, до начала first.start(); - нить создана но не запущена - NEW.
     * 2) в цикле - Нить запущена пока не остановиться - RUNNABLE.
     * 3) sout после цикла - Нить завершила работу TERMINATED.
     * Главная нить исполнения main будет работать до тех пор,
     * пока нить first не завершит свою работу.
     * *) тело цикла выполняеться произвольное количество раз,
     * зависит от планировщика потоков, Если задач много,
     * то переключение между нитями будет частое.
     * @param args
     */
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> { }
        );
        Thread second = new Thread(
                () -> { }
        );
        System.out.printf("%s %s%s", " first:", first.getState(), "\n");
        System.out.printf("%s %s%s", "second:", second.getState(), "\n");
        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED || second.getState() != Thread.State.TERMINATED) {
            System.out.printf("%s %s%s", " first:", first.getState(), "\n");
            System.out.printf("%s %s%s", "second:", second.getState(), "\n");
        }
        System.out.printf("%s %s%s", " first:", first.getState(), "\n");
        System.out.printf("%s %s%s", "second:", second.getState(), "\n");
        System.out.printf("  %s: %s", Thread.currentThread().getName(), "Threads have finished running");
    }
}
