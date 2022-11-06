package com.example.project2.menu;

import com.example.project2.HelloApplication;
import com.example.project2.graphics.Sprite;
import com.example.project2.graphics.UsefulFuncs;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class VictoryScreen extends Menu {
    private boolean keyIsPressed;
    public VictoryScreen() {
        super();
        keyIsPressed = false;
//        super.addOption("Press any key to continue");
    }

    public void resetGame() { keyIsPressed = false; }

    @Override
    public void render(GraphicsContext graphicsContext) {
        super.shadow(graphicsContext);
        UsefulFuncs.renderText(graphicsContext,
                "Congratulation!",
                Color.WHITE,
                HelloApplication.HEIGHT * Sprite.SCALED_SIZE / 2,
                (HelloApplication.WIDTH + HelloApplication.MENUHEIGHT) * Sprite.SCALED_SIZE * 3 / 10,
                30);
        UsefulFuncs.renderText(graphicsContext,
                "Your score: " + HelloApplication.score.getValue(),
                Color.WHITE,
                HelloApplication.HEIGHT * Sprite.SCALED_SIZE / 2,
                (HelloApplication.WIDTH + HelloApplication.MENUHEIGHT) * Sprite.SCALED_SIZE * 9 / 20,
                20);
        if (HelloApplication.gameLevel == 1) {
            UsefulFuncs.renderText(graphicsContext,
                    "Press any key to continue",
                    Color.WHITE,
                    HelloApplication.SCREENWIDTH / 2,
                    HelloApplication.SCREENHEIGHT * 7 / 10,
                    17,
                    true);
        }
    }

    @Override
    public void handleEvent(Scene scene1) {
        scene1.setOnKeyPressed(keyEvent -> {
            if (!keyIsPressed) {
                keyIsPressed = true;
                HelloApplication.gameState = 0;
                HelloApplication.score.newLevel();
                HelloApplication.createMap();
            }
        });
        scene1.setOnMousePressed(mouseEvent1 -> {
            if (!keyIsPressed) {
                keyIsPressed = true;
                HelloApplication.gameState = 0;
                HelloApplication.score.newLevel();
                HelloApplication.createMap();
            }
        });
    }
}
