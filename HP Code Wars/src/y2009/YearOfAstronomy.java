//********************************************************************
//  YearOfAstronomy		Author: Don Brace
//********************************************************************
import java.io.*;
import java.util.*;

public class YearOfAstronomy
{
	public static void main (String[] args) {

		Scanner			scanner = new Scanner(System.in);
		float			fo = 0.0f;
		float			d = 0.0f;
		float			fe = 0.0f;
		float			f = 0.0f;
		float			p = 0.0f;

		fo = scanner.nextFloat();
		d = scanner.nextFloat();
		fe = scanner.nextFloat();

		//System.out.println("fo:" + fo + " d:" + d + " fe:" + fe);

		f = fo / d;
		p = fe / f;

		System.out.println(f);
		System.out.println(p);
} // main
}
