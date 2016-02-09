package y2015;

// Sebastian Schagerer
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class prob09 {

    public static void main(String[] args) {
    
        try {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String line = stdIn.readLine();
            Map<Integer, Integer> teamScoreMap = new HashMap<Integer, Integer>();
            
            while (line != null && !line.equals("0 0")) {
            
                String[] numbers = line.split(" ");
                int team = new Integer(numbers[0]).intValue();
                int points = new Integer(numbers[1]).intValue();
                
                Integer existingScore = teamScoreMap.get(team);
                if (null == existingScore) {
                    existingScore = points;
                }
                else {
                    existingScore += points;
                }
                teamScoreMap.put(team, existingScore);
//                System.out.println("Team " + team + " score " + existingScore);
                
                line = stdIn.readLine();
            }
            
            Set<Integer> keys = teamScoreMap.keySet();
            List<Integer> scores = new ArrayList<Integer>(keys.size());
            Map<Integer, Integer> scoreTeamMap = new HashMap<Integer, Integer>(keys.size());
            for (Integer key : keys) {
//                System.out.println("Team " + key + " final score " + teamScoreMap.get(key));
                scores.add(teamScoreMap.get(key));
                scoreTeamMap.put(teamScoreMap.get(key), key);
            }
            
            Collections.sort(scores);
            for (int i = 0; i < 5; i++) {
                int index = (scores.size() - i - 1);
                System.out.println((i+1) + " " + scoreTeamMap.get(scores.get(index)) + " " + scores.get(index));
            }
            
        }
        catch(IOException ioex) {
            ioex.printStackTrace();
        }
    }
}
