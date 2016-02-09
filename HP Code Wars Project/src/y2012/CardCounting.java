/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.*;

/**
 *
 * @author Hazzard
 * Code Wars CardCounting
 */
public class CardCounting {

    static public HashMap<String, String> masterList = new HashMap();

    static void initMaster() {
        String cardnames[] = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

        // build master list of cards for comparison.
        for (int i = 0; i < 13; i++) {
            masterList.put(cardnames[i] + "S", cardnames[i] + "S");
            masterList.put(cardnames[i] + "C", cardnames[i] + "C");
            masterList.put(cardnames[i] + "D", cardnames[i] + "D");
            masterList.put(cardnames[i] + "H", cardnames[i] + "H");
        }
    }

    public static void main(String[] args) {
        // init master list
        initMaster();
        // accept input
        Scanner scan = new Scanner(System.in);
        // ArrayList for any extra Cards
        ArrayList<String> extraCards = new ArrayList();
        // copy the master, we will remove from the list
        HashMap<String, String> masterCopy =
                (HashMap<String, String>) masterList.clone();

        // get number of lines
        int count = scan.nextInt();
        scan.nextLine();

        // parse and test each line
        for (int i = 0; i < count; i++) {
            String line = scan.nextLine();
            StringTokenizer st = new StringTokenizer(line.toUpperCase());
            while (st.hasMoreTokens()) {
                // token the input string
                String card = st.nextToken(" ");
                String he = masterCopy.remove(card);
                // must be an extra card
                if (he == null) {
                    extraCards.add(card);
                } // if

            } // while

        } // for

        // any missing cards?
        if (!masterCopy.isEmpty()) {
            String space = "";      // create a space for additional prints
            System.out.println("Missing cards:");
            Iterator it = masterCopy.keySet().iterator();
            while (it.hasNext()) { // for all entries not found
                System.out.print(space + it.next());
                space = " ";       //
            }
            System.out.println("");
        }


        // any extra cards
        if (extraCards.size() > 0) {
            String space = "";
            System.out.println("Extra cards:");

            HashMap<String, String> used = new HashMap(); // keep from printing double
            // get a count for each card
            // might not be good for large arrays, but it works for this set
            for (String oc : extraCards) {
                int num = 0;
                if (used.get(oc) != null) {
                    continue; // already been counted, move on
                }                // find matching cards

                for (String ec : extraCards) {
                    if (oc.equals(ec)) 
                        num++; // up count
                }

                System.out.print(space + oc + "(" + num + ")");
                used.put(oc, oc);
                space = " ";
            }
            System.out.println("");
        }
    }
}
