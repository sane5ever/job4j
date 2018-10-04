package ru.job4j.tracker;

import java.util.Date;

/**
 * Точка входа в программу. Обеспечивает полноценную работу всего приложения (трекера).
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-10-03
 */
public class StartUI {
    /** константа меню для добавления новой заявки */
    private static final String ADD = "0";

    /** константа меню для вывода на экран всех заявок */
    private static final String SHOW = "1";

    /** константа меню для редактирования заявки */
    private static final String EDIT = "2";

    /** константа меню для удаления заявки */
    private static final String DELETE = "3";

    /** константа меню для поиска заявки по id */
    private static final String FIND_BY_ID = "4";

    /** константа меню для поиска заявок по имени */
    private static final String FIND_BY_NAME = "5";

    /** константа меню выхода из цикла */
    private static final String EXIT = "6";

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
        boolean exit = false;
        while (!exit) {
            this.showMenu();
            String answer = this.input.ask("Введите пункт меню: ");
            if (ADD.equals(answer)) {
                this.createItem();
            } else if (SHOW.equals(answer)) {
                this.showAll();
            } else if (EDIT.equals(answer)) {
                this.editItem();
            } else if (DELETE.equals(answer)) {
                this.deleteItem();
            } else if (FIND_BY_ID.equals(answer)) {
                this.findById();
            } else if (FIND_BY_NAME.equals(answer)) {
                this.findByName();
            } else if (EXIT.equals(answer)) {
                exit = true;
            }
        }
    }

    /**
     * Вывод пунктов меню в консоль.
     */
    private void showMenu() {
        System.out.println(String.format("Меню.%s0. Add new Item%<s1. Show all items%<s2. Edit item%<s3. Delete item%<s4. Find item by Id%<s5. Find items by name%<s6. Exit Program",
                System.lineSeparator()));
    }

    /**
     * Добавление новой заявки в хранилище.
     */
    private void createItem() {
        System.out.println("------------ Добавление новой заявки --------------");
        String name = this.input.ask("Введите имя заявки : ");
        String desc = this.input.ask("Введите описание заявки : ");
        Item item = new Item(name, desc);
        this.tracker.add(item);
        System.out.println(String.format("---------- Новая заявка с ID : %s -----------", item.getId()));
    }

    /**
     * Выводит в консоль все имеющиеся заявки.
     */
    private void showAll() {
        System.out.println("------------ Вывод имеющихся заявок ---------------");
        this.outputItems(this.tracker.findAll());
        System.out.println("---------------------------------------------------");

    }

    /**
     * Вспомогательный метод для вывода заявок в консоль.
     */
    private void outputItems(Item... items) {
        for (Item item : items) {
            System.out.println(String.format("%s|%s|%s|%td-%<tm-%<ty", item.getId(), item.getName(), item.getDescription(), new Date(item.getCreate())));
        }
    }

    /**
     * Редактирование заявки.
     */
    private void editItem() {
        System.out.println("------- Редактирование существующей заявки --------");
        String id = this.input.ask("Введите ID заявки : ");
        String name = this.input.ask("Введите имя заявки : ");
        String desc = this.input.ask("Введите описание заявки : ");
        Item item = new Item(name, desc);
        String output = this.tracker.replace(id, item) ? "Исправлена" : "Не найдена";
        System.out.println(String.format("--------%s  заявка с ID : %s ---------", output, id));
    }

    /**
     * Удаление заявки.
     */
    private void deleteItem() {
        System.out.println("---------- Удаление существующей заявки -----------");
        String id = this.input.ask("Введите ID заявки: ");
        String output = this.tracker.delete(id) ? "Удалена" : "Не найдена";
        System.out.println(String.format("--------%s  заявка с ID : %s ----------", output, id));
    }

    /**
     * Вывод на экран заявки по ID.
     */
    private void findById() {
        System.out.println("--------------- Поиск заявки по ID ----------------");
        String id = this.input.ask("Введите ID заявки: ");
        Item item = this.tracker.findById(id);
        if (item != null) {
            outputItems(item);
        }
        System.out.println("---------------------------------------------------");
    }

    /**
     * Вывод на экран заявок с указанным именем.
     */
    private void findByName() {
        System.out.println("--------------- Поиск заявок по имени --------------");
        String name = this.input.ask("Введите ключевое имя: ");
        outputItems(this.tracker.findByName(name));
        System.out.println("---------------------------------------------------");
    }


    /**
     * Запуск программы.
     * @param args args
     */
    public static void main(String[] args) {
        new StartUI(new ConsoleInput(), new Tracker()).init();

    }
}