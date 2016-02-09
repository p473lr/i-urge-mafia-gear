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
 * codewars 2009
 * Problem name: Security Door
 * 
 * Time : 1 hour
 * Difficulty : very easy
 *  
 * @author Michael Scheetz
 *
 */
public class SecurityDoor
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
        
        new SecurityDoor(input);
    }

    /**
     * Iterate through the input, 
     * handle the commands,
     * and output the state. 
     * 
     * @param input
     */
    public SecurityDoor(List<String> input)
    {
        DoorState currentState = new UninitializedState();
        
        for(String commandString : input)
        {
            CommandInput command = new CommandInput(commandString);
            DoorState nextState = currentState.handle(command);
            System.out.println(nextState.show());
            currentState = nextState.updateCodes();
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

    /**
     * Enumerate the possible commands expected from the inputs.
     *
     */
    private enum Command
    {
        SYNC,
        OPEN,
        END,
    }
    
    /**
     * A class to encapsulate the input data from a single command.
     */
    private class CommandInput
    {
        /**
         * The command.
         */
        private Command myCommand;
        /**
         * The access code. 
         */
        private int myCode;
        
        /**
         * Construct an object with a command and access code.
         * 
         * @param commandString
         */
        public CommandInput(String commandString)
        {
            String [] inputStrings = commandString.split(" ");
            myCommand = Command.valueOf(inputStrings[0]);
            myCode = Integer.parseInt(inputStrings[1]);
        }

        /**
         * Return <code>true</code> if this object is the given command.
         * 
         * @param sync
         * @return
         */
        public boolean isType(Command sync)
        {
            return myCommand == sync;
        }
        
        /**
         * Return this command type.
         * 
         * @return
         */
        public Command getCommand()
        {
            return myCommand;
        }

        /**
         * Return this access code.
         * 
         * @return
         */
        public int getCode()
        {
            return myCode;
        }
    }

    /**
     * Parent class for the state pattern.
     *
     */
    private interface DoorState
    {
        /**
         * Handle the next input state.
         * 
         * @param input
         * @return
         */
        public DoorState handle(CommandInput input);

        /**
         * Output the current state.
         * 
         * @return
         */
        public String show(); 

        /**
         * This method is required to return the correct
         * state after the current state has been output.
         * 
         * @return
         */
        public DoorState updateCodes();
    }
    
    /**
     * Must <code>SYNC</code> to get out of this state.
     *
     */
    private class UninitializedState implements DoorState
    {
        /* (non-Javadoc)
         * @see SecurityDoor.DoorState#updateCodes()
         */
        public DoorState handle(CommandInput input)
        {
            if (input.isType(Command.SYNC))
            {
                return new SyncronizedState(input.getCode());
            }
            else
            {
                return new InvalidState(this);
            }
        }

        /* (non-Javadoc)
         * @see SecurityDoor.DoorState#updateCodes()
         */
        public DoorState updateCodes()
        {
            return this;
        }
        
        /* (non-Javadoc)
         * @see SecurityDoor.DoorState#updateCodes()
         */
        public String show()
        {
            throw new UnsupportedOperationException();
       }
    }

    /**
     * This state prints out "INVALID" and then returns
     * to then previous state.
     *
     */
    private class InvalidState implements DoorState
    {
        private DoorState myReturnState;
        
        /**
         * @param uninitializedState
         */
        public InvalidState(DoorState returnState)
        {
            myReturnState = returnState;
        }

        /* (non-Javadoc)
         * @see SecurityDoor.DoorState#updateCodes()
         */
        public DoorState handle(CommandInput input)
        {
            throw new UnsupportedOperationException();
        }

        /* (non-Javadoc)
         * @see SecurityDoor.DoorState#updateCodes()
         */
        public DoorState updateCodes()
        {
            return myReturnState;
        }
        
        /* (non-Javadoc)
         * @see SecurityDoor.DoorState#updateCodes()
         */
        public String show()
        {
            return "INVALID";
        }
    }
    
    /**
     * This state is re-synchronizes the access codes based on the given
     * code.  
     *
     */
    private class SyncronizedState implements DoorState
    {
        /**
         * The code that was issued with this command.
         */
        private int myCode;
        
        /**
         * Construct this object with the new code.
         * 
         * @param code
         */
        public SyncronizedState(int code)
        {
            myCode = code;
        }
        
        /* (non-Javadoc)
         * @see SecurityDoor.DoorState#updateCodes()
         */
        public DoorState handle(CommandInput input)
        {
            throw new UnsupportedOperationException();
        }

        /* (non-Javadoc)
         * @see SecurityDoor.DoorState#updateCodes()
         */
        public DoorState updateCodes()
        {
            return new WaitState(myCode);
        }

        /* (non-Javadoc)
         * @see SecurityDoor.DoorState#updateCodes()
         */
        public String show()
        {
            return "SYNCHRONIZED";
        }
    }
    
    /**
     * This is the state which most states go to after a command.
     * It handles the valid codes.
     *
     */
    private class WaitState implements DoorState
    {
        /**
         * The number of codes that are stored as valid.
         */
        private final int VALID_CODE_COUNT = 3;
        /**
         * The list of valid access codes.
         */
        int [] myValidCodes;
        
        /**
         * This constructor is used to update the valid codes.
         * 
         * @param myCode
         */
        public WaitState(int code)
        {
            myValidCodes = new int[VALID_CODE_COUNT];
            int [] validCodes = createValidCodes(code);
            System.arraycopy(validCodes, 0, myValidCodes, 0, VALID_CODE_COUNT);
        }

        /**
         * Return an array of valid codes based on the given code.
         * 
         * @param code
         * @return
         */
        private int[] createValidCodes(int code)
        {
            int [] retval = new int[VALID_CODE_COUNT];
            int oldCode = code;
            for (int i=0 ; i<VALID_CODE_COUNT ; i++)
            {
                int newCode = ( 17 * oldCode + 91 ) % 256;
                retval[i] = newCode;
                oldCode   = newCode;
            }
            
            return retval;
        }
        
        /**
         * Validate that the given code is in the list of valid codes.
         * 
         * @param code
         * @return <code>true</code> if the given code is valid.
         */
        private boolean validate(int code)
        {
            for (int validCode : myValidCodes)
            {
                if (code == validCode)
                {
                    return true;
                }
            }
            
            return false;
        }

        /* (non-Javadoc)
         * @see SecurityDoor.DoorState#updateCodes()
         */
        public DoorState handle(CommandInput input)
        {
            DoorState retval = null;
            
            int code = input.getCode();
            switch(input.getCommand())
            {
                case SYNC:
                    retval = new SyncronizedState(code);
                    break;
                case OPEN:
                    if(validate(code))
                    {
                        retval = new OpenState(code);
                    }
                    else
                    {
                        retval = new InvalidState(this);
                    }
                    break;
                case END:
                    retval = new EndState();
                    break;
                default:
                    throw new IllegalArgumentException();
            }
            
            return retval;
        }
        
        /* (non-Javadoc)
         * @see SecurityDoor.DoorState#updateCodes()
         */
        public DoorState updateCodes()
        {
            throw new UnsupportedOperationException();
        }

        /* (non-Javadoc)
         * @see SecurityDoor.DoorState#updateCodes()
         */
        public String show()
        {
            throw new UnsupportedOperationException();
       }
    }
    
    /**
     * Used to indicate that the current state is open.
     *
     */
    private class OpenState implements DoorState
    {
        /**
         * The access code given.
         */
        private int myCode;
        
        /**
         * Instantiate a new object with the given code.
         * 
         * @param code
         */
        public OpenState(int code)
        {
            myCode = code;
        }

        /* (non-Javadoc)
         * @see SecurityDoor.DoorState#updateCodes()
         */
        public DoorState handle(CommandInput input)
        {
            throw new UnsupportedOperationException();
        }
        
        /* (non-Javadoc)
         * @see SecurityDoor.DoorState#updateCodes()
         */
        public DoorState updateCodes()
        {
            return new WaitState(myCode);
        }

        /* (non-Javadoc)
         * @see SecurityDoor.DoorState#updateCodes()
         */
        public String show()
        {
            return "UNLOCK";
        }
    }
    
    /**
     * Once this state is reached, no other state can be reached.
     *
     */
    private class EndState implements DoorState
    {
        /* (non-Javadoc)
         * @see SecurityDoor.DoorState#handle(SecurityDoor.CommandInput)
         */
        public DoorState handle(CommandInput input)
        {
            return this;
        }

        /* (non-Javadoc)
         * @see SecurityDoor.DoorState#show()
         */
        public String show()
        {
            return "";
        }

        /* (non-Javadoc)
         * @see SecurityDoor.DoorState#updateCodes()
         */
        public DoorState updateCodes()
        {
            return this;
        }
    }
}