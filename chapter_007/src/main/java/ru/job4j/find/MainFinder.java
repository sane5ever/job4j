package ru.job4j.find;

public class MainFinder {
    public static void main(String[] args) {
        try {
            new MainFinder().start(args);
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    public void start(String[] args) {
        Args arguments = new Args(args);
        arguments.parse();
        FileManager manager = new FileManager(arguments.directory(), arguments.output(), arguments.matcher());
        manager.search();
        System.out.println("Search results successfully written to file '" + arguments.output() + '\'');
    }
}
