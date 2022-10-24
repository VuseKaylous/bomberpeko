package com.example.project2.menu;

import com.example.project2.HelloApplication;
import com.example.project2.graphics.Sprite;
import javafx.event.EventHandler;
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

//import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static com.example.project2.HelloApplication.HEIGHT;
import static com.example.project2.HelloApplication.WIDTH;

public class Menu {
    private int size = 2;
    private String[] options = new String[]{"Continue", "Exit"};
    private Rectangle[] decorationRect = new Rectangle[size]; // rect - height: 20;
    private int rectHeight = 30;
    private int rectWidth = 100;
    private GraphicsContext gc;
    private Canvas canvas;
    private Group root;
    public Scene scene;
    private Color[] fillColor = new Color[size];

    public Menu() {
        canvas = new Canvas(Sprite.SCALED_SIZE * HEIGHT, Sprite.SCALED_SIZE * WIDTH);
        gc = canvas.getGraphicsContext2D();
        root = new Group();
        root.getChildren().add(canvas);
        scene = new Scene(root);
    }

    public void render() {
        gc.setFont(new Font("Comic Sans MS", 20));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
//        gc.fillText(options[0], canvas.getWidth() / 2, canvas.getHeight() / 2);

//        fillColor = Color.color(0, 0, 0);
        if (size % 2 == 0) {
            for (int i = 0; i < size / 2; i++) {
                int rectY = (int) canvas.getHeight() / 2 - ((size / 2 - i) * (rectHeight + rectHeight/2));
                gc.fillText(options[i], canvas.getWidth() / 2, rectY );
                decorationRect[i] = new Rectangle(canvas.getWidth() / 2 - rectWidth / 2, rectY - rectHeight / 2, rectWidth, rectHeight);
                fillColor[i] = Color.BLACK;
                int finalI = i;
                decorationRect[i].setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        fillColor[finalI] = Color.color(36, 117, 189);
                    }
                });
                decorationRect[i].setFill(fillColor[i]);
            }
            for (int i = size - 1; i >= size / 2; i--) {
                int rectY = (int) canvas.getHeight() / 2 - ((size / 2 - i) * (rectHeight + rectHeight/2));
                gc.fillText(options[i], canvas.getWidth() / 2, canvas.getHeight() / 2 - ((size / 2 - i) * (rectHeight + rectHeight/2)) );
                decorationRect[i] = new Rectangle(canvas.getWidth() / 2 - rectWidth / 2, rectY - rectHeight / 2, rectWidth, rectHeight);
//                decorationRect[i].setFill(Color.BLACK);
                fillColor[i] = Color.BLACK;
                int finalI = i;
                decorationRect[i].setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        fillColor[finalI] = Color.color(36, 117, 189);
                    }
                });
                decorationRect[i].setFill(fillColor[i]);
            }
        } else {
            for (int i = 0; i < size / 2; i++) {
                gc.fillText(options[i], canvas.getWidth() / 2, canvas.getHeight() / 2 - ((size / 2 - i) * (rectHeight + rectHeight/2) + rectHeight / 2) );
            }
            for (int i = size - 1; i > size / 2; i--) {
                gc.fillText(options[i], canvas.getWidth() / 2, canvas.getHeight() / 2 - ((size / 2 - i) * (rectHeight + rectHeight/2) - rectHeight / 2) );
            }
        }
        for (int i = 0; i < size; i++) {
            gc.setStroke(Paint.valueOf(String.valueOf(fillColor[i])));
            System.out.println(String.valueOf(fillColor[i]));
            gc.strokeRect(decorationRect[i].getX(),
                    decorationRect[i].getY(),
                    decorationRect[i].getWidth(),
                    decorationRect[i].getHeight());
        }
    }
}
