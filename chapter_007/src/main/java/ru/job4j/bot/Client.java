package ru.job4j.bot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;


/**
 * Client part of oracle program
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2019-07-23
 */
public class Client {
    private final Socket socket;
    private final InputStream in;

    public Client(Socket socket) {
        this(socket, System.in);
    }

    public Client(Socket socket, InputStream in) {
        this.socket = socket;
        this.in = in;
    }

    /**
     * Represents main loop of client program that repeatedly waiting for keyboard commands, sends them to server,
     * then receives server responses, and print them to console
     *
     * @throws IOException if an io error occurs
     */
    public void run() throws IOException {
        try (final Connection connection = new Connection(socket);
             final BufferedReader console = new BufferedReader(new InputStreamReader(in))) {
            handshake(connection);
            String phrase;
            do {
                phrase = console.readLine();
                connection.send(phrase);
                String response = connection.multilineReceive();
                System.out.println(response);
            } while (!Command.EXIT.value().equalsIgnoreCase(phrase));
        }
    }

    /**
     * Receives welcome message from server
     *
     * @param connection socket connection
     * @throws IOException if an io error occurs
     */
    private void handshake(Connection connection) throws IOException {
        System.out.println(connection.multilineReceive());
    }

    public static void main(String[] args) {
        try (final Socket socket = new Socket(InetAddress.getByName("localhost"), 2222)) {
            new Client(socket).run();
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}