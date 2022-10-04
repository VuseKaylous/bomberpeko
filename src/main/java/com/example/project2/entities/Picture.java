package com.example.project2.entities;

import com.example.project2.graphics.Sprite;
import com.example.project2.graphics.SpriteSheet;

public class Picture {
    public Sprite[][] player = new Sprite[5][3];
    public Sprite[][] oneal = new Sprite[2][4];
    public Picture() {
        for (int i = 0; i < 3; i++) {
            player[0][i] = new Sprite(Sprite.DEFAULT_SIZE, 0, i, SpriteSheet.tiles, 12, 16);
            player[1][i] = new Sprite(Sprite.DEFAULT_SIZE, 1, i, SpriteSheet.tiles, 12, 16);
            player[2][i] = new Sprite(Sprite.DEFAULT_SIZE, 2, i, SpriteSheet.tiles, 12, 16);
            player[3][i] = new Sprite(Sprite.DEFAULT_SIZE, 3, i, SpriteSheet.tiles, 12, 16);
            player[4][i] = new Sprite(Sprite.DEFAULT_SIZE, 4 + i, 2, SpriteSheet.tiles, 12, 16);
        }

    }
}
