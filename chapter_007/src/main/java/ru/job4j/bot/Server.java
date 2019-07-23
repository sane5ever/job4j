package ru.job4j.bot;

import one.util.streamex.StreamEx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

import static java.util.function.Function.identity;

/**
 * Server part of oracle program
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2019-07-23
 */
public class Server {
    private static final Logger LOG = LoggerFactory.getLogger(Server.class);
    private static final Map<String, Command> COMMANDS =
            StreamEx.of(Command.values()).toMap(Command::value, identity());


    private final Socket socket;

    public Server(Socket socket) {
        this.socket = socket;
    }

    /**
     * entry point of the server program work
     *
     * @throws IOException if an io error occurs
     */
    public void run() throws IOException {
        try (final Connection connection = new Connection(socket)) {
            handshake(connection);
            mainLoop(connection);
        }

    }

    /**
     * Sends welcome message, shows supported commands
     *
     * @param connection socket connection
     */
    private void handshake(Connection connection) {
        connection.send("Hello, dear friend, I'm a oracle.");
        Command.ALL.doResponse(connection);
        connection.flush();
    }

    /**
     * Represents main loop of server program  that repeatedly waiting commands from client, tries to serve it,
     * then sends response to client
     *
     * @param connection socket connection
     * @throws IOException if an io error occurs
     */
    private void mainLoop(Connection connection) throws IOException {
        Command command;
        do {
            String call = connection.receive();
            command = COMMANDS.get(call.toLowerCase());
            if (command != null) {
                command.doResponse(connection);
                LOG.info("has been processing command {}", command);
            } else {
                connection.send("I don't understand.");
                connection.send("Try again.");
            }
            connection.flush();
        } while (command != Command.EXIT);
    }


    public static void main(String[] args) {
        try (final Socket socket = new ServerSocket(2222).accept()) {
            new Server(socket).run();
        } catch (Exception e) {
            LOG.error("i/o error due to chat", e);
        }
    }
}