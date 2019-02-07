package ru.job4j.io;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

/**
 * Проверка байтового потока.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2019-02-07
 */
public class InputChecker {
    private static final Logger LOG = LogManager.getLogger(InputChecker.class);

    /**
     * Метод проверяет, что в байтовом потоке записано чётное число
     *
     * @param in байтовый поток
     * @return <tt>true</tt>, если число и оно чётное
     */
    public boolean isEvenNumber(InputStream in) {
        boolean isDigitSequence = true;
        int lastByte = -1;
        try {
            for (int b = in.read(); b != -1; b = in.read()) {
                if (b < 48 || b > 57) {
                    isDigitSequence = false;
                    break;
                }
                lastByte = b;
            }
        } catch (IOException ioe) {
            LOG.error(ioe.getMessage(), ioe);
        }
        return isDigitSequence && lastByte % 2 == 0;
    }
}