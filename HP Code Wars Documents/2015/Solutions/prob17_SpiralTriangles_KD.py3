#!/usr/bin/env python

#CodeWars 2015
#
# Spiral Triangles

# Please note that in order to draw the spiral
#  on a text console, one unit of horizontal motion uses three dash characters.
# Input
# Each line of input describes a single spiral. 
# The first character indicates the direction of the first vertex away from the center. 
# The second character indicates the number of segments, which is also the number 
# of the final vertex that should be drawn. 
# The points should be labelled sequentially from 0 through 9, then from A to Z. 
# The last line of input contains two hash tags.
# \ 2
# - 5
# / D
# # #
# Output
# The program must print the spirals described by the input. 
# Remember the spiral must wind clockwise out from the center. 
# Pay attention to the spacing between the vertices and edges and use periods to fill in empty spaces.
# ..0..
# ...\.
# 2---1
# ....3....
# .../.\...
# ..2...4..
# ./.....\.
# 1---0...5
# ........A......
# ......./.\.....
# ......9...B....
# ...../.....\...
# ....8...1...C..
# .../.../.\...\.
# ..7...0...2...D
# ./.........\...
# 6---5---4---3..


import sys

# global
chars='0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ'

for line in sys.stdin:
    line = line.rstrip('\n')
    dirChar, lastChar = line.split()
    if (dirChar == '#'):
        exit()      
    currChar=0
    row=25
    col=25
    maxRow = row
    minRow = row
    maxCol = col
    minCol = col
    grid = [['.' for x in range(2*row)] for x in range(2*col)] # overkill array to solve problem.
    grid[row][col]='0'
    while (chars[currChar] != lastChar):
        currChar = currChar+1
        # Move and place
        if (dirChar == '/'): #Up and right
            grid[row-1][col+1]=dirChar
            row=row-2
            col=col+2
            grid[row][col]=chars[currChar]
            if (row < minRow):
                minRow = row
            if (col > maxCol):
                maxCol = col
            if (grid[row+2][col+2] == '.'): # OK to turn right
                dirChar = '\\'
        elif (dirChar == '\\'): # Down and right
            grid[row+1][col+1]=dirChar
            row=row+2
            col=col+2
            grid[row][col]=chars[currChar]
            if (row > maxRow):
                maxRow = row
            if (col > maxCol):
                maxCol = col
            if (grid[row][col-4] == '.'):  #OK to turn right
                dirChar = '-'

        else: # Left
            grid[row][col-1] = '-'
            grid[row][col-2] = '-'
            grid[row][col-3] = '-'
            col = col-4
            grid[row][col] = chars[currChar]
            if (col < minCol):
                minCol = col
            if (grid[row-2][col+2] == '.'): # OK to turn right
                dirChar = '/'
    # Done moving.  Print result
    print("") #new line
    for row in range(minRow, maxRow+1):
        for col in range(minCol, maxCol+1):
            print(grid[row][col], sep='', end='')
        print("") #new line

