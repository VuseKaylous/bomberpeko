package com.example.project2.menu;

import com.example.project2.HelloApplication;
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
        super.addOption("Exit");
        darkMode = false;
    }

    @Override
    public void render(GraphicsContext graphicsContext) {
//        paintRect(new Rectangle(0, 0, canvas.getWidth(), canvas.getHeight()), graphicsContext, Color.color(0, 0, 0, 1));
        graphicsContext.setFill(Paint.valueOf(String.valueOf(Color.color(0, 0, 0, 0.5))));
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        graphicsContext.setFill(Paint.valueOf(String.valueOf(Color.WHITE)));
        super.render(graphicsContext);
    }

    @Override
    public void handleEvent() {
        scene.setOnMouseMoved(mEvent -> mouseEvent = mEvent);
        scene.setOnMouseReleased(mEvent -> {
            mouseEvent = mEvent;
            for (int i = 0; i < decorationRect.size(); i++)
                if (inRect(decorationRect.get(0))) {
//                    System.out.println("is Restart");
//                    HelloApplication.isRestart = true;
                    HelloApplication.gameState = 0;
                } else if (inRect(decorationRect.get(1))) {
                    HelloApplication.gameState = 2;
                }
        });
    }

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
                    HelloApplication.gameState = 2;
                }
        });
    }

}
