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
 * счет Thread.sleep. Пауза вычисляеться - (speed - timeDelay).
 */
public class Wget implements Runnable {
    String url;
    int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    /**
     * кода для скачивания файла с задержкой.
     */
    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream out = new FileOutputStream("pom_tmp.xml")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long startLoading = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                out.write(dataBuffer, 0, bytesRead);
                Thread.sleep(1000);
                long finishLoading = System.currentTimeMillis();
                long timeDelay = finishLoading - startLoading;
                if (speed > timeDelay) {
                    Thread.sleep(speed - timeDelay);
                }
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = "https://raw.githubusercontent.com/peterarsentev/course_test/master/pom.xml";
        int speed = 2000;

        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
