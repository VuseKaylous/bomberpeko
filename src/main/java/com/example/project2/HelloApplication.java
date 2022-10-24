package com.example.project2;

import com.example.project2.entities.*;
import com.example.project2.graphics.Sprite;
import com.example.project2.menu.Menu;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HelloApplication extends Application {
    private Canvas canvas;
    private GraphicsContext gc;

    public static final int WIDTH = 13;
    public static final int HEIGHT = 31;
    // 416 x 992
    public int test = 0;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();
    public static Bomber bomber;

    private Picture pictures = new Picture();
    public static Map map = new Map();
    private boolean keyPressed = false;
    private KeyEvent event;
    private int gameState = 1; // 0: gameplay, 1: pause screen
    private Menu menu = new Menu();

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        canvas = new Canvas(Sprite.SCALED_SIZE * HEIGHT, Sprite.SCALED_SIZE * WIDTH);
        gc = canvas.getGraphicsContext2D();

        // gc : items(bomber, ...) -> canvas -> Group root -> scene -> stage

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
                if (gameState == 0) {
                    stage.setScene(scene);
                } else {
                    stage.setScene(menu.scene);
                }
                render();
                if (gameState == 0) {
                    normalUpdate();
                    scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent keyEvent) {
                            keyPressed = true;
                            event = keyEvent;
                        }
                    });
                    scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent keyEvent) {
                            keyPressed = false;
                        }
                    });
                } else if (gameState == 1) {

                }

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
                        bomber = new Bomber(x, y, pictures.player[1][0].getFxImage());
//                        entities.add(object);
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

    public void updateReleased() {
        keyPressed = false;
    }

    public void normalUpdate() {
        if (keyPressed) {
            bomber.update(event);
        }
        for(int i = 0; i < entities.size(); i++) {
            entities.get(i).update();
        }
    }
    public void update(KeyEvent event) throws InterruptedException {
//        Thread.sleep(300);
        for(int i = 0; i < entities.size(); i++) {
            if(entities.get(i) instanceof Bomber) {
                entities.get(i).update(event);
            }
//            else {
//                entities.get(i).update();
//            }
        }
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        if (gameState == 0) {
            for (int i = 0; i < HEIGHT; i++) {
                for (int j = 0; j < WIDTH; j++) {
                    map.sprite[i][j].render(gc);
                }
            }
            entities.forEach(g -> g.render(gc));
            bomber.render(gc);
        } else if (gameState == 1) {
            menu.render();
        }

    }
}