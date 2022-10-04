package com.example.project2.entities;


import com.example.project2.entities.Entity;
import javafx.scene.image.Image;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Balloom extends Entity {
    public Balloom(int x, int y, Image img) {
        super(x, y, img);
    }


    @Override
    public void update() {
//        while() {
            int change_i[] = {0, 32, 0, -32};
            int change_j[] = {-32, 0, 32, 0};
            int k = (int)(Math.random()*(4));
            Balloom.super.x += change_i[k];
            Balloom.super.y += change_j[k];
//

//        }

    }
}
