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
     * Returns list of files which placed in specified directory (or sub-directories of it).
     * Only files with specified extensions will be added.
     *
     * @param parent root directory
     * @param exts   list of extensions of files which need to be added
     * @return filtered file list
     */
    public List<File> files(String parent, List<String> exts) {
        return files(parent, exts, true, false);
    }

    /**
     * Returns list of files and sub-directories which placed in specified directory (or sub-directories of it).
     * Only files without specified extensions will be added.
     *
     * @param parent root directory
     * @param exts   list of extensions of files which need to be skipped
     * @return filtered file list
     */
    public List<File> skipFilesPlusDirectories(String parent, List<String> exts) {
        return files(parent, exts, false, true);
    }


    /**
     * Возвращает список файлов, найденных в заданном каталоге,
     * кот. соответствуют переданному списку расширений
     *
     * @param parent          начальный каталог, с кот. осуществляется поиск
     * @param exts            расширения интересующих файлов
     * @param isIncludedExts  включаются или исключается из списка переданный список расширений
     * @param needIncludeDirs необходимо ли добавить директории в список файлов
     * @return список файлов, найденный в заданном каталоге
     */
    List<File> files(String parent, List<String> exts, boolean isIncludedExts, boolean needIncludeDirs) {
        List<File> result = initStorage();
        File directory = new File(parent);
        Queue<File> order = new LinkedList<>();
        order.offer(directory);
        while (!order.isEmpty()) {
            File file = order.poll();
            if (file.isDirectory()) {
                processDirectory(result, file, order, needIncludeDirs);
            } else if (file.isFile()) {
                addIfValid(result, file, exts, isIncludedExts);
            }
        }
        if (needIncludeDirs) {
            result.remove(0);  // no need to include root in the result list
        }
        return checkResult(result);
    }

    /**
     * Gets file list of specified directory, then adds them in processing order.
     * Add empty directory in result list if necessary
     *
     * @param result          file list to be collected
     * @param directory       processing directory
     * @param order           processing order
     * @param needIncludeDirs {@code true} if empty dirs need to be added in result list
     */
    private void processDirectory(List<File> result, File directory, Queue<File> order, boolean needIncludeDirs) {
        File[] files = directory.listFiles();
        if (files != null) {
            if (needIncludeDirs) {
                result.add(directory);
            }
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

    void addIfValid(List<File> result, File file, List<String> exts, boolean included) {
        String fileName = file.getName();
        int i = fileName.lastIndexOf('.');
        String fileExt = i > 0 ? fileName.substring(i + 1) : null;
        if (checkExt(fileExt, exts, included)) {
            result.add(file);
        }
    }

    private boolean checkExt(String fileExt, List<String> exts, boolean included) {
        return included
                ? fileExt != null && exts.contains(fileExt)
                : fileExt == null || !exts.contains(fileExt);
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
