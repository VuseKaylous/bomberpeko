package com.example.project2.entities;

import com.example.project2.graphics.Sprite;
import com.example.project2.graphics.SpriteSheet;

public class Picture {
    public static Sprite[][] player = new Sprite[5][3]; // up - right - down - left - dead
    public static Sprite[][] balloom = new Sprite[3][3]; // left - right - dead
    public static Sprite[][] oneal = new Sprite[3][3]; // left - right - dead
    public static Sprite[][] doll = new Sprite[3][3]; // left - right - dead
    public static Sprite[][] minvo = new Sprite[3][3]; // left - right - dead
    public static Sprite[][] kondoria = new Sprite[3][3]; // (left - right - dead), (time)
    public static Sprite[][] ghost = new Sprite[3][3];
    public static Sprite[][][] explosion = new Sprite[3][3][3]; // (vertical, horizontal, middle) - (small,medium,big), (length)
    public static Sprite[][] mob_dead = new Sprite[3][3]; // (time1, time2, time3) - (normal, blue, ghost)
    public static Sprite[] bomb = new Sprite[3];
    public static Sprite[] brick = new Sprite[4]; // normal, exploded 1-2-3
    public static Sprite[] powerup = new Sprite[8]; //bomb - flames - speed - wallpass - detonator - bombpass - flamepass
    public static Sprite grass = new Sprite(Sprite.DEFAULT_SIZE, 6, 0, SpriteSheet.tiles, 16, 16);
    public static Sprite wall = new Sprite(Sprite.DEFAULT_SIZE, 5, 0, SpriteSheet.tiles, 16, 16);
    public static Sprite portal = new Sprite(Sprite.DEFAULT_SIZE, 4, 0, SpriteSheet.tiles, 16, 16);
    public static Sprite pauseIcon = new Sprite(Sprite.DEFAULT_SIZE, 0, 12, SpriteSheet.tiles, 16, 16);

    public Picture() {
        for (int i = 0; i < 3; i++) {
            player[0][i] = new Sprite(Sprite.DEFAULT_SIZE, 0, i, SpriteSheet.tiles, 10, 10);
            player[1][i] = new Sprite(Sprite.DEFAULT_SIZE, 1, i, SpriteSheet.tiles, 10, 10);
            player[2][i] = new Sprite(Sprite.DEFAULT_SIZE, 2, i, SpriteSheet.tiles, 10, 10);
            player[3][i] = new Sprite(Sprite.DEFAULT_SIZE, 3, i, SpriteSheet.tiles, 10, 10);
            player[4][i] = new Sprite(Sprite.DEFAULT_SIZE, 4 + i, 2, SpriteSheet.tiles, 10, 10);
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                balloom[i][j] = new Sprite(Sprite.DEFAULT_SIZE, 9 + i, j, SpriteSheet.tiles, 16, 16);
                oneal[i][j] = new Sprite(Sprite.DEFAULT_SIZE, 11 + i, j, SpriteSheet.tiles, 16, 16);
                doll[i][j] = new Sprite(Sprite.DEFAULT_SIZE, 13 + i, j, SpriteSheet.tiles, 16, 16);
                minvo[i][j] = new Sprite(Sprite.DEFAULT_SIZE, 8 + i, 5 + j, SpriteSheet.tiles, 16, 16);
                kondoria[i][j] = new Sprite(Sprite.DEFAULT_SIZE, 10 + i, 5 + j, SpriteSheet.tiles, 16, 16);
                ghost[i][j] = new Sprite(Sprite.DEFAULT_SIZE, 6 + i, 5 + j, SpriteSheet.tiles, 16, 16);
            }
        }
        balloom[2][0] = new Sprite(Sprite.DEFAULT_SIZE, 9, 3, SpriteSheet.tiles, 16, 16);
        oneal[2][0] = new Sprite(Sprite.DEFAULT_SIZE, 11, 3, SpriteSheet.tiles, 16, 16);
        doll[2][0] = new Sprite(Sprite.DEFAULT_SIZE, 13, 3, SpriteSheet.tiles, 16, 16);
        minvo[2][0] = new Sprite(Sprite.DEFAULT_SIZE, 8, 8, SpriteSheet.tiles, 16, 16);
        kondoria[2][0] = new Sprite(Sprite.DEFAULT_SIZE, 10, 8, SpriteSheet.tiles, 16, 16);
        ghost[2][0] = new Sprite(Sprite.DEFAULT_SIZE, 6, 8, SpriteSheet.tiles, 16, 16);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                explosion[0][i][j] = new Sprite(Sprite.DEFAULT_SIZE, 1 + i, 4 + j, SpriteSheet.tiles, 16, 16);
                explosion[1][i][j] = new Sprite(Sprite.DEFAULT_SIZE, j, 7 + i, SpriteSheet.tiles, 16, 16);
            }
            explosion[2][i][0] = new Sprite(Sprite.DEFAULT_SIZE, 0, 4 + i, SpriteSheet.tiles, 16, 16);
        }

        for (int i = 0; i < 3; i++) {
            mob_dead[i][0] = new Sprite(Sprite.DEFAULT_SIZE, 15, i, SpriteSheet.tiles, 16, 16);
            mob_dead[i][1] = new Sprite(Sprite.DEFAULT_SIZE, 15, i + 10, SpriteSheet.tiles, 16, 16);
            mob_dead[i][2] = new Sprite(Sprite.DEFAULT_SIZE, 14, i + 10, SpriteSheet.tiles, 16, 16);
            bomb[i] = new Sprite(Sprite.DEFAULT_SIZE, i, 3, SpriteSheet.tiles, 13, 15);
        }
        for (int i = 0; i < 4; i++) {
            brick[i] = new Sprite(Sprite.DEFAULT_SIZE, 7, i, SpriteSheet.tiles, 16, 16);
        }
        for (int i = 0; i < 8; i++) {
            powerup[i] = new Sprite(Sprite.DEFAULT_SIZE, i, 10, SpriteSheet.tiles, 16, 16);
        }
    }
}
