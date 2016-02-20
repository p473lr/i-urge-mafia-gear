import java.io.InputStream;
import java.io.PrintStream;

public class QwertyGenerator
{
	public static final int RB = -2;
	public static final int RW = -1;
	private static final int UB = 2;
	private static final int UW = 1;

	public int[][] hitmap =
			new int[][] {
					{ RB, RB, RB, RB, RB, RB, RB, RW, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00,
							00, RW, RB, RB, RB, RB, RB, RB, RB },
					{ RB, RW, RW, RW, RW, RW, RB, RW, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00,
							00, RW, RB, RW, RW, RW, RW, RW, RB },
					{ RB, RW, RB, RB, RB, RW, RB, RW, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00,
							00, RW, RB, RW, RB, RB, RB, RW, RB },
					{ RB, RW, RB, RB, RB, RW, RB, RW, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00,
							00, RW, RB, RW, RB, RB, RB, RW, RB },
					{ RB, RW, RB, RB, RB, RW, RB, RW, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00,
							00, RW, RB, RW, RB, RB, RB, RW, RB },
					{ RB, RW, RW, RW, RW, RW, RB, RW, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00,
							00, RW, RB, RW, RW, RW, RW, RW, RB },
					{ RB, RB, RB, RB, RB, RB, RB, RW, RB, RW, RB, RW, RB, RW, RB, RW, RB, RW, RB, RW, RB, RW, RB, RW,
							RB, RW, RB, RB, RB, RB, RB, RB, RB },
					{ RW, RW, RW, RW, RW, RW, RW, RW, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00,
							00, RW, RW, RW, RW, RW, RW, RW, RW },
					{ 00, 00, 00, 00, 00, 00, RB, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00,
							00, 00, 00, 00, 00, 00, 00, 00, 00 },
					{ 00, 00, 00, 00, 00, 00, RW, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00,
							00, 00, 00, 00, 00, 00, 00, 00, 00 },
					{ 00, 00, 00, 00, 00, 00, RB, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00,
							00, 00, 00, 00, 00, 00, 00, 00, 00 },
					{ 00, 00, 00, 00, 00, 00, RW, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00,
							00, 00, 00, 00, 00, 00, 00, 00, 00 },
					{ 00, 00, 00, 00, 00, 00, RB, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00,
							00, 00, 00, 00, 00, 00, 00, 00, 00 },
					{ 00, 00, 00, 00, 00, 00, RW, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00,
							00, 00, 00, 00, 00, 00, 00, 00, 00 },
					{ 00, 00, 00, 00, 00, 00, RB, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00,
							00, 00, 00, 00, 00, 00, 00, 00, 00 },
					{ 00, 00, 00, 00, 00, 00, RW, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00,
							00, 00, 00, 00, 00, 00, 00, 00, 00 },
					{ 00, 00, 00, 00, 00, 00, RB, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00,
							00, 00, 00, 00, 00, 00, 00, 00, 00 },
					{ 00, 00, 00, 00, 00, 00, RW, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00,
							00, 00, 00, 00, 00, 00, 00, 00, 00 },
					{ 00, 00, 00, 00, 00, 00, RB, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00,
							00, 00, 00, 00, 00, 00, 00, 00, 00 },
					{ 00, 00, 00, 00, 00, 00, RW, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00,
							00, 00, 00, 00, 00, 00, 00, 00, 00 },
					{ 00, 00, 00, 00, 00, 00, RB, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00,
							00, 00, 00, 00, 00, 00, 00, 00, 00 },
					{ 00, 00, 00, 00, 00, 00, RW, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00,
							00, 00, 00, 00, 00, 00, 00, 00, 00 },
					{ 00, 00, 00, 00, 00, 00, RB, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00,
							00, 00, 00, 00, 00, 00, 00, 00, 00 },
					{ 00, 00, 00, 00, 00, 00, RW, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00,
							00, 00, 00, 00, 00, 00, 00, 00, 00 },
					{ 00, 00, 00, 00, 00, 00, RB, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00,
							RB, RB, RB, RB, RB, 00, 00, 00, 00 },
					{ RW, RW, RW, RW, RW, RW, RW, RW, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00,
							RB, RW, RW, RW, RB, 00, 00, 00, 00 },
					{ RB, RB, RB, RB, RB, RB, RB, RW, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00,
							RB, RW, RB, RW, RB, 00, 00, 00, 00 },
					{ RB, RW, RW, RW, RW, RW, RB, RW, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00,
							RB, RW, RW, RW, RB, 00, 00, 00, 00 },
					{ RB, RW, RB, RB, RB, RW, RB, RW, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00,
							RB, RB, RB, RB, RB, 00, 00, 00, 00 },
					{ RB, RW, RB, RB, RB, RW, RB, RW, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00,
							00, 00, 00, 00, 00, 00, 00, 00, 00 },
					{ RB, RW, RB, RB, RB, RW, RB, RW, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00,
							00, 00, 00, 00, 00, 00, 00, 00, 00 },
					{ RB, RW, RW, RW, RW, RW, RB, RW, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00,
							00, 00, 00, 00, 00, 00, 00, 00, 00 },
					{ RB, RB, RB, RB, RB, RB, RB, RW, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00,
							00, 00, 00, 00, 00, 00, 00, 00, 00 }
	};

	public QwertyGenerator()
	{
	}

	int x = 32, y = 32;

	void reset()
	{
		x = 32;
		y = 32;
	}

	void stashNext(int bitval)
	{
//		hitmap[x][y] = bitval;
		if(bitval != 0)
			hitmap[x][y] = UB;
		else
			hitmap[x][y] = UW;

		do
		{
			if (x % 2 == 0) // even numbered column, going upward.
			{
				if (y == 0) // we're at the top. move to the left one.
					x--;
				else
					y--; // not at the top. move up one.
			}
			else
			// odd numbered column, going downard
			{
				if (y == 32) // we're at the bottom. move to the left one.
					x--;
				else
					y++; // not at the bottom, move down one
			}
		} while (x >= 0 && y >= 0 && hitmap[x][y] != 0);
	}

	public void go(InputStream stdin, PrintStream stdout)
	{
		byte[] data = "Welcome to CodeWars and Fun with QweRty Codes! Welcome to CodeWars and Fun with QweRty Codes!".getBytes();

		for(int i = 0; i < data.length * 8; i++)
			stashNext(data[i / 8] & (1<<(7-(i%8))));

		// Debug, for showing the pixel order
//		for (int i = 0; i < (33 * 33) - (3 * (8 * 8)) - 25 - 17 - 17; i++)
//			stashNext(i % 256);

		for (int iy = 0; iy < 33; iy++)
		{
			for (int ix = 0; ix < 33; ix++)
			{
//				stdout.printf("%02x", (byte) hitmap[ix][iy]);  // debug, display pixel value
				if(Math.abs(hitmap[ix][iy]) == UB)
					stdout.print("##");
				else
					stdout.print("  ");
			}
			stdout.println();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		new QwertyGenerator().go(null, System.out);
	}

}
