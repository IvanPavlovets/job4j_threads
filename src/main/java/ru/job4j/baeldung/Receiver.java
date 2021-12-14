package ru.job4j.baeldung;

import java.util.concurrent.ThreadLocalRandom;

/**
 * В цикле просто вызываем receive()
 * пока не получим пакет данных "End".
 */
public class Receiver implements Runnable {
    private Data load;

    public Receiver() {
    }

    public Receiver(Data data) {
        this.load = data;
    }

    @Override
    public void run() {
        for (String msg = load.receive();
             !"End".equals(msg);
             msg = load.receive()) {
            System.out.println(msg);
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 5000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }
}
