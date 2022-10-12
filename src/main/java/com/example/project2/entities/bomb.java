package com.example.project2.entities;

import com.example.project2.entities.*;
import com.example.project2.graphics.Sprite;

import com.example.project2.HelloApplication;
import com.example.project2.entities.Entity;
import com.example.project2.graphics.Sprite;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import com.example.project2.entities.*;
import java.util.Scanner;

public class bomb extends Entity {



    public bomb(int x, int y, Image img) {
        super(x, y, img);
    }
    @Override
    public void update(KeyEvent event) {
        KeyCode key = event.getCode();
        switch(key) {

        }
    }


    @Override
    public void update() {

    }
}
