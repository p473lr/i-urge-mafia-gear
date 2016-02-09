import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * codewars 2009
 * Problem name: Word Construction Engineer
 * 
 * Time : 2 hours
 * Difficulty : easy
 *  
 * @author Michael Scheetz
 *
 */
public class WordConstruction
{
    /**
     * Read the input and solve the problem.
     * Passing exceptions all the way up makes the code simpler. 
     * 
     * @param args No used
     * @throws Exception For any issue.
     */
    public static void main(String[] args) throws Exception 
    {
        String filename = InputHandler.extractFilenameByOption(args);
        InputHandler inputHandler = new InputHandler(filename);
        List<String> input = inputHandler.readInput();
        
        new WordConstruction(input);
    }

    /**
     * Iterate through all of the input, build and output the answer strings.
     * 
     * @param input
     */
    public WordConstruction(List<String> input)
    {
        for(String line : input)
        {
            if (line.length() > 1)
            {
                Order order = new Order(line.toUpperCase());
                String answer = order.build();
                System.out.println(answer);
            }
        }
    }

    /**
     * A class to encapsulate information from a single order.
     */
    private class Order
    {
        /**
         * The Build-to-order word.
         */
        private String  myWord;
        
        /**
         * The community words to search. 
         */
        private List<String> myCommunityWords = new ArrayList<String>();
        
        /**
         * Construct an order from the input string.
         * 
         * @param commandString
         */
        public Order(String orderString)
        {
            String [] orderParts = orderString.split(" ");
            int count = Integer.valueOf(orderParts[0]);
            
            if (count > 0)
            {
                myWord = orderParts[1];
                
                for (int i=2 ; i<orderParts.length ; i++)
                {
                    myCommunityWords.add(orderParts[i]);
                }
                
                if (myCommunityWords.size() != count-1)
                {
                    throw new IllegalArgumentException("Number of community words does not match the expected number.");
                }
            }
        }

        /**
         * Find all of the matching substrings and make sure they make the whole word.
         */
        public String build()
        {
            List<Match> frontWords = findMatchingSubStrings(myWord, myCommunityWords);
            String reversedWord = new StringBuilder(myWord).reverse().toString();
            List<String> reversedCommunityWords = reverseCollectionWords(myCommunityWords);
            List<Match> reversedBackWords  = findMatchingSubStrings(reversedWord, reversedCommunityWords);
            List<Match> backWords = reverseCollectionMatches(reversedBackWords);
            
            String answer = findSolution(myWord, frontWords, backWords);
            return answer;
        }

        /**
         * The front and back lists are sorted so we only need to see that
         * the sum of their length is at least as long as the word we
         * are making. 
         * 
         * @param myWord2
         * @param frontWords
         * @param backWords
         * @return
         */
        private String findSolution(
                String word, 
                List<Match> frontWords,
                List<Match> backWords
            )
        {
            StringBuilder retval = new StringBuilder();
            retval.append(word);
            retval.append(" ");
            
            Match firstMatch = frontWords.get(0);
            String firstWord = firstMatch.getSub();

            Match lastMatch = backWords.get(0);
            String lastWord = lastMatch.getSub();

            String mergeWord = firstWord + lastWord;
            if(mergeWord.length() >= word.length())
            {
                retval.append(firstMatch.getWord());
                retval.append(" ");
                retval.append(lastMatch.getWord());
                return retval.toString();
            }
            
            return "No solution found for " + word;
        }

        /**
         * Reverse a collection of words so that we don't have to write some nearly 
         * duplicate code to operate on both sides of a string.
         * 
         * @param communityWords
         * @return
         */
        private List<String> reverseCollectionWords(List<String> communityWords)
        {
            List<String> retval = new ArrayList<String>();
            
            for(String word : communityWords)
            {
                String reversedWord = new StringBuilder(word).reverse().toString();
                retval.add(reversedWord);
            }
            
            return retval;
        }

        /**
         * Reverse a collection of matches so we can undo the reversed words that
         * were passed to the search algorightm
         * 
         * @param reversedBackWords
         * @return
         */
        private List<Match> reverseCollectionMatches(List<Match> matches)
        {
            List<Match> retval = new ArrayList<Match>();
            
            for(Match match : matches)
            {
                Match reversedMatch = match.reverse();
                retval.add(reversedMatch);
            }
            
            return retval;
        }

        /**
         * Search through the list to find any words that have a substring of the 
         * word we want to build.
         * 
         * @param myReverseWord
         * @param myCommunityWords2
         * @return
         */
        private List<Match> findMatchingSubStrings(
                String buildWord,
                List<String> searchWords
            )
        {
            List<Match> retval = new ArrayList<Match>();
            for (String srchword : searchWords)
            {
                Match match = Match.getInstance(buildWord, srchword);
                if (match != null)
                {
                    retval.add(match);
                }
            }
            
            Collections.sort(retval, new MatchLengthComparator());
            return retval;
        }
    }
    
    /**
     * Use this comparator to sort the matches by length of its substring.
     * If the longest substrings are not enough then we failed to find a match.
     *
     */
    private class MatchLengthComparator implements Comparator<Match>
    {
        /* (non-Javadoc)
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        public int compare(Match o1, Match o2)
        {
            return o2.subLength() - o1.subLength();
        }
    }
    
    /**
     * Encapsulate a word and substring pair for words that can 
     * be used to construct the word we are constructing.
     *
     */
    private static class Match
    {
        /**
         * The whole word.
         */
        String myWord;
        /**
         * The substring which matches the word being constructed.
         */
        String mySub;

        /**
         * A constructor used by the reverse copy method.
         */
        private Match()
        {
        }
        
        /**
         * A constructor used by the getInstance method.
         * @param word
         * @param sub
         */
        private Match(String word, String sub)
        {
            myWord = word;
            mySub  = sub;
        }
        
        /**
         * A factory method for this class so a null can be returned
         * if there is no substring of the searchWord which matches the buildWord.
         * 
         * @param buildWord
         * @param searchWord
         * @return
         */
        public static Match getInstance(String buildWord, String searchWord)
        {
            Match retval = null;
            String sub = findLongestSubString(buildWord, searchWord);
            if(sub != null)
            {
                retval = new Match(searchWord, sub);
            }
            return retval;
        }
        
        /**
         * Find the longest substring from searchWord which matches the first n characters
         * of the buildWord.
         * 
         * @param buildWord
         * @param searchWord
         * @return
         */
        private static String findLongestSubString(String buildWord, String searchWord)
        {
            String longestSubString = null;
            for (int len=2; len < buildWord.length() ; len++)
            {
                String searchSubString = buildWord.substring(0, len);
                int index = searchWord.indexOf(searchSubString, 0);
                if (index >= 0)
                {
                    longestSubString = searchSubString;
                }
                else
                {
                    break;
                }
            }
            
            return longestSubString;
        }

        /**
         * Return the length of the substring.
         * Used by the comparator.
         * 
         * @return
         */
        public int subLength()
        {
            return mySub.length();
        }
        
        /**
         * Return the substring that was found.
         * 
         * @return
         */
        public String getSub()
        {
            return mySub;
        }
        
        /**
         * Return the whole word.
         * 
         * @return
         */
        public String getWord()
        {
            return myWord;
        }
        
        /**
         * Used for debugging.
         */
        public String toString()
        {
            return myWord + " " + mySub;
        }
        
        /**
         * Return a new object with the whole word and substring reversed.
         * 
         * @return
         */
        public Match reverse()
        {
            Match retval = new Match();
            retval.myWord = reverse(myWord);
            retval.mySub = reverse(mySub);
            return retval;
        }
        
        /**
         * Reverse a single word.
         * 
         * @param word
         * @return
         */
        private String reverse(String word)
        {
            return new StringBuilder(word).reverse().toString();
        }
    }

    /**
     * I use this class to handle input for most of my CodeWar problems.
     */
    private static class InputHandler
    {
        /**
         * Include a helper method for a common task often needed by 
         * users of this class.
         * 
         * @param args
         * @return The string following the input option (-i).
         */
        public static String extractFilenameByOption(String args[])
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
                        for(int i=1 ; i< args.length ; i++)
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
        }
        
        /**
         * Construct an InputHandler using the given filename.
         * 
         * @param filename
         */
        public InputHandler(String filename)
        {
            setInputFile(filename);
        }
        
        /**
         * Read the input into memory.
         * 
         * @return An unmodifiable <code>List</code> of input lines.
         * @throws Exception For any issue.
         */
        public List<String> readInput() throws Exception
        {
            List<String> retval = new ArrayList<String>();
            
            InputStream inputStream = getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
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
            
            return Collections.unmodifiableList(retval);
        }

        /**
         * Use a file for input instead of stdin.
         * 
         * @param filename
         * @throws FileNotFoundException 
         */
        private void setInputFile(String filename)
        {
            if (filename != null && filename.length() > 0)
            {
                File inputFile = new File(filename);
                if (inputFile.exists())
                {
                    myInputFile = inputFile;
                }
            }
        }
        
        /**
         * Create an input stream.  If we have a filename then use it,
         * otherwise use stdin.
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
