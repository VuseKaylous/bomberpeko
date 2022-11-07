package com.example.project2.menu;

import com.example.project2.HelloApplication;
import com.example.project2.graphics.UsefulFuncs;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.TextAlignment;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class HighScoreScreen extends Menu {
    private ArrayList<String> name = new ArrayList<String>();
    private ArrayList<Integer> score = new ArrayList<Integer>();

    private final Rectangle backButton;
    public HighScoreScreen() {
        super();
//        File file = new File("highScore.txt");
        try {
//            Scanner reader = new Scanner(file);
            List<String> lines = Files.readAllLines(Paths.get("highScore.txt"));
            for (int i = 0; i < lines.size(); i++) {
                String[] arr = lines.get(i).split(" ", 2);
                name.add(arr[0]);
                score.add(Integer.valueOf(arr[1]));
            }
        } catch (IOException e) {
            System.out.println("Can't find highScore.txt");
        }
        backButton = new Rectangle(70, 370, 80, 30);
    }

    public String toString() {
        String ans = "";
        for (int i = 0; i < name.size(); i++) {
            ans = ans + name.get(i) + " " + score.get(i);
            if (i != name.size() - 1) {
                ans = ans + "\n";
            }
        }
        return ans;
    }

    public void insert(String _name, int highscore) {
        name.add(_name);
        score.add(highscore);
        int pos = name.size() - 1;
        while (pos > 0 && score.get(pos) > score.get(pos-1)) {
            Collections.swap(score, pos, pos-1);
            Collections.swap(name, pos, pos-1);
            pos--;
        }
        try {
            PrintWriter fw = new PrintWriter("highScore.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(this.toString());
            bw.close();
        } catch (IOException e) {
            System.out.println("Can't write in highScore.txt");
        }
    }

    public boolean checkHighscore(int highscore) {
        if (name.size() < 5) {
            return true;
        }
        if (score.get(4) < highscore) {
            return true;
        }
        return false;
    }

    public void updateHighscore(String _name, int highscore) {
        if (checkHighscore(highscore)) {
            if (score.size() >= 5) {
                score.remove(4);
            }
            insert(_name, highscore);
        }
    }

    @Override
    public void render(GraphicsContext graphicsContext) {
        UsefulFuncs.renderText(graphicsContext,
                "Back",
                Color.BLACK,
                (int) (backButton.getX() + backButton.getWidth() / 2),
                (int) (backButton.getY() + backButton.getHeight() / 2),
                20);
        super.paintRect(backButton, graphicsContext);

        graphicsContext.setFont(Font.font("Comic Sans MS", FontPosture.REGULAR, 20));
        graphicsContext.setTextAlign(TextAlignment.LEFT);
        graphicsContext.setTextBaseline(VPos.TOP);
        graphicsContext.setFill(Color.BLACK);
        int x1 = 250;
        int x2 = x1 + 50;
        int x3 = x2 + 200;
        int y = 100;
        int nameHeight = 50;
        for (int i = 0; i < score.size(); i++) {
            graphicsContext.fillText(String.valueOf(i+1), x1, y + i * nameHeight);
            graphicsContext.fillText(name.get(i), x2, y + i * nameHeight);
            graphicsContext.fillText(String.valueOf(score.get(i)), x3, y + i * nameHeight);
        }
    }

    @Override
    public void handleEvent(Scene scene1) {
        scene1.setOnMouseMoved(MouseEvent -> {
            mouseEvent = MouseEvent;
        });
        scene1.setOnMouseReleased(mouseEvent1 -> {
            mouseEvent = mouseEvent1;
            if (UsefulFuncs.inRect(backButton, mouseEvent1)) {
                HelloApplication.gameState = HelloApplication.GameState.START;
            }
        });
    }
}
