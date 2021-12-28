package ru.job4j.pool;

import ru.job4j.block.queue.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

/**
 * Обьектный пул - набор инициализированых и готовых к исполнению обьектов.
 *  1) В каждую нить передается блокирующая очередь tasks.
 *  В методе run мы должны получить задачу из очереди tasks.
 *  2) Если в очереди нет элементов, то нить переводиться
 *  в состоянии Thread.State.WAITING.
 *  3) Когда приходит новая задача, всем нитям в состоянии
 *  Thread.State.WAITING посылается сигнал проснуться и начать работу.
 */
public class ThreadPool {
    /**
     * количество ядер в системе.
     */
    private static final int SIZE = Runtime.getRuntime().availableProcessors();

    /**
     * список нитей.
     */
    private final List<Thread> threads = new LinkedList<>();
    /**
     * очередь с задачами
     */
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(SIZE);

    /**
     *  метод добавляет задачи в блокирующую очередь tasks.
     * @param job - задача для одной нити.
     */
    public void work(Runnable job) throws InterruptedException {
        if (threads.isEmpty()) {
            initPool(job);
        }
        for (int i = 0; threads.size() != 0; i++) {
            tasks.offer(threads.get(i));
        }
    }

    /**
     * Инициализация списка нитей.
     * @param job
     */
    private void initPool(Runnable job) {
        Thread thread = new Thread(job);
        for (int i = 0; i < SIZE; i++) {
            threads.add(thread);
        }
    }

    /**
     * метод завершит все запущенные задачи.
     */
    public void shutdown() {
        threads.stream().forEach(thread -> thread.interrupt());
    }

}
