package com.example.project2.entities;


import com.example.project2.HelloApplication;
import com.example.project2.entities.Entity;
import com.example.project2.graphics.Sprite;
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
        int change_i[] = {0, 1, 0, -1};
        int change_j[] = {-1, 0, 1, 0};
        int k = (int) (Math.random() * 4);
        int x1 = super.x / Sprite.SCALED_SIZE + change_i[k];
        int y1 = super.y / Sprite.SCALED_SIZE + change_j[k];
        if (x1 < 0 || x1 >= HelloApplication.HEIGHT || y1 < 0 || y1 >= HelloApplication.WIDTH) {
//            System.out.println("1 " + x1 + " " + y1);
            return;
        } else if (HelloApplication.map.sprite[x1][y1] instanceof Wall) {
//            System.out.println("2");
            return;
        } else if (HelloApplication.map.sprite[x1][y1] instanceof Brick && HelloApplication.map.state[x1][y1] == true) {
//            System.out.println("3");
            return;
        } else {
            for(int i = 0; i < Sprite.SCALED_SIZE; i++) {
                x = x + change_i[k];
                y = y + change_j[k];
            }
        }
    }
}
