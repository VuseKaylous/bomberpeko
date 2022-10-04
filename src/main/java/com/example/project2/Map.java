package com.example.project2;

import com.example.project2.entities.*;
import com.example.project2.graphics.Sprite;

import java.util.Scanner;

public class Map {
    //sprite lưu vị trí wall, grass, brick
    public Entity[][] sprite = new Entity[HelloApplication.HEIGHT][HelloApplication.WIDTH];

    //tool lưu portal, các item
    public Entity[][] tool = new Entity[HelloApplication.HEIGHT][HelloApplication.WIDTH];

    /*state lưu trạng thái của brick
    nếu brick còn => state[i][j] = true
    nếu brick đã nổ => state[i][j] = false*/
    public boolean[][] state = new boolean[HelloApplication.HEIGHT][HelloApplication.WIDTH];

    public final int WALL = 0;
    public final int GRASS = 1;
    public final int BRICK = 2;

    public Map() {
        for (int i = 0; i < HelloApplication.HEIGHT; i++) {
            for (int j = 0; j < HelloApplication.WIDTH; j++) {
                sprite[i][j] = new Grass(i, j, Picture.grass.getFxImage());
                tool[i][j] = new Grass(i, j, Picture.grass.getFxImage());
                state[i][j] = false;
            }
        }
    }

    public void updateCol(String col, int col_num) {
        for (int i = 0; i < col.length(); i++) {
            if (col.charAt(i) == '#') {
                sprite[i][col_num] = new Wall(i, col_num, Picture.wall.getFxImage());
            } else if (col.charAt(i) == '*') {
                sprite[i][col_num] = new Brick(i, col_num, Picture.brick[0].getFxImage());
                state[i][col_num] = true;
            } else if (col.charAt(i) == 'x') {
                tool[i][col_num] = new Portal(i, col_num, Picture.portal.getFxImage());
                sprite[i][col_num] = new Brick(i, col_num, Picture.brick[0].getFxImage());
                state[i][col_num] = true;
            } else if (col.charAt(i) == 'b') {
                tool[i][col_num] = new BombItem(i, col_num, Picture.powerup[0].getFxImage());
                sprite[i][col_num] = new Brick(i, col_num, Picture.brick[0].getFxImage());
                state[i][col_num] = true;
            } else if (col.charAt(i) == 'f') {
                tool[i][col_num] = new FlameItem(i, col_num, Picture.powerup[1].getFxImage());
                sprite[i][col_num] = new Brick(i, col_num, Picture.brick[0].getFxImage());
                state[i][col_num] = true;
            } else if (col.charAt(i) == 's') {
                tool[i][col_num] = new SpeedItem(i, col_num, Picture.powerup[2].getFxImage());
                sprite[i][col_num] = new Brick(i, col_num, Picture.brick[0].getFxImage());
                state[i][col_num] = true;
            }
        }
    }
}
