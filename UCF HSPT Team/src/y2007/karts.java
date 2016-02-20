
import java.io.*;
import java.util.*;

public class karts
{
   //
   // This will allow us to examine debug values in the program from a printout,
   // so that we don't tie up the workstation. Set it to 'false' before submitting!
   //
   public static final boolean debug = false;


   /**
    * The Racer object represents a single racer on the circuit. These objects
    * will be re-used between different race courses. A racer has a unique name
    * and a fixed kart speed. When running each course, a racer will "get ready"
    * to clear out any state information, and while on each course it may hit
    * some bananas, which will affect its overall time for the course.
    */
   static class Racer
   {
      public Racer( String name, Integer speed )
      {
         this.name = name;
         this.speed = speed;
         this.bananaHitCount = 0;
      }

      public String getName()
      {
         return name;
      }

      public int getSpeed()
      {
         return speed;
      }

      /**
       * When a racer hits a banana, we simply increment their number of hits.
       */
      public void hitBanana()
      {
         bananaHitCount += 1;
      }

      /**
       * Reset between courses; Clear out any banana hits.
       */
      public void getReady()
      {
         bananaHitCount = 0;
      }

      /**
       * Return the time it takes this Racer to travel the specified distance,
       * multiplied by the product of all Racer speeds (POAS).
       *
       * Start with the standard formula:
       *    Speed * Time = Distance
       *
       * Solve for Time:
       *    Time = Distance / Speed
       *
       * Multiply both sides by a constant:
       *    K * Time = K * Distance / Speed
       *    K * Time = Distance * (K / Speed)
       *
       * For this function, K is the product of all speeds (POAS).
       *    POAS * Time = Distance * (POAS / Speed)
       *
       * Since POAS is a multiple of Speed, POAS/Speed will always be an integer.
       * It will overflow an 'int' which is why we use a 'long' type.
       *
       * (We did some quick checks to make sure it won't overflow a 'long' type.)
       *
       *
       * Yay! Now we don't have to worry about floating point errors!
       *
       */
      public long getTimeToPosition( int distance )
      {
         if( debug ) System.out.println( "### Racer=" + name + ", speed=" + speed );

         long s = productOfAllSpeeds / speed;
         if( debug ) System.out.println( "### s=" + s );

         // get the travel time (actually POAS x Time)
         long t = distance * s;
         if( debug ) System.out.println( "### t=" + t );

         // total up 5 sec slip time for each banana hit
         long b = bananaHitCount * 5;
         if( debug ) System.out.println( "### b=" + b );

         // slip time also needs to be (POAS x Time), not just Time
         b = b * productOfAllSpeeds;
         if( debug ) System.out.println( "### b=" + b );

         // add banana slip time to travel time
         t = t + b;
         if( debug ) System.out.println( "### t=" + t );

         return t;
      }

      /**
       * Calculate and store off the product of all racer speeds.
       */
      public static long calculateProductOfAllSpeeds( List<Racer> list )
      {
         long product = 1;  // start product with identity value, 1

         for( int i = 0; i < list.size(); ++i )
         {
            Racer r = list.get( i );
            int speed = r.getSpeed();

            product *= speed;
         }

         if( debug ) System.out.println( "### product of all speeds=" + product );
         return product;
      }

      /**
       * Calculate and store off the worst possible time.
       * Requires the product of all speeds to be calculated first.
       */
      public static long calculateWorstPossibleTime( long prodOfSpeeds )
      {
         long maxBananaHits = 10;
         long lowestPossibleSpeed = 1;
         long longestPossibleDistance = 1000;

         long s = prodOfSpeeds / lowestPossibleSpeed;
         long t = longestPossibleDistance * s;
         long b = maxBananaHits * 5;
         b = b * prodOfSpeeds;
         t = t + b;

         if( debug ) System.out.println( "### worst possible time=" + t );
         return t;
      }

      private static long productOfAllSpeeds;
      private static long worstPossibleTime;
      private final String name;
      private final Integer speed;
      private Integer bananaHitCount;
   };


   /**
    * The Course object represents a single race course in a circuit.
    * Each course has a unique name, a length, and some bananas.
    * Working with the list of racers, the course can determine which
    * racer will be the fastest to each position along the length,
    * up to the length itself (the finish line).
    */
   static class Course
   {
      public Course( String name, Integer length )
      {
         this.name = name;
         this.length = length;
         this.bananaPositions = new ArrayList<Integer>();
      }

      public String getName()
      {
         return name;
      }

      /**
       * Add a banana to this course at the specified position.
       */
      public void addBanana( Integer position )
      {
         // insert in sorted order
         for( int b = 0; b < bananaPositions.size(); ++b )
         {
            if( position < bananaPositions.get( b ) )
            {
               if( debug ) System.out.println( "### adding banana for course position "
                     + position + " to list at index " + b );

               // insert before b
               bananaPositions.add( b, position );
               return;
            }
         }
         if( debug ) System.out.println( "### adding banana for course position "
               + position + " at end of list" );

         // add at end
         bananaPositions.add( position );
      }

      /**
       * From the racers in the given list, find which one will first reach the
       * specified race course position. This method assumes that any banana peel
       * hits before the position have already been accounted for.
       */
      public Racer findFastestRacerToPosition( List<Racer> racerList, int position )
      {
         Racer fastestRacer = null;

         //
         // Start with the fastest Time set to a value larger than what is
         // actually possible, so that we can be sure that on of the racer's
         // Times will replace it.
         //
         long fastestTime = Racer.worstPossibleTime + 1;

         //
         // For each racer, calculate the time (actually POAS x Time) to get to
         // the given position, including any banana slip time.
         //
         for( int r = 0; r < racerList.size(); ++r )
         {
            Racer racer = racerList.get( r );
            long t = racer.getTimeToPosition( position );

            if( debug )
            {
               String fastestName = "<none>";
               if( null != fastestRacer ) fastestName = fastestRacer.getName();
               System.out.println( "### fastest=" + fastestName + ", time=" + fastestTime );
            }

            //
            // If this racer has a lower time than our current fastest,
            // then he becomes the new fastest.
            //
            if( t < fastestTime )
            {
               fastestTime = t;
               fastestRacer = racer;
            }
         }

         return fastestRacer;
      }

      /**
       * Simulate the race, but finding the first racer to reach each banana
       * peel, adding the effects of that banana peel to the racer as we go,
       * and then finding which is the first to reach the finish line.
       */
      public Racer runRace( List<Racer> racerList )
      {
         //
         // Get the racers ready
         //
         for( int r = 0; r < racerList.size(); ++r )
         {
            racerList.get( r ).getReady();
         }

         //
         // For each banana, find which racer reaches it first. This will be
         // the racer with the lowest Time (actually the lowest POAS x Time).
         // Note that in order to adjust racer Times correctly, we have to hit
         // the bananas in the order they appear on the course, which is not
         // necessarily the same as the order they appeared in the input.
         //
         for( int b = 0; b < bananaPositions.size(); ++b )
         {
            int bananaPos = bananaPositions.get( b );

            Racer firstRacerToBanana = findFastestRacerToPosition( racerList, bananaPos );

            if( debug ) System.out.println( "### racer " + firstRacerToBanana.getName()
                  + " hit banana at " + bananaPos );
            if( debug ) System.out.println( "###" );

            //
            // Whichever racer has the fastest time for this banana is the one
            // who hits it. This will affect subsequent Time calculations for
            // that racer.
            //
            firstRacerToBanana.hitBanana();
         }

         //
         // Now that all bananas are applied to the racers, find out who reaches
         // the finish line first. The finish line is at position 'length'.
         //
         Racer firstRacerToFinish = findFastestRacerToPosition( racerList, length );

         return firstRacerToFinish;
      }

      private final String name;
      private final Integer length;
      private List<Integer> bananaPositions;
   };


   /**
    * This main routine for the Java program will read the
    * input, build our data structures, and show the output.
    */
   public static void main( String[] args ) throws IOException
   {
      BufferedReader inputFile = new BufferedReader( new FileReader( "karts.in" ) );
      
      //
      // Get the number of circuits
      //
      int numCircuits = Integer.parseInt( inputFile.readLine() );

      //
      // For each circuit, get the racers, get the courses, and evaluate each Course
      //
      for( int y = 1; y <= numCircuits; ++y )
      {
         //
         // Show the circuit header
         //
         System.out.println( "Circuit #" + y + ":" );

         List<Racer> racerList = new ArrayList<Racer>();

         //
         // Get the number of racers
         //
         int numRacers = Integer.parseInt( inputFile.readLine() );

         //
         // For each Racer, read the name and speed, and store it in our Racer list
         //
         for( int r = 0; r < numRacers; ++r )
         {
            StringTokenizer racerTok = new StringTokenizer( inputFile.readLine(), " " );
            String name = racerTok.nextToken();
            int speed = Integer.parseInt( racerTok.nextToken() );

            racerList.add( new Racer( name, speed ) );
         }

         //
         // Now that we have all the racers, determine the product of all their
         // speeds and the worst possible time. These constants will be useful on
         // all the courses.
         //
         Racer.productOfAllSpeeds = Racer.calculateProductOfAllSpeeds( racerList );
         Racer.worstPossibleTime = Racer.calculateWorstPossibleTime( Racer.productOfAllSpeeds );

         //
         // Get the number of courses
         //
         int numCourses = Integer.parseInt( inputFile.readLine() );

         //
         // For each Course, read the name, length, and banana informantion. Then
         // run a simulated race for the course and output the winner.
         //
         for( int c = 0; c < numCourses; ++c )
         {
            StringTokenizer courseTok = new StringTokenizer( inputFile.readLine(), " " );
            String name = courseTok.nextToken();
            int length = Integer.parseInt( courseTok.nextToken() );

            //
            // Set up the Course
            //
            Course course = new Course( name, length );

            //
            // Get the number of bananas for this Course
            //
            int numBananas = Integer.parseInt( courseTok.nextToken() );

            //
            // Add bananas to the Course
            //
            for( int b = 0; b < numBananas; ++b )
            {
               int bananaPosition = Integer.parseInt( courseTok.nextToken() );

               //
               // If the banana is on the finish line, skip it. The first racer
               // to reach the finish line (not cross it) is the winner, and we
               // don't want that last banana peel to throw off our simulation.
               //
               if( bananaPosition == length ) continue;

               //
               // Record the banana position
               //
               course.addBanana( bananaPosition );
            }

            //
            // Since we don't need to remember anything from one Course to the
            // next, we can go ahead and simulate the race for this Course.
            // (Otherwise, we'd store off the courses and do a separate loop.)
            //
            Racer winner = course.runRace( racerList );

            //
            // Output results for this race in the proper format.
            //
            System.out.println( course.getName() + ": " + winner.getName() + " is the winner!" );
         }

         //
         // Put a blank line after each circuit's races.
         //
         System.out.println();
      }
   }
};

