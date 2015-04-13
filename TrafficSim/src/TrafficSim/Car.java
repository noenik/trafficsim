package TrafficSim;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author nikla_000
 */
public class Car extends Vehicle {
    
    
    public Car(ArrayList<Square> grid, Random rand, ArrayList<Crossing> crossings) {
        
        super("graphics/bil1.png", grid, rand, crossings);
        
    }
int moved = 0;
    @Override
    public void act() {

        float x = 0;
        float y = 0;
        if(heading == Direction.NORTH) {
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
            y = 4;
        }
        
        boolean drive = true;
        if(checkForCrossings() == false) {
            drive = false;
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
