package ru.job4j.filing;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class MultiFileSystemScanner {
    private List<File> result = new CopyOnWriteArrayList<>();
    private Queue<File> queue = new LinkedList<>();
    private int threads = Runtime.getRuntime().availableProcessors();
    private ExecutorService executor = Executors.newFixedThreadPool(threads);
    private Semaphore semaphore = new Semaphore(threads);

    /**
     * Метод для получения списка всех файлов с заданным расширением по указанному пути.
     * Поиск производится в многопоточном режиме.
     * Все внутренние каталоги обходятся в ширину.
     *
     * @param parent путь, в котором ищутся файлы.
     * @param exts   список расширений файлов, которые надо найти.
     * @return список найденных файлов.
     */
    public List<File> files(String parent, List<String> exts) {
        File root = new File(parent);
        queue.offer(root);
        while (!queue.isEmpty()) {
            File nextFileEntry = queue.poll();
            if (nextFileEntry.isDirectory()) {
                for (File file : nextFileEntry.listFiles()) {
                    queue.offer(file);
                }
            } else {
                executor.submit(
                        () -> {
                            try {
                                semaphore.acquire();
                                File file = nextFileEntry;
                                String fileName = file.getName();
                                int index = fileName.lastIndexOf(".") == -1 ? 0 : fileName.lastIndexOf(".");
                                String ext = fileName.substring(index);
                                for (String extension : exts) {
                                    if (extension.equals(ext)) {
                                        result.add(file);
                                        System.out.println(file.getPath());
                                        break;
                                    }
                                }
                                semaphore.release();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        });
            }
        }
        try {
            if (semaphore.availablePermits() == threads) {
                executor.shutdown();
            } else {
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
}
