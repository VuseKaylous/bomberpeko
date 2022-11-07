package com.example.project2.menu;

import com.example.project2.HelloApplication;
import com.example.project2.entities.Entity;
import com.example.project2.graphics.Sprite;
import com.example.project2.graphics.UsefulFuncs;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.SubScene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class SubSceneWithText extends SubScene {
    protected Canvas canvas;
    protected GraphicsContext gc;
    protected Group root;
    protected double width;
    protected double height;
    public Rectangle rectangle;
    public SubSceneWithText() {
        super(new Group(), 300, 300);
    }
    public SubSceneWithText(String str, double width, double height) {
        super(new Group(), width, height);
        root = (Group) this.getRoot();
        this.width = width;
        this.height = height;
        canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);

        this.prefWidth(width);
        this.prefHeight(height);
//        subScene.setLayoutX(helpRect.getX());
//        subScene.setLayoutY(helpRect.getY());
        rectangle = new Rectangle(0, 0, 600, 300);
//        rectangle.setFill(Paint.valueOf(String.valueOf(Color.WHITE)));
        gc.setFill(Paint.valueOf(String.valueOf(Color.WHITE)));
        gc.fillRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
        UsefulFuncs.paintRect(rectangle, gc, Color.BLACK);

        UsefulFuncs.renderText(gc, str, Color.BLACK, (int) (canvas.getWidth() / 2), (int) (canvas.getHeight() / 2), 20);
    }
}
