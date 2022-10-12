package com.example.project2.entities;

import com.example.project2.HelloApplication;
import com.example.project2.graphics.Sprite;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

import java.security.Key;
import java.util.Random;
import java.util.random.RandomGenerator;

public class Oneal extends Entity {
    private int dir;
    private final int[] arrDir = new int[4];

    //speed
    private final RandomGenerator generator = new Random(System.currentTimeMillis());
    private int speed;

    //goal
    public int destinationX, destinationY;


    /**
     * constructor
     */
    public Oneal(int x, int y, Image img) {
        super(x, y, img);
        destinationX = x * Sprite.SCALED_SIZE;
        destinationY = y * Sprite.SCALED_SIZE;
    }

    private boolean validSquare(int fakeX, int fakeY) {
        if (fakeX < 0 || fakeX >= HelloApplication.HEIGHT || fakeY < 0 || fakeY >= HelloApplication.WIDTH) {
            return false;
        }
        if (HelloApplication.map.sprite[fakeX][fakeY] instanceof Grass) {
            return true;
        }
        if (HelloApplication.map.sprite[fakeX][fakeY] instanceof Brick brick) {
            return brick.isDestroyed();
        }
        return false;
    }

    @Override
    public void update(KeyEvent e) {

    }

    @Override
    public void update() {
        boolean stop = false;
        if ((x == destinationX && y == destinationY) || stop == true) {
            int dem = 0;
            for (int i = 0; i <= 3; i++) {
                if (i != (dir + 2) % 4 && validSquare(getSmallX() + DIRX[i], getSmallY() + DIRY[i])) {
                    arrDir[dem] = i;
                    dem++;
                }
            }
            if (dem != 0) {
                int id = (int) System.currentTimeMillis() % dem;
                if (id < 0) id += dem;
                dir = arrDir[id];
            } else {
                dir = (dir + 2) % 4;
            }
            this.destinationX = x + DIRX[dir] * Sprite.SCALED_SIZE;
            this.destinationY = y + DIRY[dir] * Sprite.SCALED_SIZE;
        }
        for (Entity entity : HelloApplication.entities) {
            if (entity instanceof Grass) {
                continue;
            } else {
                if (x + DIRX[dir] == entity.x && y + DIRY[dir] == entity.y) {
                    stop = true;
                }
            }
        }
        if (!stop) {
            x += DIRX[dir];
            y += DIRY[dir];
        }
    }
}
