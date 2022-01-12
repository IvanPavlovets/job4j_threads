package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceExample {
    public static void main(String[] args) {
        /*
         Создаем готвый thredPool по количеству ядер в системе.
         */
        ExecutorService pool = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );

        /*
          Добавляем задачи в пул и сразу их выполняем.
         */
        pool.submit(() -> System.out.println(
                Thread.currentThread().getName() + " execute")
        );
        pool.submit(() -> System.out.println(
                Thread.currentThread().getName() + " execute")
        );

        /*
          Закрываем пул и ждем пока все задачи завершаться.
         */
        pool.shutdown();

        while (!pool.isTerminated()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Execute " + Thread.currentThread().getName());
    }
}
