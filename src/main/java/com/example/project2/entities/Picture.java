package com.example.project2.entities;

import com.example.project2.graphics.Sprite;
import com.example.project2.graphics.SpriteSheet;

public class Picture {
    public Sprite[][] player = new Sprite[5][3]; // up - right - down - left - dead
    public Sprite[][] balloom = new Sprite[3][3]; // left - right - dead
    public Sprite[][] oneal = new Sprite[3][3]; // left - right - dead
    public Sprite[][] doll = new Sprite[3][3]; // left - right - dead
    public Sprite[][] minvo = new Sprite[3][3]; // left - right - dead
    public Sprite[][] kondoria = new Sprite[3][3]; // (left - right - dead), (time)
    public Sprite[][][] explosion = new Sprite[3][3][3]; // (vertical, horizontal, middle) - (small,medium,big), (length)
    public Sprite[] mob_dead = new Sprite[3];
    public Sprite[] bomb = new Sprite[3];
    public static Sprite[] brick = new Sprite[4]; // normal, exploded 1-2-3
    public Sprite[] powerup = new Sprite[7]; //bomb - flames - speed - wallpass - detonator - bombpass - flamepass
    public static Sprite grass = new Sprite(Sprite.DEFAULT_SIZE, 6, 0, SpriteSheet.tiles, 16, 16);
    public static Sprite wall = new Sprite(Sprite.DEFAULT_SIZE, 5, 0, SpriteSheet.tiles, 16, 16);
    public static Sprite portal = new Sprite(Sprite.DEFAULT_SIZE, 4, 0, SpriteSheet.tiles, 14, 14);

    public Picture() {
        for (int i = 0; i < 3; i++) {
            player[0][i] = new Sprite(Sprite.DEFAULT_SIZE, 0, i, SpriteSheet.tiles, 12, 16);
            player[1][i] = new Sprite(Sprite.DEFAULT_SIZE, 1, i, SpriteSheet.tiles, 12, 16);
            player[2][i] = new Sprite(Sprite.DEFAULT_SIZE, 2, i, SpriteSheet.tiles, 12, 16);
            player[3][i] = new Sprite(Sprite.DEFAULT_SIZE, 3, i, SpriteSheet.tiles, 12, 16);
            player[4][i] = new Sprite(Sprite.DEFAULT_SIZE, 4 + i, 2, SpriteSheet.tiles, 12, 16);
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                balloom[i][j] = new Sprite(Sprite.DEFAULT_SIZE, 9 + i, j, SpriteSheet.tiles, 16, 16);
                oneal[i][j] = new Sprite(Sprite.DEFAULT_SIZE, 11 + i, j, SpriteSheet.tiles, 16, 16);
                doll[i][j] = new Sprite(Sprite.DEFAULT_SIZE, 13 + i, j, SpriteSheet.tiles, 16, 16);
                minvo[i][j] = new Sprite(Sprite.DEFAULT_SIZE, 8 + i, 5 + j, SpriteSheet.tiles, 16, 16);
                kondoria[i][j] = new Sprite(Sprite.DEFAULT_SIZE, 10 + i, 5 + j, SpriteSheet.tiles, 16, 16);
            }
        }
        balloom[2][0] = new Sprite(Sprite.DEFAULT_SIZE, 9, 3, SpriteSheet.tiles, 16, 16);
        oneal[2][0] = new Sprite(Sprite.DEFAULT_SIZE, 11, 3, SpriteSheet.tiles, 16, 16);
        doll[2][0] = new Sprite(Sprite.DEFAULT_SIZE, 13, 3, SpriteSheet.tiles, 16, 16);
        minvo[2][0] = new Sprite(Sprite.DEFAULT_SIZE, 8, 8, SpriteSheet.tiles, 16, 16);
        kondoria[2][0] = new Sprite(Sprite.DEFAULT_SIZE, 10, 8, SpriteSheet.tiles, 16, 16);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                explosion[0][i][j] = new Sprite(Sprite.DEFAULT_SIZE, 1 + i, 4 + j, SpriteSheet.tiles, 16, 16);
                explosion[1][i][j] = new Sprite(Sprite.DEFAULT_SIZE, j, 7 + i, SpriteSheet.tiles, 16, 16);
            }
            explosion[2][i][0] = new Sprite(Sprite.DEFAULT_SIZE, 0, 4 + i, SpriteSheet.tiles, 16, 16);
        }

        for (int i = 0; i < 3; i++) {
            mob_dead[i] = new Sprite(Sprite.DEFAULT_SIZE, 15, i, SpriteSheet.tiles, 16, 16);
            bomb[i] = new Sprite(Sprite.DEFAULT_SIZE, i, 3, SpriteSheet.tiles, 13, 15);
        }
        for (int i = 0; i < 4; i++) {
            brick[i] = new Sprite(Sprite.DEFAULT_SIZE, 7, i, SpriteSheet.tiles, 16, 16);
        }
        for (int i = 0; i < 7; i++) {
            powerup[i] = new Sprite(Sprite.DEFAULT_SIZE, i, 10, SpriteSheet.tiles, 16, 16);
        }
    }
}
