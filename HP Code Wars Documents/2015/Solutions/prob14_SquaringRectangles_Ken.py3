#!/usr/bin/env python

#CodeWars 2015
#
# Squaring Rectangles

# Input
# The input is one line with two integers,the Width and Length of the room.
# Example 1:
# 11 10
# Example 2:
# 19 18
# Output
# On the first line, print N squares can cover WxL (replacing N, W, and L with their values). 
#  On the next line print the sizes of the squares in increasing order, separated by spaces. 
# Finally, print a map of the room, using a different lowercase letter (a, b, ) 
# for each square of carpet used. (No problem will ever need more than 26 squares.)
# Example 1:
# 6 squares can cover 11x10.
# 2 2 4 5 5 6
# aaaaaabbbbb
# aaaaaabbbbb
# aaaaaabbbbb
# aaaaaabbbbb
# aaaaaabbbbb
# aaaaaaccccc
# ddddeeccccc
# ddddeeccccc
# ddddffccccc
# ddddffccccc
# Example 2:
# 7 squares can cover 19x18.
# 3 5 5 7 7 8 11
# aaaaaaaaaaabbbbbbbb
# aaaaaaaaaaabbbbbbbb
# aaaaaaaaaaabbbbbbbb
# aaaaaaaaaaabbbbbbbb
# aaaaaaaaaaabbbbbbbb
# aaaaaaaaaaabbbbbbbb
# aaaaaaaaaaabbbbbbbb
# aaaaaaaaaaabbbbbbbb
# aaaaaaaaaaacccddddd
# aaaaaaaaaaacccddddd
# aaaaaaaaaaacccddddd
# eeeeeeefffffffddddd
# eeeeeeefffffffddddd
# eeeeeeefffffffggggg
# eeeeeeefffffffggggg
# eeeeeeefffffffggggg
# eeeeeeefffffffggggg
# eeeeeeefffffffggggg
# 
# A little bit of logic helps to shrink the search:
#   - There is at least one "largest" square in the grid.
#   - If it is NOT in a corner, then it must be surrounded on at least 3 sides by smaller squares
#        in such a way that it cannot be swapped with them to move to a corner.
#        Such a configuration requires about 9 smaller squares.  Just a little bit of trial
#        and error to try to find such a configuration will also show that an equal or better
#        solution can be found with the large square in the corner.
#   - So start with the plan that the largest square will be in the first corner.
# 
import sys

line = sys.stdin.readline().rstrip('\n')
words = line.split()
cols = int(words[0])
rows = int(words[1])
# Initial Maximums and global grids
maxSquares = 30
grid = [['.' for x in range(cols)] for x in range(rows)]
sizes = [100 for x in range(maxSquares)]
bestGrid = [['.' for x in range(cols)] for x in range(rows)]
bestSizes = [100 for x in range(maxSquares)]
squareIDs = 'abcdefghijklmnopqrstuvwxyz'

def fillSquare (R, C, size, fillChar):
    for i in range(R,R+size):
        for j in range(C,C+size):
            grid[i][j] = fillChar

# 
def maxSquareSize (R, C, squareCount):
    tmpMax = min(rows, cols)
    if (squareCount >0):
        tmpMax = sizes[0] # First square is always largest
    # Check right for max empty grid rows
    i = C
    while ((i < cols) and (grid[R][i] == '.')):
        i=i+1
    tmpMax = min(tmpMax, i-C) # distance to next used square (or right edge)
    tmpMax = min(tmpMax, rows-R) # distance to low edge
    return tmpMax
#
def findNextEmptySquare (R,C):
    i = R
    j = C
    while ((i<rows) and (grid[i][j] != '.')):
        j = j+1
        if (j==cols):
            j = 0
            i = i+1
    return i,j # i==rows will be a "not found"  otherwise it's found.
# 
def copyBestSolution (squareCount):
    global maxSquares 
    maxSquares = squareCount
    for i in range(0,rows):
        for j in range(0,cols):
            bestGrid[i][j] = grid[i][j]
    for i in range(0,maxSquares):
        bestSizes[i] = sizes[i]

def placeSquare (R, C, squareCount):
    global sizes
    if (squareCount+1 >= maxSquares): # placing a new square won't improve our best solution
        return
    m = maxSquareSize(R,C,squareCount) # start at max and move down
    for size in range(m,0, -1):
        fillSquare(R,C,size,squareIDs[squareCount]) # mark all grid locations for this square    
        sizes[squareCount] = size
        newR, newC = findNextEmptySquare(R,C)
        if (newR == rows): #grid is full
            if (squareCount+1<maxSquares):
                copyBestSolution(squareCount+1)
        else: #grid is not full, place a new square
            placeSquare(newR, newC, squareCount+1) # Recursive call to add a new square
        fillSquare(R,C,size,'.') # Un-mark all grid locations for placed square     

# main command starts the recursion
placeSquare(0,0,0) # Start at top corner, with no squares placed

# Print the bestSizes and bestGrid found
print(maxSquares, " squares can cover ", cols,"x",rows,".", sep='')
sortedSizes =  sorted(bestSizes[:maxSquares])
for i in range(0, maxSquares):
    print (sortedSizes[i]," ",sep='', end='')
print ("")
for i in range(0,rows):
    for j in range(0,cols):
        print(bestGrid[i][j],sep='',end='')
    print("")

