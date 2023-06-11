import java.util.LinkedList;
import java.util.Hashtable;
import java.util.Vector;
import java.util.Scanner;
import java.util.Enumeration;
import java.lang.Comparable;
import java.io.*;

/**
 * Represents an object of type Movie.
 * A Movie object has a title, some Actors, and results for the twelve Bechdel tests.
 *
 * @author Moji, Janice, and Hana
 * @version 12/8/2022
 */
public class Movie implements Comparable<Movie>
{
    private String title;
    private Hashtable<Actor, String> cast; 
    private Vector<String> testResults;
    private int totalNumTestPassed; // used to break tie

    /**
     * Constructor for objects of class Movie
     * 
     * @param  title  the title of the movie
     */
    public Movie(String title) {
        // initializing instance variables
        this.title = title;
        cast = new Hashtable<Actor, String>();
        testResults = new Vector<String>();
        totalNumTestPassed = 0;
    }

    /**
     * Returns the movie's title
     * 
     * @return the title of this movie
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Returns a LinkedList with all the actor names who played 
     * in this movie. Although this method may seem redundant
     * at first, it is useful when displaying the number of actors
     * in a Movie
     * 
     * @return  a LinkedList with the names of all the actors who 
     * played in this movie
     */
    public LinkedList<String> getActors() {        
        LinkedList<String> result = new LinkedList<String>();

        Enumeration<Actor> e = cast.keys();
        while (e.hasMoreElements()) {
            Actor anActor = e.nextElement();
            String info = cast.get(anActor);
            result.add(anActor.getName());
        }

        return result;
    }

    /**
     * Returns the movie's actors in a Hashtable
     * 
     * @return  a Hashtable with all the actors who played in this 
     * movie
     */
    public Hashtable<Actor, String> getAllActors() {
        return cast;
    }

    /**
     * Returns a Vector with all the Bechdel test results for this 
     * movie
     * 
     * @return  a Vector with the Bechdel test results for this 
     * movie: A test result can be "1" or "0" indicating that this 
     * movie passed or did not pass the corresponding test
     */
    public Vector<String> getAllTestResults() {
        return testResults;
    }

    /**
     * Populates the testResults vector with "0" and "1"s, each 
     * representing the result of the corresponding test on this 
     * movie. This information will be read from the file 
     * "nextBechdel_allTests.csv"
     * 
     * @param  results  a String consisting of 0s and 1s. Each one 
     * of these values denotes the result of the corresponding test 
     * on this movie
     */
    public void setTestResults(String results) {
        // first split the results into an array
        String[] withoutComma = results.split(",");

        for (String i : withoutComma) {
            testResults.add(i);
        }
    }

    /**
     * Tests this movie object with the input one and determines whether they are equal.
     * 
     * @return true if both objects are movies and have the same title, 
     * false in any other case.
     */
    public boolean equals(Object other) {
        if (other instanceof Movie) {
            return this.title.equals(((Movie) other).title); // Need explicit (Movie) cast to use .title
        } else {
            return false;
        }
    }

    /**
     * Takes in a String, formatted as lines are in the input file 
     * ("nextBechdel_castGender.txt"), generates an Actor, and adds 
     * the object to the actors of this movie. Input String has the 
     * following formatting: "ACTOR","CHARACTER_NAME","TYPE","BILLING","GENDER" 
     * Example of input: "Ricky Dillon","Aspen Heitz","Supporting","18","Male"
     * 
     * @param  line  a String representing the information of each Actor
     * @return  the Actor that was just added to this movie
     */
    public Actor addOneActor(String line) {
        /* gets rid of first and last quotation marks
         * "ACTOR","CHARACTER_NAME","TYPE","BILLING","GENDER" to
         * ACTOR","CHARACTER_NAME","TYPE","BILLING","GENDER 
         */
        line = line.substring(1, line.length()-1);

        /* split the information into an array
         * ACTOR","CHARACTER_NAME","TYPE","BILLING","GENDER to
         * [ACTOR, CHARACTER_NAME, TYPE, BILLING, GENDER]
         */
        String[] info = line.split("\",\"");

        // info[0] is ACTOR; info[4] is GENDER
        Actor newActor = new Actor(info[0], info[4]);

        // info[2] is TYPE -- put (Actor, role) pair into Hashtable
        cast.put(newActor, info[2]);

        return newActor;
    }

    /**
     * Reads the input file ("nextBechdel_castGender.txt"), and adds
     * all its Actors to this movie. Each line in the movie has the 
     * following formatting: Input String has the following 
     * formatting: "MOVIE TITLE","ACTOR","CHARACTER_NAME","TYPE","BILLING","GENDER" 
     * Example of input: "Trolls","Ricky Dillon","Aspen Heitz","Supporting","18","Male"
     * 
     * @param  actorsFile  the file containing information on each 
     * actor who acted in the movie
     */
    public void addAllActors(String actorsFile) {
        try {
            Scanner fileScan = new Scanner(new File(actorsFile));
            // gets rid of the first line that describes each column
            fileScan.nextLine();

            while (fileScan.hasNext()) {
                String current = fileScan.nextLine();
                /* if the line being assessed contains the title of
                 * this Movie, then add the Actor
                 */
                if (current.contains("\"" + title + "\"")) {
                    // title.length() + 3 to account for "TITLE", part
                    addOneActor(current.substring(title.length() + 3));
                }
            }
            fileScan.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    /**
     * feministScore is a method that calculates the movie's feminist
     * score depending on the following criteria: 
     *  Add 4 points if it passes the Rees-Davies test
     *  Add 3 points if it passes the Pierce test
     *  Add 2 points if it passes the Hagen test
     *  Add 1 point if it passes the Ko test.
     * This method also calculates the total number of tests the
     * Movie passes, which is then assigned to the instance variable
     * totalNumTestPassed (used to break ties)
     * 
     * @return  the feminist score of this Movie
     */
    public int feministScore() {
        int feministScore = 0;
        // so that printing a Movie object before calling feministScore will not produce an exception
        if (!testResults.isEmpty()) {
            // Rees-Davies is 13th test (index 12)
            if (testResults.get(12).equals("0")) {
                feministScore += 4;
            }
            // Pierce is 2nd test (index 1)
            if (testResults.get(1).equals("0")) {
                feministScore += 3;
            }
            // Hagen is 4th test (index 5)
            if (testResults.get(5).equals("0")) {
                feministScore += 2;
            }
            // Ko is 5th test (index 6)
            if (testResults.get(6).equals("0")) {
                feministScore++;
            }
        }

        // for counting how many tests (out of the 13) this Movie passes
        int counter = 0;
        for (String i : testResults) {
            if (i.equals("0")) {
                counter++;
            }
        }
        totalNumTestPassed = counter;

        return feministScore;
    }

    /**
     * compareTo compares this Movie object and another Movie object
     * by their feminist scores
     * If the feminist scores are the same, it compares the total
     * number of tests they pass (out of 13)
     * If the total number of tests they pass are the same too, it
     * compares the title of the Movies (alphabetically)
     * 
     * @param other  the other Movie to be compared to this Movie
     * @return  0, 1, or -1 depending first on the Movies' feminist
     * scores, then (if the tie is still not broken) on the total 
     * number of tests they pass, and finally alphabetically by title
     */
    public int compareTo(Movie other) {
        if (this.feministScore() == other.feministScore()) {
            /* if the feminist scores are the same, compare the
             * total number of tests they pass
             */
            if (this.totalNumTestPassed > other.totalNumTestPassed) {
                return 1;
            } else if (this.totalNumTestPassed < other.totalNumTestPassed) {
                return -1;
            }

            /* if it is still the same, rank them alphabetically
             * by their titles
             */
            if (this.getTitle().compareTo(other.getTitle()) < 0) {
                return 1;
            } else if (this.getTitle().compareTo(other.getTitle()) > 0) {
                return -1;
            }

            // if all three criteria are the same, return 0
            return 0;
        } else if (this.feministScore() > other.feministScore()) {
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * Returns a String representation of this movie. For easier 
     * testing, in the current version it returns only the title of 
     * the movie, its feminist score, and the number of tests (out
     * of 13) it passes
     * 
     * @return  a reasonable String representation of this movie:
     * includes the title and the feminist score, as well as the
     * total number of tests (out of 13) it passes
     */
    public String toString() {
        return title + " (" + feministScore() + "," + totalNumTestPassed + ")";
    }

    public static void main(String[] args) {
        Movie m1 = new Movie("Alpha");
        Movie m2 = new Movie("Alpha");
        Movie m3 = new Movie("Beta");
        Movie m4 = new Movie("Gamma");

        // Testing toString 
        System.out.println("Printing out Movies 1, 2, 3, and 4 (before any information is added):");
        System.out.println(m1);
        System.out.println(m2);
        System.out.println(m3);
        System.out.println(m4);

        // Testing getTitle
        System.out.println("\nTesting getTitle on Movie 1 (expect Alpha): " +  m1.getTitle());
        System.out.println("Testing getTitle on Movie 3 (expect Beta): " + m3.getTitle());

        // Testing addOneActor and getActors
        m1.addOneActor("\"Tyler Perry\",\"Madea/Joe/Brian\",\"Leading\",\"1\",\"Male\"");
        System.out.println("\nTesting addOneActor and getActors on Movie 1 (adding Tyler Perry): " + m1.getActors());

        // Testing addAllActors and getActors
        m2.addAllActors("data/small_castGender.txt");
        System.out.println("\nTesting addAllActors and getActors on Movie 2 (adding Tyler, Cassi, Patrice, and Stella: " + m2.getActors());

        // Testing addAllActors and getAllActors
        m3.addAllActors("data/small_castGender.txt");
        System.out.println("Testing addAllActors and getAllActors on Movie 3 (adding Tyler, Cassi, Patrice, and Takis): " + m3.getAllActors());
        m4.addAllActors("data/small_castGender.txt");
        System.out.println("Testing addAllActors and getAllActors on Movie 4 (adding Tyler and Cassi): " + m4.getAllActors());

        // Testing setTestResults and getAllTestResults
        m1.setTestResults("0,0,1,0,0,1,1,1,1,0,1,1,1");
        System.out.println("\nTesting setTestResults on Movie 1 with the data \"0,0,1,0,0,1,1,1,1,0,1,1,1\"");
        System.out.println("Testing getAllTestResults on Movie 1 (expect above data): " + m1.getAllTestResults());
        System.out.println("Testing getAllTestResults on Movie 2 (expect empty vector): " + m2.getAllTestResults());
        m3.setTestResults("0,0,1,0,0,0,1,1,1,1,1,1,0");
        System.out.println("Testing setTestResults on Movie 3 with the data \"0,0,1,0,0,0,1,1,1,1,1,1,0\"");
        System.out.println("Testing getAllTestResults on Movie 3 (expect above data): " + m3.getAllTestResults());
        m4.setTestResults("0,0,1,0,0,1,1,1,1,0,1,1,1");
        System.out.println("Testing setTestResults on Movie 4 with the data \"0,0,1,0,0,1,1,1,1,0,1,1,1\"");        
        System.out.println("Testing getAllTestResults on Movie 4 (expect above data): " + m4.getAllTestResults());

        // Testing feministScore
        System.out.println("\nTesting feministScore on Movie 1 (expect 3): " + m1.feministScore());
        System.out.println("Testing feministScore on Movie 3 (expect 9): " + m3.feministScore());
        System.out.println("Testing feministScore on Movie 4 (expect 3): " + m4.feministScore());

        // Printing out 
        System.out.println("\nPrinting out Movies 1, 2, 3, and 4 again (now with information):");
        System.out.println(m1);
        System.out.println(m2);
        System.out.println(m3);
        System.out.println(m4);

        // Testing equals
        System.out.println("\nTesting equals on Movies 1 and 2 (expect true): " + m1.equals(m2));
        System.out.println("Testing equals on Movies 2 and 1 (expect true): " + m2.equals(m1));
        System.out.println("Testing equals on Movies 3 and 4 (expect false): " + m3.equals(m4));

        // Testing compareTo
        System.out.println("\nTesting compareTo on Movies 1 and 2 (expect 1 because 3 > 0): " + m1.compareTo(m2));
        System.out.println("Testing compareTo on Movies 2 and 1 (expect -1 because 0 < 3): " + m2.compareTo(m1));
        System.out.println("Testing compareTo on Movies 2 and 3 (expect -1 because 0 < 9): " + m2.compareTo(m3));
        System.out.println("Testing compareTo on Movies 1 and 4 (expect 1 because (3,5) == (3,5) but A before G): " + m1.compareTo(m4));

        m2.setTestResults("0,0,1,0,0,1,1,1,1,0,1,1,1"); // same as Movie 1
        System.out.println("\nSet the same testResults data as Movie 1 to Movie 2 -- feministScore of Movie 2 (expect 3): " + m2.feministScore());
        System.out.println("Testing compareTo on Movies 1 and 2 (expect 0): " + m1.compareTo(m2)); 
    }
}
