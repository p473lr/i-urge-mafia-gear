
"use strict"

// This solution actually has two different solver routines: a really slow brute force solver and a
// faster-but-still-slow brute force solver. I'm sure there's a better way to do it but that's an
// exercise left to the reader. :-)

// load( "stdio.js" );
// the file stdio.js is included here so that the program is a single file.
// BEGIN stdio.js ======================================================
importPackage(java.io);
importPackage(java.lang);
//
// the pupose of the stdio class is to provide console I/O support
// in the rhino JavaScript environment using native JavaScript types.
// rhino I/O support is provided via Java library calls, which
// return Java types and not JavaScript types.
//
var stdio = (
function()
{
    var stdin = new BufferedReader(new InputStreamReader(System['in']));

    var stdio = {};

    stdio.readLine = function()
    {
        return new String( stdin.readLine() );
    };
    stdio.readChar = function()
    {
        return String.fromCharCode( stdin.read() );
    };
    // readToken() will read the input stream one character at a time.
    // it will ignore whitespace, then read non-whitespace characters
    // until a whitespace is read. the function returns the sequence
    // of non-whitespace characters read from stdin.
    stdio.readToken = function()
    {
        var token = '';
        var intValue = stdin.read(); // read a single character
        while( intValue <= 32 )
            intValue = stdin.read();
        while( intValue > 32 )
        {
            token += String.fromCharCode( intValue );
            intValue = stdin.read();
        }
        return token;
    };
    stdio.print = function( s )
    {
        System.out.print( s );
    };
    stdio.printLine = function( s )
    {
        if( !s ) s = '';
        System.out.print( s + "\n" );
    };
    return stdio;
}
) ();
// END OF stdio.js =====================================================


function FruitMath()
{
    this.parseInput = function()
    {
        this.size = parseInt( stdio.readLine() );
        for( var row=0; row<this.size; ++row )
        {
            this.grid.push( [] );
            var gridRow = this.grid[row];
            for( var col=0; col<this.size; ++col )
            {
                var letter = stdio.readToken();
                if( !this.letterHash[ letter ] )
                {
                    this.letterHash[ letter ] = { value: 0, anchor: false };
                    this.letters.push( letter );
                }
                gridRow.push( letter );
            }
            this.rowSums.push( parseInt( stdio.readLine() ) );
        }
        for( var col=0; col<this.size; ++col )
            this.colSums.push( parseInt( stdio.readToken() ) );
    };
    this.gridValue = function( row, col )
    {
        return this.letterHash[ this.grid[ row ][ col ] ].value;
    };
    this.test = function()
    {
        // no two letters may have the same value
        for( var letter1 in this.letterHash )
            for( var letter2 in this.letterHash )
                if( letter1 !== letter2 && this.letterHash[letter1].value === this.letterHash[letter2].value )
                    return false;
        // initialize the row and col sums
        var rowSums = [ 0, 0, 0, 0, 0, 0, 0, 0, 0 ];
        var colSums = [ 0, 0, 0, 0, 0, 0, 0, 0, 0 ];
        // calculate the row and col sums
        for( var row=0; row<this.size; ++row )
        {
            for( var col=0; col<this.size; ++col )
            {
                rowSums[row] += this.gridValue( row, col );
                colSums[col] += this.gridValue( row, col );
            }
        }
        // verify the row and col sums
        for( var i=0; i<this.size; ++i )
            if( rowSums[i] !== this.rowSums[i] || colSums[i] !== this.colSums[i] )
                return false;
        return true;
    };
    this.slowBruteForceSolver = function()
    {
        var max = 10;
        var init = 1;
        for( var i=1; i<this.letters.length; ++i )
        {
            max *= 10;
            init = 10*init + i+1;
        }
        for( var p=init; p<max; ++p )
        {
            pValue = p;
            for( var v=0; v<this.letters.length; ++v )
            {
                this.letterHash[ this.letters[v] ].value = pValue % 10;
                pValue = Math.floor( pValue / 10 );
            }
            if( this.test() )
            {
                this.printResult();
                return;
            }
        }
    };
    this.fasterBruteForceSolver = function()
    {
        var str = "123456789";
        var width = this.letters.length;
        var DEBUG = false;
        var DEBUG_COUNT = 10;
        var i = [ ];
        for( var x=0; x<=width; ++x )
            i.push(0);
        position = width - 1;
        var maxShift = 1 + str.length - width;
        while( position >= 0 )
        {
            var shiftSize = str.length - position;
            if(DEBUG) stdio.printLine( "position="+position+", i="+i+ ", shiftSize="+shiftSize );
            while( i[position]<shiftSize && shiftSize >= maxShift )
            {
                if(DEBUG) stdio.printLine(str.substring(0,width));
                if(DEBUG) if( --DEBUG_COUNT === 0 ) return;
                if(DEBUG) stdio.printLine(str);


                for( var v=0; v<width; ++v )
                    this.letterHash[ this.letters[v] ].value = parseInt(str.charAt(v));
                if( this.test() )
                {
                    this.printResult();
                    stdio.printLine("------");
                    return;
                }

                
                var s2 = rotate( str, shiftSize );
                if(DEBUG) stdio.printLine( str + " -> " + s2 );
                str = s2;

                ++i[position];

                if( position < width )
                {
                    i[ ++position ] = 0;
                    shiftSize = str.length - position;
                }
                if(DEBUG) stdio.printLine( "position="+position+", i="+i+ ", shiftSize="+shiftSize );
            }

            --position;
        }

        // str = "123456789" // length = 9
        //  n = 7
        // return "124567893"
        function rotate( str, n )
        {
            var leftSize = str.length - n;
            var left = str.substring( 0, leftSize );
            var c = str.charAt( leftSize );
            var right = str.substring( leftSize+1 );
            return left + right + c;
        }

    }; 
    this.printResult = function()
    {
        var hash = this.letterHash;
        // make a copy of this.letters before we sort so that the order of this.letters is preserved
        var sortedLetters = [ ];
        for( var i=0; i<this.letters.length; ++i )
            sortedLetters.push( this.letters[i] );
        sortedLetters.sort( function(a,b) { return hash[a].value - hash[b].value; } );
        for( var v=0; v<sortedLetters.length; ++v )
            stdio.printLine( hash[ sortedLetters[v] ].value + " " + this.fruits[ sortedLetters[v] ] );
    };
    this.size = 0;
    this.grid = [ ];
    this.colSums = [ ];
    this.rowSums = [ ];
    this.fruits = { A:"Apple", B:"Banana", C:"Cherry", D:"Durian", F:"Fig", G:"Grape", L:"Lychee", O:"Orange", P:"Pear" };
    this.letters = [ ];
    this.letterHash = { };
    this.parseInput();
    // this.slowBruteForceSolver();
    this.fasterBruteForceSolver();
};

new FruitMath();

