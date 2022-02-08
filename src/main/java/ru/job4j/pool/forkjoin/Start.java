package ru.job4j.pool.forkjoin;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * В клссе проверяем скорость выполнения задачи с распараллеливанием и без.
 */
public class Start {
    public static void main(String[] args) {
        /**
         * большой массив имитирует большую задачу.
         */
        int[] array1 = IntStream.range(1, 10000001)
                .map(i -> 10000001 - i)
                .toArray();

        /**
         * Вычисления большой задачи без распараллеливания.
         */
        long startSort = System.currentTimeMillis();
        Arrays.toString(MergeSort.sort(array1));
        long endSort = System.currentTimeMillis();
        long finisTime = endSort - startSort;
        System.out.println(finisTime);

        /**
         * Вычисления большой задачи с пулом fork/join.
         */
        long startSort1 = System.currentTimeMillis();
        Arrays.toString(ParallelMergeSort.sort(array1));
        long endSort1 = System.currentTimeMillis();
        long finisTime1 = endSort1 - startSort1;
        System.out.println(finisTime1);

        /**
         * Нахождения индекса элемента в массиве.
         * Сначала создаем Integer[] из array1.
         */
        Integer[] arr = Arrays.stream(array1).boxed().toArray(Integer[]::new);
        long startSort2 = System.currentTimeMillis();
        ParrallelSearchIndex.searchIndex(arr, 50000);
        long endSort2 = System.currentTimeMillis();
        long finisTime2 = endSort2 - startSort2;
        System.out.println("bigSearch: " + finisTime2);

        /**
         * Запуски задачи с нечетными масивами.
         */
        Integer[] arr1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        long startSort3 = System.currentTimeMillis();
        System.out.println(ParrallelSearchIndex.searchIndex(arr1, 10));
        long endSort3 = System.currentTimeMillis();
        long finisTime3 = endSort3 - startSort3;
        System.out.println("seachLine: " + finisTime3);

        Integer[] arr2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        long startSort4 = System.currentTimeMillis();
        System.out.println(ParrallelSearchIndex.searchIndex(arr2, 13));
        long endSort4 = System.currentTimeMillis();
        long finisTime4 = endSort4 - startSort4;
        System.out.println("seachFork/Join: " + finisTime4);


    }
}
