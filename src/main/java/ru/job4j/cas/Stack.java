package ru.job4j.cas;

import net.jcip.annotations.NotThreadSafe;

@NotThreadSafe
public class Stack<T> {
    private Node<T> head;

    /**
     * Поместить элемент в стек.
     * делает операцию check-then-act.
     * @param value
     */
    public void push(T value) {
        Node<T> temp = new Node<>(value);
        if (head == null) {
            head = temp;
            return;
        }
        temp.next = head;
        head = temp;
    }

    /**
     * Вытолкнуть из стека элемент лежащий наверху.
     * делает операцию check-then-act.
     * @return T
     */
    public T poll() {
        Node<T> temp = head;
        if (temp == null) {
            throw new IllegalStateException("Stack is empty");
        }
        head = temp.next;
        temp.next = null;
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
