#!/usr/bin/env python

#CodeWars 2015
#
# Sorted Affair

# Input
# Each line of input represents a single correct program using two integer values: 
# a three-digit team number and a point value between one and twenty-one. 
# The end of input is signaled by two zeroes.
# 123 1 
# 354 2 
# 481 1 
# 354 4 
# 213 1 
# 987 3 
# 742 2 
# 354 5
# 0 0
# Output
# The program must print the top five teams, in order. 
# Each line of output must have the team rank, the team number, 
# and the total number of points scored by that team. Lucky for you there will be no ties.
# 1 354 33
# 2 213 27
# 3 987 25
# 4 508 20
# 5 481 9


import sys

teamDict = {0:0} #prepopulate one entry to start

for line in sys.stdin:
    line = line.rstrip('\n')
    team, valueStr = line.split(None, 1)
    value = int(valueStr)
    if ((team!='0') and (value!=0)): #add or update team score
        if (team in teamDict.keys()): #already exists.  Update value
            teamDict[team] = teamDict[team] + value
        else: #team doesn't exist.  Add it.
            teamDict[team]=value
#        print (teamDict)
    else: #0 0 means it's time to print results
        teamSorted=sorted(teamDict, key=lambda k:teamDict[k], reverse=True)
#        print(teamSorted)
        print("") # print a blank line
        for i in range(0,5):
            print (i+1, teamSorted[i], teamDict[teamSorted[i]])
        exit()
