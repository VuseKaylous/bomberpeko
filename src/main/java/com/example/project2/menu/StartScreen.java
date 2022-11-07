package com.example.project2.menu;

import com.example.project2.HelloApplication;
import com.example.project2.graphics.Sprite;
import com.example.project2.graphics.SpriteSheet;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;

import java.io.IOException;
import java.net.URL;

public class StartScreen extends Menu {
    private static final String BACKGROUND_IMAGE = "/textures/background.png";
    private final SubSceneWithText help;
    private final SubSceneWithText start_quote;
    private SubSceneWithText instruction_quote;
    private SubSceneWithText exit_quote;
    private boolean helpIn;
    private final Rectangle helpRect;
    private final Rectangle start;
    private Rectangle instruction;
    private Rectangle exit;

    public StartScreen() {
        super();
        URL a = SpriteSheet.class.getResource(BACKGROUND_IMAGE);
        try {
            assert a != null;
            Image raw = new Image(a.openStream());
            int h = HelloApplication.HEIGHT * Sprite.SCALED_SIZE;
            int w = (HelloApplication.WIDTH + HelloApplication.MENUHEIGHT) * Sprite.SCALED_SIZE;
            ImageView imageView = new ImageView(raw);
            imageView.setFitHeight(w);
            imageView.setFitWidth(h);
            this.root.getChildren().add(imageView);
        } catch (IOException e) {
            System.out.println("không tìm thấy ảnh");
        }
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
        help.setLayoutX(helpRect.getX());
        help.setLayoutY(helpRect.getY());
        this.root.getChildren().add(help);

        int rectHeight = 30;
        int rectY = (int) canvas.getHeight() / 2 - rectHeight / 2 - ((rectHeight * 2));
        rectY = (int) (canvas.getHeight() - (canvas.getHeight() - rectY));

        start = new Rectangle(canvas.getWidth() / 2 - 60, rectY - 15, 120, 30);
        instruction = new Rectangle(canvas.getWidth() / 2 - 60, rectY + 45, 120, 30);
        exit = new Rectangle(canvas.getWidth() / 2 - 60, rectY + 105, 120, 30);

        start_quote = new SubSceneWithText("Start game", start.getWidth(), start.getHeight());
        instruction_quote = new SubSceneWithText("Help", instruction.getWidth(), instruction.getHeight());
        exit_quote = new SubSceneWithText("Exit", exit.getWidth(), exit.getHeight());

        start_quote.setLayoutX(start.getX());
        start_quote.setLayoutY(start.getY());
        instruction_quote.setLayoutX(instruction.getX());
        instruction_quote.setLayoutY(instruction.getY());
        exit_quote.setLayoutX(exit.getX());
        exit_quote.setLayoutY(exit.getY());

        this.root.getChildren().add(start_quote);
        this.root.getChildren().add(instruction_quote);
        this.root.getChildren().add(exit_quote);
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
            transition.setToY(-1000);
            helpRect.setY(-1000);
        }
        transition.play();
    }

    public void move2(boolean in) {
        TranslateTransition transition = new TranslateTransition();
        TranslateTransition transition2 = new TranslateTransition();
        TranslateTransition transition3 = new TranslateTransition();

        transition.setDuration(javafx.util.Duration.seconds(0.3));
        transition2.setDuration(javafx.util.Duration.seconds(0.3));
        transition3.setDuration(javafx.util.Duration.seconds(0.3));

        transition.setNode(start_quote);
        transition2.setNode(instruction_quote);
        transition3.setNode(exit_quote);
        if (in) {
            transition.setToY(1000.0);
            transition2.setToY(2000.0);
            transition3.setToY(3000.0);
            start.setY(1000.0);
            instruction.setY(2000.0);
            exit.setY(3000.0);
        } else {
            transition.setToY(0.0);
            transition2.setToY(0.0);
            transition3.setToY(0.0);
            start.setY(0.0);
            instruction.setY(0.0);
            exit.setY(0.0);
        }
        transition.play();
        transition2.play();
        transition3.play();
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
                        HelloApplication.restartGame();
                    } else if (inRect(decorationRect.get(1))) {
                        helpIn = true;
                        move(true);
                        move2(true);
                    } else if (inRect(decorationRect.get(2))) {
                        HelloApplication.gameState = 2;
                    }
            } else {
                if (!Menu.inRect(helpRect, mEvent)) {
                    helpIn = false;
                    move(false);
                    move2(false);
                }
            }
        });
    }
}
