
"use strict"

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
        System.out.print( s + "\n" );
    };
    return stdio;
}
) ();
// END OF stdio.js =====================================================

function Node( value )
{
    this.value = value;
    this.left = null;
    this.right = null;
    this.parent = null;
    this.addLeft = function( node )
    {
        this.left = node;
        node.parent = this;
    };
    this.addRight = function( node )
    {
        this.right = node;
        node.parent = this;
    };
    this.traverse = function()
    {
        var text = '';
        if( this.left )  text += this.left.traverse();
        if( this.right ) text += this.right.traverse();
        text += this.value + ' ';
        return text;
    };
};

function NodeSet()
{
    this.set = {  };
    this.getNode = function( letter )
    {
        if( letter !== '.' && !this.set[ letter ] )
            this.set[ letter ] = new Node( letter );
        return this.set[ letter ];
    };
    this.getHead = function()
    {
        for( var letter in this.set )
        {
            var node = this.set[ letter ];
            if( !node.parent )
                return node;
        }
    };
};


function main()
{
    var nodeSet = new NodeSet();
    var n = parseInt( stdio.readLine() );
    for( var i=0; i<n; ++i )
    {
        var parent = nodeSet.getNode( stdio.readToken() );
        var left   = nodeSet.getNode( stdio.readToken() );
        var right  = nodeSet.getNode( stdio.readToken() );
        stdio.readLine(); // read the end-of-line
        if( left ) parent.addLeft( left );
        if( right ) parent.addRight( right );
    }
    stdio.printLine( nodeSet.getHead().traverse() );
};

main();
