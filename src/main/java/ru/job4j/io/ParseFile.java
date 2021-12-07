package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

/**
 * Класс парсер файла.
 */
public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent() {
        return parse(integer -> true);
    }

    public String getContentWithoutUnicode() {
        return parse(integer -> integer < 0x80);
    }

    /**
     * Внутрений метод парсинга файла по условию.
     * @param filter
     * @return String
     */
    private String parse(Predicate<Integer> filter) {
        StringBuilder output = new StringBuilder();
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = in.read()) > 0) {
                if (filter.test(data)) {
                    output.append((char) data);
                }
            }
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return output.toString();
    }
}
