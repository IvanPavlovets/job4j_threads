package ru.job4j.pool.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Пример использования Fork/Join для задачи
 * поиск индекса в массиве объектов.
 * Метод поиска обобщенный.
 * @param <T>
 */
public class ParrallelSearchIndex<T> extends RecursiveTask<Integer> {

    private final T[] arr;
    private final T element;
    private final int from;
    private final int to;


    public ParrallelSearchIndex(T[] arr, T element, int from, int to) {
        this.arr = arr;
        this.element = element;
        this.from = from;
        this.to = to;
    }

    /**
     * Поиск индекса в массиве по элементу.
     * Если масив до 10 элементов то linearSearch.
     * Если больше 10 то поиск с помощью Fork/Join.
     * @param arr масив для поиска
     * @param element элемент индекс которого ищем
     * @param <T>
     * @return индекс искомого элемента.
     */
    public static <T> int searchIndex(T[] arr, T element) {
        ForkJoinPool pool = new ForkJoinPool();
        return (int) pool.invoke(new ParrallelSearchIndex(arr, element, 0, arr.length - 1));
    }

    /**
     * Алгоритм линейного поиска. Применяеться
     * если размер массива не больше 10.
     * @param arr массив до 10 элементов.
     * @param searcElement искомый индекс.
     * @param from нижнее ограничение длинны в массиве
     * @param to верхнее ограничение длинны в масииве
     * @param <T> обобщеный тип
     * @return искомый индекс или -1
     */
    public static <T> int linearSearch(T[] arr, T searcElement, int from, int to) {
        for (int i = from; i <= to; i++) {
            if (arr[i].equals(searcElement)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Поиск индекса в массиве, с помощью каркаса fork/join.
     * Если размер массива до 10 элементов
     * то выполняеться линейный поиск.
     * to, from - нижнее и верхнее ограничение длинны в массиве.
     * для каждой половины задачи (leftSearch, rightSearch)
     * эти ограничения разные.
     * @return индекс искомого элемента в массиве
     */
    @Override
    protected Integer compute() {
        if ((to + 1) - from <= 10) {
            return linearSearch(arr, element, from, to);
        }
        int mid = (from + to) / 2;
        ParrallelSearchIndex<T> leftSearch = new ParrallelSearchIndex<>(arr, element, from, mid);
        ParrallelSearchIndex<T> rightSearch = new ParrallelSearchIndex<>(arr, element, mid + 1, to);
        leftSearch.fork();
        rightSearch.fork();
        return (leftSearch.join() + rightSearch.join() + 1);
    }

}
