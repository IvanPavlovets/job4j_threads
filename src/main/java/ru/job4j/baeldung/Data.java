package ru.job4j.baeldung;

public class Data {
    /**
     * Данные которые передаються по сети.
     */
    private String packet;

    /**
     * Переменая которую Sender и Receiver используют
     * для синхронизации.
     * True - получатель должен подождать отправителя.
     * False - отправитель должен подождать, пока получатель
     * получит сообщение.
     */
    private boolean transfer = true;

    /**
     * Отправитель использует метод send() для отправки данных получателю.
     * Если transfer = false, мы ждем вызов метода wait() в этом потоке.
     * Если transfer = true, мы переключаем статус и устанавливаем сообщение
     * и вызываем notifyAll(), что бы разбудить другие потоки, указывая им что
     * произошло событие и они могут проверить, могут ли они продолжать выполнение.
     * @param packet
     */
    public synchronized void send(String packet) {
        while (!transfer) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
        transfer = false;
        this.packet = packet;
        notifyAll();
    }

    /**
     * Метод для приема данных.
     * Получатель использует метод:
     * Если transfer = false, будет продолжена передача,
     * в противном случае вызывем wait() в этом потоке.
     * Когда условие выполнено, мы переключаем статус,
     * notifyAll() - для всех ожидающих потоков
     * и возвращаем пакет данных который отправлен Отправителем.
     * @return String пакет даных.
     */
    public synchronized String receive() {
        while (transfer) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
        transfer = true;
        notifyAll();
        return packet;
    }
}
