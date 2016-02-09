import java.util.*;

public class dollar {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		int N = in.nextInt();
		for (int i=0; i<N; i++) {

			int x = in.nextInt();

			// Integer division by 2 will round down,
			// which is equivalent to only counting
			// every 2nd answer, in this case.
			int d = x/2;

			System.out.printf("Person #%d: $%d\n", i+1, d);

		}
	}
}
