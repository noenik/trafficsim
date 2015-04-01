package TrafficSim;

import processing.core.*;

/**
 *
 * @author nikla_000
 */
public class Simulator extends PApplet {

    Landscape landscape;
    PImage landscapeImage;
    PImage car;
    float x = 0;

    @Override
    public void setup() {
        size(1000, 1000);
        car = loadImage("graphics/bil1.png");
        car.resize(100, 0);
//        noLoop();
    }

    @Override
    public void draw() {
        image(landscape.getLandscape(), 0, 0);

        image(car, x, 500);

        if (x > 1100) {
            x = 0;
        } else {
            x++;
        }
    }

    public void setLandscape(Landscape landscape) {

        this.landscape = landscape;

    }

}
