package ru.job4j.archiver;

import ru.job4j.filing.FileSystemScanner;

import java.io.File;
import java.util.List;

public class MainZip {

    public static void main(String[] args) {
        try {
            new MainZip().start(args);
        } catch (Exception e) {
            System.out.println("cannot create zip: " + e.getMessage());
        }
    }

    public void start(String[] args) throws Exception {
        Args arguments = new Args(args);
        arguments.parse();
        FileSystemScanner scanner = new FileSystemScanner();
        List<String> exts = arguments.exclude();
        // we need empty folders to save file system hierarchy in the archive
        List<File> files = scanner.skipFilesPlusDirectories(arguments.directory(), exts);

        ZipManager manager = new ZipManager(arguments.output(), arguments.directory(), files);
        manager.createZip();
        System.out.println("Archive of '" + arguments.directory() + '\''
                + (exts.isEmpty() ? "" : " excluding " + exts.toString() + " files")
                + " is successfully created in " + arguments.output()
        );
    }
}