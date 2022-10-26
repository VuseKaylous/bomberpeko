package com.example.project2.entities;

import com.example.project2.HelloApplication;
import com.example.project2.graphics.Sprite;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

public class Balloom extends Entity {
    private int destinationX;
    private int destinationY;
    private int startX;
    private int startY;
    private int dir;

    public Balloom(int x, int y, Image img) {
        super(x, y, img);
        this.destinationX = x * Sprite.SCALED_SIZE;
        this.destinationY = y * Sprite.SCALED_SIZE;
        startX = destinationX;
        startY = destinationY;
    }

    private boolean validSquare(int fakeX, int fakeY) {
        if (fakeX < 0 || fakeX >= HelloApplication.HEIGHT || fakeY < 0 || fakeY >= HelloApplication.WIDTH) {
            return false;
        }
        for (Entity entity : HelloApplication.entities) {
            if (fakeX == entity.getSmallX() && fakeY == entity.getSmallY()) {
                return false;
            }
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
    public void update() {
        if (x == destinationX && y == destinationY) {
            int[] arrDir = new int[4];
            int dem = 0;
            for (int i = 0; i <= 3; i++) {
                if (i != (dir + 2) % 4 && validSquare(getSmallX() + DIRX[i], getSmallY() + DIRY[i])) {
                    arrDir[dem] = i;
                    dem++;
                }
            }
            if (dem != 0) {
                int id = (int) (Math.random() * dem);
                if (id < 0) id += dem;
                dir = arrDir[id];
            } else {
                dir = (dir + 2) % 4;
            }
            destinationX = x + DIRX[dir] * Sprite.SCALED_SIZE;
            destinationY = y + DIRY[dir] * Sprite.SCALED_SIZE;
            startX = x;
            startY = y;
        }
        x += DIRX[dir];
        y += DIRY[dir];
        for (Entity entity : HelloApplication.entities) {
            if (this.check_collision(entity)) {
                destinationX = startX;
                destinationY = startY;
                x -= DIRX[dir];
                y -= DIRY[dir];
                dir = (dir + 2) % 4;
                break;
            }
        }

    }

    @Override
    public void update(KeyEvent e) {

    }
}
