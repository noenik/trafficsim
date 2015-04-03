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
        
        if(heading == Direction.NORTH) {
            x = 0;
            y = -2;
        } else if(heading == Direction.EAST) {
            x = 2;
            y = 0;
        } else if(heading == Direction.WEST) {
            x = -2;
            y = 0;
        } else if(heading == Direction.SOUTH) {
            x = 0;
            y = 2;
        }
        
        boolean drive = true;
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
