package TrafficSim;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author nikla_000
 */
public class MC extends Vehicle{
    
    
    public MC(ArrayList<Square> grid, Random rand) {
        super("graphics/bil1.png", grid, rand, null);
    }

    @Override
    public void act() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
