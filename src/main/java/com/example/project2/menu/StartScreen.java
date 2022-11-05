package com.example.project2.menu;

import com.example.project2.HelloApplication;
import javafx.scene.Scene;

public class StartScreen extends Menu {
    public StartScreen() {
        super();
        super.addOption("Start game");
        super.addOption("Exit");
    }

    @Override
    public void handleEvent(Scene scene1) {
        scene1.setOnMouseMoved(mEvent -> mouseEvent = mEvent);
        scene1.setOnMouseReleased(mEvent -> {
            mouseEvent = mEvent;
            for (int i = 0; i < decorationRect.size(); i++)
                if (inRect(decorationRect.get(0))) {
                    HelloApplication.gameState = 0;
                } else if (inRect(decorationRect.get(1))) {
                    HelloApplication.gameState = 2;
                }
        });
    }
}
