package com.example.project2.menu;

import com.example.project2.HelloApplication;
import com.example.project2.graphics.Sprite;
import com.example.project2.graphics.UsefulFuncs;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public class VictoryScreen extends Menu {
    private boolean keyIsPressed;
    private TextField username;
    private AnchorPane anchorPane;
    private int getHighScore;
    public VictoryScreen() {
        super();
        keyIsPressed = false;
        getHighScore = 0;
        username = new TextField();
        anchorPane = new AnchorPane();
        AnchorPane.setLeftAnchor(username, 480.0);
        AnchorPane.setTopAnchor(username, (double) ((HelloApplication.WIDTH + HelloApplication.MENUHEIGHT) * Sprite.SCALED_SIZE * 12 / 20) - 10);
        anchorPane.getChildren().add(username);
//        AnchorPane.setRightAnchor(username, 300.0);
//        AnchorPane.setRightAnchor(username, 300.0);

//        TextField.positionInArea(canvas,
//                        HelloApplication.SCREENWIDTH / 2.0,
//                        HelloApplication.SCREENHEIGHT * 11 / 20.0,
//                        100,
//                        30,
//                        0,
//                        Insets.EMPTY,
//                        HPos.CENTER,
//                        VPos.CENTER,
//                        true);
    }

    public void resetGame() { keyIsPressed = false; }

    public void render(GraphicsContext graphicsContext, Group group) {
        super.shadow(graphicsContext);
        UsefulFuncs.renderText(graphicsContext,
                "Congratulation!",
                Color.WHITE,
                HelloApplication.HEIGHT * Sprite.SCALED_SIZE / 2,
                (HelloApplication.WIDTH + HelloApplication.MENUHEIGHT) * Sprite.SCALED_SIZE * 3 / 10,
                30);
        if (HelloApplication.gameLevel < HelloApplication.MAXLEVEL) {
            UsefulFuncs.renderText(graphicsContext,
                    "Your score: " + HelloApplication.score.getValue(),
                    Color.WHITE,
                    HelloApplication.HEIGHT * Sprite.SCALED_SIZE / 2,
                    (HelloApplication.WIDTH + HelloApplication.MENUHEIGHT) * Sprite.SCALED_SIZE * 9 / 20,
                    20);
            UsefulFuncs.renderText(graphicsContext,
                    "Press any key to continue",
                    Color.WHITE,
                    HelloApplication.SCREENWIDTH / 2,
                    HelloApplication.SCREENHEIGHT * 7 / 10,
                    17,
                    true);
            return;
        }
        if (!HelloApplication.highScoreScreen.checkHighscore(HelloApplication.score.getValue())) {
            UsefulFuncs.renderText(graphicsContext,
                    "Your score: " + HelloApplication.score.getValue(),
                    Color.WHITE,
                    HelloApplication.HEIGHT * Sprite.SCALED_SIZE / 2,
                    (HelloApplication.WIDTH + HelloApplication.MENUHEIGHT) * Sprite.SCALED_SIZE * 9 / 20,
                    20);
        } else {
            UsefulFuncs.renderText(graphicsContext,
                    "New high score: " + HelloApplication.score.getValue(),
                    Color.WHITE,
                    HelloApplication.HEIGHT * Sprite.SCALED_SIZE / 2,
                    (HelloApplication.WIDTH + HelloApplication.MENUHEIGHT) * Sprite.SCALED_SIZE * 9 / 20,
                    20);
            if (getHighScore == 0) {
                getHighScore = 1;
                keyIsPressed = false;
                group.getChildren().add(anchorPane);
            }
            if (getHighScore == 1) {
                UsefulFuncs.renderText(graphicsContext,
                        "Please enter your name: ",
                        Color.WHITE,
                        HelloApplication.HEIGHT * Sprite.SCALED_SIZE / 2 - 140,
                        (HelloApplication.WIDTH + HelloApplication.MENUHEIGHT) * Sprite.SCALED_SIZE * 12 / 20,
                        20);
//                username = new TextField();
//                username.setMaxWidth(150);
//                username.setMinWidth(100);
//                username.setPrefHeight(30);
//                TextField.layoutInArea(canvas,
//                        HelloApplication.SCREENWIDTH / 2.0,
//                        HelloApplication.SCREENHEIGHT * 11 / 20.0,
//                        100,
//                        30,
//                        0,
//                        Insets.EMPTY,
//                        true,
//                        true,
//                        HPos.CENTER,
//                        VPos.CENTER,
//                        true);
//                group.getChildren().add(username);
            } else if (getHighScore == 2) {
                UsefulFuncs.renderText(graphicsContext,
                        "High score saved!",
                        Color.WHITE,
                        HelloApplication.HEIGHT * Sprite.SCALED_SIZE / 2,
                        (HelloApplication.WIDTH + HelloApplication.MENUHEIGHT) * Sprite.SCALED_SIZE * 11 / 20,
                        20);
                UsefulFuncs.renderText(graphicsContext,
                        "Press any key to continue",
                        Color.WHITE,
                        HelloApplication.SCREENWIDTH / 2,
                        HelloApplication.SCREENHEIGHT * 7 / 10,
                        17,
                        true);
            }
        }
    }

    @Override
    public void handleEvent(Scene scene1) {
        scene1.setOnKeyPressed(keyEvent -> {
            if (getHighScore != 1) {
//                System.out.println(keyIsPressed);
                if (!keyIsPressed) {
                    keyIsPressed = true;
                    if (HelloApplication.gameLevel < HelloApplication.MAXLEVEL) {
                        HelloApplication.gameState = HelloApplication.GameState.GAMEPLAY;
                        HelloApplication.score.newLevel();
                        HelloApplication.createMap();
                    } else {
//                        System.out.println("change to start");
                        HelloApplication.gameState = HelloApplication.GameState.START;
                        HelloApplication.restartGame();
//                        HelloApplication.createMap();
                    }
                    getHighScore = 0;
                }
            }
        });
        scene1.setOnMousePressed(mouseEvent1 -> {
            if (getHighScore != 1) {
                if (!keyIsPressed) {
                    keyIsPressed = true;
                    if (HelloApplication.gameLevel < HelloApplication.MAXLEVEL) {
                        HelloApplication.gameState = HelloApplication.GameState.GAMEPLAY;
                        HelloApplication.score.newLevel();
                        HelloApplication.createMap();
                    } else {
                        HelloApplication.gameState = HelloApplication.GameState.START;
//                        HelloApplication.restartGame();
//                        HelloApplication.createMap();
                    }
                    getHighScore = 0;
                }
            }
        });
        username.setOnAction(ActionEvent -> {
            String str = username.getText();
            if (!str.isEmpty()) {
                HelloApplication.highScoreScreen.updateHighscore(username.getText(), HelloApplication.score.getValue());
                ((Group) scene1.getRoot()).getChildren().remove(((Group) scene1.getRoot()).getChildren().size() - 1);
                getHighScore = 2;
            }
        });
    }
}
