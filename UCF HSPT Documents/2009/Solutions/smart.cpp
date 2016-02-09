/**
 * 2009 UCF High School Programming Tournament
 *
 * C++ solution to "Obtain Intelligence"
 *
 */
#include <fstream>
#include <iostream>

int main()
{
   // Set up input and output for this program.
   //
   // The input will be from a file called smart.in; we'll call this 'fin' for
   // "[f]ile [in]put."
   //
   // The output will be to the console, also called "standard output" and
   // provided by C++ as the 'cout' object (meaning "[c]onsole [out]put").
   // Note that the 'ifstream' class and the 'cout' object are both in the
   // 'std' ("standard") namespace, so we have to put "std::" in front of
   // them.
   std::ifstream fin( "smart.in" );

   // The problem defines positive integer 'n' as the number of throws
   unsigned int n;

   // Read in the number of throws from the input file.
   fin >> n;

   // Use a loop, counting from 0 to n-1, to process each throw
   for( unsigned int i = 0; i < n; ++i )
   {
      // For each throw, there will be two positive integers 't' and 'p'.
      unsigned int t;  // 't' is the position of the target
      unsigned int p;  // 'p' is the position of Agent 13's throw

      // Read in 't' and 'p' from the input file.
      // C++ will automatically take care of the spaces and line breaks.
      fin >> t >> p;

      // Check whether the target was hit.
      if( t == p )
      {
         // It's a hit! Output the correct message.
         // Be careful with capitalization, punctuation, and spacing!
         std::cout << "The Old Knife-Hits-the-Target Trick" << std::endl;
      }
      else
      {
         // It's a miss. Output the correct message.
         // Be careful with capitalization, punctuation, and spacing!
         std::cout << "Missed it by THAT much" << std::endl;
      }
   }
}

