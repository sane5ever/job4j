package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.Date;
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

    public void fillActions() {
        this.actions.add(new AddItem(0, "Add new Item"));
        this.actions.add(this.new ShowItem(1, "Show all items"));
        this.actions.add(new MenuTracker.EditItem(2, "Edit item"));
        this.actions.add(new MenuTracker.DeleteItem(3, "Delete item"));
        this.actions.add(this.new FindItemById(4, "Find item by Id"));
        this.actions.add(this.new FindItemsByName(5, "Find items by name"));
        this.actions.add(new ExitProgram(6, "Exit Program"));
    }

    public void select(int key) {
        this.actions.get(key).execute(this.input, this.tracker);
    }

    public void show() {
        System.out.println("Menu.");
        for (UserAction action : this.actions) {
            if (action != null) {
                System.out.println(action.info());
            }
        }
    }

    public class ShowItem implements UserAction {

        private final int key;
        private final String name;

        public ShowItem(int key, String name) {
            this.key = key;
            this.name = name;
        }

        @Override
        public int key() {
            return this.key;
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------ Вывод имеющихся заявок ---------------");
            MenuTracker.this.outputItems(tracker.findAll());
            System.out.println("---------------------------------------------------");
        }

        @Override
        public String info() {
            return String.format("%s. %s.", this.key, this.name);
        }
    }

    public class FindItemById implements UserAction {

        private final int key;
        private final String name;

        public FindItemById(int key, String name) {
            this.key = key;
            this.name = name;
        }

        @Override
        public int key() {
            return this.key;
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("--------------- Поиск заявки по ID ----------------");
            String id = input.ask("Введите ID заявки: ");
            Item item = tracker.findById(id);
            if (item != null) {
                MenuTracker.this.outputItems(item);
            }
            System.out.println("---------------------------------------------------");
        }

        @Override
        public String info() {
            return String.format("%s. %s.", this.key, this.name);
        }
    }

    public class FindItemsByName implements UserAction {

        private final int key;
        private final String name;

        public FindItemsByName(int key, String name) {
            this.key = key;
            this.name = name;
        }

        @Override
        public int key() {
            return this.key;
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("--------------- Поиск заявок по имени --------------");
            String name = input.ask("Введите ключевое имя: ");
            MenuTracker.this.outputItems(tracker.findByName(name));
            System.out.println("---------------------------------------------------");
        }

        @Override
        public String info() {
            return String.format("%s. %s.", this.key, this.name);
        }
    }

    public static class EditItem implements UserAction {
        private final int key;
        private final String name;

        public EditItem(int key, String name) {
            this.key = key;
            this.name = name;
        }
        @Override
        public int key() {
            return this.key;
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

        @Override
        public String info() {
            return String.format("%s. %s.", this.key, this.name);
        }
    }

    public static class DeleteItem implements UserAction {
        private final int key;
        private final String name;

        public DeleteItem(int key, String name) {
            this.key = key;
            this.name = name;
        }
        @Override
        public int key() {
            return this.key;
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("---------- Удаление существующей заявки -----------");
            String id = input.ask("Введите ID заявки: ");
            String output = tracker.delete(id) ? "Удалена" : "Не найдена";
            System.out.println(String.format("--------%s  заявка с ID : %s ----------", output, id));
        }

        @Override
        public String info() {
            return String.format("%s. %s.", this.key, this.name);
        }
    }

    private void outputItems(Item... items) {
        for (Item item : items) {
            System.out.println(String.format("%s|%s|%s|%td-%<tm-%<ty", item.getId(), item.getName(), item.getDescription(), new Date(item.getCreate())));
        }
    }
}

class AddItem implements UserAction {
    private final int key;
    private final String name;

    public AddItem(int key, String name) {
        this.key = key;
        this.name = name;
    }
    @Override
    public int key() {
        return this.key;
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

    @Override
    public String info() {
        return String.format("%s. %s.", this.key, this.name);
    }
}



class ExitProgram implements UserAction {
    private final int key;
    private final String name;

    public ExitProgram(int key, String name) {
        this.key = key;
        this.name = name;
    }
    @Override
    public int key() {
        return this.key;
    }

    @Override
    public void execute(Input input, Tracker tracker) {
        if ("y".equals(input.ask("Exit?(y): "))) {
            tracker.setReady(false);
        }
    }

    @Override
    public String info() {
        return String.format("%s. %s.", this.key, this.name);
    }
}
