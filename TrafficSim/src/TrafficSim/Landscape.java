package trafficsim;

import java.util.ArrayList;
import processing.core.*;

/**
 *
 * @author nikla_000
 */
public class Landscape extends PApplet {

    ArrayList<Square> grid = new ArrayList<>();

    public void setup() {

        size(1000, 1000);
        background(0, 255, 0);

        noLoop();

    }

    public void draw() {

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

        makeGrid();
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
        for (int x = 0; x < width; x += 10) {
            for (int y = 0; y < height; y += 10) {
                if (get(x+5, y+5) == dir1Color) {
                    grid.add(new Square(x, y, 255));
                } else if (get(x+5, y+5) == dir2Color) {
                    grid.add(new Square(x, y, 200));
                } else if (get(x+5, y+5) == innerRAColor) {
                    grid.add(new Square(x, y, 150));
                } else if (get(x+5, y+5) == outerRAColor) {
                    grid.add(new Square(x, y, 100));
                }
            }
        }
        
        for(Square s : grid) {
            fill(s.getR(), 0, 0);
            rect(s.getxStart(), s.getyStart(), 10, 10);
        }
    }
}
