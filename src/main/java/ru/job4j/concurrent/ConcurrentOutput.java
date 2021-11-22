package ru.job4j.concurrent;

public class ConcurrentOutput {
    /**
     * Последовательность вывода нитей может быть разной,
     * но в тексе метода они описаны по порядку:
     * sout(Thread.currentThread().getName()); - main
     * another.start(); - Thread-0
     * second.start(); - Thread-1
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName());
        Thread another = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        another.start();
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        second.start();
    }
}
