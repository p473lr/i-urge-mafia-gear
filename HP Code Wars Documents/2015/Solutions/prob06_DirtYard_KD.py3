#!/usr/bin/env python

#CodeWars 2015
#
# Dirt Yard

# Input
# The input consists of three lines, each with a single integer. 
# These are the length, width, and depth of the hole, measured in feet.
# 4
# 6
# 3
# Output
# The program must print the minimum number of (cubic) yards of soil 
# required to fill the hole. Landscaping companies sell soil in whole 
# yards, not in fractional parts, so the answer must be the smallest 
# integer number of cubic yards that will fill the hole.
# 3

import sys

A = int(sys.stdin.readline())
B = int(sys.stdin.readline())
C = int(sys.stdin.readline())
volume = A*B*C
cubicYards = int(volume/27)
if ((volume % 27) != 0):
    cubicYards = cubicYards+1
print(cubicYards)
