package com.example.project2.graphics;

import javafx.scene.input.KeyCode;

import java.io.File;
import java.security.Key;
import java.util.Scanner;

public class KeyConfig {
    public KeyCode pause;
    public KeyCode exit;

    public KeyCode up;
    public KeyCode down;
    public KeyCode left;
    public KeyCode right;

    public KeyCode setBomb;
    public KeyConfig() {
        /*
        File keymap = new File("keymap.txt");
        try {
            Scanner reader = new Scanner(keymap);

            String data = reader.nextLine();
            pause = (KeyCode) data;

        }
         */
        pause = KeyCode.P;
        exit = KeyCode.ESCAPE;

        up = KeyCode.UP;
        down = KeyCode.DOWN;
        left = KeyCode.LEFT;
        right = KeyCode.RIGHT;

        setBomb = KeyCode.Q;
    }
}
