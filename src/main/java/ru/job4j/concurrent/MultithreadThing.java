package ru.job4j.concurrent;

public class MultithreadThing implements Runnable{
    int number;

    public MultithreadThing(int number) {
        this.number = number;
    }

    /**
     * в методе приостанавливаем выполнения на 500 мс.
     * что бы во время этой остановки начали работу другие потоки.
     */
    @Override
    public void run() {
        for (int i = 0; i <= 5; i++) {
            System.out.println(i + " threadNumber: " + number);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * в медоде демонстрируеться работа метода join()
     * - планировщик потоков запустит первый поток и
     * другие потоки будут ждать пока он не завершиться
     * затем запустятться другие потоки.
     * Без этого метода потоки будут запускаться вразноброс.
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            MultithreadThing threadThing = new MultithreadThing(i);
            Thread thread = new Thread(threadThing);
            thread.start();
            thread.join();
        }
    }
}
