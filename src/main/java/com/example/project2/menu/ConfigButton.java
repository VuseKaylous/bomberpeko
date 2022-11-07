package com.example.project2.menu;

import com.example.project2.HelloApplication;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.TextAlignment;

public class ConfigButton {
    private Rectangle borderRect;
    private int buttonNumber;
    public boolean mouseHover;
    public ConfigButton(int num) {
        borderRect = new Rectangle(250, 40);
        this.buttonNumber = num;
        mouseHover = false;
    }

    public ConfigButton(int num, int x, int y) {
        borderRect = new Rectangle(x, y, 250, 40);
        this.buttonNumber = num;
        mouseHover = false;
    }

    public void render(GraphicsContext gc) {
        render(gc, (int) borderRect.getX(), (int) borderRect.getY());
    }

    public void render(GraphicsContext gc, int x, int y) {
//        System.out.println("fuck");
        gc.setFont(Font.font("Comic Sans MS", FontPosture.REGULAR, 20));
        gc.setTextAlign(TextAlignment.LEFT);
        gc.setTextBaseline(VPos.TOP);
        gc.setFill(Color.BLACK);
//        System.out.println(HelloApplication.keyConfig.getKeyName(buttonNumber));
        gc.fillText(HelloApplication.keyConfig.getKeyName(buttonNumber) + ": ", x + 10, y + 5);

        if (!mouseHover) {
            gc.setStroke(Paint.valueOf(String.valueOf(Color.BLACK)));
        } else {
            gc.setStroke(Paint.valueOf(String.valueOf(Color.color(55 / 255.0,174 / 255.0,208 / 255.0))));
        }
        gc.strokeRect(x + borderRect.getWidth() / 2,
                y,
                borderRect.getWidth() / 2,
                borderRect.getHeight());

        gc.fillText((HelloApplication.keyConfig.getKeyCode(buttonNumber)).getName(), x + 10 + borderRect.getWidth() / 2, y + 5);
    }

    public Rectangle getBorderRect() {
        return borderRect;
    }
}
