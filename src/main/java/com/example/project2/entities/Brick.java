package com.example.project2.entities;

import com.example.project2.entities.Entity;
import javafx.scene.image.Image;

public class Brick extends Entity{
    private boolean destroyed;
    private int frame;
    public Brick(int x, int y, Image img) {
        super(x, y, img);
        destroyed = false;
        frame = 0;
    }

    public void setDestroyed() {
        destroyed = true;
    }

    @Override
    public void update() {
        if (destroyed) {
            if (frame == 32 * 4) {
                return;
            }
            frame = frame + 1;
            this.img = Picture.brick[frame/32].getFxImage();
        }
    }
}
