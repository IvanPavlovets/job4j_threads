package ru.job4j.asynchrony;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

public class RolColSumTest {

    @Test
    public void whenAsyncSumThenMatrixAreEqual() throws ExecutionException, InterruptedException {
        int[][] matrix1 = new int[][]{
                {1, 2, 3, 4}, {5, 6, 7, 8},
                {9, 10, 11, 12}, {13, 14, 15, 16}
        };
        RolColSum.Sums[] expected = {
                new RolColSum.Sums(10, 28),
                new RolColSum.Sums(26, 32),
                new RolColSum.Sums(42, 36),
                new RolColSum.Sums(58, 40),
        };
        assertEquals(RolColSum.asyncSum(matrix1), expected);
    }

    @Test
    public void whenSumThenMatrixAreEqual() throws ExecutionException, InterruptedException {
        int[][] matrix1 = new int[][]{
                {1, 2, 3, 4}, {5, 6, 7, 8},
                {9, 10, 11, 12}, {13, 14, 15, 16}
        };
        RolColSum.Sums[] expected = {
                new RolColSum.Sums(10, 28),
                new RolColSum.Sums(26, 32),
                new RolColSum.Sums(42, 36),
                new RolColSum.Sums(58, 40),
        };
        assertEquals(RolColSum.sum(matrix1), expected);
    }

}
