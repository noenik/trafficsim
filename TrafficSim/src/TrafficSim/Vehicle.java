package TrafficSim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import processing.core.PApplet;
import static processing.core.PApplet.atan2;
import static processing.core.PApplet.dist;
import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.HALF_PI;
import static processing.core.PConstants.PI;
import processing.core.PGraphics;
import processing.core.PImage;

/**
 *
 * @author nikla_000
 */
public abstract class Vehicle extends PApplet {

    private float x;
    private float y;
    private float xEnd;
    private float yEnd;
    private float tx;
    private float ty;
    private float f;
    private float a;
    private float amount;
    private float speed;
    private boolean inRoundabout;
    private boolean turn = false;
    private boolean end = false;
    private final PImage model;
    private final PGraphics mg;
    private final Random rand;
    private final ArrayList<Square> grid;
    private ArrayList<Square> currentOccupied;
    private ArrayList<Float> endCurve;
    private final ArrayList<Crossing> crossings;
    protected ArrayList<Square> fieldOfView;

    private final HashMap<Direction, ArrayList<Float>> paths;
    private final HashMap<Direction, Direction> map;

    protected Direction heading;
    protected Direction path;


    /**
     * Constructor
     * @param modelUrl
     * @param grid
     * @param rand 
     */
    public Vehicle(String modelUrl, ArrayList<Square> grid, Random rand, float speed, ArrayList<Crossing> crossings) {

        init();
        this.rand = rand;
        this.grid = grid;
        this.speed = speed;
        model = loadImage(modelUrl);
        model.resize(0, 100);
        this.crossings = crossings;
        model.resize(0, 70);

        mg = createGraphics(125, 125);

        currentOccupied = new ArrayList<>();
        fieldOfView = new ArrayList<>();
        endCurve = new ArrayList<>();
        paths = new HashMap<>();
        map = new HashMap<>();

        fillPaths();
        fillHeading();
        initiate();
        setOrientation(map.get(path), false);

    }
    
    private void drawGraphic(Float rotation) {
        mg.beginDraw();
        mg.background(0, 0);
        mg.imageMode(CENTER);
        mg.translate(mg.width / 2, mg.height / 2);
        mg.rotate(rotation);
        mg.image(model, 0, 0);
        mg.endDraw();
    }

    /**
     * Sets the initial values and orientation.
     */
    private void initiate() {

        int randomInt = rand.nextInt(4);

        switch (randomInt) {

            case 0:
                x = -200;
                y = 532;
                setOrientation(Direction.EAST, true);
                break;
            case 1:
                x = 468;
                y = -200;
                setOrientation(Direction.SOUTH, true);
                break;
            case 2:
                x = 1200;
                y = 468;
                setOrientation(Direction.WEST, true);
                break;
            case 3:
                x = 532;
                y = 1200;
                setOrientation(Direction.NORTH, true);

        }

        randomPathFrom(heading);
//        if(heading == Direction.WEST)
//            zToPi = (float) (Math.PI/2);
//        else if(heading == Direction.SOUTH)
//            zToPi = (float) (Math.PI);
//        else if(heading == Direction.EAST)
//            zToPi = (float) (3*(Math.PI/2));
    }

    /**
     * Sets the heading field and rotates the image to point the correct way.
     * @param dir
     * @param rotate 
     */
    private void setOrientation(Direction dir, boolean rotate) {
        amount = 0;


        if (dir == Direction.EAST){
            xEnd = 1200;
            yEnd = 532;
        } else if (dir == Direction.WEST) {
            amount = PI;
            xEnd = -200;
            yEnd = 468;
        } else if (dir == Direction.NORTH) {
            amount = HALF_PI * 3;
            xEnd = 532;
            yEnd = -200;
        } else if (dir == Direction.SOUTH) {
            amount = HALF_PI;
            xEnd = 468;
            yEnd = 1200;
        }

        if (rotate) {
            heading = dir;
            drawGraphic(amount);
        }
    }

    /**
     * Assigns a random path to spawned vehicle.
     * @param dir 
     */
    private void randomPathFrom(Direction dir) {

        int randomNum = rand.nextInt(3);

        if (dir == Direction.EAST) {

            switch (randomNum) {
                case 0:
                    path = Direction.WESTTONORTH;
                    break;
                case 1:
                    path = Direction.WESTTOSOUTH;
                    break;
                case 2:
                    path = Direction.WESTTOEAST;
            }

        } else if (dir == Direction.NORTH) {

            switch (randomNum) {
                case 0:
                    path = Direction.SOUTHTONORTH;
                    break;
                case 1:
                    path = Direction.SOUTHTOEAST;
                    break;
                case 2:
                    path = Direction.SOUTHTOWEST;
            }

        } else if (dir == Direction.SOUTH) {

            switch (randomNum) {
                case 0:
                    path = Direction.NORTHTOEAST;
                    break;
                case 1:
                    path = Direction.NORTHTOSOUTH;
                    break;
                case 2:
                    path = Direction.NORTHTOWEST;
            }

        } else if (dir == Direction.WEST) {

            switch (randomNum) {
                case 0:
                    path = Direction.EASTTONORTH;
                    break;
                case 1:
                    path = Direction.EASTTOSOUTH;
                    break;
                case 2:
                    path = Direction.EASTTOWEST;
            }

        }

    }

    /**
     * Fills the hash map with the paths and the associated bezier array.
     */
    private void fillPaths() {

        paths.put(Direction.EASTTONORTH, makeArray(703, 464, 620, 423, 540, 365, 530, 292));
        paths.put(Direction.EASTTOSOUTH, makeArray(701, 462, 458, 305, 372, 507, 459, 711));
        paths.put(Direction.EASTTOWEST, makeArray(701, 465, 458, 153, 358, 463, 282, 462));
        paths.put(Direction.NORTHTOEAST, makeArray(455, 304, 327, 642, 572, 617, 698, 542));
        paths.put(Direction.NORTHTOSOUTH, makeArray(469, 297, 171, 534, 482, 624, 459, 706));
        paths.put(Direction.NORTHTOWEST, makeArray(462, 301, 450, 387, 385, 453, 299, 464));
        paths.put(Direction.SOUTHTOEAST, makeArray(535, 703, 550, 613, 610, 556, 710, 537));
        paths.put(Direction.SOUTHTONORTH, makeArray(535, 699, 801, 401, 543, 402, 537, 287));
        paths.put(Direction.SOUTHTOWEST, makeArray(526, 697, 847, 498, 501, 157, 297, 470));
        paths.put(Direction.WESTTOSOUTH, makeArray(303, 537, 354, 542, 444, 645, 462, 695));
        paths.put(Direction.WESTTOEAST, makeArray(304, 534, 500, 812, 624, 560, 698, 536));
        paths.put(Direction.WESTTONORTH, makeArray(301, 532, 678, 686, 638, 412, 534, 304));

    }

    /**
     * Maps the final heading to the path.
     */
    private void fillHeading() {
        map.put(Direction.EASTTONORTH, Direction.NORTH);
        map.put(Direction.WESTTONORTH, Direction.NORTH);
        map.put(Direction.SOUTHTONORTH, Direction.NORTH);
        map.put(Direction.EASTTOSOUTH, Direction.SOUTH);
        map.put(Direction.NORTHTOSOUTH, Direction.SOUTH);
        map.put(Direction.WESTTOSOUTH, Direction.SOUTH);
        map.put(Direction.EASTTOWEST, Direction.WEST);
        map.put(Direction.NORTHTOWEST, Direction.WEST);
        map.put(Direction.SOUTHTOWEST, Direction.WEST);
        map.put(Direction.NORTHTOEAST, Direction.EAST);
        map.put(Direction.SOUTHTOEAST, Direction.EAST);
        map.put(Direction.WESTTOEAST, Direction.EAST);

    }

    /**
     * Makes and returns an arraylist of float values for bezier.
     * @param startX
     * @param startY
     * @param c1X
     * @param c1Y
     * @param c2X
     * @param c2Y
     * @param endX
     * @param endY
     * @return 
     */
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


    protected boolean checkForCrossings() {
        boolean atCrossing = false;
        int cID = 4;
        if (x > 60 && x < 100 && heading == Direction.EAST) {
            atCrossing = true;
            cID = 0;
        }
        if (x > 240 && x < 300 && heading == Direction.WEST) {
            atCrossing = true;
            cID = 0;
        }
        if (x > 940 && x < 960 && heading == Direction.WEST) {
            atCrossing = true;
            cID = 1;
        }
        if (x > 700 && x < 760 && heading == Direction.EAST) {
            atCrossing = true;
            cID = 1;
        }
        if (y > 60 && y < 100 && heading == Direction.SOUTH) {
            atCrossing = true;
            cID = 2;
        }
        if (y > 240 && y < 300 && heading == Direction.NORTH) {
            atCrossing = true;
            cID = 2;
        }
        if (y > 950 && y < 980 && heading == Direction.NORTH) {
            atCrossing = true;
            cID = 3;
        }
        if (y > 700 && y < 760 && heading == Direction.SOUTH) {
            atCrossing = true;
            cID = 3;
        }
        if (atCrossing == true) {
            for (Crossing c : crossings) {
                if (c.getID() == cID) {
                    if (c.getNumberOfOccupants() > 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    /**
     * Finds and assigns a field of view for the vehicle.
     */

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
                if (positive && s.getxStart() > x && s.getyStart() < y) {
                    fieldOfView.add(s);
                } else if (!positive && s.getxStart() < x && s.getyStart() > y) {
                    fieldOfView.add(s);
                }
            } else {
                if (positive && s.getyStart() > y && s.getxStart() > x) {
                    fieldOfView.add(s);
                } else if (!positive && s.getyStart() < y && s.getxStart() < x) {
                    fieldOfView.add(s);
                }
            }
        }

    }

    /**
     * Finds and assigns the portion of the grid that the vehicle is currently occupying.
     */
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

    /**
     * Determines whether or not an objects is at a safe distance. 
     * @param s
     * @return 
     */
    protected boolean safeDistance(Square s) {

        float distance = dist(x, y, s.getxStart(), s.getyStart());
        Vehicle occupant = s.getOccupant();
        
        if(!inRoundabout && occupant.inRoundabout() && distance < (150 + (rand.nextInt(40) - 20))) {
            return false;
        } else if(inRoundabout) {
            return true;
        }else
            return (distance > 30 && distance > model.width) || headingOpposite(occupant.getHeading());

    }

    /**
     * Determines whether or not an object is heading at the opposite direction.
     * @param dir
     * @return 
     */
    private boolean headingOpposite(Direction dir) {

        return (heading == Direction.EAST && dir == Direction.WEST)
                || (heading == Direction.NORTH && dir == Direction.SOUTH)
                || (heading == Direction.WEST && dir == Direction.EAST)
                || (heading == Direction.SOUTH && dir == Direction.NORTH);

    }

    /**
     * Act method. Must be implemented for all vehicles.
     */
    public abstract void act();


    /**
     * Makes the vehicle move.
     * @param amountX
     * @param amountY 
     */
    public void drive(float amountX, float amountY) {

        float distFromCenter = dist(500, 500, x, y);

        if (!end && distFromCenter < 250 && distFromCenter > 125 || turn) {
            turn = true;

            if (!driveThroughCurve(paths.get(path), speed)) {
                setOrientation(map.get(path), true);
                makeEndCurve(paths.get(path).get(6), paths.get(path).get(7));
                f = 0;
                inRoundabout = false;
                end = true;
                turn = false;
            } else {
                inRoundabout = true;
                speed += 0.001;
//                drawGraphic(a);
            }

        } else if (end) {
            
            if (!driveThroughCurve(endCurve, 0.05f)) {
                end = false;
            } else {
//                drawGraphic(a);
            }

        } else {
            f = 0;
            x += amountX;
            y += amountY;
        }
    }

    /**
     * Moves the vehicle through the proper bezier curve through the roundabout.
     * @param points
     * @param speed
     * @return 
     */
    private boolean driveThroughCurve(ArrayList<Float> points, float speed) {
        
        if (f < 1) {
            float startX = points.get(0);
            float startY = points.get(1);
            float c1X = points.get(2) + 2*rand.nextFloat() - 1;
            float c1Y = points.get(3) + 2*rand.nextFloat() - 1;
            float c2X = points.get(4) + 2*rand.nextFloat() - 1;
            float c2Y = points.get(5) + 2*rand.nextFloat() - 1;
            float endX = points.get(6);
            float endY = points.get(7);
            
            if(dist(startX, startY, endX, endY) < 250)
                speed += speed*2;
                        
            x = bezierPoint(startX, c1X, c2X, endX, f);
            y = bezierPoint(startY, c1Y, c2Y, endY, f);
            tx = bezierTangent(startX, c1X, c2X, endX, f);
            ty = bezierTangent(startY, c1Y, c2Y, endY, f);

            a = atan2(ty, tx);

            f += speed;

            return true;

        } else {
            return false;
        }

    }

    /**
     * Creates a bezier curve from the end point of the roundabouts path and to
     * the end of the road.
     * @param pointX
     * @param pointY 
     */
    private void makeEndCurve(float pointX, float pointY) {
        
        float space = dist(pointX, pointY, xEnd, yEnd) / 3;
        endCurve.add(pointX);
        endCurve.add(pointY);

        if (heading == Direction.EAST) {

            endCurve.add(pointX + space);
            endCurve.add(yEnd + (rand.nextInt(14) - 7));
            endCurve.add(pointX + (2 * space));
            endCurve.add(yEnd + (rand.nextInt(14) - 7));

        } else if (heading == Direction.WEST) {

            endCurve.add(pointX - space);
            endCurve.add(yEnd + (rand.nextInt(14) - 7));
            endCurve.add(pointX - (2 * space));
            endCurve.add(yEnd + (rand.nextInt(14) - 7));

        } else if (heading == Direction.NORTH) {

            endCurve.add(xEnd + (rand.nextInt(14) - 7));
            endCurve.add(pointY - space);
            endCurve.add(xEnd + (rand.nextInt(14) - 7));
            endCurve.add(pointY - (2 * space));

        } else if (heading == Direction.SOUTH) {

            endCurve.add(xEnd + (rand.nextInt(14) - 7));
            endCurve.add(pointY + space);
            endCurve.add(xEnd + (rand.nextInt(14) - 7));
            endCurve.add(pointY + (2 * space));

        }

        endCurve.add(xEnd);
        endCurve.add(yEnd);

    }
    
    public boolean inRoundabout() {
        return inRoundabout;
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

    public ArrayList<Float> getCurve() {
        return endCurve;
    }
}
