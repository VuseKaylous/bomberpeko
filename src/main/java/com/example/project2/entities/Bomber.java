package com.example.project2.entities;

import com.example.project2.HelloApplication;
import com.example.project2.graphics.Sound;
import com.example.project2.graphics.Sprite;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;

public class Bomber extends Entity {
    public static List<Integer> count = new ArrayList<>();
    private final int[] change_x = {-1, 0, 1, 0, 0};
    private final int[] change_y = {0, -1, 0, 1, 0};
    Sound soundtmp = new Sound();
    public String dir = "";
    int count_move = 0;
    //    Bomber left = new Bomber(x, y, Picture.player[4][2].getFxImage());
//    Bomber left1 = new Bomber(x, y, Picture.player[4][1].getFxImage());
//    Bomber left2 = new Bomber(x, y, Picture.player[4][0].getFxImage());
    Image left = Picture.player[3][2].getFxImage();
    Image left1 = Picture.player[3][1].getFxImage();
    Image left2 = Picture.player[3][0].getFxImage();
    Image right = Picture.player[1][2].getFxImage();
    Image right1 = Picture.player[1][1].getFxImage();
    Image right2 = Picture.player[1][0].getFxImage();
    Image up = Picture.player[0][2].getFxImage();
    Image up1 = Picture.player[0][1].getFxImage();
    Image up2 = Picture.player[0][0].getFxImage();
    Image down = Picture.player[2][2].getFxImage();
    Image down1 = Picture.player[2][1].getFxImage();
    Image down2 = Picture.player[2][0].getFxImage();


    public Bomber(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        //biến count để đếm thời gian đổi ảnh

        System.out.println(count_move);
        count.replaceAll(integer -> integer + 1);
        updateImage();
        bomberUpdateImage();
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

    public void bomberUpdateImage() {
        switch (dir) {
            case "left":
                if (count_move == 0) {
                    img = left;
                } else if (count_move == 10) {
                    img = left1;
                } else if (count_move == 20) {
                    img = left2;
                    count_move = 0;
                }
                break;
            case "right":
                if (count_move == 0) {
                    img = right;
                } else if (count_move == 10) {
                    img = right1;
                } else if (count_move == 20) {
                    img = right2;
                    count_move = 0;
                }
                break;
            case "up":
                if (count_move == 0) {
                    img = up;
                } else if (count_move == 10) {
                    img = up1;
                } else if (count_move == 20) {
                    img = up2;
                    count_move = 0;
                }
                break;
            case "down":
                if (count_move == 0) {
                    img = down;
                } else if (count_move == 10) {
                    img = down1;
                } else if (count_move == 20) {
                    img = down2;
                    count_move = 0;
                }
                break;
        }
    }

    public void updateImage() {
        //bug ở đây
        for (int i = 0; i < count.size(); i++) {
            int num = count.get(i);
            if (num <= 50 + i) {
                Bomb current = (Bomb) HelloApplication.bomb.get(i);
                Bomb Bomb2 = new Bomb(current.x / Sprite.SCALED_SIZE, current.y / Sprite.SCALED_SIZE, Picture.bomb[1].getFxImage());
                HelloApplication.bomb.set(i, Bomb2);
            }
            if (50 + i < num && num <= 100 + i) {
                Bomb current = (Bomb) HelloApplication.bomb.get(i);
                Bomb Bomb2 = new Bomb(current.x / Sprite.SCALED_SIZE, current.y / Sprite.SCALED_SIZE, Picture.bomb[2].getFxImage());
                HelloApplication.bomb.set(i, Bomb2);
            }
            if (100 + i < num && num <= 150 + i) {
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
            if (150 + i < num && num <= 200 + i) {
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
            if (200 + i < num && num <= 250 + i) {
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
            if (num > 250 + i) {
                HelloApplication.bomb.remove(i);
                count.remove(i);
                HelloApplication.flame.remove(i);
                i--;
                //playMusic(1); chưa biết nhét mô
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
            case LEFT:
                count_move++;
                direction = 0;
                dir = "left";
                break;
            case UP:
                count_move++;
                direction = 1;
                dir = "up";
                break;
            case RIGHT:
                count_move++;
                direction = 2;
                dir = "right";
                break;
            case DOWN:
                count_move++;
                direction = 3;
                dir = "down";
                break;
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

    public void playMusic(int i) {
        soundtmp.setFile(i);
        soundtmp.play();
        soundtmp.loop();

    }

    public void stopMusic() {
        soundtmp.stop();
    }

    public void playSE(int i) {
        soundtmp.setFile(i);
        soundtmp.play();

    }
}