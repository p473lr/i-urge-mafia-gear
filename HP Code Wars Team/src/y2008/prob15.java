/*
 * RailwayShunting.java
 *
 * Created on February 17, 2008, 9:16 AM
 *
 * @author Powell "Hap" Hazzard
 * Hp CodeWars Railway Shunting Crew
 */

package y2008;
import java.io.*;


public class prob15 {
    
    /** Creates a new instance of RailwayShunting */
    public prob15() {
    }
    
    // class that will represent the Rail Cars
    
    class RailCar {
        int num;            // rail car number
        int posPrio;        // final car position
        boolean notUsed;    // quick check to see if this car is in the final train
        static final int notFinal = -1;
        
        
        public RailCar(int carNum) {
            this(carNum, notFinal);
        }
        
// Car number then car's final pos, if not used -1
        
        public RailCar(int carNum, int pos) {
            num = carNum;
            posPrio = pos;
            if (pos == notFinal)
                notUsed = true;
            else
                notUsed = false;
            
        }
        
//        Store the final car location
        public void updatePos(int pos) {
            posPrio = pos;
            if (pos == notFinal)
                notUsed = true;
            else
                notUsed = false;
        }
    }
    class Track {
        String name;
        RailCar cars[];  // queue of rail cars
        int numCars=0;   // number of current rail cars on this siding
        int maxCars=0;   // max number allowed on this siding
        public boolean full;  // quick check to see if the siding is full
        public boolean empty; // quick check to see if the siding is empty
        
        public Track(String name,
                int max) {
            maxCars = max;
            cars = new RailCar[max];
            empty = true;
        }
        
        // What is the first car on the siding that can be moved?
        public RailCar peekFirstCar() {
            if (numCars == 0)
                return null; // no cars on siding
            return cars [numCars -1 ];
        }
        
        // if a car is on this siding return the car, if not return null
        public RailCar findCar(int carNum) {
            for (int i=0; i<numCars;i++)
                if (cars[i].num == carNum)
                    return cars[i];
            
            return null;
        }
        
        // Does this siding have cars that will be part of the
        // final configuration?
        public boolean hasFinalCars() {
            for (int i=0; i<numCars; i++)
                if (cars[i].posPrio > 0)
                    return true;
            return false;
        }
        
        //  move a car to the switch
        public RailCar removeFirstCar() {
            RailCar retv;
            if (empty ) {
                // if empty throw exception
            }
            
            retv = cars[--numCars];
            cars[numCars] = null;
            if (numCars == 0)
                empty = true;
            full = false;
            return retv;
        }
        
        // move a car to this track
        public void insertCar(RailCar newCar) {
            if (full) {
                // throw full exception
            }
            
            cars[numCars++] = newCar;
            if (numCars == maxCars)
                full = true;
            empty=false;
        }
        
        // Compare two tracks, if already in order return true.
        public boolean inOrder(Track s, int num) {
            if (num > s.numCars)
                return false;
            for (int i=0 ; i< num;i++) {
                if (this.cars[i] != s.cars[i])
                    return false;
            }
            return true;
        }
        // Compare two tracks.  Do the have the same cars?
        public boolean equal(Track s) {
            
            if (this.numCars != s.numCars)
                return false;
            if (this.maxCars != s.maxCars)
                return false;
            for (int i=0; i < this.numCars; i++)
                if (this.cars[i] != s.cars[i])
                    return false;
            
            return true;
            
        }
        //print the current layout of this track
        // Some tracks are displayed in reverse order
        // The mainline will have the switch and the Locomotive
        public String printTrack(boolean revOrder, boolean mainLine) {
            char view[] = new char[maxCars];
            int i = 0;
            
            for (i=0;i< view.length; i++)
                view[i] = '-';
            
            if (mainLine)
                view[4]= '+';
            
            for (i=0;i<view.length;i++)
                if (cars[i] != null)
                    if (revOrder)
                        view[view.length - 1 - i] = (char) (cars[i].num + '0');
                    else
                        view[i] = (char) (cars[i].num + '0');
            
            if (mainLine)
                return new String("L"+new String(view));
            else
                return new String(view);
        }
    }
    
    public Track[] initTracks(String ab, String cd, String results) {
        
        Track a = new Track("A",5); // holds A siding 5 cars max
        Track b = new Track("B",5); // holds B siding 5 cars max
        Track c = new Track("C",3); // holds C siding 3 cars max
        Track d = new Track("D",3); // holds D siding 3 cars max
        Track finalOrder = new Track("Final",5); // desired results on "A"
        Track all[] = {a, b, c, d, finalOrder}; // return all tracks
        
        // load "A" siding
        for (int i=0; i < a.maxCars; i++)
            // only create cars if they exist on a siding
            if (ab.charAt(i) != '-' &&
                ab.charAt(i) != 'L') {
            RailCar n = new RailCar(ab.charAt(i) - '0');
            a.insertCar(n);
            };
            
            // load "C" siding
            for (int i=0; i < c.maxCars; i++)
                if (cd.charAt(i) != '-') {
                RailCar n = new RailCar(cd.charAt(i) - '0');
                c.insertCar(n);
                };
                
                // load "B" siding
                for (int i=ab.length()-1; ab.charAt(i) != '+'; i--)
                    if (ab.charAt(i) != '-') {
                    RailCar n = new RailCar(ab.charAt(i) - '0');
                    b.insertCar(n);
                    };
                    
                    // load "D" siding
                    for (int i=cd.length()-1; cd.charAt(i) != '^'; i--)
                        if (cd.charAt(i) != '-') {
                        RailCar n = new RailCar(cd.charAt(i) - '0');
                        d.insertCar(n);
                        };
                        
                        //load final order and mark cars with final car position
                        for (int i=1; i < results.length(); i++)
                            if (results.charAt(i) != 'L') {
                            int carNum = results.charAt(i) - '0';
                            RailCar n= null;
                            for (int t=0; t < all.length - 1; t++)
                                if (null != (n = all[t].findCar(carNum)))
                                    break;
                            n.updatePos(i); // "L" doesn't count for position
                            finalOrder.insertCar(n);
                            };
                            
                            return all;
    }
    
    // quick method to print out the current siding tracking information
    public void printTrackView(Track mainSiding, Track subSiding[]) {
        
        System.out.println();
        System.out.println(mainSiding.printTrack(false,true)+ // not reversed, mainline
                subSiding[0].printTrack(true,false)); // reversed, not mainline
        System.out.println(subSiding[1].printTrack(false,false) + "^" +
                subSiding[2].printTrack(true,false));
        
    }
    // first element should be the main siding
    // last element should be the final order
    // t[1] element is siding "B"
    // t[2] element is siding "C"
    // t[3] element is Siding "D"
    public void orderCars(Track t[]) {
        Track finalSiding = t[4];
        Track mainSiding = t[0];
        Track subSiding[] = {t[1],t[2],t[3]};
        Track allSiding[] = {t[0],t[1],t[2],t[3]};
        final int numSubSiding = subSiding.length;
        int loopCounter=0;
        // let's get down to business
        // let's do this in three passes
        // first pass, move all the cars that will be used to build the train on to siding "A"
        // second pass, rearrange the cars using the spare slots on the subsidings
        // third and final pass, build the train
        
        //first pass, move all the cars on the "A" siding that will be used to build the train
        wh:
            while (subSiding[0].hasFinalCars() || subSiding[1].hasFinalCars() ||
                    subSiding[2].hasFinalCars()) {
                printTrackView(mainSiding, subSiding);
                loopCounter++;
                
                // Note: for speed, this could be combined into one large "if" statement
                // let's attempt to give each rule a unique "for" loop for readability
                
                // highest priority rule
                // if we have an empty subsiding, attempt to move a non-used car to it
                // check all subsidings
                // if empty, move a car that isn't used in the final configuration to
                // the end of the line...
                for (int i =0; i < numSubSiding;i++) {
                    // do we have an empty siding (beside the main siding)?
                    if (subSiding[i].empty) {
                        // do we have a non-used car on the mainline?
                        if (!mainSiding.empty && mainSiding.peekFirstCar().notUsed) {
                            // move from main to sub
                            subSiding[i].insertCar(mainSiding.removeFirstCar());
                            continue wh;
                        } else {
                            for (int i1 = 0; i1 < numSubSiding; i1++) {
                                if (!mainSiding.full && !subSiding[i1].empty &&
                                        subSiding[i1].peekFirstCar().notUsed) {
                                    // move from sub to main
                                    mainSiding.insertCar(subSiding[i1].removeFirstCar());
                                    continue wh;
                                }
                            }
                        }
                    }
                }
                
                // No empty sub-track
                // let's attempt to group unused cars on the same siding
                // find a car that is first on the siding with cars that will
                // be used in the final configuation.  Attempt to free the "used"
                // cars by moving the lead car to the mainline
                
                if (!mainSiding.full && (
                        mainSiding.empty || !mainSiding.peekFirstCar().notUsed)) {
                    for (int i = 0; i< numSubSiding;i++)
                        if (!subSiding[i].empty &&
                            subSiding[i].hasFinalCars() &&
                            subSiding[i].peekFirstCar().notUsed) {
                        mainSiding.insertCar(subSiding[i].removeFirstCar());
                        continue wh;
                        } else if (subSiding[i].hasFinalCars()) {
                        
                        mainSiding.insertCar(subSiding[i].removeFirstCar());
                        continue wh;
                        }
                }
                
                // Mainline has a car that will not be used in the final
                // configuration.  Attempt to move it to a siding that is
                // holding nothing but "used" cars
                
                if (!mainSiding.empty &&
                        mainSiding.peekFirstCar().notUsed)
                    for (int i = 0; i< numSubSiding;i++)
                        if (!subSiding[i].hasFinalCars() &&
                        !subSiding[i].full) {
                    subSiding[i].insertCar(mainSiding.removeFirstCar());
                    continue wh;
                        }
                
                // main siding is not empty if we reached this point
                // load up the main siding with cars for the final confirugation
                
                for (int i=0; i< numSubSiding;i++) {
                    if (subSiding[i].hasFinalCars() &&
                            !subSiding[i].peekFirstCar().notUsed){
                        RailCar mb = mainSiding.peekFirstCar();
                        if (mb.posPrio < subSiding[i].peekFirstCar().posPrio) {
                            mainSiding.insertCar(subSiding[i].removeFirstCar());
                            continue wh;
                        }
                    }
                }
                /*
                 */
                // last check, move cars that are used to the main siding
                for (int i=0; i< numSubSiding;i++) {
                    if (subSiding[i].hasFinalCars() &&
                            !subSiding[i].peekFirstCar().notUsed){
                        mainSiding.insertCar(subSiding[i].removeFirstCar());
                        continue wh;
                    }
                }
                
                // if we ever get here, track "A" should contain only used cars
                // in the final configuration
                // let the "while" statement exit or you can use
                // a break here
            } //  while subSiding.hasFinalCars[0] || subSiding.hasFinalCars[1] || subSiding.hasFinalCars[0])
            
            // second pass: sorting pass: place the cars on the subsiding so we can
            // reconfigure the final train in order
            while (!finalSiding.equal(mainSiding) && !mainSiding.empty &&
                   !finalSiding.inOrder(mainSiding, mainSiding.numCars) ) {
                printTrackView(mainSiding, subSiding);
                loopCounter++;
                
                
                for (int i=0; i< numSubSiding;i++){
                    RailCar bs = subSiding[i].peekFirstCar();
                    RailCar ms = mainSiding.peekFirstCar();
                    if (!subSiding[i].full &&
                            (bs == null || ms.posPrio < bs.posPrio || bs.notUsed)) {
                        subSiding[i].insertCar(mainSiding.removeFirstCar());
                        break;
                    }}
            }
            
//  final stage put them in order...
            
            
            // everything is ready for the final configuration
            // just rearrange the cars
            while (!finalSiding.equal(mainSiding)) {
                printTrackView(mainSiding, subSiding);
                loopCounter++;
                {
                    RailCar ms = mainSiding.peekFirstCar();
                    Track next = null;
                    for (int i=0; i<numSubSiding;i++) {
                        RailCar rc = subSiding[i].peekFirstCar();
                        if (rc == null)
                            continue;
                        if (next == null & !rc.notUsed)
                            next = subSiding[i];
                        else
                            if (!rc.notUsed && rc.posPrio < next.peekFirstCar().posPrio)
                                next = subSiding[i];
                    }
                    if (ms == null && next != null)
                        mainSiding.insertCar(next.removeFirstCar());
                    else
                        if (ms != null && next != null &&
                            ms.posPrio < next.peekFirstCar().posPrio)
                            mainSiding.insertCar(next.removeFirstCar());
                    
                }
            }
            
            printTrackView(mainSiding, subSiding);
            System.out.println("Solved in "+loopCounter+ " moves");
    }
    
    public static void main(String[] args) throws IOException {
        
        String ab = "";
        String cd = "";
        String result = "";
        
        //RailwayShunting rw = new RailwayShunting();
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        //  gather input from the user until they enter "END"
        
        System.out.println("Enter cars on Side A & B in the following format AAAAA+BBBBB");
        ab = br.readLine();
        // ab = new String("L----+86321");
        ab = ab.trim();
        System.out.println("Enter cars on Side C & D in the following format CCC^DDD");
        cd = br.readLine();
        //cd = new String("  457^---");
        cd = cd.trim();
        
        System.out.println("Enter final order in the following format L12345");
        result = br.readLine();
        //result = new String("L12345");
        result.trim();
        
        //rw.orderCars(rw.initTracks(ab,cd,result));
        
    }
    
}
