package TrafficSim;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import processing.core.*;

/**
 *
 * @author nikla_000
 */
public class Simulator extends PApplet implements ActionListener{

    ArrayList<Vehicle> vehicles = new ArrayList<>();
    ArrayList<Vehicle> vehiclesOut = new ArrayList<>();
    
    ArrayList<Person> persons = new ArrayList<>();
    ArrayList<Person> personsOut = new ArrayList<>();

    
    ArrayList<Crossing> crossings = new ArrayList<>();
    Random rand = new Random();
    Landscape landscape;
    PImage landscapeImage;
    PImage car;
    float x = 0;
    float y = 500;
    int currentFr = 25;
    int vehicleRate = 2;
    int peopleRate = 5;
    int vehicleOutCount;

    @Override
    public void setup() {
        size(1000, 1000);
        car = loadImage("graphics/bil1.png");
        car.resize(100, 0);
        frameRate(currentFr);
        landscapeImage = landscape.getLandscape();
        crossings = landscape.getCrossings();
        
//        vehicles.add(new Car(landscape.getGrid(), rand));
//        noLoop();
    }

    @Override
    public void draw() {

        if (frameCount % currentFr * vehicleRate == 0) {
            vehicles.add(new Car(landscape.getGrid(), rand));
        }
        
        background(landscape.getLandscape());
        
        imageMode(CENTER);

        for (Square s : landscape.getGrid()) {
            s.setOccupant(null);
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
        
        for (Vehicle v : vehicles) {
            fill(255, 0, 0);
            image(v.getModel(), v.getXCoord(), v.getYCoord());
            //line(width/2, height/2, v.getXCoord(), v.getYCoord());
//            for(Square s : v.getFOV())
//                rect(s.getxStart(), s.getyStart(), 10, 10);
            v.act();

            if (v.getXCoord() > width + 200 || v.getXCoord() < -200
                    || v.getYCoord() > height + 200 || v.getYCoord() < -200) {
                vehiclesOut.add(v);
            }
        }
        
        for(Crossing c : crossings) {
            System.out.println("occu: " + c.getNumberOfOccupants());
            c.removeAll();
        }
        
        for (Vehicle out : vehiclesOut) {

            if (vehicles.contains(out)) {
                vehicleOutCount++;
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

    public void setLandscape(Landscape landscapex) {

        this.landscape = landscapex;

    }
    
    public void setVehicleRate(int rate) {
        vehicleRate = rate;
    }
    
    public void setPeopleRate(int rate) {
        peopleRate = rate;
    }
    
    public int getVehicleCount() {
        return vehicleOutCount;
    }
    
    public int getTime() {
        return frameCount % currentFr;
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
        persons.add(new Person(crossings, rand));
        Person p = new Person(crossings, rand);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Start":
                loop();
                System.out.println("s");
                break;
            case "Stop":
                noLoop();
                break;
            case "Resume":
                noLoop();
                break;
}
    }
}
