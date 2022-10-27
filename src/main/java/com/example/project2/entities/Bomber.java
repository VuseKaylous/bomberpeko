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
    private final int speed = 2;
    private final int snapSize = 5;



    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int getSmallX() {
        return x / Sprite.SCALED_SIZE;
    }

    @Override
    public int getSmallY() {
        return y / Sprite.SCALED_SIZE;
    }

    @Override
    public void update() {
        //bien count chay moi lan update
        count++;
        System.out.println(count);
        updateImage();
        //bomb.update();
//        System.out.println(count);
    }

    public void updateImage() {
        if (count == 100) {
            HelloApplication.stillObjects.remove(newBomb);
            HelloApplication.stillObjects.add(newBomb1);
            //System.out.println("1");
        }else if (count == 200) {
            HelloApplication.stillObjects.remove(newBomb1);
            HelloApplication.stillObjects.add(newBomb2);
            //System.out.println("2");
        }else if(count == 300) {
            HelloApplication.stillObjects.remove(newBomb2);
            //System.out.println("3");
            HelloApplication.stillObjects.add(Explosive1);
            playSEE(1);
        }else if(count == 400) {

            HelloApplication.stillObjects.remove(Explosive1);


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
                if (HelloApplication.map.sprite[i][j] instanceof Brick && !HelloApplication.map.state[i][j]) {
                    continue;
                }
                if (this.checkCollision(HelloApplication.map.sprite[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void update(KeyEvent event) {

        int direction = 4; // ko co event thi dung yen
        KeyCode key = event.getCode();
        switch (key) {
            case LEFT:
                direction = 0;
                break;
            case UP:
                direction = 1;
                break;
            case RIGHT:
                //Bomber rightDir = new Bomber(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE, Picture.player[1][3].getFxImage());
                direction = 2;
                break;
            case DOWN:
                direction = 3;
                break;
            case Q:
                //khoi tao qua bom roi dat vao duoi chan con bomber
                count = 0;
                bomb newBomb = new bomb(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE, Picture.bomb[0].getFxImage());
//                bomb newBomb1 = new bomb(newBomb.x / Sprite.SCALED_SIZE, newBomb.y / Sprite.SCALED_SIZE, Picture.bomb[1].getFxImage());
//                bomb newBomb2 = new bomb(newBomb1.x / Sprite.SCALED_SIZE, newBomb1.y / Sprite.SCALED_SIZE, Picture.bomb[2].getFxImage());
//                bomb Explosive1 = new bomb(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE, Picture.explosion[0][1][1].getFxImage());
                HelloApplication.stillObjects.add(newBomb);
                update();
                break;
        }

        x = x + change_x[direction] * speed;
        y = y + change_y[direction] * speed;

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
