import java.util.*;
import java.io.*;
public class aquarium {
   static boolean[][] grid;
   public static void main(String[] args) throws IOException {
      Scanner in = new Scanner(new File("aquarium.in"));
      int cases = in.nextInt();
      for(int caseOn = 1; caseOn <= cases; caseOn++) {
         System.out.printf("Aquarium #%d:\n",caseOn);
         int r = in.nextInt();
         int c = in.nextInt();
         int n = in.nextInt();
         int t = in.nextInt();
         grid = new boolean[r][c];
         for(int i = 0; i < r; i++) {
            String next = in.next();
            for(int j = 0; j < c; j++) {
               grid[i][j] = next.charAt(j)=='X';
            }
         }
         ArrayList<fish> fishes = new ArrayList<fish>();
         for(int i = 0; i < n; i++) {
            fish f = new fish(in.next(),in.nextInt(),in.nextInt()-1,in.nextInt()-1,in.next(),i);
            fishes.add(f);
         }
         LinkedList<fish> dead = new LinkedList<fish>();
         for(int time = 0; time < t; time++) {
            for(fish f : fishes) {
               f.move();
            }
            Collections.sort(fishes);
            for(int i = 0; i < fishes.size()-1; i++) {
               while(i < fishes.size()-1 && fishes.get(i+1).x == fishes.get(i).x && fishes.get(i+1).y == fishes.get(i).y) {
                  fishes.get(i).bonus++;
                  fishes.get(i+1).kill();
                  dead.add(fishes.get(i+1));
                  fishes.remove(i+1);
               }
            }
         }
         LinkedList<fish> allFish = new LinkedList<fish>();
         allFish.addAll(dead);
         allFish.addAll(fishes);
         fish.sortByIndex=true;
         Collections.sort(allFish);
         for(fish f : allFish) {
            System.out.printf("%d. ",f.mainIndex+1);
            if(!f.alive)
               System.out.printf("(Deceased) ");
            for(int i = 0; i < f.bonus; i++) {
               System.out.printf("Big ");
            }
            System.out.printf("%s\n",f.name);
         }
         System.out.println();
      }
   }
   
   static boolean canMove(int x, int y, int dx, int dy) {
      if(x+dx <0 || x+dx >= grid[0].length || y+dy < 0 || y+dy>=grid.length)
         return false;
      return !grid[y+dy][x+dx];
   }
   
   static class fish implements Comparable<fish> {
      static boolean sortByIndex;
      static final int[] dx = new int[]{0,1,-1,0};
      static final int[] dy = new int[]{1,0,0,-1};
      static final int down = 0, right = 1, left = 2, up = 3, stationary = 4;
      int size, bonus, index, lastMovement, x, y, mainIndex;
      String name;
      int[] movement;
      boolean alive;
      public fish(String n, int s, int y, int x, String m, int index) {
         sortByIndex = false;
         movement = new int[m.length()];
         for(int i = 0; i < m.length(); i++) {
            switch(m.charAt(i)) {
               case 'D':
                  movement[i] = down;
                  break;
               case 'U':
                  movement[i] = up;
                  break;
               case 'L':
                  movement[i] = left;
                  break;
               case 'R':
                  movement[i] = right;
                  break;
            }
         }
         this.y = y;
         this.x = x;
         this.name = n;
         this.size = s;
         this.bonus = 0;
         this.index = 0;
         this.mainIndex = index;
         this.lastMovement = 0;
         alive = true;
      }
      
      public void kill() {
         alive = false;
      }
      
      public void move() {
         if(!canMove(x,y,dx[movement[index]],dy[movement[index]])) {
            lastMovement = stationary;
            index++;
            if(index==movement.length)
               index=0;
            return;
         }
         x+=dx[movement[index]];
         y+=dy[movement[index]];
         lastMovement = movement[index];
         index++;
         if(index==movement.length)
            index=0;
      }
      
      public int compareTo(fish o) {
         if(sortByIndex)
            return this.mainIndex - o.mainIndex;
         if(x==o.x) {
            if(this.y==o.y){
               if(size+bonus == o.size + o.bonus) {
                  return this.lastMovement - o.lastMovement;
               }
               return o.size+o.bonus-this.size-this.bonus;
            }
            return this.y - o.y;
         }
         return this.x-o.x;
      }
   }
}
