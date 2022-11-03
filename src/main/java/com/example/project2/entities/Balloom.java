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
    public boolean is_dead;
    public int cnt;
    private int count;

    public Balloom(int x, int y, Image img) {
        super(x, y, img);
        this.destinationX = this.x;
        this.destinationY = this.y;
        startX = destinationX;
        startY = destinationY;
        is_dead = false;
        cnt = 0;
        count = 0;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public void updateImage() {
        if (10 < cnt && cnt <= 16) {
            img = Picture.balloom[2][0].getFxImage();
        } else if (16 < cnt && cnt <= 22) {
            img = Picture.mob_dead[0][0].getFxImage();
        } else if (22 < cnt && cnt <= 28) {
            img = Picture.mob_dead[1][0].getFxImage();
        } else if (28 < cnt && cnt <= 34) {
            img = Picture.mob_dead[2][0].getFxImage();
        }
    }

    private boolean validSquare(int fakeX, int fakeY) {
        for (Bomb b : HelloApplication.bomb) {
            if (fakeX == b.getSmallX() && fakeY == b.getSmallY()) {
                return false;
            }
        }
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
        if (is_dead) {
            cnt++;
            updateImage();
            return;
        }
        count++;
        if (count % 20 == 0) {
            img = Picture.balloom[(int) (Math.random() * 2)][(int) (Math.random() * 3)].getFxImage();
        }
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
