package ru.job4j.archiver;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.EnumMap;
import java.util.Map;

/**
 * AbstractArgs class parses and stores all the parameters based on any enum
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2019-06-27
 */
public abstract class AbstractArgs<E extends Enum<E>> {
    private final String[] args;
    private final Class<E> argEnum;
    private final Map<E, String> parameters;

    public AbstractArgs(String[] args, Class<E> argEnum) {
        this.args = args;
        this.argEnum = argEnum;
        this.parameters = new EnumMap<>(argEnum);
    }

    /**
     * provides parsing logic from adjusted args array and enum to parameters map
     */
    public void parse() {
        E[] values = argEnum.getEnumConstants();
        Options options = composeOptions(values);
        DefaultParser parser = new DefaultParser();
        try {
            CommandLine commandLine = parser.parse(options, args);
            for (E arg : values) {
                String value = getRequiredValue(commandLine, arg);
                parameters.put(arg, value);
            }
        } catch (ParseException e) {
            throw new IllegalStateException("cannot parse args: " + e.getMessage(), e);
        }
    }

    /**
     * @return enum map containing all the parsed parameters
     */
    public Map<E, String> parameters() {
        if (parameters.isEmpty()) {
            throw new IllegalStateException("parameters map is empty, firstly invoke parse method");
        }
        return parameters;
    }

    /**
     * Template method encapsulating logic of value parsing defined by specific args set
     *
     * @param commandLine list of arguments
     * @param arg         specific arg
     * @return arg value if required (or null)
     */
    protected abstract String getRequiredValue(CommandLine commandLine, E arg);

    /**
     * Template method encapsulating logic of args parser options defined by specific args set
     *
     * @param values all the arg values
     * @return composed options for parser work
     */
    protected abstract Options composeOptions(E[] values);
}
