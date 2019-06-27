package ru.job4j.archiver;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

import java.util.Collections;
import java.util.List;

import static java.lang.String.format;

/**
 * Args class parses and stores necessary parameters for archiver work (based on Arg enum)
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2019-06-27
 */
public class Args extends AbstractArgs<Arg> {
    public Args(String[] args) {
        super(args, Arg.class);
    }

    /**
     * @return root directory to be archived
     */
    public String directory() {
        return parameters().get(Arg.d);
    }

    /**
     * @return array of file extensions which need to be filtered
     */
    public List<String> exclude() {
        String exts = parameters().get(Arg.e);
        return exts == null ? Collections.emptyList() : List.of(exts.split(","));
    }

    /**
     * @return file path to resulting archive
     */
    public String output() {
        return parameters().get(Arg.o);
    }

    @Override
    protected String getRequiredValue(CommandLine commandLine, Arg arg) {
        String value = commandLine.getOptionValue(arg.name());
        if (arg.isRequired() && value == null) {
            throw new IllegalArgumentException(format("%s parameter should be set (-%s value)", arg.toString(), arg.name()));
        }
        return value;
    }

    @Override
    protected Options composeOptions(Arg[] values) {
        Options options = new Options();
        for (Arg arg : values) {
            options.addOption(arg.name(), true, arg.toString());
        }
        return options;
    }
}
