package com.example.project2.entities;


import com.example.project2.graphics.Sprite;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import javafx.geometry.Rectangle2D;

public abstract class Entity {
    //Tọa độ ngang tính từ góc trái trên trong Canvas
    protected static int x;

    //Tọa độ dọc tính từ góc trái trên trong Canvas
    protected static int y;
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

//    public boolean checkCollision(Entity spr) {
//        return spr.getBoundary().intersects(this.getBoundary());
//    }

    public abstract void update();

    public abstract void update(KeyEvent e);
}

