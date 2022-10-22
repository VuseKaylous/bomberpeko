package com.example.project2.entities;

import com.example.project2.HelloApplication;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

public class SpeedItem extends Entity {
    private boolean eaten = false;
    private boolean updated = false;
    public SpeedItem(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        if (eaten) {

        }
    }

    public void setEaten(boolean eaten) {
        this.eaten = eaten;
    }

    @Override
    public void update(KeyEvent e) {

    }
}
