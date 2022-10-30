package com.example.project2.entities;

import com.example.project2.HelloApplication;
import com.example.project2.entities.*;
import com.example.project2.graphics.Sound;
import com.example.project2.graphics.Sprite;
import com.example.project2.graphics.SpriteSheet;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;

public class Bomber extends Entity {
    Sound sound1 = new Sound();
    public List<Integer> count = new ArrayList<>();
    private final int[] change_x = {-1, 0, 1, 0, 0};
    private final int[] change_y = {0, -1, 0, 1, 0};

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        //biến count để đếm thời gian đổi ảnh
        count.replaceAll(integer -> integer + 1);
        updateImage();
    }

    private boolean validSquare(int fakeX, int fakeY) {
        if (fakeX < 0 || fakeX >= HelloApplication.HEIGHT || fakeY < 0 || fakeY >= HelloApplication.WIDTH) {
            return false;
        }
        if (HelloApplication.map.sprite[fakeX][fakeY] instanceof Grass) {
            return true;
        }
        return HelloApplication.map.sprite[fakeX][fakeY] instanceof Brick;
    }

    public void updateImage() {
        for (int i = 0; i < count.size(); i++) {
            int num = count.get(i);
            if (num <= 20 + i) {
                Bomb current = (Bomb) HelloApplication.bomb.get(i);
                Bomb Bomb2 = new Bomb(current.getSmallX(), current.getSmallY(), Picture.bomb[1].getFxImage());
                HelloApplication.bomb.set(i, Bomb2);
            }
            if (20 + i < num && num <= 40 + i) {
                Bomb current = (Bomb) HelloApplication.bomb.get(i);
                Bomb Bomb2 = new Bomb(current.getSmallX(), current.getSmallY(), Picture.bomb[0].getFxImage());
                HelloApplication.bomb.set(i, Bomb2);
            }
            if (40 + i < num && num <= 60 + i) {
                Bomb current = (Bomb) HelloApplication.bomb.get(i);
                Bomb Bomb2 = new Bomb(current.getSmallX(), current.getSmallY(), Picture.bomb[1].getFxImage());
                HelloApplication.bomb.set(i, Bomb2);
            }
            if (60 + i < num && num <= 80 + i) {
                Bomb current = (Bomb) HelloApplication.bomb.get(i);
                Bomb Bomb2 = new Bomb(current.getSmallX(), current.getSmallY(), Picture.bomb[2].getFxImage());
                HelloApplication.bomb.set(i, Bomb2);
            }
            if (80 + i < num && num <= 82 + i) {
                Bomb current = (Bomb) HelloApplication.bomb.get(i);
                Bomb Explo1 = new Bomb(current.getSmallX(), current.getSmallY(), Picture.explosion[2][0][0].getFxImage());
                HelloApplication.bomb.set(i, Explo1);
                for (int j = 0; j < 4; j++) {
                    int newX = current.getSmallX() + change_x[j];
                    int newY = current.getSmallY() + change_y[j];
                    if (validSquare(newX, newY)) {
                        if (HelloApplication.map.sprite[newX][newY] instanceof Brick) {
                            if (!((Brick) HelloApplication.map.sprite[newX][newY]).isDestroyed()) {
                                HelloApplication.map.sprite[newX][newY] = new Brick(newX, newY, Picture.brick[1].getFxImage());
                            }
                        }
                        Bomb Flame;
                        if (j % 2 == 1) {
                            Flame = new Bomb(newX, newY, Picture.explosion[0][0][j - 1].getFxImage());
                        } else {
                            Flame = new Bomb(newX, newY, Picture.explosion[1][0][j].getFxImage());
                        }
                        HelloApplication.flame.get(i).add(Flame);
                    }
                }
            }
            if (82 + i < num && num <= 84 + i) {
                Bomb current = (Bomb) HelloApplication.bomb.get(i);
                Bomb Explo2 = new Bomb(current.getSmallX(), current.getSmallY(), Picture.explosion[2][1][0].getFxImage());
                HelloApplication.bomb.set(i, Explo2);
                for (int j = 0; j < 4; j++) {
                    int newX = current.getSmallX() + change_x[j];
                    int newY = current.getSmallY() + change_y[j];
                    if (validSquare(newX, newY)) {
                        if (HelloApplication.map.sprite[newX][newY] instanceof Brick) {
                            if (!((Brick) HelloApplication.map.sprite[newX][newY]).isDestroyed()) {
                                HelloApplication.map.sprite[newX][newY] = new Brick(newX, newY, Picture.brick[2].getFxImage());
                            }
                        }
                        Bomb Flame;
                        if (j % 2 == 1) {
                            Flame = new Bomb(newX, newY, Picture.explosion[0][1][j - 1].getFxImage());
                        } else {
                            Flame = new Bomb(newX, newY, Picture.explosion[1][1][j].getFxImage());
                        }
                        HelloApplication.flame.get(i).add(Flame);
                    }
                }
            }
            if (84 + i < num && num <= 86 + i) {
                Bomb current = (Bomb) HelloApplication.bomb.get(i);
                Bomb Explo3 = new Bomb(current.getSmallX(), current.getSmallY(), Picture.explosion[2][2][0].getFxImage());
                HelloApplication.bomb.set(i, Explo3);
                for (int j = 0; j < 4; j++) {
                    int newX = current.getSmallX() + change_x[j];
                    int newY = current.getSmallY() + change_y[j];
                    if (validSquare(newX, newY)) {
                        if (HelloApplication.map.sprite[newX][newY] instanceof Brick) {
                            if (!((Brick) HelloApplication.map.sprite[newX][newY]).isDestroyed()) {
                                HelloApplication.map.sprite[newX][newY] = new Brick(newX, newY, Picture.brick[3].getFxImage());
                            }
                        }
                        Bomb Flame;
                        if (j % 2 == 1) {
                            Flame = new Bomb(newX, newY, Picture.explosion[0][2][j - 1].getFxImage());
                        } else {
                            Flame = new Bomb(newX, newY, Picture.explosion[1][2][j].getFxImage());
                        }
                        HelloApplication.flame.get(i).add(Flame);
                    }
                }
            }
            if (num > 86 + i) {
                for (int j = 0; j < 4; j++) {
                    int newX = HelloApplication.bomb.get(i).getSmallX() + change_x[j];
                    int newY = HelloApplication.bomb.get(i).getSmallY() + change_y[j];
                    if (validSquare(newX, newY)) {
                        if (HelloApplication.map.sprite[newX][newY] instanceof Brick) {
                            if (!((Brick) HelloApplication.map.sprite[newX][newY]).isDestroyed()) {
                                ((Brick) HelloApplication.map.sprite[newX][newY]).destroyed = true;
                            }
                        }
                    }
                }
                HelloApplication.bomb.remove(i);
                count.remove(i);
                HelloApplication.flame.remove(i);
                i--;
            }
        }
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(x, y, 12 * 2, 16 * 2);
    }

    public boolean checkCollision(Entity entity) {
        return entity.getBoundary().intersects(this.getBoundary());
    }

    public boolean checkSnapAble() {
        for (int i = 0; i < HelloApplication.HEIGHT; i++) {
            for (int j = 0; j < HelloApplication.WIDTH; j++) {
                if (HelloApplication.map.sprite[i][j] instanceof Grass) {
                    continue;
                }
                if (HelloApplication.map.sprite[i][j] instanceof Brick && ((Brick) HelloApplication.map.sprite[i][j]).isDestroyed()) {
                    continue;
                }
                if (this.checkCollision(HelloApplication.map.sprite[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }

    public void setBomb(KeyEvent event) {
        boolean check = false;
        KeyCode key = event.getCode();
        if (key == KeyCode.Q) {
            for (Entity bomb : HelloApplication.bomb) {
                if (bomb.getSmallX() == (x + 10) / Sprite.SCALED_SIZE && bomb.getSmallY() == (y + 10) / Sprite.SCALED_SIZE) {
                    check = true;
                    break;
                }
            }
            if (!check) {
                Bomb newBomb = new Bomb((x + 10) / Sprite.SCALED_SIZE,
                        (y + 10) / Sprite.SCALED_SIZE, Picture.bomb[0].getFxImage());
                count.add(0);
                HelloApplication.bomb.add(newBomb);
                List<Entity> l = new ArrayList<>();
                HelloApplication.flame.add(l);
            }
        }
    }

    @Override
    public void update(KeyEvent event) {
        int direction = 4; // ko co event thi dung yen
        KeyCode key = event.getCode();
        switch (key) {
            case LEFT -> direction = 0;
            case UP -> direction = 1;
            case RIGHT -> direction = 2;
            case DOWN -> direction = 3;
        }
        int speed = 2;
        x = x + change_x[direction] * speed;
        y = y + change_y[direction] * speed;

        int snapSize = 5;
        for (int snap = 0; snap <= snapSize; snap++) {
            if (direction % 2 == 0 && direction < 4) { // di theo chieu y
                y += snap;
            } else {
                x += snap;
            }
            boolean snapAble = checkSnapAble();
            if (snapAble) {
                return;
            }
            if (direction % 2 == 0 && direction < 4) { // di theo chieu y
                y -= snap;
            } else {
                x -= snap;
            }
            if (snap != 0) {
                if (direction % 2 == 0 && direction < 4) { // di theo chieu y
                    y -= snap;
                } else {
                    x -= snap;
                }
                snapAble = checkSnapAble();
                if (snapAble) {
                    return;
                }
                if (direction % 2 == 0 && direction < 4) { // di theo chieu y
                    y += snap;
                } else {
                    x += snap;
                }
            }
        }
        x = x - change_x[direction] * speed;
        y = y - change_y[direction] * speed;
    }

    public void playMusicc(int i) {
        sound1.setFile(i);
        sound1.play();
        sound1.loop();

    }

    public void stopMusicc() {
        sound1.stop();
    }

    public void playSEE(int i) {
        sound1.setFile(i);
        sound1.play();

    }


}
