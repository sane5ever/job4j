package ru.job4j.bot;

import com.google.common.base.Joiner;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Represents commands those can be completed with oracle server
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2019-07-23
 */
public enum Command {
    DATE("date", "d.MM.YYYY"),
    DAY("day", "d"),
    MONTH("month", "MMMM"),
    YEAR("year", "YYYY"),
    TIME("time", "H:mm:ss"),
    HOUR("hour", "H"),
    MINUTE("minute", "m"),
    SECOND("second", "s"),

    ALL("all", null) {
        private String allCommands;

        @Override
        public void doResponse(Connection connection) {
            connection.send("Use these commands: " + allCommands() + '.');
        }

        private String allCommands() {
            if (allCommands == null) {
                allCommands = Joiner.on(", ").join(values());
            }
            return allCommands;
        }
    },

    EXIT("exit", null) {
        @Override
        public void doResponse(Connection connection) {
            connection.send("Have a good day, dude!");
        }
    };

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat();

    private String value;
    private String pattern;

    Command(String value, String pattern) {
        this.value = value;
        this.pattern = pattern;
    }

    public void doResponse(Connection connection) {
        FORMATTER.applyPattern(pattern);
        connection.send(FORMATTER.format(Calendar.getInstance().getTime()));
    }

    public String value() {
        return value;
    }
}