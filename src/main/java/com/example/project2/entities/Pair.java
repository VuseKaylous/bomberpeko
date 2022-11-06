package com.example.project2.entities;

public class Pair {
    int x;
    int y;

    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String print() {
        return x + " " + y;
    }

    public boolean equals(Pair p) {
        return this.x == p.x && this.y == p.y;
    }
}
