package com.example.project2.menu;

import com.example.project2.HelloApplication;
import com.example.project2.graphics.Sprite;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class GameOver extends Menu {
    public GameOver() {
        super();
        super.addOption("Restart");
        super.addOption("Back");
        darkMode = false;
    }

    @Override
    public void render(GraphicsContext graphicsContext) {
        super.shadow(graphicsContext);
        Sprite.renderText(graphicsContext,
                "GAME OVER",
                Color.WHITE,
                HelloApplication.HEIGHT * Sprite.SCALED_SIZE / 2,
                (HelloApplication.WIDTH + HelloApplication.MENUHEIGHT) * Sprite.SCALED_SIZE * 3 / 10,
                30);
        Sprite.renderText(graphicsContext,
                "Your score: " + HelloApplication.score.getValue(),
                Color.WHITE,
                HelloApplication.HEIGHT * Sprite.SCALED_SIZE / 2,
                (HelloApplication.WIDTH + HelloApplication.MENUHEIGHT) * Sprite.SCALED_SIZE * 9 / 20,
                20);
        super.render(graphicsContext, 0.75);
    }

    @Override
    public void handleEvent(Scene scene1) {
        scene1.setOnMouseMoved(mEvent -> mouseEvent = mEvent);
        scene1.setOnMouseReleased(mEvent -> {
            mouseEvent = mEvent;
            for (int i = 0; i < decorationRect.size(); i++)
                if (inRect(decorationRect.get(0))) {
//                    System.out.println("is Restart");
//                    HelloApplication.isRestart = true;
                    HelloApplication.createMap();
                    HelloApplication.gameState = 0;
                } else if (inRect(decorationRect.get(1))) {
                    HelloApplication.gameState = 4;
                }
        });
    }

}
