package ru.job4j.block.count;

public class CountingThred implements Runnable {
    private final CountBarrier barrier;

    public CountingThred(CountBarrier barrier) {
        this.barrier = barrier;
    }

    @Override
    public void run() {
        barrier.await();
    }

}
