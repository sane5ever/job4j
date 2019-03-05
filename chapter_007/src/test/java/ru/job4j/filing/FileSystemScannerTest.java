package ru.job4j.filing;

/**
 * Тестирование однопоточного сканера файловой системы.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2019-03-05
 */
public class FileSystemScannerTest extends AbstractFileSystemScannerTest {
    public FileSystemScannerTest() {
        super(new FileSystemScanner());
    }
}