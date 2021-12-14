package ru.job4j.baeldung;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Мы создаем несколько случайных пакетов данных,
 * которые будут отправлены по сети в массиве packets []
 * Для каждого пакета мы вызываем send().
 */
public class Sender implements Runnable {
    private Data data;

    public Sender() {
    }

    public Sender(Data data) {
        this.data = data;
    }

    @Override
    public void run() {
        String[] packets = {
                "First packet",
                "Second packet",
                "Third packet",
                "Fourth packet",
                "End"
        };

        for (String packet : packets) {
            data.send(packet);
        }

        /**
         * имитируем тяжелую обработку на сервере.
         */
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 5000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}
