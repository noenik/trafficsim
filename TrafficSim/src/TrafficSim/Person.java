package TrafficSim;

/**
 *
 * @author nikla_000
 */
public class Person {
    
   private String firstName;
   private String lastName;
   
   
    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public void setFirstname(String firstName) {
        this.firstName = firstName;
    }
    
    public String getFirstname() {
        return firstName;
    }
    
    public void setLastname(String lastName)
    {
        this.lastName = lastName;
    }
    
    public String getLastname() {
        return lastName;
    }
}
