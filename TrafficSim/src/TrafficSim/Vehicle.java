package TrafficSim;

/**
 *
 * @author nikla_000
 */
public abstract class Vehicle {
 
    private String make;
    private String model;
    private String reg;
    private int year;
    
    
        public Vehicle(String make, String model, int year, String reg) {
            
            this.make = make;
            this.model = model;
            this.year = year;
            this.reg = reg;
        }
        
        public void setMake(String make) {
            this.make = make;
        }
        
        public String getMake() {
            return make;
        }
        
        public void setModel(String model) {
            this.model = model;
        }
        
        public String getModel()
        {
            return model;
        }
        
        public void setYear(int year) {
            this.year = year;
        }
        
        public int getYear()
        {
            return year;
        }
        
        public void setRegistration(String reg) {
            this.reg = reg;
        }
        
        public String getRegistration() {
            return reg;
        }
}
