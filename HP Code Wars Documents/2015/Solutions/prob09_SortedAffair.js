// Sorted Affair
// Code Wars program written in JavaScript for the RingoJS environment
//
// The MIT License (MIT)
//
// Copyright (c) 2015 Lee Jenkins
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.

var stdin = require("system").stdin;
var stdout = require("system").stdout;

"use strict";

(function SortedAffair() {

   var teamScores = [ ];

   function addScore( scoreLine ) {
      for( var i=0; i<teamScores.length; ++i ) {
         if( teamScores[i].teamNumber === scoreLine.teamNumber ) {
            teamScores[i].totalScore += scoreLine.pointValue;
            return;
         }
      }
      teamScores.push({
         teamNumber: scoreLine.teamNumber,
         totalScore: scoreLine.pointValue
      });
   };

   function execute() {
      var scoreLine = readTeamScoreLine();
      while( scoreLine.pointValue ) {
         addScore( scoreLine );
         scoreLine = readTeamScoreLine();
      }
      teamScores.sort( function compareForSortDescending( a, b ) {
         return ( b.totalScore - a.totalScore );
      });
      for( var i=0; i<5; ++i ) {
         stdout.writeLine( (i+1) + " " + teamScores[i].teamNumber + " " + teamScores[i].totalScore );
      }
   };

   function readTeamScoreLine() {
      var tokens = stdin.readLine().split(/\s+/);
      var scoreData = {
         teamNumber: tokens[0],
         pointValue: parseInt(tokens[1])
      };
      return scoreData;
   };

   execute();

}) ();
