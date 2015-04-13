package TrafficSim;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author nikla_000
 */
public class Truck extends Vehicle{
    
    
    public Truck(ArrayList<Square> grid, Random rand) {

        super("graphics/bil1.png", grid, rand, 0.01f, null);

    }

    @Override
    public void act() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
