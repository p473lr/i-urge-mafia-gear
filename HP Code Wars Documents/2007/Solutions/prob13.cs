using System;
using System.Collections.Generic;
using System.Text;

namespace NotQuiteOCR2
{
    enum FourState
    {
        Good,
        Bad,
        Neither,
        NotGarbled
    }

    class Constants
    {
        public static int DigitDimension
        {
            get { return 3;  }
        }
        public static int AccNumLength
        {
            get { return 27; }
        }
        public static string One
        {
            get { return "     |  |"; }
        }
        public static string Two
        {
            get { return " _  _||_ "; }
        }
        public static string Three
        {
            get { return " _  _| _|"; }
        }
        public static string Four
        {
            get { return "   |_|  |"; }
        }
        public static string Five
        {
            get { return " _ |_  _|"; }
        }
        public static string Six
        {
            get { return " _ |_ |_|"; }
        }
        public static string Seven
        {
            get { return " _   |  |"; }
        }
        public static string Eight
        {
            get { return " _ |_||_|"; }
        }
        public static string Nine
        {
            get { return " _ |_| _|"; }
        }
        public static string Zero
        {
            get { return " _ | ||_|"; }
        }
    }

    class AccountNumber
    {
        string[] digits;
        int[] numbers;
        int numDigits;
        int garbledIndex;
        int checksum;

        public AccountNumber()
        {
            numDigits = Constants.AccNumLength / Constants.DigitDimension;
            digits = new string[numDigits];
            numbers = new int[numDigits];
            for (int i = 0; i < numDigits; i++)
            {
                digits[i] = "";
                numbers[i] = -1;
            }
            garbledIndex = -1;
            checksum = 0;
        }

        public void AddData(string[] str)
        {
            int i;
            int j;
            int k;
            int h;
            for( i = 0; i < str.Length; i++ )
            {
                k = 0;
                for (j = 0; j < numDigits; j++)
                {
                    for( h = 0; h < Constants.DigitDimension; h++ )
                    {
                        digits[numDigits-(j+1)] += str[i][k];
                        k++;
                    }
                }
            }

            for (j = 0; j < numDigits; j++)
            {
                numbers[j] = GetNumber(digits[j]);
                if (numbers[j] == -1)
                {
                    garbledIndex = j;
                }
            }

            if (garbledIndex < 0) // No garbled digits
            {
                CalculateChecksum();
            }

            // Otherwise, no checksum knowale yet.
        }

        protected int GetNumber(string strNumber)
        {
            int result = -1;

            if (strNumber == Constants.One)
                result = 1;
            else if (strNumber == Constants.Two)
                result = 2;
            else if (strNumber == Constants.Three)
                result = 3;
            else if (strNumber == Constants.Four)
                result = 4;
            else if (strNumber == Constants.Five)
                result = 5;
            else if (strNumber == Constants.Six)
                result = 6;
            else if (strNumber == Constants.Seven)
                result = 7;
            else if (strNumber == Constants.Eight)
                result = 8;
            else if (strNumber == Constants.Nine)
                result = 9;
            else if (strNumber == Constants.Zero)
                result = 0;
            else result = -1;

            return result;
        }

        public bool IsGarbled()
        {
            return (garbledIndex >= 0);
        }

        public string Number
        {
            get
            {
                string strResult = "";
                for (int i = numDigits-1; i >= 0; i--)
                {
                    strResult += numbers[i].ToString();
                }

                return strResult;
            }
        }

        protected void CalculateChecksum()
        {
            checksum = 0;
            for (int i = 0; i < numDigits; i++)
            {
                checksum += (i + 1) * numbers[i];
            }
        }

        public bool Checksum()
        {
            if (garbledIndex >= 0) // there's a garbled digit
                return false;

            CalculateChecksum();
            if (checksum % 11 == 0)
                return true;

            return false;
        }

        protected bool Checksum(bool IgnoreGarbling)
        {
            if (IgnoreGarbling)
            {
                CalculateChecksum();
                if (checksum % 11 == 0)
                    return true;

                return false;
            }
            else
                return Checksum();
        }

        protected bool CouldItBe(string check, string theNumber)
        {
            if (check.Length != theNumber.Length)
                return false;

            int i;
            for (i = 0; i < check.Length; i++)
            {
                // If the character in check is NOT a space then it must be identical to the character in theNumber
                if (check[i] != ' ')
                {
                    if (check[i] != theNumber[i])
                        return false;
                }
                else
                {
                    // If it IS a space, then the character in theNumber is either identical (i.e.
                    // also a space), or this is a place to add missing data
//                    if (theNumber[i] == ' ')
//                    {
//                        return false;
//                    }
                    // Basically, it's a no-op.
                }
            }

            // if we get here, then it is possible that check could add one or more '|' or '_' to produce theNumber

            return true;
        }

        public FourState Ungarble()
        {
            if (garbledIndex < 0) // it's not garbled
                return FourState.NotGarbled;

            string str = digits[garbledIndex];
            int itCouldBe = -1;
            int numCouldBes = 0;
            int temp = 0;

            if (CouldItBe(str, Constants.One))
            {
                temp = numbers[garbledIndex];
                numbers[garbledIndex] = 1;
                if (Checksum(true))
                {
                    itCouldBe = 1;
                    numCouldBes++;
                }
                else
                    numbers[garbledIndex] = temp;
            }
            if (CouldItBe(str, Constants.Two))
            {
                temp = numbers[garbledIndex];
                numbers[garbledIndex] = 2;
                if (Checksum(true))
                {
                    itCouldBe = 2;
                    numCouldBes++;
                }
                else
                    numbers[garbledIndex] = temp;
            }
            if (CouldItBe(str, Constants.Three))
            {
                temp = numbers[garbledIndex];
                numbers[garbledIndex] = 3;
                if (Checksum(true))
                {
                    itCouldBe = 3;
                    numCouldBes++;
                }
                else
                    numbers[garbledIndex] = temp;
            }
            if (CouldItBe(str, Constants.Four))
            {
                temp = numbers[garbledIndex];
                numbers[garbledIndex] = 4;
                if (Checksum(true))
                {
                    itCouldBe = 4;
                    numCouldBes++;
                }
                else
                    numbers[garbledIndex] = temp;
            }
            if (CouldItBe(str, Constants.Five))
            {
                temp = numbers[garbledIndex];
                numbers[garbledIndex] = 5;
                if (Checksum(true))
                {
                    itCouldBe = 5;
                    numCouldBes++;
                }
                else
                    numbers[garbledIndex] = temp;
            }
            if (CouldItBe(str, Constants.Six))
            {
                temp = numbers[garbledIndex];
                numbers[garbledIndex] = 6;
                if (Checksum(true))
                {
                    itCouldBe = 6;
                    numCouldBes++;
                }
                else
                    numbers[garbledIndex] = temp;
            }
            if (CouldItBe(str, Constants.Seven))
            {
                temp = numbers[garbledIndex];
                numbers[garbledIndex] = 7;
                if (Checksum(true))
                {
                    itCouldBe = 7;
                    numCouldBes++;
                }
                else
                    numbers[garbledIndex] = temp;
            }
            if (CouldItBe(str, Constants.Eight))
            {
                temp = numbers[garbledIndex];
                numbers[garbledIndex] = 8;
                if (Checksum(true))
                {
                    itCouldBe = 8;
                    numCouldBes++;
                }
                else
                    numbers[garbledIndex] = temp;
            }
            if (CouldItBe(str, Constants.Nine))
            {
                temp = numbers[garbledIndex];
                numbers[garbledIndex] = 9;
                if (Checksum(true))
                {
                    itCouldBe = 9;
                    numCouldBes++;
                }
                else
                    numbers[garbledIndex] = temp;
            }
            if (CouldItBe(str, Constants.Zero))
            {
                temp = numbers[garbledIndex];
                numbers[garbledIndex] = 0;
                if (Checksum(true))
                {
                    itCouldBe = 0;
                    numCouldBes++;
                }
                else
                    numbers[garbledIndex] = temp;
            }

            if (numCouldBes == 1) // if and only if there is a single possibility
            {
                numbers[garbledIndex] = itCouldBe;
                garbledIndex = -1;
                return FourState.Good;
            }
            else
            {
                if (numCouldBes > 1)
                    return FourState.Neither;
                else
                    return FourState.Bad;
            }
        }
    }

    class Program
    {
        static void Main(string[] args)
        {
            string strNumAccounts;
            string[] AccountData;
            int numAccounts;
            AccountNumber acc = new AccountNumber();
            string Output = "";

            // First read the number of lines of input
            strNumAccounts = System.Console.ReadLine();
            numAccounts = Int32.Parse(strNumAccounts);

            AccountData = new string[Constants.DigitDimension];
            for (int i = 0; i < numAccounts; i++)
            {
                for (int j = 0; j < Constants.DigitDimension; j++)
                {
                    AccountData[j] = System.Console.ReadLine();
                }
                acc.AddData(AccountData);
                if (!acc.IsGarbled())
                {
                    // It's either valid or invalid as-is.
                    if (acc.Checksum())
                        Output += acc.Number + '\n';
                    else
                        Output += "failure\n";
                }
                else
                {
                    // Garbling has occurred or the account number is invalid
                    FourState fs = acc.Ungarble();
                    if (fs == FourState.Good) // We've ungarbled exactly one possibility
                    {
                        if (acc.Checksum())
                            Output += acc.Number + '\n';
                        else
                            Output += "failure\n";
                    }
                    else if (fs == FourState.Bad) // The account number cannot be determined, so it's outside the bounds of the prolem definition
                    {
                        Output += "OUTSIDE THE BOUNDS OF THE PROBLEM DEFINITION\n";
                    }
                    else if (fs == FourState.Neither) // More than one possible acount numbers exist
                    {
                        Output += "ambiguous\n";
                    }
                    else // fs == FourState.NotGarbled -- should never get here
                    {
                        Output += "LOGIC ERROR IN ANALYZING ACCOUNT NUMBERS\n";
                    }
                }
                
                // Prepare for the next entry
                acc = null;
                acc = new AccountNumber();
            }

            System.Console.Write(Output);
            System.Console.ReadLine();
        }
    }
}
