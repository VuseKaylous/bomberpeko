package com.example.project2.entities;

import com.example.project2.HelloApplication;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

public class SpeedItem extends Entity {
    public boolean eaten = false;
    private boolean updated = false;
    public SpeedItem(double x, double y, Image img) {
        super(x, y, img);
    }

    public SpeedItem(double x, double y) {
        super(x, y);
        this.img = Picture.powerup[2].getFxImage();
    }

    @Override
    public void update() {

    }

    public void setEaten(boolean eaten) {
        this.eaten = eaten;
    }

    @Override
    public void update(KeyEvent e) {

    }
}
