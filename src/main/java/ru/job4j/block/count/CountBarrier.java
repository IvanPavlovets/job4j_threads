package ru.job4j.block.count;

/**
 * Блокирует выполнение по условию счетчика.
 */
public class CountBarrier {
    private final Object monitor = this;
    /**
     * содержит количество вызовов метода count().
     */
    private final int total;
    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    /**
     * Изменяет состояние программы - метод notifyAll.
     */
    public void count() {
        synchronized (monitor) {
            count++;
            notifyAll();
        }
    }

    /**
     * Метод ожидания. Показывает ждущие потоки.
     * Нити, которые выполняют метод await,
     * могут начать работу если поле count >= total
     * Если оно не равно, то нужно перевести нить
     * в состояние wait.
     */
    public void await() {
        synchronized (monitor) {
            try {
                while (count < total) {
                    monitor.wait();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }
}
