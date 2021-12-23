package ru.job4j.cas.cache;

import org.junit.Test;
import ru.job4j.cas.Stack;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class CacheTest {
    /**
     * 2 раза вызываем update
     * и видим что version увеличиваеться.
     */
    @Test
    public void whenUpdateThenVersinIncreased() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        cache.add(base);
        cache.update(base);
        cache.update(base);

        assertEquals(cache.getMemory().get(1).getVersion(), 2);
    }
}
