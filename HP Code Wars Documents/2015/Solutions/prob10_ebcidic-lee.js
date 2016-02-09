// ebcdic.js
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

var system = require("system");
var stdout = system.stdout;
var stdin = system.stdin;

"use strict";

var ebcdictionary = {
  "81":"a", "82":"b", "83":"c", "84":"d", "85":"e", "86":"f", "87":"g", "88":"h", "89":"i", "91":"j", "92":"k", "93":"l", "94":"m",
  "95":"n", "96":"o", "97":"p", "98":"q", "99":"r", "A2":"s", "A3":"t", "A4":"u", "A5":"v", "A6":"w", "A7":"x", "A8":"y", "A9":"z",
  "C1":"A", "C2":"B", "C3":"C", "C4":"D", "C5":"E", "C6":"F", "C7":"G", "C8":"H", "C9":"I", "D1":"J", "D2":"K", "D3":"L", "D4":"M",
  "D5":"N", "D6":"O", "D7":"P", "D8":"Q", "D9":"R", "E2":"S", "E3":"T", "E4":"U", "E5":"V", "E6":"W", "E7":"X", "E8":"Y", "E9":"Z",
  "40":" ", "4B":".", "6B":",", "5A":"!"
};

function EBCDIC2ASCII()
{
    var numberOfLines = parseInt( stdin.readLine() );
    for( var i=0; i<numberOfLines; ++i )
    {
        var tokens = stdin.readLine().replace(/[\n\r]/g,"").split(/\s+/);
        // the first token is the number of codes, but we can
        // figure that out by checking the array length.
        var line = "";
        for( var t=1; t<tokens.length; ++t ) {
            line += ebcdictionary[tokens[t]];
        }
        stdout.writeLine( line );
    }
}

function reverseLookup( c )
{
    for( var code in ebcdictionary )
    {
        if( ebcdictionary[code] === c )
        {
            return code;
        }
    }
    return null;
}

function ASCII2EBCDIC()
{
    print( "type a line of text to translate into ebcdic: (zero-length line to exit)" );
    var line = stdin.readLine().replace(/[\n\r]/g,"");
    while( line.length )
    {
        var text = "" + line.length;
        for( var i=0; i<line.length; ++i )
        {
            text += " " + reverseLookup(line[i]);
        }
        stdout.writeLine( text + "\n" );
        line = stdin.readLine().replace(/[\n\r]/g,"");
    }
}

(function main(){
    // this program can also be used to generate input data for the contest 
    if( system.args.length > 1 && system.args[1] === "--a2e" ) {
        ASCII2EBCDIC();
    }
    else {
        EBCDIC2ASCII();
    }
})();
