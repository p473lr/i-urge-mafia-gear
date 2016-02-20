import java.util.Scanner;

public class PonziCalculator 
{
    private static int targetProfit = 10000000;
    
    private int investorsPerMonth;
    private int amountPerInvestor;
    private float interestPerMonth;
    
    private int month;
    private int investorCount;
    private int startBalance;
    private int endBalance;
    private int interestPaid;
    
    /**
     * Constructs a new ponzi calculator that can run one simulation of the
     * scam.
     * 
     * @param amountPerInvestor The amount of money each investor invests.
     * @param investorsPerMonth The number of new investors each month.
     * @param interestPerYear The amount of interest the investors will earn 
     * over a year.  This is in percentage form.
     */
    public PonziCalculator(int amountPerInvestor, int investorsPerMonth, int interestPerYear)
    {
        this.amountPerInvestor = amountPerInvestor;
        this.investorsPerMonth = investorsPerMonth;
        this.interestPerMonth = interestPerYear / 12.0f / 100;
        
        month = 0;
        investorCount = 0;
        startBalance = 0;
        endBalance = 0;
        interestPaid = 0;
    }
    
    /**
     * Calculates the results for the next month.
     */
    public void nextMonth()
    {
        ++month;
        investorCount += investorsPerMonth;
        startBalance = endBalance + (investorsPerMonth * amountPerInvestor);
        interestPaid = (int)(investorCount * amountPerInvestor * interestPerMonth);
        endBalance = startBalance - interestPaid;
    }
    
    /**
     * Returns true when the ponzi experiment has either failed or succeeded. 
     * This returns false when the experiment should continue.
     */
    public boolean isDone()
    {
        return endBalance < 0 || startBalance >= targetProfit;
    }
    
    /**
     * Prints the header for the table.
     */
    public void printHeader()
    {
        System.out.println("Month Investors Begin Balance  Interest Paid  End Balance");
        System.out.println("----- --------- -------------- -------------- ---------------");
    }
    
    /**
     * Prints the results for the current month.
     */
    public void printMonthsResults()
    {
        System.out.printf("%-6d %-10d $%-13d $%-13d $%-13d\n", month, investorCount, startBalance, interestPaid, endBalance);
    }
    
    /**
     * Prints the final results of the ponzi experiment.  The method 
     * isDone() should return true before calling this method.
     */
    public void printResults()
    {
        System.out.println();
        if (endBalance < 0)
        {
            System.out.println("Abort Project Emu Farms!");
        }
        else if (startBalance >= targetProfit)
        {
            System.out.println("Total Balance at beginning of Month " + month + " is $" + startBalance);
            System.out.println("Purple Horseshow loves Emu Farms!");
        }
        System.out.println();
    }
    
    private static Scanner scanner;
    
    /**
     * Prints the given prompt and reads the input as an int.
     */
    public static final int readInt(String prompt)
    {
        System.out.print(prompt);
        return scanner.nextInt();
    }
    
    public static void main(String[] args) 
    {
        scanner = new Scanner(System.in);
        int amountPerInvester = readInt("Investment Cost (per person) $: ");
        int investorsPerMonth = readInt("New Investors (per month): ");
        int interestPerYear = readInt("Guaranteed Annual Interest Rate (%): ");
        System.out.println();
        
        PonziCalculator calculator = 
            new PonziCalculator(amountPerInvester, investorsPerMonth, interestPerYear);
        
        calculator.printHeader();
        while (!calculator.isDone())
        {
            calculator.nextMonth();
            calculator.printMonthsResults();
        }
        calculator.printResults();
    }
}
