package TrafficSim;

import java.util.ArrayList;
import java.util.Random;
import processing.core.*;

/**
 *
 * @author nikla_000
 */
public class Simulator extends PApplet {

    ArrayList<Vehicle> vehicles = new ArrayList<>();
    ArrayList<Vehicle> vehiclesOut = new ArrayList<>();

    Random rand = new Random();
    Landscape landscape;
    PImage landscapeImage;
    PImage car;
    float x = 0;
    float y = 500;

    @Override
    public void setup() {
        size(1000, 1000);
        car = loadImage("graphics/bil1.png");
        car.resize(100, 0);

        vehicles.add(new Car(landscape.getGrid(), rand));
//        noLoop();
    }

    @Override
    public void draw() {
        background(landscape.getLandscape());
        noFill();
        if (fl.size() >= 8) {
            bezier(fl.get(0), fl.get(1), fl.get(2), fl.get(3), fl.get(4), fl.get(5), fl.get(6), fl.get(7));
        }

        imageMode(CENTER);

        for (Square s : landscape.getGrid()) {
            s.setOccupant(null);
        }

        for (Vehicle v : vehicles) {
            fill(255, 0, 0);
            image(v.getModel(), v.getXCoord(), v.getYCoord());
//            for(Square s : v.getOccupied())
//                rect(s.getxStart(), s.getyStart(), 10, 10);
            v.act();

            if (v.getXCoord() > width + 200 || v.getXCoord() < 200
                    || v.getYCoord() > height + 200 || v.getYCoord() < -200) {
                vehiclesOut.add(v);
            }

        }

        for (Vehicle out : vehiclesOut) {

            if (vehicles.contains(out)) {
                vehicles.remove(out);
            }

        }

        vehiclesOut.clear();

    }

    public void setLandscape(Landscape landscape) {

        this.landscape = landscape;

    }

    int mouseClicks = 0;
    ArrayList<Integer> fl = new ArrayList<>();

    public void mousePressed() {

//        if (mouseClicks < 4) {
//            System.out.println("X: " + mouseX + " Y: " + mouseY);
//            fl.add(mouseX);
//            fl.add(mouseY);
//        } else {
//            fl.clear();
//            mouseClicks = 0;
//        }
        vehicles.add(new Car(landscape.getGrid(), rand));
    }

}
