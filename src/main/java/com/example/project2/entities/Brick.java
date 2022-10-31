package com.example.project2.entities;

import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

public class Brick extends Entity {
    public boolean destroyed;
    private int frame;

    public Brick(int x, int y, Image img) {
        super(x, y, img);
        destroyed = false;
        frame = 0;
    }

    public void setDestroyed() {
        destroyed = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    @Override
    public void update() {
        if (destroyed) {
            if (frame == 32 * 4) {
                return;
            }
            frame = frame + 1;
            this.img = Picture.brick[frame / 32].getFxImage();
        }
    }

    @Override
    public void update(KeyEvent e) {

    }
}
