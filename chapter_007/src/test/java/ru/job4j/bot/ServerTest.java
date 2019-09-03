package ru.job4j.bot;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringJoiner;

import static java.lang.String.format;
import static java.lang.System.lineSeparator;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServerTest {
    public static final String LS = lineSeparator();

    @Test
    public void testGoodbyeCommand() throws IOException {
        processCommands(null);
    }

    @Test
    public void testDateCommand() throws IOException {
        SimpleDateFormat formatter = new SimpleDateFormat("d.MM.YYYY");
        processCommands(formatter.format(new Date()), "date");
    }

    @Test
    public void testDateAndYearCommand() throws IOException {
        SimpleDateFormat formatter = new SimpleDateFormat("d.MM.YYYY");
        Date date = Calendar.getInstance().getTime();
        StringJoiner expected = new StringJoiner(LS)
                .add(formatter.format(date))
                .add("");

        formatter.applyPattern("YYYY");
        expected.add(formatter.format(date));

        processCommands(expected.toString(), "date", "year");
    }

    @Test
    public void testUnknownCommand() throws IOException {
        processCommands(format("I don't understand.%nTry again."), "unknown");
    }


    private void processCommands(String expected, String... commands) throws IOException {
        StringJoiner input = new StringJoiner(LS);
        for (String command : commands) {
            input.add(command);
        }
        input.add("exit");
        StringJoiner output = new StringJoiner(LS)
                .add("Hello, dear friend, I'm a oracle.")
                .add("Use these commands: DATE, DAY, MONTH, YEAR, TIME, HOUR, MINUTE, SECOND, ALL, EXIT.")
                .add("");
        if (expected != null) {
            output.add(expected).add("");
        }
        output.add("Have a good day, dude!").add(LS);
        processMock(input.toString(), output.toString());
    }

    private void processMock(String input, String output) throws IOException {
        try (var in = new ByteArrayInputStream(input.getBytes());
             var out = new ByteArrayOutputStream();
             var socket = mock(Socket.class)) {
            when(socket.getInputStream()).thenReturn(in);
            when(socket.getOutputStream()).thenReturn(out);
            Server server = new Server(socket);
            server.run();
            assertEquals(output, out.toString());
        }
    }
}