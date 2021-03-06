package ru.job4j.block.queue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Модель шаблона Producer Consumer на примере
 * блокирующей очереди, ограниченной по размеру.
 * Producer - производитель.
 * Consumer - потребитель.
 *
 * @param <T>
 */
@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    /**
     * максимальное количество элементов в очереди.
     */
    private final int max;

    public SimpleBlockingQueue(int max) {
        this.max = max;
    }

    /**
     * Добавить обьект во внутреннюю очередь.
     * Если в очереди нет места, то переведет
     * текущую нить в состояние - wait.
     * тоесть - очередь заполнена полностью,
     * то при попытке добавления поток Producer блокируется,
     * до тех пор пока Consumer не извлечет очередные данные
     */
    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() >= max) {
            wait();
        }
        queue.offer(value);
        notifyAll();
    }

    /**
     * Вернет объект из внутренней коллекции.
     * Если в коллекции объектов нет, то переведет
     * текущую нить в состояние ожидания - wait.
     * тоесть - очередь пуста, поток Consumer блокируется,
     * до тех пор пока Producer не поместит в очередь данные.
     * @return T
     */
    public synchronized T poll() throws InterruptedException {
        T element;
        while (queue.isEmpty()) {
            wait();
        }
        element = queue.poll();
        notifyAll();
        return element;
    }

    /**
     * Проверяем пуста ли внутреняя очередь.
     * @return boolean
     */
    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}
