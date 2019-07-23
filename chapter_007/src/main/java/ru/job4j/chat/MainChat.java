package ru.job4j.chat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MainChat {
    public static void main(String[] args) {
        try {
            new Chat(new FileOutputStream("D:/test.txt"))
                    .fillBotDictionary(new FileInputStream("D:/phrases.txt"))
                    .start();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
