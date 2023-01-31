package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {

    public boolean firstClick;

    private boolean[][] bomb;
    public int height;
    public int width;
    public int numBombs;
    private Random random;

    public void init(int height, int width, int numBombs) {
        this.bomb = new boolean[height][width];
        this.height = height;
        this.width = width;
        this.numBombs = numBombs;
        if (height * width < numBombs) this.numBombs = height * width;
        this.random = new Random();
    }

    public void generateBombs() {
        firstClick = true;
        if(numBombs > height * width * 0.75) {
            generateBombsListy();
        } else {
            generateBombsGreedy();
        }
    }

    public void generateBombsGreedy() {
        this.bomb = new boolean[height][width];
        for (int i = 0; i < numBombs; i++) {
            int y = random.nextInt(height);
            int x = random.nextInt(width);
            if (bomb[y][x]) {
                i--;
                continue;
            }
            bomb[y][x] = true;
        }
    }

    public void generateBombsListy() {
        List<Integer> pos = new ArrayList<>();
        for (int i = 0; i < height * width; i++) {
            pos.add(i);
        }
        for (int i = 0; i < numBombs; i++) {
            int p = pos.get(random.nextInt(pos.size()));
            int y = p / width;
            int x = p % width;
            if (bomb[y][x]) {
                i--;
                continue;
            }
            bomb[y][x] = true;
        }
    }

    public void setSeed(long seed) {
        this.random = new Random(seed);
    }

    public boolean isBomb(int y, int x) {
        return bomb[y][x];
    }

    public int getNumber(int y, int x) {
        int num = 0;
        for(int iy = (y>0?-1:0); iy < (y<height-1?2:1); iy++) {
            for(int ix = (x>0?-1:0); ix < (x<width-1?2:1); ix++) {
                if(bomb[y+iy][x+ix]) num++;
            }
        }
        return num;
    }
}
