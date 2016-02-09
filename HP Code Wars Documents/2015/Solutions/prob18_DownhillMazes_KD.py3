#!/usr/bin/env python

#CodeWars 2015
#
# Downhill Mazes

# Write a program to find a path through a downhill maze.
# 
# Input
# The first line of input indicates the number of rows and columns in the maze. 
# The maximum for either number is nine. 
# Every line after that is a single row in the maze.
# 5 9
# 3 3 3 3 2 1 S 8 9
# 3 1 1 3 3 0 6 8 7
# 1 2 2 4 3 2 5 9 7
# 1 2 1 5 4 3 4 4 6
# 1 1 X 6 4 4 5 5 5
# Output
# The program must print a grid of the same size as the input grid, 
# but the output grid should print periods along the boulder's path. 
# Every unvisited space should be marked with a hash "#" character. 
# The start and target cells should keep their marks. 
# There will only be one path through the maze.
# . . . . # # S . #
# . # # . . # # . .
# . # # # . # # # .
# . # # # . # # # .
# . . X # . . . . .


import sys

line = sys.stdin.readline().rstrip('\n')
words = line.split()
rows = int(words[0])
cols = int(words[1])
grid = [[20 for x in range(cols+2)] for x in range(rows+2)]
usedGrid = [['#' for x in range(cols+2)] for x in range(rows+2)]
startRow=0
startCol=0
endRow=0
endCol=0

def printFinal():
    usedGrid[startRow][startCol] = 'S'
    usedGrid[endRow][endCol]='X'
    for i in range(1,rows+1):
        for j in range(1,cols+1):
            print(usedGrid[i][j], sep='', end='')
        print ("") #Move to next line
    exit()

def makeNextMove (R, C): # Move Up, Right, Down, Left from current square
    #First see if we reached the end and print
    if ((R==endRow) and (C == endCol)):
        printFinal()
    usedGrid[R][C] = '.' # Mark this square used
    if ((grid[R][C] >= grid[R-1][C]) and (usedGrid[R-1][C] =='#')): # UP
        makeNextMove (R-1, C)
    if ((grid[R][C] >= grid[R][C+1]) and (usedGrid[R][C+1] =='#')): # RIGHT
        makeNextMove (R, C+1)
    if ((grid[R][C] >= grid[R+1][C]) and (usedGrid[R+1][C] =='#')): # DOWN
        makeNextMove (R+1, C)
    if ((grid[R][C] >= grid[R][C-1]) and (usedGrid[R][C-1] =='#')): # LEFT
        makeNextMove (R, C-1)
    usedGrid[R][C] = '#' # Mark this square unused

for i in range(1,rows+1):
    line = sys.stdin.readline().rstrip('\n')
    words = line.split()
    for j in range(0,cols):
        if (words[j] == 'S'):   # Start location. Mark with 10.
            grid[i][j+1]=10
            startRow=i
            startCol=j+1
        elif (words[j] == 'X'): # End location. Mark with 0.
            grid[i][j+1]=-1
            endRow=i
            endCol=j+1
        else:
            grid[i][j+1] = int(words[j])
# Grid loaded
#print("Grid:")
#for i in range(1,rows+1):
#    for j in range(1,cols+1):
#        print(grid[i][j]," ", sep='',  end='')
#    print ("") #Move to next line
#print ("Grid printed")
print("") # Blank Line
makeNextMove(startRow, startCol)
