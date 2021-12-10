package ru.job4j.transaction;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

/**
 * Rлассическая задача по переводу денег с одного счета на другой.
 * Чтобы операции были атомарны, нам нужно один объект монитора.
 */
@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final Map<Integer, User> users = new HashMap<>();

    /**
     * добовяет в мапу элемент, если его в ней нет,
     * если такой элемент есть в мапе возвращаем null.
     * @param user
     * @return
     */
    public synchronized boolean add(User user) {
        return users.putIfAbsent(user.getId(), user) != null;
    }

    /**
     * Находит в колеции обьект user и обновить его деньги,
     * если обьекта нет, то обновления не происходит.
     * При добавлении в хешмапу обьекта с существующим ключом,
     * предыдущий элемент будет перезаписан.
     * @param user
     * @return boolean
     */
    public synchronized boolean update(User user) {
        return users.replace(user.getId(), user) != null;
    }

    /**
     * Удаляет из мапы запись, если сопоставленая по ключа запись
     * есть в мапе.
     * @param user
     * @return
     */
    public synchronized boolean delete(User user) {
        return users.remove(user.getId()) != null;
    }

    /**
     * Достаем записи по id.
     * Условие - если обьекты существуют и есть сумма,
     * то отнимаем у одного и прибовляем другому
     * @param fromId
     * @param toId
     * @param amount
     */
    public synchronized void transfer(int fromId, int toId, int amount) {
        User from = users.get(fromId);
        User to = users.get(toId);
        if ((from != null) || (to != null) || (from.getAmount() - amount) >= 0) {
            users.replace(fromId, new User(fromId, from.getAmount() - amount));
            users.replace(toId, new User(toId, to.getAmount() + amount));
        } else {
            System.out.println("Несуществующий id User-а или не хватает денег на счете.");
        }
    }

    @Override
    public String toString() {
        return "UserStorage{" +
                "users=" + users +
                '}';
    }

    public static void main(String[] args) {
        UserStorage storage = new UserStorage();
        storage.add(new User(1, 100));
        storage.add(new User(2, 200));

        storage.transfer(2, 1, 150);

        System.out.println(storage);

        User removed = new User(1, 1000);

        System.out.println(storage.delete(removed));
        storage.add(new User(3, 400));
        storage.transfer(3, 2, 100);
        System.out.println(storage);
    }
}
