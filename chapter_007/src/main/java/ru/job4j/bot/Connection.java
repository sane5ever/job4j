package ru.job4j.bot;

import java.io.*;
import java.net.Socket;
import java.util.StringJoiner;

import static java.lang.System.lineSeparator;

/**
 * Represents facade of socket connection API
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2019-07-23
 */
public class Connection implements Closeable {
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    /**
     * Sends a message line to socket output stream
     *
     * @param message message line
     */
    public void send(String message) {
        out.println(message);
    }

    /**
     * Sends empty line to show that several line message is over
     */
    public void flush() {
        out.println();
    }

    /**
     * Receives a message line from socket input stream
     *
     * @return message line
     * @throws IOException if an io error occurs
     */
    public String receive() throws IOException {
        return in.readLine();
    }

    /**
     * Receives a message divided into several lines
     *
     * @return message
     * @throws IOException if an io error occurs
     */
    public String multilineReceive() throws IOException {
        StringJoiner joiner = new StringJoiner(lineSeparator());
        for (String line = receive(); !line.isEmpty(); line = receive()) {
            joiner.add(line);
        }
        return joiner.toString();
    }

    /**
     * Closes both socket's input and output stream, then closes the socket itself
     *
     * @throws IOException if an io error occurs
     */
    @Override
    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
}