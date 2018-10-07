package ru.job4j.tracker;

/**
 * Точка входа в программу. Обеспечивает полноценную работу всего приложения (трекера).
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-10-03
 */
public class StartUI {
    /** получение данных от пользователя */
    private final Input input;

    /** хранилище заявок */
    private final Tracker tracker;

    /**
     * Конструктор, инициализирующий финальные поля.
     * @param input ввод данных от пользователя
     * @param tracker хранилище заявок
     */
    public StartUI(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    /**
     * Обеспечивает сновной цикл программы.
     */
    public void init() {
        MenuTracker menu = new MenuTracker(this.input, this.tracker);
        menu.fillActions();
        do {
            menu.show();
            menu.select(Integer.valueOf(this.input.ask("select:")));
        } while (this.tracker.isReady());
    }
    /**
     * Запуск программы.
     * @param args args
     */
    public static void main(String[] args) {
        new StartUI(new ConsoleInput(), new Tracker()).init();

    }
}