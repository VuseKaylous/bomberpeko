package com.example.project2.graphics;

import javafx.scene.input.KeyCode;

public class KeyConfig {
//    public KeyCode pause;
//    public KeyCode exit;
//    public KeyCode setBomb;
//    public KeyCode up;
//    public KeyCode down;
//    public KeyCode left;
//    public KeyCode right;
    public KeyCode[] keyCodes = new KeyCode[7];
    public String[] nameKeys = new String[]{"Pause", "Exit", "Set bomb", "Up", "Down", "Left", "Right"};
    public final int PAUSE = 0;
    public final int EXIT = 1;
    public final int SETBOMB = 2;
    public final int UP = 3;
    public final int DOWN = 4;
    public final int LEFT = 5;
    public final int RIGHT = 6;

    public KeyConfig() {
        keyCodes[0] = KeyCode.P;
        keyCodes[1] = KeyCode.ESCAPE;
        keyCodes[2] = KeyCode.Q;

        keyCodes[3] = KeyCode.UP;
        keyCodes[4] = KeyCode.DOWN;
        keyCodes[5] = KeyCode.LEFT;
        keyCodes[6] = KeyCode.RIGHT;
        /*
        File keymap = new File("keymap.txt");
        try {
            Scanner reader = new Scanner(keymap);

            String data = reader.nextLine();
            pause = (KeyCode) data;

        }
         */
    }

    public String getKeyName(int key) { return nameKeys[key]; }
    public KeyCode getKeyCode(int key) { return keyCodes[key]; }

    public void setKeyCode(int chosen, KeyCode keyCode) {
        this.keyCodes[chosen] = keyCode;
    }

    public KeyCode getPause() { return keyCodes[PAUSE]; }
    public KeyCode getExit() { return keyCodes[EXIT]; }
    public KeyCode getSetBomb() { return keyCodes[SETBOMB]; }

    public KeyCode getUp() { return keyCodes[UP]; }
    public KeyCode getDown() { return keyCodes[DOWN]; }
    public KeyCode getLeft() { return keyCodes[LEFT]; }
    public KeyCode getRight() { return keyCodes[RIGHT]; }

}
