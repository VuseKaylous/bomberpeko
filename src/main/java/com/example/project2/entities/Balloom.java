package com.example.project2.entities;


import com.example.project2.HelloApplication;
import com.example.project2.entities.Entity;
import com.example.project2.graphics.Sprite;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Balloom extends Entity {
    int destinationX;
    int destinationY;
    int dir;
    public Balloom(int x, int y, Image img) {
        super(x, y, img);
        destinationX = this.x;
        destinationY = this.y;
    }

    /*
    @Override
    public void update() {
        int change_i[] = {0, 1, 0, -1};
        int change_j[] = {-1, 0, 1, 0};
        int k = (int) (Math.random() * 4);

        int x1 = super.x / Sprite.SCALED_SIZE + change_i[k];
        int y1 = super.y / Sprite.SCALED_SIZE + change_j[k];
        if (x1 < 0 || x1 >= HelloApplication.HEIGHT || y1 < 0 || y1 >= HelloApplication.WIDTH) {
            return;
        } else if (HelloApplication.map.sprite[x1][y1] instanceof Wall) {
            return;
        } else if (HelloApplication.map.sprite[x1][y1] instanceof Brick ) {
            Brick testBrick = (Brick) HelloApplication.map.sprite[x1][y1];
            if (testBrick.isDestroyed()) return;
        } else {
            for(int i = 0; i <  Sprite.SCALED_SIZE; i++) {
                x = x + change_i[k];
                y = y + change_j[k];
            }
        }
    }
     */
    private boolean validSquare(int fakeX, int fakeY) {
        if (fakeX < 0 || fakeX >= HelloApplication.HEIGHT || fakeY < 0 || fakeY >= HelloApplication.WIDTH) {
            return false;
        }
        if (HelloApplication.map.sprite[fakeX][fakeY] instanceof Grass) return true;
        if (HelloApplication.map.sprite[fakeX][fakeY] instanceof Brick) {
            Brick fuck = (Brick) HelloApplication.map.sprite[fakeX][fakeY];
            if (fuck.isDestroyed()) {
                return true;
            }
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
//                    System.out.println(i);
                    dem++;
                }
            }
//            System.out.println("end");
            if (dem != 0) {
                //            dir = arrDir[(int) (Math.random() * dem)];
                //            int fuck = direction.nextInt();
                int fuck = (int) System.currentTimeMillis() % dem;
                if (fuck < 0) fuck += dem;
                dir = arrDir[fuck];
                //            System.out.println(fuck);
            } else {
                dir = (dir + 2) % 4;
            }
//            System.out.println("equal: " + destinationX + ", " + destinationY + ": " + x + ", " + y);
            //        HelloApplication.map.sprite[getSmallX()][getSmallY()] = new Grass(getSmallX(), getSmallY(), Picture.grass.getFxImage());
            this.destinationX = x + DIRX[dir] * Sprite.SCALED_SIZE;
            this.destinationY = y + DIRY[dir] * Sprite.SCALED_SIZE;
        }

        this.x += DIRX[dir];
        this.y += DIRY[dir];
//        System.out.println(destinationX + ", " + destinationY + ": " + x + ", " + y);
    }

    @Override
    public void update(KeyEvent e) {

    }
}
