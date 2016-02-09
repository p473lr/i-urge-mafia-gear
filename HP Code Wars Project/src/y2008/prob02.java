import java.io.*;
import java.util.*;


/* inputs: fo fe fa
 * 
 */
public class prob02 {
	
	public static void main(String[] args)
	{
		try {
			while(true)
			{
				BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
				
				   String inputs = inp.readLine();
				   
				   StringTokenizer stoken=new StringTokenizer(inputs," ");     
			       
				   int count=stoken.countTokens();
			       
			       if(count !=3)
			    	   continue;
			       
			       String fo_str = stoken.nextToken();
			       String fe_str = stoken.nextToken();
			       String fa_str = stoken.nextToken();
			       
			       fo_str.trim();
			       fe_str.trim();
			       fa_str.trim();
			       
			       
			       float fo = Float.valueOf(fo_str).floatValue();
			       float fe = Float.valueOf(fe_str).floatValue();
			       float fa = Float.valueOf(fa_str).floatValue();
			       
			       if( fo==0 && fo==fe && fo==fa )
			       {   
			           System.exit(0);
			       }
			       			       
			       float M= fo/fe;
			       float ft= fa/M;
			       
			       float M_round = Round(M,2);
			       float ft_round = Round(ft,2);

			       System.out.printf("%.2f %.2f%n", M_round,ft_round);
		       
			}//while
		}//try
		catch (Exception e)
        {
            e.printStackTrace();
        }
	}//main
	
	public static float Round(float Rval, int Rpl) {
		  float p = (float)Math.pow(10,Rpl);
		  Rval = Rval * p;
		  float tmp = Math.round(Rval);
		  return (float)tmp/p;
		    }
}//class views