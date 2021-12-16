package ru.job4j.block.buffer;

import ru.job4j.block.queue.SimpleBlockingQueue;

/**
 * Механизм остановки потребителя, когда производитель закончил свою работу.
 * Прерывание для consumer происходит по окончанию работы producer
 * и consumer заканчивает свою работу как прерваный.
 * Что бы не происходила ситуация - когда нить производитель заканчивает свою работу,
 * потребители переходят в режим wait.
 */
public class ParallelSearch {
    public static void main(String[] args) {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);
        int queueCall = 3;
        final Thread consumer = new Thread(
                () -> {
                    try {
                        while (!Thread.currentThread().isInterrupted()) {
                            System.out.println(queue.poll());
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
        );

        Thread producer = new Thread(
                () -> {
                    try {
                        for (int i = 0; i != queueCall; i++) {
                            queue.offer(i);
                            Thread.sleep(500);
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                    consumer.interrupt();
                }
        );
        producer.start();
        consumer.start();
    }
}
