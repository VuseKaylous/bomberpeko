package com.example.project2.entities;

import com.example.project2.HelloApplication;
import com.example.project2.entities.Entity;
import com.example.project2.graphics.Sprite;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.Scanner;

public class Bomber extends Entity {
    private final int[] change_x = {-1, 0, 1, 0};
    private final int[] change_y = {0, -1, 0, 1};
    private final int speed = 2;

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {

    }

    public boolean checkCollision(Entity entity) {
        return entity.getBoundary().intersects(new Rectangle2D(x, y, 12 * 2, 16 * 2));
    }

    @Override
    public void update(KeyEvent event) {
        int dx = 0;
        int dy = 0;
        KeyCode key = event.getCode();
        switch (key) {
            case LEFT -> {
                dx = change_x[0];
                dy = change_y[0];
            }
            case UP -> {
                dx = change_x[1];
                dy = change_y[1];
            }
            case RIGHT -> {
                dx = change_x[2];
                dy = change_y[2];
            }
            case DOWN -> {
                dx = change_x[3];
                dy = change_y[3];
            }
        }
        /*
        int x1 = x / Sprite.SCALED_SIZE + dx;
        int y1 = y / Sprite.SCALED_SIZE + dy;
        if (x1 < 0 || x1 >= HelloApplication.HEIGHT || y1 < 0 || y1 >= HelloApplication.WIDTH) {
            return;
        } else if (HelloApplication.map.sprite[x1][y1] instanceof Wall) {
            return;
        } else if (HelloApplication.map.sprite[x1][y1] instanceof Brick && HelloApplication.map.state[x1][y1]) {
            return;
        } else {
            for(int i = 0; i <  Sprite.SCALED_SIZE; i++) {
                x = x + dx;
                y = y + dy;
            }
        }

         */
        x = x + dx * speed;
        y = y + dy * speed;
        for (int i = 0; i < HelloApplication.HEIGHT; i++) {
            for (int j = 0; j < HelloApplication.WIDTH; j++) {
                if (HelloApplication.map.sprite[i][j] instanceof Grass) {
                    continue;
                }
                if (HelloApplication.map.sprite[i][j] instanceof Brick && !HelloApplication.map.state[i][j]) {
                    continue;
                }
                if (this.checkCollision(HelloApplication.map.sprite[i][j])) {
                    x -= dx * speed;
                    y -= dy * speed;
                    return;
                }
            }
        }
    }
}
