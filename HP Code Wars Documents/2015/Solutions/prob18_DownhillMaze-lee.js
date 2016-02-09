// Downhill Maze
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

(function DownhillMaze() {

   var maze = [ ];
   var path = [ ];
   var start = { row: 0, col: 0 };
   var vectors = [
      { dr:  1, dc:  0 },
      { dr: -1, dc:  0 },
      { dr:  0, dc:  1 },
      { dr:  0, dc: -1 }
   ];

   function run() {
      readMaze();
      solveMaze( start.row, start.col );
      path[ start.row ][ start.col ] = 'S';
      printSolution();
   };

   function readMaze() {
      var dimensions = stdin.readLine().trim().split(/\s+/);
      var rows = parseInt( dimensions[0] );
      var cols = parseInt( dimensions[1] );
      for( var r=0; r<rows; ++r ) {
         maze.push( stdin.readLine().trim().split(/\s+/) );
         maze.push( stdin.readLine().trim().split(/\s+/) );
         var rowPath = [ ];
         for( var c=0; c<cols; ++c ) {
            rowPath.push( '#' );
            if( maze[r][c] === 'S' ) {
               start = { row: r, col: c };
            }
         }
         path.push( rowPath );
      }
   };

   function height(row,col) {
      if( maze[row][col] === 'S' )
         return 9;
      if( maze[row][col] === 'X' )
         return 0;
      return parseInt( maze[row][col] );
   };

   function solveMaze( row, col ) {
      if( maze[row][col] === 'X' ) {
         path[row][col] = 'X';
         return true;
      }
      var thisHeight = height(row,col);
      for( var v=0; v<vectors.length; ++v ) {
            var row1 = row + vectors[v].dr;
            var col1 = col + vectors[v].dc;
            if( row1 < 0 || row1 > maze.length ||
                col1 < 0 || col1 > maze[0].length )
               continue;
            if( thisHeight >= height(row1,col1) && path[row1][col1] === '#' ) {
               path[row1][col1] = '.';
               if( solveMaze( row1, col1 ) )
                  return true;
               path[row1][col1] = '#';
            }
      }
      return false;
   };
   
   function printSolution(){
      for( var r=0; r<path.length; ++r ) {
         for( var c=0; c<path[r].length; ++c ) {
            stdout.write( path[r][c] + ' ' );
         }
         stdout.writeLine('');
      }
   };

   run();

}) ();
