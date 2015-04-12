package TrafficSim;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author nikla_000
 */
public class Car extends Vehicle {
    
    public Car(ArrayList<Square> grid, Random rand) {
        
        super("graphics/bil1.png", grid, rand);
        
    }
int moved = 0;
    @Override
    public void act() {

        float x = 0;
        float y = 0;
        float speed = 0;

        float distanceFromCenter = dist(getXCoord(), getYCoord(), 500, 500);

        speed = (distanceFromCenter / 100) + rand.nextInt(3) - 1;

        boolean drive = true;
        if (distanceFromCenter > 250 && distanceFromCenter < 270) {
            drive = false;

        }
        
        for (Square s : fieldOfView) {
            if (s.getOccupant() != null && !safeDistance(s)) {
                drive = false;
                break;
            } else if(s.getOccupant() != null && safeDistance(s) && distanceFromCenter < 270) {
                drive = true;
                 speed = 10;
            } else
                drive = true;
        }

        
        if (heading == Direction.NORTH) {
            x = 0;
            y = -4;
        } else if(heading == Direction.EAST) {
            x = 4;
            y = 0;
        } else if(heading == Direction.WEST) {
            x = -4;
            y = 0;
        } else if(heading == Direction.SOUTH) {
            x = 0;
            y = speed;
        }

        for(Square s : fieldOfView) {
            if(s.getOccupant() != null && !safeDistance(s)) {
                drive = false;
                break;
            }
        }
        if(drive)
            drive(x, y);
        
        moved++;
        findOccupied();
        findFieldOfView();
    }
}
