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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * <pre>
 * codewars 2011 Problem name: Fish Tales
 * 
 * Time : 9 hours 
 * Difficulty : difficult (the concepts were not difficult, but there ended up being a lot of code)
 * 
 * @author Michael Scheetz
 * 
 */
public class FishTales
{
    List<String> solve(final List<String> input)
    {
        validateInputSize(input);
        final Map<String, String> nameClue = extractNamesAndClues(input);
        final Collection<Clue> clues = convertCluesIntoObjects(nameClue.values(), nameClue.keySet());
        final TruthTable table = new TruthTable(nameClue.keySet());
        final List<String> soln = table.solve(clues);
        return soln;
    }

    private void validateInputSize(final List<String> input)
    {
        if (input == null)
        {
            throw new IllegalArgumentException("null input is not allowed.");
        }
        final int expectedInputClueCount = Position.values().length;
        if (input.size() != expectedInputClueCount)
        {
            throw new IllegalArgumentException("exactly " + expectedInputClueCount + " lines of input is required.");
        }
    }

    private Map<String, String> extractNamesAndClues(final List<String> input)
    {
        final Map<String, String> nameClue = new HashMap<String, String>();
        for (final String line : input)
        {
            final String[] nameAndClue = line.split(":");
            if (nameAndClue.length != 2)
            {
                throw new IllegalArgumentException("Invalid input for line: " + line);
            }

            final String name = nameAndClue[0].trim();
            final String rawClue = nameAndClue[1].trim();
            final String clue = convertRelativeToAbsoluteReferences(name, rawClue);
            nameClue.put(name, clue);
        }

        return nameClue;
    }

    private Collection<Clue> convertCluesIntoObjects(final Collection<String> clues, final Collection<String> names)
    {
        final Collection<Clue> retval = new ArrayList<Clue>();

        for (final String clueTxt : clues)
        {
            // grammar always has 3 tokens
            final String[] tokens = clueTxt.split(" ");
            if (tokens.length != 3)
            {
                throw new IllegalArgumentException("Malformed clue: " + clueTxt + ":" + Arrays.toString(tokens));
            }

            // first token is always a name
            final String name1 = tokens[0];
            if (!names.contains(name1))
            {
                throw new IllegalArgumentException("First token should be a name: " + clueTxt + ":"
                        + Arrays.toString(tokens));
            }

            // second token is always a Quality
            final Quality quality = Quality.valueOf(tokens[1]);
            if (quality == null)
            {
                throw new IllegalArgumentException("Second token is not recognized: " + clueTxt + ":"
                        + Arrays.toString(tokens));
            }

            // third token is either a name or a position
            final String token3 = tokens[2];
            final Clue clue = quality.createClue(name1, token3, names);
            retval.add(clue);
        }

        return retval;
    }

    static String convertRelativeToAbsoluteReferences(final String name, final String rawClue)
    {
        return rawClue.replaceFirst("I ", name + " ");
    }

    private enum Position
    {
        FIRST, SECOND, THIRD, LAST
    }

    private enum Quality
    {
        WAS
        {
            @Override
            public Clue createClue(final String name1, final String positionStr, final Collection<String> notUsed)
            {
                final Position position = Position.valueOf(positionStr);
                if (position == null)
                {
                    throw new IllegalArgumentException("Third token should be a position: " + positionStr);
                }
                return new ClueWas(name1, position);
            }

        }

        ,
        BEAT
        {
            @Override
            public Clue createClue(final String name1, final String name2, final Collection<String> validNames)
            {
                if (!validNames.contains(name2))
                {
                    throw new IllegalArgumentException("Third token should be a name: " + name2);
                }
                return new ClueBeat(name1, name2);
            }
        };

        public abstract Clue createClue(final String name1, final String token3, Collection<String> validNames);
    }

    static enum Truth
    {
        True, False, Unknown
    }

    public interface Clue
    {
        Collection<TruthCell> getEasyTruths();

        Collection<TruthCell> getHardTruths(final Map<String, Integer> bestPos, final Map<String, Integer> worstPos);
    }

    static class ClueBeat implements Clue
    {
        private final String name1;
        private final String name2;

        public ClueBeat(final String name1, final String name2)
        {
            this.name1 = name1;
            this.name2 = name2;
        }

        /**
         * <pre>
         * Since this clue was a lie, we know 2 things:
         * 1. name1 was not first.
         * 2. name2 was not last.
         * 
         * We might be able to determine more from this clue with information on
         * the other players position.
         */
        @Override
        public Collection<TruthCell> getEasyTruths()
        {
            final TruthCell truth1 = new TruthCell(name1, Position.FIRST.ordinal());
            truth1.setFalse();
            final TruthCell truth2 = new TruthCell(name2, Position.LAST.ordinal());
            truth2.setFalse();

            final Collection<TruthCell> retval = new ArrayList<TruthCell>();
            retval.add(truth1);
            retval.add(truth2);
            return retval;
        }

        /**
         * Iterate over these truths to get better and better results.
         */
        @Override
        public Collection<TruthCell> getHardTruths(final Map<String, Integer> bestPos,
                final Map<String, Integer> worstPos)
        {
            final Collection<TruthCell> retval = new ArrayList<TruthCell>();
            final int bestPositionOpenToPlayer2 = bestPos.get(name2);
            for (int i = Position.FIRST.ordinal(); i < bestPositionOpenToPlayer2; i++)
            {
                // player1 can't be better than player2's best open slot
                final TruthCell truthForPlayer1 = new TruthCell(name1, i);
                truthForPlayer1.setFalse();
                retval.add(truthForPlayer1);
            }
            final int worstPositionOpenToPlayer1 = worstPos.get(name1);
            for (int i = worstPositionOpenToPlayer1; i < Position.LAST.ordinal(); i++)
            {
                // player2 can't be worse than player1's worst open slot
                final TruthCell truthForPlayer2 = new TruthCell(name2, i);
                truthForPlayer2.setFalse();
                retval.add(truthForPlayer2);
            }
            return retval;
        }

        @Override
        public String toString()
        {
            return name1 + " beat " + name2;
        }
    }

    static class ClueWas implements Clue
    {
        String name;
        int position;

        public ClueWas(final String name, final Position position)
        {
            this.name = name;
            this.position = position.ordinal();
        }

        /**
         * Since this clue was a lie, we know that this player was not at this
         * position.
         */
        @Override
        public Collection<TruthCell> getEasyTruths()
        {
            final TruthCell truth = new TruthCell(name, position);
            truth.setFalse();
            final Collection<TruthCell> retval = new ArrayList<TruthCell>();
            retval.add(truth);
            return retval;
        }

        /**
         * This type of clue only has easy truths
         */
        @Override
        public Collection<TruthCell> getHardTruths(final Map<String, Integer> notUsed1,
                final Map<String, Integer> notUsed2)
        {
            return new ArrayList<TruthCell>();
        }

        @Override
        public String toString()
        {
            return name + " was " + position;
        }
    }

    static class TruthTable
    {
        private static final int MAX_ITERATIONS = 1000;
        Map<String, List<TruthCell>> rowsIndexedByName = new HashMap<String, List<TruthCell>>();
        Map<Integer, List<TruthCell>> columnsIndexedByPosition = new HashMap<Integer, List<TruthCell>>();
        Map<String, Integer> nameIndexMap = new HashMap<String, Integer>();

        /**
         * <pre>
         * Initialize the table cells to UNKNOWN.
         * Put the table cells into two indexed structures:
         * 1. by name 
         * 2. by position
         * 
         * @param names
         */
        public TruthTable(final Collection<String> names)
        {
            initializeColumns(names.size());
            int nameIndex = 0;
            for (final String name : names)
            {
                nameIndexMap.put(name, nameIndex++);
                final List<TruthCell> initRow = new ArrayList<TruthCell>();
                for (int i = 0; i < names.size(); i++)
                {
                    final TruthCell cell = new TruthCell(name, i);
                    initRow.add(cell);
                    addToColumn(cell);
                }
                rowsIndexedByName.put(name, initRow);
            }
        }

        private void initializeColumns(final int size)
        {
            for (Integer i = 0; i < size; i++)
            {
                final List<TruthCell> initColumn = new ArrayList<TruthCell>();
                columnsIndexedByPosition.put(i, initColumn);
            }
        }

        private void addToColumn(final TruthCell cell)
        {
            final int position = cell.getPosition();
            final String name = cell.getName();
            final List<TruthCell> column = columnsIndexedByPosition.get(position);
            final int nameIndex = nameIndexMap.get(name);
            column.add(nameIndex, cell);
        }

        Collection<TruthCell> getAllCells()
        {
            final Collection<TruthCell> retval = new ArrayList<TruthCell>();
            for (final List<TruthCell> row : rowsIndexedByName.values())
            {
                retval.addAll(row);
            }
            return retval;
        }

        public List<TruthCell> getTruthCellsByName(final String name)
        {
            return rowsIndexedByName.get(name);
        }

        public List<TruthCell> getTruthCellsByPosition(final int position)
        {
            return columnsIndexedByPosition.get(position);
        }

        public void setFalse(final String name, final int position)
        {
            final List<TruthCell> row = getTruthCellsByName(name);
            final TruthCell targetCell = row.get(position);
            targetCell.setFalse();
        }

        /**
         * If we know that a cell in the table is true then we also know that
         * all other cells in that row and column are false.
         * 
         * @param name
         * @param position
         */
        public void setTrue(final String name, final int position)
        {
            final List<TruthCell> row = getTruthCellsByName(name);
            final List<TruthCell> column = getTruthCellsByPosition(position);

            final TruthCell targetCell = row.get(position);
            targetCell.setTrue();

            final Set<TruthCell> all = new HashSet<TruthCell>();
            all.addAll(row);
            all.addAll(column);
            all.remove(targetCell);
            for (final TruthCell cell : all)
            {
                if (cell.isTrue())
                {
                    throw new IllegalStateException("Another cell in this row or column is already true: " + cell);
                }
                cell.setFalse();
            }
        }

        boolean isDone()
        {
            final Collection<TruthCell> all = getAllCells();
            for (final TruthCell cell : all)
            {
                if (cell.isUnknown())
                {
                    return false;
                }
            }
            return true;
        }

        public List<String> solve(final Collection<Clue> clues)
        {
            final Collection<TruthCell> easyClues = extractEasyClues(clues);
            addClues(easyClues);

            int iterations = 0;
            while (!isDone())
            {
                boolean hasChanges = true;

                final Collection<TruthCell> hardClues = extractHardClues(clues);
                addClues(hardClues);

                while (hasChanges)
                {
                    hasChanges = false;
                    hasChanges &= solveRows();
                    hasChanges &= solveColumns();

                    iterations++;
                    if (iterations > MAX_ITERATIONS)
                    {
                        throw new IllegalStateException("Solution has exceeded maximum number of iterations: "
                                + this.toString());
                    }
                }
            }

            final List<String> orderedNames = getOrderedNames();
            return orderedNames;
        }

        Collection<TruthCell> extractHardClues(final Collection<Clue> clues)
        {
            final Collection<TruthCell> retval = new ArrayList<TruthCell>();
            for (final Clue clue : clues)
            {
                final Map<String, Integer> bestPos = createBestPositionMap();
                final Map<String, Integer> worstPos = createWorstPositionMap();
                retval.addAll(clue.getHardTruths(bestPos, worstPos));
            }
            return retval;
        }

        private Map<String, Integer> createWorstPositionMap()
        {
            final Map<String, Integer> retval = new HashMap<String, Integer>();
            for (final Entry<String, List<TruthCell>> entries : rowsIndexedByName.entrySet())
            {
                final String name = entries.getKey();
                final List<TruthCell> positions = new ArrayList<TruthCell>(entries.getValue());
                final int bestPosition = extractWorstPossiblePosition(positions);
                retval.put(name, bestPosition);
            }
            return retval;
        }

        private Map<String, Integer> createBestPositionMap()
        {
            final Map<String, Integer> retval = new HashMap<String, Integer>();
            for (final Entry<String, List<TruthCell>> entries : rowsIndexedByName.entrySet())
            {
                final String name = entries.getKey();
                final List<TruthCell> positions = new ArrayList<TruthCell>(entries.getValue());
                final int worstPosition = extractBestPossiblePosition(positions);
                retval.put(name, worstPosition);
            }
            return retval;
        }

        static int extractWorstPossiblePosition(final List<TruthCell> positions)
        {
            int worstPosition = Position.LAST.ordinal();
            Collections.reverse(positions);
            for (final TruthCell truth : positions)
            {
                if (truth.isTrue() || truth.isUnknown())
                {
                    worstPosition = truth.getPosition();
                    break;
                }
            }
            return worstPosition;
        }

        static int extractBestPossiblePosition(final List<TruthCell> positions)
        {
            int bestPosition = Position.FIRST.ordinal();
            for (final TruthCell truth : positions)
            {
                if (truth.isTrue() || truth.isUnknown())
                {
                    bestPosition = truth.getPosition();
                    break;
                }
            }
            return bestPosition;
        }

        private Collection<TruthCell> extractEasyClues(final Collection<Clue> clues)
        {
            final Collection<TruthCell> retval = new ArrayList<TruthCell>();
            for (final Clue clue : clues)
            {
                retval.addAll(clue.getEasyTruths());
            }
            return retval;
        }

        private void addClues(final Collection<TruthCell> clues)
        {
            if (clues != null)
            {
                for (final TruthCell clue : clues)
                {
                    final TruthCell targetCell = getCell(clue);
                    assignKnownValue(targetCell, clue);
                }
            }
        }

        private void assignKnownValue(final TruthCell targetCell, final TruthCell clue)
        {
            if (clue.isTrue())
            {
                targetCell.setTrue();
            }
            else
            {
                if (clue.isUnknown())
                {
                    throw new IllegalStateException("Clue cannot have unknown value: " + clue);
                }
                targetCell.setFalse();
            }
        }

        private TruthCell getCell(final TruthCell clue)
        {
            final List<TruthCell> row = getTruthCellsByName(clue.getName());
            final TruthCell targetCell = row.get(clue.getPosition());
            return targetCell;
        }

        private List<String> getOrderedNames()
        {
            final List<String> names = new ArrayList<String>();
            for (int position = 0; position < columnsIndexedByPosition.size(); position++)
            {
                final Collection<TruthCell> column = getTruthCellsByPosition(position);
                final TruthCell cell = getTrueCell(column);
                names.add(cell.getName());
            }

            return names;
        }

        private TruthCell getTrueCell(final Collection<TruthCell> column)
        {
            for (final TruthCell cell : column)
            {
                if (cell.isTrue())
                {
                    return cell;
                }
            }
            throw new IllegalStateException("Table must be solved before calling this method");
        }

        private boolean solveColumns()
        {
            boolean hasChanges = false;
            for (final List<TruthCell> column : columnsIndexedByPosition.values())
            {
                hasChanges &= determineTrueCellFromKnownFalses(column);
            }

            return hasChanges;
        }

        private boolean solveRows()
        {
            boolean hasChanges = false;
            for (final List<TruthCell> row : rowsIndexedByName.values())
            {
                hasChanges &= determineTrueCellFromKnownFalses(row);
            }
            return false;
        }

        private boolean determineTrueCellFromKnownFalses(final List<TruthCell> line)
        {
            boolean hasChanges = false;
            int unknownCount = 0;
            TruthCell lastUnkownCell = null;
            for (final TruthCell cell : line)
            {
                if (cell.isTrue())
                {
                    // already solved this cross section.
                    return false;
                }
                if (cell.isUnknown())
                {
                    unknownCount++;
                    lastUnkownCell = cell;
                }
            }

            if (unknownCount == 1)
            {
                final String name = lastUnkownCell.getName();
                final int position = lastUnkownCell.getPosition();
                setTrue(name, position);
                hasChanges = true;
            }

            return hasChanges;
        }

        @Override
        public String toString()
        {
            final StringBuilder retval = new StringBuilder();
            for (final List<TruthCell> row : rowsIndexedByName.values())
            {
                retval.append(row);
            }

            return retval.toString();
        }
    }

    static class TruthCell
    {
        private final String name;
        private final int position;
        private Truth value = Truth.Unknown;

        public TruthCell(final String name, final int position)
        {
            this.name = name;
            this.position = position;
        }

        public TruthCell(final String name, final int position, final Truth value)
        {
            this(name, position);
            this.value = value;
        }

        public boolean isTrue()
        {
            return value == Truth.True;
        }

        public boolean isUnknown()
        {
            return value == Truth.Unknown;
        }

        public void setTrue()
        {
            value = Truth.True;
        }

        public void setFalse()
        {
            value = Truth.False;
        }

        public int getPosition()
        {
            return position;
        }

        public String getName()
        {
            return name;
        }

        @Override
        public boolean equals(final Object thatObj)
        {
            if (!(thatObj instanceof TruthCell))
            {
                return false;
            }
            final TruthCell that = (TruthCell) thatObj;

            return this.name == that.name && this.position == that.position;
        }

        @Override
        public int hashCode()
        {
            int retval = 31;
            retval += name.hashCode() * 31 ^ 2;
            retval += position * 31 ^ 1;
            return retval;
        }

        @Override
        public String toString()
        {
            return "(" + name + ", " + position + ") = " + value;
        }

        public Truth getValue()
        {
            return value;
        }
    }

    /**
     * Read the input and solve the problem. Passing exceptions all the way up
     * makes the code simpler.
     * 
     * @param args
     *            No used
     * @throws Exception
     *             For any issue.
     */
    public static void main(final String[] args) throws Exception
    {
        final String filename = InputHandler.extractFilenameByOption(args);
        final InputHandler inputHandler = new InputHandler(filename);
        inputHandler.ignoreBlankLines();
        final List<String> input = inputHandler.readInput();

        final FishTales problem = new FishTales();
        final List<String> solution = problem.solve(input);
        final StringBuilder output = new StringBuilder();
        for (final String name : solution)
        {
            output.append(name);
            output.append(" ");
        }
        System.out.println(output.toString().trim());
    }

    /**
     * I use this class to handle input for most of my CodeWar problems.
     */
    public static class InputHandler
    {
        /**
         * A file can be used for input if it is set.
         */
        File myInputFile = null;
        private boolean isIgnoreBlankLines = false;

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
            final StringBuilder retval = new StringBuilder();
            if (args.length >= 2)
            {
                if (args[0].equalsIgnoreCase(fileOption))
                {
                    // This handles directories with spaces.
                    for (int i = 1; i < args.length; i++)
                    {
                        retval.append(args[i]);
                        retval.append(" ");
                    }
                }
            }
            return retval.toString().trim();
        }

        public void ignoreBlankLines()
        {
            isIgnoreBlankLines = true;
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
         * Use a file for input instead of stdin.
         * 
         * @param filename
         * @throws FileNotFoundException
         */
        private void setInputFile(final String filename)
        {
            if (filename != null && filename.length() > 0)
            {
                final File inputFile = new File(filename);
                if (inputFile.exists())
                {
                    myInputFile = inputFile;
                }
            }
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
            final BufferedReader in = createReader();
            if (in.ready())
            {
                for (String line = in.readLine(); line != null; line = in.readLine())
                {
                    if (isIgnoreBlankLines)
                    {
                        if (line.isEmpty())
                        {
                            continue;
                        }
                    }
                    retval.add(line);
                }
            }
            in.close();

            return Collections.unmodifiableList(retval);
        }

        private BufferedReader createReader() throws FileNotFoundException
        {
            final InputStream inputStream = getInputStream();
            final BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            return in;
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
            if (myInputFile == null)
            {
                return System.in;
            }
            else
            {
                return new FileInputStream(myInputFile);
            }
        }
    }
}
