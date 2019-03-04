package ru.job4j.filing;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FileSystemScanner {
    /**
     * Возвращает список файлов, найденных в заданном каталоге,
     * кот. соответствуют переданному списку расширений
     *
     * @param parent начальный каталог, с кот. осуществляется поиск
     * @param exts   расширения интересующих файлов
     * @return список файлов, найденный в заданном каталоге
     */
    List<File> files(String parent, List<String> exts) {
        List<File> result = new ArrayList<>();
        File directory = new File(parent);
        Queue<File> order = new LinkedList<>();
        order.offer(directory);
        while (!order.isEmpty()) {
            File file = order.poll();
            if (file.isDirectory()) {
                fillOrder(order, file.listFiles());
            } else if (file.isFile()) {
                addIfValid(result, file, exts);
            }
        }
        return result;
    }

    /**
     * Добавляет массив файлов в очередь
     *
     * @param order очередь файлов
     * @param files массив файлов
     */
    private void fillOrder(Queue<File> order, File[] files) {
        if (files != null) {
            for (File f : files) {
                order.offer(f);
            }
        }
    }

    /**
     * Добавляет заданный файл, если его расширение присутствует в переданном списке расширений
     *
     * @param result список для добавления
     * @param file   заданный файл
     * @param exts   список расширений
     */
    private void addIfValid(List<File> result, File file, List<String> exts) {
        String fileName = file.getName();
        int i = fileName.lastIndexOf('.');
        String fileExt = i > 0 ? fileName.substring(i + 1) : null;
        if (fileExt != null && exts.contains(fileExt)) {
            result.add(file);
        }
    }
}