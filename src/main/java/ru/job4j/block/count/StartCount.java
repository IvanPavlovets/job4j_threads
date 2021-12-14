package ru.job4j.block.count;

public class StartCount {
    public static void main(String[] args) {
        CountBarrier barrier = new CountBarrier(3);

        Thread first = new Thread(new CountingThred(barrier));
        Thread second = new Thread(new CountingThred(barrier));
        Thread third = new Thread(new CountingThred(barrier));

        first.start();
        second.start();
        third.start();
    }
}
