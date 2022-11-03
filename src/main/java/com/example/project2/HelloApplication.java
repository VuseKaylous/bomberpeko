package com.example.project2;

import com.example.project2.entities.*;
import com.example.project2.graphics.Sound;
import com.example.project2.graphics.Sprite;
import com.example.project2.menu.GameOver;
import com.example.project2.menu.Menu;
import com.example.project2.menu.PauseScreen;
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
import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HelloApplication extends Application {
    private Canvas canvas;
    private GraphicsContext gc;

    static Sound sound = new Sound();
    public static final int MENUHEIGHT = 2;
    public static final int WIDTH = 13;
    public static final int HEIGHT = 31;
    public static List<Entity> entities = new ArrayList<>();
    public static List<Bomb> bomb = new ArrayList<>();
    public static List<List<Bomb>> flame = new ArrayList<>();
    private static Bomber bomber;

    public Picture pictures = new Picture();
    public static Map map = new Map();
    private boolean keyPressed = false;
    private KeyEvent event;
    public static int gameState = 0; // 0: gameplay, 1: pause screen, 2: end game
    private final Menu menu = new PauseScreen();
    private GameOver gameOverScreen = new GameOver();
//    public static boolean isRestart = false;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        canvas = new Canvas(Sprite.SCALED_SIZE * HEIGHT, Sprite.SCALED_SIZE * (WIDTH + MENUHEIGHT));
        gc = canvas.getGraphicsContext2D();

        // Tạo root container
        Group root = new Group();
        root.getChildren().add(canvas);
        // Tạo scene
        Scene scene = new Scene(root);
        // Thêm scene vào stage
        stage.setScene(scene);
        stage.show();
//        isRestart = false;
        playMusic(0);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (gameState == 0 || gameState == 3) {
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
                        if (keyEvent.getCode() == KeyCode.ESCAPE) {
                            this.stop();
                            stage.close();
                        }
                    });
                    scene.setOnKeyReleased(keyEvent -> {
                        bomber.setBomb(event);
                        keyPressed = false;
                    });
                } else if (gameState == 1) {
                    menu.handleEvent();
                } else if (gameState == 2) { // end game
                    this.stop();
                    stage.close();
                } else if (gameState == 3) { // game over
                    gameOverScreen.handleEvent(scene);
                    scene.setOnKeyPressed(keyEvent -> {
                        if (keyEvent.getCode() == KeyCode.ESCAPE) {
                            this.stop();
                            stage.close();
                        }
                    });
                }

            }
        };
        timer.start();

        createMap();
    }

    public static void createMap() {
        File maptxt = new File("inp.txt");
        try {
            Scanner reader = new Scanner(maptxt);
//            System.out.println("create map");
            map = new Map();
            entities.clear();
            flame.clear();
            bomb.clear();
//            gc.clearRect();
//            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            for (int y = 0; y < WIDTH; y++) {
                String data = reader.nextLine();
                map.updateCol(data, y);
                for (int x = 0; x < HEIGHT; x++) {
                    Entity object;
                    if (data.charAt(x) == 'p') {
                        bomber = new Bomber(x, y, Picture.player[1][0].getFxImage());
                    } else if (data.charAt(x) == '1') {
                        object = new Balloom(x, y, Picture.balloom[0][0].getFxImage());
                        entities.add(object);
                    } else if (data.charAt(x) == '2') {
                        object = new Oneal(x, y, Picture.oneal[0][0].getFxImage());
                        entities.add(object);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
//        playMusic(0);
    }

    public void normalUpdate() {
        if (keyPressed) {
            bomber.update(event);
        }
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i) instanceof Balloom balloom) {
                if (balloom.is_dead && balloom.cnt > 32) {
                    entities.remove(i);
                    i--;
                }
            } else if (entities.get(i) instanceof Oneal oneal) {
                if (oneal.is_dead && oneal.cnt > 32) {
                    entities.remove(i);
                    i--;
                }
            }
        }
        for (Entity entity : entities) {
            if (entity instanceof Balloom ||
                    entity instanceof Oneal) {
                if (bomber.checkCollision(entity)) {
                    gameState = 3;
                    return;
                }
            }
        }
        entities.forEach(Entity::update);
        bomber.update();
    }

    private void gameplayRender() {
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
        bomb.forEach(g -> g.render(gc));
        for (List<Bomb> e : flame) {
            e.forEach(g -> g.render(gc));
        }
        bomber.render(gc);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        if (gameState == 0) {
            gameplayRender();
        } else if (gameState == 1) {
            menu.render();
        } else if (gameState == 3) {
            /*
            if (isRestart) {
                isRestart = false;
                createMap();
            }
             */
            gameplayRender();
            gameOverScreen.render(gc);
        }

//        entities.forEach(g -> g.render(gc));
//        bomb.forEach(g -> g.render(gc));
//        for (List<Bomb> e : flame) {
//            e.forEach(g -> g.render(gc));
//        }
//        bomber.render(gc);
    }

    public static void playMusic(int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();

    }
}