package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.URL;

/**
 * консольная программа - аналог wget из предыдущего урока.
 * Программа скачивает файл из сети с ограничением по скорости скачки.
 * Для ограничения скорости скачивания, засекаеться время
 * скачивания 1024 байт - переменная startLoading.
 * Если время меньше указанного, то в условии выставляеться паузу за
 * счет Thread.sleep.
 */
public class Wget implements Runnable {
    private String url;
    private String urlOut;
    private int speed;

    public Wget(String url, String urlOut, int speed) {
        this.url = url;
        this.urlOut = urlOut;
        this.speed = speed;
    }

    /**
     * Код для скачивания файла с задержкой до 1 мегабайта в секунду.
     * время задержки вычисляеться в deltaTime.
     * Затем после набора количества проч./запис. байт в bytesWrited
     * >= пропускной способности speed идет вычисление задержки,
     * остатка от секунды = 1000.
     */
    @Override
    public void run() {
        int i = 1;
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream out = new FileOutputStream(urlOut)) {
            long startLoading = System.currentTimeMillis();
            long countTime = startLoading;
            long deltaTime;
            byte[] dataBuffer = new byte[1024];
            int bytesRead = 0;
            long bytesWrited = 0;
            while (bytesRead != -1) {
                bytesRead = in.read(dataBuffer, 0, 1024);
                bytesWrited = bytesWrited + bytesRead;
                out.write(dataBuffer, 0, 1024);
                if (bytesWrited >= speed) {
                    deltaTime = System.currentTimeMillis() - countTime;
                    if (deltaTime < 1000) {
                        Thread.sleep(1000 - deltaTime);
                    }
                    System.out.printf("%d bytesWrited: %d deltaTime: %d %s", i++, bytesWrited, deltaTime, "\n");
                    bytesWrited = 0;
                    countTime = System.currentTimeMillis();
                }
            }
            long endLoading = System.currentTimeMillis();
            long finisTime = endLoading - startLoading;
            System.out.printf("totalTime: %.2f", ((double) (finisTime)) / 1000);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            e.fillInStackTrace();
        }
    }

    /**
     * Для запуска программы нужно передать 3 аргумента
     * url - url файла;
     * urlOut - путь(url) на своей локальной машине, куда сохранить файд
     * bytesPerSecond - пропускная способность. Байты в секунду.
     * если ограничивать скорость до 1 мегабайта в секунду
     * - это 1048576 байт в секунду.
     * КБайт - 1024 байта.
     * МБайт - 1024 килобайта.
     * @param args аргументы для ввода с консоли
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        validateArgsCount(args);
        String url = args[0];
        String urlOut = args[1];
        int bytesPerSecond = Integer.parseInt(args[2]);

        Thread wget = new Thread(new Wget(url, urlOut, bytesPerSecond));
        wget.start();
        wget.join();
    }

    /**
     * Валидатор ввода. Пример ввода аргументов для консоли:
     * "https://proof.ovh.net/files/10Mb.dat" "targetPath" 1048576
     * 1 арг - пример сохраняемого файла,
     * 2 арг - путь к файлу в директории,
     * 3 арг - скорость байт в секунду (1048576 байт - 10 сек,
     * 2097152 байт - 5 сек)
     * @param args - 3 аргумента валидатора
     */
    private static void validateArgsCount(String[] args) {
        if (args.length < 3) {
            throw new IllegalArgumentException("there must be 3 arguments!");
        }
    }
}
