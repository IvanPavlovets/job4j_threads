package ru.job4j.io;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public final class FileSever {
    private final String urlOut;

    public FileSever(String urlOut) {
        this.urlOut = urlOut;
    }

    /**
     * Сохраняет строку в файл.
     * @param content
     */
    public synchronized void saveContent(String content) {
        try (BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(urlOut))) {
            for (int i = 0; i < content.length(); i += 1) {
                output.write(content.charAt(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
