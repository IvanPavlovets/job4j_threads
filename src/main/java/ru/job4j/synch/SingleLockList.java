package ru.job4j.synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Класс пример потокобезопасной колекции.
 * SingleLockList - обертка над классом List,
 * патерн декоратор добовляет новое поведение для List,
 * в конструкторе получаем копию List, что бы работать с копией данных.
 * В java уже есть аналогичный метод Collections.synchronizedList().
 * Этот метод делает обертку над коллекциями типа List.
 */
@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {
    @GuardedBy("this")
    private final List<T> list;

    public SingleLockList(List<T> list) {
        this.list = copy(list);
    }

    public synchronized void add(T value) {
        this.list.add(value);
    }

    public synchronized T get(int index) {
        return this.list.get(index);
    }

    /**
     * Глубокая копия - каждый изменяемый элемент
     * рекурсивно копируеться.
     * Используем snapshots (копирование всех элементов).
     * @param list
     * @return List<T>
     */
    private List<T> copy(List<T> list) {
        List<T> deepCopy = new ArrayList<>();
        for (T element : list) {
            deepCopy.add(element);
        }
        return deepCopy;
    }

    /**
     * Отказоустойчивый итератор, работает с глубокой копией.
     * Итератор будет работать в режиме fail-safe
     * все изменения после получения коллекции не будут
     * отображаться в итераторе. Минусы токого итератора -
     * необходимость создавать глубокую копию и то что если
     * произойдут изменения в главной колекции, коллекция
     * итератора ничего об этом знать не будет.
     * @return Iterator<T>
     */
    @Override
    public synchronized Iterator<T> iterator() {
        return copy(this.list).iterator();
    }
}
