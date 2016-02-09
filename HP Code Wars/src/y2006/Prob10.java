/*
 * Prob10.java
 * Codewars2006 - DVR Scheduling
 *
 * Created on March 1, 2006, 3:18 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
import java.io.*;
import java.util.*;
import java.util.regex.*;
/**
 *
 * @author skearney
 */
public class Prob10 {
    
    /** Creates a new instance of Prob10 */
    public Prob10() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Scanner in=null;
        SeasonPass sp = new SeasonPass();
        
        // Use input from File, but default to STDIN if file is not found
        try {
            in = new Scanner(new File("Prob10.in"));
        } catch (FileNotFoundException e) {
            in = new Scanner(System.in);
        }
    
        //Add each show to our Season Pass
        //The constructor for Show parses each line
        while(in.hasNextLine()) {
            sp.add(new Show(in.nextLine()));
        }
        
        // The scheduler will detect and print any conflicts
        sp.Schedule();
        
        in.close();
        System.exit(0);
    }
}

class SeasonPass extends ArrayList {
        
    public SeasonPass(){}
    
    public void Schedule() {
        
        // Keep track of what are tuners are doing
        List<Hashtable> tuners = new ArrayList<Hashtable>();
        tuners.add(0,new Hashtable());
        tuners.add(1,new Hashtable());
 
        // Run through our Season Pass and schedule each show.  Output conflicts as they occur
        Iterator it = this.listIterator();
        while(it.hasNext()) {
            Show show = (Show) it.next();
            
            // See if Tuner 1 is busy, if not add show to tuner
            if(!tunerBusy(tuners.get(0),show)) {
                tuners.get(0).put(show.airDate,show);

            // See if Tuner 2 is busy, if not add show to tuner
            } else if(!tunerBusy(tuners.get(1),show)) {
                tuners.get(1).put(show.airDate,show);

            // We have a conflict,  find and print ALL conflicts
            } else {
                System.out.print(show.Title + " conflicts with ");
                List conflicts = getConflicts(show, tuners);
                Iterator c_it = conflicts.iterator();
                String conflict = (String) c_it.next();
                System.out.print(conflict);
                while(c_it.hasNext()) {
                    conflict = (String) c_it.next();
                    System.out.print(","+conflict);
                }
                System.out.print("\n");
            }
        }
    }
    
    private boolean tunerBusy(Hashtable tuner, Show show){
        // Iterate through the items on the tuner.  Since we add items to the 
        // tuner in priority order, and show on the tuner list that starts during
        // the program we are interested in creates a conflict
        for( Enumeration e = tuner.keys(); e.hasMoreElements();  ) {
            int time = (Integer) e.nextElement();
            if( time >= show.airDate && time < (show.airDate + show.length) )
                return true;
        }
        return false;
    }
    
    private List<String> getConflicts(Show show, List<Hashtable> tuners) {
        List<String> conflicts = new ArrayList<String>();
        
        // Enumerate our tuners, find all conflict and add them to the list
        for(Iterator it = tuners.iterator(); it.hasNext(); ) {
            Hashtable tuner = (Hashtable) it.next();
            for( Enumeration e = tuner.keys(); e.hasMoreElements();  ) {
                int time = (Integer) e.nextElement();
                if( time >= show.airDate && time < (show.airDate + show.length) ) {
                    Show conflictingShow = (Show) tuner.get(time);
                    conflicts.add(conflictingShow.Title);
                }
            }
        }
        return conflicts;
    }
}

class Show {
    public String Title=null;
    public int airDate;
    public int length;                      //length of show in military time
    
    public Show(String info) {
        String[] tokens = info.split("\"");
        Title = tokens[1];  //token[0] is an empty string
        
        String[] tkn = tokens[2].split(" ");
        airDate = toIntDate(tkn[1],tkn[2]);
        int min = Integer.parseInt(tkn[3]);
        length = (min/60)*100 + min%60;
    }

    // Convert DateTime to and integer offset from SUN 0000=0
    private int toIntDate(String day, String time) {
        int date=0;
        
        if(day.compareTo("MON")==0) date+=2400;
        else if(day.compareTo("TUE")==0) date+=4800;
        else if(day.compareTo("WED")==0) date+=7200;
        else if(day.compareTo("THU")==0) date+=9600;
        else if(day.compareTo("FRI")==0) date+=12000;
        else if(day.compareTo("SAT")==0) date+=14200;
        
        return date+Integer.parseInt(time);
    }
}