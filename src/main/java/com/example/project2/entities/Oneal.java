package com.example.project2.entities;

import com.example.project2.HelloApplication;
import com.example.project2.graphics.Sprite;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

import java.security.Key;
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
    private final int[] DIRX = new int[]{0, 1, 0, -1};
    private final int[] DIRY = new int[]{1, 0, -1, 0};
    private int dir;
    private int[] arrDir = new int[4];
    /**
     * speed.
     */
    private RandomGenerator generator = new RandomGenerator() {
        @Override
        public long nextLong() {
            return RANGESPEED;
        }
    };
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
        destinationX = x;
        destinationY = y;
    }

    private boolean validSquare(int fakeX, int fakeY) {
        if (fakeX < 0 || fakeX >= HelloApplication.HEIGHT || fakeY < 0 || fakeY >= HelloApplication.WIDTH) {
            return false;
        }
        if (HelloApplication.map.sprite[fakeX][fakeY] instanceof Grass) return true;

//        if (HelloApplication.map.sprite[fakeX][fakeY] instanceof Wall) {
//            return false;
//        }
//        if (HelloApplication.map.sprite[fakeX][fakeY] instanceof Brick) {
//            Brick fuck = (Brick) HelloApplication.map.sprite[fakeX][fakeY];
//            if (!fuck.isDestroyed()) {
//                return false;
//            }
//        }
        return false;
    }

    private boolean countDir(int fakeX, int fakeY) {
        int dem = 0;
        for (int i = 0; i < 4; i++) {
            if (validSquare(fakeX + DIRX[i], fakeY + DIRY[i])) {
                dem++;
            }
        }
        if (dem >= 3) {
            return true;
        }
        if (dem < 2) return false;
        if (validSquare(fakeX + DIRX[0], fakeY + DIRY[0]) && validSquare(fakeX + DIRX[2], fakeY + DIRY[2])) {
            return false;
        }
        if (validSquare(fakeX + DIRX[1], fakeY + DIRY[1]) && validSquare(fakeX + DIRX[3], fakeY + DIRY[3])) {
            return false;
        }
        return true;
    }

    @Override
    public void update(KeyEvent e) {

    }

    @Override
    public void update() {
        /*
        if (destinationX == getSmallX() && destinationY == getSmallY()) {
            speed = (int)generator.nextLong() + MINSPEED;
            dir = (int) (Math.random() * 4);
            for (int i = 0; i < 4; i++) {
                destinationX += DIRX[dir];
                destinationY += DIRY[dir];
                while (!countDir(destinationX, destinationY)) {
                    if (HelloApplication.map.sprite[destinationX][destinationY] instanceof Wall) {
                        break;
                    }
                    if (HelloApplication.map.sprite[destinationX][destinationY] instanceof Brick) {
                        Brick fuck = (Brick) HelloApplication.map.sprite[destinationX][destinationY];
                        if (!fuck.isDestroyed()) {
                            break;
                        }
                    }
                    destinationX += DIRX[dir];
                    destinationY += DIRY[dir];
                }
                if (!countDir(destinationX, destinationY)) {
                    destinationX -= DIRX[dir];
                    destinationY -= DIRY[dir];
                }
                if (destinationY != getSmallY() || destinationX != getSmallX()) {
                    break;
                }
                dir = (dir + 1) % 4;
            }
        }
        System.out.println(destinationX + ", " + destinationY);
        x += DIRX[dir] * Sprite.SCALED_SIZE;
        y += DIRY[dir] * Sprite.SCALED_SIZE;
        */
        int dem = 0;
        for (int i = 0; i < 3; i++) {
            if (i != dir && validSquare(getSmallX() + DIRX[i], getSmallY() + DIRY[i])) {
                arrDir[dem] = i;
                dem++;
            }
        }
        if (dem != 0) {
            dir = arrDir[(int) (Math.random() * dem)];
        }
        this.x += DIRX[dir] * Sprite.SCALED_SIZE;
        this.y += DIRY[dir] * Sprite.SCALED_SIZE;
        dir = (dir + 2) % 4;
    }
}
