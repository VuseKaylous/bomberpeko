package com.example.project2.entities;

import com.example.project2.HelloApplication;
import com.example.project2.entities.Entity;
import com.example.project2.graphics.Sprite;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.Scanner;

public class Bomber extends Entity {
    private final int[] change_x = {-1, 0, 1, 0};
    private final int[] change_y = {0, -1, 0, 1};
    public Bomber(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {

    }

    @Override
    public void update(KeyEvent event) {
        int dx = 0;
        int dy = 0;
        KeyCode key = event.getCode();
        switch (key) {
            case LEFT:
                dx = change_x[0];
                dy = change_y[0];
                break;
            case UP:
                dx = change_x[1];
                dy = change_y[1];
                break;
            case RIGHT:
                dx = change_x[2];
                dy = change_y[2];
                break;
            case DOWN:
                dx = change_x[3];
                dy = change_y[3];
                break;
        }
        int x1 = x / Sprite.SCALED_SIZE + dx;
        int y1 = y / Sprite.SCALED_SIZE + dy;
        if (x1 < 0 || x1 >= HelloApplication.HEIGHT || y1 < 0 || y1 >= HelloApplication.WIDTH) {
            return;
        } else if (HelloApplication.map.sprite[x1][y1] instanceof Wall) {
            return;
        } else if (HelloApplication.map.sprite[x1][y1] instanceof Brick && HelloApplication.map.state[x1][y1] == true) {
            return;
        } else {
            for(int i = 0; i <  Sprite.SCALED_SIZE; i++) {
                x = x + dx;
                y = y + dy;
            }
        }
    }
}
