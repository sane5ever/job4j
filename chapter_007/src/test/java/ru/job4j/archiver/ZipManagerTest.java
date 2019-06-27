package ru.job4j.archiver;


import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.TempIOData;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static ru.job4j.TempIOData.*;
import static ru.job4j.archiver.ArgsTest.ARCHIVE_PATH;

public class ZipManagerTest {

    private static final Path ARCHIVE = Paths.get(ARCHIVE_PATH);

    private static final char FS = File.separatorChar;

    @BeforeClass
    public static void init() throws IOException {
        TempIOData.init();
    }

    @Test
    public void testCreateZip() throws IOException {
        ZipManager manager = new ZipManager(
                ARCHIVE_PATH, TMP_TEST_ROOT, List.of(file1, file2, file3)
        );
        Files.deleteIfExists(ARCHIVE);
        assertTrue(Files.notExists(ARCHIVE));
        manager.createZip();
        assertTrue(Files.exists(ARCHIVE));
        List<String> filenames = getArchiveStructure();
        assertEquals(3, filenames.size());
        assertEquals("1" + FS + '4' + FS + "2.xml", relativize(TMP_TEST_ROOT, file2.getPath()));
    }

    @Test
    public void testCreatedZipContent() throws IOException {
        ZipManager manager = new ZipManager(ARCHIVE_PATH, TMP_TEST_ROOT, List.of(file1));
        String expected = fillFile(file1);
        manager.createZip();
        try (ZipInputStream zipIn = new ZipInputStream(Files.newInputStream(ARCHIVE));
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            zipIn.getNextEntry();
            zipIn.transferTo(out);
            String actual = out.toString();
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testCreateEmptyZip() throws IOException {
        ZipManager manager = new ZipManager(ARCHIVE_PATH, TMP_TEST_ROOT, List.of());
        manager.createZip();
        List<String> filenames = getArchiveStructure();
        assertTrue(filenames.isEmpty());
    }

    @Test
    public void testCreateZipWithEmptyFolder() throws IOException {
        File dir = DIRS.get(1);
        ZipManager manager = new ZipManager(ARCHIVE_PATH, TMP_TEST_ROOT, List.of(dir));
        manager.createZip();
        List<String> names = getArchiveStructure();
        assertEquals(1, names.size());
        assertEquals(dir.getName() + FS, names.get(0));
    }

    @Test
    public void testCreateZipWhenParentDirsNotExist() throws IOException {
        Path newArchivePath = Paths.get(TMP_TEST_ROOT, "new_path", "2", "3", "4", "archive.zip");
        assertTrue(Files.notExists(newArchivePath));
        ZipManager manager = new ZipManager(newArchivePath.toString(), TMP_TEST_ROOT, List.of());
        manager.createZip();
        assertTrue(Files.exists(newArchivePath));
        removeToRoot(newArchivePath);
    }

    private void removeToRoot(Path path) throws IOException {
        Path root = Paths.get(TMP_TEST_ROOT);
        while (!path.equals(root)) {
            Files.delete(path);
            path = path.getParent();
        }
    }

    private List<String> getArchiveStructure() throws IOException {
        List<String> names = new ArrayList<>();
        try (ZipInputStream zipIn = new ZipInputStream(Files.newInputStream(ARCHIVE))) {
            for (ZipEntry entry = zipIn.getNextEntry(); entry != null; entry = zipIn.getNextEntry()) {
                names.add(entry.getName());
            }
        }
        return names;
    }

    private String fillFile(File file) throws IOException {
        String data = file.getName();
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(file))) {
            writer.print(data);
        }
        return data;
    }


}
