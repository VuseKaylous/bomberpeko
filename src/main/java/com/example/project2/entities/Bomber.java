package com.example.project2.entities;

import com.example.project2.entities.Entity;
import javafx.scene.image.Image;

public class Bomber extends Entity {
    public Bomber(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        int[] x_move = {-1, 0, 1, 0};
        int[] y_move = {0, -1, 0, 1};

    }
}
