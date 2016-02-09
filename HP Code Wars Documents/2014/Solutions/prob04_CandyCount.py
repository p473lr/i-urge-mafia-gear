#!/usr/bin/env python

#CodeWars 2014
#
#Candy Count
#
#Original problem text:
#
#Input:
#Input for each day of the contest begins with an integer C (0 < C < 1000) 
# describing the candy count for that day.
#The next line contains the number of students S (0 < S < 30) guessing that day.
#
#The following S lines contain the guess and name of the student. 
# The guess will be an integer G (0 < G < 1000). 
# A single space will separate the guess from the name. 
# The student name will consist only of uppercase and lowercase letters A-Z. 
# Names will not contain any spaces.
#
#Example 1:
#480
#4
#90 John
#400 Melinda
#560 Chuck
#173 Miika
#
#Example 2:
#362
#5
#123 Miika
#456 John
#321 Chuck
#400 Melinda
#314 David
#
#Output:
#Output the winner of the day. If multiple students are the same number from the actual candy count, output their names in the order they appear in the input, separated by spaces.
#
#Example 1: Melinda Chuck
#Example 2: Melinda
#

import sys
import string
from collections import defaultdict

if __name__ == '__main__':
    print ("Enter Candy Count, Number of Students, then student guesses and names. ")
    candyCount = int(sys.stdin.readline())
    studentCount = int(sys.stdin.readline())
    userNames = defaultdict(str)
    userGuesses = defaultdict(int)
    closestGuess = 1000  # Start big

    # Store names in order, so they can be printed in order
    for studentLoop in range(0, studentCount):
        line = sys.stdin.readline().rstrip('\n')
        guess, studentName = line.split(None,1)
        userNames[studentLoop]=studentName
        studentGuess = abs (int(guess)-candyCount) # only need to store difference
        userGuesses[studentLoop]=studentGuess
        if studentGuess < closestGuess:
            closestGuess = studentGuess
#            print("New Closest Guess: ", closestGuess)

    for studentLoop in range(0, studentCount):
        if (userGuesses[studentLoop] == closestGuess):
            print(userNames[studentLoop], " ",sep="",end="")
