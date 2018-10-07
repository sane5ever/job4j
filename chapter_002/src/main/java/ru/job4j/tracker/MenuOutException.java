package ru.job4j.tracker;

/**
 * Исключение, выкидываемое при некорректном выборе пункта меню.
 */
public class MenuOutException extends RuntimeException {
    public MenuOutException(String message) {
        super(message);
    }
}