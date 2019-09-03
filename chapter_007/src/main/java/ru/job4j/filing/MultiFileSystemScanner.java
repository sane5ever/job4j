package ru.job4j.filing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Многопоточная реализация сканера файловой системы.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2019-03-05
 */
public class MultiFileSystemScanner extends FileSystemScanner {
    private static final Logger LOG = LoggerFactory.getLogger(MultiFileSystemScanner.class);
    /**
     * Исполнитель потоков для валидации элементов в файловой системе
     */
    private ExecutorService executor;

    @Override
    void addIfValid(List<File> result, File file, List<String> exts, boolean included) {
        executor.execute(() -> super.addIfValid(result, file, exts, included));
    }

    /**
     * Перед возвратом результата из основного метода необходимо дождаться завершения работы тредпула {@link #executor}
     *
     * @param result результирующий список файлов
     * @return результат, кот. должен вернуть основной метод
     */
    @Override
    List<File> checkResult(List<File> result) {
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            LOG.error("interrupted exception via awaiting thread pool termination", e);
        }
        return result;
    }

    /**
     * В многопоточной реализации необходимо потокобезопасное хранилище результатов.
     * Так же инициализирует новый исполнитель потоков для обработки фильтрации файлов.
     *
     * @return {@link CopyOnWriteArrayList} потокобезопасная реализация листа
     */
    @Override
    List<File> initStorage() {
        executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        return new CopyOnWriteArrayList<>();
    }
}