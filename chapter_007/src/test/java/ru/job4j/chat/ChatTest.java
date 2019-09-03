package ru.job4j.chat;

import org.junit.Test;

import java.io.*;
import java.util.StringJoiner;
import java.util.function.Function;

import static java.lang.System.lineSeparator;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ChatTest {

    @Test
    public void testStart() throws IOException {
        String testInput = "привет\nзакончить\n";
        String expected = new StringJoiner(lineSeparator())
                .add("Client: привет")
                .add("Bot: привет?")
                .add("Client: закончить")
                .add("Bot: Пока!")
                .add("")
                .toString();
        assertLogOutput(testInput, expected);
    }

    @Test
    public void testFillDictionary() throws IOException {
        String testInput = "реплика1\nреплика2\nреплика3\nзакончить\n";
        String expected = new StringJoiner(lineSeparator())
                .add("Client: реплика1")
                .add("Bot: idk")
                .add("Client: реплика2")
                .add("Bot: idk")
                .add("Client: реплика3")
                .add("Bot: idk")
                .add("Client: закончить")
                .add("Bot: Пока!")
                .add("")
                .toString();
        assertLogOutput(testInput, "idk", expected);
    }

    @Test
    public void testFillDictionaryWithError() throws IOException {
        String testInput = "реплика1\nреплика2\nреплика3\nзакончить\n";
        String expected = new StringJoiner(lineSeparator())
                .add("Client: реплика1")
                .add("Bot: реплика1?")
                .add("Client: реплика2")
                .add("Bot: реплика2?")
                .add("Client: реплика3")
                .add("Bot: реплика3?")
                .add("Client: закончить")
                .add("Bot: Пока!")
                .add("")
                .toString();
        try (InputStream erroredIn = new InputStream() {
            @Override
            public int read() throws IOException {
                throw new IOException("test");
            }
        }) {
            assertLogOutput(testInput, out -> new Chat(out).fillBotDictionary(erroredIn), expected);
        }

    }

    @Test
    public void testStartWithIOError() throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             InputStream erroredIn = new InputStream() {
                 @Override
                 public int read() throws IOException {
                     throw new IOException("test");
                 }
             }) {
            Chat chat = new Chat(erroredIn, out);
            chat.start();
            assertTrue(out.toString().isEmpty());
        }
    }

    @Test
    public void testStartPauseContinueKeyWords() throws IOException {
        String testInput = "реплика1\nстоп\nреплика2\nреплика3\nпродолжить\nреплика4\nзакончить\n";
        String expected = new StringJoiner(lineSeparator())
                .add("Client: реплика1")
                .add("Bot: so...")
                .add("Client: стоп")
                .add("Client: реплика2")
                .add("Client: реплика3")
                .add("Client: продолжить")
                .add("Bot: so...")
                .add("Client: реплика4")
                .add("Bot: so...")
                .add("Client: закончить")
                .add("Bot: Пока!")
                .add("")
                .toString();
        assertLogOutput(testInput, "so...", expected);
    }

    private void assertLogOutput(String dataInput, String dictionary, String expected) throws IOException {
        try (ByteArrayInputStream inDictionary = new ByteArrayInputStream(dictionary.getBytes())) {
            assertLogOutput(dataInput, out -> new Chat(out).fillBotDictionary(inDictionary), expected);
        }
    }

    private void assertLogOutput(String dataInput, String expected) throws IOException {
        assertLogOutput(dataInput, Chat::new, expected);
    }

    private void assertLogOutput(String dataInput, Function<OutputStream, Chat> chatCreator, String expected) throws IOException {
        InputStream originalIn = System.in;
        try (InputStream in = new ByteArrayInputStream(dataInput.getBytes());
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            System.setIn(in);
            Chat chat = chatCreator.apply(out);
            chat.start();
            assertEquals(expected, out.toString());
            System.setIn(originalIn);
        }
    }

}