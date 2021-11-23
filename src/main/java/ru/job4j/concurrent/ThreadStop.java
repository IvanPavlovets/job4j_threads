package ru.job4j.concurrent;

public class ThreadStop {
    /**
     * 1) В методе происходит прерывание потокка
     * (выставляем внутрений флаг true для прерывания нити)
     * метод isInterrupted() проверяет состояние флага, готов или нет
     * на прерывание. Если не готов то в цикле увеличиваем
     * счетчик count++. После прерывание нить переходит в
     * состояние TERMINATED, когда все операторы в методе run()
     * выполнены.
     * 2) Планировщик выделяет разное время для каждой нити,
     * по этой причине флаг прерывания выставляется в произвольное время.
     * Нить main выставляет прерывание - thread.interrupt();
     * В нити thread идет проверка этого флага.
     * Если он выставлен, то мы не заходим больше в тело цикла
     * и выходим из метода run().
     * @param args
     */
    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> {
                    int count = 0;
                    while (!Thread.currentThread().isInterrupted()) {
                        System.out.println(count++);
                    }
                }
        );
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
    }
}
