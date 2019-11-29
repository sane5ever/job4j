package ru.job4j.filing;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.TempIOData;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static ru.job4j.TempIOData.*;

/**
 * Заготовка для тестирования реализаций сканера файловой системы.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2019-03-05
 */
public abstract class AbstractFileSystemScannerTest {
    FileSystemScanner scanner;

    public AbstractFileSystemScannerTest(FileSystemScanner scanner) {
        this.scanner = scanner;
    }

    @BeforeClass
    public static void init() throws IOException {
        TempIOData.init();
    }

    @Test
    public void whenJavaAndXml() {
        List<File> files = scanner.files(TMP_TEST_ROOT, List.of("java", "xml"));
        assertEquals(
                Set.of(file1, file2),
                new HashSet<>(files) // to prevent test assertion error on multithreading scanner work
        );
    }

    @Test
    public void whenTxt() {
        assertEquals(
                List.of(file3),
                scanner.files(TMP_TEST_ROOT, List.of("txt"))
        );
    }

    @Test
    public void whenEmptyList() {
        assertEquals(
                Collections.<File>emptyList(),
                scanner.files(TMP_TEST_ROOT, Collections.emptyList())
        );
    }
}
