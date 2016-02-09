using System;
using System.Collections.Generic;
using System.Text;

namespace Combination
{
    class Program
    {
        static void Main(string[] args)
        {
            string str = System.Console.ReadLine();
            string[] STRS = str.Split(new char[] { ' ' }, StringSplitOptions.RemoveEmptyEntries);
            Int64 n = Int64.Parse(STRS[0]);
            Int64 m = Int64.Parse(STRS[1]);
            Int64 NminusM = n - m;
            Int64 combinations = 1;

            // First, remove unnecessary factors from the numerator and denominator
            Int64 lowestDenominator = m > NminusM ? NminusM : m;
            Int64 highestDenominator = m > NminusM ? m : NminusM;

            // Now finish the calculation
            while (n > highestDenominator)
            {
                combinations *= n;
                n--;
                // we also need to roll the denominator in. Doing so as we multiply can reduce possibility 
                // of an overflow error
                if (combinations % lowestDenominator == 0) // this avoid lossy integer division
                {
                    if (lowestDenominator > 1) // no need to continue dividing after this point
                    {
                        combinations /= lowestDenominator;
                        lowestDenominator--;
                    }
                }
            }

            // Just in case
            while (lowestDenominator > 1)
            {
                combinations /= lowestDenominator;
                lowestDenominator--;
            }

            System.Console.WriteLine(combinations.ToString());
            System.Console.ReadLine();
        }
    }
}
