package com.example.project2.menu;

import com.example.project2.HelloApplication;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

public class PauseScreen extends Menu {
    public PauseScreen() {
        super();
        super.addOption("Continue");
        super.addOption("Restart");
        super.addOption("Setting");
        super.addOption("Back");
        this.darkMode = false;
    }

    @Override
    public void render(GraphicsContext graphicsContext) {
        super.shadow(graphicsContext);
        super.render(graphicsContext);
    }

    @Override
    public void handleEvent(Scene scene1) {
        scene1.setOnMouseMoved(mEvent -> mouseEvent = mEvent);
        scene1.setOnMouseReleased(mEvent -> {
            mouseEvent = mEvent;
            for (int i = 0; i < decorationRect.size(); i++) {
                if (inRect(decorationRect.get(0))) {
                    HelloApplication.gameState = HelloApplication.GameState.GAMEPLAY;
                } else if (inRect(decorationRect.get(1))) {
                    HelloApplication.gameState = HelloApplication.GameState.GAMEPLAY;
                    HelloApplication.restartGame();
                } else if (inRect(decorationRect.get(2))) {
                    HelloApplication.settingScreen.previousIsStart = false;
                    HelloApplication.gameState = HelloApplication.GameState.SETTING;
                } else if (inRect(decorationRect.get(3))) {
                    HelloApplication.gameState = HelloApplication.GameState.START;
                }
            }
        });
    }
}
