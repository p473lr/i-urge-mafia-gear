#include<stdio.h>
#include<math.h>
int main(){
    //
    FILE* ifp;
    
    //open the input file
    ifp = fopen("circle.in","r");
    
    int cases, i;
    //read in the number of cases
    fscanf(ifp, "%d", &cases);
    
    for(i = 1;i<=cases;i++){
         //variables to store which character is at each position
         char r1c1;
         char r1c2;
         char r1c3;
         char r2c1;
         char r2c2;
         char r2c3;
         char r3c1;
         char r3c2;
         char r3c3;
         //reads in the chacter at each position
         fscanf(ifp, "%c", &r1c1);
         fscanf(ifp, "%c");
         fscanf(ifp, "%c", &r1c2);
         fscanf(ifp, "%c");
         fscanf(ifp, "%c", &r1c3);
         fscanf(ifp, "%c");
         fscanf(ifp, "%c", &r2c1);
         fscanf(ifp, "%c");
         fscanf(ifp, "%c", &r2c2);
         fscanf(ifp, "%c");
         fscanf(ifp, "%c", &r2c3);
         fscanf(ifp, "%c");
         fscanf(ifp, "%c", &r3c1);
         fscanf(ifp, "%c");
         fscanf(ifp, "%c", &r3c2);
         fscanf(ifp, "%c");
         fscanf(ifp, "%c", &r3c3);
         fscanf(ifp, "%c");
         int j;
         printf("Game #%d:\n",i);
         //checks all possible winning positions
         if(r1c1 == r1c2 && r1c2 == r1c3 && r1c1 != '#'){
                 
                 if(r1c1 == 'X'){
                      printf("Eureka! X wins!\n");   
                 }
                 else{
                      printf("Gadzooks! O wins!\n");
                 }        
                 
         }
         else if(r2c1 == r2c2 && r2c2 == r2c3 && r2c1 != '#'){
                 
                 if(r2c1 == 'X'){
                      printf("Eureka! X wins!\n");   
                 }
                 else{
                      printf("Gadzooks! O wins!\n");
                 }        
                 
         }
         else if(r3c1 == r3c2 && r3c2 == r3c3 && r3c1 != '#'){
                 
                 if(r3c1 == 'X'){
                      printf("Eureka! X wins!\n");   
                 }
                 else{
                      printf("Gadzooks! O wins!\n");
                 }        
                 
         }
         else if(r1c1 == r2c1 && r2c1 == r3c1 && r1c1 != '#'){
                 
                 if(r1c1 == 'X'){
                      printf("Eureka! X wins!\n");   
                 }
                 else{
                      printf("Gadzooks! O wins!\n");
                 }        
                 
         }
         else if(r1c2 == r2c2 && r2c2 == r3c2 && r1c2 != '#'){
                 
                 if(r1c2 == 'X'){
                      printf("Eureka! X wins!\n");   
                 }
                 else{
                      printf("Gadzooks! O wins!\n");
                 }        
                 
         }
         else if(r1c3 == r2c3 && r2c3 == r3c3 && r1c3 != '#'){
                 
                 if(r1c3 == 'X'){
                      printf("Eureka! X wins!\n");   
                 }
                 else{
                      printf("Gadzooks! O wins!\n");
                 }        
                 
         }
         else if(r1c1 == r2c2 && r2c2 == r3c3 && r1c1 != '#'){
                 
                 if(r1c1 == 'X'){
                      printf("Eureka! X wins!\n");   
                 }
                 else{
                      printf("Gadzooks! O wins!\n");
                 }        
                 
         }
         else if(r1c3 == r2c2 && r2c2 == r3c1 && r1c3 != '#'){
                 
                 if(r1c3 == 'X'){
                      printf("Eureka! X wins!\n");   
                 }
                 else{
                      printf("Gadzooks! O wins!\n");
                 }        
                 
         }
         else{
            //otherwise its a tie  
            printf("Lolcat's game!\n");
              
         }        
         printf("\n");
    }
    
    return 0;
}
    
    
