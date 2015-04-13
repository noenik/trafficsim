package TrafficSim;

import java.util.ArrayList;
import processing.core.*;
import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.CORNER;
import static processing.core.PConstants.CORNERS;

/**
 *
 * @author nikla_000
 */
public class Landscape extends PApplet {

    ArrayList<Square> grid = new ArrayList<>();
    int gridSize = 10;
    ArrayList<Crossing> crossings = new ArrayList<>();
    boolean isReady = false;
    PImage blood = loadImage("graphics/Untitled.png");

    public void setup() {

        size(1000, 1000);
        background(0, 255, 0);
        fill(200);
        rectMode(CENTER);
        rect(width / 2, (height / 2) - (75 / 2), 1000, 75);
        rect((width / 2) - (75 / 2), height / 2, 75, 1000);

        fill(220);
        rect(width / 2, (height / 2) + (75 / 2), 1000, 75);
        rect((width / 2) + (75 / 2), height / 2, 75, 1000);

        line(width / 2, 0, width / 2, height);
        line(0, height / 2, width, height / 2);

        ellipseMode(CENTER);
        fill(214);
        ellipse(width / 2, height / 2, 400, 400);
        fill(207);
        ellipse(width / 2, height / 2, 250, 250);
        fill(155);
        ellipse(width / 2, height / 2, 100, 100);
        fill(255);
        rect(440, 200, 20, 80);
        rect(470, 200, 20, 80);
        rect(500, 200, 20, 80);
        rect(530, 200, 20, 80);
        rect(560, 200, 20, 80);

        rect(440, 800, 20, 80);
        rect(470, 800, 20, 80);
        rect(500, 800, 20, 80);
        rect(530, 800, 20, 80);
        rect(560, 800, 20, 80);

        rect(200, 440, 80, 20);
        rect(200, 470, 80, 20);
        rect(200, 500, 80, 20);
        rect(200, 530, 80, 20);
        rect(200, 560, 80, 20);

        rect(800, 440, 80, 20);
        rect(800, 470, 80, 20);
        rect(800, 500, 80, 20);
        rect(800, 530, 80, 20);
        rect(800, 560, 80, 20);
        makeGrid();

        makeCrossings();

        //rectMode(CORNERS);
//        for(Crossing c : crossings) {
//            rect(c.getXStart(), c.getYStart(), c.getXEnd(), c.getYEnd());
//        }

        isReady = true;

        noLoop();

    }

    public boolean ready() {
        return isReady;
    }

    public PImage getLandscape() {

        return this.get();

    }

    private void makeGrid() {

        int dir1Color = get(490, 10);
        int dir2Color = get(510, 10);
        int innerRAColor = get(560, 560);
        int outerRAColor = get(620, 620);

        rectMode(CORNER);
        int colNum = 0;
        for (int x = 0; x < width; x += 10) {
            int rowNum = 0;
            for (int y = 0; y < height; y += 10) {
                boolean add = false;

                Direction dir;

                if (get(x + 5, y + 5) == dir1Color) {

                    if (x > 600 && x < 300) {
                        dir = Direction.WEST;
                    } else {
                        dir = Direction.SOUTH;
                    }

                    grid.add(new Square(x, y, colNum, rowNum, gridSize, dir));

                } else if (get(x + 5, y + 5) == dir2Color) {

                    if (x > 600 && x < 400) {
                        dir = Direction.EAST;
                    } else {
                        dir = Direction.NORTH;
                    }

                    grid.add(new Square(x, y, colNum, rowNum, gridSize, dir));

                } else if (get(x + 5, y + 5) == innerRAColor) {
                    grid.add(new Square(x, y, colNum, rowNum, gridSize, Direction.WEST));
                } else if (get(x + 5, y + 5) == outerRAColor) {
                    grid.add(new Square(x, y, colNum, rowNum, gridSize, Direction.WEST));
                }
                rowNum++;
            }
            colNum++;

        }

//        for (Square s : grid) {
//            fill(255, 0, 0);
//            rect(s.getxStart(), s.getyStart(), 10, 10);
//        }
    }

    private void makeCrossings() {
        Crossing crossingWest = new Crossing(160, 400, 240, 600, 0);
        Crossing crossingNorth = new Crossing(400, 160, 600, 240, 2);
        Crossing crossingEast = new Crossing(760, 400, 840, 600, 1);
        Crossing crossingSouth = new Crossing(400, 760, 600, 840, 3);
        crossings.add(crossingWest);
        crossings.add(crossingNorth);
        crossings.add(crossingEast);
        crossings.add(crossingSouth);
    }

    public ArrayList<Square> getGrid() {
        return grid;
    }

    public ArrayList<Crossing> getCrossings() {

        return crossings;
    }
    
    public void drawBlood(float x, float y) {
        fill(255, 0, 0);
        image(blood, x, y);
    }

}
