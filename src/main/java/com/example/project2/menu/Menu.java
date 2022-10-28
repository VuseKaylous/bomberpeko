package com.example.project2.menu;

import com.example.project2.HelloApplication;
import com.example.project2.graphics.Sprite;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

//import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static com.example.project2.HelloApplication.HEIGHT;
import static com.example.project2.HelloApplication.WIDTH;
//import static com.example.project2.HelloApplication.mouseEvent;

public class Menu {
    private int size = 2;
    private final int CONTINUE = 0;
    private final int EXIT = 1;
    private String[] options = new String[]{"Continue", "Exit"};
    private Rectangle[] decorationRect = new Rectangle[size]; // rect - height: 20;
    private Button[] buttons = new Button[size];
    private int rectHeight = 30;
    private int rectWidth = 100;
    private GraphicsContext gc;
    private Canvas canvas;
    private Group root;
    public Scene scene;
    public static MouseEvent mouseEvent;
    private Color[] fillColor = new Color[size];

    public Menu() {
        canvas = new Canvas(Sprite.SCALED_SIZE * HEIGHT, Sprite.SCALED_SIZE * WIDTH);
        gc = canvas.getGraphicsContext2D();
        root = new Group();
        root.getChildren().add(canvas);
        scene = new Scene(root);
    }

    static boolean inRect(Rectangle rect) {
        if (mouseEvent == null) {
            return false;
        }
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();
//        System.out.println(x + " " + y);
        return (rect.getX() <= x && x <= rect.getX() + rect.getWidth() &&
                rect.getY() <= y && y <= rect.getY() + rect.getHeight());
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFont(new Font("Comic Sans MS", 20));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        for (int i = 0; i < size; i++) {
            int rectY = (int) canvas.getHeight() / 2 - ((size / 2 - i) * (rectHeight * 2));
            gc.fillText(options[i], canvas.getWidth() / 2, rectY );
            decorationRect[i] = new Rectangle(canvas.getWidth() / 2 - rectWidth / 2, rectY - rectHeight / 2, rectWidth, rectHeight);
        }
        for (int i = 0; i < size; i++) {
            if (decorationRect[i] == null) {
                continue;
            }
            fillColor[i] = Color.BLACK;
            if (inRect(decorationRect[i])) {
                fillColor[i] = Color.color(36 / 255.0, 117 / 255.0, 189 / 255.0);
            }
//            decorationRect[i].setFill(fillColor[i]);
            gc.setStroke(Paint.valueOf(String.valueOf(fillColor[i])));
//            System.out.println(String.valueOf(fillColor[i]));
            gc.strokeRect(decorationRect[i].getX(),
                    decorationRect[i].getY(),
                    decorationRect[i].getWidth(),
                    decorationRect[i].getHeight());
        }
    }

    public void handleEvent() {
        scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mEvent) {
                mouseEvent = mEvent;
            }
        });
        scene.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mEvent) {
                mouseEvent = mEvent;
                if (inRect(decorationRect[CONTINUE])) {
                    HelloApplication.gameState = 0;
                } else if (inRect(decorationRect[EXIT])) {
                    HelloApplication.gameState = 2;
                }
//                System.out.println(mouseEvent.getEventType());
            }
        });
    }
}
