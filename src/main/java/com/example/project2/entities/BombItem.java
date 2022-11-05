package com.example.project2.entities;

import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

public class BombItem extends Entity {
    public BombItem(double x, double y, Image img) {
        super(x, y, img);
    }

    public BombItem(double x, double y) {
        super(x, y);
        this.img = Picture.powerup[0].getFxImage();
    }

    @Override
    public void update() {

    }

    @Override
    public void update(KeyEvent e) {

    }
}
