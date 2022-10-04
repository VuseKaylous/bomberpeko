package com.example.project2;

import com.example.project2.entities.*;
import com.example.project2.graphics.Sprite;

import java.util.Scanner;

public class Map {
    public Entity[][] sprite = new Entity[HelloApplication.HEIGHT][HelloApplication.WIDTH];
    public final int WALL = 0;
    public final int GRASS = 1;
    public final int BRICK = 2;

    public Map() {
        for (int i=0;i<HelloApplication.HEIGHT;i++) {
            for (int j = 0;j < HelloApplication.WIDTH;j++) {
                sprite[i][j] = new Grass(i, j, Picture.grass.getFxImage());
            }
        }
    }
    public void updateCol(String col, int col_num) {
        for (int i=0;i<col.length();i++) {
            if (col.charAt(i) == '#') {
                sprite[i][col_num] = new Wall(i, col_num, Picture.wall.getFxImage());
            }
            else if (col.charAt(i) == '*') {
                sprite[i][col_num] = new Brick(i, col_num, Picture.brick[0].getFxImage());
            }
        }
    }
}
