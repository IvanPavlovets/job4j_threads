package ru.job4j.cas;

import net.jcip.annotations.ThreadSafe;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Счетчик поддерживает CAS операции.
 * head.compareAndSet(ref, temp) - атомарная операция:
 * head - сравниваемое значение;
 * ref - то с чем сравниваем;
 * temp - результат который будет записан в head при условии что head и ref равны.
 */
@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    /**
     * Увеличиваем на 1 значение в count,
     * если значение ref соответсвует значению в count,
     * тоесть ref из той же самой нити исполнения что и count.
     */
    public void increment() {
        Integer ref;
        do {
            ref = count.get();
        } while (!count.compareAndSet(ref, ref + 1));
    }

    /**
     * Получить текущее значение из AtomicReference.
     * count.get() - атомарная операция нет нужды использовать
     * count.compareAndSet(ref, ref)).
     * @return Integer
     */
    public int get() {
        return count.get();
    }

}
