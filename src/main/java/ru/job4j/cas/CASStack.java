package ru.job4j.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Здесь нет синхронизации, но класс потокобезопасный.
 * Класс поддерживает CAS операции.
 * compare-and-swap - атомарная операция на уровне процессора,
 * тоесть внутри себя уже делает атомарно check-then-act.
 * head.compareAndSet(ref, temp) - атомарная операция:
 * head - сравниваемое значение;
 * ref - то с чем сравниваем;
 * temp - результат который будет записан в head при условии что head и ref равны.
 * @param <T>
 */
@ThreadSafe
public class CASStack<T> {
    private final AtomicReference<Node<T>> head = new AtomicReference<>();

    /**
     * Поместить элемент в стек.
     * @param value
     */
    public void push(T value) {
        Node<T> temp = new Node<>(value);
        Node<T> ref;
        do {
            ref = head.get();
            temp.next = ref;
        } while (!head.compareAndSet(ref, temp));
    }

    /**
     * Вытолкнуть из стека элемент лежащий наверху.
     * @return T
     */
    public T poll() {
        Node<T> temp;
        Node<T> ref;
        do {
            ref = head.get();
            if (ref == null) {
                throw new IllegalStateException("Stack is empty");
            }
            temp = ref.next;
        } while (!head.compareAndSet(ref, temp));
        ref.next = null;
        return temp.value;
    }

    private static final class Node<T> {
        private final T value;
        private Node<T> next;

        public Node(final T value) {
            this.value = value;
        }
    }
}
