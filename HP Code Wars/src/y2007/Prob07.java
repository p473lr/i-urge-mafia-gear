/*
 * Prob07.java
 */


import java.io.*;

/**
 *
 * @author James
 */
public class Prob07 {
    
    //  Drive size cost constants
    static final int DRIVE_COSTS [] = {
        75,
        110,
        140,
        250
    };
    
    //  Drive size constants
    static final int DRIVE_SIZES [] = {
        250,
        400,
        500,
        750
    };
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        
        //  input values
        int capacity;
        int raidLevel;
        
            
        //  Read the capacity parameter
        System.out.print("\nEnter array capacity (in GB): ");
        input = br.readLine();
            
        try{
            capacity = Integer.parseInt(input);
        }
        catch (NumberFormatException nfe){
             System.out.println("\nInvalid entry.");
             return;
        }
            
  
            
        //  Read the raidLevel parameter
        System.out.print("Enter RAID level: ");
        input = br.readLine();
         
        try{
            raidLevel = Integer.parseInt(input);
        }
        catch (NumberFormatException nfe){
            System.out.println("\nInvalid entry.");
              return;
            }
            
	    System.out.println("\n");

            switch (raidLevel){
                case 0:
                    calculateRaidZero(capacity);
                    break;
                
                case 1:
                    calculateRaidOne(capacity);
                    break;
                
                case 5:
                    calculateRaidFive(capacity);
                    break;
                
                default:
                    System.out.println("\nInvalid RAID level.");
                   
                    
            }   // switch
            
        
    }
    
    //  Helper functions
    
    private static void calculateRaidZero(int capacity){
        
        float arrayCost = 0;
        int diskPrice = 0;
        int driveCount = 0;
	int arrayCapacity = 0;
        
        String diskType = "";
        
        for (int i=0 ; i<DRIVE_SIZES.length ; i++){
            
            int curCount = getNumDrivesNeeded(capacity, DRIVE_SIZES[i]);
            float curCost = (curCount * DRIVE_COSTS[i]);
            
           //  We can't have more than eight disks for raid 0
            if (curCount > 8){
                continue;
	    }

            if ((arrayCost == 0) || curCost < arrayCost){
                
                arrayCost = (curCount * DRIVE_COSTS[i]);
		arrayCapacity = (curCount * DRIVE_SIZES[i]);
                diskType = "" + DRIVE_SIZES[i] + "GB";
                diskPrice = DRIVE_COSTS[i];
                driveCount = curCount;
                
            }
            
        }
        
        System.out.println("Qty: " + driveCount + " Disk: " + diskType + " Price: $" + diskPrice);
        System.out.println("Total price of this " + arrayCapacity + "GB array: $" + (driveCount*diskPrice));
        
    }
    
    private static void calculateRaidOne(int capacity){
        
        float arrayCost = 0;
        int diskPrice = 0;
        int driveCount = 0;
	int arrayCapacity = 0;
        
        String diskType = "";
        
        for (int i=0 ; i<DRIVE_SIZES.length ; i++){
            
            int curCount = getNumDrivesNeeded(capacity, DRIVE_SIZES[i]);
            float curCost = (curCount * DRIVE_COSTS[i]);
            
           //  We can't have more than four disks for raid 1 (+ mirrors)
            if (curCount > 4){
                continue;
	    }
		
            if ((arrayCost == 0) || curCost < arrayCost){
                
                arrayCost = (curCount * DRIVE_COSTS[i]);
		arrayCapacity = (curCount * DRIVE_SIZES[i]);
                diskType = "" + DRIVE_SIZES[i] + "GB";
                diskPrice = DRIVE_COSTS[i];
                driveCount = curCount;
                
            }
            
        }
        
        driveCount *= 2;
        
        System.out.println("Qty: " + driveCount + " Disk: " + diskType + " Price: $" + diskPrice);
        System.out.println("Total price of this " + arrayCapacity + "GB array: $" + (driveCount*diskPrice));
        
    }
    
    private static void calculateRaidFive(int capacity){
        
        float arrayCost = 0;
        int diskPrice = 0;
        int driveCount = 0;
	int arrayCapacity = 0;
        
        String diskType = "";
        
        for (int i=0 ; i<DRIVE_SIZES.length ; i++){
            
            int curCount = getNumDrivesNeeded(capacity, DRIVE_SIZES[i]) + 1;
            float curCost = (curCount * DRIVE_COSTS[i]);
            
            //  We can't have more than eight disks in raid 5.
            if (curCount > 8){
                continue;
            }
            
            if ((arrayCost == 0) || curCost < arrayCost){
                
                arrayCost = (curCount * DRIVE_COSTS[i]);
		arrayCapacity = ((curCount - 1) * DRIVE_SIZES[i]);
                diskType = "" + DRIVE_SIZES[i] + "GB";
                diskPrice = DRIVE_COSTS[i];
                driveCount = curCount;
                
            }
            
        }
        
        System.out.println("Qty: " + driveCount + " Disk: " + diskType + " Price: $" + diskPrice);
        System.out.println("Total price of this " + arrayCapacity + "GB array: $" + (driveCount*diskPrice));
        
    }
    
    private static int getNumDrivesNeeded(int capacity, int driveSize){
        return (int)Math.round(Math.ceil((float)capacity / (float)driveSize));
    }
    
}

