package com.example.project2.function;

import com.example.project2.HelloApplication;
import com.example.project2.graphics.Sprite;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Score {
    private int value;
    private final int scale = 10;
    public int initialScore;
    private final int x  = Sprite.SCALED_SIZE * HelloApplication.HEIGHT / 2;
    private final int y = Sprite.SCALED_SIZE;
    private final int[] levelScore = new int[]{1000, 1500};

    public Score() {
        value = 1000 * scale;
        initialScore = 0;
    }

    public Score(int level) {
        if (level == 1) initialScore = 0;
        System.out.println(level + " " + initialScore);
        value = initialScore + levelScore[level - 1] * scale;
    }

    public void update(boolean ate) {
        if (getValue() == 0) return;
        if (ate) {
            value += 100 * scale;
        } else {
            value--;
        }
        if (getValue() == 950) {
            HelloApplication.gameState = 5; // test
        }
    }

    public void newLevel() {
        initialScore = value;
        value += 1500 * scale;
        HelloApplication.gameLevel++;
    }

    public void render(GraphicsContext gc) {
        gc.setFont(new Font("Comic Sans MS", 20));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.setFill(Color.BLACK);
        gc.fillText("Score: " + value / scale, x, y);
    }

    public int getValue() {
        return value / scale;
    }
}
