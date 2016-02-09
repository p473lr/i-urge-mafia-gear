#!/usr/bin/env python

#CodeWars 2015
#
# Diamond in the Rough


#  Write a program that can generate diamond patterns of different sizes.
#  Input
#  Each line of input contains three positive integers: 
# the size of the diamonds and the number of rows and columns of diamonds to be drawn. 
# Due to the way the diamonds are drawn, the diamond size will always be an even integer. 
# All values will be less than one hundred. The input ends with three zeros.
#  6 2 4
#  2 3 7
#  0 0 0
#  Output
#  The program must print diamonds in a rectangular grid using the slash and backslash 
# characters for the bodies of the diamonds. A diamond of size two has exactly two slashes 
# and two blackslashes arranged in a diamond pattern. For diamonds larger than size two, 
# the program should fill the spaces between the diamonds with hash characters to represent 
# lumps of coal. The diamond grid must match the input rows and columns.
#  ##/\####/\####/\####/\##
#  #//\\##//\\##//\\##//\\# 
#  ///\\\///\\\///\\\///\\\
#  \\\///\\\///\\\///\\\///
#  #\\//##\\//##\\//##\\//# 
#  ##\/####\/####\/####\/## 
#  ##/\####/\####/\####/\##
#  #//\\##//\\##//\\##//\\# 
#  ///\\\///\\\///\\\///\\\
#  \\\///\\\///\\\///\\\///
#  #\\//##\\//##\\//##\\//# 
#  ##\/####\/####\/####\/## 
#
#  /\/\/\/\/\/\/\ 
#  \/\/\/\/\/\/\/ 
#  /\/\/\/\/\/\/\ 
#  \/\/\/\/\/\/\/ 
#  /\/\/\/\/\/\/\ 
#  \/\/\/\/\/\/\/ 



import sys

def printOneDiamondRow (rowNum, size):
    halfSize = int(size/2)
    if (rowNum <= halfSize): # Top half of diamond
        firstChar = '/'
        lastChar = '\\'
        totalChars = rowNum
    else:
        firstChar = '\\'
        lastChar = '/'
        totalChars = (size+1)-rowNum
    totalHalfCoal = halfSize-totalChars

    for i in range(1,totalHalfCoal+1):
        print('#',end='')
    for i in range(1,totalChars+1):
        print(firstChar,end='')
    for i in range(1,totalChars+1):
        print(lastChar,end='')
    for i in range(1,totalHalfCoal+1):
        print('#',end='')

for line in sys.stdin:
    line = line.rstrip('\n')
    words = line.split()
    size=int(words[0])
    rows=int(words[1])
    cols=int(words[2])
    if (size==0):
        exit()
    for rowCount in range(1,rows+1):
        for diamondRow in range(1,size+1):
            for colCount in range(1,cols+1):
                printOneDiamondRow(diamondRow, size)
            print("");
    print(""); # Print a blank line between sets
