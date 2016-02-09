/**
 * 2009 UCF High School Programming Tournament
 *
 * C++ solution to "Password Check"
 *
 */
#include <cctype>
#include <fstream>
#include <iostream>
#include <string>

int main()
{
   // Set up input and output for this program.
   //
   // The input will be from a file called password.in; we'll call this 'fin'
   // for "[f]ile [in]put."
   //
   // The output will be to the console, also called "standard output" and
   // provided by C++ as the 'cout' object (meaning "[c]onsole [out]put").
   // Note that the 'ifstream' class and the 'cout' object are both in the
   // 'std' ("standard") namespace, so we have to put "std::" in front of
   // them.
   std::ifstream fin( "password.in" );

   // The problem defines positive integer 'n' as the number of passwords
   // to check
   unsigned int n;

   // Read in the number of passwords from the input file.
   fin >> n;

   // Use a loop, counting from 1 to n, to process each password
   for( unsigned int i = 1; i <= n; ++i )
   {
      // Each password will be a string, containing no spaces
      std::string password;

      // Read in a password from the input file.
      // C++ will automatically take care of the line breaks.
      fin >> password;

      // In C++, the characters in strings are numbered from zero to 'length'
      // minus one. So the last character will be...
      char last = password[password.length() - 1];

      if( isdigit( last ) )
      {
         // It's a digit! Output the correct message.
         // Be careful with capitalization, punctuation, and spacing!
         std::cout << "Password #" << i << ": Check password!" << std::endl;
      }
      else
      {
         // It's not a digit. Output the correct message.
         // Be careful with capitalization, punctuation, and spacing!
         std::cout << "Password #" << i << ": Probably okay." << std::endl;
      }
   }
}

