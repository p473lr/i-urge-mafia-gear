#!/usr/bin/env python

#CodeWars 2014
#
#Bee Math

#The population of one particular bee colony can be approximated by the equation
#
#. . . . . . . . . . P(t) = 100 * sqrt(t) + 201/(t+1) + 1
#
#where t is measured in days after the colonization of the beehive.
#
#Write a program to compute the population of a beehive after a specified number of days.
#
#Input
#
#Each line of input is a positive integer value for t, the number of days 
# after the colonization of the hive. The input ends with a zero.
#
#7
#38
#24
#0
#
#Output
#
#For each value of t, the program must print t and the population of the hive 
# for that day, rounded to the nearest integer. Result must match expected value with +/- 1.
#
#7 291
#38 623
#24 499
#

import sys
from math import sqrt

print ("Enter Number of Days. 0 to end.")
for line in sys.stdin:
    days=int(line)
    if (days==0):
        break
    print( days, int(100 * sqrt(days) + (201 / (days+1)) + 1 + 0.5)) #0.5 to round up
