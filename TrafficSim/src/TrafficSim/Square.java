package TrafficSim;

/**
 *
 * @author nikla_000
 */
public class Square {

    private final float xStart;
    private final float yStart;
    private final int colNum;
    private final int rowNum;
    private int size;
    private Direction direction;

    private Vehicle occupant;
    private Person pedestrian;

    public Square(float x, float y, int colNum, int rowNum, int size, Direction dir) {

        xStart = x;
        yStart = y;
        direction = dir;
        this.size = size;
        this.rowNum = rowNum;
        this.colNum = colNum;

    }

    public boolean hasPoint(float x, float y) {

        boolean withinX = x > xStart && x < (xStart + size);
        boolean withinY = y > yStart && y < (yStart + size);

        return withinX && withinY;

    }

    public void setOccupant(Vehicle v) {
        occupant = v;
    }
    
    public void setPedestrian(Person p) {
        pedestrian = p;
    }

    public float getxStart() {
        return xStart;
    }

    public float getyStart() {
        return yStart;
    }

    public int getSize() {
        return size;
    }

    public int getColNum() {
        return colNum;
    }

    public int getRowNum() {
        return rowNum;
    }

    public Vehicle getOccupant() {
        return occupant;
    }
    
    public Person getPedestrian() {
        return pedestrian;
    }

    public Direction getDirection() {
        return direction;
    }
    
    

}
