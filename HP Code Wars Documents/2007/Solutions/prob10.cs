using System;
using System.Collections.Generic;
using System.Text;

namespace NewMath
{
    class Program
    {

        static Int64 LetterToNumber(char c)
        {
            switch (c)
            {
                case '0' :
                    return 0;
                case '1':
                    return 1;
                case '2':
                    return 2;
                case '3':
                    return 3;
                case '4':
                    return 4;
                case '5':
                    return 5;
                case '6':
                    return 6;
                case '7':
                    return 7;
                case '8':
                    return 8;
                case '9':
                    return 9;
                case 'a':
                    return 10;
                case 'b':
                    return 11;
                case 'c':
                    return 12;
                case 'd':
                    return 13;
                case 'e':
                    return 14;
                case 'f':
                    return 15;
                case 'g':
                    return 16;
                case 'h':
                    return 17;
                case 'i':
                    return 18;
                case 'j':
                    return 19;
                case 'k':
                    return 20;
                case 'l':
                    return 21;
                case 'm':
                    return 22;
                case 'n':
                    return 23;
                case 'o':
                    return 24;
                case 'p':
                    return 25;
                case 'q':
                    return 26;
                case 'r':
                    return 27;
                case 's':
                    return 28;
                case 't':
                    return 29;
                case 'u':
                    return 31;
                case 'v':
                    return 31;
                case 'w':
                    return 32;
                case 'x':
                    return 33;
                case 'y':
                    return 34;
                case 'z':
                    return 35;
            }

            return 0;
        }
        static char NumberToLetter(Int64 x)
        {
            switch (x)
            {
                case 0:
                    return '0';
                case 1:
                    return '1';
                case 2:
                    return '2';
                case 3:
                    return '3';
                case 4:
                    return '4';
                case 5:
                    return '5';
                case 6:
                    return '6';
                case 7:
                    return '7';
                case 8:
                    return '8';
                case 9:
                    return '9';
                case 10:
                    return 'a';
                case 11:
                    return 'b';
                case 12:
                    return 'c';
                case 13:
                    return 'd';
                case 14:
                    return 'e';
                case 15:
                    return 'f';
                case 16:
                    return 'g';
                case 17:
                    return 'h';
                case 18:
                    return 'i';
                case 19:
                    return 'j';
                case 20:
                    return 'k';
                case 21:
                    return 'l';
                case 22:
                    return 'm';
                case 23:
                    return 'n';
                case 24:
                    return 'o';
                case 25:
                    return 'p';
                case 26:
                    return 'q';
                case 27:
                    return 'r';
                case 28:
                    return 's';
                case 29:
                    return 't';
                case 30:
                    return 'u';
                case 31:
                    return 'v';
                case 32:
                    return 'w';
                case 33:
                    return 'x';
                case 34:
                    return 'y';
                case 35:
                    return 'z';
            }

            return '0';
        }


        static Int64 convert(string s)
        {
            // Numbers come in the format number^base, so split the string
            string []STRS = s.Split(new char []{'^'}, StringSplitOptions.RemoveEmptyEntries);
            string Number = STRS[0];
            Int64 number = 0;
            Int64 Base = Int64.Parse(STRS[1]);

            // Convert to base 10 from right to left
            int exponent = 0;
            Int64 place;
            for (int i = Number.Length - 1; i >= 0; i--)
            {
                place = LetterToNumber(Number[i]);
                for( int j = 0; j < exponent; j++ )
                    place *= Base;

                exponent++;
                number += place;
            }

            return number;
        }

        static string invert(string s)
        {
            string t = "";
            for (int i = s.Length - 1; i >= 0; i--)
            {
                t += s[i];
            }

            return t;
        }

        static string ConvertToBase(Int64 Number, Int64 Base)
        {
            bool negative = false;
            string result;
            if (Number < 0) // negative result!!
            {
                Number = -Number;
                negative = true;
            }

            Int64 PlaceValue = Number % Base;
            Int64 WorkingValue = Number / Base;
            string invertedValue = NumberToLetter(PlaceValue).ToString();
            while (WorkingValue > 0)
            {
                PlaceValue = WorkingValue % Base;
                WorkingValue /= Base;
                invertedValue += NumberToLetter(PlaceValue);
            }

            result = invert(invertedValue);
            if (negative)
                result = "-" + result;

            return result;
        }

        static void Main(string[] args)
        {
            // First read in this mess
            string str = System.Console.ReadLine();

            // Now tokenize the string to get at the data. Operators come later.
            string[] STRS = str.Split(new char[] { '+', '-', '*', '=' }, StringSplitOptions.RemoveEmptyEntries);

            //Now, the operators
            string[] OPS = str.Split(new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b',
                                                   'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
                                                   'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                                                   '^', '='}, StringSplitOptions.RemoveEmptyEntries);

            Int64[] NUMS = new Int64[STRS.Length - 1];

            int i;
            for( i = 0; i < STRS.Length - 1; i ++ ) // The last string is in a different format
            {
                // We need to do a conversion and store it back in the NUMS bucket
                NUMS[i] = convert(STRS[i]);
            }

            string[] SENTENCE = new string[NUMS.Length + OPS.Length];
            string[] SIMPLESENTENCE = new string[SENTENCE.Length];
            i = 0;
            int j = 0;
            int k = 0;
            while (i < NUMS.Length)
            {
                SENTENCE[k] = NUMS[i].ToString();
                k++;
                if( j < OPS.Length ) SENTENCE[k] = OPS[j];
                i++;
                j++;
                k++;
            }

            i = 0;
            j = 0;
            bool tMultiplying = false;
            Int64 product = 1;
            while (i < SENTENCE.Length)
            {
                if (Char.IsDigit(SENTENCE[i][0]))
                {
                    if (!tMultiplying)
                    {
                        SIMPLESENTENCE[j] = SENTENCE[i];
                        j++;
                    }
                    else
                    {
                        product *= Int64.Parse(SENTENCE[i]);
                    }
                }
                else
                {
                    if (SENTENCE[i] == "*")
                    {
                        if (!tMultiplying)
                            product *= Int64.Parse(SENTENCE[i - 1]);
                        tMultiplying = true;
                    }
                    else
                    {
                        if (product > 1)
                        {
                            SIMPLESENTENCE[--j] = product.ToString(); ;
                            j++;
                            product = 1;
                        }
                        tMultiplying = false;
                        SIMPLESENTENCE[j] = SENTENCE[i];
                        j++;
                    }
                }

                i++;
            }
            if (product > 1) // We still have to store a running multiplication
                SIMPLESENTENCE[--j] = product.ToString();

            // Now just walk the simple sentence doing addition and subtraction
            bool tAdd = true;
            Int64 result = 0;
            for (i = 0; i < SIMPLESENTENCE.Length; i++)
            {
                if (SIMPLESENTENCE[i] == null)
                {
                    break;
                }

                if (Char.IsDigit(SIMPLESENTENCE[i][0]))
                {
                    if (tAdd)
                        result += Int64.Parse(SIMPLESENTENCE[i]);
                    else
                        result -= Int64.Parse(SIMPLESENTENCE[i]);
                }
                else
                {
                    if (SIMPLESENTENCE[i] == "-")
                        tAdd = false;
                    else
                        tAdd = true;
                }
            }

            // Recall that the final entry in STRS is the output base in format ^base
            Int64 Base = Int64.Parse(STRS[STRS.Length - 1].Split(new char[] { '^' }, StringSplitOptions.RemoveEmptyEntries)[0]);
            string Result = ConvertToBase(result, Base);
            System.Console.WriteLine(Result + "^" + Base);
            System.Console.ReadLine();
        }
    }
}
