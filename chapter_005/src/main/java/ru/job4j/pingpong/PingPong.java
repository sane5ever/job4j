package ru.job4j.pingpong;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class PingPong extends Application {
    private static final String JOB4J = "Пинг-понг www.job4j.ru";

    @Override
    public void start(Stage stage) throws Exception {
        int limitX = 300;
        int limitY = 300;
        Group group = new Group();
        Rectangle rect = new Rectangle(50, 100, 10, 10);
        group.getChildren().add(rect);
        Thread animating = new Thread(new RectangleMove(rect, limitX));
        animating.start();
        stage.setScene(new Scene(group, limitX, limitY));
        stage.setResizable(false);
        stage.setTitle(PingPong.JOB4J);
        stage.show();
        stage.setOnCloseRequest(event -> animating.interrupt());
    }

    public static void main(String[] args) {
        Application.launch();
    }
}
