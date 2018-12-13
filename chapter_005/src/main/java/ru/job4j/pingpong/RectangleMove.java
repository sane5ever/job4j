package ru.job4j.pingpong;

import javafx.scene.shape.Rectangle;

public class RectangleMove implements Runnable {
    private final Rectangle rect;
    private final int limitX;

    public RectangleMove(Rectangle rect, int limitX) {
        this.rect = rect;
        this.limitX = limitX;
    }

    @Override
    public void run() {
        int dx = 1;
        while (!Thread.currentThread().isInterrupted()) {
            if (this.rect.getX() <= 0 || this.rect.getX() + this.rect.getWidth() >= this.limitX) {
                dx = -dx;
            }
            this.rect.setX(this.rect.getX() + dx);

            try {
                Thread.sleep(50);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
