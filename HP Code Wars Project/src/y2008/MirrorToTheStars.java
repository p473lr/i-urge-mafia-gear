import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *  Codewars2008
 *  Mirror to the Stars
 *  
 *  Assumptions:
 *    1. Image name has no spaces.
 *    
 *  Time: 3 hours
 *  Difficulty: low (4)
 *  Author: Michael Scheetz
 *  
 *  This code should work for any size image until the JVM runs out of memory.
 */
public class MirrorToTheStars
{
    /**
     * Just need to instantiate and run this object.
     * I may also want to run the problem from another class. 
     * Passing the exception all the way up makes some of the code 
     * look cleaner but would not be appropriate for product code.
     * 
     * @param args The keyboard input.
     */
    public static void main(final String [] args) throws Exception
    {
        MirrorToTheStars main = new MirrorToTheStars();
        main.execute();
    }

    /**
     * Use the ordinal as an index into the header array.
     *
     */
    enum HeaderIndex
    {
        name,
        width,
        height,
        transform
    };
    
    /**
     * Map the transformation input to an enumerated type. 
     *
     */
    public enum Transform_t
    {
        inversion('I'),
        reversal('R')
        ;
        
        /**
         * Need this map so we can lookup which transform the input represents.
         */
        private static Map<Character,Transform_t> lookup = new HashMap<Character,Transform_t>();
        static 
        {
            for(Transform_t trans : EnumSet.allOf(Transform_t.class))
            {
                 lookup.put(trans.getAcronym(), trans);
            }
        }
        
        /**
         * Store the input character for this transformation type.
         */
        private char myAcronym;
        /**
         * Store the method that will be used to perform this transformation type.
         */
        private Method myMethod;
        
        /**
         * Assign the transformation method which matches the enumerated label.
         * 
         * @param _acronym
         */
        private Transform_t(final char _acronym)
        {
            myAcronym = _acronym;
            try
            {
                myMethod = Transform_t.class.getDeclaredMethod(name(), Image.class);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                assert false;
            }
        }
        
        /**
         * Invert the given image.
         * Reflection is used to make this call from transform method.
         * Use the same name as the enumerated label.
         * 
         * @param image
         * @see transform(Image)
         */
        @SuppressWarnings("unused")
        private void inversion(Image image)
        {
            Collections.reverse(image.myPic);
        }

        /**
         * Reverse the given image.
         * Reflection is used to make this call from transform method.
         * Use the same name as the enumerated label.
         * 
         * @param image
         * @see transform(Image)
         */
        @SuppressWarnings("unused")
        private void reversal(Image image)
        {
            ArrayList<String>origPic = new ArrayList<String>(image.myPic);
            image.myPic.clear();
            for (String line : origPic)
            {
                StringBuffer revLine = new StringBuffer(line);
                image.myPic.add(revLine.reverse().toString());
            }
            origPic = null;
        }

        /**
         * Use reflection to invoke the appropriate method for this transformation.
         * 
         * WARNING: Using reflection this way moves some errors to runtime.
         * Should have a unit test to verify that all enums have a method.
         * OR - use A polymorphic class instead of an enum. 
         * OR - use an 'if' statement, but that would be too functional for my taste. :)
         * 
         * @param image
         */
        public void transform(Image image)
        {
            if ( null != image )
            {
                try
                {
                    myMethod.invoke(this, image);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    assert false;
                }
            }
        }
        
        /**
         * Provide an easy way to convert the input string to a list of transformations.
         * 
         * @param transform
         * @return
         */
        public static Set<Transform_t> lookup(final String transform)
        {
            Set<Transform_t> retval = EnumSet.noneOf(Transform_t.class);
            
            int len = transform.length();
            String upperTrans = transform.toUpperCase();
            for (int i=0 ; i<len ; i++) 
            {
                char token = upperTrans.charAt(i);
                Transform_t trans = lookup.get(token);
                if (trans != null)
                {
                    retval.add(trans);
                }
                else
                {
                    assert false:"Invalid tranformation string : " + transform;
                }
            }
            
            return retval;
        }

        /**
         * Getter method.
         * 
         * @return the acronym
         */
        public char getAcronym()
        {
            return myAcronym;
        }
    };
    
    /**
     * Use an object to encapsulate the image information.
     *
     */
    private class Image implements Cloneable
    {
        private String myLabel;
        /**
         * The picture to transform.
         */
        private ArrayList<String> myPic = new ArrayList<String>();
        /**
         * Start with an empty list of transformations.
         */
        private Set<Transform_t> myTrans = EnumSet.noneOf(Transform_t.class);
        /**
         * Used to note the current state of the image.
         */
        private boolean myIsTransformed = false;

        /**
         * A copy constructor.
         * 
         * @param that
         */
        public Image(final Image that)
        {
            if (that != null)
            {
                myLabel = that.myLabel;
                myTrans = that.myTrans;
                myIsTransformed = that.myIsTransformed;
                myPic = new ArrayList<String>(that.myPic);
            }
        }

        /**
         * Parse the header and convert the image into object attributes.
         * 
         * @param rawImage
         */
        public Image(final String[] header, final String[] rawImage)
        {
            myLabel = header[HeaderIndex.name.ordinal()];
            myTrans = Transform_t.lookup(header[HeaderIndex.transform.ordinal()]);

            // We don't need the height and width but we can use it to validate the data.
            final int width = Integer.valueOf(header[HeaderIndex.width.ordinal()]);
            final int height = Integer.valueOf(header[HeaderIndex.height.ordinal()]);
            
            int len = rawImage.length;
            for (int i=0 ; i<len ; i++)
            {
                String line = rawImage[i];
                assert width == line.length() : "Error: Unexpected width : expecting " + width + " but was " + line.length();
                myPic.add(line);
            }
            assert height == myPic.size() : "Error: Unexpected height : expecting " + height + " but was " + myPic.size();
        }

        /**
         * Transform this image as requested.
         * The transformation logic is in the Transform enumeration.
         * 
         * @param myPic
         * @return
         */
        Image transform()
        {
            Image retval = (Image)this.clone();
            for(Transform_t transformation : myTrans)
            {
                transformation.transform(retval);
            }
            // Toggle the cloned object's state
            retval.myIsTransformed = myIsTransformed == false;
            return retval;
        }

        /**
         * Do a deep copy of this object.
         * 
         * @return
         */
        public Object clone()
        {
            Image retval = new Image(this);
            return retval;
        }
        
        /**
         * Convert this image to output.
         */
        public String toString()
        {
            String newline = System.getProperty("line.separator");
            
            StringBuilder retval = new StringBuilder(myLabel);
            for(String imageLine : myPic)
            {
                retval.append(newline);
                retval.append(imageLine);
            }
            
            return retval.toString();
        }
    }
    
    /**
     * Execute the featured steps to solve the problem.
     */
    void execute() throws Exception
    {
        ArrayList<String> input = readStdin();
        Collection<Image> imageList= processInput(input);
        for (Image image : imageList)
        {
            Image outputImage = image.transform();
            System.out.println(outputImage.toString());
        }
    }
    
    /**
     * Read the stdin and return each line in a list.
     * Will not return null.
     * 
     * @return
     * @throws Exception
     */
    ArrayList<String> readStdin() throws Exception
    {
        ArrayList<String> retval = new ArrayList<String>();
        
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        
        if ( in != null )
        {
            if (in.ready())
            {
                for (String line = in.readLine(); line != null ; line = in.readLine())
                {
                    retval.add(line);
                }
            }
            in.close();
        }
        else
        {
            System.out.println("Error: no reader");
        }
        
        return retval;
    }

    /**
     * Parse the user input into distinct image objects.
     * Will not return null.
     * 
     * This method uses the height from the header to split the images.
     * We have done something like test for alpha characters and not use
     * any of the given dimensions.
     * 
     * @param args
     * @return
     */
    ArrayList<Image> processInput(final ArrayList<String> input)
    {
        ArrayList<Image> retval = new ArrayList<Image>();
        if (null != input)
        {
            final int len = input.size();
            final String [] inputArr = (String[])input.toArray(new String[0]);
            
            int pos = 0;
            while (pos < len)
            {
                final String header = input.get(pos);
                final String [] tokens = header.split(" ");
                
                final int imageSize = Integer.valueOf(tokens[HeaderIndex.height.ordinal()]);
                final String[] currentImage = new String[imageSize];
                
                System.arraycopy(inputArr, pos + 1, currentImage, 0, imageSize);
                retval.add(new Image(tokens, currentImage));
                pos += imageSize + 1;
            }        
        }        
        return retval;
    }
}
