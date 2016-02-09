
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;


public class prob21
{
    public String[] inputLines;
    public Statement[] ruleStatements;
    public String[] names;
    int namesIndex = 0;
    
    public String[] solution = new String[6];
    
    public prob21()
    {
        // Step 1 Read Input
        readInput();
        
        // Step 2 Parse input lines
        String currentLine = null;
        int indexOf = -1;
        Statement currentStatement = null;
        String[] currentWords = null;
        ruleStatements = new Statement[6];
        names = new String[5];
        
        for (int line = 0; line < 6; line++)
        {
            currentLine = inputLines[line];
            indexOf = -1;
            
            // Split line on spaces
            currentWords = currentLine.split(" ");
            // Check for Statement 2
            indexOf = currentLine.indexOf(" OR ");
            if (indexOf > 0)
            {
                currentStatement = new Statement(2);
                currentStatement.name = currentWords[0];
                currentStatement.floorX = new Integer(currentWords[4]);
                currentStatement.floorY = new Integer(currentWords[6]);
            }
            
            // Check for Statement 1
            indexOf = currentLine.indexOf("NOT ON FLOOR ");
            if (indexOf > 0)
            {
                currentStatement = new Statement(1);
                currentStatement.name = currentWords[0];
                currentStatement.floorX = new Integer(currentWords[4]);
            }
            
            // Check for Statement 3
            indexOf = currentLine.indexOf(" HIGHER");
            if (indexOf > 0)
            {
                currentStatement = new Statement(3);
                currentStatement.name = currentWords[0];
                currentStatement.otherName = currentWords[5];
            }
            
            // Check for Statement 5
            indexOf = currentLine.indexOf("NOT ON ADJACENT ");
            if (indexOf > 0)
            {
                currentStatement = new Statement(5);
                currentStatement.name = currentWords[0];
                currentStatement.otherName = currentWords[6];
            }
            
            // Assume Statement 4
            if (null == currentStatement)
            {
                currentStatement = new Statement(4);
                currentStatement.name = currentWords[0];
                currentStatement.otherName = currentWords[5];
            }
            
            addName(currentStatement.name, currentStatement.otherName);
            ruleStatements[line] = currentStatement;
            currentStatement = null;
            
        }
       
        createPossibleSolution();
    }

    private void createPossibleSolution()
    {
        String[] possibleSolution = new String[6];
        
        for (int first = 1; first <= 5; first++)
        {
            int floorOne = fillNextOpenFloor(possibleSolution, 1, first, names[0]);
            
            for (int second = 1; second <= 4; second++)
            {
                int floorTwo = fillNextOpenFloor(possibleSolution, floorOne, second, names[1]);
                for (int third = 1; third <= 3; third++)
                {
                    int floorThree = fillNextOpenFloor(possibleSolution, floorTwo, third, names[2]);
                    for (int fourth = 1; fourth <= 2; fourth++)
                    {
                        int floorFour = fillNextOpenFloor(possibleSolution, floorThree, fourth, names[3]);
                        for (int fifth = 1; fifth <= 1; fifth++)
                        {
                            int floorFive = fillNextOpenFloor(possibleSolution, floorFour, fifth, names[4]);
                            if (true ==testSolution(possibleSolution))
                            {
                                printArray(possibleSolution);
                            }
                            possibleSolution[floorFive] = null;
                        }
                        possibleSolution[floorFour] = null;
                    }
                    possibleSolution[floorThree] = null;
                }
                possibleSolution[floorTwo] = null;
            }
            possibleSolution[floorOne] = null; 
        }
    }   
    
    private int fillNextOpenFloor(String[] aPossibleSolution, int startFloor, int nthEmptySlot, String name)
    {
        int nextFloor = -1;
        int floor = 0;
        while(nthEmptySlot != 0)
        {
            nextFloor = (startFloor+floor);
            if (nextFloor > 5)
            { 
                nextFloor = nextFloor % 5; 
                if (nextFloor == 0)
                { nextFloor++; }
            }
            if (null == aPossibleSolution[nextFloor] || true == aPossibleSolution[nextFloor].equals(""))
            {
                nthEmptySlot--;
            }
            floor++;
        }
        aPossibleSolution[nextFloor] = name;
        return nextFloor;
    }
    
    private boolean testSolution(String[] testSolution)
    {
        boolean answerFound = false;
        int statementMatches = 0;
        
        for (int ruleIndex = 0; ruleIndex < 6; ruleIndex++)
        {
            if (true == ruleStatements[ruleIndex].satisfied(testSolution))
            {
                statementMatches++;
            }
        }
        
        if (6 == statementMatches)
        {
            System.out.println("Solution found");
            answerFound = true;
        }
        return answerFound;
    }
    
    private void addName(String name, String otherName)
    {
        boolean foundName = false;
        boolean foundOtherName = false;
        for (int index = 0; index < namesIndex; index++)
        {
            if (null != names[index] && names[index].equals(name))
            {
                foundName = true;
            }
            if (null != names[index] && names[index].equals(otherName))
            {
                foundOtherName = true;
            }
        }
        
        if (null != name && false == foundName)
        {
            names[namesIndex] = name;
            namesIndex++;
        }
        
        if (null != otherName && false == foundOtherName)
        {
            names[namesIndex] = otherName;
            namesIndex++;
        }
    }
    
    private void readInput()
    {
        inputLines = new String[6];
        try 
        {
            BufferedReader standardIn = new BufferedReader(new InputStreamReader(System.in));
            String aLine = null;
            for (int line = 0; line < 6; line++)
            {
                aLine = standardIn.readLine();
                if (null == aLine)
                {
                    System.out.println("read NULL line!");
                    System.exit(123);
                }
                inputLines[line] = aLine;
            }
            
        }
        catch(IOException ioEx)
        {
            ioEx.printStackTrace();
        }
    }
    
    private void printArray(String[] array)
    {
        for(int i =0; i < array.length;i++)
        { System.out.println("[" + i + ": " + array[i] + "]"); }
    }
        
    public static void main(String[] args)
    {
        prob21 instance = new prob21();
    }

}

class Statement
{
    int type = -1;
    int floorX = -1;
    int floorY = -1;
    String name = null;
    String otherName = null;
    
    public Statement(int type)
    {
        this.type = type;
    }
    
    public boolean satisfied(String[] aSolution)
    {
        boolean isStatementSatisfied = true;
        int nameFloor = findFloor(aSolution, name);
        int otherFloor = findFloor(aSolution, otherName);
        
        switch (type)
        {
            case 1:
            {
                if (aSolution[floorX].equals(name))
                {
                    isStatementSatisfied = false;
                }
                break;
            }
            case 2:
            {
                if (aSolution[floorX].equals(name) || aSolution[floorY].equals(name))
                {
                    isStatementSatisfied = false;
                }
                break;
            }
            case 3:
            {
                if (nameFloor < otherFloor)
                {
                    isStatementSatisfied = false;
                }
                break;
            }
            case 4:
            {
                if (-1 == nameFloor || -1 == otherFloor)
                { System.out.println("BUG"); }
                
                if ((nameFloor+1) != otherFloor && (nameFloor-1) != otherFloor)
                {  
                    isStatementSatisfied = false;
                }
                break;
            }
            case 5:
            {
                if (-1 == nameFloor || -1 == otherFloor)
                { System.out.println("BUG1"); }
                
                if ((nameFloor+1) == otherFloor || (nameFloor-1) == otherFloor)
                {
                    isStatementSatisfied = false;
                }
                break;
            }
            default:
            {
                System.out.println("UNKNOWN Statement Type: " + type);
                break;
            }
        }
        
        return isStatementSatisfied;
    }
    
    private int findFloor(String[] aSolution, String name)
    {
        int floor = -1;
        for (int f = 1; f < aSolution.length; f++)
        {
            if (aSolution[f].equals(name))
            {
                floor = f;
                break;
            }
        }
        return floor;
    }
    
    private boolean isAdjacentFloor(int floorA, int floorB)
    {
        boolean isAdjacent = true;
        do
        {
            if (floorA == floorB)
            {
                isAdjacent = false;
                break;
            }
            
            if (1 == floorA && 2 != floorB)
            {
                isAdjacent = false;
                break;
            }
            
            if (1 == floorB && 2 != floorA)
            {
                isAdjacent = false;
                break;
            }
            
            if (5 == floorA && 4 != floorB)
            {
                isAdjacent = false;
                break;
            }
            
            if (5 == floorB && 4 != floorA)
            {
                isAdjacent = false;
                break;
            }
            
            // We know that this won't go out of bounds
            if ((floorA+1) != floorB && (floorA-1) != floorB)
            {
                isAdjacent = false;
                break;
            }
        } while(false);
        
        return isAdjacent;
    }
    public void print()
    {
        System.out.println("[type: " + type + ", floorX: " + floorX + ", floorY: " + floorY + ", name: " + name + ", otherName: " + otherName);
    }
    
}
