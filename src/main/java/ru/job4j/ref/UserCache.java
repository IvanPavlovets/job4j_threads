package ru.job4j.ref;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Пример потокобезапасной работы с User.
 * в кеш добавляем копию объекта и возвращаем копию.
 * Методы add и findById работают с копиями объекта User.
 */
public class UserCache {
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    /**
     * добавляет в users копию обьекта User.
     * @param user
     */
    public void add(User user) {
        users.put(id.incrementAndGet(), User.of(user.getName()));
    }

    /**
     * возвращает копию, найденого в users, обьекта по id
     * с таким же именем.
     * @param id
     * @return
     */
    public User findById(int id) {
        return User.of(users.get(id).getName());
    }

    /**
     * Что бы вернуть List обьектов User нужно
     * для каждого члена этого списка создать
     * копию этого обьекта через метод User.of()
     * @return
     */
    public List<User> findAll() {
        return users.values().stream()
                .map(user -> User.of(user.getName()))
                .collect(Collectors.toList());
    }
}
