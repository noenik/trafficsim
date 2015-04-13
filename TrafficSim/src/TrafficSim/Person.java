package TrafficSim;

import java.util.ArrayList;
import java.util.Random;
import processing.core.PApplet;
import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.HALF_PI;
import static processing.core.PConstants.PI;
import processing.core.PGraphics;
import processing.core.PImage;

/**
 *
 * @author nikla_000
 */
public class Person extends PApplet {

    private final PImage sprite1;
    private final PImage sprite2;
    private final PImage sprite3;
    private final PImage sprite4;
    private float xCoord;
    private float yCoord;
    private float xStart;
    private float yStart;
    private float xEnd;
    private float yEnd;
    private float amount;
    private int actCount;
    private final PGraphics mp;
    private final Random rand;
    private final ArrayList<Crossing> crossings;
    protected Direction heading;
    private boolean inCrossing;

    public Person(ArrayList<Crossing> crossingsX, Random rand) {
        init();
        mp = createGraphics(200, 200);
        sprite1 = loadImage("graphics/sprite1.png");
        sprite2 = loadImage("graphics/sprite2.png");
        sprite3 = loadImage("graphics/sprite3.png");
        sprite4 = loadImage("graphics/sprite4.png");
        sprite1.resize(40, 0);
        sprite2.resize(40, 0);
        sprite3.resize(40, 0);
        actCount = 0;
        xCoord = 20;
        yCoord = 20;
        crossings = crossingsX;
        inCrossing = false;
        this.rand = rand;
        initiate();
    }

    private void initiate() {
        mp.beginDraw();

        int randomInt = rand.nextInt(8);
        

        mp.imageMode(CENTER);
        mp.translate(mp.width / 2, mp.height / 2);

        switch (randomInt) {

            case 0:
                xStart = 0;
                yStart = 200;
                xCoord = 0;
                yCoord = 200;
                xEnd = 2000;
                yEnd = 200;
                setOrientation(Direction.SOUTH, true);
                break;
            case 1:
                xStart = 200;
                yStart = 0;
                xCoord = 200;
                yCoord = 0;
                xEnd = 200;
                yEnd = 2000;
                setOrientation(Direction.WEST, true);
                break;
            case 2:
                xStart = 800;
                yStart = 0;
                xCoord = 800;
                yCoord = 0;
                xEnd = 800;
                yEnd = 2000;
                setOrientation(Direction.WEST, true);
                break;
            case 3:
                xStart = 960;
                yStart = 200;
                xCoord = 960;
                yCoord = 200;
                xEnd = -2000;
                yEnd = 200;
                setOrientation(Direction.NORTH, true);
                break;
            case 4:
                xStart = 960;
                yStart = 800;
                xCoord = 960;
                yCoord = 800;
                xEnd = -2000;
                yEnd = 800;
                setOrientation(Direction.NORTH, true);
                break;
            case 5:
                xStart = 0;
                yStart = 800;
                xCoord = 0;
                yCoord = 800;
                xEnd = 2000;
                yEnd = 800;
                setOrientation(Direction.SOUTH, true);
                break;
            case 6:
                xStart = 200;
                yStart = 960;
                xCoord = 200;
                yCoord = 960;
                xEnd = 200;
                yEnd = -2000;
                setOrientation(Direction.EAST, true);
                break;
            case 7:
                xStart = 800;
                yStart = 960;
                xCoord = 800;
                yCoord = 960;
                xEnd = 800;
                yEnd = -2000;
                setOrientation(Direction.EAST, true);
                break;
        }

        mp.image(sprite1, 0, 0);
        mp.endDraw();

        //randomPathFrom(heading);
//        if(heading == Direction.WEST)
//            zToPi = (float) (Math.PI/2);
//        else if(heading == Direction.SOUTH)
//            zToPi = (float) (Math.PI);
//        else if(heading == Direction.EAST)
//            zToPi = (float) (3*(Math.PI/2));
    }

    private void setOrientation(Direction dir, boolean rotate) {
        amount = 0;

        if (dir == Direction.WEST) {
            amount = PI;
        } else if (dir == Direction.NORTH) {
            amount = HALF_PI * 3;
        } else if (dir == Direction.SOUTH) {
            amount = HALF_PI;
        }

        if (rotate) {
            heading = dir;
            mp.rotate(amount);
        }
    }

    public void act() {
        actCount++;
        if(actCount > 20) {
            mp.clear();
            mp.image(sprite3, 0, 0);
        }
        else if(actCount > 10) {
            mp.clear();
            mp.image(sprite4, 0, 0);
        }
        if (actCount > 30) {
            mp.clear();
            mp.image(sprite1, 0, 0);
            actCount = 0;
        }
        if(xStart > xEnd) {
            xCoord=xCoord-5;
        }
        if(xStart < xEnd) {
            xCoord=xCoord+5;
        }
        if(yStart < yEnd) {
            yCoord=yCoord+5;
        }
        if(yStart > yEnd) {
            yCoord=yCoord-5;
        }
        
        for(Crossing c : crossings){
            if(xCoord >= c.getXStart() && xCoord <= c.getXEnd() && yCoord >= c.getYStart() && yCoord <= c.getYEnd()) {
                c.addOccupant(this);
                //System.out.println("occu: " + c.getNumberOfOccupants());
            }
        }
    }

    public PImage getSprite() {
        /**
        PImage sprite = sprite1;
        if (actCount < 5) {
            sprite = sprite2;
        } else if (actCount < 10) {
            sprite = sprite3;
        }
        return sprite;
        */
        return mp.get();
    }

    public float getXCoord() {
        return xCoord;
    }

    public float getYCoord() {
        return yCoord;
    }
}
