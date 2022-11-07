package com.example.project2.entities;

import com.example.project2.HelloApplication;
import com.example.project2.graphics.Sprite;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

import java.util.*;

public class Kondoria extends Entity {
    boolean[][] check = new boolean[31][13];
    public boolean is_dead;
    public int cnt;
    public int count;
    private int destinationX;
    private int destinationY;
    private int startX;
    private int startY;
    private int dir;

    public Kondoria(int x, int y, Image img) {
        super(x, y, img);
        destinationX = this.x;
        destinationY = this.y;
        startX = destinationX;
        startY = destinationY;
        is_dead = false;
        cnt = 0;
        count = 0;
    }

    private boolean validSquare(int fakeX, int fakeY) {
        for (Bomb b : HelloApplication.bomb) {
            if (fakeX == b.getSmallX() && fakeY == b.getSmallY()) {
                return false;
            }
        }
        if (fakeX < 0 || fakeX >= HelloApplication.HEIGHT || fakeY < 0 || fakeY >= HelloApplication.WIDTH) {
            return false;
        }
        for (Entity entity : HelloApplication.entities) {
            if (fakeX == entity.getSmallX() && fakeY == entity.getSmallY()) {
                return false;
            }
        }
        if (HelloApplication.map.sprite[fakeX][fakeY] instanceof Grass) {
            return true;
        }
        if (HelloApplication.map.sprite[fakeX][fakeY] instanceof Brick brick) {
            return brick.isDestroyed();
        }
        return false;
    }

    public void updateImage() {
        if (10 < cnt && cnt <= 16) {
            img = Picture.kondoria[2][0].getFxImage();
        } else if (16 < cnt && cnt <= 22) {
            img = Picture.mob_dead[0][1].getFxImage();
        } else if (22 < cnt && cnt <= 28) {
            img = Picture.mob_dead[1][1].getFxImage();
        } else if (28 < cnt && cnt <= 34) {
            img = Picture.mob_dead[2][1].getFxImage();
        }
    }

    @Override
    public void update() {
        if (is_dead) {
            cnt++;
            updateImage();
            return;
        }
        count++;
        if (count % 20 == 0) {
            img = Picture.kondoria[(int) (Math.random() * 2)][(int) (Math.random() * 3)].getFxImage();
        }
        for (int i = 0; i < HelloApplication.HEIGHT; i++) {
            for (int j = 0; j < HelloApplication.WIDTH; j++) {
                check[i][j] = false;
            }
        }
        Map<Pair, Pair> parent = new HashMap<>();
        List<Pair> queue = new ArrayList<>();

        Pair start = new Pair(this.getSmallX(), this.getSmallY());
        Pair ending = new Pair(HelloApplication.bomber.getSmallX(), HelloApplication.bomber.getSmallY());
        queue.add(start);
        while (!queue.isEmpty()) {
            Pair root = queue.get(0);
            check[root.x][root.y] = true;
            queue.remove(0);
            for (int j = 0; j < 4; j++) {
                int newX = root.x + DIRX[j];
                int newY = root.y + DIRY[j];
                if (validSquare(newX, newY)) {
                    if (!check[newX][newY]) {
                        Pair ins = new Pair(newX, newY);
                        parent.put(ins, root);
                        queue.add(ins);
                        if (ins.equals(ending)) {
                            ending = ins;
                        }
                    }
                }
            }
        }
        if (x == destinationX && y == destinationY) {
            if (!check[ending.x][ending.y]) {
                int[] arrDir = new int[4];
                int dem = 0;
                for (int i = 0; i <= 3; i++) {
                    if (i != (dir + 2) % 4 && validSquare(getSmallX() + DIRX[i], getSmallY() + DIRY[i])) {
                        arrDir[dem] = i;
                        dem++;
                    }
                }
                if (dem != 0) {
                    int id = (int) (Math.random() * dem);
                    if (id < 0) id += dem;
                    dir = arrDir[id];
                } else {
                    dir = (dir + 2) % 4;
                }
                destinationX = this.x + DIRX[dir] * Sprite.SCALED_SIZE;
                destinationY = this.y + DIRY[dir] * Sprite.SCALED_SIZE;
            } else {
                while (ending != start) {
                    if (parent.get(ending) == start) {
                        break;
                    }
                    ending = parent.get(ending);
                }
                for (int j = 0; j < 4; j++) {
                    if (start.x + DIRX[j] == ending.x && start.y + DIRY[j] == ending.y) {
                        dir = j;
                        break;
                    }
                }
                destinationX = ending.x * Sprite.SCALED_SIZE;
                destinationY = (ending.y + HelloApplication.MENUHEIGHT) * Sprite.SCALED_SIZE;
            }
            startX = x;
            startY = y;
        }
        x += DIRX[dir];
        y += DIRY[dir];
        for (Entity entity : HelloApplication.entities) {
            if (this.check_collision(entity)) {
                destinationX = startX;
                destinationY = startY;
                x -= DIRX[dir];
                y -= DIRY[dir];
                dir = (dir + 2) % 4;
                break;
            }
        }
    }

    @Override
    public void update(KeyEvent e) {

    }
}
