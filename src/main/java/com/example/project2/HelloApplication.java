package com.example.project2;

import com.example.project2.entities.*;
import com.example.project2.graphics.Sprite;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import javax.swing.JPanel;

public class HelloApplication extends Application {
    private Canvas canvas;
    private GraphicsContext gc;

    public static final int FPS = 60;
    public static final boolean gameThread = true;

    public static final int WIDTH = 13;
    public static final int HEIGHT = 31;
    public int test = 0;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();

    private Picture pictures = new Picture();
    public static Map map = new Map();

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        canvas = new Canvas(Sprite.SCALED_SIZE * HEIGHT, Sprite.SCALED_SIZE * WIDTH);
        gc = canvas.getGraphicsContext2D();

        // Tạo root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tạo scene
        Scene scene = new Scene(root);

        // Thêm scene vào stage
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
                }
        };
        timer.start();

        createMap();
    }

    public void createMap() {
        File maptxt = new File("inp.txt");
        try {
            Scanner reader = new Scanner(maptxt);
            for (int y = 0; y < WIDTH; y++) {
                String data = reader.nextLine();
                map.updateCol(data, y);
                for (int x = 0; x < HEIGHT; x++) {
                    Entity object;
                    if (data.charAt(x) == 'p') {
                        object = new Bomber(x, y, pictures.player[1][0].getFxImage());
                        entities.add(object);
                    } else if (data.charAt(x) == '1') {
                        object = new Balloom(x, y, pictures.balloom[0][0].getFxImage());
                        entities.add(object);
                    } else if (data.charAt(x) == '2') {
                        object = new Oneal(x, y, pictures.oneal[0][0].getFxImage());
                        entities.add(object);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    public void update(){
//        double drawInterValue = 1000000000/120;
//        double nextDrawTime = System.nanoTime() + drawInterValue;
//
//        try {
//            double remainingTime = nextDrawTime - System.nanoTime();
//            remainingTime = remainingTime/100000;
//            Thread.sleep((long) remainingTime);
//            nextDrawTime += drawInterValue;
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        double drawInterval = 100000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        entities.forEach(Entity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                map.sprite[i][j].render(gc);
            }
        }
        entities.forEach(g -> g.render(gc));
    }
}