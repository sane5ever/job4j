package ru.job4j.filing;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Однопоточная реализация поисковика файлов в каталогах.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2019-03-05
 */
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
        List<File> result = initStorage();
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
        return checkResult(result);
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

    void addIfValid(List<File> result, File file, List<String> exts) {
        String fileName = file.getName();
        int i = fileName.lastIndexOf('.');
        String fileExt = i > 0 ? fileName.substring(i + 1) : null;
        if (fileExt != null && exts.contains(fileExt)) {
            result.add(file);
        }
    }

    /**
     * В реализациях класса могут понадобиться дополнительные мероприятия
     * перед возвратом результата работы основного метода.
     * <p>
     * Данная реализация является заглушкой для тех реализаций, где результат сразу готов к возврату.
     *
     * @param result результирующий список файлов
     * @return результат, кот. должен вернуть основной метод
     */
    List<File> checkResult(List<File> result) {
        return result;
    }

    /**
     * В реализациях класса могут быть использованы разные структуры для сохранения результатов поиска.
     * Стандартная реализация — {@link ArrayList}
     *
     * @return хранилище для списка файлов
     */
    List<File> initStorage() {
        return new ArrayList<>();
    }
}
