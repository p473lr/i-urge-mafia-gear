import java.io.*;
import java.util.*;
import java.lang.*;

class QueueEntry {
	void setEntry(String s, int o) {
		QString = s;
		offset = o;
	}
	String QString;
	int offset;
};

class Queue {
	int index = 0;
	int queue_num = 0;
	QueueEntry[] Q = new QueueEntry[9];

	Queue() {
		index = 0;
		queue_num = 0;
		for (int i = 0; i < 9; i++)
			Q[i] = new QueueEntry();
	}

	void addQEntry(String s, int off) {
		Q[queue_num].setEntry(s, off);
		++queue_num;
	}

	QueueEntry getNextQEntry() {
		return Q[index++];
	}

	String printString()
	{
		String s = new String("H");
		return s;
	}
};

class QueuingTheory
{
	public static void main(String args[])
	{
		Scanner scan = new Scanner(System.in);
		int queue_len; // Length of each queue
		int num_inputs;
		int i;
		Queue[] queue = new Queue[9];

		// Read in queue length
		queue_len = scan.nextInt();
		// Read in number of inputs to process
		num_inputs = scan.nextInt();

		for (i = 0; i < 9; i++)
			queue[i] = new Queue();

		// Now read in each queue number, offseet, and string to add.
		// Add it to a Q.
		for (i = 0; i < num_inputs; i++) {
			String Q = scan.next();
			int qoffset = scan.nextInt();
			String QString = scan.next();
			int Qnum = Q.charAt(1) - '0';
			queue[Qnum].addQEntry(QString, qoffset);
		} // for

		char[] FinalString = new char[44];
		for (i = 0; i < 44; i++)
			FinalString[i] = ' ';

		for (i = 0; i < num_inputs; i++) {
			int k = 0;
			QueueEntry Q;
			String s = scan.next();
			int Qnum = s.charAt(1) - '0';
			Q = queue[Qnum].getNextQEntry();

			k = 0;
			for (int j = Q.offset; j < (Q.offset + Q.QString.length()); j++) {
				FinalString[j] = Q.QString.charAt(k++);
			}
		}
		for (i = 0; i < 44; i++)
			System.out.print(FinalString[i]);
		System.out.println();

	} // main
}
