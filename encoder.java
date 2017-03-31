import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.lang.Math.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

class encoder
{
	public static void main(String args[])
	{
		
		int ij,kj;
		int arg=Integer.parseInt(args[0]);
		Random rn=new Random();
		String temp;
		String[] filearray={"img0.jpg","img1.jpg","img2.jpg","img3.jpg","img4.jpg","img5.jpg","img6.jpg","img7.jpg","img8.jpg","img9.jpg"};
		for(ij=arg-1;ij>1;ij--)
		{
			kj=rn.nextInt(ij+1);
			temp=filearray[kj];
			filearray[kj]=filearray[ij];
			filearray[ij]=temp;
		}
		for(ij=0;ij<arg;ij++)
		{
			if(filearray[ij]=="img0.jpg")
			{
				temp=filearray[0];
				filearray[0]=filearray[ij];
				filearray[ij]=temp;
				
			}
			//System.out.println(filearray[ij]);
		}
		String mess[]=new String[arg];
		
		try
		{
			BufferedImage img=null;
			int bin[]=new int[8];
			
			ToBinary1 o=new ToBinary1();
			BinaryTo b1=new BinaryTo();
			round rou=new round();
			String message=" ";
			String mainmessage=o.StringToBinary("messagetest.txt");  //stores the message data
			int length1=mainmessage.length(),count=0,r,g,b,a,k;
			int noc=length1/8;
			int tempo=0;
			System.out.println(mainmessage);
			System.out.println(noc);
			for(kj=arg;kj>=1;kj--)
			{
				mess[arg-kj]=mainmessage.substring(tempo,tempo+(8*rou.roundof(noc/kj)));
				//System.out.println(mess[arg-kj]);
				tempo+=8*rou.roundof(noc/kj);
				noc=noc-rou.roundof(noc/kj);
			}
			
			int no=0,number=0;
			char num=' ';
	for(ij=0;ij<arg;ij++)
	{
		count =0;
		FileOutputStream fout=new FileOutputStream("pixel"+ij+".txt");  //stores the input image date
		FileOutputStream fe=new FileOutputStream("pixelenco"+ij+".txt");  //stores the encoded image data
		File originalImage=new File(filearray[ij]); //input image file
		img=null;
		String st="",st1="";
		message=mess[ij];
		if(ij!=9)
		{
			num=filearray[ij+1].charAt(3);
			number=(int)num;
			System.out.println(number);
			bin=o.InttoBinary(number);
			for(int jj=0;jj<8;jj++)
				message=bin[jj]+message;
		}
			img=ImageIO.read(originalImage);	
			byte box[];
			
			
			//System.out.println();
			//System.out.println(filearray[ij]);
			System.out.println(message);
			
			
			int length=message.length();
			BufferedImage newimage=new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_INT_ARGB);
			for(int i=0;i<img.getWidth();i++)
			{
				for(int j=0;j<img.getHeight();j++)
				{
					Color c=new Color(img.getRGB(i,j));
					//red
					r=c.getRed();
					bin=o.InttoBinary(r); //r to binary
					st="";
					 st=""+bin[7]; 
					for(k=6;k>=0;k--)	
						st=st+bin[k];
					box=st.getBytes();
					fout.write(box); //print the binary in pixel.txt
					if(count<length)
					{
						if(message.charAt(count++)=='1') 
							bin[0]=1;  //avoiding 48-49 error
						else
							bin[0]=0;
					}
					else
						bin[0]=0;
					st1="";
					st1=""+bin[7]; 
					for(k=6;k>=0;k--)	
						st1=st1+bin[k];
					box=st1.getBytes();
					fe.write(box);  //print the encoded binary in pixelenco.txt
					r=b1.BinaryToDecimal(bin);  //back to dec
					
					//green
					g=c.getGreen();
					//System.out.println(g);  // checking the initial value of g
					bin=o.InttoBinary(g);  //g to binary
					st="";
					st=""+bin[7]; 
					for(k=6;k>=0;k--)	
						st=st+bin[k];
					 box=st.getBytes(); 
					fout.write(box); //print in pixel.txt
					if(count<length)
					{
						if(message.charAt(count++)=='1')
							bin[0]=1;
						else
							bin[0]=0;
					}
					else
						bin[0]=0;
					st1="";
					st1=""+bin[7]; 
					for(k=6;k>=0;k--)	
						st1=st1+bin[k];
					box=st1.getBytes();
					fe.write(box);  //print the encoded data in pixelenco.txt
					g=b1.BinaryToDecimal(bin);
					//System.out.println(g); //checking the new dec value of g
					 
					 //blue
					b=c.getBlue();
					bin=o.InttoBinary(b); //b to binary
					st="";
					st=""+bin[7]; 
					for(k=6;k>=0;k--)	
						st=st+bin[k];
					 box=st.getBytes();
					fout.write(box); // print binary in pixel.txt
					if(count<length)
					{
						if(message.charAt(count++)=='1')
							bin[0]=1;
						else
							bin[0]=0;
					}
					else
						bin[0]=0;
					st1="";
					st1=""+bin[7]; 
					for(k=6;k>=0;k--)	
						st1=st1+bin[k];
					box=st1.getBytes();
					fe.write(box); //print encoded in pixelenco.txt
					b=b1.BinaryToDecimal(bin); //back to dec
					a=c.getAlpha(); //alpha value(no use)
					
						//System.out.println(r+"."+g+"."+b+" ");
					
					Color nCol=new Color(r,g,b,a);
					newimage.setRGB(i,j,nCol.getRGB()); //setting the pixels nd colors 
				}
				//System.out.println();
			}
			fout.close(); // close pixel
			fe.close(); //close pixelenco
			String name="nimg"+no+".jpg";
			ImageIO.write(newimage,"png",new File(name)); //create the file image with args[1] name,buffer stores the current img
			no=number-48;
		}
	}
		catch(IOException e)
		{
			System.out.println(e);
		}
	}
}
			
class ToBinary1
{
	int[] InttoBinary(int x)//int to bin conversion ,array return(opposite)
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
		String s=" ",st=" ";
		String message="";
   try{  
     FileOutputStream fout=new FileOutputStream(x);  
	 char c;
	 String sCurrentLine;
	 BufferedReader br1=null;
	 BufferedReader br=null;
	 
	 int i,j,ch,bin[]=new int[8],quotient;
     //System.out.println("Enter the message file to be encoded:");
	 br1=new BufferedReader(new FileReader("enco.txt"));
	 while((sCurrentLine=br1.readLine())!=null)
	 {
		 s=s+sCurrentLine;
	 }
	 
	 br1.close();
	 s=s.trim();
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
		for( i=0;i<8;i++)
		{
				term=(int)(bin[i]*Math.pow(2,i));
				intt+=term;
		}
		return intt;
	}
}
class round
{
	int roundof(double xi )
	{
		if((xi-(int)xi)>=0.5)
			return (((int)xi)+1);
		else	
			return (int)xi;
	}
}