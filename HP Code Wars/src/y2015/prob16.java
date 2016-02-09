// Sebastian Schagerer
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class prob16 {

    String[] input = null;
    String message = null;
    Map<String, State> states = null;
    
    public prob16() {
        readInput();
        createStates();
        //printStates();
        String decodedMessage = decodeMessage();
        System.out.println(decodedMessage);
    }
    
    private String decodeMessage() {
        
        StringBuilder decodedMessage = new StringBuilder();
        State currentState = states.get("#");
        
        for(int index = 0; index < message.length(); index++) {
            String transition = message.substring(index, index+1);
            currentState = currentState.getNextState(transition);
            decodedMessage.append(currentState.label);
        }
        return decodedMessage.toString();
    }
    
    private void printStates() {
        Set<String> stateKeys = states.keySet();
        for (String key : stateKeys) {
            State aState = states.get(key);
            System.out.println(aState.toString());
        }
    }
    
    private void createStates() {
        states = new HashMap<String, State>();
        
        for (int i = 0; i < input.length; i++) {
            String stateLine = input[i];
            String[] parts = stateLine.split(" ");
            String state = parts[0];
            String trigger = parts[1];
            String nextState = parts[2];
            
            if (false == states.containsKey(state)) {
                State newState = new State(state);
                states.put(state, newState);
            }
            
            if (false == states.containsKey(nextState)) {
                State newState = new State(nextState);
                states.put(nextState, newState);
            }
            
            State fromState = states.get(state);
            State toState = states.get(nextState);
            fromState.addTransition(trigger, toState);
        }
    }
    
    private void readInput() {
        try {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String line = stdIn.readLine();
            
            int numStates = new Integer(line).intValue();
            input = new String[numStates];
            
            for (int s = 0; s < numStates; s++) {
                line = stdIn.readLine();
                input[s] = line;
            }
            
            message = stdIn.readLine();
        }
        catch(IOException ioex) {
            ioex.printStackTrace();
        }
    }
    public static void main(String[] args) {
    
        new prob16();
    }
}

class State {
    
    public String label;
    // Map <trigger, next state>
    public Map<String, State> triggers;
    
    public State(String label) {
        this.label = label;
        triggers = new HashMap<String, State>();
    }
    
    public void addTransition(String trigger, State nextState) {
        triggers.put(trigger, nextState);
    }
    
    public State getNextState(String trigger) {
        State nextState = null;
        
        if (triggers.containsKey(trigger)) {
            nextState = triggers.get(trigger);
        }
        
        return nextState;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[State: ");
        sb.append(label);
        sb.append(", triggers { ");
        
        Set<String> triggerKeys = triggers.keySet();
        for(String trigger : triggerKeys) {
            sb.append("(");
            sb.append(trigger);
            sb.append(">");
            State nextState = triggers.get(trigger);
            sb.append(nextState.label);
            sb.append(") ");
        }
        sb.append("}]");
        return sb.toString();
    }
}