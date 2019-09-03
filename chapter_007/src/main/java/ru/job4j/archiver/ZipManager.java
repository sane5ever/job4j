package ru.job4j.archiver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * ZipManager class provides zipping logic of archiver
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2019-06-27
 */
public class ZipManager {
    private final Path zipFile;
    private final Path root;
    private final List<File> files;

    public ZipManager(String zipFile, String root, List<File> files) {
        this.zipFile = Paths.get(zipFile);
        this.root = Paths.get(root);
        this.files = files;
    }

    /**
     * Main method to create zip archive
     *
     * @throws IOException when read-write errors have occurred
     */
    public void createZip() throws IOException {
        // to prevent recursive self-adding in case when the archive already exists in file list
        Path archive = createTempFile();
        try (ZipOutputStream zipOut = new ZipOutputStream(Files.newOutputStream(archive))) {
            for (File file : files) {
                addNewZipEntry(zipOut, file);
            }
        }
        createDirectoriesIfNotExist();
        Files.move(archive, zipFile, StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * Provides putting the next zip entry (file or directory) in archive
     *
     * @param zipOut output stream of created archive
     * @param file   current file to be added
     * @throws IOException when read-write errors have occurred
     */
    private void addNewZipEntry(ZipOutputStream zipOut, File file) throws IOException {
        String resolvedPath = root.relativize(file.toPath())
                + (file.isDirectory() ? File.separator : "");
        ZipEntry entry = new ZipEntry(resolvedPath);
        zipOut.putNextEntry(entry);
        if (file.isFile()) {
            try (InputStream in = new FileInputStream(file)) {
                // https://stackoverflow.com/a/39440936/10375242
                in.transferTo(zipOut);
            }
        }
        zipOut.closeEntry();
    }

    private void createDirectoriesIfNotExist() throws IOException {
        Path path = zipFile.toAbsolutePath().getParent();
        if (Files.notExists(path)) {
            Files.createDirectories(path);
        }
    }

    /**
     * Creates empty temp file
     *
     * @return tmp file
     * @throws IOException if cannot create temp file
     */
    private Path createTempFile() throws IOException {
        return Files.createTempFile(null, null);
    }
}
