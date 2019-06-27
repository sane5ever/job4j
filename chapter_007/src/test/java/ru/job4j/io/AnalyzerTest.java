package ru.job4j.io;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class AnalyzerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final Analyzer analyzer = new Analyzer();

    @Test
    public void testUnavailable() throws IOException {
        String source = createAndFillTestFile(
                new StringJoiner("\n")
                        .add("200 10:56:01")
                        .add("500 10:57:01")
                        .add("400 10:58:01")
                        .add("200 10:59:01")
                        .add("500 11:01:02")
                        .add("300 11:02:02")
                        .toString()
        );
        String expected = "10:57:01;10:59:01\n11:01:02;11:02:02";
        assertEquals(source, expected);
    }

    @Test
    public void testUnavailableBlank() throws IOException {
        String source = createEmptyTempFile();
        assertEquals(source, "");
    }

    @Test
    public void testUnavailableNotExisted() throws IOException {
        String source = "not_existed";
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Error due read/write file");
        assertEquals(source, "no_matter");
    }


    private void assertEquals(String source, String expected) throws IOException {
        String target = createEmptyTempFile();
        analyzer.unavailable(source, target);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(target)))) {
            String actual = reader.lines().collect(Collectors.joining("\n"));
            Assert.assertEquals(expected, actual);
        }
    }

    private String createEmptyTempFile() throws IOException {
        return Files.createTempFile(null, null).toString();
    }

    private String createAndFillTestFile(String lines) throws IOException {
        Path tmp = Files.createTempFile(null, null);
        try (PrintWriter writer = new PrintWriter(Files.newOutputStream(tmp))) {
            writer.print(lines);
        }
        return tmp.toString();
    }
}