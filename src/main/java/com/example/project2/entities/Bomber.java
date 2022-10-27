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
    public Bomber(int x, int y, Image img) {
        super(x, y, img);
    }
    public bomb newBomb = new bomb(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE, Picture.bomb[0].getFxImage());

    public bomb newBomb1 = new bomb(newBomb.x / Sprite.SCALED_SIZE, newBomb.y / Sprite.SCALED_SIZE, Picture.bomb[1].getFxImage());
     public bomb newBomb2 = new bomb(newBomb1.x / Sprite.SCALED_SIZE, newBomb1.y / Sprite.SCALED_SIZE, Picture.bomb[2].getFxImage());
    public bomb Explosive1 = new bomb(newBomb2.x / Sprite.SCALED_SIZE, newBomb2.y / Sprite.SCALED_SIZE, Picture.explosion[0][1][1].getFxImage());
    public int count = 1;
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
        //bug ở đây
        for (int i = 0; i < count.size(); i++) {
            int num = count.get(i);
            if (num <= 25 + i) {
                Bomb current = (Bomb) HelloApplication.bomb.get(i);
                Bomb Bomb2 = new Bomb(current.x / Sprite.SCALED_SIZE, current.y / Sprite.SCALED_SIZE, Picture.bomb[1].getFxImage());
                HelloApplication.bomb.set(i, Bomb2);
            }
            if (25 + i < num && num <= 50 + i) {
                Bomb current = (Bomb) HelloApplication.bomb.get(i);
                Bomb Bomb2 = new Bomb(current.x / Sprite.SCALED_SIZE, current.y / Sprite.SCALED_SIZE, Picture.bomb[2].getFxImage());
                HelloApplication.bomb.set(i, Bomb2);
            }
            if (50 + i < num && num <= 60 + i) {
                Bomb current = (Bomb) HelloApplication.bomb.get(i);
                Bomb Explo1 = new Bomb(current.x / Sprite.SCALED_SIZE,
                        current.y / Sprite.SCALED_SIZE, Picture.explosion[2][0][0].getFxImage());
                for (int j = 0; j < 4; j++) {
                    if (validSquare(current.getSmallX() + change_x[j], current.getSmallY() + change_y[j])) {
                        Bomb Flame;
                        if (j % 2 == 1) {
                            Flame = new Bomb(current.getSmallX() + change_x[j],
                                    current.getSmallY() + change_y[j], Picture.explosion[0][0][j - 1].getFxImage());
                        } else {
                            Flame = new Bomb(current.getSmallX() + change_x[j],
                                    current.getSmallY() + change_y[j], Picture.explosion[1][0][j].getFxImage());
                        }
                        HelloApplication.flame.get(i).add(Flame);
                    }
                }
                HelloApplication.bomb.set(i, Explo1);
            }
            if (60 + i < num && num <= 70 + i) {
                Bomb current = (Bomb) HelloApplication.bomb.get(i);
                Bomb Explo2 = new Bomb(current.x / Sprite.SCALED_SIZE,
                        current.y / Sprite.SCALED_SIZE, Picture.explosion[2][1][0].getFxImage());
                for (int j = 0; j < 4; j++) {
                    if (validSquare(current.getSmallX() + change_x[j], current.getSmallY() + change_y[j])) {
                        Bomb Flame;
                        if (j % 2 == 1) {
                            Flame = new Bomb(current.getSmallX() + change_x[j],
                                    current.getSmallY() + change_y[j], Picture.explosion[0][1][j - 1].getFxImage());
                        } else {
                            Flame = new Bomb(current.getSmallX() + change_x[j],
                                    current.getSmallY() + change_y[j], Picture.explosion[1][1][j].getFxImage());
                        }
                        HelloApplication.flame.get(i).add(Flame);
                    }
                }
                HelloApplication.bomb.set(i, Explo2);
            }
            if (70 + i < num && num <= 80 + i) {
                Bomb current = (Bomb) HelloApplication.bomb.get(i);
                Bomb Explo3 = new Bomb(current.x / Sprite.SCALED_SIZE,
                        current.y / Sprite.SCALED_SIZE, Picture.explosion[2][2][0].getFxImage());
                for (int j = 0; j < 4; j++) {
                    if (validSquare(current.getSmallX() + change_x[j], current.getSmallY() + change_y[j])) {
                        Bomb Flame;
                        if (j % 2 == 1) {
                            Flame = new Bomb(current.getSmallX() + change_x[j],
                                    current.getSmallY() + change_y[j], Picture.explosion[0][2][j - 1].getFxImage());
                        } else {
                            Flame = new Bomb(current.getSmallX() + change_x[j],
                                    current.getSmallY() + change_y[j], Picture.explosion[1][2][j].getFxImage());
                        }
                        HelloApplication.flame.get(i).add(Flame);
                    }
                }
                HelloApplication.bomb.set(i, Explo3);
            }
            if (num > 80 + i) {
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
        KeyCode key = event.getCode();
        if (key == KeyCode.Q) {
            Bomb newBomb = new Bomb(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE, Picture.bomb[0].getFxImage());
            count.add(0);
            HelloApplication.bomb.add(newBomb);
            List<Entity> l = new ArrayList<>();
            HelloApplication.flame.add(l);
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
