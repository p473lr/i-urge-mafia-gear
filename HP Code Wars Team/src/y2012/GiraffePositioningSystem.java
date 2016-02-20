import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

/**
 * @author Michael Scheetz
 * 
 *         <pre>
 * Problem: Giraffes move through sectors of a combat zone from start to
 * finish eating branches up to their height before moving to the next
 * sector. The shortest combined path is the expected output.
 *         
 * Example input: 
 *         3 
 *         Irving 10 
 *         George 12 
 *         Geoffrey 15 
 *         4 4 
 *         S  5 11 11 
 *         11 6 11 11 
 *         11 11 11 11 
 *         11 14 11 F
 * 
 * Example output: 
 *         Irving D D D R R R 
 *         George D E D E D E R R E R
 *         Geoffrey D E E E D E E E D E E E R E R E E E R
 * 
 * Steps: 
 *    1. Parse Input. 
 *    2. Enumerate all paths.
 *    3. Run each giraffe through each known path and count moves.
 * 
 * I think it will be faster to find all of the paths once and then run the 
 * giraffes through the paths.  Otherwise I would have to find all paths for 
 * each giraffe, including all of the ones that don't make it to finish.  
 * Another advantage to finding all of the paths once is that I can sort them 
 * and start with the shortest paths first.  Longer paths can be eliminated 
 * if they can't possibly beat the best one found so far.
 * 
 * Breadth-first or Depth-first search: Sectors have to keep state while each 
 * giraffe traverses the field searching for its shortest path. A breadth-first 
 * search means that we have to clone each sector while we find all of the paths.
 * A depth-first search can find a path and then clone the current state.
 * 
 * This solution deviates from the requirements by outputting all of the solutions
 * instead of just one of them.
 * 
 */
public class GiraffePositioningSystem
{
    private final List<Giraffe> giraffeList;
    private final CombatZone combatZone;
    private static Sector START;

    /**
     * Read the input and solve the problem. Passing exceptions all the way up
     * makes the code simpler.
     * 
     * @param args
     * @throws Exception
     *             For any issue.
     */
    public static void main(final String[] args) throws Exception
    {
        final String filename = InputHandler.extractFilenameByOption(args);
        final InputHandler inputHandler = new InputHandler(filename);
        final List<String> input = inputHandler.readInput();

        final GiraffePositioningSystem problem = new GiraffePositioningSystem(input);
        final Collection<Solution> solutions = problem.solve();

        if (solutions.size() > 1)
        {
            System.out.println("There are " + solutions.size() + " solutions.\n");
        }
        for (final Solution solution : solutions)
        {
            System.out.println(solution.toPrettyString());
        }
    }

    public GiraffePositioningSystem(final List<String> input)
    {
        final InputParser parser = new InputParser(input);
        giraffeList = parser.parseGiraffes();
        combatZone = new CombatZone(parser.parseCombatZone());
    }

    private Collection<Solution> solve()
    {
        final Collection<Solution> solutions = combatZone.findShortestPath(giraffeList);
        return solutions;
    }

    private interface Sector
    {
        boolean hasVisited();

        /**
         * This is a recursive call.
         * 
         * @return
         */
        Collection<Path> findPathsToFinishSector(Collection<Path> allPaths, Path currentPath);

        void upIs(Sector upSector);

        void downIs(Sector downSector);

        void leftIs(Sector leftSector);

        void rightIs(Sector rightSector);

        void visit();

        void unvisit();

        int height();

        int eat(int giraffeHeight);

        void uneat();
    }

    /**
     * Use this as a border so I don't have to check for null.
     */
    private static final Sector BORDER = new Sector()
    {
        @Override
        public boolean hasVisited()
        {
            return true;
        }

        @Override
        public void upIs(final Sector upSector)
        {
            // Ignore.
        }

        @Override
        public void downIs(final Sector downSector)
        {
            // Ignore.
        }

        @Override
        public void leftIs(final Sector leftSector)
        {
            // Ignore.
        }

        @Override
        public void rightIs(final Sector rightSector)
        {
            // Ignore.
        }

        @Override
        public Collection<Path> findPathsToFinishSector(final Collection<Path> allPaths, final Path currentPath)
        {
            throw new IllegalStateException("Error: Don't call this method from a border.");
        }

        @Override
        public void visit()
        {
            throw new IllegalStateException("Error: Don't call this method from a border.");
        }

        @Override
        public void unvisit()
        {
            throw new IllegalStateException("Error: Don't call this method from a border.");
        }

        @Override
        public String toString()
        {
            return "B";
        }

        @Override
        public int height()
        {
            throw new IllegalStateException("Error: Don't call this method from a border.");
        }

        @Override
        public int eat(final int giraffeHeight)
        {
            throw new IllegalStateException("Error: Don't call this method from a border.");
        }

        @Override
        public void uneat()
        {
            throw new IllegalStateException("Error: Don't call this method from a border.");
        }
    };

    /**
     * Represents the finish sector. There is only one and there are a couple of
     * methods that need to be specialized for this node.
     */
    private static final Sector FINISH = new Sector()
    {
        @Override
        public boolean hasVisited()
        {
            return false;
        }

        @Override
        public void upIs(final Sector upSector)
        {
            upSector.downIs(this);
        }

        @Override
        public void downIs(final Sector downSector)
        {
            // Do nothing.
        }

        @Override
        public void leftIs(final Sector leftSector)
        {
            leftSector.rightIs(this);
        }

        @Override
        public void rightIs(final Sector rightSector)
        {
            // Do nothing.
        }

        /**
         * A call to this method means we found a completed path to the finish
         * sector. Store the path and backtrack for more paths.
         */
        @Override
        public Collection<Path> findPathsToFinishSector(final Collection<Path> allPaths, final Path currentPath)
        {
            allPaths.add(new Path(currentPath));
            return allPaths;
        }

        @Override
        public void visit()
        {
            // Do nothing.
        }

        @Override
        public void unvisit()
        {
        }

        @Override
        public String toString()
        {
            return "F";
        }

        @Override
        public int height()
        {
            return Integer.MAX_VALUE;
        }

        @Override
        public int eat(final int giraffeHeight)
        {
            return 0;
        }

        @Override
        public void uneat()
        {
            // Do nothing.
        }
    };

    /**
     * The sectors in the combat zone that the giraffes can move through.
     * 
     */
    private static class MovementSector implements Sector
    {
        private final String id;
        private final Stack<Integer> currentHeight = new Stack<Integer>();
        private boolean hasVisited = false;
        private final Map<Direction, Sector> linkedSectors = new EnumMap<Direction, Sector>(Direction.class);

        /**
         * Initialize all directions out of this sector as a border.
         * 
         * @param _id
         * @param height
         */
        public MovementSector(final String _id, final int height)
        {
            id = _id;
            currentHeight.push(height);

            for (final Direction d : Direction.values())
            {
                linkedSectors.put(d, BORDER);
            }
        }

        @Override
        public void upIs(final Sector upSector)
        {
            linkedSectors.put(Direction.U, upSector);
            upSector.downIs(this);
        }

        @Override
        public void downIs(final Sector downSector)
        {
            linkedSectors.put(Direction.D, downSector);
        }

        @Override
        public void leftIs(final Sector leftSector)
        {
            linkedSectors.put(Direction.L, leftSector);
            leftSector.rightIs(this);
        }

        @Override
        public void rightIs(final Sector rightSector)
        {
            linkedSectors.put(Direction.R, rightSector);
        }

        @Override
        public boolean hasVisited()
        {
            return hasVisited;
        }

        /**
         * Recursively call this method until we find the finish sector.
         * 
         */
        @Override
        public Collection<Path> findPathsToFinishSector(final Collection<Path> allPaths, final Path currentPath)
        {
            for (final Entry<Direction, Sector> entry : linkedSectors.entrySet())
            {
                final Sector sector = entry.getValue();
                if (!sector.hasVisited())
                {
                    sector.visit();
                    final Direction pushDir = entry.getKey();
                    currentPath.push(pushDir, sector);
                    sector.findPathsToFinishSector(allPaths, currentPath);
                    final Direction popDir = currentPath.pop();
                    sector.unvisit();
                    if (popDir != pushDir) { throw new IllegalStateException(
                            "Error: push direction is not the same as pop direction: push=" + pushDir + ", pop="
                                    + popDir); }
                }
            }

            return allPaths;
        }

        @Override
        public void visit()
        {
            hasVisited = true;
        }

        @Override
        public void unvisit()
        {
            hasVisited = false;
        }

        /**
         * Return the number of rounds a giraffe needs to eat in this sector.
         */
        @Override
        public int eat(final int height)
        {
            final int retval = Math.max(height - currentHeight.peek(), 0);
            currentHeight.push(Math.max(height, currentHeight.peek()));
            return retval;
        }

        /**
         * Return the sector back to its state before the last giraffe came
         * through.
         */
        @Override
        public void uneat()
        {
            currentHeight.pop();
        }

        @Override
        public int height()
        {
            return currentHeight.peek();
        }

        @Override
        public String toString()
        {
            return id;
        }
    }

    private enum Direction
    {
        U, D, L, R;
    }

    private static class Giraffe
    {
        private final String name;
        private final int height;

        public Giraffe(final String[] nameAndHeight)
        {
            name = nameAndHeight[0];
            height = Integer.parseInt(nameAndHeight[1]);
        }

        public String getName()
        {
            return name;
        }

        public int getHeight()
        {
            return height;
        }

        /**
         * A recursive call to send each giraffe down all of the known paths
         * from start to finish and calculate the score for each path until we
         * find the shortest ones.
         * 
         * @param sortedPaths
         *            All of the known paths from start to finish.
         * @param subList
         *            The remaining giraffes that need to traverse.
         * @param bestSoFar
         *            A collection of the best solutions so far.
         * @param buildingSolution
         *            The solution that is currently being worked on.
         * @return All of the paths that are shortest.
         */
        public Collection<Solution> traverse(final List<Path> sortedPaths, final List<Giraffe> giraffeList,
                final Collection<Solution> allBestSoFar, final Solution buildingSolution)
        {
            final int shortestPathLength = sortedPaths.get(0).length();
            final int remainingGiraffeCount = giraffeList.size();
            final int idealScoreForRemainingGiraffes = remainingGiraffeCount * shortestPathLength;

            for (final Path path : sortedPaths)
            {
                final Solution firstBest = allBestSoFar.iterator().next();
                final int idealTotalScore = path.length() + buildingSolution.getScore()
                        + idealScoreForRemainingGiraffes;
                if (!firstBest.canBeBeat(idealTotalScore))
                {
                    break;
                }
                buildingSolution.push(this, path);

                if (!giraffeList.isEmpty())
                {
                    final Giraffe nextGiraffe = giraffeList.get(0);
                    nextGiraffe.traverse(sortedPaths, giraffeList.subList(1, remainingGiraffeCount), allBestSoFar,
                            buildingSolution);
                }
                else
                {
                    final int scoreSoFar = buildingSolution.getScore();
                    if (scoreSoFar == firstBest.getScore())
                    {
                        final Solution sameScore = new Solution(buildingSolution);
                        allBestSoFar.add(sameScore);
                    }
                    else if (scoreSoFar < firstBest.getScore())
                    {
                        allBestSoFar.clear();
                        final Solution better = new Solution(buildingSolution);
                        allBestSoFar.add(better);
                    }
                }

                buildingSolution.pop();
            }

            return allBestSoFar;
        }

        @Override
        public String toString()
        {
            return name;
        }
    }

    /**
     * Encapsulate the methods that work on the collection of sectors.
     * 
     */
    private class CombatZone
    {
        /**
         * Need to keep these alive.
         */
        private final Collection<Sector> sectors;

        public CombatZone(final Collection<Sector> inputSectors)
        {
            sectors = inputSectors;
        }

        public Collection<Solution> findShortestPath(final List<Giraffe> giraffeList)
        {
            final Collection<Path> allPaths = enumerateAllPaths();
            final Collection<Solution> solutions = walkGiraffes(giraffeList, allPaths);
            return solutions;
        }

        /**
         * Determine all of the paths that go from start to finish. I only want
         * to do this once.
         * 
         * @return All of the paths from start to finish.
         */
        public Collection<Path> enumerateAllPaths()
        {
            START.visit();
            final Collection<Path> allPaths = START.findPathsToFinishSector(new ArrayList<Path>(), new Path());
            return allPaths;
        }

        /**
         * Start the recursive call to walk each giraffe through all of the
         * paths and find the shortest one.
         * 
         * @param giraffeList
         * @param allPaths
         * @return
         */
        public Collection<Solution> walkGiraffes(final List<Giraffe> giraffeList, final Collection<Path> allPaths)
        {
            final List<Path> sortedPaths = new ArrayList<Path>(allPaths);
            Collections.sort(sortedPaths, new Comparator<Path>()
            {
                @Override
                public int compare(final Path o1, final Path o2)
                {
                    return Integer.compare(o1.length(), o2.length());
                }
            });

            final Giraffe firstGiraffe = giraffeList.get(0);
            final Collection<Solution> bestSoFar = new ArrayList<Solution>();
            final Solution aBest = new Solution();
            bestSoFar.add(aBest);
            final Collection<Solution> best = firstGiraffe.traverse(sortedPaths,
                    giraffeList.subList(1, giraffeList.size()), bestSoFar, new Solution());

            return best;
        }
    }

    /**
     * A set of sectors and the direction to go from start to finish.
     * 
     */
    private static class Path
    {
        private final Stack<Direction> directions = new Stack<Direction>();
        private final Stack<Sector> sectors = new Stack<Sector>();

        public Path()
        {
        }

        /**
         * A copy constructor to clone the path when we find a path to the
         * finish. We continue to push and pop the original object until we have
         * found all paths.
         * 
         * @param that
         */
        public Path(final Path that)
        {
            this.directions.addAll(that.directions);
            this.sectors.addAll(that.sectors);
        }

        public int length()
        {
            return directions.size();
        }

        /**
         * Walk a giraffe through the path and calculate how many rounds it eats
         * in each sector.
         * 
         * @param giraffe
         *            A giraffe to walk on this path.
         * @return A list of rounds needed to eat in each sector.
         */
        public List<Integer> walk(final Giraffe giraffe)
        {
            final List<Integer> pathBites = new ArrayList<Integer>();
            final int giraffeHeight = giraffe.getHeight();
            for (final Sector sector : sectors)
            {
                final int bites = sector.eat(giraffeHeight);
                pathBites.add(bites);
            }

            return pathBites;
        }

        /**
         * Undo a giraffe walk through the sectors and return to an earlier,
         * uneaten, state.
         */
        public void unwalk()
        {
            for (final Sector sector : sectors)
            {
                sector.uneat();
            }
        }

        /**
         * Push a sector into this path while searching for the finish sector.
         * 
         * @param pushDir
         *            A direction taken to the given sector.
         * @param sector
         *            A sector.
         */
        public void push(final Direction pushDir, final Sector sector)
        {
            sectors.push(sector);
            directions.push(pushDir);
        }

        /**
         * Pop a sector and direction from this path to return to an earlier
         * state while searching for the finish sector.
         * 
         * @return The direction that poped.
         */
        public Direction pop()
        {
            sectors.pop();
            return directions.pop();
        }

        /**
         * Get the direction for the given index. Used to print the solution
         * output.
         * 
         * @param index
         *            An index in the path.
         * @return A direction
         */
        public Direction get(final int index)
        {
            return directions.elementAt(index);
        }

        @Override
        public String toString()
        {
            return Arrays.toString(directions.toArray());
        }
    }

    /**
     * A solution for the problem.
     * 
     */
    private static class Solution
    {
        private final Stack<Giraffe> giraffes = new Stack<Giraffe>();
        private final Stack<Path> paths = new Stack<Path>();
        private final Stack<List<Integer>> eats = new Stack<List<Integer>>();

        public Solution()
        {
        }

        /**
         * A copy constructor to clone this object.
         * 
         * @param that
         */
        public Solution(final Solution that)
        {
            this.giraffes.addAll(that.giraffes);
            this.paths.addAll(that.paths);
            this.eats.addAll(that.eats);
        }

        /**
         * Push data for a giraffe and its path onto the solution.
         * 
         * @param giraffe
         *            A giraffe
         * @param path
         *            A path for the giraffe
         */
        public void push(final Giraffe giraffe, final Path path)
        {
            giraffes.push(giraffe);
            paths.push(path);
            final List<Integer> pathBites = path.walk(giraffe);
            eats.push(pathBites);
        }

        /**
         * pop the data from an earlier solution.
         * 
         */
        public void pop()
        {
            giraffes.pop();
            final Path path = paths.pop();
            path.unwalk();
            eats.pop();
        }

        /**
         * Calculate the score of this solution.
         * 
         * @return a score for this solution.
         */
        public int getScore()
        {
            if (paths.isEmpty()) { return Integer.MAX_VALUE; }
            int retval = 0;
            for (final Path path : paths)
            {
                retval += path.length();
            }
            for (final List<Integer> pathBites : eats)
            {
                final int totalBites = sumList(pathBites);
                retval += totalBites;
            }
            return retval;
        }

        /**
         * Sum a list of integers
         * 
         * @param pathBites
         *            A list integers.
         * @return The sum of the list.
         */
        private int sumList(final List<Integer> pathBites)
        {
            int totalBites = 0;
            for (final Integer bites : pathBites)
            {
                totalBites += bites;
            }
            return totalBites;
        }

        /**
         * Determine if the best score from the remaining giraffes can eliminate
         * further processing.
         * 
         * @param idealRemainingScore
         * @return true if the current path has potential to beat the best score
         *         so far.
         */
        public boolean canBeBeat(final int idealRemainingScore)
        {
            return (idealRemainingScore <= getScore());
        }

        /**
         * Format output data.
         * 
         * @return output string.
         */
        public String toPrettyString()
        {
            final StringBuilder retval = new StringBuilder();
            for (int i = 0; i < giraffes.size(); i++)
            {
                final Giraffe giraffe = giraffes.get(i);

                retval.append(giraffe.getName());
                retval.append(" ");

                final Path path = paths.get(i);
                final List<Integer> ate = eats.get(i);
                for (int j = 0; j < path.length(); j++)
                {
                    final Direction dir = path.get(j);
                    final int ateTimes = ate.get(j);

                    retval.append(dir);
                    retval.append(" ");
                    for (int k = 0; k < ateTimes; k++)
                    {
                        retval.append("E ");
                    }
                }
                retval.append("\n");
            }
            return retval.toString();
        }

        @Override
        public String toString()
        {
            return getScore() + ":" + Arrays.toString(paths.toArray());
        }
    }

    /**
     * Parse an array of strings into components specific to this problem, e.g.
     * giraffes and sectors.
     * 
     */
    private class InputParser
    {
        private static final String STARTTAG = "S";
        private static final String FINISHTAG = "F";

        private final List<String> giraffeData;
        private final List<String> combatZoneData;
        private final int combatZoneRows;
        private final int combatZoneColumns;

        /**
         * Calculate indexes for each of the sections.
         * 
         * @param input
         *            An array of strings from the input.
         */
        public InputParser(final List<String> input)
        {
            final int giraffeCountIndex = 0;
            final int giraffeNameStartIndex = 1;

            final int giraffeCount = Integer.parseInt(input.get(giraffeCountIndex));
            final int giraffeNameEndIndex = giraffeCount + 1;
            final int combatZoneDimensionIndex = giraffeNameEndIndex;
            final int[] combatZoneDimensions = parseCombatZoneSize(input.get(combatZoneDimensionIndex));
            combatZoneRows = combatZoneDimensions[0];
            combatZoneColumns = combatZoneDimensions[1];

            final int combatZoneStartIndex = combatZoneDimensionIndex + 1;
            final int combatZoneEndIndex = combatZoneStartIndex + combatZoneRows;

            if (combatZoneEndIndex != input.size()) { throw new IllegalStateException(
                    "Error: Expected end of combat zone data on line " + combatZoneEndIndex
                            + " but input data ended on line " + input.size()); }

            giraffeData = input.subList(giraffeNameStartIndex, giraffeNameEndIndex);
            combatZoneData = input.subList(combatZoneStartIndex, combatZoneEndIndex);
        }

        /**
         * Parse the giraffe section into a list of giraffe objects.
         * 
         * @returns the current index of the input list
         */
        public List<Giraffe> parseGiraffes()
        {
            final List<Giraffe> retval = new ArrayList<Giraffe>();

            for (final String line : giraffeData)
            {
                final String[] nameAndHeight = line.split("[ ]+", 2);
                final Giraffe giraffe = new Giraffe(nameAndHeight);
                retval.add(giraffe);
            }

            return retval;
        }

        /**
         * Parse the input strings section for the combat zone dimensions.
         * 
         * @param zoneSize
         *            A space separated list of zone dimensions
         * @return An array of the combat zone dimensions.
         */
        private int[] parseCombatZoneSize(final String zoneSize)
        {
            final int DIMENSIONS = 2;
            final String[] zoneSizeStrs = zoneSize.split("[ ]+", DIMENSIONS);
            final int[] retval = new int[DIMENSIONS];
            for (int i = 0; i < DIMENSIONS; i++)
            {
                retval[i] = Integer.parseInt(zoneSizeStrs[i]);
            }

            return retval;
        }

        /**
         * Parse the section of input strings into sectors and connect the
         * sectors together.
         * 
         * @return A collection of sectors
         */
        public Collection<Sector> parseCombatZone()
        {
            final Map<String, Sector> sectorDb = new HashMap<String, Sector>();

            for (int row = 0; row < combatZoneData.size(); row++)
            {
                final String line = combatZoneData.get(row);
                final String[] sectionHeights = line.split("[ ]+");
                if (sectionHeights.length != combatZoneColumns) { throw new IllegalStateException("Error: Expected "
                        + combatZoneColumns + " of column data but found " + sectionHeights.length + "."); }

                Sector leftSector = BORDER;
                for (int column = 0; column < sectionHeights.length; column++)
                {
                    final String heightStr = sectionHeights[column];
                    final String sectorId = row + "." + column;

                    Sector newSector = null;
                    if (heightStr.trim().equalsIgnoreCase(STARTTAG))
                    {
                        START = new MovementSector("S", Integer.MAX_VALUE);
                        newSector = START;
                    }
                    else if (heightStr.trim().equalsIgnoreCase(FINISHTAG))
                    {
                        newSector = FINISH;
                    }
                    else
                    {
                        final int height = Integer.parseInt(heightStr);
                        newSector = new MovementSector(sectorId, height);
                    }

                    sectorDb.put(sectorId, newSector);

                    newSector.leftIs(leftSector);
                    leftSector = newSector;

                    final String upSectorId = (row - 1) + "." + column;
                    final Sector upSector = sectorDb.get(upSectorId);
                    if (upSector != null)
                    {
                        newSector.upIs(upSector);
                    }
                }
            }

            return sectorDb.values();
        }
    }

    /**
     * Parse input from file or stdin into an array of strings.
     * 
     * I use this class to handle input for most of my CodeWar problems.
     */
    private static class InputHandler
    {
        /**
         * Include a helper method for a common task often needed by users of
         * this class.
         * 
         * @param args
         * @return The string following the input option (-i).
         */
        public static String extractFilenameByOption(final String args[])
        {
            final String fileOption = "-f";
            String retval = "";
            if (args.length > 0)
            {
                if (args[0].equalsIgnoreCase(fileOption))
                {
                    if (args.length >= 2)
                    {
                        // This handles directories with spaces.
                        for (int i = 1; i < args.length; i++)
                        {
                            retval += args[i] + " ";
                        }
                        retval.trim();
                    }
                }
            }

            return retval;
        }

        /**
         * A file can be used for input if it is set.
         */
        File myInputFile = null;

        /**
         * Construct an InputHandler using stdin.
         */
        public InputHandler()
        {
            // Use the defaults.
        }

        /**
         * Construct an InputHandler using the given filename.
         * 
         * @param filename
         */
        public InputHandler(final String filename)
        {
            setInputFile(filename);
        }

        /**
         * Read the input into memory.
         * 
         * @return An unmodifiable <code>List</code> of input lines.
         * @throws Exception
         *             For any issue.
         */
        public List<String> readInput() throws Exception
        {
            final List<String> retval = new ArrayList<String>();

            final InputStream inputStream = getInputStream();
            final BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            if (in != null)
            {
                if (in.ready())
                {
                    for (String line = in.readLine(); line != null; line = in.readLine())
                    {
                        retval.add(line);
                    }
                }
                in.close();
            }
            else
            {
                throw new IllegalStateException("Error: no reader");
            }

            return Collections.unmodifiableList(retval);
        }

        /**
         * Use a file for input instead of stdin.
         * 
         * @param filename
         * @throws FileNotFoundException
         */
        private void setInputFile(final String filename)
        {
            if ((filename != null) && (filename.length() > 0))
            {
                final File inputFile = new File(filename);
                if (inputFile.exists())
                {
                    myInputFile = inputFile;
                }
            }
        }

        /**
         * Create an input stream. If we have a filename then use it, otherwise
         * use stdin.
         * 
         * @return
         * @throws FileNotFoundException
         */
        private InputStream getInputStream() throws FileNotFoundException
        {
            InputStream inputStream = System.in;

            if (myInputFile != null)
            {
                inputStream = new FileInputStream(myInputFile);
            }

            return inputStream;
        }
    }
}
