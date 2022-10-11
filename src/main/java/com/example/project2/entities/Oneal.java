package com.example.project2.entities;

import com.example.project2.HelloApplication;
import com.example.project2.graphics.Sprite;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

import java.security.Key;
import java.util.Random;
import java.util.random.RandomGenerator;

public class Oneal extends Entity{
    /**
     * direction.
     */
    private RandomGenerator direction = new RandomGenerator() {
        @Override
        public long nextLong() {
            return 3;
        }
    };
//    private final int[] DIRX = new int[]{0, 1, 0, -1};
//    private final int[] DIRY = new int[]{1, 0, -1, 0};
    private int dir;
    private int[] arrDir = new int[4];
    /**
     * speed.
     */
    private RandomGenerator generator = new Random(System.currentTimeMillis());
    private final long RANGESPEED = 10;
    private final int MINSPEED = 10;
    private int speed;
    /**
     * goal.
     */
    private int destinationX, destinationY;

    /**
     * constructor
     * @param x coordinate
     * @param y coordinate
     * @param img image
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
    public void update(KeyEvent e) {

    }

    @Override
    public void update() {
        if (x == destinationX && y == destinationY) {
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
}
