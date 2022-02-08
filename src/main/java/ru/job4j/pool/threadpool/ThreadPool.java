package ru.job4j.pool.threadpool;

import ru.job4j.block.queue.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

/**
 * Обьектный пул - набор инициализированых и готовых к исполнению обьектов.
 * 1) В каждую нить передается блокирующая очередь tasks.
 * В методе run мы должны получить задачу из очереди tasks.
 * 2) Если в очереди нет элементов, то нить переводиться
 * в состоянии Thread.State.WAITING.
 * 3) Когда приходит новая задача, всем нитям в состоянии
 * Thread.State.WAITING посылается сигнал проснуться и начать работу.
 * Смотри пример http://tutorials.jenkov.com/java-concurrency/thread-pools.html
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
     * В конструкторе происходит инициализация списка потоков.
     * В каждую нить передается блокирующая очередь tasks.
     * В методе run мы должны получить задачу из очереди tasks.
     * job - это наша работа которую мы положили в tasks, в методе work.
     */
    public ThreadPool() {
        for (int i = 0; i < SIZE; i++) {
            threads.add(new Thread(
                    () -> {
                        try {
                            while (!Thread.currentThread().isInterrupted()) {
                                Runnable job = tasks.poll();
                                job.run();
                            }
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
            ));
        }
        threads.forEach(thread -> thread.start());
    }

    /**
     * Метод добавляет задачи job в блокирующую очередь tasks.
     * Добавляет задачи клиент - тот кто использует пул потоков.
     * @param job - задача для одной нити.
     */
    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    /**
     * метод завершит все запущенные задачи.
     */
    public void shutdown() {
        threads.forEach(thread -> thread.interrupt());
    }

    /**
     * в main вызываем work и передаем job в аргумент, в виде lambda
     * вывести имя нити и номер задачи (job).
     * Задачи вытаскиваються нитями из очереди каждый раз в разном порядке.
     * @param args
     */
    public static void main(String[] args) {
        ThreadPool pool = new ThreadPool();
        for (int i = 1; i < 11; i++) {
            int jobNumber = i;
            try {
                pool.work(() -> System.out.println(
                        Thread.currentThread().getName() + " job: " + jobNumber)
                );
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
        pool.shutdown();
    }

}
