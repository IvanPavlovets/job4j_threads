package ru.job4j.concurrent;

public class Wget {
    /**
     * В методе происходит имитация загрузки.
     * В конструкторе нити метод print печатает
     * символы в строку без перевода каретки.
     * Символ \r указывает, что каретку каждый
     * раз нужно вернуть в начало строки.
     * Таким образом через промежуток времени
     * каждый раз подменяеться строка
     * Выполнение потока при этом приостанавливаеться
     * что бы человек мог увидеть эти остановки
     *
     * @param args
     */
    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> {
                    try {
                        System.out.println("Start loading ... ");
                        for (int i = 0; i <= 100; i++) {
                            System.out.print("\rLoading : " + i + "%");
                            Thread.sleep(1000);
                        }
                        System.out.println("\nLoading has been completed");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
        );
        thread.start();
    }
}
