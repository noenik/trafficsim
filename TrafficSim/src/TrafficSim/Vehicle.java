package TrafficSim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

/**
 *
 * @author nikla_000
 */
public abstract class Vehicle extends PApplet {

    private float x;
    private float y;
    private float f;
    private final PImage model;
    private final PGraphics mg;
    private final Random rand;
    private final ArrayList<Square> grid;
    private ArrayList<Square> currentOccupied;
    protected ArrayList<Square> fieldOfView;
    
    private final HashMap<Direction, ArrayList<Float>> paths;

    protected Direction heading;

    public Vehicle(String modelUrl, ArrayList<Square> grid, Random rand) {

        init();
        this.rand = rand;
        this.grid = grid;
        model = loadImage(modelUrl);
        model.resize(0, 100);

        mg = createGraphics(model.width, model.width);

        currentOccupied = new ArrayList<>();
        fieldOfView = new ArrayList<>();
        paths = new HashMap<>();
        
        fillPaths();

        initiate();

    }

    private void initiate() {
        mg.beginDraw();

        int randomInt = rand.nextInt(4);

        mg.imageMode(CENTER);
        mg.translate(width / 2, height / 2);

        switch (randomInt) {

            case 0:
                x = -100;
                y = 525;
                heading = Direction.EAST;
                break;
            case 1:
                x = 425;
                y = -100;
                heading = Direction.SOUTH;
                mg.rotate(HALF_PI);
                break;
            case 2:
                x = 1100;
                y = 475;
                heading = Direction.WEST;
                mg.rotate(PI);
                break;
            case 3:
                x = 525;
                y = 1100;
                heading = Direction.NORTH;
                mg.rotate(HALF_PI * 3);

        }

        mg.image(model, 0, 0);
        mg.endDraw();

//        if(heading == Direction.WEST)
//            zToPi = (float) (Math.PI/2);
//        else if(heading == Direction.SOUTH)
//            zToPi = (float) (Math.PI);
//        else if(heading == Direction.EAST)
//            zToPi = (float) (3*(Math.PI/2));
    }
    
    private void fillPaths() {
        
        paths.put(Direction.EASTTONORTH, new ArrayList<>());
        paths.put(Direction.EASTTOSOUTH, makeArray(701, 462, 458, 305, 372, 507, 459, 711));
        paths.put(Direction.EASTTOWEST, makeArray(701, 465, 458, 153, 358, 463, 282, 462));
        paths.put(Direction.NORTHTOEAST, new ArrayList<>());
        paths.put(Direction.NORTHTOSOUTH, new ArrayList<>());
        paths.put(Direction.NORTHTOWEST, new ArrayList<>());
        paths.put(Direction.SOUTHTOEAST, new ArrayList<>());
        paths.put(Direction.SOUTHTONORTH, new ArrayList<>());
        paths.put(Direction.SOUTHTOWEST, new ArrayList<>());
        paths.put(Direction.WESTSOUTH, new ArrayList<>());
        paths.put(Direction.WESTTOEAST, new ArrayList<>());
        paths.put(Direction.WESTTONORTH, new ArrayList<>());
                
    }
    
    private ArrayList<Float> makeArray(float startX, float startY, float c1X,
            float c1Y, float c2X, float c2Y, float endX, float endY) {
        
        ArrayList<Float> list = new ArrayList<>();
        
        list.add(startX);
        list.add(startY);
        list.add(c1X);
        list.add(c1Y);
        list.add(c2X);
        list.add(c2Y);
        list.add(endX);
        list.add(endY);
        
        return list;
        
    }

    protected void findFieldOfView() {

        boolean horizontal = false;
        boolean positive = false;

        fieldOfView.clear();

        if (heading == Direction.EAST) {
            horizontal = true;
            positive = true;
        } else if (heading == Direction.NORTH) {
            horizontal = false;
            positive = false;
        } else if (heading == Direction.SOUTH) {
            horizontal = false;
            positive = true;
        } else if (heading == Direction.WEST) {
            horizontal = true;
            positive = false;
        }

        for (Square s : grid) {
            if (horizontal) {
                if (positive && s.getxStart() > x) {
                    fieldOfView.add(s);
                } else if (!positive && s.getxStart() < x) {
                    fieldOfView.add(s);
                }
            } else {
                if (positive && s.getyStart() > y) {
                    fieldOfView.add(s);
                } else if (!positive && s.getyStart() < y) {
                    fieldOfView.add(s);
                }
            }
        }

    }

    protected void findOccupied() {
        currentOccupied.clear();
        for (Square s : grid) {
            boolean xMax = (s.getxStart() + 5) < (x + (model.width / 2));
            boolean xMin = (s.getxStart() + 5) > (x - (model.width / 2));
            boolean yMax = (s.getyStart() + 5) < (y + (model.height / 2));
            boolean yMin = (s.getyStart() + 5) > (y - (model.height / 2));

            if (xMax && xMin && yMax && yMin) {
                currentOccupied.add(s);
                s.setOccupant(this);
            } else if (s.getOccupant() == this) {
                s.setOccupant(null);
            }

        }

    }

    protected boolean safeDistance(Square s) {

        float distance = dist(x, y, s.getxStart(), s.getyStart());

        return (distance > 100 && distance > model.width) || headingOpposite(s.getOccupant().getHeading());

    }

    private boolean headingOpposite(Direction dir) {

        return (heading == Direction.EAST && dir == Direction.WEST)
                || (heading == Direction.NORTH && dir == Direction.SOUTH)
                || (heading == Direction.WEST && dir == Direction.EAST)
                || (heading == Direction.SOUTH && dir == Direction.NORTH);

    }

    public abstract void act();

    boolean tuuuuurn = false;

    public void drive(float amountX, float amountY) {

        float distFromCenter = dist(500, 500, x, y);

        if (distFromCenter < 200 && distFromCenter > 125 || tuuuuurn) {
            tuuuuurn = true;
            
//            driveThroughCurve();
            driveThroughCurve(Direction.EASTTOWEST);
            
//            driveThroughCurve(Direction.EASTTOSOUTH);
            
            
            
            
            
//            driveThroughCurve(701, 462, 460, 291, 307, 528, 457, 697);
            

            if (f > 1) {
                tuuuuurn = false;
            }

        } else {
            f = 0;
            x += amountX;
            y += amountY;
        }

    }

    public void driveThroughCurve(Direction dir) {

        ArrayList<Float> points = paths.get(dir);
        
        float startX = points.get(0);
        float startY = points.get(1);
        float c1X = points.get(2);
        float c1Y = points.get(3);
        float c2X = points.get(4);
        float c2Y = points.get(5);
        float endX = points.get(6);
        float endY = points.get(7);
        
        x = bezierPoint(startX, c1X, c2X, endX, f);
        y = bezierPoint(startY, c1Y, c2Y, endY, f);
        
        f += 0.01;

    }

    public Direction getHeading() {
        return heading;
    }

    public float getXCoord() {
        return x;
    }

    public float getYCoord() {
        return y;
    }

    public PImage getModel() {
        return mg.get();
    }

    public ArrayList<Square> getOccupied() {
        return currentOccupied;
    }

    public ArrayList<Square> getFOV() {
        return fieldOfView;
    }
}
