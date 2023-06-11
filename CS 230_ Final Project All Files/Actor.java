
/**
 * Represents an object of type Actor. An Actor has a name and a gender.
 *
 * @author Moji, Janice, and Hana
 * @version 12/8/2022
 */
public class Actor
{
    private String name;
    private String gender;
    
    /**
     * Constructor for objects of class Actor. Takes in the name
     * and the gender of the actor
     * 
     * @param  name  the name of the actor
     * @param  gender  the gender of the actor
     */
    public Actor(String name, String gender) {
        // initialize instance variables
        this.name = name;
        this.gender = gender;
    }
    
    /**
     * Returns the name of this actor
     * @return  the name of this actor
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Set the anme of this actor
     * @param  newName  the name of this actor
     */
    public void setName(String newName) {
        this.name = newName;
    }
    
    /**
     * Returns the gender of this actor
     * @return the gender of this actor
     */
    public String getGender() {
        return this.gender;
    }
    
    /**
     * Sets the gender of this actor
     * @param  newGender  the gender of this actor
     */
    public void setGender(String newGender) {
        this.gender = newGender;
    }

    /**
     * This method is defined here because Actor (mutable) is used as a key in a Hashtable.
     * It makes sure that same Actors have always the same hash code.
     * So, the hash code of any object taht is used as key in a hash table,
     * has to be produced on an *immutable* quantity,
     * like a String (such a string is the name of the actor in our case)
     * 
     * @return an integer, which is the has code for the name of the actor
     */
    public int hashCode() {
        return name.hashCode();
    }
    
    /**
     * Tests this actor against the input one and determines whether they are equal.
     * Two actors are considered equal if they have the same name and gender.
     * 
     * @return true if both objects are of type Actor, 
     * and have the same name and gender, false in any other case.
     */
    public boolean equals(Object other) {
        if (other instanceof Actor) {
            return this.name.equals(((Actor) other).name) && 
            this.gender.equals(((Actor) other).gender); // Need explicit (Actor) cast to use .name
        } else {
            return false;
        }
    }
    
    /**
     * toString returns a String representation of this Actor object.
     * For easier testing, in the current version it returns only
     * the name of the actor. Normally, it should also include their
     * gender as well
     * 
     * @return  a reasonable String representation of this actor, 
     * containing their name and gender
     */
    public String toString() {
        return this.name + " (" + this.gender + ")";
    }
    
    public static void main(String[] args) {
        Actor a1 = new Actor("Tyler Perry", "Male");
        Actor a2 = new Actor("Cassie Davis", "Female");
        Actor a3 = new Actor("Patrice Lovely", "Female");
        
        // Testing toString
        System.out.println("Printing out actors 1, 2, and 3:");
        System.out.println("Actor 1: " + a1);
        System.out.println("Actor 2: " + a2);
        System.out.println("Actor 3: " + a3);
        
        // Testing getters
        System.out.println("\nTesting getName on Actor 1 (expect Tyler Perry): " + a1.getName());
        System.out.println("Testing getGender on Actor 2 (expect Female): " +  a2.getGender());
        
        // Testing setters
        a1.setName("Cassie Davis");
        System.out.println("\nTesting setName on Actor 1 (expect Cassie Davis (Male)): " + a1);
        a1.setGender("Female");
        System.out.println("Testing setGender on Actor 1 (expect Cassie Davis (Female)): " + a1);
        
        // Testing hashcode
        System.out.println("\nTesting hashCode on Actor 1: " + a1.hashCode());
        System.out.println("Testing hashCode on Actor 2 (expect same as above): " + a2.hashCode());
        System.out.println("Testing hashCode on Actor 3 (expect different as above): " + a3.hashCode());
        
        // Testing equals
        System.out.println("\nTesting equals on Actors 1 and 2 (expect true): " + a1.equals(a2));
        System.out.println("Testing equals on Actors 2 and 1 (expect true): " + a2.equals(a1));        
        System.out.println("Testing equals on Actors 2 and 3 (expect false): " + a2.equals(a3));
    }
}
