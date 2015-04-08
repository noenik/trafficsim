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
    private float xEnd;
    private float yEnd;
    private float tx;
    private float ty;
    private float f;
    private float a;
    private float amount;
    private final PImage model;
    private final PGraphics mg;
    private final Random rand;
    private final ArrayList<Square> grid;
    private ArrayList<Square> currentOccupied;
    private ArrayList<Float> endCurve;

    protected ArrayList<Square> fieldOfView;

    private final HashMap<Direction, ArrayList<Float>> paths;
    private final HashMap<Direction, Direction> map;

    protected Direction heading;
    protected Direction path;

    public Vehicle(String modelUrl, ArrayList<Square> grid, Random rand) {

        init();
        this.rand = rand;
        this.grid = grid;
        model = loadImage(modelUrl);
        model.resize(0, 100);

        mg = createGraphics(200, 200);

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

    private void initiate() {
        mg.beginDraw();

        int randomInt = rand.nextInt(4);

        mg.imageMode(CENTER);
        mg.translate(mg.width / 2, mg.height / 2);

        switch (randomInt) {

            case 0:
                x = -100;
                y = 525;
                xEnd = width;
                yEnd = 525;
                setOrientation(Direction.EAST, true);
                break;
            case 1:
                x = 425;
                y = -100;
                xEnd = 425;
                yEnd = height;
                setOrientation(Direction.SOUTH, true);
                break;
            case 2:
                x = 1100;
                y = 475;
                xEnd = 0;
                yEnd = 475;
                setOrientation(Direction.WEST, true);
                break;
            case 3:
                x = 525;
                y = 1100;
                xEnd = 525;
                yEnd = 0;
                setOrientation(Direction.NORTH, true);

        }

        mg.image(model, 0, 0);
        mg.endDraw();

        randomPathFrom(heading);
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
            mg.rotate(amount);
        }
    }

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

    boolean turn = false;
    boolean end = false;

    public void drive(float amountX, float amountY) {

        float distFromCenter = dist(500, 500, x, y);

        if (distFromCenter < 200 && distFromCenter > 125 || turn) {
            turn = true;

            mg.beginDraw();
            mg.imageMode(CENTER);
            mg.translate(mg.width / 2, mg.height / 2);
            mg.background(0, 0);

            if (!driveThroughCurve(paths.get(path))) {
                setOrientation(map.get(path), true);
                end = true;
                turn = false;
            } else {
                mg.rotate(a);
            }

            mg.image(model, 0, 0);
            mg.endDraw();

        } else if (end) {
            f = 0;
            
            mg.beginDraw();
            mg.imageMode(CENTER);
            mg.translate(mg.width / 2, mg.height / 2);
            mg.background(0, 0);
            
            if(!driveThroughCurve(endCurve)) {
                end = false;
            } else {
                mg.rotate(a);
            }
            
            mg.image(model, 0, 0);
            mg.endDraw();
            
        } else {
            f = 0;
            x += amountX;
            y += amountY;
        }
    }

    private boolean driveThroughCurve(ArrayList<Float> points) {

        float endX = 0;
        float endY = 0;
        
        if (f < 1) {
            float startX = points.get(0);
            float startY = points.get(1);
            float c1X = points.get(2);
            float c1Y = points.get(3);
            float c2X = points.get(4);
            float c2Y = points.get(5);
            endX = points.get(6);
            endY = points.get(7);

            x = bezierPoint(startX, c1X, c2X, endX, f);
            y = bezierPoint(startY, c1Y, c2Y, endY, f);
            tx = bezierTangent(startX, c1X, c2X, endX, f);
            ty = bezierTangent(startY, c1Y, c2Y, endY, f);

            a = atan2(ty, tx);

            f += 0.01;

            return true;

        } else {
            makeEndCurve(endX, endY);
            return false;
        }

    }

    private void makeEndCurve(float pointX, float pointY) {

        float space = dist(pointX, pointY, xEnd, yEnd) / 3;
        endCurve.add(pointX);
        endCurve.add(pointY);

        if (heading == Direction.EAST) {
            
            endCurve.add(pointX + space);
            endCurve.add(yEnd + (rand.nextInt(10) - 5));
            endCurve.add(pointX + (2 * space));
            endCurve.add(yEnd + (rand.nextInt(10) - 5));
            
        } else if (heading == Direction.WEST) {

            endCurve.add(pointX - space);
            endCurve.add(yEnd + (rand.nextInt(10) - 5));
            endCurve.add(pointX - (2 * space));
            endCurve.add(yEnd + (rand.nextInt(10) - 5));
            
        } else if (heading == Direction.NORTH) {
            
            endCurve.add(xEnd + (rand.nextInt(10) - 5));
            endCurve.add(pointY - space);
            endCurve.add(xEnd + (rand.nextInt(10) - 5));
            endCurve.add(pointY - (2 * space));

        } else if (heading == Direction.SOUTH) {
            
            endCurve.add(xEnd + (rand.nextInt(10) - 5));
            endCurve.add(pointY + space);
            endCurve.add(xEnd + (rand.nextInt(10) - 5));
            endCurve.add(pointY + (2 * space));

        }

        endCurve.add(xEnd);
        endCurve.add(yEnd);

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
