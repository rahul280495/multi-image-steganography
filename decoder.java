import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.lang.Math.*;
class decoder
{
	public static void main(String args[])
	{
		//String filearray[]=new String[10];
		
		try
		{	
			FileOutputStream dt=new FileOutputStream("decodedtext.txt");
			String name=" ",mainmessage=" ";
			char num='0';
			int arg=Integer.parseInt(args[0]);
		for(int ij=0;ij<arg;ij++)
		{
			//System.out.println(ij);
			//System.out.println(num);
			name="nimg"+num+".jpg";
			System.out.println(name);
			File originalImage=new File(name); //input image file
			
			BufferedImage img=null;
			img=ImageIO.read(originalImage);
			String st="",st1="";
			BinaryTo b1=new BinaryTo();
			ToBinary1 o=new ToBinary1();
			int ctr=0;
			int bin[]=new int[8];
			int binary[]=new int[8];
			String mesfin=" ";
			char ascii=' ';
			int r,b,g,a,k;
			//BufferedImage img=new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_INT_ARGB);
			for(int i=0;i<img.getWidth();i++)
			{
				for(int j=0;j<img.getHeight();j++)
				{
					Color c=new Color(img.getRGB(i,j));
					//red
					r=c.getRed();
					bin=o.InttoBinary(r); //r to binary
					binary[ctr++]=bin[0];
					if(ctr==8)
					{
						ascii=(char)b1.BinaryToDecimal(binary);
						if(b1.BinaryToDecimal(binary)==0)
						{
							ctr=0;
							break;
						}
							
						mesfin+=ascii;
						for(k=0;k<8;k++)
							binary[k]=0;
						ctr=0;
					}
					
					//green
					g=c.getGreen();
					bin=o.InttoBinary(g); 
					binary[ctr++]=bin[0];
					if(ctr==8)
					{
						ascii=(char)b1.BinaryToDecimal(binary);
						if(b1.BinaryToDecimal(binary)==0)
						{
							ctr=0;
							break;
						}
						mesfin+=ascii;
						for(k=0;k<8;k++)
							binary[k]=0;
						ctr=0;
					}
					
					 //blue
					b=c.getBlue();
					bin=o.InttoBinary(b); //b to binary
					binary[ctr++]=bin[0];
					if(ctr==8)
					{
						ascii=(char)b1.BinaryToDecimal(binary);
						if(b1.BinaryToDecimal(binary)==0)
						{
							ctr=0;
							break;
						}
						mesfin+=ascii;
						for(k=0;k<8;k++)
							binary[k]=0;
						ctr=0;
					}
					
					a=c.getAlpha(); //alpha value(no use)
					
				}
				
			}
			for(int i=0;i<mesfin.length();i++)
			{
				if(mesfin.charAt(0)==' ')
					mesfin=mesfin.substring(1,mesfin.length());
				else
					break;
			}
			System.out.print(mesfin);
			num=mesfin.charAt(0);
			if(ij!=9)
			mesfin=mesfin.substring(1,mesfin.length());
			mainmessage=mainmessage+mesfin;
			
			
			
		}
		/*for(int i=0;i<mainmessage.length();i++)
			{
				if(mainmessage.charAt(0)==' ')
					mainmessage=mainmessage.substring(1,mainmessage.length());
				else
					break;
			}*/
		mainmessage=mainmessage.trim();
		System.out.println();
		System.out.println(mainmessage);
		byte box[]=mainmessage.getBytes();
					dt.write(box);
			dt.close();
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
		
	}
}

class ToBinary1
{
	int[] InttoBinary(int x)//int to bin conversion ,array return
	{
		int j;
		int bin[]=new int[8];
		for(j=0;j<8;j++)
   		bin[j]=0;
		j=0;
    	int quotient = x;
		while(quotient!=0)
		{
			bin[j++]= quotient % 2;
			quotient = (int)quotient / 2;
		} 
		return bin;
	}
	String StringToBinary(String x) // string to bin conversion, string returns(message)
	{
		String s,st=" ";
		String message="";
   try{  
     FileOutputStream fout=new FileOutputStream(x);  
	 char c;
	 
	 int i,j,ch,bin[]=new int[8],quotient;
     System.out.println("Enter the message to be encoded:");
	 BufferedReader br=new BufferedReader(new InputStreamReader(System.in)) ;
	 s=br.readLine();
	   System.out.println(s.length());
	 for(i=0;i<s.length();i++)
	 {

		c=s.charAt(i);
		  
		st=" ";
		for(j=0;j<8;j++)
   		bin[j]=0;
    	ch=(int)c;
    	//System.out.println(ch);
    	j=0;
    	quotient = ch;
	while(quotient!=0)
	{
        	 bin[j++]= quotient % 2;
		    quotient = (int)quotient / 2;
	}
		  st=""+bin[7]; 
	for(j=6;j>=0;j--)
		st=st+bin[j];
	message+=st;
	
	 byte b[]=st.getBytes();//converting string into byte array  
	 // for(int i=0;i<b.length;i++)
		//System.out.println(b[i]);
     fout.write(b);  
    
  
      }
	 
	 
      fout.close(); 
     System.out.println("success...");  
    }catch(Exception e){System.out.println(e);}
	return message;
	}
}

class BinaryTo
{
	int BinaryToDecimal(int bin[])
	{
		int i,term,intt=0;
		for( i=7;i>=0;i--)
		{
				term=(int)(bin[i]*Math.pow(2,7-i));
				intt+=term;
		}
		return intt;
	}
}