package com.example.project2.menu;

import com.example.project2.HelloApplication;
import com.example.project2.graphics.Sprite;
import javafx.animation.TranslateTransition;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class StartScreen extends Menu {
    private static final String BACKGROUND_IMAGE = "/textures/background.png";
    private SubSceneWithText help;
    private boolean helpIn;
    private Rectangle helpRect;
    public StartScreen() {
        super();
        super.addOption("Start game");
        super.addOption("Help");
        super.addOption("Exit");

        helpIn = false;
        helpRect = new Rectangle((HelloApplication.SCREENWIDTH - 600) / 2.0, -300, 600, 300);
        helpRect.setFill(Paint.valueOf(String.valueOf(Color.WHITE)));

        help = new SubSceneWithText("This is a game where player place bombs and kill the bots,\nwhich " +
                "are moving objects. There will be 2 levels in total.\nThe quicker you kill the bots " +
                "the more points you can get.\nEnjoy yourself!",
                helpRect.getWidth(), helpRect.getHeight());
//        help.prefWidth(helpRect.getWidth());
//        help.prefHeight(helpRect.getHeight());
        help.setLayoutX(helpRect.getX());
        help.setLayoutY(helpRect.getY());

        this.root.getChildren().add(help);

    }

    public void move(boolean in) {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(javafx.util.Duration.seconds(0.3));
        transition.setNode(help);
        int transitAmount = 350;
        if (in) {
            transition.setToY(transitAmount);
            helpRect.setY(transitAmount);
        } else {
            transition.setToY(-transitAmount);
            helpRect.setY(-transitAmount);
        }

        transition.play();
    }

    @Override
    public void handleEvent(Scene scene1) {
        scene1.setOnMouseMoved(mEvent -> mouseEvent = mEvent);
        scene1.setOnMouseReleased(mEvent -> {
            mouseEvent = mEvent;
            if (!helpIn) {
                for (int i = 0; i < decorationRect.size(); i++)
                    if (inRect(decorationRect.get(0))) {
                        HelloApplication.gameState = 0;
//                        HelloApplication.gameLevel = 1;
                        HelloApplication.restartGame();
                    } else if (inRect(decorationRect.get(1))) {
                        helpIn = true;
                        move(true);
                    } else if (inRect(decorationRect.get(2))) {
                        HelloApplication.gameState = 2;
                    }
            } else {
                if (!Menu.inRect(helpRect, mEvent)) {
                    helpIn = false;
                    move(false);
                }
            }
        });
    }
}
