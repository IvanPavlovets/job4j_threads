package ru.job4j.asynchrony;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Matrix {
    /**
     * Возвращает массив с подсчитаными суммами всех диагоналей матрицы вида int[][].
     * В цикле мы запускаем две задачи. Одну с начала обхода, другую с конца.
     */
    public static int[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int n = matrix.length;
        int[] sums = new int[2 * n];
        Map<Integer, CompletableFuture<Integer>> futures = new HashMap<>();
        // считаем суммы по главной диагонали
        futures.put(0, getTask(matrix, 0, n - 1, n - n, true));
        // считаем суммы по побочной диагонали
        for (int k = 1; k <= n; k++) {
            futures.put(k, getTask(matrix, 0, k - 1, k - 1, false));
            if (k < n) {
                futures.put(2 * n - k, getTask(matrix, n - k, n - 1, n - 1, false));
            }
        }
        for (Integer key : futures.keySet()) {
            sums[key] = futures.get(key).get();
        }
        return sums;
    }

    /**
     * Возвращает результат - подсчитывает сумму в одной диагонали.
     * Перед началом разбора рекомендуется нарисовать матрицу на бумаге и начертить диагонали
     * дектремент или инкремент для col в зависимости от значения traversal.
     * @param data изначальная матрица
     * @param startRow начало диагонали
     * @param endRow конец диагонали
     * @param startCol ряд в матрице, откуда начинаем читать диагональ
     * @param traversal направление диагонали (true - с лева на право, false - с права на лево)
     * @return CompletableFuture<Integer>
     */
    private static CompletableFuture<Integer> getTask(int[][] data, int startRow, int endRow, int startCol, boolean traversal) {
        return CompletableFuture.supplyAsync(
                () -> {
                    int sum = 0;
                    int col = startCol;
                    for (int i = startRow; i <= endRow; i++) {
                        sum += data[i][col];
                        col = traversal ? col + 1 : col - 1;
                    }
                    return sum;
                }
        );
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[][] matrix1 = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        System.out.println(Arrays.deepToString(matrix1));
        System.out.println(matrix1.length);

        System.out.println(Arrays.toString(Matrix.asyncSum(matrix1)));
    }
}
