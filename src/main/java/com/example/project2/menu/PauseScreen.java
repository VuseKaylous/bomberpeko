package com.example.project2.menu;

import com.example.project2.HelloApplication;
import javafx.scene.shape.Rectangle;

public class PauseScreen extends Menu {
    public PauseScreen() {
        super();
        super.addOption("Continue");
        super.addOption("Restart");
        super.addOption("Exit");
    }

    @Override
    public void handleEvent() {
        scene.setOnMouseMoved(mEvent -> mouseEvent = mEvent);
        scene.setOnMouseReleased(mEvent -> {
            mouseEvent = mEvent;
            for (int i = 0; i < decorationRect.size(); i++)
            if (inRect(decorationRect.get(0))) {
                HelloApplication.gameState = 0;
            } else if (inRect(decorationRect.get(1))) {
                HelloApplication.gameState = 0;
                HelloApplication.createMap();
            } else if (inRect(decorationRect.get(2))) {
                HelloApplication.gameState = 2;
            }
        });
    }
}
