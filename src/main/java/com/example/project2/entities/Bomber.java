package com.example.project2.entities;
import java.lang.Math;
import com.example.project2.HelloApplication;
import com.example.project2.graphics.Sound;
import com.example.project2.graphics.Sprite;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
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
    public boolean getSpeed_item;
    public boolean getBomb_item;
    public boolean getFlame_item;
    public boolean getRandom_item;
    public boolean getBoost_item;
    private ArrayList<Entity> itemsGot = new ArrayList<Entity>();
    int count_move = 0;
    int count_defense = 0;
    int speed;
    int rand = (int) (Math.random() * 3) + 1;

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
        count_move = 0;
        count.clear();
        getBomb_item = false;
        getFlame_item = false;
        getSpeed_item = false;
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
            if (80 + i < num && num < 86 + i) {
//                playMusic(1);
                int id = 0;
                if (80 + i < num && num <= 82 + i) id = 0;
                else if (82 + i <= num && num < 84 + i) id = 1;
                else if (84 + i <= num && num < 86 + i) id = 2;
                HelloApplication.bomb.get(i).setImg(Picture.explosion[2][id][0].getFxImage());
                Bomb current = HelloApplication.bomb.get(i);
                for (int j = 0; j < 4; j++) {
                    int newX = current.getSmallX() + change_x[j];
                    int newY = current.getSmallY() + change_y[j];
                    int newX2 = newX + change_x[j];
                    int newY2 = newY + change_y[j];
                    if (validSquare(newX, newY)) {
                        playMusic(1);
                        if (getFlame_item && validSquare(newX2, newY2)) {
                            //có item
                            if (HelloApplication.map.sprite[newX][newY] instanceof Brick br && !br.isDestroyed()) {
                                br.setImg(Picture.brick[id + 1].getFxImage());
                            } else {
                                if (HelloApplication.map.sprite[newX2][newY2] instanceof Brick br && !br.isDestroyed()) {
                                    br.setImg(Picture.brick[id + 1].getFxImage());
                                } else {
                                    Bomb Flame, Flame2;
                                    if (j % 2 == 1) {
                                        Flame = new Bomb(newX2, newY2, Picture.explosion[0][id][j - 1].getFxImage());
                                        Flame2 = new Bomb(newX, newY, Picture.explosion[0][id][1].getFxImage());
                                    } else {
                                        Flame = new Bomb(newX2, newY2, Picture.explosion[1][id][j].getFxImage());
                                        Flame2 = new Bomb(newX, newY, Picture.explosion[1][id][1].getFxImage());
                                    }
                                    HelloApplication.flame.get(i).add(Flame);
                                    HelloApplication.flame.get(i).add(Flame2);

                                    for (Entity e : HelloApplication.entities) {
                                        if (e instanceof Balloom b && (e.check_collision(Flame) || e.check_collision(current) || e.check_collision(Flame2))) {
                                            b.is_dead = true;
                                        } else if (e instanceof Oneal o && (e.check_collision(Flame) || e.check_collision(current) || e.check_collision(Flame2))) {
                                            o.is_dead = true;
                                        }
                                    }
                                    if (Flame.check_collision(this) || current.check_collision(this) || Flame2.check_collision(this)) {
                                        HelloApplication.gameState = 3;
                                    }
                                }
                            }
                        } else {
                            //không có item
                            if (HelloApplication.map.sprite[newX][newY] instanceof Brick br && !br.isDestroyed()) {
                                br.setImg(Picture.brick[id + 1].getFxImage());
                            } else {
                                Bomb Flame;
                                if (j % 2 == 1) {
                                    Flame = new Bomb(newX, newY, Picture.explosion[0][id][j - 1].getFxImage());
                                } else {
                                    Flame = new Bomb(newX, newY, Picture.explosion[1][id][j].getFxImage());
                                }
                                for (Entity e : HelloApplication.entities) {
                                    if (e instanceof Balloom && (e.check_collision(Flame) || e.check_collision(current))) {
                                        ((Balloom) e).is_dead = true;
                                    } else if (e instanceof Oneal && (e.check_collision(Flame) || e.check_collision(current))) {
                                        ((Oneal) e).is_dead = true;
                                    }
                                }
                                if (Flame.check_collision(this) || current.check_collision(this)) {
                                    count_defense--;
                                    System.out.println(count_defense);
                                    if(count_defense >= 0) {
                                        HelloApplication.gameState = 0;
                                    }else if(count_defense < 0)  {
                                        HelloApplication.gameState = 3;
                                    }
                                }
                                HelloApplication.flame.get(i).add(Flame);
                            }
                        }
                    }
                }
            }
            if (num > 86 + i) {
                for (int j = 0; j < 4; j++) {
                    int newX = HelloApplication.bomb.get(i).getSmallX() + change_x[j];
                    int newY = HelloApplication.bomb.get(i).getSmallY() + change_y[j];
                    int newX2 = newX + change_x[j];
                    int newY2 = newY + change_y[j];
                    if (validSquare(newX, newY)) {
                        if (HelloApplication.map.sprite[newX][newY] instanceof Brick br) {
                            if (!br.isDestroyed()) {
                                br.destroyed = true;
                            } else {
                                if (getFlame_item && HelloApplication.map.sprite[newX2][newY2] instanceof Brick brick) {
                                    brick.destroyed = true;
                                }
                            }
                        } else {
                            if (getFlame_item && HelloApplication.map.sprite[newX2][newY2] instanceof Brick brick) {
                                brick.destroyed = true;
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
//            playMusic(2);
            if (!getBomb_item && HelloApplication.bomb.size() > 0) {
                check = true;
            }
            if (getBomb_item && HelloApplication.bomb.size() >= 2) {
                check = true;
            }
            for (Entity e : HelloApplication.entities) {
                if (e.check_collision(this)) {
                    check = true;
                }
            }
            for (Bomb b : HelloApplication.bomb) {
                if (b.check_collision(this)) {
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
        if (HelloApplication.map.tool[getSmallX()][getSmallY()] instanceof SpeedItem) {
            if (HelloApplication.map.sprite[getSmallX()][getSmallY()] instanceof Brick brick) {
                if (brick.isDestroyed()) {
                    getSpeed_item = true;
                    int id = itemsGot.size();
                    itemsGot.add(new SpeedItem(1.5 * id + 1,
                            0.5 - HelloApplication.MENUHEIGHT));
                }
            }
        }
        if (HelloApplication.map.tool[getSmallX()][getSmallY()] instanceof BombItem) {
            if (HelloApplication.map.sprite[getSmallX()][getSmallY()] instanceof Brick brick) {
                if (brick.isDestroyed()) {
                    getBomb_item = true;
                    int id = itemsGot.size();
                    itemsGot.add(new BombItem(1.5 * id + 1,
                            0.5 - HelloApplication.MENUHEIGHT));
                }
            }
        }
        if (HelloApplication.map.tool[getSmallX()][getSmallY()] instanceof FlameItem) {
            if (HelloApplication.map.sprite[getSmallX()][getSmallY()] instanceof Brick brick) {
                if (brick.isDestroyed()) {
                    getFlame_item = true;
                    int id = itemsGot.size();
                    itemsGot.add(new FlameItem(1.5 * id + 1,
                            0.5 - HelloApplication.MENUHEIGHT));
                }
            }
        }
        if (HelloApplication.map.tool[getSmallX()][getSmallY()] instanceof RandomItem) {
            if (HelloApplication.map.sprite[getSmallX()][getSmallY()] instanceof Brick brick) {
                if (brick.isDestroyed()) {
                    getRandom_item = true;
                    int a = rand;
                    if(a == 1) {
                        getSpeed_item = true;
                        int id = itemsGot.size();
                        itemsGot.add(new SpeedItem(1.5 * id + 1,
                                0.5 - HelloApplication.MENUHEIGHT));
                    }else if(a == 2) {
                        getBomb_item = true;
                        int id1 = itemsGot.size();
                        itemsGot.add(new BombItem(1.5 * id1 + 1,
                                0.5 - HelloApplication.MENUHEIGHT));
                    }else if(a == 3) {
                        getFlame_item = true;
                        int id2 = itemsGot.size();
                        itemsGot.add(new FlameItem(1.5 * id2 + 1,
                                0.5 - HelloApplication.MENUHEIGHT));
                    }
                }
            }
        }
        if (HelloApplication.map.tool[getSmallX()][getSmallY()] instanceof BoostItem) {
            if (HelloApplication.map.sprite[getSmallX()][getSmallY()] instanceof Brick brick) {
                if (brick.isDestroyed()) {
                    getBoost_item = true;
                    getSpeed_item = true;
                    getFlame_item = true;
                    int id = itemsGot.size();
                    itemsGot.add(new BoostItem(1.5 * id + 1,
                            0.5 - HelloApplication.MENUHEIGHT));
                }
            }
        }
        speed = 2;
        if (getSpeed_item) {
            speed = 3;
        }
        int direction = 4; // ko co event thi dung yen
        KeyCode key = event.getCode();
        int[] directionToPicture = new int[]{3, 0, 1, 2};
        switch (key) {
            case LEFT -> {
                direction = 0;
            }
            case UP -> {
                direction = 1;
            }
            case RIGHT -> {
                direction = 2;
            }
            case DOWN -> {
                direction = 3;
            }
        }
        count_move = (count_move + 1) % 30;
        if (count_move % 10 == 0 && direction < 4) {
            img = Picture.player[directionToPicture[direction]][2 - (count_move / 10)].getFxImage(); // thay anh
        }
        //System.out.println(speed);
        x = x + change_x[direction] * speed;
        y = y + change_y[direction] * speed;

        int snapSize = 6;
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

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
//        System.out.println(itemsGot.size());
        for (Entity entity : itemsGot) {
//            System.out.println("fuck");
            entity.render(gc);
        }
    }

    public void playMusic(int i) {
        soundtmp.setFile(i);
        soundtmp.play();
    }

    public void stopMusic() {
        soundtmp.stop();
    }

    public void playSE(int i) {
        soundtmp.setFile(i);
        soundtmp.play();

    }
}
