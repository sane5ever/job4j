package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.List;

/**
 * Меню трекера.
 */
public class MenuTracker {

    private Input input;

    private Tracker tracker;

    private List<UserAction> actions = new ArrayList<>();

    public MenuTracker(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    /**
     * Формируем массив с ключами пунктов меню для проверки пользовательского ввода.
     *
     * @return массив с ключами
     */
    public int[] getRange() {
        int[] result = new int[this.actions.size()];
        int count = 0;
        for (UserAction action : actions) {
            result[count++] = action.key();
        }
        return result;
    }

    /**
     * Заполняем массив меню возможными операциями.
     */
    public void fillActions(StartUI ui) {
        this.actions.add(new AddItem(0, "Add new Item"));
        this.actions.add(this.new ShowItem(1, "Show all items"));
        this.actions.add(new MenuTracker.EditItem(2, "Edit item"));
        this.actions.add(new MenuTracker.DeleteItem(3, "Delete item"));
        this.actions.add(this.new FindItemById(4, "Find item by Id"));
        this.actions.add(this.new FindItemsByName(5, "Find items by name"));
        this.actions.add(new ExitProgram(6, "Exit Program", ui));
    }

    /**
     * Запускаем исполнение выбранного пользовательского действия.
     *
     * @param key ключ пункта меню
     */
    public void select(int key) {
        this.actions.get(key).execute(this.input, this.tracker);
    }

    /**
     * Выводим в консоль доступные пункты меню.
     */
    public void show() {
        System.out.println("Menu.");
        this.actions.forEach(
                action -> System.out.println(action.info())
        );
    }

    /**
     * Юзер-экшн
     * Показать всех заметки.
     */
    public class ShowItem extends BaseAction {
        public ShowItem(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------ Вывод имеющихся заявок ---------------");
            tracker.findAll().forEach(System.out::println);
            System.out.println("---------------------------------------------------");
        }
    }

    /**
     * Поиск заметки по уникальному номеру.
     */
    public class FindItemById extends BaseAction {
        public FindItemById(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("--------------- Поиск заявки по ID ----------------");
            String id = input.ask("Введите ID заявки: ");
            Item item = tracker.findById(id);
            if (item != null) {
                System.out.println(item);
            }
            System.out.println("---------------------------------------------------");
        }
    }

    /**
     * Юзер-экшн.
     * Поиск заметок по имени.
     */
    public class FindItemsByName extends BaseAction {
        public FindItemsByName(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("--------------- Поиск заявок по имени --------------");
            String name = input.ask("Введите ключевое имя: ");
            tracker.findByName(name).forEach(System.out::println);
            System.out.println("---------------------------------------------------");
        }
    }

    /**
     * Юзер-экшн.
     * Редактировать заметку (по ID).
     */
    public static class EditItem extends BaseAction {
        public EditItem(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------- Редактирование существующей заявки --------");
            String id = input.ask("Введите ID заявки : ");
            String name = input.ask("Введите имя заявки : ");
            String desc = input.ask("Введите описание заявки : ");
            Item item = new Item(name, desc);
            String output = tracker.replace(id, item) ? "Исправлена" : "Не найдена";
            System.out.println(String.format("--------%s  заявка с ID : %s ---------", output, id));
        }
    }

    /**
     * Юзер-экшн.
     * Удалить заметку (по ID).
     */
    public static class DeleteItem extends BaseAction {
        public DeleteItem(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("---------- Удаление существующей заявки -----------");
            String id = input.ask("Введите ID заявки: ");
            String output = tracker.delete(id) ? "Удалена" : "Не найдена";
            System.out.println(String.format("--------%s  заявка с ID : %s ----------", output, id));
        }
    }
}

/**
 * Юзер-экшн.
 * Добавление заметки.
 */
class AddItem extends BaseAction {
    public AddItem(int key, String name) {
        super(key, name);
    }

    @Override
    public void execute(Input input, Tracker tracker) {
        System.out.println("------------ Добавление новой заявки --------------");
        String name = input.ask("Введите имя заявки : ");
        String desc = input.ask("Введите описание заявки : ");
        Item item = new Item(name, desc);
        tracker.add(item);
        System.out.println(String.format("---------- Новая заявка с ID : %s -----------", item.getId()));
    }
}


/**
 * Юзер-экшн.
 * Выход из программы.
 */
class ExitProgram extends BaseAction {
    private final StartUI ui;

    public ExitProgram(int key, String name, StartUI ui) {
        super(key, name);
        this.ui = ui;
    }

    @Override
    public void execute(Input input, Tracker tracker) {
        if ("y".equals(input.ask("Exit?(y): "))) {
            this.ui.finish();
        }
    }
}
