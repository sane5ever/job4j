package ru.job4j.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Provides console chat program logic
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2019-07-23
 */
public class Chat {
    private static final Logger LOG = LoggerFactory.getLogger(Chat.class);
    private static final String
            BYE = "Пока!",
            PAUSE = "стоп",
            CONTINUE = "продолжить",
            BREAK = "закончить",
            CLIENT_PREFIX = "Client: ",
            BOT_PREFIX = "Bot: ";

    private final InputStream in;
    private final OutputStream out;
    private final List<String> dictionary;
    private final Random random;

    private boolean exit = false;
    private boolean needReplay = true;

    /**
     * @param in  input stream to read requests for the bot
     * @param out output stream to log the chat
     */
    public Chat(InputStream in, OutputStream out) {
        this.in = in;
        this.out = out;
        this.dictionary = new ArrayList<>();
        this.random = new Random();
    }

    /**
     * Inits a chat bot instance setting {@link System#in} as input stream to read requests for the bot
     *
     * @param out output stream to log the chat
     */
    public Chat(OutputStream out) {
        this(System.in, out);
    }

    /**
     * Tries to fill this bot's answer phrases dictionary from the submitted input stream
     *
     * @param in input stream to read phrases
     * @return built chat bot instance in result
     */
    public Chat fillBotDictionary(InputStream in) {
        clearBotDictionary();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            reader.lines().forEach(dictionary::add);
        } catch (Exception e) {
            LOG.error("Error due to fill bot dictionary", e);
        }
        return this;
    }

    /**
     * Clears chat bot's answer phrases dictionary
     */
    public void clearBotDictionary() {
        dictionary.clear();
    }

    /**
     * Provides main loop of the chat bot work
     */
    public void start() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in));
             PrintWriter logWriter = new PrintWriter(out)) {
            while (!exit) {
                exchangeWords(reader, logWriter);
            }
        } catch (IOException e) {
            LOG.error("error due to read console/input", e);
        }
    }

    /**
     * Provides one-step phrase exchange between user and the bot
     *
     * @param reader reader that user phrase takes from
     * @param writer log writer
     * @throws IOException if an io error occurs
     */
    private void exchangeWords(BufferedReader reader, PrintWriter writer) throws IOException {
        String question = reader.readLine();
        writer.println(CLIENT_PREFIX + question);
        checkQuestion(question);
        if (needReplay) {
            String phrase = getAnswer(question);
            System.out.println(phrase);
            writer.println(BOT_PREFIX + phrase);
        }
    }

    /**
     * Creates bot answer based on user's question
     *
     * @param question user's question
     * @return bot answer
     */
    private String getAnswer(String question) {
        return exit ? BYE : dictionary.isEmpty() ? (question + '?') : getRandomPhrase();
    }

    /**
     * @return random phrase from the bot dictionary
     */
    private String getRandomPhrase() {
        int index = random.nextInt(dictionary.size());
        return dictionary.get(index);
    }

    /**
     * Checks user's phrase if the bot need to pause, or to stop, or to continue the talk
     *
     * @param question user's phrase
     */
    private void checkQuestion(String question) {
        if (PAUSE.equalsIgnoreCase(question)) {
            needReplay = false;
        } else if (CONTINUE.equalsIgnoreCase(question)) {
            needReplay = true;
        } else if (BREAK.equalsIgnoreCase(question)) {
            exit = true;
        }
    }
}
