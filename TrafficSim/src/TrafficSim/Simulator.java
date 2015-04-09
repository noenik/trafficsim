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

    ArrayList<Person> persons = new ArrayList<>();
    ArrayList<Person> personsOut = new ArrayList<>();

    Random rand = new Random();
    Landscape landscape;
    PImage landscapeImage;
    PImage car;
    float x = 0;
    float y = 500;
    int currentFr = 25;

    int vehicleRate = 2;
    int peopleRate = 2;

    @Override
    public void setup() {
        size(1000, 1000);
        car = loadImage("graphics/bil1.png");
        car.resize(100, 0);

        frameRate(currentFr);

//        vehicles.add(new Car(landscape.getGrid(), rand));
//        noLoop();
    }

    @Override
    public void draw() {
        if (frameCount % currentFr * 2 == 0) {
            vehicles.add(new Car(landscape.getGrid(), rand));
            System.out.println("lel");
        }

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
            //line(width/2, height/2, v.getXCoord(), v.getYCoord());
//            for(Square s : v.getOccupied())
//                rect(s.getxStart(), s.getyStart(), 10, 10);
            v.act();

            if (v.getXCoord() > width + 200 || v.getXCoord() < -200
                    || v.getYCoord() > height + 200 || v.getYCoord() < -200) {
                vehiclesOut.add(v);
            }

        }

        for (Person p : persons) {
            fill(255, 0, 0);
            image(p.getSprite(), p.getXCoord(), p.getYCoord());
            p.act();

            if (p.getXCoord() > width + 40 || p.getXCoord() < -40
                    || p.getYCoord() > height + 40 || p.getYCoord() < -40) {
                personsOut.add(p);
            }
        }

        for (Vehicle out : vehiclesOut) {

            if (vehicles.contains(out)) {
                vehicles.remove(out);
            }

        }
        for (Person out : personsOut) {

            if (persons.contains(out)) {
                persons.remove(out);
            }

        }

        vehiclesOut.clear();
        personsOut.clear();

    }

    public void setLandscape(Landscape landscape) {

        this.landscape = landscape;

    }

    public void setVehicleRate(int rate) {
        vehicleRate = rate;
    }
    
    public void setPeopleRate(int rate) {
        peopleRate = rate;
    }

    int mouseClicks = 0;
    ArrayList<Integer> fl = new ArrayList<>();

    public void mousePressed() {

      //  if (mouseClicks < 4) {
        //       System.out.print(mouseX + ", " + mouseY + ", ");
        //       fl.add(mouseX);
        //       fl.add(mouseY);
        //   } else {
        //       fl.clear();
        //       mouseClicks = 0;
        // }
        vehicles.add(new Car(landscape.getGrid(), rand));
        persons.add(new Person(rand));
    }

}
