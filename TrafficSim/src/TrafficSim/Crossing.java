/* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TrafficSim;

import java.util.ArrayList;

/**
 *
 * @author vidar
 */
public class Crossing {
    
    int xStart;
    int yStart;
    int xEnd;
    int yEnd;
    int id;
    ArrayList<Person> occupants;
    
    public Crossing(int xa, int ya, int xb, int yb, int id) {
        this.id = id;
        xStart = xa;
        xEnd = xb;
        yStart = ya;
        yEnd = yb;
        occupants = new ArrayList<Person>();
    }
    
    public void addOccupant(Person p) {
        occupants.add(p);
    }
    
    public void removeOccupant(Person p) {
        occupants.remove(p);
    }
    
    public int getNumberOfOccupants() {
        return occupants.size();
    }
    
    public int getXStart() {
        return xStart;
    }
    
    public int getYStart() {
        return yStart;
    }
    public int getXEnd() {
        return xEnd;
    }
    public int getYEnd() {
        return yEnd;
    }
    
    public void removeAll() {
        occupants.clear();
    }
    
    public int getID() {
        return id;
    }
    
    
}
