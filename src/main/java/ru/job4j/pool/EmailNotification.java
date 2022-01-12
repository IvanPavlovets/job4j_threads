package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Класс сервис для рассылки почты.
 */
public class EmailNotification {
    /**
     * Создаем готвый thredPool по количеству ядер в системе.
     */
    ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());

    /**
     * через ExecutorService отправяет почту.
     * создаеться задача submit(), которая будет создавать данные для пользователя
     * и передавать их в метод send, по шаблону.
     * Метод берет данные пользователя и подставлять в шаблон:
     * subject = Notification {username} to email {email}.
     * body = Add a new event to {username}
     * @param user
     */
    public void emailTo(User user) {
        pool.submit(() -> {
            String subject = String.format("Notification {%s} to email {%s}", user.getUsername(), user.getEmail());
            String body = String.format("Add a new event to {%s}", user.getUsername());
            send(subject, body, user.getEmail());
        });
    }

    public void close() {
        pool.shutdown();
    }

    public void send(String subject, String body, String email) {
        /*
          метод send() - должен быть пустой.
         */
    }
}
