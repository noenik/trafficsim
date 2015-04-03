package TrafficSim;

import java.util.ArrayList;
import java.util.Random;
import processing.core.PApplet;
import processing.core.PImage;

/**
 *
 * @author nikla_000
 */
public abstract class Vehicle extends PApplet {

    private float x;
    private float y;
    private final PImage model;
    private final Random rand;
    private final ArrayList<Square> grid;
    private ArrayList<Square> currentOccupied;
    protected ArrayList<Square> fieldOfView;

    protected Direction heading;

    public Vehicle(String modelUrl, ArrayList<Square> grid, Random rand) {

        init();
        this.rand = rand;
        this.grid = grid;
        model = loadImage(modelUrl);
        model.resize(0, 100);
        currentOccupied = new ArrayList<>();
        fieldOfView = new ArrayList<>();

        initiate();

    }

    private void initiate() {

        int randomInt = rand.nextInt(4);

        imageMode(CENTER);
        size(model.width, model.width);
        translate(width / 2, height / 2);

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
                rotate(HALF_PI);
                break;
            case 2:
                x = 1100;
                y = 475;
                heading = Direction.WEST;
                rotate(PI);
                break;
            case 3:
                x = 525;
                y = 1100;
                heading = Direction.NORTH;
                rotate(HALF_PI * 3);

        }

        image(model, 0, 0);

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

    public void drive(float amountX, float amountY) {

        x += amountX;
        y += amountY;

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
        return this.get();
    }

    public ArrayList<Square> getOccupied() {
        return currentOccupied;
    }

    public ArrayList<Square> getFOV() {
        return fieldOfView;
    }
}
