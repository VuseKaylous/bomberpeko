package com.example.project2;

import com.example.project2.entities.*;
import com.example.project2.graphics.Sound;
import com.example.project2.graphics.Sprite;
import com.example.project2.menu.Menu;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HelloApplication extends Application {
    private Canvas canvas;
    private GraphicsContext gc;

    Sound sound = new Sound();
    public static final int WIDTH = 13;
    public static final int HEIGHT = 31;
    public static List<Entity> entities = new ArrayList<>();
    public static List<Entity> bomb = new ArrayList<>();
    public static List<List<Entity>> flame = new ArrayList<>();
    private Entity bomber;

    private final Picture pictures = new Picture();
    public static Map map = new Map();
    private boolean keyPressed = false;
    private KeyEvent event;
    public static int gameState = 0; // 0: gameplay, 1: pause screen, 2: end game
    private final Menu menu = new Menu();

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
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
                if (gameState == 0) {
                    stage.setScene(scene);
                } else if (gameState == 1) {
                    stage.setScene(menu.scene);
                }
                render();
                if (gameState == 0) {
                    normalUpdate();
                    scene.setOnKeyPressed(keyEvent -> {
                        keyPressed = true;
                        event = keyEvent;
                        if (event.getCode() == KeyCode.P) { // p: pause screen
                            gameState = 1;
                        }
                    });
                    scene.setOnKeyReleased(keyEvent -> {
                        ((Bomber) bomber).setBomb(event);
                        keyPressed = false;
                    });
                } else if (gameState == 1) {
                    menu.handleEvent();
                } else if (gameState == 2) { // end game
                    this.stop();
                    stage.close();
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
                        bomber = new Bomber(x, y, Picture.player[1][0].getFxImage());
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

        playMusic(0);
    }

    public void normalUpdate() {
        if (keyPressed) {
            bomber.update(event);
        }
        for (Entity entity : entities) {
            entity.update();
        }
        bomber.update();
    }


    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        if (gameState == 0) {
            for (int i = 0; i < HEIGHT; i++) {
                for (int j = 0; j < WIDTH; j++) {
                    if (map.sprite[i][j] instanceof Brick) {
                        if (!((Brick) map.sprite[i][j]).isDestroyed()) {
                            map.sprite[i][j].render(gc);
                        } else {
                            map.tool[i][j].render(gc);
                        }
                    } else map.sprite[i][j].render(gc);
                }
            }
            entities.forEach(g -> g.render(gc));
            bomber.render(gc);
        } else if (gameState == 1) {
            menu.render();
        }

        entities.forEach(g -> g.render(gc));
        bomb.forEach(g -> g.render(gc));
        for (List<Entity> e : flame) {
            e.forEach(g -> g.render(gc));
        }
        bomber.render(gc);
    }

    public void playMusic(int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();

    }
}