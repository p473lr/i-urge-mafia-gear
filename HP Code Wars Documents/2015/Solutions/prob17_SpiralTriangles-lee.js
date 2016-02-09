// Spiral Triangles
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

(function SpiralTriangles() {

   var drawEdgeFunctionList = {
      "/":  drawEdgeUp,
      "\\": drawEdgeDown,
      "-":  drawEdgeLeft
   };

   function run() {
      var descriptor = readDescriptor();
      while( descriptor.firstEdge !== "#" ) {
         solve( descriptor );
         descriptor = readDescriptor();
      }
   };

   function solve( descriptor ) {
      var size = 36;
      var grid = createGrid(size);
      var location = { row: size/2, col: size/2 };
      var edge = descriptor.firstEdge;
      var boundaries = {
         L: location.col, // left
         R: location.col, // right
         T: location.row, // top
         B: location.row  // bottom
      };
      var drawEdgeFunction = drawEdgeFunctionList[ descriptor.firstEdge ];
      var label = "0";
      var numberOfSteps = 1;
      var stepCount = 0;
      while( label !== descriptor.lastLabel ) {
         grid[location.row][location.col] = label;
         label = nextLabel( label );
         drawEdgeFunction = drawEdgeFunctionList[ edge ];
         drawEdgeFunction( grid, location, edge );
         // we could also write the function call in a single line:
         //   drawEdgeFunctionList[ edge ]( grid, location, edge );
         updateBoundaries( location, boundaries );
         if( ++stepCount === numberOfSteps ) {
            ++numberOfSteps;
            stepCount = 0;
            edge = nextEdge( edge );
         }
      }
      grid[location.row][location.col] = label;
      printGrid( grid, boundaries );
   };

   function readDescriptor() {
      var tokens = stdin.readLine().split(/\s+/);
      return {
         firstEdge: tokens[0],
         lastLabel: tokens[1]
      };
   };

   function createGrid( size ) {
      var grid = [ ];
      for( var i=0; i<size; ++i ) {
         grid[i] = [ ];
         for( var j=0; j<size; ++j ) {
            grid[i][j] = ".";
         }
      }
      return grid;
   };

   function nextLabel( label ) {
      var labelList = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
      return labelList.substr( labelList.search( label )+1, 1 );
   };

   function nextEdge( edge ) {
      var nextEdgeMap = { "/": "\\", "\\": "-", "-": "/" };
      return nextEdgeMap[edge];
   };

   function drawEdgeLeft( grid, location ) {
      for( var i=0; i<3; ++i ) {
         grid[location.row][--location.col] = "-";
      }
      --location.col;
   };

   function drawEdgeUp( grid, location ) {
      grid[--location.row][++location.col] = "/";
      ++location.col;
      --location.row;
   };

   function drawEdgeDown( grid, location ) {
      grid[++location.row][++location.col] = "\\";
      ++location.col;
      ++location.row;
   };

   function updateBoundaries( location, boundaries ) {
      if( boundaries.L > location.col ) {
         boundaries.L = location.col;
      }
      if( boundaries.R < location.col ) {
         boundaries.R = location.col;
      }
      if( boundaries.T > location.row ) {
         boundaries.T = location.row;
      }
      if( boundaries.B < location.row ) {
         boundaries.B = location.row;
      }
   };

   function printGrid( grid, boundaries ) {
      for( var row=boundaries.T; row<=boundaries.B; ++row ) {
         var line = "";
         for( var col=boundaries.L; col<=boundaries.R; ++col ) {
            var c = grid[row][col];
            line += c;
         }
         print( line );
      }
   };

   run();

}) ();
