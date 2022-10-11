package com.example.project2.entities;

import com.example.project2.HelloApplication;
import com.example.project2.graphics.Sprite;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

import java.security.Key;
import java.util.random.RandomGenerator;

public class Oneal extends Entity{
    private final long RANGESPEED = 10;
    private final int MINSPEED = 10;
    private RandomGenerator generator = new RandomGenerator() {
        @Override
        public long nextLong() {
            return RANGESPEED;
        }
    };

    private int speed;
    private int destinationX, destinationY;
    public Oneal(int x, int y, Image img) {
        super(x, y, img);
        destinationX = x;
        destinationY = y;
    }

    @Override
    public void update() {
//        if (destinationX == x && destinationY == y) {
//            speed = (int)generator.nextLong(RANGESPEED) + MINSPEED;
//
//        }
        int change_i[] = {0, 1, 0, -1, 0, 2, 0, -2};
        int change_j[] = {-1, 0, 1, 0, -2, 0, 2, 0};
        int k = (int) (Math.random() * 4);
        int x1 = (int) (super.x / Sprite.SCALED_SIZE + change_i[k]);
        int y1 = (int) (super.y / Sprite.SCALED_SIZE + change_j[k]);
        if (x1 < 0 || x1 >= HelloApplication.HEIGHT || y1 < 0 || y1 >= HelloApplication.WIDTH) {
            System.out.println("1 " + x1 + " " + y1);
            return;
        } else if (HelloApplication.map.sprite[x1][y1] instanceof Wall) {
            System.out.println("2");
            return;
        } else if (HelloApplication.map.sprite[x1][y1] instanceof Brick && HelloApplication.map.state[x1][y1] == true) {
            System.out.println("3");
            return;
        } else if (HelloApplication.map.sprite[x1][y1] instanceof Entity && HelloApplication.map.state[x1][y1] == true) {
            System.out.println("4");
            return;
        } else {
            for(int i = 0; i < Sprite.SCALED_SIZE; i++) {
                x = x + change_i[k];
                y = y + change_j[k];
            }
        }
    }

    @Override
    public void update(KeyEvent e) {

    }
}
