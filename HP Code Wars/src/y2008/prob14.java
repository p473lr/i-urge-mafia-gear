package y2008;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * codewars 2008
 * Problem name: Six Degrees Of Kevin Bacon
 * 
 * Assumptions:
 * 1. how to handle no relationship?
 * 2. space between results?
 * 
 * Time : 3 hours
 * Difficulty : medium (needs either recursion or a tree.)
 * Author : Michael Scheetz
 *
 */
public class prob14 
{
    /**
     * Need a new line in a couple of places.
     */
    public static String newline = System.getProperty("line.separator");
    
    /**
     * Start solving the problem.
     * Passing the exception all the way up makes some of the code 
     * look cleaner but would not be appropriate for product code.
     * 
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception 
    {
        SixDegreesOfKevinBacon prob = new SixDegreesOfKevinBacon();
        prob.execute();
    }

    /**
     * Represents a node in a tree.
     * A link to a parent indicates that the two actors
     * were in a movie together.  The name of the movie
     * is stored in the child node.
     *
     */
    @SuppressWarnings("serial")
    class Relation extends DefaultMutableTreeNode
    {
        /**
         * The name of this actor.
         */
        private String myName;
        /**
         * The movie this actor shared with its parent.
         */
        private String myMovie;
        
        /**
         * Getters.
         * 
         * @return
         */
        public String getName()
        {
            return myName;
        }
        public String getMovie()
        {
            return myMovie;
        }
        
        /**
         * The popular constructor.
         * 
         * @param name
         * @param movie
         */
        public Relation(String name, String movie)
        {
            this(name, movie, true);
        }

        /**
         * Use this constructor if no relationship is found
         * to the target
         * 
         * @param name
         * @param movie
         * @param isRelated
         */
        public Relation(String name, String movie, boolean isRelated)
        {
            super();
            myName = name;
            if (isRelated)
            {
                myMovie= movie;
            }
        }

        /**
         * Put together an appropriate message for this node.
         */
        public String toString()
        {
            StringBuilder retval = new StringBuilder(
                    myName + " has a " + DataBase.DEGREENAME + " number of ");
            Relation parent = (Relation)getParent();
            if ( parent != null )
            {
                retval.append(getLevel());
                retval.append(newline);
                retval.append(detailString());
            }
            else
            {
                if (myName.equals(DataBase.ROOTNAME))
                {
                    // The search name is the same as the target name.
                    retval.append(getLevel());
                    retval.append(newline);
                }
                else
                {
                    // No relationship found.
                    retval.append("NaN");
                    retval.append(newline);
                }
            }
            retval.append(newline);
            return retval.toString();
        }
        
        /**
         * Iterate through all of the parent nodes
         * to print the detail of all the relationships.
         * 
         * @return
         */
        public String detailString()
        {
            StringBuilder retval = new StringBuilder();

            Relation parent = (Relation)getParent();
            if (parent != null)
            {
                retval.append(myName);
                retval.append(" was in ");
                retval.append(myMovie);
                retval.append(" with ");
                retval.append(parent.myName);
                retval.append(newline);
                retval.append(parent.detailString());
            }            
            
            return retval.toString();
        }
    }
    
    /**
     * Build a database of tree nodes that can be searched.
     *
     */
    class DataBase
    {
        private static final String ROOTNAME = "KevinBacon";
        private static final String DEGREENAME = "Bacon";
        private Relation rootNode;

        /**
         * Take the given map of movies and actors and
         * construct a tree of actors.
         * 
         * @param _movies
         */
        public DataBase(Map<String, ArrayList<String>> movies)
        {
            rootNode = new Relation(ROOTNAME,"");
            buildTree(rootNode, movies);
        }
        
        /**
         * Recursively build a tree of actors where a link 
         * indicates a movie that the parent and the child 
         * participated in.
         * The child object holds the name of the linking movie.
         * We build the tree in a breadth-first order so 
         * that we can remove the movie thereby removing any
         * duplicate entries.
         */
        @SuppressWarnings("unchecked")
        void buildTree(Relation node,Map<String, ArrayList<String>> allMovies)
        {
            ArrayList<String> movies = findMovies(node.getName(), allMovies);
            Map<String, ArrayList<String>> remainingMovies = new HashMap<String, ArrayList<String>>(allMovies);
            for(String movieName : movies)
            {
                ArrayList<String> actors = allMovies.get(movieName);
                for(String actorName : actors)
                {
                    node.add(new Relation(actorName, movieName));
                }
                remainingMovies.remove(movieName);
            }
            
            if (!remainingMovies.isEmpty())
            {
                for (Enumeration<Relation> children = node.children() ; children.hasMoreElements() ;)
                {
                    Relation child = children.nextElement();
                    buildTree(child, remainingMovies);
                }
            }
        }

        /**
         * Search the given list to return all of the movies
         * from it in which the given actor performed. 
         */
        ArrayList<String> findMovies(String actor, Map<String, ArrayList<String>> allMovies)
        {
            ArrayList<String> retval = new ArrayList<String>();
            for (String movie : allMovies.keySet())
            {
                ArrayList<String> actors = allMovies.get(movie);
                if (actors.contains(actor))
                {
                    retval.add(movie);
                }
            }
            
            return retval;
        }
        
        /**
         * Do a breadth-first search of the database tree
         * to find the first tree node that contains
         * the given actor's name.
         * 
         * If no relationship is found then return a Relation
         * that will indicate no relationship to the target actor.
         * 
         * @param name
         * @return
         */
        @SuppressWarnings("unchecked")
        public Relation search(String name)
        {
            assert rootNode != null;
            assert name != null;
            
            for (Enumeration<Relation> children = rootNode.breadthFirstEnumeration() ; children.hasMoreElements() ;)
            {
                Relation child = children.nextElement();
                if(name.equals(child.getName()))
                {
                    // found one
                    return child;
                }
            }
            // did not find a relationship
            return new Relation(name, null, false);
        }
    }

    /**
     * Run through the steps to parse the input,
     * build the database,
     * and find the requested names.
     * 
     * @throws IOException 
     */
    void execute() throws Exception
    {
        Collection<String> input = readStdin();
        Collection<String> searchList = new ArrayList<String>();
        Map<String, ArrayList<String>> movies = processInput(input,searchList);
        DataBase db = new DataBase(movies);
        for (String name : searchList)
        {
            Relation node = db.search(name);
            System.out.print(node.toString());
        }
    }

    /**
     * Read the stdin and return each line in a list.
     * 
     * @return
     * @throws Exception
     */
    ArrayList<String> readStdin() throws Exception
    {
        ArrayList<String> retval = new ArrayList<String>();
        
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        
        if ( in != null )
        {
            if (in.ready())
            {
                for (String line = in.readLine(); line != null ; line = in.readLine())
                {
                    retval.add(line);
                }
            }
            in.close();
        }
        else
        {
            System.out.println("Error: no reader");
        }
        
        return retval;
    }

    /**
     * Parse the input into a map of movie and actor data,
     * also return a list of actor names to search for.
     * 
     * @param input
     * @param searchList 
     * @return
     */
    Map<String,ArrayList<String>> processInput(Collection<String> input, Collection<String> searchList)
    {
        assert searchList!=null;
        assert input != null;
        
        Map<String,ArrayList<String>> retval = new HashMap<String, ArrayList<String>>();
        boolean isMovie = false;
        for (String line : input)
        {
            if (line.equals("[MOVIES]")) isMovie=true;
            else if(line.equals("[ACTORS]")) isMovie=false;
            else if(line.equals("[END]")) ;
            else
            {
                if (isMovie)
                {
                    String[] tokens = line.split(" ");
                    if (tokens!=null && tokens.length>0)
                    {
                        ArrayList<String> movieActors = new ArrayList<String>();
                        String movieName = tokens[0];
                        for (String actor : tokens)
                        {
                            if(!movieName.equals(actor))
                            {
                                movieActors.add(actor);
                            }
                        }
                        retval.put(movieName, movieActors);
                    }
                }
                else
                {
                    searchList.add(line);
                }
            }
        }
        return retval;
    }
}
