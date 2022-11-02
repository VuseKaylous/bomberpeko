package com.example.project2.menu;

import com.example.project2.HelloApplication;
import com.example.project2.graphics.Sprite;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import static com.example.project2.HelloApplication.HEIGHT;
import static com.example.project2.HelloApplication.WIDTH;

public class Menu {
    private final int size = 2;
    private final int CONTINUE = 0;
    private final int EXIT = 1;
    private final String[] options = new String[]{"Continue", "Exit"};
    private final Rectangle[] decorationRect = new Rectangle[size]; // rect - height: 20;
    private final GraphicsContext gc;
    private final Canvas canvas;
    public Scene scene;
    public static MouseEvent mouseEvent;
    private final Color[] fillColor = new Color[size];

    public Menu() {
        canvas = new Canvas(Sprite.SCALED_SIZE * HEIGHT, Sprite.SCALED_SIZE * WIDTH);
        gc = canvas.getGraphicsContext2D();
        Group root = new Group();
        root.getChildren().add(canvas);
        scene = new Scene(root);
    }

    static boolean inRect(Rectangle rect) {
        if (mouseEvent == null) {
            return false;
        }
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();
        return (rect.getX() <= x && x <= rect.getX() + rect.getWidth() &&
                rect.getY() <= y && y <= rect.getY() + rect.getHeight());
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFont(new Font("Comic Sans MS", 20));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        for (int i = 0; i < size; i++) {
            int rectHeight = 30;
            int rectY = (int) canvas.getHeight() / 2 - ((size / 2 - i) * (rectHeight * 2));
            gc.fillText(options[i], canvas.getWidth() / 2, rectY);
            int rectWidth = 100;
            decorationRect[i] = new Rectangle(canvas.getWidth() / 2 - rectWidth / 2.0, rectY - rectHeight / 2.0, rectWidth, rectHeight);
        }
        for (int i = 0; i < size; i++) {
            if (decorationRect[i] == null) {
                continue;
            }
            fillColor[i] = Color.BLACK;
            if (inRect(decorationRect[i])) {
                fillColor[i] = Color.color(36 / 255.0, 117 / 255.0, 189 / 255.0);
            }
            gc.setStroke(Paint.valueOf(String.valueOf(fillColor[i])));
            gc.strokeRect(decorationRect[i].getX(),
                    decorationRect[i].getY(),
                    decorationRect[i].getWidth(),
                    decorationRect[i].getHeight());
        }
    }

    public void handleEvent() {
        scene.setOnMouseMoved(mEvent -> mouseEvent = mEvent);
        scene.setOnMouseReleased(mEvent -> {
            mouseEvent = mEvent;
            if (inRect(decorationRect[CONTINUE])) {
                HelloApplication.gameState = 0;
            } else if (inRect(decorationRect[EXIT])) {
                HelloApplication.gameState = 2;
            }
        });
    }
}
