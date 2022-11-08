package com.example.project2.menu;

import com.example.project2.HelloApplication;
import com.example.project2.graphics.UsefulFuncs;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.TextAlignment;

public class SettingScreen extends Menu {
    private final ConfigButton[] configButtons = new ConfigButton[7];
    private int chosenButton;
    private boolean isChoosing;
    private final Rectangle backButton;
    private boolean soundOn;
    private final ConfigButton soundConfig;
    public boolean previousIsStart;
    public SettingScreen() {
        super();
        int x1 = 120;
        int x2 = (int) canvas.getWidth() / 2 + x1;
        int y = 80;
        for (int i = 0; i < 3; i++) {
            configButtons[i] = new ConfigButton(i, x1, y + 50 * i);
        }
        soundConfig = new ConfigButton(7, x1, y + 50 * 3);
        for (int i = 3; i < 7; i++) {
            configButtons[i] = new ConfigButton(i, x2, y + 50 * (i - 3));
        }
        isChoosing = false;
        soundOn = true;
        previousIsStart = true;

        backButton = new Rectangle(70, 370, 80, 30);
    }

    @Override
    public void render(GraphicsContext graphicsContext) {
//        super.render(graphicsContext);
        for (int i = 0; i < 7; i++) {
            configButtons[i].render(graphicsContext);
        }
        graphicsContext.setFont(Font.font("Comic Sans MS", FontPosture.REGULAR, 20));
        graphicsContext.setTextAlign(TextAlignment.LEFT);
        graphicsContext.setTextBaseline(VPos.TOP);
        graphicsContext.setFill(Color.BLACK);
//        System.out.println(HelloApplication.keyConfig.getKeyName(buttonNumber));
        graphicsContext.fillText("Music: ", soundConfig.getBorderRect().getX() + 10, soundConfig.getBorderRect().getY() + 5);
        if (!soundConfig.mouseHover) {
            graphicsContext.setStroke(Paint.valueOf(String.valueOf(Color.BLACK)));
        } else {
            graphicsContext.setStroke(Paint.valueOf(String.valueOf(Color.color(55 / 255.0,174 / 255.0,208 / 255.0))));
        }
        graphicsContext.strokeRect(soundConfig.getBorderRect().getX() + soundConfig.getBorderRect().getWidth() / 2,
                soundConfig.getBorderRect().getY(),
                soundConfig.getBorderRect().getWidth() / 2,
                soundConfig.getBorderRect().getHeight());
        if (soundOn) {
            graphicsContext.fillText("On",
                    soundConfig.getBorderRect().getX() + 10 + soundConfig.getBorderRect().getWidth() / 2,
                    soundConfig.getBorderRect().getY() + 5);
        } else {
            graphicsContext.fillText("Off",
                    soundConfig.getBorderRect().getX() + 10 + soundConfig.getBorderRect().getWidth() / 2,
                    soundConfig.getBorderRect().getY() + 5);
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
                soundConfig.mouseHover = UsefulFuncs.inRect(soundConfig.getBorderRect(), mouseEvent);
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
                if (UsefulFuncs.inRect(soundConfig.getBorderRect(), mouseEvent1)) {
                    if (soundOn) {
                        soundOn = false;
                        HelloApplication.sound.stop();
                    } else {
                        soundOn = true;
                        HelloApplication.playMusic(0);
                    }
                }
                if (!isChoosing) {
                    if (UsefulFuncs.inRect(backButton, mouseEvent1)) {
                        if (previousIsStart) {
                            HelloApplication.gameState = HelloApplication.GameState.START;
                        } else {
                            HelloApplication.gameState = HelloApplication.GameState.PAUSE;
                        }
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
