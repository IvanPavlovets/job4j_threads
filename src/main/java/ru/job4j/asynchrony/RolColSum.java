package ru.job4j.asynchrony;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Класс подсчитывает суммы по строкам и столбцам квадратной матрицы.
 * - sums[i].rowSum - сумма элементов по i строке
 * - sums[i].colSum  - сумма элементов по i столбцу
 */
public class RolColSum {
    public static class Sums {
        /**
         * сумма элементов по i строке
         */
        private int rowSum;
        /**
         * сумма элементов по i столбцу
         */
        private int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum
                    && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }

        @Override
        public String toString() {
            return "Sums{"
                    + "rowSum="
                    + rowSum
                    + ", colSum="
                    + colSum
                    + '}';
        }
    }

    /**
     * Последовательная версия программы
     * Логика аналогична асинхронной версии за исключением
     * того что внутриний цикл выполняеться последовательно.
     * @param matrix
     * @return
     */
    public static Sums[] sum(int[][] matrix) {
        int rowSum;
        int columnSum;
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            rowSum = 0;
            columnSum = 0;
            for (int j = 0; j < matrix.length; j++) {
                rowSum += matrix[i][j];
                columnSum += matrix[j][i];
            }
            sums[i] = new Sums(rowSum, columnSum);
        }
        return sums;
    }

    /**
     * Асинхронная версия программы.
     * В цикле осуществляеться проход по элементам матрицы matrix
     * при этом каждый подсчет сумм, поля класса Sums, в методе calcSum
     * происходит асинхронно. Результат подсчитанных сумм, по вертикали
     * и горизонтали, заноситься в поля экземпляра Sums и достаеться
     * с помощью get(). Экземпляр Sums в свою очередь становиться
     * элементом массива sums.
     * Поля экземпляра это суммы по вертикали и горизонтали матрицы.
     * @param matrix Sums и есть суммы по вертикали и горизонтали.
     * @return массив обьектов Sums
     */
    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = calcSum(matrix, i).get();
        }
        return sums;
    }

    /**
     * supplyAsync() - запускаем асинхронное выполнение задачи
     * из которой вернеться результат.
     * Для наглядности обхода элементов матрицы нарисуйте ее.
     * В методе происходит проход по элементам матрицы data,
     * rowSum - аккамулиреться сумма элементов по горизонтали.
     * columnSum - аккамулиреться сумма элементов по вертикали.
     * @param data переданая матрица типа int[][]
     * @param index индекс верхнего цикла
     * @return экземпляр Sums, с полями суммами по горизонтали и вертикали,
     * обернутый в CompletableFuture.
     */
    private static CompletableFuture<Sums> calcSum(int[][] data, int index) {
        return CompletableFuture.supplyAsync(() -> {
            int rowSum = 0;
            int columnSum = 0;
            for (int i = 0; i < data.length; i++) {
                rowSum += data[index][i];
                columnSum += data[i][index];
            }
            return new Sums(rowSum, columnSum);
        });
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[][] matrix1 = new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8},
                {9, 10, 11, 12}, {13, 14, 15, 16}};
        System.out.println(Arrays.deepToString(matrix1));
        System.out.println(matrix1.length);

        System.out.println(Arrays.toString(RolColSum.asyncSum(matrix1)));
    }

}
