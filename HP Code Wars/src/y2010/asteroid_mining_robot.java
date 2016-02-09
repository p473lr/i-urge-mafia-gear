/*
 * Author: Don Brace
 *
 * Solves asteroid mining problem.
 *
 * Key to solution: Paying attention to the following:
 *                  1. Mining continues until low on fuel. [Key #1!]
 *                  2. Optimize cargo by value. Like the Knapsack problem.
 *                  3. Fill ore until you reach weight limit.
 *
 * I used a lot of classes and Vectors for this solution to demonstrate 
 * their use in object-oriented programming.
 */

import java.util.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;

class max_values {
	max_values() {
		weight = 0.0;
		value = 0.0;
	}
	max_values(double w, double v) {
		weight = w;
		value = v;
	}
	double weight;
	double value;
};

class ore_values {
	String ore_name;
	double value;

	ore_values(String s, double d) {
		ore_name = s;
		value = d;
	}
};

class ore_excavation {
	int	fuel_to_excavate;
	String	ore_type;
	int	ore_mass;
	double	ore_value;

	ore_excavation() {
	}

	ore_excavation(int f, String o, int m, double v) {
		fuel_to_excavate = f;
		ore_type = o;
		ore_mass = m;
		ore_value = v;
	} /* ore_excavation */

	void Print(String msg) { 
		System.out.println(msg + fuel_to_excavate + " " +
					ore_type + " " + ore_mass +
					" " + ore_value);
	}
}; /* ore excavation */

class mining_project {
	int	beginning_fuel;
	int	return_fuel;
	int	cargo_size;
	int	num_excavations;
	Vector<ore_excavation> oe;
	Vector<ore_values> ov;
	Vector<ore_excavation> returned_ore = new Vector<ore_excavation>();

	mining_project() {
		num_excavations = 0;
		oe = new Vector<ore_excavation>();
		ov = new Vector<ore_values>();

		/*
 		 * Make a table of minerals and their values
 		 */
		ov.add(new ore_values("Cu", 1.730));
		ov.add(new ore_values("Zn", 1.130));
		ov.add(new ore_values("H2O", 3.720));
		ov.add(new ore_values("Fe", 0.410));
		ov.add(new ore_values("Si", 0.320));
		ov.add(new ore_values("Mg", 3.460));
		ov.add(new ore_values("C", 2.750));
		ov.add(new ore_values("Pt", 25000.0));
		ov.add(new ore_values("Au", 12260.0));
		ov.add(new ore_values("Ag", 190.629));
	}

	/* Lookup the mineral in the list */
	ore_values FindOreValue(String ore) {
		int i;
		for (i = 0; i < ov.size(); i++) {
			ore_values v = ov.get(i);
			if (v.ore_name.equals(ore))
				return v;
		}
		return null;
	}

	/* Add the ore to the list of excavated minerals */
	void AddExcavation(int fuel, String ore, int mass) {

		ore_values v = FindOreValue(ore);
		double value = mass * v.value;

		ore_excavation o = new ore_excavation(fuel, ore, mass, value);
		oe.add(o);
		++num_excavations;
	} /* AddExcavation */

	/* 
 	 * Add ore sorted by descending value.
 	 */
	void AddToCargo(ore_excavation o) {
		int i;

		if (returned_ore.size() == 0) {
			returned_ore.add(o);
			return;
		}

		for (i = 0; i < returned_ore.size(); i++) {
			ore_excavation o1 = returned_ore.get(i);

			/* Sort descending */
			if (o.ore_value > o1.ore_value) {
				returned_ore.add(i, o);
				return;
			}
		}
		returned_ore.add(o);
	}

	/* 
 	 * Mine the ore.
 	 */
	void MineOre() {
		int i;
		int fuel = beginning_fuel;
		max_values mv;

		/* 
 		 * Add ore to cargo until low on fuel
 		 */
		for (i = 0; i < oe.size(); i++) {

			ore_excavation o = oe.get(i);

			fuel -= o.fuel_to_excavate;

			if ((fuel < return_fuel))
				break;

			AddToCargo(o);

		} /* for */

		/* Now optimize the cargo for value */
		mv = OptimizeCargo(returned_ore, cargo_size, 0);

		NumberFormat formatter = new DecimalFormat(".00"); 
		String s = formatter.format(Math.round(mv.value));
		System.out.println("kg=" + mv.weight + " value=" + s);
	}

	/*
 	 * OptimizeCargo - Similar to the Knapsack problem. Want to 
 	 * optimize for weight and value. Lookup Knapsack problems to 
 	 * see different solutions.
 	 *
 	 * This function tracks both weight and value.
 	 */
	max_values OptimizeCargo (Vector<ore_excavation> oe, double wt, 
					int index) {
		int i;
		double weight = 0.0;
		max_values mv;
		max_values new_mv = new max_values();
		/*
 		 * I am making a subset of the original vector because I
 		 * will be taking mined minerals out to test if adding
 		 * it to the cargo will maximize my value. I may need to
 		 * remove this mineral from the cargo and use another.
 		 *
 		 * But the beauty of recursion is that if the mineral does
 		 * not fit, unwinding the stack frames returns the
 		 * algorithm to a previous state to try other combinations of
 		 * minerals.
 		 *
 		 * Making a copy of the input vector allows this to happen.
 		 */
		Vector<ore_excavation> ro = new Vector<ore_excavation>(oe);

		/* Loop over all remaining minerals in subset */
		for (i = index; i < ro.size(); i++) { 

			ore_excavation o = ro.get(i);

			weight = wt - o.ore_mass;

			if (weight >= 0.0) {
				/*
 				 * Weight will work. Now make the working
 				 * set smaller and try more minerals.
 				 * Pass the smaller set of remaining minerals
 				 * to try to add them to the cargo.
 				 */
				ro.remove(i); // Make set smaller.
				mv = OptimizeCargo(ro, weight, 0);
				mv.value += o.ore_value;

				if (mv.value > new_mv.value) {
					new_mv.value = mv.value;
					new_mv.weight = mv.weight + o.ore_mass;
				}
				continue;
			}
		} /* for */
		return new_mv;
	} /* OptimizeCargo */

	/*
 	 * Just a debug function to print out my datasets.
 	 */
	void Print() {
		int i;
		System.out.println("beg_fuel:" + beginning_fuel +
					" return_fuel:" + return_fuel +
					" cargo_size:" + cargo_size);
		for (i = 0; i < num_excavations; i++) {
			ore_excavation o = oe.get(i);
			o.Print("   ");
		}
	} /* Print */
}; /* mining_project */

public class asteroid_mining_robot {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		mining_project mp = new mining_project();
		String ore_type;
		int fuel;
		int weight;

		/* Read in beginnig fuel, return_fuel, cargo_size */
		mp.beginning_fuel = scan.nextInt();
		mp.return_fuel = scan.nextInt();
		mp.cargo_size = scan.nextInt();

		/*
 		 * Read in mine excavation data
 		 * We use a sentinal value to stop
 		 */
		while (true) {
			fuel = scan.nextInt();
			ore_type = scan.next();
			weight = scan.nextInt();
			if ((fuel == 0) && ore_type.equals("X") &&
				(weight == 0))
				break;
			mp.AddExcavation(fuel, ore_type, weight);
		} /* while */

		mp.MineOre();
	} /* main */
}; /* asteroid_mining_robot */
