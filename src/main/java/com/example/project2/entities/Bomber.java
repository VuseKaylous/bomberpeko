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
        count_move = 0;
        count.clear();
    }

    @Override
    public void update() {
        //biến count để đếm thời gian đổi ảnh

//        System.out.println(count_move);
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
        for (int i = 0; i < count.size(); i++) {
            int num = count.get(i);
            if (num <= 20 + i) {
                HelloApplication.bomb.get(i).setImg(Picture.bomb[1].getFxImage());
            }
            if (20 + i < num && num <= 40 + i) {
                HelloApplication.bomb.get(i).setImg(Picture.bomb[0].getFxImage());
            }
            if (40 + i < num && num <= 60 + i) {
                HelloApplication.bomb.get(i).setImg(Picture.bomb[1].getFxImage());
            }
            if (60 + i < num && num <= 80 + i) {
                HelloApplication.bomb.get(i).setImg(Picture.bomb[2].getFxImage());
            }
            if (80 + i < num) {
                if (num <= 82 + i) {
                    HelloApplication.bomb.get(i).setImg(Picture.explosion[2][0][0].getFxImage());
                } else if (num <= 84 + i) {
                    HelloApplication.bomb.get(i).setImg(Picture.explosion[2][1][0].getFxImage());
                } else if (num <= 86 + i) {
                    HelloApplication.bomb.get(i).setImg(Picture.explosion[2][2][0].getFxImage());
                }
                Bomb current = HelloApplication.bomb.get(i);
                for (int j = 0; j < 4; j++) {
                    int newX = current.getSmallX() + change_x[j];
                    int newY = current.getSmallY() + change_y[j];
                    if (validSquare(newX, newY)) {
                        if (HelloApplication.map.sprite[newX][newY] instanceof Brick) {
                            if (!((Brick) HelloApplication.map.sprite[newX][newY]).isDestroyed()) {
                                if (num <= 86){
                                    ((Brick) HelloApplication.map.sprite[newX][newY]).setImg(Picture.brick[(num - 79) / 2].getFxImage());
                                } else {
                                    ((Brick) HelloApplication.map.sprite[newX][newY]).destroyed = true;
                                }
                            } else {
                                if (num > 86) continue;
                                Bomb Flame;
                                if (j % 2 == 1) {
                                    Flame = new Bomb(newX, newY, Picture.explosion[0][1][j - 1].getFxImage());
                                } else {
                                    Flame = new Bomb(newX, newY, Picture.explosion[1][1][j].getFxImage());
                                }
                                HelloApplication.flame.get(i).add(Flame);
                            }
                        } else {
                            if (num > 86) continue;
                            Bomb Flame;
                            if (j % 2 == 1) {
                                Flame = new Bomb(newX, newY, Picture.explosion[0][1][j - 1].getFxImage());
                            } else {
                                Flame = new Bomb(newX, newY, Picture.explosion[1][1][j].getFxImage());
                            }
                            for (Entity e : HelloApplication.entities) {
                                if (e instanceof Balloom && (e.check_collision(Flame) || e.check_collision(current))) {
                                    ((Balloom) e).is_dead = true;
                                } else if (e instanceof Oneal && (e.check_collision(Flame) || e.check_collision(current))) {
                                    ((Oneal) e).is_dead = true;
                                }
                            }
                            if (Flame.check_collision(this) || current.check_collision(this)) {
                                HelloApplication.gameState = 3;
                            }
                            HelloApplication.flame.get(i).add(Flame);
                        }
                    }
                }
                if (num > 86 + i) {
                    HelloApplication.bomb.remove(i);
                    count.remove(i);
                    HelloApplication.flame.remove(i);
                    i--;
                }
            }
            /*
            if (80 + i < num && num <= 82 + i) {
                HelloApplication.bomb.get(i).setImg(Picture.explosion[2][0][0].getFxImage());
                Bomb current = HelloApplication.bomb.get(i);
                for (int j = 0; j < 4; j++) {
                    int newX = current.getSmallX() + change_x[j];
                    int newY = current.getSmallY() + change_y[j];
                    if (validSquare(newX, newY)) {
                        if (HelloApplication.map.sprite[newX][newY] instanceof Brick) {
                            if (!((Brick) HelloApplication.map.sprite[newX][newY]).isDestroyed()) {
                                ((Brick) HelloApplication.map.sprite[newX][newY]).setImg(Picture.brick[1].getFxImage());
                            } else {
                                Bomb Flame;
                                if (j % 2 == 1) {
                                    Flame = new Bomb(newX, newY, Picture.explosion[0][1][j - 1].getFxImage());
                                } else {
                                    Flame = new Bomb(newX, newY, Picture.explosion[1][1][j].getFxImage());
                                }
                                HelloApplication.flame.get(i).add(Flame);
                            }
                        } else {
                            Bomb Flame;
                            if (j % 2 == 1) {
                                Flame = new Bomb(newX, newY, Picture.explosion[0][1][j - 1].getFxImage());
                            } else {
                                Flame = new Bomb(newX, newY, Picture.explosion[1][1][j].getFxImage());
                            }
                            for (Entity e : HelloApplication.entities) {
                                if (e instanceof Balloom && (e.check_collision(Flame) || e.check_collision(current))) {
                                    ((Balloom) e).is_dead = true;
                                } else if (e instanceof Oneal && (e.check_collision(Flame) || e.check_collision(current))) {
                                    ((Oneal) e).is_dead = true;
                                }
                            }
                            if (Flame.check_collision(this) || current.check_collision(this)) {
                                HelloApplication.gameState = 3;
                            }
                            HelloApplication.flame.get(i).add(Flame);
                        }
                    }
                }
            }
            if (82 + i < num && num <= 84 + i) {
                HelloApplication.bomb.get(i).setImg(Picture.explosion[2][1][0].getFxImage());
                Bomb current = HelloApplication.bomb.get(i);
                for (int j = 0; j < 4; j++) {
                    int newX = current.getSmallX() + change_x[j];
                    int newY = current.getSmallY() + change_y[j];
                    if (validSquare(newX, newY)) {
                        if (HelloApplication.map.sprite[newX][newY] instanceof Brick) {
                            if (!((Brick) HelloApplication.map.sprite[newX][newY]).isDestroyed()) {
                                ((Brick) HelloApplication.map.sprite[newX][newY]).setImg(Picture.brick[2].getFxImage());
                            } else {
                                Bomb Flame;
                                if (j % 2 == 1) {
                                    Flame = new Bomb(newX, newY, Picture.explosion[0][1][j - 1].getFxImage());
                                } else {
                                    Flame = new Bomb(newX, newY, Picture.explosion[1][1][j].getFxImage());
                                }
                                HelloApplication.flame.get(i).add(Flame);
                            }
                        } else {
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
            }
            if (84 + i < num && num <= 86 + i) {
                HelloApplication.bomb.get(i).setImg(Picture.explosion[2][2][0].getFxImage());
                Bomb current = HelloApplication.bomb.get(i);
                for (int j = 0; j < 4; j++) {
                    int newX = current.getSmallX() + change_x[j];
                    int newY = current.getSmallY() + change_y[j];
                    if (validSquare(newX, newY)) {
                        if (HelloApplication.map.sprite[newX][newY] instanceof Brick) {
                            if (!((Brick) HelloApplication.map.sprite[newX][newY]).isDestroyed()) {
                                ((Brick) HelloApplication.map.sprite[newX][newY]).setImg(Picture.brick[3].getFxImage());
                            } else {
                                Bomb Flame;
                                if (j % 2 == 1) {
                                    Flame = new Bomb(newX, newY, Picture.explosion[0][1][j - 1].getFxImage());
                                } else {
                                    Flame = new Bomb(newX, newY, Picture.explosion[1][1][j].getFxImage());
                                }
                                HelloApplication.flame.get(i).add(Flame);
                            }
                        } else {
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
                //playMusic(1); chưa biết nhét mô
            }
             */
        }
    }

    public Rectangle2D getBoundary2D() {
        return new Rectangle2D(x, y, 12 * 2, 16 * 2);
    }

    public boolean checkCollision(Entity entity) {
        return entity.getBoundary2D().intersects(this.getBoundary2D());
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
            if (HelloApplication.bomb.size() != 0) {
                check = true;
            }
            for (Entity e : HelloApplication.entities) {
                if (e.check_collision(this)) {
                    check = true;
                }
            }
            if (!check) {
                Bomb newBomb = new Bomb((x + 10) / Sprite.SCALED_SIZE,
                        (y + 10) / Sprite.SCALED_SIZE - HelloApplication.MENUHEIGHT, Picture.bomb[0].getFxImage());
                count.add(0);
                HelloApplication.bomb.add(newBomb);
                List<Bomb> l = new ArrayList<>();
                HelloApplication.flame.add(l);
            }
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