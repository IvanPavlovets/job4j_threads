package ru.job4j.ref;

/**
 * Класс пример не потокобезопасного поведения класса User:
 * 1. Добавляем объект в кеш - cache.add(user);
 * 2. Редактируем объект по ссылке -  user.setName("rename");
 * 3. Получаем значение - System.out.println(cache.findById(1).getName());
 * Последний оператор может напечатать name или rename.
 * Чтобы этого избежать, в кеш нужно добавлять копию объекта и возвращать копию.
 * Потокобезапасное повденеие после изменений класса UserCache:
 * После того как методы add и findById будут работать с копиями объекта User,
 * Нить first будет работает с локальным объект user.
 * Изменение этого объекта не влечет изменений в самом кеше.
 */
public class ShareNotSafe {
    public static void main(String[] args) throws InterruptedException {
        UserCache cache = new UserCache();
        User user = User.of("name");
        cache.add(user);
        Thread first = new Thread(
                () -> {
                    user.setName("rename");
                }
        );
        first.start();
        first.join();
        System.out.println(cache.findById(1).getName());
    }
}
