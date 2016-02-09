/***
 * @Project  :  Code Wars Solution to Problem 11
 * @Date     :  03-02-2007
 */
import java.io.*;
import java.util.Scanner;

class LedDecoder {
    private String line = "";   
    private static final String[][][] Leds = {
      {{"1234567"},{"B"}},     
      {{"123457"},{"A"}},
      {{"123459"},{"R"}},
      {{"123567"},{"O"}},
      {{"135790"},{"W"}},
      {{"12347"},{"Q"}},
      {{"12357"},{"M"}},
      {{"12456"},{"E"}},
      {{"12467"},{"S"}},
      {{"12569"},{"G"}},      
      {{"13457"},{"H"}},
      {{"13459"},{"K"}},
      {{"13567"},{"U"}},
      {{"23456"},{"Z"}},
      {{"1249"},{"F"}},
      {{"1347"},{"Y"}},
      {{"1379"},{"V"}},
      {{"1458"},{"P"}},
      {{"1580"},{"D"}},      
      {{"3567"},{"J"}},
      {{"3579"},{"N"}},
      {{"156"},{"L"}},
      {{"278"},{"T"}},
      {{"456"},{"C"}},
      {{"37"},{"I"}}, 
      {{"90"},{"X"}},
      {{"0"},{" "}}
    };

    public LedDecoder(String input) {
      line = input;
      decode();
    }

    private void decode(){
      for (int i=0;i<Leds.length;i++){
        /* substitute all codes from longest to shortest */
        line = line.replaceAll(Leds[i][0][0],Leds[i][1][0]);
      }
    }

    public void print(){
      System.out.println(line);
    }    
}

public class Prob11 {
  public static void main(String[] args) {   
    Scanner scan = new Scanner(System.in);
           
    LedDecoder decoder = new LedDecoder(scan.nextLine());
    decoder.print();             
  }  
}



