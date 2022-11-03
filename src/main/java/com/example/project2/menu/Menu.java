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
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;

import static com.example.project2.HelloApplication.*;

public abstract class Menu {
//    private final int size = 2;
//    private final int CONTINUE = 0;
//    private final int EXIT = 1;
//    private final String[] options = new String[]{"Continue", "Exit"};
    protected ArrayList<String> options = new ArrayList<String>(); // rect - height: 20;
//    private final Rectangle[] decorationRect = new Rectangle[size];
    protected ArrayList<Rectangle> decorationRect = new ArrayList<Rectangle>();
    private final GraphicsContext gc;
    protected final Canvas canvas;
    public Scene scene;
    public static MouseEvent mouseEvent;
    protected boolean darkMode = true;

    public Menu() {
        canvas = new Canvas(Sprite.SCALED_SIZE * HEIGHT, Sprite.SCALED_SIZE * (WIDTH + MENUHEIGHT));
        gc = canvas.getGraphicsContext2D();
        Group root = new Group();
        root.getChildren().add(canvas);
        scene = new Scene(root);
        darkMode = true;
    }

    protected void addOption(String str) {
        this.options.add(str);
        this.decorationRect.add(new Rectangle());
    }

    public static boolean inRect(Rectangle rect, MouseEvent mEvent) {
        if (mEvent == null) {
            return false;
        }
        double x = mEvent.getX();
        double y = mEvent.getY();
        return (rect.getX() <= x && x <= rect.getX() + rect.getWidth() &&
                rect.getY() <= y && y <= rect.getY() + rect.getHeight());
    }

    public static boolean inRect(Rectangle rect) {
        if (mouseEvent == null) {
            return false;
        }
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();
        return (rect.getX() <= x && x <= rect.getX() + rect.getWidth() &&
                rect.getY() <= y && y <= rect.getY() + rect.getHeight());
    }

    public void render() {
        int size = options.size();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFont(new Font("Comic Sans MS", 20));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        for (int i = 0; i < size; i++) {
            int rectHeight = 30;
            int rectY = (int) canvas.getHeight() / 2 - ((size / 2 - i) * (rectHeight * 2));
            gc.fillText(options.get(i), canvas.getWidth() / 2, rectY);
            int rectWidth = 100;
            Rectangle thisRect = new Rectangle(canvas.getWidth() / 2 - rectWidth / 2.0,
                    rectY - rectHeight / 2.0,
                    rectWidth,
                    rectHeight);
            decorationRect.set(i, thisRect);
            paintRect(thisRect);
        }
    }

    public void render(GraphicsContext graphicsContext) {
        int size = options.size();
        graphicsContext.setFont(new Font("Comic Sans MS", 20));
        graphicsContext.setTextAlign(TextAlignment.CENTER);
        graphicsContext.setTextBaseline(VPos.CENTER);
        for (int i = 0; i < size; i++) {
            int rectHeight = 30;
            int rectY = (int) canvas.getHeight() / 2 - ((size / 2 - i) * (rectHeight * 2));
            Text text = new Text(options.get(i));
            graphicsContext.fillText(text.getText(), canvas.getWidth() / 2, rectY);
            int rectWidth = 100;
            Rectangle thisRect = new Rectangle(canvas.getWidth() / 2 - rectWidth / 2.0,
                    rectY - rectHeight / 2.0,
                    rectWidth,
                    rectHeight);
            decorationRect.set(i, thisRect);
            paintRect(thisRect, graphicsContext);
        }
    }

    protected void paintRect(Rectangle rect) {
        if (rect == null) {
            return;
        }
        Color fillColor = Color.BLACK;
        if (!darkMode) {
            fillColor = Color.WHITE;
        }
        if (inRect(rect)) {
            if (darkMode) {
                fillColor = Color.color(36 / 255.0, 117 / 255.0, 189 / 255.0);
            } else {
                fillColor = Color.ALICEBLUE;
            }
        }
        gc.setStroke(Paint.valueOf(String.valueOf(fillColor)));
        gc.strokeRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    protected void paintRect(Rectangle rect, GraphicsContext graphicsContext) {
        if (rect == null) {
            return;
        }
        Color fillColor = Color.BLACK;
        if (!darkMode) {
            fillColor = Color.WHITE;
        }
        if (inRect(rect)) {
            if (darkMode) {
                fillColor = Color.color(36 / 255.0, 117 / 255.0, 189 / 255.0);
            } else {
                fillColor = Color.color(55 / 255.0,174 / 255.0,208 / 255.0);
            }
        }
        graphicsContext.setStroke(Paint.valueOf(String.valueOf(fillColor)));
        graphicsContext.strokeRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    protected void paintRect(Rectangle rect, GraphicsContext graphicsContext, Color fillColor) {
        if (rect == null) {
            return;
        }
        graphicsContext.setStroke(Paint.valueOf(String.valueOf(fillColor)));
        graphicsContext.strokeRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    public abstract void handleEvent();

}
