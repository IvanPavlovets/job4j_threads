package ru.job4j.block.queue;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    /**
     * В тесте проверяем ситуацию при которой очередь пуста
     * Consumer переходит в состояние wait,
     * до тех пор пока Producer поменстит в очередь данные.
     * 1) Получаем состояние WAITING остановленой нити, после попытки ИЗВЛЕЧЬ
     * из очереди элементы.
     */
    @Test
    public void whenQueueIsEmptyThenConsumerIsWait() {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue(3);
        Thread consumer = new Thread(
                () -> {
                    try {
                        queue.poll();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        e.printStackTrace();
                    }
                    assertEquals(Thread.currentThread().getState(), Thread.State.WAITING);
                }
        );
        consumer.start();
    }

    /**
     * В тесте проверяем ситуацию при которой очередь заполнена
     * Producer переходит в состояние wait,
     * до тех пор пока Consumer не извлечет очередные данные.
     * 1) Получаем состояние WAITING остановленой нити, после попытки ДОБАВИТЬ
     * в очередь элементы сверх нормы.
     */
    @Test
    public void whenQueueIsFullThenProducerIsWait() {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue(3);
        Thread producer = new Thread(
                () -> {
                    try {
                        queue.offer(10);
                        queue.offer(20);
                        queue.offer(30);
                        queue.offer(40);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        e.printStackTrace();
                    }
                    assertEquals(Thread.currentThread().getState(), Thread.State.WAITING);
                }
        );
        producer.start();
    }

    /**
     * Проверяем нормальную работу очереди,
     * producer добовляет элементы
     * consumer потребляет добавленые элементы
     * в результате все помещеные в очередь элементы
     * извлекаються в result и мы это видем в проверке.
     */
    @Test
    public void whenAddAndPollElementFromQueue() throws InterruptedException {
        List<Integer> expected = List.of(10, 20, 30, 40);
        List<Integer> result = new ArrayList<>();
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue(3);
        Thread producer = new Thread(
                () -> {
                    try {
                        queue.offer(10);
                        queue.offer(20);
                        queue.offer(30);
                        queue.offer(40);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        e.printStackTrace();
                    }
                }
        );
        Thread consumer = new Thread(
                () -> {
                    try {
                        result.add(queue.poll());
                        result.add(queue.poll());
                        result.add(queue.poll());
                        result.add(queue.poll());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        e.printStackTrace();
                    }
                }
        );
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertEquals(result, expected);
    }

}
