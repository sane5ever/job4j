package ru.job4j.bot;

import com.google.common.base.Joiner;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.job4j.bot.ServerTest.LS;

public class ClientTest {
    @Test
    public void testSingleLineStartAndExit() throws IOException {
        String userRequests = "exit";
        String serverResponses = Joiner.on(LS).join(
                "welcome message",
                "",
                "goodbye message",
                LS
        );
        processMock(userRequests, serverResponses);
    }

    @Test
    public void testStartMultilineResp() throws IOException {
        String userRequests = Joiner.on(LS).join(
                "first_req",
                "second_req",
                "third_req",
                "exit"
        );
        String serverResponses = Joiner.on(LS).join(
                "welcome message, pt.1",
                "welcome message, pt.2",
                "",
                "first_resp, pt. 1",
                "first_resp, pt. 2",
                "first_resp, pt. 3",
                "",
                "second_resp, pt. 1",
                "second_resp, pt. 2",
                "",
                "third single resp",
                "",
                "goodbye, pt. 1",
                "goodbye, pt. 2",
                LS
        );
        processMock(userRequests, serverResponses);
    }

    private void processMock(String userRequests, String serverResponses) throws IOException {
        InputStream originalIn = System.in;
        try (var consoleIn = new ByteArrayInputStream(userRequests.getBytes());
             var socketIn = new ByteArrayInputStream(serverResponses.getBytes());
             var socketOut = new ByteArrayOutputStream();
             var socket = mock(Socket.class)
        ) {
            System.setIn(consoleIn);
            when(socket.getInputStream()).thenReturn(socketIn);
            when(socket.getOutputStream()).thenReturn(socketOut);

            Client client = new Client(socket);
            client.run();
            assertEquals(userRequests + LS, socketOut.toString());
            System.setIn(originalIn);
        }
    }
}