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
     * код для скачивания файла с задержкой до 1 мегабайта в секунду.
     */
    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream out = new FileOutputStream(urlOut)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long bytesWrited = 0;
            long deltaTime;
            long startLoading = System.currentTimeMillis();
            do {
                bytesRead = in.read(dataBuffer, 0, 1024);
                bytesWrited = bytesWrited + bytesRead;
                out.write(dataBuffer, 0, 1024);
                if (bytesWrited >= speed) {
                    deltaTime = System.currentTimeMillis() - startLoading;
                    if (deltaTime < 1000) {
                        Thread.sleep(1000 - deltaTime);
                    }
                }
            } while (bytesRead != -1);
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
     * @param args
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

    private static void validateArgsCount(String[] args) {
        if (args.length < 3) {
            throw new IllegalArgumentException("there must be 3 arguments!");
        }
    }
}
