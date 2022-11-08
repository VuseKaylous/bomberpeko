package com.example.project2;

import com.example.project2.entities.*;
import com.example.project2.function.PauseButton;
import com.example.project2.function.Score;
import com.example.project2.graphics.KeyConfig;
import com.example.project2.graphics.Sound;
import com.example.project2.graphics.Sprite;
import com.example.project2.graphics.UsefulFuncs;
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
    private static Canvas canvas;
    private static GraphicsContext gc;
    private static Group root;
    private static Scene scene;

    public static Sound sound = new Sound();
    public static final int MENUHEIGHT = 2;
    public static final int WIDTH = 13;
    public static final int HEIGHT = 31;
    public static int SCREENWIDTH = HEIGHT * Sprite.SCALED_SIZE;
    public static int SCREENHEIGHT = (WIDTH + MENUHEIGHT) * Sprite.SCALED_SIZE;
    public static List<Entity> entities = new ArrayList<>();
    public static List<Bomb> bomb = new ArrayList<>();
    public static List<List<Bomb>> flame = new ArrayList<>();
    public static Bomber bomber;

    public Picture pictures = new Picture();
    public static Map map = new Map();
    private boolean keyPressed = false;
    private KeyEvent event;
    public static KeyConfig keyConfig = new KeyConfig();
    public enum GameState {
        GAMEPLAY,
        PAUSE,
        RETURN,
        GAMEOVER,
        START,
        VICTORY,
        SETTING,
        HIGHSCORE
    }
    // 0: gameplay, 1: pause screen, 2: end game immediately, 3: game over, 4: start game, 5: victory, 6: setting
    public static GameState gameState = GameState.START;
    public static int gameLevel = 1;
    public static final int MAXLEVEL = 2;
    private final PauseScreen pauseScreen = new PauseScreen();
    private final GameOver gameOverScreen = new GameOver();
    private final StartScreen startScreen = new StartScreen();
    private static final VictoryScreen victoryScreen = new VictoryScreen();
    public static final SettingScreen settingScreen = new SettingScreen();
    public static final HighScoreScreen highScoreScreen  = new HighScoreScreen();
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
        root = new Group();
        root.getChildren().add(canvas);
        // Tạo scene
        scene = new Scene(root);
        // Thêm scene vào stage
        stage.setScene(scene);
        stage.show();
//        isRestart = false;
        playMusic(0);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                if (gameState == GameState.GAMEPLAY) {
                    stage.setScene(scene);
                    normalUpdate();
                    scene.setOnKeyPressed(keyEvent -> {
                        keyPressed = true;
                        event = keyEvent;
                        if (event.getCode() == keyConfig.getPause()) { // p: pause screen
                            gameState = GameState.PAUSE;
                        }
                        if (keyEvent.getCode() == keyConfig.getExit()) {
                            this.stop();
                            stage.close();
                        }
//                        System.out.println(event.getCode()); // test
                    });
                    scene.setOnKeyReleased(keyEvent -> {
                        event = keyEvent;
                        bomber.setBomb(event);
                        keyPressed = false;
                    });
                    scene.setOnMouseReleased(mouseEvent -> {
                        if (UsefulFuncs.inRect(pauseButton.getBoundary(), mouseEvent)) {
                            gameState = GameState.PAUSE;
                        }
                    });
                } else if (gameState == GameState.PAUSE) {
                    stage.setScene(scene);
                    pauseScreen.handleEvent(scene);
                } else if (gameState == GameState.RETURN) { // end game
                    this.stop();
                    stage.close();
                } else if (gameState == GameState.GAMEOVER) { // game over
                    gameOverScreen.handleEvent(scene);
//                    scene.setOnKeyPressed(keyEvent -> {
//                        if (keyEvent.getCode() == keyConfig.getExit()) {
//                            this.stop();
//                            stage.close();
//                        }
//                    });
                } else if (gameState == GameState.START) {
                    stage.setScene(startScreen.scene);
                    startScreen.handleEvent();
                } else if (gameState == GameState.VICTORY) {
                    victoryScreen.handleEvent(scene);
                } else if (gameState == GameState.SETTING) {
                    stage.setScene(settingScreen.scene);
                    settingScreen.handleEvent();
                } else if (gameState == GameState.HIGHSCORE) {
                    stage.setScene(highScoreScreen.scene);
                    highScoreScreen.handleEvent();
                }
//                test();
            }
        };
        timer.start();

        createMap();
        createStateBar();
    }

    private void createStateBar() {
        pauseButton = new PauseButton(HEIGHT - 2, -1.5, Picture.pauseIcon.getFxImage());
        score = new Score();
    }

    private void renderStateBar() {
        pauseButton.render(gc);
        score.render(gc);
    }

    public static void createMap() {
        File maptxt = new File("inp" + gameLevel + ".txt");
        try {
            Scanner reader = new Scanner(maptxt);
            map = null;
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
//                    }else if (data.charAt(x) == '3') {
//                        object = new Doll(x, y, Picture.doll[0][0].getFxImage());
//                        entities.add(object);
//                    }else if (data.charAt(x) == '4') {
//                        object = new Minvo(x, y, Picture.minvo[0][0].getFxImage());
                    } else if (data.charAt(x) == '3') {
                        object = new Ghost(x, y, Picture.ghost[0][0].getFxImage());
                        entities.add(object);
                    } else if (data.charAt(x) == '4') {
                        object = new Kondoria(x, y, Picture.kondoria[0][0].getFxImage());
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
        gameLevel = 1;
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        root = new Group(canvas);
        scene = new Scene(root);
        createMap();
        score.resetScore(gameLevel);
        victoryScreen.resetGame();
    }

    public void normalUpdate() {
        if (gameState != GameState.GAMEOVER && keyPressed) {
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
            } else if (entities.get(i) instanceof Ghost ghost) {
                if (ghost.is_dead && ghost.cnt > 32) {
                    entities.remove(i);
                    i--;
                }
            } else if (entities.get(i) instanceof Kondoria kondoria) {
                if (kondoria.is_dead && kondoria.cnt > 32) {
                    entities.remove(i);
                    i--;
                }
            }
        }
        for (Entity entity : entities) {
            if (entity.check_collision(bomber)) {
                gameState = GameState.GAMEOVER;
            }
        }
        if (entities.size() == 0) {
            if (bomber.check_collision(map.getPortal())) {
                gameState = GameState.VICTORY;
            }
        }
        if (gameState != GameState.GAMEOVER) entities.forEach(Entity::update);
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
                        } else if (map.tool[i][j] instanceof RandomItem && bomber.getRandom_item) {
                            map.tool[i][j] = new Grass(i, j, Picture.grass.getFxImage());
                        } else if (map.tool[i][j] instanceof BoostItem && bomber.getBoost_item) {
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
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        if (gameState == GameState.GAMEPLAY) {
            gameplayRender();
        } else if (gameState == GameState.PAUSE) {
            gameplayRender();
            pauseScreen.render(gc);
        } else if (gameState == GameState.GAMEOVER) {
            if (bomber.cnt <= 30) {
                normalUpdate();
                gameplayRender();
            } else {
                gameplayRender();
                gameOverScreen.render(gc);
            }
        } else if (gameState == GameState.START) {
            startScreen.render();
        } else if (gameState == GameState.VICTORY) {
            gameplayRender();
            victoryScreen.render(gc, root);
        } else if (gameState == GameState.SETTING) {
            settingScreen.render();
        } else if (gameState == GameState.HIGHSCORE) {
            highScoreScreen.render();
        }
    }

    public static void playMusic(int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();
    }
}
