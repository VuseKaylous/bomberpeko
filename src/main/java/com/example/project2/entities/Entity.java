package com.example.project2.entities;

import com.example.project2.graphics.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import javafx.scene.input.KeyEvent;

import javafx.geometry.Rectangle2D;

public abstract class Entity {
    //Tọa độ ngang tính từ góc trái trên trong Canvas
    public int x;

    //Tọa độ dọc tính từ góc trái trên trong Canvas
    public int y;
    public static final int[] DIRX = new int[]{0, 1, 0, -1};
    public static final int[] DIRY = new int[]{1, 0, -1, 0};

    protected Image img;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity(int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
    }

    public int getSmallX() {
        return this.x / Sprite.SCALED_SIZE;
    }

    public int getSmallY() {
        return this.y / Sprite.SCALED_SIZE;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(x, y, img.getWidth(), img.getHeight());
    }

    public boolean check_collision(Entity e) {
        if (this == e) {
            return false;
        } else {
            for (int i = e.x; i < e.x + Sprite.SCALED_SIZE; i++) {
                for (int j = e.y; j < e.y + Sprite.SCALED_SIZE; j++) {
                    if (x <= i && i < x + Sprite.SCALED_SIZE && y <= j && j < y + Sprite.SCALED_SIZE) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public abstract void update();

    public abstract void update(KeyEvent e);
}

