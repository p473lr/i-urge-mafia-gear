/**
 * 2009 UCF High School Programming Tournament
 *
 * C++ solution to "Obama's Order"
 *
 */
#include <fstream>
#include <iostream>
#include <string>

int main()
{
   // Set this to 'true' in order to see extra output that can help diagnose
   // programming errors. But be sure to set it to 'false' before submitting!
   const bool debug = false;

   // Set up input and output for this program.
   //
   // The input will be from a file called obama.in; we'll call this 'fin'
   // for "[f]ile [in]put."
   //
   // The output will be to the console, also called "standard output" and
   // provided by C++ as the 'cout' object (meaning "[c]onsole [out]put").
   // Note that the 'ifstream' class and the 'cout' object are both in the
   // 'std' ("standard") namespace, so we have to put "std::" in front of
   // them.
   std::ifstream fin( "obama.in" );

   // The problem defines positive integer 'n' as the number of potential
   // judges.
   unsigned int n;

   // Read in the number of potential judges from the input file.
   fin >> n;

   // Use a loop, counting from 0 to n-1, to process each potential judge.
   for( unsigned int i = 0; i < n; ++i )
   {
      // For each potential judge, read in the name from the input file.
      // C++ will automatically take care of the line breaks.
      std::string name;
      fin >> name;

      // Now that we have the name, we can count the number of 'O' letters
      // using the variable 'Ohs'. (Get it? Oh my goodness.) Start the count
      // at zero.
      unsigned int Ohs = 0; 

      // Use a loop, counting from the first letter (zero) to the last
      // letter of the name (one less than its length), to check for O's.
      for( unsigned int j = 0; j < name.length(); ++j )
      {
         // If we find an 'O', increment our 'Ohs' count.
         if( 'O' == name[j] )
         {
            ++Ohs;
         }

         // Here's some extra output we can use to check our work.
         // See the note above about 'debug'.
         if( debug )
         {
            std::cout << "### "
                << " i=" << i
                << " name=" << name
                << " j=" << j
                << " name[j]=" << name[j]
                << " Ohs=" << Ohs
                << std::endl;
         }
      }

      // Output the required message.
      // Be careful with capitalization, punctuation, and spacing!
      std::cout << "Potential Judge " << name << "'s last name "
            "has " << Ohs << " O's in it." << std::endl;
   }
}

