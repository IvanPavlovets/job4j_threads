package ru.job4j.baeldung;

/**
 * низкоуровневые API, такие как wait (), notify () и notifyAll (),
 * являются традиционными методами, которые работают хорошо,
 * но механизмы более высокого уровня часто проще и лучше - например,
 * native интерфейсы Lock и Condition (доступны в пакете java.util.concurrent.locks).
 */
public class Start {
    public static void main(String[] args) {
        Data data = new Data();
        Thread sender = new Thread(new Sender(data));
        Thread receiver = new Thread(new Receiver(data));

        sender.start();
        receiver.start();

    }
}
