package ru.job4j.filing;

/**
 * Тестирование многопоточного сканера файловой системы.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2019-03-05
 */
public class MultiFileSystemScannerTest extends AbstractFileSystemScannerTest {
    public MultiFileSystemScannerTest() {
        super(new MultiFileSystemScanner());
    }
}