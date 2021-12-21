package ru.job4j.cas;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Optional;


public class StackTest {

    @Test
    public void when3PushThen3Poll() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(Optional.of(stack.poll()), Optional.of(3));
        assertEquals(Optional.of(stack.poll()), Optional.of(2));
        assertEquals(Optional.of(stack.poll()), Optional.of(1));
    }

    @Test
    public void when1PushThen1Poll() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        assertEquals(Optional.of(stack.poll()), Optional.of(1));
    }

    @Test
    public void when2PushThen2Poll() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        assertEquals(Optional.of(stack.poll()), Optional.of(2));
        assertEquals(Optional.of(stack.poll()), Optional.of(1));
    }

}
