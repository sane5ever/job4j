package ru.job4j.find;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import ru.job4j.archiver.AbstractArgs;

import java.nio.file.PathMatcher;
import java.util.stream.Collectors;

import static ru.job4j.find.Util.PATH_MATCHERS;

/**
 * Args class parses and stores necessary parameters for file finder work (based on Arg enum)
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2019-07-25
 */
public class Args extends AbstractArgs<Arg> {
    private Arg matcher;

    public Args(String[] args) {
        super(args, Arg.class);
    }

    /**
     * @return root directory where file search should be start
     */
    public String directory() {
        return parameters().get(Arg.d);
    }

    /**
     * @return path of file where search result should be written to
     */
    public String output() {
        return parameters().get(Arg.o);
    }

    /**
     * @return name or mask or regex of files that should be found.
     */
    public String name() {
        return parameters().get(Arg.n);
    }

    /**
     * @return filename matcher
     */
    public PathMatcher matcher() {
        validate();
        return PATH_MATCHERS.get(matcher).apply(name());
    }

    @Override
    protected String getRequiredValue(CommandLine commandLine, Arg arg) {
        String value = null;
        if (arg.isValueRequired()) {
            value = commandLine.getOptionValue(arg.name());
            if (value == null) {
                throw new IllegalArgumentException(
                        String.format("%s parameter should be set (-%s %1$s)", arg, arg.name())
                );
            }
        } else if (commandLine.hasOption(arg.name())) {
            if (matcher == null) {
                matcher = arg;
            } else {
                throw new IllegalArgumentException(String.format(
                        "-%s presented along with -%s. Accepted only one matcher type parameter", matcher.name(), arg.name()
                ));
            }
        }
        return value;
    }

    @Override
    protected Options composeOptions(Arg[] values) {
        Options options = new Options();
        for (Arg arg : values) {
            options.addOption(arg.name(), arg.isValueRequired(), arg.toString());
        }
        return options;
    }

    /**
     * Provides fail-fast program behavior to check if matcher be loaded correct
     */
    private void validate() {
        if (matcher == null) {
            String matchersLine = PATH_MATCHERS.keySet().stream()
                    .map(arg -> '-' + arg.name() + '(' + arg + ')')
                    .collect(Collectors.joining(", "));
            throw new IllegalArgumentException("One of matcher type parameters should be set: " + matchersLine);
        }
    }
}
