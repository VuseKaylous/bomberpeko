package com.example.project2.menu;

import com.example.project2.graphics.Sprite;
import com.example.project2.graphics.UsefulFuncs;
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

import java.util.ArrayList;

import static com.example.project2.HelloApplication.*;

public abstract class Menu {
    protected ArrayList<String> options = new ArrayList<>(); // rect - height: 20;
    protected ArrayList<Rectangle> decorationRect = new ArrayList<>();
    private final GraphicsContext gc;
    protected final Canvas canvas;
    protected Group root;
    public Scene scene;
    public static MouseEvent mouseEvent;
    protected boolean darkMode = true;

    public Menu() {
        canvas = new Canvas(Sprite.SCALED_SIZE * HEIGHT, Sprite.SCALED_SIZE * (WIDTH + MENUHEIGHT));
        gc = canvas.getGraphicsContext2D();
        root = new Group();
        root.getChildren().add(canvas);
        scene = new Scene(root);
        darkMode = true;
    }

    protected void addOption(String str) {
        this.options.add(str);
        this.decorationRect.add(new Rectangle());
    }

    public static boolean inRect(Rectangle rect) {
        return UsefulFuncs.inRect(rect, mouseEvent);
    }

    public void render() {
        gc.setFill(Color.WHITE);
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        render(gc);
    }

    public void shadow(GraphicsContext graphicsContext) {
        graphicsContext.setFill(Paint.valueOf(String.valueOf(Color.color(0, 0, 0, 0.5))));
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        graphicsContext.setFill(Paint.valueOf(String.valueOf(Color.WHITE)));
    }

    public void render(GraphicsContext graphicsContext) {
        render(graphicsContext, 1);
    }

    public void render(GraphicsContext graphicsContext, double scaleY) {
        int size = options.size();
        graphicsContext.setFont(new Font("Comic Sans MS", 20));
        graphicsContext.setTextAlign(TextAlignment.CENTER);
        graphicsContext.setTextBaseline(VPos.CENTER);

        int rectHeight = 30;
        int rectY = (int) canvas.getHeight() / 2 - rectHeight / 2 - ((size / 2) * (rectHeight * 2));
        if (size % 2 == 0) {
            rectY = (int) canvas.getHeight() / 2 + rectHeight / 2 - (size / 2) * (rectHeight * 2);
        }
        rectY = (int) (canvas.getHeight() - (canvas.getHeight() - rectY) * scaleY);

        for (int i = 0; i < size; i++) {
            if (darkMode) {
                graphicsContext.setFill(Color.BLACK);
            } else {
                graphicsContext.setFill(Color.WHITE);
            }
            graphicsContext.fillText(options.get(i), canvas.getWidth() / 2, rectY + i * rectHeight * 2);
            int rectWidth = 120;
            Rectangle thisRect = new Rectangle(canvas.getWidth() / 2 - rectWidth / 2.0,
                    rectY + i * rectHeight * 2 - rectHeight / 2.0, rectWidth, rectHeight);
            decorationRect.set(i, thisRect);
            paintRect(thisRect, graphicsContext);
        }
    }

    protected void paintRect(Rectangle rect) {
        paintRect(rect, gc);
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
                fillColor = Color.color(55 / 255.0, 174 / 255.0, 208 / 255.0);
            }
        }
        graphicsContext.setStroke(Paint.valueOf(String.valueOf(fillColor)));
        graphicsContext.strokeRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    public void handleEvent() {
        handleEvent(scene);
    }

    public abstract void handleEvent(Scene scene1);

}
