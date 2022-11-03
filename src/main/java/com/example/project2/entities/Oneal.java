package com.example.project2.entities;

import com.example.project2.HelloApplication;
import com.example.project2.graphics.Sprite;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

public class Oneal extends Entity {
    private final int[] arrDir = new int[4];
    private int startX;
    private int startY;

    //speed;
    private int change_speed = 1;

    //goal
    public int destinationX, destinationY;
    public int dir;

    //dead render
    public boolean is_dead;
    public int cnt;

    //normal render
    private int count;

    public Oneal(int x, int y, Image img) {
        super(x, y, img);
//        destinationX = x * Sprite.SCALED_SIZE;
//        destinationY = y * Sprite.SCALED_SIZE;
        destinationX = this.x;
        destinationY = this.y;
        startX = destinationX;
        startY = destinationY;
        is_dead = false;
        cnt = 0;
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

    public void updateImage() {
        if (10 < cnt && cnt <= 16) {
            img = Picture.oneal[2][0].getFxImage();
        } else if (16 < cnt && cnt <= 22) {
            img = Picture.mob_dead[0][1].getFxImage();
        } else if (22 < cnt && cnt <= 28) {
            img = Picture.mob_dead[1][1].getFxImage();
        } else if (28 < cnt && cnt <= 34) {
            img = Picture.mob_dead[2][1].getFxImage();
        }
    }

    @Override
    public void update(KeyEvent e) {

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
            //random_speed;
            if (count % 200 == 0) {
//                System.out.println("yeah");
                change_speed = (int) (Math.random() * 2) + 1;
            }
            img = Picture.oneal[(int) (Math.random() * 2)][(int) (Math.random() * 3)].getFxImage();
        }
        if (x == destinationX && y == destinationY) {
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
                dir = (this.dir + 2) % 4;
            }
            destinationX = x + DIRX[dir] * Sprite.SCALED_SIZE;
            destinationY = y + DIRY[dir] * Sprite.SCALED_SIZE;
            startX = x;
            startY = y;
        }
//        x += DIRX[dir];
//        y += DIRY[dir];
//        for (Entity entity : HelloApplication.entities) {
//            if (this.check_collision(entity)) {
//                destinationX = startX;
//                destinationY = startY;
//                x -= DIRX[dir];
//                y -= DIRY[dir];
//                dir = (dir + 2) % 4;
//                break;
//            }
//        }
        for (int i = 1; i <= change_speed; i++) {
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
            if (x == destinationX && y == destinationY) break;
        }
    }
}
