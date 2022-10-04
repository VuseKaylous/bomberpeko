package com.example.project2.entities;

import javafx.scene.image.Image;
import java.util.random.RandomGenerator;

public class Oneal extends Entity{
    private final long RANGESPEED = 10;
    private final int MINSPEED = 10;
    private RandomGenerator generator = new RandomGenerator() {
        @Override
        public long nextLong() {
            return RANGESPEED;
        }
    };

    private int speed;
    private int destinationX, destinationY;
    public Oneal(int x, int y, Image img) {
        super(x, y, img);
        destinationX = x;
        destinationY = y;
    }

    @Override
    public void update() {
        if (destinationX == x && destinationY == y) {
            speed = (int)generator.nextLong(RANGESPEED) + MINSPEED;

        }

    }
}
