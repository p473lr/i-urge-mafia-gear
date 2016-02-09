// Command-line compile:
// g++ -lm -o sparta sparta.cpp
#include <fstream>
#include <iostream>

// Note: This boolean is useful to have in a solution-program template.
// It allows you to put debugging output throughout your code (which is good
// because it allows you to debug from a printout, away from the computer)
// and then you can TURN IT OFF all at once, instead of deleting or
// commenting out individual debug print statements.
const bool debug = false;

// We'll do everything in the main routine of this program.
int main()
{
   // Create a new input file stream to read our input file. Call it "fin"
   // for "file input" - like "cin" for the standard console input.
   std::ifstream fin( "sparta.in" , std::ifstream::in );

   // Read in the number of disputes in the file
   unsigned numDisputes;
   fin >> numDisputes;
   // Make sure we read the input correctly
   if( debug ) std::cout << "### numDisputes = " << numDisputes << std::endl;

   // Use a loop to read in all the disputes
   for( unsigned d = 1; d <= numDisputes; ++d )
   {
      // For the current dispute, start the number of Persian troops at zero
      unsigned numTroops = 0;

      // For the current dispute, read in the number of times the Persian
      // ambassador yells "MADNESS"
      unsigned numPersianYells;
      fin >> numPersianYells;
      // Make sure we read the input correctly
      if( debug ) std::cout << "### Test case " << d << ": "
            "Persian shouts = " << numPersianYells << std::endl;

      // For the current dispute, use a loop to read in the Persian's
      // individual yells
      for( unsigned y = 1; y <= numPersianYells; ++y )
      {
         std::string MADNESS; // string to store the word "MADNESS"
         unsigned loudness;   // integer to store the loudness of the yell

         // Read in a line containing a yell
         fin >> MADNESS >> loudness;
         // Make sure we read the input correctly
         if( debug ) std::cout << "### Test case " << d << ", yell " << y << ": "
               "loudness = " << loudness << std::endl;

         // Add the loudness of the current yell to the number of Persian
         // troops for the current dispute
         numTroops += loudness;

         // Note that we don't use the string MADNESS for anythiing.
      }

      // Format the output properly for the current dispute.
      // On the first line, show the dispute number.
      std::cout << "Dispute " << d << ":" << std::endl;
      // On the second line, show the numbers of troops in each army.
      std::cout << "Persian " << numTroops << ", Spartan 300" << std::endl;
      // On the third line, show the Spartan king's retort.
      std::cout << "This is SPARTA!" << std::endl;
      // Blank line after each dispute
      std::cout << std::endl;
   }

   return 0; // C++ "main" routine must return integer
}

