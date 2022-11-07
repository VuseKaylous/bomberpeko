package com.example.project2.entities;

import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

public class Bomb extends Entity {
    public Bomb(int x, int y, Image img) {
        super(x, y, img);
    }

    public static boolean is_effective = true;

    public void setImg(Image img) {
        this.img = img;
    }

    @Override
    public void update(KeyEvent event) {
    }

    @Override
    public void update() {
    }
}
