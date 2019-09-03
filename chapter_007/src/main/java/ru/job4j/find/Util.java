package ru.job4j.find;

import java.nio.file.FileSystems;
import java.nio.file.PathMatcher;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Utility class
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2019-07-25
 */
public class Util {
    /**
     * Contains functions producing the defined path matcher that based on an accepted pattern string
     */
    public static final Map<Arg, Function<String, PathMatcher>> PATH_MATCHERS = new EnumMap<>(Arg.class) {
        {
            // https://stackoverflow.com/a/31685610
            put(Arg.m, pattern -> createPathMatcher("glob", pattern));
            put(Arg.f, pattern -> filename -> filename.toString().equalsIgnoreCase(pattern));
            put(Arg.r, pattern -> createPathMatcher("regex", pattern));
        }
    };

    /**
     * Not for instantiating
     */
    private Util() {
    }

    /**
     * Utility method for correct path matcher creating
     *
     * @param syntax  matcher syntax: glob or regex patterns
     * @param pattern pattern line
     * @return defined path matcher
     */
    private static PathMatcher createPathMatcher(String syntax, String pattern) {
        return FileSystems.getDefault().getPathMatcher(syntax + ':' + pattern);
    }

}
