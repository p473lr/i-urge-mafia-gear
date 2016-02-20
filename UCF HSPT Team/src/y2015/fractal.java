import java.util.Scanner;

public class fractal {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int runs = scan.nextInt();

		for(int caseNum = 1; caseNum <= runs; caseNum++) {
			int a = scan.nextInt(), b = scan.nextInt();
			/**
			  * This whole problem simplifies down to the equation x = a/x + b/x after substituting in x for the denominators of the expression.
			  * Some algebra will rearrange the equation to x^2 = a + b or x = sqrt(a + b).
			  * Since we can't take the square root of a negative number, x doesn't exist if the sum of a and b is negative.
			  */
			if(a + b > 0)
				System.out.printf("Fraction #%d: %.2f\n", caseNum, Math.sqrt(a + b));
			else
				System.out.printf("Fraction #%d: DNE\n", caseNum);
		}
		scan.close();
	}
}
