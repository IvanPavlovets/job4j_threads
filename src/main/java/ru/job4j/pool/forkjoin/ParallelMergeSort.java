package ru.job4j.pool.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/*
Приммер рекурсивной сортировки слиянием (merge sort).
Сортировка не изменяет исходный порядок элементов,
а возвращает новый отсортированный массив, поэтому
создаются новые массивы в коде, а не перестановка
элементов в текущем.
Версия с пулом.
 */
public class ParallelMergeSort extends RecursiveTask {

    private final int[] array;
    private final int from;
    private final int to;

    public ParallelMergeSort(int[] array, int from, int to) {
        this.array = array;
        this.from = from;
        this.to = to;
    }

    /**
     * В условии - если массив из 1 элемента возвращаем 1 элемент.
     * Если в массиве больше 1 элемента, то создаем задачи (leftSort, rightSort)
     * для сортировки частей. Затем делаем fork до тех пор пока в частях
     * не останиться по 1 элементу.
     * В join - обьеденяем полученые результаты.
     * @return
     */
    @Override
    protected int[] compute() {
        if (from == to) {
            return new int[] {
                    array[from]
            };
        }
        int mid = (from + to) / 2;
        ParallelMergeSort leftSort = new ParallelMergeSort(array, from, mid);
        ParallelMergeSort rightSort = new ParallelMergeSort(array, mid + 1, to);
        leftSort.fork();
        rightSort.fork();
        int[] left = (int[]) leftSort.join();
        int[] right = (int[]) rightSort.join();
        return MergeSort.merge(left, right);
    }

    public static int[] sort(int[] array) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return (int[]) forkJoinPool.invoke(new ParallelMergeSort(array, 0, array.length - 1));
    }
}
