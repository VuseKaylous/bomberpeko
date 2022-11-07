package com.example.project2.menu;

import com.example.project2.HelloApplication;
import com.example.project2.graphics.UsefulFuncs;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SettingScreen extends Menu {
    private ConfigButton[] configButtons = new ConfigButton[7];
    private int chosenButton;
    private boolean isChoosing;
    private Rectangle backButton;
    public SettingScreen() {
        super();
        int x1 = 120;
        int x2 = (int) canvas.getWidth() / 2 + x1;
        int y = 80;
        for (int i = 0; i < 3; i++) {
            configButtons[i] = new ConfigButton(i, x1, y + 50 * i);
        }
        for (int i = 3; i < 7; i++) {
            configButtons[i] = new ConfigButton(i, x2, y + 50 * (i - 3));
        }
        isChoosing = false;

        backButton = new Rectangle(70, 370, 80, 30);
    }

    @Override
    public void render(GraphicsContext graphicsContext) {
//        super.render(graphicsContext);
        for (int i = 0; i < 7; i++) {
            configButtons[i].render(graphicsContext);
        }
        UsefulFuncs.renderText(graphicsContext,
                "Back",
                Color.BLACK,
                (int) (backButton.getX() + backButton.getWidth() / 2),
                (int) (backButton.getY() + backButton.getHeight() / 2),
                20);
        super.paintRect(backButton, graphicsContext);
        if (isChoosing) {
            super.shadow(graphicsContext);
            UsefulFuncs.renderText(graphicsContext,
                    "Press the new key:",
                    Color.WHITE,
                    (int) (canvas.getWidth() / 2),
                    200, 30);
        }

//        super.shadow(graphicsContext);
    }

    @Override
    public void handleEvent(Scene scene1) {
        if (!isChoosing) {
            scene1.setOnMouseMoved(MouseEvent -> {
                mouseEvent = MouseEvent;
                for (int i = 0; i < 7; i ++) {
                    configButtons[i].mouseHover = UsefulFuncs.inRect(configButtons[i].getBorderRect(), mouseEvent);
                }
            });
            scene1.setOnMouseReleased(mouseEvent1 -> {
                mouseEvent = mouseEvent1;
                for (int i = 0; i < 7; i ++) {
                    if (UsefulFuncs.inRect(configButtons[i].getBorderRect(), mouseEvent)) {
                        chosenButton = i;
                        isChoosing = true;
                        break;
                    }
                }
                if (!isChoosing) {
                    if (UsefulFuncs.inRect(backButton, mouseEvent1)) {
                        HelloApplication.gameState = 4;
                    }
                }
            });
        } else {
            scene1.setOnKeyPressed(keyEvent -> {
                HelloApplication.keyConfig.keyCodes[chosenButton] = keyEvent.getCode();
                isChoosing = false;
                HelloApplication.keyConfig.updateTxt();
            });
        }
    }
}
