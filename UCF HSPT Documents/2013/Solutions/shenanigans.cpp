
// 27th Annual UCF High School Programming Tournament
//
// Problem Name: Rock Paper Shenanigans (shenanigans)
//
// Author:        Kyle Urquhart
// Judge Data:    Gabe Pita
// C Solution:    Andrew Harn
// Java Solution: Marcos Arribas
// Verifier:      Stephen Royal

#include <fstream>
#include <iostream>
#include <map>

#define NO_ONE -1
#define ANDREW 0
#define GABE 1

using namespace std;

int main() {

    //The input file stream
    ifstream input;
    input.open("shenanigans.in", ifstream::in);

    //Result string message
    string resultMessage[2];
    resultMessage[ANDREW] = "Looks like Andrew won again.";
    resultMessage[GABE] = "Oh snap! Gabe beat Andrew!";


    //Rock Paper Scissor map
    map<char, int> moveMap;
    moveMap['R'] = 0;
    moveMap['P'] = 1;
    moveMap['S'] = 2;


    //First get the number of games
    int numberOfGames;
    input >> numberOfGames;

    //A for loop for every game
    for (int gameNumber = 1; gameNumber <= numberOfGames; gameNumber++) {

        //Get the number of matches for the game
        int numberOfMatches;
        input >> numberOfMatches;

        //Get the moves played by Andrew and Gabe
        char gameMove[numberOfMatches][2];
        for (int matchNumber = 0; matchNumber < numberOfMatches; matchNumber++) {
            input >> gameMove[matchNumber][ANDREW] >> gameMove[matchNumber][GABE];
        }

        //Integer seeing who has the advantage:
        int advantage = NO_ONE;

        //Start playing the game
        for (int matchNumber = 0; matchNumber < numberOfMatches; matchNumber++) {

            //Get the integer representations of the moves
            int andrewMove = moveMap[gameMove[matchNumber][ANDREW]];
            int gabeMove = moveMap[gameMove[matchNumber][GABE]];

            //If it's a tie
            if (andrewMove == gabeMove) {

                //If no one has the advantage, we play the game on as usual
                if (advantage == NO_ONE) {
                    continue;   //And continue to the next match
                }
                //Else, someone has the advantage, and we stop playing
                else {
                    break;
                }
            }

            //Otherwise see who as the advantage
            //If Andrew's move beats Gabe's, Andrew gets the advantage
            else if (andrewMove == (gabeMove + 1) % 3) {
                advantage = ANDREW;
            }

            //Otherwise, Gabe gets the advantage
            else {
                advantage = GABE;
            }
        }

        //Print out the result
        cout << "Game #" << gameNumber << ": " << resultMessage[advantage] << "\n";
    }

    //Return statement
    input.close();
    return 0;

}
