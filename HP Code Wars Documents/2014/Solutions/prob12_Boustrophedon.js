
"use strict"

importPackage(java.io);
importPackage(java.lang);
var stdin = new BufferedReader( new InputStreamReader(System['in']) )

function readLine()
{
    // stdin.readLine() returns a Java String. yuck.
    // convert it to a JavaScript string
    return String( stdin.readLine() );
};

function trim(s)
{
    return s.replace(/^\s+/g,'').replace(/\s+$/g,'');
};

(
    function Boustrophedon()
    {
        this.letters = { };
        this.reverse = { };

        this.parseLetterFile = function()
        {
            this.letters = { };
            this.reverse = { };
            var infile = new BufferedReader( new InputStreamReader( new FileInputStream("letters5x5.txt") ) );
            while( infile.ready() )
            {
                var L2R = '';
                var R2L = '';
                var letterChar = '*';
                var line = '';
                for( var i=0; i<5; ++i )
                {
                     line = String( infile.readLine() );
                     letterChar = line.match(/[^\.]/)[0];
                     line = line.replace(/[^\.]/g,"#");
                     L2R += line;
                     R2L += this.reverseLetterLine( line );
                }
                this.letters[ letterChar ] = L2R;
                this.reverse[ letterChar ] = R2L;
            }
            return letters;
        };
        this.reverseLetterLine = function( line )
        {
            return line.charAt(4) + line.charAt(3) + line.charAt(2) + line.charAt(1) + line.charAt(0);
        };
        this.searchLetter = function( textLetter, reference )
        {
            for( var letter in reference )
            {
                if( textLetter === reference[ letter ] )
                    return letter;
            }
            return ' ';
        };
        this.scanLetter = function( scanMatrix, p )
        {
            var textLetter = '';
            for( var line=0; line<5; ++line )
                textLetter += scanMatrix[line].substr( p, 5 );
            return textLetter.replace(/ /g,".");;
        };
        this.parseForTextDirection = function( scanMatrix )
        {
            var scanLength = scanMatrix[0].length;
            var c = '*';
            for( var p=0; p<scanLength; p+=6 )
            {
                var textLetter = this.scanLetter( scanMatrix, p );
                c = this.searchLetter( textLetter, this.letters );
                if( c === ' ' ) // letter is NOT L2R
                    return 'R2L';
                c = this.searchLetter( textLetter, this.reverse );
                if( c === ' ' ) // letter is NOT R2L
                    return 'L2R';
            }
            return '???'; // entire line uses symmetric letters
        };
        this.parseLine = function( scanMatrix, direction )
        {
            var text = '';
            var scanLength = scanMatrix[0].length;
            for( var p=0; p<scanLength; p+=6 )
            {
                var textLetter = this.scanLetter( scanMatrix, p );
                if( direction === 'L2R' )
                   text = text + this.searchLetter( textLetter, this.letters );
                else
                   text = this.searchLetter( textLetter, this.reverse ) + text;
            }
            return text;
        };
        this.parseInput = function()
        {
            var numberOfRows = parseInt( readLine() );
            var text = '';
            var direction = '';
            for( var n=0; n<numberOfRows; ++n )
            {
                readLine(); // read the empty line
                var scanMatrix = [ ];
                for( var i=0; i<5; ++i )
                    scanMatrix.push( readLine() );
                if( direction === '' )
                    direction = parseForTextDirection( scanMatrix );
                text += this.parseLine( scanMatrix, direction );
                direction = ( direction === 'L2R' ) ? 'R2L' : 'L2R';
            }
            return text;
        };

        this.parseLetterFile();
        print( this.parseInput() );
    }
)(); // self-invocation

