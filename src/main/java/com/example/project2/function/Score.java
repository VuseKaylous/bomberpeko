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
    private final int scale;
    private final int x;
    private final int y;
    public Score() {
        scale = 6;
        value = 1000 * scale;
        x = Sprite.SCALED_SIZE * HelloApplication.HEIGHT / 2;
        y = Sprite.SCALED_SIZE;
    }

    public void update(boolean ate) {
        if (ate) {
            value += 100 * scale;
        } else {
            value --;
        }
    }

    public void render(GraphicsContext gc) {
        gc.setFont(new Font("Comic Sans MS", 20));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.setFill(Color.BLACK);
        gc.fillText("Score: " + value / scale, x, y);
    }
}
