// Tentaizu
// Ryan Patterson

import java.io.*;
import java.util.*;

public class tentaizu {
	static int[][] board = new int[7][7];
	static int[][] adj = new int[7][7];
	static int[][] adj2 = new int[7][7];
	static int nfilled;
	
	public static void main(String[] args) throws Exception {
		Scanner s = new Scanner(new File("tentaizu.in"));
		int n = s.nextInt();
		for(int i = 1; i <= n; i++) {
			do_puzzle(i, s);
		}
	}
	static void do_puzzle(final int cnum, Scanner s) {
		for(int y = 0; y < 7; y++) {
			char[] line = s.next().toCharArray();
			for(int x = 0; x < 7; x++) {
				board[y][x] = 0;
				adj2[y][x] = 0;
				adj[y][x] = -1;
				if(Character.isDigit(line[x]))
					adj[y][x] = line[x] - '0';
			}
		}
		nfilled = 0;

		System.out.printf("Tentaizu Board #%d:%n", cnum);
		if(!go(0, 0))
			System.out.printf("No solution found!%n");
		System.out.printf("%n");
	}
	static boolean go(int y, int x) {
		if(x == 7) {
			x = 0;
			y++;
		}
		if(y == 7) {
			if(nfilled != 10)
				return false;
			if(adj[5][5] != -1 && adj[5][5] != adj2[5][5])
				return false;
			if(adj[5][6] != -1 && adj[5][6] != adj2[5][6])
				return false;
			for(x = 0; x < 7; x++) {
				if(adj[6][x] != -1 && adj[6][x] != adj2[6][x])
					return false;
			}

			for(y = 0; y < 7; y++) {
				for(x = 0; x < 7; x++) {
					if(board[y][x] != 0) System.out.printf("*");
					else if(adj[y][x] != -1) System.out.printf("%d", adj[y][x]);
					else System.out.printf(".");
				}
				System.out.printf("%n");
			}
			return true;
		}
		if(y >= 1 && x >= 2) {
			if(adj[y - 1][x - 2] != -1 && adj[y - 1][x - 2] != adj2[y - 1][x - 2])
				return false;
		}
		if(y >= 2 && x == 0) {
			if(adj[y - 2][5] != -1 && adj[y - 2][5] != adj2[y - 2][5])
				return false;
			if(adj[y - 2][6] != -1 && adj[y - 2][6] != adj2[y - 2][6])
				return false;
		}
		if(adj[y][x] != -1)
			return go(y, x + 1);
		for(int dx = -1; dx <= 1; dx++) {
			for(int dy = -1; dy <= 1; dy++) {
				if(dx == 0 && dy == 0)
					continue;
				if(y + dy < 0 || x + dx < 0)
					continue;
				if(y + dy >= 7 || x + dx >= 7)
					continue;
				adj2[y + dy][x + dx]++;
			}
		}
		nfilled++;
		board[y][x] = 1;
		if(go(y, x + 1))
			return true;
		nfilled--;
		board[y][x] = 0;
		for(int dx = -1; dx <= 1; dx++) {
			for(int dy = -1; dy <= 1; dy++) {
				if(dx == 0 && dy == 0)
					continue;
				if(y + dy < 0 || x + dx < 0)
					continue;
				if(y + dy >= 7 || x + dx >= 7)
					continue;
				adj2[y + dy][x + dx]--;
			}
		}
		return go(y, x + 1);
	}
}
