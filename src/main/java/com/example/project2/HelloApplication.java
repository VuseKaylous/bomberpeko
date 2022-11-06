package com.example.project2;

import com.example.project2.entities.*;
import com.example.project2.function.PauseButton;
import com.example.project2.function.Score;
import com.example.project2.graphics.Sound;
import com.example.project2.graphics.Sprite;
import com.example.project2.menu.*;
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
import java.lang.invoke.VolatileCallSite;
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
    public static int SCREENWIDTH = HEIGHT * Sprite.SCALED_SIZE;
    public static int SCREENHEIGHT = (WIDTH + MENUHEIGHT) * Sprite.SCALED_SIZE;
    public static List<Entity> entities = new ArrayList<>();
    public static List<Bomb> bomb = new ArrayList<>();
    public static List<List<Bomb>> flame = new ArrayList<>();
    private static Bomber bomber;

    public Picture pictures = new Picture();
    public static Map map = new Map();
    private boolean keyPressed = false;
    private KeyEvent event;
    public static int gameState = 4;
    // 0: gameplay, 1: pause screen, 2: end game immediately, 3: game over, 4: start game, 5: victory
    public static int gameLevel = 1;
    private final Menu pauseScreen = new PauseScreen();
    private final Menu gameOverScreen = new GameOver();
    private final Menu startScreen = new StartScreen();
    private final Menu victoryScreen = new VictoryScreen();
    private PauseButton pauseButton;
    public static Score score;

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
                render();
                if (gameState == 0) {
                    stage.setScene(scene);
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
//                        System.out.println(event.getCode()); // test
                    });
                    scene.setOnKeyReleased(keyEvent -> {
                        bomber.setBomb(event);
                        keyPressed = false;
                    });
                    scene.setOnMouseReleased(mouseEvent -> {
                        if (Menu.inRect(pauseButton.getBoundary(), mouseEvent)) {
                            gameState = 1;
                        }
                    });
                } else if (gameState == 1) {
                    pauseScreen.handleEvent(scene);
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
                } else if (gameState == 4) {
                    stage.setScene(startScreen.scene);
                    startScreen.handleEvent();
                } else if (gameState == 5) {
                    victoryScreen.handleEvent(scene);
                }
//                test();
            }
        };
        timer.start();

        createMap();
        createStateBar();
    }

//    private void test() {
//        Entity testSubject = new FlameItem(1,
//                (float) -1.5, Picture.powerup[1].getFxImage());
//        testSubject.render(gc);
//    }

    private void createStateBar() {
        pauseButton = new PauseButton(HEIGHT - 2, -1.5, Picture.pauseIcon.getFxImage());
        score = new Score();
    }

    private void renderStateBar() {
        pauseButton.render(gc);
    }

    public static void createMap() {
        File maptxt = new File("inp" + gameLevel + ".txt");
        try {
            Scanner reader = new Scanner(maptxt);
            map = new Map();
            entities.clear();
            flame.clear();
            bomb.clear();
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
            System.out.println("File not found " + gameLevel);
        }
//        playMusic(0);
    }

    public static void restartGame() {
        createMap();
        gameLevel = 1;
        score.resetScore(gameLevel);
    }

    public void normalUpdate() {
        if (keyPressed) {
            if (event != null) bomber.update(event);
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
                if (entity.check_collision(bomber)) {
                    gameState = 3;
                    return;
                }
            }
        }
        if (entities.size() == 0) {
            if (bomber.check_collision(map.getPortal())) {
                gameState = 5;
            }
        }
        entities.forEach(Entity::update);
        bomber.update();
        score.update(false);
    }

    private void gameplayRender() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (map.sprite[i][j] instanceof Brick) {
                    if (!((Brick) map.sprite[i][j]).isDestroyed()) {
                        map.sprite[i][j].render(gc);
                    } else {
                        if (map.tool[i][j] instanceof BombItem && bomber.getBomb_item) {
                            map.tool[i][j] = new Grass(i, j, Picture.grass.getFxImage());
                        } else if (map.tool[i][j] instanceof FlameItem && bomber.getFlame_item) {
                            map.tool[i][j] = new Grass(i, j, Picture.grass.getFxImage());
                        } else if (map.tool[i][j] instanceof SpeedItem && bomber.getSpeed_item) {
                            map.tool[i][j] = new Grass(i, j, Picture.grass.getFxImage());
                        }
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
        renderStateBar();
        bomber.render(gc);
        score.render(gc);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        if (gameState == 0) {
            gameplayRender();
        } else if (gameState == 1) {
            gameplayRender();
            pauseScreen.render(gc);
        } else if (gameState == 3) {
            gameplayRender();
            gameOverScreen.render(gc);
        } else if (gameState == 4) {
            startScreen.render();
        } else  if (gameState == 5) {
            gameplayRender();
            victoryScreen.render(gc);
        }
    }

    public static void playMusic(int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();
    }
}
