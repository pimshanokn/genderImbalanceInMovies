import java.util.LinkedList;
import java.util.Scanner;
import java.util.Enumeration;
import java.util.Vector;
import java.io.*;
import javafoundations.PriorityQueue;

/**
 * Represents a collection of Movies. Uses a LinkedList to hold the 
 * movie objects. Movie data (title and test results) are coming 
 * from a file named "nextBechdel_allTests.txt". Data regarding 
 * actors who participated in each movie is read from a file named 
 * "nextBechdel_castGender.txt". Both files are provided in the 
 * "data" folder.
 *
 * @author Moji, Janice, and Hana
 * @version 12/16/2022
 */
public class MovieCollection
{
    private LinkedList<Movie> allMovies;
    private LinkedList<Actor> allActors;
    // other instance variables
    private String testsFileName;
    private String castsFileName;

    /**
     * Constructor for objects of class MovieCollection
     */
    public MovieCollection(String testsFileName, String castsFileName) {
        allMovies = new LinkedList<Movie>();
        allActors = new LinkedList<Actor>();
        this.testsFileName = testsFileName;
        this.castsFileName = castsFileName;
        // populating allMovies and allActors with information from files
        this.readMovies();
        this.readCasts();
    }

    /**
     * Returns all the movies in the collection as a LinkedList
     * 
     * @return  a LinkedList with all the movies, each complete 
     * with its title, actors, and Bechdel test results
     */
    public LinkedList<Movie> getMovies() {
        return allMovies;
    }

    /**
     * Returns all the Actors in the collection as a LinkedList
     * 
     * @return  a LinkedList with all the Actors, each complete
     * with their name and gender
     */
    public LinkedList<Actor> getActors() {
        return allActors;
    }

    /**
     * Returns the titles of all movies in the collection
     * 
     * @return  a LinkedList with the titles of all the movies
     */
    public LinkedList<String> getMovieTitles() {
        LinkedList<String> titles = new LinkedList<String>();
        for(Movie m:allMovies) {
            titles.add(m.getTitle());
        }

        return titles;
    }

    /**
     * Returns the names of all actors in the collection
     * 
     * @return  a LinkedList with the names of all actors
     */
    public LinkedList<String> getActorNames() {
        LinkedList<String>  names = new LinkedList<String>();
        for(Actor a:allActors) {
            names.add(a.getName());
        }

        return names;
    }

    /**
     * Reads the input file, and uses its first column (movie title) 
     * to create all movie objects. Adds the included information on 
     * the Bechdel test results to each movie. It is perhaps better 
     * to make this method private
     */
    private void readMovies() {
        try {
            Scanner fileScan = new Scanner(new File(testsFileName));
            // gets rid of the first line
            fileScan.nextLine();

            while (fileScan.hasNext()) {
                String current = fileScan.nextLine();
                String[] info = current.split(",");
                Movie m = new Movie(info[0]); //create movie object 

                // always going to have 13 tests - starting from 1 because
                // info[0] is the movie's name
                String results = "";
                for (int i = 1; i <= 13; i++){
                    results += info[i] + ",";
                }

                m.setTestResults(results.substring(0,results.length()-1));
                // It didn't specify so I'm assuming to add to the
                // collection
                allMovies.add(m); 
            }
            fileScan.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    /**
     * Reads the casts for each movie, from input casts file; 
     * Assume lines in this file are formatted as followes: 
     * "MOVIE","ACTOR","CHARACTER_NAME","TYPE","BILLING","GENDER" 
     * For example: "Trolls","Ricky Dillon","Aspen Heitz","Supporting","18","Male". 
     * Notes: 1) each movie will appear in (potentially) many 
     * consecutive lines, one line per actor. 
     * 2) Each token (title, actor name, etc) appears in double 
     * quotes, which need to be removed as soon as the tokens are read. 
     * 3) If a movie does not have any test results, it is ignored 
     * and not included in the collection. (There is actually one 
     * such movie) It is perhaps better to make this method private
     */
    private void readCasts() {
        // make the assumption that readMovies is called before readCasts
        // for each movie object, do addAllActors
        for (Movie m : allMovies) {
            m.addAllActors(castsFileName);

            // for each Actor in the hashtable. add them to allActors
            Enumeration<Actor> e = m.getAllActors().keys();

            while (e.hasMoreElements()) {
                Actor anActor = e.nextElement();
                if (!allActors.contains(anActor)) {
                    allActors.add(anActor);
                }
            }
        }
    }

    /**
     * Returns a LinkedList of all Movies that pass the n-th 
     * Bechdel test
     * 
     * @param  n  integer identifying the n-th test in the list of
     * 13 Bechdel-like tests, starting from zero
     * @return  a LinkedList of all Movies which have passed the 
     * n-th test
     */
    public LinkedList<Movie> findAllMoviesPassedTestNum(int n) {
        // create a LinkedList to add the Movie objects in
        LinkedList<Movie> result = new LinkedList<Movie>();

        // for each movie in allMovies
        for (Movie m : allMovies) {
            // access its testResults
            Vector<String> allTestResults = m.getAllTestResults();
            // access the nth element
            String passOrNot = allTestResults.get(n);
            // assess -- if 0, passed and added to LinkedList
            if (passOrNot.equals("0")) {
                // System.out.println("adding " + m.getTitle());
                result.add(m);
            }
        }
        return result;
    }

    /**
     * rankMovies ranks the Movies in this MovieCollection by their
     * feminist scores -- if two Movies have the same feminist scores,
     * the one that passes more tests (out of the 13 tests) will 
     * have higher priority; if two Movies have the same feminist
     * scores AND share the same amount of tests that they pass, 
     * they will be sorted according to the alphabetical order 
     * of their Movie titles (described in detail in Movie class)
     * 
     * @return  a PriorityQueue of Movies that, when dequeued, show
     * Movies from highest to lowest feminist scores
     */
    public PriorityQueue<Movie> rankMovies() {
        PriorityQueue<Movie> result = new PriorityQueue<Movie>();
        for (Movie m : allMovies) {
            result.enqueue(m);
        }

        return result;
    }

    /**
     * passBechdel returns a LinkedList of Movies that passes the
     * Bechdel test
     * 
     * @return  a LinkedList of Movies that pass the Bechdel test
     */
    public LinkedList<Movie> passBechdel() {    
        return findAllMoviesPassedTestNum(0);
    }

    public LinkedList<Movie> passPierceOrLandau(){
        LinkedList<Movie> result = new LinkedList<Movie>();

        for (Movie m : allMovies) {
            Vector<String> allTestResults = m.getAllTestResults();
            String passOrNot = allTestResults.get(1);
            // 0 means passing the test.
            // Pierce test is 2nd in order, while Landau test is 3rd. 
            if (allTestResults.get(1).equals("0") || 
            allTestResults.get(2).equals("0") ) 
                result.add(m);
        }

        return result;        
    }

    /**
     * passWhiteButNotReesDavies returns a LinkedList of Movies that
     * passes the White test but fails the Rees-Davies test
     * 
     * @return  a LinkedList of Movies that pass the White test but
     * fails the Rees-Davies test
     */
    public LinkedList<Movie> passWhiteButNotReesDavies() {
        LinkedList<Movie> result = new LinkedList<Movie>();

        // white test is 12th (index 11)
        LinkedList<Movie> passedWhite = findAllMoviesPassedTestNum(11);

        // rees-davies test is 13th (index 12)
        LinkedList<Movie> passedReesDavies = findAllMoviesPassedTestNum(12);

        for (Movie m : passedWhite) {
            if (!passedReesDavies.contains(m)) {
                result.add(m);
            }
        }

        return result;
    }

    /**
     * Returns a String representation of this MovieCollection
     * 
     * @return  a String representation of this collection, including
     * the number of movies and the movies themselves
     */
    public String toString() {
        String message = "There are " + allMovies.size() 
            + " movies in this collection:";

        for (Movie m:allMovies) {
            message += "\n" + m;
        }

        return message;
    }

    public static void main(String[] args) {
        System.out.println("TEST 1 (using data/test1_allTests.txt and data/test1_castGender.txt)");
        System.out.println("Instantiating a new MovieCollection object loading information from filed. (using readMovies() and readCasts() in constructor.)");
        MovieCollection test1 = new MovieCollection("data/test1_allTests.txt", "data/test1_castGender.txt");
        System.out.println("Initial toString():");
        System.out.println(test1 + "\n");
        System.out.println("Testing getMovies() (expect a LinkedList of Movie object):");
        System.out.println(test1.getMovies() + "\n");
        System.out.println("Testing getActors() (expect a LinkedList of Actor object with gender):");
        System.out.println(test1.getActors() + "\n");
        System.out.println("Testing getMovieTitles() (expect a LinkedList of movie's title):");
        System.out.println(test1.getMovieTitles() + "\n");
        System.out.println("Testing getActorNames() (expect a LinkedList of actor's names):");
        System.out.println(test1.getActorNames() + "\n");

        System.out.println("Testing findAllMoviesPassedTestNum(3) (Feldman test) (expect 2: The Florida Project and Lady Bird):");
        System.out.println(test1.findAllMoviesPassedTestNum(3) + "\n");

        System.out.println("Testing passBechdel() (Expect all 4 movies)");
        System.out.println(test1.passBechdel() + "\n");

        System.out.println("Testing passPierceOrLandau() (Expect 3: The Florida Project, Lady Bird, Don't Look Up)"); 
        System.out.println(test1.passPierceOrLandau() + "\n");

        System.out.println("Testing passWhiteButNotReesDavies() (Expect 1: Lady Bird)");
        System.out.println(test1.passWhiteButNotReesDavies()  + "\n");
        System.out.println("Testing rankMovies() (Expected order: Don't Look Up, The Florida Project, Lady Bird, and When Harry Met Sally...)");
        PriorityQueue<Movie> rank1 = test1.rankMovies();
        while (!rank1.isEmpty())
            System.out.println(rank1.dequeue());

        System.out.println("\n~~~~~");

        System.out.println("\nTEST 2 (using data/small_projectCast.txt and data/small_projectMovies.txt)");
        System.out.println("Instantiating a new MovieCollection object loading information from files. (using readMovies() and readCasts() in constructor.)");
        MovieCollection test2 = new MovieCollection("data/small_projectMovies.txt", "data/small_projectCast.txt");
        System.out.println("Initial toString():");
        System.out.println(test2 + "\n");

        System.out.println("Testing getMovies() (expect a LinkedList of Movie object):");
        System.out.println(test2.getMovies() + "\n");
        System.out.println("Testing getActors() (expect a LinkedList of Actor object with gender):");
        System.out.println(test2.getActors() + "\n");

        System.out.println("Testing getMovieTitles() (expect a LinkedList of movie's title):");
        System.out.println(test2.getMovieTitles() + "\n");
        System.out.println("Testing getActorNames() (expect a LinkedList of actor's names):");
        System.out.println(test2.getActorNames() + "\n");

        System.out.println("Testing findAllMoviesPassedTestNum(4) (Villareal test) (expect 8: Hidden Figures, Sully, The Girl on the Train, 10 Cloverfield Lane, Bad Moms, La La Land, Arrival, Storks):");
        System.out.println(test2.findAllMoviesPassedTestNum(4) + "\n");

        System.out.println("Testing passBechdel() (Expect 8 movies: Boo! A Madea Halloween, Hidden Figures, The Girl on the Train, Don't Breathe, 10 Cloverfield Lane, Bad Moms, La La Land, Arrival])");
        System.out.println(test2.passBechdel() + "\n");

        System.out.println("Testing passPierceOrLandau() (Expect all 11 movies)"); 
        System.out.println(test2.passPierceOrLandau() + "\n");

        System.out.println("Testing passWhiteButNotReesDavies() (Expect no movies)");
        System.out.println(test2.passWhiteButNotReesDavies()  + "\n");

        System.out.println("Testing rankMovies() (Expect a Priority Queue of movies)");
        PriorityQueue<Movie> testRank = test2.rankMovies();

        while (!testRank.isEmpty())
            System.out.println(testRank.dequeue());
    }
}
