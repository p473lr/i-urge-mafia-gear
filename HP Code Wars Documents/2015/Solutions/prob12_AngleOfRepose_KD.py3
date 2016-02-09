#!/usr/bin/env python

#CodeWars 2015
#
# Angle of Repose

# Write a program to calculate the maximum slope angle of a pile of material.
#
# Input
# The input is a two-dimensional square grid that represents the height of a pile 
# of material measured at regular one-meter intervals. The value at each point in 
# the grid is the height of the material, in meters, at that location. 
# The first line of the input is an integer that indicates the number of row and columns in the grid. 
# You should not assume a certain number of spaces between the numbers or assume that the decimals will align.
# 9 
# 4.5 4.6 4.4 4.5 4.5 4.4 4.5 4.6 4.5 
# 4.5 4.6 4.6 4.7 4.8 4.6 4.5 4.5 4.4 
# 4.4 4.5 4.7 4.9 5.0 4.8 4.7 4.6 4.5 
# 4.5 4.7 4.8 5.0 5.2 5.1 5.0 4.8 4.6 
# 4.5 4.6 4.8 5.0 5.3 5.1 4.9 4.8 4.6 
# 4.4 4.6 4.9 5.1 5.2 5.1 4.9 4.7 4.5 
# 4.5 4.5 4.6 4.8 4.9 4.8 4.7 4.5 4.4 
# 4.6 4.6 4.5 4.6 4.7 4.7 4.6 4.5 4.4 
# 4.5 4.5 4.4 4.5 4.5 4.6 4.5 4.4 4.5
# 
# Output
# The program must print the steepest slope angle (in degrees) between any adjacent points 
# (including the diagonals) on the grid. The answer must match the expected value to within 
# plus or minus 0.1 degrees.
# 38.X (not really calculated)


import sys
from math import atan, degrees, sqrt

# global
maxDegrees = 0
maxA=0
maxB=0

gridSize = int(sys.stdin.readline().rstrip('\n'))
grid = [[0.1 for x in range(gridSize)] for x in range(gridSize)] 

for row in range(0,gridSize):
    line = sys.stdin.readline().rstrip('\n')
    words = line.split()
    #print (words)
    for col in range(0,gridSize):
        grid[row][col] = float(words[col])
        if (col>0): # check degrees horizontally
            tmpDegrees = degrees(atan(abs(grid[row][col]-grid[row][col-1])))
            if (tmpDegrees>maxDegrees):
                maxDegrees = tmpDegrees
                maxA=grid[row][col]
                maxB=grid[row][col-1]
        if (row>0): # check degrees vertically
            tmpDegrees = degrees(atan(abs(grid[row][col]-grid[row-1][col])))
            if (tmpDegrees>maxDegrees):
                maxDegrees = tmpDegrees
                maxA=grid[row][col]
                maxB=grid[row-1][col]
            if (col<gridSize-1): #check degrees up-and-right
                tmpDegrees = degrees(atan((abs(grid[row][col]-grid[row-1][col+1])/sqrt(2))))
                if (tmpDegrees>maxDegrees):
                    maxDegrees = tmpDegrees
                    maxA=grid[row][col]
                    maxB=grid[row-1][col+1]
            if (col>0): #check degrees up-and-left
               tmpDegrees = degrees(atan((abs(grid[row][col]-grid[row-1][col-1])/sqrt(2))))
               if (tmpDegrees>maxDegrees):
                   maxDegrees = tmpDegrees
                   maxA=grid[row][col]
                   maxB=grid[row-1][col-1]

#print(grid)
# Grid is filled, and analyzed.  Print result.
print("") #Empty line
print (maxDegrees, "between", maxA, "and", maxB)
