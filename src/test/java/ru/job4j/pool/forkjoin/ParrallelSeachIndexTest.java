package ru.job4j.pool.forkjoin;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParrallelSeachIndexTest {

    @Test
    public void whenEvenArrLenght() {
        Integer[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        assertEquals(9, ParrallelSearchIndex.searchIndex(arr, 10));
    }

    @Test
    public void whenOddArrLenght() {
        Integer[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        assertEquals(10, ParrallelSearchIndex.searchIndex(arr, 11));
    }

    @Test
    public void whenNoIndexThenNegativeValue() {
        Integer[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        assertEquals(-1, ParrallelSearchIndex.searchIndex(arr, 12));
    }
}
