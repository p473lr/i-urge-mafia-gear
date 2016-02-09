#!/usr/bin/env python

#CodeWars 2015
#
# Battleship

import sys

totalShips=0
shipLabels = "abcdefghijklmnopqrstuvwxyz"
shipCount = {'B':0, 'C':0, 'D':0, 'F':0, 'G':0}

# Define each orientation of all ships
battleship1 = {
1:'XXXX',
2:'----',
3:'----',
4:'----',
5:'B'}
battleship2 = {
1:'X---',
2:'X---',
3:'X---',
4:'X---',
5:'B'}
carrier1 = {
1:'--X-',
2:'-XX-',
3:'XX--',
4:'X---',
5:'C'}
carrier2 = {
1:'XX--',
2:'-XX-',
3:'--XX',
4:'----',
5:'C'}
destroyer1 = {
1:'-X--',
2:'-X--',
3:'XXX-',
4:'XXX-',
5:'D'}
destroyer2 = {
1:'XX--',
2:'XXXX',
3:'XX--',
4:'----',
5:'D'}
destroyer3 = {
1:'XXX-',
2:'XXX-',
3:'-X--',
4:'-X--',
5:'D'}
destroyer4 = {
1:'--XX',
2:'XXXX',
3:'--XX',
4:'----',
5:'D'}
frigate1 = {
1:'-X--',
2:'XXX-',
3:'XXX-',
4:'-X--',
5:'F'}
frigate2 = {
1:'-XX-',
2:'XXXX',
3:'-XX-',
4:'----',
5:'F'}
gunner1 = {
1:'XXXX',
2:'XXXX',
3:'----',
4:'----',
5:'G'}
gunner2 = {
1:'XX--',
2:'XX--',
3:'XX--',
4:'XX--',
5:'G'}

ships={}
ships[0]=battleship1
ships[1]=battleship2
ships[2]=carrier1
ships[3]=carrier2
ships[4]=destroyer1
ships[5]=destroyer2
ships[6]=destroyer3
ships[7]=destroyer4
ships[8]=frigate1
ships[9]=frigate2
ships[10]=gunner1
ships[11]=gunner2

def labelShip (R, C):
    global totalShips
    shipSquares=[]
    shipSquares.append((R,C))
   
    #First scan through all neighboring Xs and build a map
    newShip = [['-' for col in range(8)] for row in range(6)]
    shipIndex=0
    newR = 1
    newC = 4
    while ( shipIndex < len(shipSquares) ):
        gridR, gridC = shipSquares[shipIndex]
        shipIndex=shipIndex+1
        shipR = newR+gridR-R # Set offset in the newShip array
        shipC = newC+gridC-C
        newShip[shipR][shipC] = 'X' # Build the ship in the newShip array
        grid[gridR][gridC] = shipLabels[totalShips] # Change all grid Xs so not found later
        # Search right, left, down for more Xs and add to shipSquares
        if (grid[gridR][gridC+1] == 'X'):
            shipSquares.append((gridR, gridC+1))
        if (grid[gridR][gridC-1] == 'X'):
            shipSquares.append((gridR, gridC-1))
        if (grid[gridR+1][gridC] == 'X'):
            shipSquares.append((gridR+1, gridC))
    totalShips = totalShips+1
    # Ship is completely read in.  Now match it.
    found=0
    i=0
    while ((i < len(ships)) and (found==0)): # For each known ship
        col=0 #start at first column
        row=1 #top row
        while (ships[i][row][col] != 'X'): # Find the first X in top row of known ship
            col = col+1
        colOffset = newC - col # Amount to add to match shipSquares to known ship
        rowOffset = newR - row
        match=1 # Start by assuming we found it
        for row in range (1,5):
            for col in range(0,4):
                if (ships[i][row][col] != newShip[row+rowOffset][col+colOffset]):
                    match=0
        if (match==1):
            found=1
            shipCount[ships[i][5]] = 1+shipCount[ships[i][5]] # Increment the count for the found ship
            #print ("Found:", ships[i][5])
        i=i+1 # Try next known ship

# Main Program Starts here
N = int(sys.stdin.readline()) # size of grid
grid = [['-' for x in range(N+2)] for x in range(N+2)] # empty grid with buffer on each side
for i in range(N):
    line = sys.stdin.readline()
    for j in range(N):
        grid[i+1][j+1] = line[j]

# Scan and label all ships, counting as we go
for i in range(1,N+1):
    for j in range(1,N+1):
        if (grid[i][j] == 'X'):
            labelShip (i,j)
#print the grid - debug
print("")
for i in range(1,N+1):
    for j in range(1,N+1):
        print (grid[i][j],sep='',end='')
    print("")

# We counted every ship.  Print the counts
print("")
print(shipCount['B'], "Battleship")
print(shipCount['C'], "Carrier")
print(shipCount['D'], "Destroyer")
print(shipCount['F'], "Frigate")
print(shipCount['G'], "Gunner")

# Now scan for diagonal touches
touchingShips = {} # Empty Dictionary

for i in range(1,N): #Loop over top-left corners of 2x2 sections
    for j in range(1,N):
        g1 = grid[i][j]
        g2 = grid[i][j+1]
        g3 = grid[i+1][j]
        g4 = grid[i+1][j+1]
        if ( (g1 != '-') and (g4 != '-') and (g1 != g4)): #UpperLeft to BottomRight touching
            touchingShips[g1] = 1
            touchingShips[g4] = 1
        if ( (g2 != '-') and (g3 != '-') and (g2 != g3)): #UpperRight to BottomLeft touching
            touchingShips[g2] = 1
            touchingShips[g3] = 1
touchingCount = len(touchingShips)
touchingList = sorted(touchingShips.keys())
print (touchingCount, "ships are touching others: ", end='')
for i in range(touchingCount):
    print(touchingList[i]," ",sep='',end='')
print("")

