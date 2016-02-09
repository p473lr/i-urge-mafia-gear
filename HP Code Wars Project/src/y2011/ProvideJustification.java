import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <pre>
 * codewars 2011 Problem name: Provide Justification
 * 
 * Time : x hours 
 * Difficulty : easy
 *  * 
 * @author Michael Scheetz
 * 
 */
public class ProvideJustification
{
    private static final int MAX_LINE_LEN = 30;

    public List<String> solve(final String input)
    {
        final String[] terminatedString = input.split("#");
        final String[] tokens = terminatedString[0].split(" ");
        final List<String> output = fullJustify(tokens);
        final List<String> outputWithDots = convertSpaceToDot(output);
        return outputWithDots;
    }

    List<String> fullJustify(final String[] tokens)
    {
        final List<LineTokens> tokenLines = splitTokensIntoLines(tokens, MAX_LINE_LEN);
        final LineTokens lastLine = tokenLines.get(tokenLines.size() - 1);
        lastLine.isLastLine = true;

        final List<String> retval = new ArrayList<String>();
        for (final LineTokens tokensForLine : tokenLines)
        {
            final String line = tokensForLine.concatTokens();
            retval.add(line);
        }
        return retval;
    }

    List<LineTokens> splitTokensIntoLines(final String[] tokens, final int lineWidth)
    {
        final List<LineTokens> retval = new ArrayList<LineTokens>();
        LineTokens line = new LineTokens(lineWidth);
        for (final String token : tokens)
        {
            final boolean success = line.addToken(token);
            if (!success)
            {
                retval.add(line);
                line = new LineTokens(lineWidth);
                line.addToken(token);
            }
        }
        retval.add(line);

        return retval;
    }

    private List<String> convertSpaceToDot(final List<String> output)
    {
        final List<String> retval = new ArrayList<String>();
        for (final String line : output)
        {
            final String convertedLine = convertSpaceToDot(line);
            retval.add(convertedLine);
        }
        return retval;
    }

    private String convertSpaceToDot(final String line)
    {
        return line.replace(" ", ".");
    }

    public static class LineTokens
    {
        private static final String DELIM = " ";
        List<String> tokens = new ArrayList<String>();
        int finalWidth;
        int singleSpacedWidth;
        boolean isLastLine = false;

        public LineTokens(final int lineWidth)
        {
            finalWidth = lineWidth;
        }

        public LineTokens(final int lineWidth, final String... tokens)
        {
            this(lineWidth);
            for (final String token : tokens)
            {
                final boolean success = addToken(token);
                if (!success)
                {
                    throw new IllegalAccessError("Line width violation when adding token:" + token);
                }
            }
        }

        public boolean addToken(final String token)
        {
            if (token == null || token.isEmpty())
            {
                throw new IllegalArgumentException("Invalid token.");
            }

            final int newSingleSpacedWidth = calculateTokenWidthWithSpace(token) + singleSpacedWidth;
            if (newSingleSpacedWidth > finalWidth)
            {
                return false;
            }

            tokens.add(token);
            singleSpacedWidth = newSingleSpacedWidth;
            return true;
        }

        int calculateTokenWidthWithSpace(final String token)
        {
            int retval = 0;
            retval += token.length();
            if (retval > 0 && !isFirstToken())
            {
                retval++;
            }

            return retval;
        }

        private boolean isFirstToken()
        {
            return tokens.isEmpty();
        }

        public String concatTokens()
        {
            if (isLastLine)
            {
                return concatWithoutPadding();
            }
            return concatWithPadding();
        }

        String concatWithoutPadding()
        {
            final StringBuilder retval = new StringBuilder();
            for (final String token : tokens)
            {
                retval.append(token);
                retval.append(DELIM);
            }
            return retval.toString().trim();
        }

        String concatWithPadding()
        {
            if (tokens.isEmpty())
            {
                return "";
            }

            final StringBuilder retval = new StringBuilder();
            final int minPadding = finalWidth / singleSpacedWidth;
            final int numberOfSpacesWithExtraPadding = finalWidth - singleSpacedWidth
                    - (tokens.size() * (minPadding - 1));

            final StringBuilder padding = new StringBuilder();
            for (int i = 0; i < minPadding; i++)
            {
                padding.append(DELIM);
            }
            for (int i = 0; i < tokens.size(); i++)
            {
                final String token = tokens.get(i);
                retval.append(token);
                retval.append(padding);
                if (i < numberOfSpacesWithExtraPadding)
                {
                    retval.append(DELIM);
                }
            }
            return retval.toString().trim();
        }

        @Override
        public String toString()
        {
            return concatTokens();
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
        final List<String> input = inputHandler.readInput();

        final ProvideJustification problem = new ProvideJustification();
        final List<String> solution = problem.solve(input.iterator().next());
        for (final String line : solution)
        {
            System.out.println(line);
        }
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
