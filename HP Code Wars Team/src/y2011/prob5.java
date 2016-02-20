
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;


public class prob5
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        // TODO Auto-generated method stub
        prob5 me = new prob5();

    }
    
    public prob5()
    {
        String userInput = getInput();
       // System.out.println("read data" +userInput );
        ArrayList<Integer> waveData = calculateWave(userInput);
        
        /* Iterator<Integer> it = waveData.iterator();
         int pos=0;
        while (it.hasNext())
        {
        	
        	System.out.println(userInput.charAt(pos) + " h= " + it.next());
        	pos++;
        }*/
        
        
        dumpOutput(userInput, waveData);

    }

    
    public String getInput()
    {
        System.out.print("Feed me the wave data ");
        BufferedReader in  = new BufferedReader(new InputStreamReader(System.in));
        String myIn = null;
        
        try
        {
        	myIn = in.readLine();
        }
        catch (IOException ex)
        {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }
        
    
        
        return myIn;
    }
    
    public ArrayList<Integer>    calculateWave(String userInput)
    {
    	ArrayList<Integer> theWaveData = new ArrayList<Integer>();
    	
    	char lastChar = ' ';// don't care
    	int waveHeight = 0;
    	for (int pos = 0; pos < userInput.length(); pos++)
    	{
    		
	    	char curVal = userInput.charAt(pos);
	    	if (pos == 0)
	    	{
	    		lastChar = curVal;
	    	}
	    	else
	    	{
	    		if (curVal > lastChar)
	    		{
	    			waveHeight++;
	    		}
	    		else if (curVal < lastChar)
	    		{
	    			waveHeight--;
	    		}
	    		lastChar = curVal;
	    	}
	    	
	    	theWaveData.add(waveHeight);
    	  
    	}
         
    	return theWaveData;
    }
    
    public void dumpOutput(String userInput, ArrayList<Integer> waveData)
    {
         int maxH = 0;
         int maxHp = 0;
         int minH = 0;
         
         Iterator<Integer> it = waveData.iterator();
         
         int pos = 0;
         while (it.hasNext())
         {
        	 int curH = it.next();
        	 if (curH > maxH)
        	 {
        		 maxH = curH;
        		 maxHp = pos;
        	 }
        	 if (curH < minH)
        	 {
        		 minH = curH;
        	 }
        	 pos++;
         }
         
         for (int ypos = maxH; ypos >= minH; ypos--)
         {
             it = waveData.iterator();             
             
             int xpos = 0;
             
             while (it.hasNext())
             {
            	 int curH = it.next();
            	 if (curH == ypos)
            	 {
            		  // print char
            		  System.out.print(userInput.charAt(xpos));
            	 }
            	 else
            	 {
            		 System.out.print(' ');
            	 }
            	 xpos++;
             }
             System.out.println();
        	 
         }
         
         
         
    }
}
